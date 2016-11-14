package com.ysu.zyw.tc.platform.web;

import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.platform.fk.shiro.TcCredentialsMatcher;
import com.ysu.zyw.tc.platform.svc.TcVerificationCodeService;
import com.ysu.zyw.tc.sys.ex.TcException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@Slf4j
@Api(value = "认证控制器")
@Controller
@RequestMapping(value = "/auth")
public class TcAuthenticationController {

    @Resource
    private TcCredentialsMatcher tcCredentialsMatcher;

    @Resource
    private TcVerificationCodeService tcVerificationCodeService;

    @ApiOperation(
            value = "获取登陆用一次性token",
            notes = "获取登陆用一次性token",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> token() {
        String token = tcCredentialsMatcher.createTokenAndSet2Session();
        return ResponseEntity.ok(TcR.ok(token));
    }

    @ApiOperation(
            value = "登陆",
            notes = "登陆",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/h_signup", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView signupWithForm(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String cltPassword,
            @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe,
            @RequestParam(value = "verificationCode") String verificationCode,
            @RequestParam(value = "targetUrl", required = false) String targetUrl,
            RedirectAttributes redirectAttributes) {
        TcR<Boolean> tcR =
                signup(username, cltPassword, rememberMe, verificationCode, targetUrl);
        if (tcR.orElse(false)) {
            // login succ
            return new ModelAndView(new RedirectView("/home.html"));
        } else {
            ModelAndView modelAndView = new ModelAndView(new RedirectView("/index.html"));
            redirectAttributes.addFlashAttribute("signupErrorMsg",
                    tcR.orElseGetStringExtraDescription(false, "系统异常，请稍后再试！"));
            return modelAndView;
        }
    }

    @ApiOperation(
            value = "登陆",
            notes = "登陆",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/j_signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TcR<Boolean>> signupWithAjax(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String cltPassword,
            @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe,
            @RequestParam(value = "verificationCode") String verificationCode,
            @RequestParam(value = "targetUrl", required = false) String targetUrl) {
        TcR<Boolean> tcR =
                signup(username, cltPassword, rememberMe, verificationCode, targetUrl);
        return ResponseEntity.ok(tcR);
    }

    @NotNull
    private TcR<Boolean> signup(String username,
                                String cltPassword,
                                Boolean rememberMe,
                                String verificationCode,
                                String targetUrl) {
        // verify verification code
        boolean verificationCodeMatch = tcVerificationCodeService.isVerificationCodeMatch(verificationCode);
        if (!verificationCodeMatch) {
            TcR<Boolean> tcR = TcR.ok(false);
            tcR.extra("验证码输入错误，请重试！");
            return tcR;
        }

        // login
        UsernamePasswordToken token = new UsernamePasswordToken(username, cltPassword);
        token.setRememberMe(rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            TcR<Boolean> tcR = TcR.ok(false);
            tcR.extra("账号不存在，请重试！");
            return tcR;
        } catch (LockedAccountException e) {
            TcR<Boolean> tcR = TcR.ok(false);
            tcR.extra("账号被锁定，请稍后再试！");
            return tcR;
        } catch (IncorrectCredentialsException e) {
            TcR<Boolean> tcR = TcR.ok(false);
            tcR.extra("账号密码错误，请重试！");
            return tcR;
        } catch (TcException e) {
            log.error("[{}] [{}] [{}]", e, username, cltPassword.substring(12), rememberMe);
            TcR<Boolean> tcR = TcR.ok(false);
            tcR.extra("系统异常，请稍后再试！");
            return tcR;
        } catch (Exception e) {
            log.error("[{}] [{}] [{}]", e, username, cltPassword.substring(12), rememberMe);
            TcR<Boolean> tcR = TcR.ok(false);
            tcR.extra("系统异常，请稍后再试！");
            return tcR;
        }

        // set session
        return TcR.ok(true);
    }

    @ApiOperation(
            value = "登出",
            notes = "登出",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public ModelAndView signout() {
        SecurityUtils.getSubject().logout();
        return new ModelAndView(new RedirectView("/index.html"));
    }

}

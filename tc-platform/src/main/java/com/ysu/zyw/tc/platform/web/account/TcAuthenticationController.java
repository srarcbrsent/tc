package com.ysu.zyw.tc.platform.web.account;

import com.ysu.zyw.tc.api.api.TcAuthenticationApi;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToMenu;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToPermission;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.platform.fk.shiro.TcCredentialsMatcher;
import com.ysu.zyw.tc.platform.svc.TcSessionService;
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
import java.util.List;
import java.util.Objects;

@Slf4j
@Api(value = "认证控制器")
@Controller
@RequestMapping(value = "/auth")
public class TcAuthenticationController {

    @Resource
    private TcCredentialsMatcher tcCredentialsMatcher;

    @Resource
    private TcVerificationCodeService tcVerificationCodeService;

    @Resource
    private TcSessionService tcSessionService;

    @Resource
    private TcAuthenticationApi tcAuthenticationApi;

    @ApiOperation(
            value = "获取登陆用一次性token",
            notes = "获取登陆用一次性token")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> token() {
        String token = tcCredentialsMatcher.createTokenAndSet2Session();
        return ResponseEntity.ok(TcR.ok(token));
    }

    @ApiOperation(
            value = "登陆",
            notes = "登陆")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signup(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String cltPassword,
            @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe,
            @RequestParam(value = "verificationCode") String verificationCode,
            @RequestParam(value = "targetUrl", required = false) String targetUrl,
            RedirectAttributes redirectAttributes) {
        TcR<Boolean> tcR =
                signup(username, cltPassword, rememberMe, verificationCode);
        if (tcR.orElse(false)) {
            // login succ
            if (Objects.nonNull(targetUrl)) {
                return new ModelAndView(new RedirectView(targetUrl));
            }
            return new ModelAndView(new RedirectView("/home.html"));
        } else {
            ModelAndView modelAndView = new ModelAndView(new RedirectView("/index.html"));
            redirectAttributes.addFlashAttribute("signupErrorMsg", tcR.orElseGetStringExtra());
            return modelAndView;
        }
    }

    @ApiOperation(
            value = "登陆",
            notes = "登陆")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/_signup", method = RequestMethod.POST)
    public ResponseEntity<TcR<Boolean>> _signup(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String cltPassword,
            @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe,
            @RequestParam(value = "verificationCode") String verificationCode,
            @RequestParam(value = "targetUrl", required = false) String targetUrl) {
        TcR<Boolean> tcR =
                signup(username, cltPassword, rememberMe, verificationCode);
        return ResponseEntity.ok(tcR);
    }

    @NotNull
    private TcR<Boolean> signup(String username,
                                String cltPassword,
                                Boolean rememberMe,
                                String verificationCode) {
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
        }

        // set session
        tcSessionService.initSessionAfterSignup();

        // find menus
        String accountId = tcSessionService.getAccountId();
        TcP<List<ToMenu>> menus = tcAuthenticationApi.findMenus(accountId);
        // TODO: 2016/11/26

        return TcR.ok(true);
    }

    @ApiOperation(
            value = "登出",
            notes = "登出")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public ModelAndView signout() {
        SecurityUtils.getSubject().logout();
        return new ModelAndView(new RedirectView("/index.html"));
    }

    @ApiOperation(
            value = "获取菜单",
            notes = "获取菜单")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/get_menus", method = RequestMethod.GET)
    public TcP<List<ToMenu>> getMenus() {

        String accountId = tcSessionService.getAccountId();

        return tcAuthenticationApi.findMenus(accountId);
    }

    @ApiOperation(
            value = "获取角色",
            notes = "获取角色")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/get_roles", method = RequestMethod.GET)
    public TcP<List<ToRole>> getRoles() {

        String accountId = tcSessionService.getAccountId();

        return tcAuthenticationApi.findRoles(accountId);
    }

    @ApiOperation(
            value = "获取页面元素权限",
            notes = "获取页面元素权限")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/get_permissions", method = RequestMethod.GET)
    public TcP<List<ToPermission>> getPermissions() {

        String accountId = tcSessionService.getAccountId();

        return tcAuthenticationApi.findPermissions(accountId);
    }

}

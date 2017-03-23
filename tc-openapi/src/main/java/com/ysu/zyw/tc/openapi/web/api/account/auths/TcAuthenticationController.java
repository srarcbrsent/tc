package com.ysu.zyw.tc.openapi.web.api.account.auths;

import com.ysu.zyw.tc.api.api.accounts.TcAuthenticationApi;
import com.ysu.zyw.tc.components.servlet.support.TcXsrfTokenFilter;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.openapi.fk.config.TcConfig;
import com.ysu.zyw.tc.openapi.svc.TcAuthenticationService;
import com.ysu.zyw.tc.openapi.svc.TcSessionService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@Api(value = "认证控制器")
@Controller
@RequestMapping(value = "/auths")
public class TcAuthenticationController {

    @Resource
    private TcAuthenticationService tcAuthenticationService;

    @Resource
    private TcSessionService tcSessionService;

    @Resource
    private TcAuthenticationApi tcAuthenticationApi;

    @Resource
    private TcConfig tcConfig;

    @ApiOperation(
            value = "获取登陆验证码",
            notes = "获取登陆验证码")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/get_verification_code", method = RequestMethod.GET)
    public ResponseEntity<TcR<String>> fetchVerificationCode() {
        String verificationCode = tcAuthenticationService.generateVerificationCodeAndSet2Cache();
        return ResponseEntity.ok(TcR.ok(verificationCode));
    }

    @ApiOperation(
            value = "获取登陆加密公钥",
            notes = "获取登陆加密公钥")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/get_public_key", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> getPublicKey() {
        String publicKey = tcAuthenticationService.generateRSAKeyAndSet2Session();
        return ResponseEntity.ok(TcR.ok(publicKey));
    }

    /**
     * 0 ==> 登陆成功;
     * 1 ==> 验证码已过期;
     * 2 ==> 验证码输入错误;
     * 3 ==> 账号不存在;
     * 4 ==> 账号被锁定;
     * 5 ==> 账号密码错误;
     */
    @ApiOperation(
            value = "登陆",
            notes = "登陆")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<TcR<Integer>> login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String rsaEncryptedPassword,
            @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe,
            @RequestParam(value = "verificationCode") String verificationCode,
            HttpServletResponse response) {
        // verify verification code
        String verificationCodeInCache = tcAuthenticationService.getVerificationCodeInCache();
        if (Objects.isNull(verificationCodeInCache)) {
            return ResponseEntity.ok(TcR.code(1, "验证码已过期！"));
        }
        boolean verificationCodeMatch = tcAuthenticationService.isVerificationCodeMatch(
                verificationCodeInCache, verificationCode);
        if (!verificationCodeMatch) {
            return ResponseEntity.ok(TcR.code(2, "验证码输入错误！"));
        }

        // login
        String password = tcAuthenticationService.decryptRSAEncryptedPassword(rsaEncryptedPassword);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            return ResponseEntity.ok(TcR.code(3, "账号不存在！"));
        } catch (LockedAccountException e) {
            return ResponseEntity.ok(TcR.code(4, "账号被锁定！"));
        } catch (IncorrectCredentialsException e) {
            return ResponseEntity.ok(TcR.code(5, "账号密码错误！"));
        }

        // set xsrf cookie
        TcXsrfTokenFilter.addXsrfCookie(response);

        // set session
        tcSessionService.initSessionAfterLogin();

        // find menus
        // String accountId = tcSessionService.getAccountId();
        // TcP<List<ToMenu>> menus = tcAuthenticationApi.findMenus(accountId);
        // TODO: 2016/11/26


        // mq TODO

        log.info("session id -> [{}] login success", SecurityUtils.getSubject().getSession().getId());

        // successful
        return ResponseEntity.ok(TcR.code(0, "登陆成功！"));
    }

    @ApiOperation(
            value = "是否已登陆",
            notes = "是否已登陆")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    public ResponseEntity<TcR<Boolean>> authenticated() {
        return ResponseEntity.ok(TcR.ok(SecurityUtils.getSubject().isAuthenticated()));
    }

    @ApiOperation(
            value = "登出",
            notes = "登出")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<TcR<Void>> logout(HttpServletResponse response) {
        // logout
        SecurityUtils.getSubject().logout();

        // remove csrf cookie
        TcXsrfTokenFilter.removeXsrfCookie(response);

        // mq TODO

        log.info("session id -> [{}] logout success", SecurityUtils.getSubject().getSession().getId());

        // successful
        return ResponseEntity.ok(TcR.ok());
    }

}

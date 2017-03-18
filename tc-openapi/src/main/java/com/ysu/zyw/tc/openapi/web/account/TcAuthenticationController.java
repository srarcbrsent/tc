package com.ysu.zyw.tc.openapi.web.account;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.api.accounts.TcAuthenticationApi;
import com.ysu.zyw.tc.components.servlet.support.TcXsrfTokenFilter;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToMenu;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToPermission;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.openapi.fk.config.TcConfig;
import com.ysu.zyw.tc.openapi.svc.TcSessionService;
import com.ysu.zyw.tc.openapi.svc.TcVerificationCodeService;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@Api(value = "认证控制器")
@Controller
@RequestMapping(value = "/auths")
public class TcAuthenticationController {

    @Resource
    private TcVerificationCodeService tcVerificationCodeService;

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
        String verificationCode = tcVerificationCodeService.generateVerificationCodeAndSet2Session();
        return ResponseEntity.ok(TcR.ok(verificationCode));
    }

    @ApiOperation(
            value = "获取登陆加密公钥",
            notes = "获取登陆加密公钥")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/get_public_key", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> getPublicKey() {
        // TODO
        return ResponseEntity.ok(TcR.ok("1"));
    }

    /**
     * 0 ==> 登陆成功;
     * 1 ==> 验证码输入错误;
     * 2 ==> 账号不存在;
     * 3 ==> 账号被锁定;
     * 4 ==> 账号密码错误;
     */
    @ApiOperation(
            value = "登陆",
            notes = "登陆")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<TcR<Integer>> signup(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String cltPassword,
            @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe,
            @RequestParam(value = "verificationCode") String verificationCode,
            HttpServletResponse response) {
        // verify verification code
        boolean verificationCodeMatch = tcVerificationCodeService.isVerificationCodeMatch(verificationCode);
        if (!verificationCodeMatch) {
            return ResponseEntity.ok(TcR.code(1, "验证码输入错误！"));
        }

        // login
        UsernamePasswordToken token = new UsernamePasswordToken(username, cltPassword);
        token.setRememberMe(rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            return ResponseEntity.ok(TcR.code(2, "账号不存在！"));
        } catch (LockedAccountException e) {
            return ResponseEntity.ok(TcR.code(3, "账号被锁定！"));
        } catch (IncorrectCredentialsException e) {
            return ResponseEntity.ok(TcR.code(4, "账号密码错误！"));
        }

        // set cookie
        response.addCookie(new Cookie(TcXsrfTokenFilter.XSRF_TOKEN_COOKIE_NAME, UUID.randomUUID().toString()));

        // set session
        tcSessionService.initSessionAfterSignup();

        // find menus
        // String accountId = tcSessionService.getAccountId();
        // TcP<List<ToMenu>> menus = tcAuthenticationApi.findMenus(accountId);
        // TODO: 2016/11/26

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
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public ResponseEntity<TcR<Void>> signout() {
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok(TcR.ok());
    }

    @ApiOperation(
            value = "获取菜单",
            notes = "获取菜单")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/get_menus", method = RequestMethod.GET)
    public ResponseEntity<TcP<List<ToMenu>>> getMenus() {

        String accountId = tcSessionService.getAccountId();
        TcP<List<ToMenu>> menus = tcAuthenticationApi.findMenus(accountId);

        return ResponseEntity.ok(menus);
    }

    @ApiOperation(
            value = "获取角色",
            notes = "获取角色")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/get_roles", method = RequestMethod.GET)
    public ResponseEntity<TcP<List<ToRole>>> getRoles() {

        TcP<List<ToRole>> roles = TcP.ok(Lists.newArrayList());
        if (SecurityUtils.getSubject().isAuthenticated()) {
            String accountId = tcSessionService.getAccountId();
            roles = tcAuthenticationApi.findRoles(accountId);
        }

        return ResponseEntity.ok(roles);
    }

    @ApiOperation(
            value = "获取页面元素权限",
            notes = "获取页面元素权限")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/get_permissions", method = RequestMethod.GET)
    public ResponseEntity<TcP<List<ToPermission>>> getPermissions() {

        TcP<List<ToPermission>> permissions = TcP.ok(Lists.newArrayList());
        if (SecurityUtils.getSubject().isAuthenticated()) {
            String accountId = tcSessionService.getAccountId();
            permissions = tcAuthenticationApi.findPermissions(accountId);
        }

        return ResponseEntity.ok(permissions);
    }

}

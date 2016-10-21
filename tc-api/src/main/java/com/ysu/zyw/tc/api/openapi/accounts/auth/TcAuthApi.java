package com.ysu.zyw.tc.api.openapi.accounts.auth;

import com.ysu.zyw.tc.api.svc.accounts.TcAccountService;
import com.ysu.zyw.tc.api.svc.accounts.auth.TcAuthService;
import com.ysu.zyw.tc.model.api.accounts.auth.TmPermission;
import com.ysu.zyw.tc.model.c.TcR;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/account/auth")
@Api(value = "账号认证控制器")
public class TcAuthApi {

    @Resource
    private TcAuthService tcAuthService;

    @Resource
    private TcAccountService tcAccountService;

    @ApiOperation(
            value = "登录",
            notes = "登录",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "验证错误，参见extra")
    })
    @RequestMapping(value = "/can_signin", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<String>> canSignin(
            @ApiParam(value = "用户名") @RequestParam(value = "username") String username,
            @ApiParam(value = "密码") @RequestParam(value = "password") String password,
            @ApiParam(value = "账号可登陆") @RequestParam(value = "canAccountLogin", defaultValue = "true")
                    Boolean canAccountLogin,
            @ApiParam(value = "邮箱可登陆") @RequestParam(value = "canEmailLogin", defaultValue = "true")
                    Boolean canEmailLogin,
            @ApiParam(value = "手机可登陆") @RequestParam(value = "canMobileLogin", defaultValue = "true")
                    Boolean canMobileLogin) {

        String succLoginAccountId = tcAccountService.canSignin(
                username, password, canAccountLogin, canEmailLogin, canMobileLogin);

        // TODO mq

        return ResponseEntity.ok(TcR.ok(succLoginAccountId));
    }

    @ApiOperation(
            value = "获取菜单",
            notes = "根据当前用户的权限获取对应的菜单",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "", response = TcR.class)
    @RequestMapping(value = "/find_menus/{id}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<List<TmPermission>> findMenus(
            @ApiParam(value = "账号id") @PathVariable(value = "id") String accountId) {
        List<TmPermission> menuList = tcAuthService.findMenus(accountId);
        return ResponseEntity.ok(menuList);
    }

    @ApiOperation(
            value = "获取菜单",
            notes = "根据当前用户的权限获取对应的菜单",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "", response = TcR.class)
    @RequestMapping(value = "/find_pms/{id}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<List<TmPermission>> findPms(
            @ApiParam(value = "账号id") @PathVariable(value = "id") String accountId) {
        List<TmPermission> pms = tcAuthService.findPms(accountId);
        return ResponseEntity.ok(pms);
    }

}

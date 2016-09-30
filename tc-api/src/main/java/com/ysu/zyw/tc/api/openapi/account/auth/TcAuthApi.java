package com.ysu.zyw.tc.api.openapi.account.auth;

import com.ysu.zyw.tc.api.svc.account.auth.TcAuthService;
import com.ysu.zyw.tc.model.api.account.auth.TmPermission;
import com.ysu.zyw.tc.model.c.TcR;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
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

    @ApiOperation(
            value = "判断用户是否可以登录",
            notes = "判断用户是否可以登录<br/> code == 0可以登录 <br/>code == 1用户存在但密码错误",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "", response = TcR.class)
    @RequestMapping(value = "/can_login", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Integer>> canLogin(
            @ApiParam(value = "账号", required = true) @RequestParam(value = "username") String username,
            @ApiParam(value = "密码", required = true) @RequestParam(value = "password") String password) {

        return new ResponseEntity<>(new TcR<>(1), HttpStatus.OK);
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
    @RequestMapping(value = "/menus/{id}", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<List<TmPermission>> getMenus(@PathVariable(value = "id") String accountId) {
        List<TmPermission> menuList = tcAuthService.findMenus(accountId);
        return ResponseEntity.ok(menuList);
    }

}

package com.ysu.zyw.tc.openapi.web.api.account.auths;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.api.accounts.TcAuthenticationApi;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToMenu;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToPermission;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.openapi.fk.config.TcConfig;
import com.ysu.zyw.tc.openapi.svc.TcAuthenticationService;
import com.ysu.zyw.tc.openapi.svc.TcSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Api(value = "认证控制器")
@Controller
@RequestMapping(value = "/auths")
public class TcAuthorizationController {

    @Resource
    private TcAuthenticationService tcAuthenticationService;

    @Resource
    private TcAuthenticationApi tcAuthenticationApi;

    @Resource
    private TcSessionService tcSessionService;

    @Resource
    private TcConfig tcConfig;

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

package com.ysu.zyw.tc.api.api;

import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToMenu;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToPermission;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(value = "/auths")
public interface TcAuthenticationApi {

    /**
     * 200 ==> 成功 返回可登陆的账号;
     * 400 ==> 参数错误;
     * 422 ==> 业务级不可登陆: code == 1 => 账号不存在; code == 2 => 账号被锁定;
     * 500 ==> 系统异常;
     */
    @POST
    @Path(value = "/signup")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<ToAccount> signup(
            @FormParam(value = "username") String username,
            @FormParam(value = "canAccountLogin") @DefaultValue(value = "true") Boolean canAccountLogin,
            @FormParam(value = "canEmailLogin") @DefaultValue(value = "true") Boolean canEmailLogin,
            @FormParam(value = "canMobileLogin") @DefaultValue(value = "true") Boolean canMobileLogin
    );

    /**
     * @param project platform ==> platform
     * @param platform web ==> web; wx ==> weixin-app
     */
    @GET
    @Path(value = "/find_menus/{project}/{platform}/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcP<List<ToMenu>> findMenus(
            @PathParam(value = "project") String project,
            @PathParam(value = "platform") String platform,
            @PathParam(value = "id") String accountId
    );

    /**
     * @param project platform ==> platform
     * @param platform web ==> web; wx ==> weixin-app
     */
    @GET
    @Path(value = "/find_pms/{project}/{platform}/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcP<List<ToPermission>> findPermissions(
            @PathParam(value = "project") String project,
            @PathParam(value = "platform") String platform,
            @PathParam(value = "id") String accountId
    );

}

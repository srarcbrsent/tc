package com.ysu.zyw.tc.api;

import com.ysu.zyw.tc.model.accounts.auth.TmPermission;
import com.ysu.zyw.tc.mw.TcP;
import com.ysu.zyw.tc.mw.TcR;

import javax.ws.rs.*;
import java.util.List;

public interface TcAuthenticationApi {
    @POST
    @Path(value = "/can_signup")
    TcR<String, Void> canSignup(
            @FormParam(value = "username") String username,
            @FormParam(value = "password") String password,
            @FormParam(value = "canAccountLogin") @DefaultValue(value = "true") Boolean canAccountLogin,
            @FormParam(value = "canEmailLogin") @DefaultValue(value = "true") Boolean canEmailLogin,
            @FormParam(value = "canMobileLogin") @DefaultValue(value = "true") Boolean canMobileLogin
    );

    @GET
    @Path(value = "/find_menus/{id}")
    TcP<List<TmPermission>, Void> findMenus(
            @PathParam(value = "id") String accountId
    );

    @GET
    @Path(value = "/find_pms/{id}")
    TcP<List<TmPermission>, Void> findPms(
            @PathParam(value = "id")String accountId
    );
}

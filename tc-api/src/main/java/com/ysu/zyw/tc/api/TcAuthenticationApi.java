package com.ysu.zyw.tc.api;

import com.ysu.zyw.tc.model.accounts.TmAccount;
import com.ysu.zyw.tc.model.accounts.auth.TmPermission;
import com.ysu.zyw.tc.mw.TcP;
import com.ysu.zyw.tc.mw.TcR;
import com.ysu.zyw.tc.validator.TcValidator;

import javax.ws.rs.*;
import java.util.List;

@Path(value = "/auth")
public interface TcAuthenticationApi {

    @POST
    @Path(value = "/signup")
    TcR<TmAccount, TcValidator.TcVerifyFailure> signup(
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

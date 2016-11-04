package com.ysu.zyw.tc.api;

import com.ysu.zyw.tc.model.accounts.TmAccount;
import com.ysu.zyw.tc.mw.TcR;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(value = "/accounts")
public interface TcAccountsApi {

//    @POST
//    @Path(value = "/create_account")
//    @Consumes(value = {MediaType.APPLICATION_JSON})
//    @Produces(value = {MediaType.APPLICATION_JSON})
//    TcR<Void, Void> createAccount();

    @GET
    @Path(value = "/find_account")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public TcR<TmAccount, Void> findAccount();

}

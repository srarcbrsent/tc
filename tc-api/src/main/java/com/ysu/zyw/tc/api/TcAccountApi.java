package com.ysu.zyw.tc.api;

import com.ysu.zyw.tc.model.accounts.TmAccount;
import com.ysu.zyw.tc.mw.TcR;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(value = "/account")
public interface TcAccountApi {

    @POST
    @Path(value = "/create_account")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Void, Void> createAccount(
            TmAccount tmAccount
    );

    @GET
    @Path(value = "/delete_account/{id}")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Void, Void> deleteAccount(
            @PathParam(value = "id") String accountId
    );

    @POST
    @Path(value = "/update_account")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Void, Void> updateAccount(
            TmAccount tmAccount
    );

    @GET
    @Path(value = "/find_account/{id}")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<TmAccount, Void> findAccount(
            @PathParam(value = "id") String accountId
    );

    @GET
    @Path(value = "/find_accounts")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<TmAccount, Void> findAccounts(
            @QueryParam(value = "ids") List<String> accountIds
    );

}

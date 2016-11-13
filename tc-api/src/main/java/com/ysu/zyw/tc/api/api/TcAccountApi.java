package com.ysu.zyw.tc.api.api;

import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.api.i.accounts.TiFindAccountsTerms;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.jboss.resteasy.annotations.Form;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(value = "/accounts")
public interface TcAccountApi {

    @POST
    @Path(value = "/create_account")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<String, Object> createAccount(
            @Valid TiAccount tiAccount
    );

    @GET
    @Path(value = "/delete_account/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Void, Object> deleteAccount(
            @NotEmpty @Length(min = 32, max = 32) @PathParam(value = "id") String accountId,
            @NotEmpty @Length(min = 32, max = 32) @QueryParam(value = "delector") String delector
    );

    @POST
    @Path(value = "/update_account")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Void, Object> updateAccount(
            @Valid TiAccount tiAccount
    );

    @GET
    @Path(value = "/find_account/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<ToAccount, Object> findAccount(
            @NotEmpty @Length(min = 32, max = 32) @PathParam(value = "id") String accountId
    );

    @POST
    @Path(value = "/count_accounts")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Long, Object> countAccounts(
            @Form TiFindAccountsTerms tiFindAccountsTerms
    );

    @POST
    @Path(value = "/find_accounts/{currentPage}/{pageSize}")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcP<List<ToAccount>, Object> findAccounts(
            @Form TiFindAccountsTerms tiFindAccountsTerms,
            @NotEmpty @Range(min = 0, max = Long.MAX_VALUE) @PathParam(value = "currentPage") Integer currentPage,
            @NotEmpty @Range(min = 1, max = 30) @PathParam(value = "pageSize") Integer pageSize
    );

}

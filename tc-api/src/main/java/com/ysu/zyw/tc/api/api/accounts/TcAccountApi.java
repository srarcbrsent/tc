package com.ysu.zyw.tc.api.api.accounts;

import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.api.i.accounts.TiFindAccountsTerms;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(value = "/accounts")
public interface TcAccountApi {

    /**
     * @code code == 0 创建成功, 可登陆账号的accountId;
     * @code code == 11 => 昵称重复;
     * @code code == 12 => 邮箱重复;
     * @code code == 13 => 手机重复;
     * @code code == 9999 => 系统异常;
     */
    @POST
    @Path(value = "/create_account")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<String> createAccount(
            TiAccount tiAccount
    );

    /**
     * @code code == 0 删除成功;
     * @code code == 1 => 账号不存在;
     * @code code == 9999 => 系统异常;
     */
    @GET
    @Path(value = "/delete_account/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Void> deleteAccount(
            @PathParam(value = "id") String accountId,
            @QueryParam(value = "delector") String delector
    );

    /**
     * @code code == 0 更新成功;
     * @code code == 1 => 账号不存在;
     * @code code == 11 => 昵称重复;
     * @code code == 12 => 邮箱重复;
     * @code code == 13 => 手机重复;
     * @code code == 9999 => 系统异常;
     */
    @POST
    @Path(value = "/update_account")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Void> updateAccount(
            TiAccount tiAccount
    );

    /**
     * @code code == 0 查询成功;
     * @code code == 9999 => 系统异常;
     */
    @GET
    @Path(value = "/find_account/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<ToAccount> findAccount(
            @PathParam(value = "id") String accountId
    );

    /**
     * @code code == 0 查询成功;
     * @code code == 9999 => 系统异常;
     */
    @POST
    @Path(value = "/count_accounts")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<Long> countAccounts(
            @Form TiFindAccountsTerms tiFindAccountsTerms
    );

    /**
     * @code code == 0 查询成功;
     * @code code == 9999 => 系统异常;
     */
    @POST
    @Path(value = "/find_accounts/{currentPage}/{pageSize}")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcP<List<ToAccount>> findAccounts(
            @Form TiFindAccountsTerms tiFindAccountsTerms,
            @PathParam(value = "currentPage") Integer currentPage,
            @PathParam(value = "pageSize") Integer pageSize
    );

}

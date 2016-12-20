package com.ysu.zyw.tc.platform.web.account;

import com.ysu.zyw.tc.api.api.TcAccountApi;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.platform.svc.TcSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Slf4j
@Api(value = "账号控制器")
@Controller
@RequestMapping(value = "/accounts")
public class TcAccountController {

    @Resource
    private TcAccountApi tcAccountApi;

    @Resource
    private TcSessionService tcSessionService;

    @ApiOperation(
            value = "创建账号",
            notes = "创建账号")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/create_account", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> createAccount(
            @RequestBody TiAccount tiAccount) {

        String accountId = tcSessionService.getAccountId();
        tiAccount.setOperatorAccountId(accountId);
        TcR<String> tcR = tcAccountApi.createAccount(tiAccount);

        return ResponseEntity.ok(tcR);
    }

    @ApiOperation(
            value = "删除账号",
            notes = "删除账号")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/delete_account/{id}", method = RequestMethod.GET)
    public ResponseEntity<TcR<Void>> deleteAccount(
            @PathVariable(value = "id") String deleteAccountId) {

        String accountId = tcSessionService.getAccountId();
        TcR<Void> tcR = tcAccountApi.deleteAccount(deleteAccountId, accountId);

        return ResponseEntity.ok(tcR);
    }

    @ApiOperation(
            value = "更新账号",
            notes = "更新账号")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/update_account", method = RequestMethod.POST)
    public ResponseEntity<TcR<Void>> updateAccount(
            @RequestBody TiAccount tiAccount) {

        String accountId = tcSessionService.getAccountId();
        tiAccount.setOperatorAccountId(accountId);
        TcR<Void> tcR = tcAccountApi.updateAccount(tiAccount);

        return ResponseEntity.ok(tcR);
    }

}

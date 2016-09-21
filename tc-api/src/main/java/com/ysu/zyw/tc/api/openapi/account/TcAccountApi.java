package com.ysu.zyw.tc.api.openapi.account;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.svc.account.TcAccountService;
import com.ysu.zyw.tc.model.api.account.TmAccount;
import com.ysu.zyw.tc.model.api.menum.TmSigninPlatform;
import com.ysu.zyw.tc.model.c.TcP;
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
import java.util.Objects;

@Controller
@RequestMapping(value = "/accounts")
@Api(value = "账号控制器")
public class TcAccountApi {

    @Resource
    private TcAccountService tcAccountService;

    public void createAccount() {

    }

    @ApiOperation(
            value = "删除指定用户",
            notes = "通过id删除指定用户",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(paramType = "header", name = "X-ApiVersion", defaultValue = "1.0", dataType = "string")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Boolean>> deleteAccount() {
        return new ResponseEntity<>(new TcR<>(null), HttpStatus.OK);
    }

    public void updateAccount() {

    }

    @ApiOperation(
            value = "查询用户列表",
            notes = "查询用户列表，且逻辑",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(paramType = "header", name = "X-ApiVersion", defaultValue = "1.0", dataType = "string")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcP<List<TmAccount>>> findAccounts(
            @ApiParam(value = "账号id") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "账号name") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "账号email") @RequestParam(value = "email", required = false) String email,
            @ApiParam(value = "账号mobile") @RequestParam(value = "mobile", required = false) String mobile,
            @ApiParam(value = "账号signinPlatform") @RequestParam(value = "signinPlatform", required = false)
                    TmSigninPlatform signinPlatform,
            @ApiParam(value = "当前页") @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @ApiParam(value = "每页条数") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<TmAccount> tmAccounts = Lists.newArrayList();
        // TODO
        return new ResponseEntity<>(new TcP<>(tmAccounts, currentPage, -1, pageSize), HttpStatus.OK);
    }

    @ApiOperation(
            value = "查询指定用户",
            notes = "通过id查询指定用户",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(paramType = "header", name = "X-ApiVersion", defaultValue = "1.0", dataType = "string")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<TmAccount>> findAccount(
            @ApiParam(value = "账号id") @PathVariable(value = "id") String accountId) {

        TmAccount account = tcAccountService.findAccount(accountId);

        if (Objects.isNull(account)) {
            return new ResponseEntity<>(new TcR<>(TcR.R.NOT_FOUND, TcR.R.NOT_FOUND_DESCRIPTION), HttpStatus.OK);
        }

        return new ResponseEntity<>(new TcR<>(account), HttpStatus.OK);
    }

}

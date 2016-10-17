package com.ysu.zyw.tc.api.openapi.account;

import com.ysu.zyw.tc.api.svc.account.TcAccountService;
import com.ysu.zyw.tc.model.api.account.TmAccount;
import com.ysu.zyw.tc.model.c.TcP;
import com.ysu.zyw.tc.model.c.TcR;
import com.ysu.zyw.tc.model.validator.TcValidator;
import com.ysu.zyw.tc.model.validator.mode.TcCreateMode;
import com.ysu.zyw.tc.model.validator.mode.TcUpdateMode;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/accounts")
@Api(value = "账号控制器")
@Slf4j
public class TcAccountApi {

    @Resource
    private TcAccountService tcAccountService;

    @ApiOperation(
            value = "创建用户",
            notes = "创建用户",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/create_account", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<String>> createAccount(
            @ApiParam(value = "账号信息") @Validated(value = TcCreateMode.class) @RequestBody TmAccount tmAccount,
            BindingResult bindingResult) {

        boolean nonePrincipal = TcValidator.isBlankOrNull(tmAccount.getAccount()) &&
                TcValidator.isBlankOrNull(tmAccount.getEmail()) &&
                TcValidator.isBlankOrNull(tmAccount.getMobile());
        if (nonePrincipal) {
            bindingResult.addError(new ObjectError("tmAccount", "账号/邮箱/手机必须至少设置一项"));
        }
        if (bindingResult.hasErrors()) {
            TcR<String> tcR = new TcR<>(TcR.R.BAD_REQUEST, TcR.R.BAD_REQUEST_DESCRIPTION);
            tcR.setExtra(new TcValidator.TcVerifyFailure(bindingResult));
            return ResponseEntity.ok(tcR);
        }

        String accountId = tcAccountService.createAccount(tmAccount);

        return ResponseEntity.ok(TcR.ok(accountId));
    }

    @ApiOperation(
            value = "删除指定用户",
            notes = "通过id删除指定用户",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/delete_account/{id}", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    @Transactional
    public ResponseEntity<TcR<Boolean>> deleteAccount(
            @ApiParam(value = "账号id") @PathVariable(value = "id") String accountId) {

        tcAccountService.deleteAccount(accountId);

        return ResponseEntity.ok(TcR.ok());
    }

    @ApiOperation(
            value = "更新指定用户",
            notes = "通过id更新指定用户",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "update_account/{id}", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Void>> updateAccount(
            @ApiParam(value = "账号id") @PathVariable(value = "id") String accountId,
            @ApiParam(value = "账号信息") @Validated(value = TcUpdateMode.class) @RequestBody TmAccount tmAccount,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            TcR<Void> tcR = new TcR<>(TcR.R.BAD_REQUEST, TcR.R.BAD_REQUEST_DESCRIPTION);
            tcR.setExtra(new TcValidator.TcVerifyFailure(bindingResult));
            return ResponseEntity.ok(tcR);
        }

        tcAccountService.updateAccount(tmAccount);

        return ResponseEntity.ok(TcR.ok());
    }

    @ApiOperation(
            value = "查询指定用户",
            notes = "通过id查询指定用户",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(
            value = "/find_account/{id}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<TmAccount>> findAccount(
            @ApiParam(value = "账号id", required = true) @PathVariable(value = "id") String accountId,
            @ApiParam(value = "包含辅助信息") @RequestParam(value = "includeAssistField", defaultValue = "false")
                    boolean includeAssistField,
            @ApiParam(value = "包含支付信息") @RequestParam(value = "includePaymentField", defaultValue = "false")
                    boolean includePaymentField) {

        TmAccount account = tcAccountService.findAccount(accountId, includeAssistField, includePaymentField);

        if (Objects.isNull(account)) {
            return new ResponseEntity<>(new TcR<>(TcR.R.NOT_FOUND, TcR.R.NOT_FOUND_DESCRIPTION), HttpStatus.OK);
        }

        return new ResponseEntity<>(TcR.ok(account), HttpStatus.OK);
    }

    @ApiOperation(
            value = "查询用户列表数量",
            notes = "查询用户列表数量，且逻辑",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/count_accounts", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Long>> countAccounts(
            @ApiParam(value = "账号ids") @RequestParam(value = "ids", required = false) List<String> ids,
            @ApiParam(value = "企业名 精确匹配") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "账号") @RequestParam(value = "account", required = false) String account,
            @ApiParam(value = "邮箱") @RequestParam(value = "email", required = false) String email,
            @ApiParam(value = "手机") @RequestParam(value = "mobile", required = false) String mobile) {

        long count = tcAccountService.countAccounts(ids, name, account, email, mobile);

        return ResponseEntity.ok(TcR.ok(count));
    }

    @ApiOperation(
            value = "查询用户列表",
            notes = "查询用户列表，且逻辑",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_accounts", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcP<List<TmAccount>>> findAccounts(
            @ApiParam(value = "账号ids") @RequestParam(value = "ids", required = false) List<String> ids,
            @ApiParam(value = "企业名 精确匹配") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "账号") @RequestParam(value = "account", required = false) String account,
            @ApiParam(value = "邮箱") @RequestParam(value = "email", required = false) String email,
            @ApiParam(value = "手机") @RequestParam(value = "mobile", required = false) String mobile,
            @ApiParam(value = "包含辅助信息") @RequestParam(value = "includeAssistField", defaultValue = "false")
                    boolean includeAssistField,
            @ApiParam(value = "包含支付信息") @RequestParam(value = "includePaymentField", defaultValue = "false")
                    boolean includePaymentField,
            @ApiParam(value = "当前页") @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @ApiParam(value = "每页条数") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        List<TmAccount> tmAccounts = tcAccountService.findAccounts(
                ids, name, account, email, mobile, includeAssistField, includePaymentField, currentPage, pageSize);

        return ResponseEntity.ok(TcP.ok(tmAccounts, currentPage, -1, pageSize));
    }

}

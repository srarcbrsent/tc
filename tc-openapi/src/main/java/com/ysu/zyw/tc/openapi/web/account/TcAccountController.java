package com.ysu.zyw.tc.openapi.web.account;

import com.ysu.zyw.tc.api.api.accounts.TcAccountApi;
import com.ysu.zyw.tc.base.validation.TcValidationUtils;
import com.ysu.zyw.tc.components.servlet.support.TcServletUtils;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.api.i.accounts.TuAccount;
import com.ysu.zyw.tc.model.menum.TmPlatform;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.openapi.svc.TcSessionService;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Api(value = "账号控制器")
@Controller
@RequestMapping(value = "/accounts")
public class TcAccountController {

    @Resource
    private TcAccountApi tcAccountApi;

    @Resource
    private TcSessionService tcSessionService;

    @Resource
    private Validator validator;

    /**
     * @code code == 0 创建成功, body = 可登陆账号的accountId;
     * @code code == 11 => 昵称重复;
     * @code code == 12 => 邮箱重复;
     * @code code == 13 => 手机重复;
     * @code code == 8888 => 信息有误, extra = 错误信息;
     * @code code == 9999 => 系统异常;
     */
    @ApiOperation(
            value = "创建账号",
            notes = "创建账号")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/create_account", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> createAccount(
            @RequestBody TiAccount tiAccount, HttpServletRequest request) {

        Set<ConstraintViolation<TiAccount>> violations = validator.validate(tiAccount);
        if (!violations.isEmpty()) {
            List<String> errs = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            return ResponseEntity.ok(TcR.br(errs));
        }

        tiAccount.setSigninIp(TcServletUtils.extractIp(request))
                .setSigninPlatform(TmPlatform.PC_PLATFORM)
                .setCreatedPerson(tcSessionService.getAccountId());
        TcR<String> tcR = tcAccountApi.createAccount(tiAccount);

        return ResponseEntity.ok(tcR);
    }

    /**
     * @code code == 0 删除成功;
     * @code code == 1 => 账号不存在;
     * @code code == 8888 => 信息有误, extra = 错误信息;
     * @code code == 9999 => 系统异常;
     */
    @ApiOperation(
            value = "删除账号",
            notes = "删除账号")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/delete_account/{id}", method = RequestMethod.GET)
    public ResponseEntity<TcR<Void>> deleteAccount(
            @PathVariable(value = "id") String deleteAccountId) {

        if (!TcValidationUtils.isId(deleteAccountId)) {
            return ResponseEntity.ok(TcR.br("不合法的主键信息"));
        }

        String accountId = tcSessionService.getAccountId();
        TcR<Void> tcR = tcAccountApi.deleteAccount(deleteAccountId, accountId);

        return ResponseEntity.ok(tcR);
    }

    /**
     * @code code == 0 更新成功;
     * @code code == 11 => 昵称重复;
     * @code code == 12 => 邮箱重复;
     * @code code == 13 => 手机重复;
     * @code code == 8888 => 信息有误, extra = 错误信息;
     * @code code == 9999 => 系统异常;
     */
    @ApiOperation(
            value = "更新账号",
            notes = "更新账号")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/update_account", method = RequestMethod.POST)
    public ResponseEntity<TcR<Void>> updateAccount(
            @RequestBody TuAccount tuAccount) {

        Set<ConstraintViolation<TuAccount>> violations = validator.validate(tuAccount);
        if (!violations.isEmpty()) {
            List<String> errs = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            return ResponseEntity.ok(TcR.br(errs));
        }

        tuAccount.setUpdatedPerson(tcSessionService.getAccountId());
        TcR<Void> tcR = tcAccountApi.updateAccount(tuAccount);

        return ResponseEntity.ok(tcR);
    }

}

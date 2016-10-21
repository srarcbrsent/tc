package com.ysu.zyw.tc.api.openapi.accounts;

import com.ysu.zyw.tc.api.svc.accounts.TcAccountService;
import com.ysu.zyw.tc.model.c.TcR;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.constraints.Future;
import java.util.Date;

@Controller
@RequestMapping(value = "/accounts")
@Api(value = "账号辅助控制器")
@Slf4j
public class TcAccountSpi {

    @Resource
    private TcAccountService tcAccountService;

    @ApiOperation(
            value = "激活手机号/邮箱",
            notes = "激活手机号/邮箱",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "参数错误，参见extra"),
            @ApiResponse(code = 422, message = "验证错误，参见extra")
    })
    @RequestMapping(value = "/active_contact_way/{id}", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Void>> activeContactWay(
            @ApiParam(value = "账号id") @PathVariable(value = "id") String accountId,
            @ApiParam(value = "激活手机") @RequestParam(value = "activeMobile", required = false) Boolean activeMobile,
            @ApiParam(value = "激活邮箱") @RequestParam(value = "activeEmail", required = false) Boolean activeEmail) {

        if (BooleanUtils.isTrue(activeMobile)) {
            tcAccountService.activeMobile(accountId);
        }

        if (BooleanUtils.isTrue(activeEmail)) {
            tcAccountService.activeEmail(accountId);
        }

        // TODO mq

        return ResponseEntity.ok(TcR.ok());
    }

    @ApiOperation(
            value = "激活支持微信支付/支付宝支付/货到付款",
            notes = "激活支持微信支付/支付宝支付/货到付款",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "参数错误，参见extra"),
            @ApiResponse(code = 422, message = "验证错误，参见extra")
    })
    @RequestMapping(value = "/active_payment_way/{id}", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Void>> activePaymentWay(
            @ApiParam(value = "账号id") @PathVariable(value = "id") String accountId,
            @ApiParam(value = "支持微信支付") @RequestParam(value = "supWeixin", required = false) Boolean supWeixin,
            @ApiParam(value = "支持支付宝支付") @RequestParam(value = "supZhifubao", required = false) Boolean supZhifubao,
            @ApiParam(value = "支持货到付款") @RequestParam(value = "supCod", required = false) Boolean supCod) {

        if (BooleanUtils.isTrue(supWeixin)) {
            tcAccountService.activeSupWeixin(accountId);
        }

        if (BooleanUtils.isTrue(supZhifubao)) {
            tcAccountService.activeSupZhifubao(accountId);
        }

        if (BooleanUtils.isTrue(supCod)) {
            tcAccountService.activeSupCod(accountId);
        }

        // TODO mq

        return ResponseEntity.ok(TcR.ok());
    }

    @ApiOperation(
            value = "锁定账号",
            notes = "锁定账号",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "参数错误，参见extra"),
            @ApiResponse(code = 422, message = "验证错误，参见extra")
    })
    @RequestMapping(value = "/lock_account/{id}", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Void>> lockAccount(
            @ApiParam(value = "账号id") @PathVariable(value = "id") String accountId,
            @ApiParam(value = "所定至") @Future @RequestParam(value = "lockReleaseTime") Date lockReleaseTime) {

        tcAccountService.lockAccount(accountId, lockReleaseTime);

        // TODO mq

        return ResponseEntity.ok(TcR.ok());
    }

}

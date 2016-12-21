package com.ysu.zyw.tc.platform.web.account;

import com.ysu.zyw.tc.api.api.accounts.TcAccountApi;
import com.ysu.zyw.tc.base.validator.TcRule;
import com.ysu.zyw.tc.base.validator.TcValidationRules;
import com.ysu.zyw.tc.base.validator.TcValidator;
import com.ysu.zyw.tc.components.servlet.support.TcServletUtils;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.menum.TmPlatform;
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
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Api(value = "账号控制器")
@Controller
@RequestMapping(value = "/accounts")
public class TcAccountController {

    @Resource
    private TcAccountApi tcAccountApi;

    @Resource
    private TcSessionService tcSessionService;

    /**
     * @code code == 0 创建成功：可登陆账号的accountId;
     * @code code == 11 => 昵称重复;
     * @code code == 12 => 邮箱重复;
     * @code code == 13 => 手机重复;
     * @code code == 20 => 信息有误;
     */
    @ApiOperation(
            value = "创建账号",
            notes = "创建账号")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/create_account", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> createAccount(
            @RequestBody TiAccount tiAccount, HttpServletRequest request) {

        TcValidationRules tcValidationRules = TcValidationRules.newShortCircuitInstance()
                .rule(() -> TcValidator.isNull(tiAccount.getId()), "唯一标志不正确")
                .rule(() -> TcValidator.isRegion(tiAccount.getRegion()), "区域格式不正确")
                .rule(() -> TcValidator.isNormalStr(tiAccount.getNickname(), 6, 16), "昵称不正确(6-16位)")
                .rule(() -> TcValidator.isEmail(tiAccount.getEmail()), "邮箱不正确")
                .rule(() -> TcValidator.nonNull(tiAccount.getEmailActivated()), "邮箱是否激活不正确")
                .rule(() -> TcValidator.isMobile(tiAccount.getMobile()), "电话不正确")
                .rule(() -> TcValidator.nonNull(tiAccount.getMobileActivated()), "电话是否激活不正确")
                .rule(((TcRule) () -> TcValidator.isPicUrl(tiAccount.getAvatar()))
                        .when(() -> Objects.nonNull(tiAccount.getAvatar())), "头像地址不正确")
                .rule(() -> TcValidator.isNormalStr(tiAccount.getPassword(), 32, 32), "密码格式不正确");
        if (!tcValidationRules.doValid()) {
            return ResponseEntity.ok(TcR.code(20, tcValidationRules.getError()));
        }

        tiAccount.setOperatorAccountId(tcSessionService.getAccountId())
                .setSigninIp(TcServletUtils.extractIp(request))
                .setSigninPlatform(TmPlatform.PC_PLATFORM);
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

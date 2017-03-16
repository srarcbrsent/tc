package com.ysu.zyw.tc.api.svc.accounts;

import com.ysu.zyw.tc.api.dao.mappers.TcBehaviorTraceMapper;
import com.ysu.zyw.tc.api.dao.po.TcBehaviorTrace;
import com.ysu.zyw.tc.base.constant.TcBaseConsts;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.menum.TmPlatform;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("Duplicates")
@Slf4j
@Service
public class TcBehaviorTraceService {

    private static final String LOG_SIGNUP_FORMATTER = "用户【{}】于【{}】在【{}】注册成功";

    private static final String LOG_SIGNIN_FORMATTER = "用户【{}】于【{}】在【{}】登陆成功";

    private static final String LOG_SIGNOUT_FORMATTER = "用户【{}】于【{}】在【{}】登出成功";

    @Resource
    private TcBehaviorTraceMapper tcBehaviorTraceMapper;

    @Resource
    private TcAccountService tcAccountService;

    @SneakyThrows
    @Transactional
    public void logSignup(String accountId, TmPlatform tmPlatform, Date doneTime) {
        checkNotNull(accountId);
        checkNotNull(tmPlatform);
        checkNotNull(doneTime);

        ToAccount toAccount = tcAccountService.findAccount(accountId, false);
        checkNotNull(toAccount);
        String description = TcFormatUtils.format(
                LOG_SIGNUP_FORMATTER,
                toAccount.getName(),
                TcDateUtils.format(doneTime),
                tmPlatform.getName());

        doLog(accountId, description);
    }

    @SneakyThrows
    @Transactional
    public void logSignin(String accountId, TmPlatform tmPlatform, Date doneTime) {
        checkNotNull(accountId);
        checkNotNull(tmPlatform);
        checkNotNull(doneTime);

        ToAccount toAccount = tcAccountService.findAccount(accountId, false);
        checkNotNull(toAccount);
        String description = TcFormatUtils.format(
                LOG_SIGNIN_FORMATTER,
                toAccount.getName(),
                TcDateUtils.format(doneTime),
                tmPlatform.getName());

        doLog(accountId, description);
    }

    @SneakyThrows
    @Transactional
    public void logSignout(String accountId, TmPlatform tmPlatform, Date doneTime) {
        checkNotNull(accountId);
        checkNotNull(tmPlatform);
        checkNotNull(doneTime);

        ToAccount toAccount = tcAccountService.findAccount(accountId, false);
        checkNotNull(toAccount);
        String description = TcFormatUtils.format(
                LOG_SIGNOUT_FORMATTER,
                toAccount.getName(),
                TcDateUtils.format(doneTime),
                tmPlatform.getName());

        doLog(accountId, description);
    }

    private void doLog(String accountId, String description) {
        Date now = new Date();
        TcBehaviorTrace tcBehaviorTrace = new TcBehaviorTrace();
        tcBehaviorTrace
                .setId(TcIdGen.upperCaseUuid())
                .setAccountId(accountId)
                .setDescription(description)
                .setUpdatedPerson(TcBaseConsts.STR_32_0)
                .setUpdatedTimestamp(now)
                .setCreatedPerson(TcBaseConsts.STR_32_0)
                .setCreatedTimestamp(now);
        int iCount = tcBehaviorTraceMapper.insert(tcBehaviorTrace);

        checkArgument(iCount == 1);
    }

}

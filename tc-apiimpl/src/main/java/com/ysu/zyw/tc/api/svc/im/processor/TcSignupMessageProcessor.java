package com.ysu.zyw.tc.api.svc.im.processor;

import com.ysu.zyw.tc.api.dao.po.TcMessageWithBLOBs;
import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.model.menum.TmPlatform;
import com.ysu.zyw.tc.model.msg.TcSignupPitaya;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class TcSignupMessageProcessor extends TcAbstractMessageProcessor {

    private static final String C_SIGNUP_ACCOUNT_ID = "account_id";

    private static final String C_SIGNUP_IP = "signup_ip";

    private static final String C_SIGNUP_PLATFORM = "signup_platform";

    @Override
    public void preProcessor(@Nonnull Map<String, Object> infos) {
        checkNotNull(infos);
        String accountId = checkNotNull(infos.get(TcSignupPitaya.MSG_KEY_SIGNUP_ACCOUNT_ID)).toString();
        String signupIp = checkNotNull(infos.get(TcSignupPitaya.MSG_KEY_SIGNUP_IP)).toString();
        String signupPlatform = checkNotNull(infos.get(TcSignupPitaya.MSG_KEY_SIGNUP_PLATFORM)).toString();

        // TODO: 2016/11/28
        infos.put(C_SIGNUP_ACCOUNT_ID, accountId);
        infos.put(C_SIGNUP_IP, signupIp);
        infos.put(C_SIGNUP_PLATFORM, signupPlatform);
        infos.put(C_RECEIVER_ACCOUNT_ID, TcConstant.Sys.STR_32x0);
        infos.put(C_RECEIVER_REGION_ID, null);
        infos.put(C_BIZ_KEY, TcConstant.Sys.STR_32x0);
    }

    @Override
    public void processWebPlatform(@Nonnull Map<String, Object> infos) {
        Date now = new Date();
        TcMessageWithBLOBs tcMessage = new TcMessageWithBLOBs();
        tcMessage
                .setContent("Hello World!")
                .setExtra(null)
                .setId(TcIdWorker.upperCaseUuid())
                .setReceiverAccountId((String) infos.get(C_RECEIVER_ACCOUNT_ID))
                .setReceiverRegionId((String) infos.get(C_RECEIVER_REGION_ID))
                .setType(this.getTmMessageType())
                .setPlatform(TmPlatform.PC_PLATFORM)
                .setRead(false)
                .setBizKey((String) infos.get(C_BIZ_KEY))
                .setUpdatedPerson(TcConstant.Sys.STR_32x0)
                .setUpdatedTimestamp(now)
                .setCreatedPerson(TcConstant.Sys.STR_32x0)
                .setCreatedTimestamp(now);
        int iCount = tcMessageMapper.insert(tcMessage);
        checkArgument(iCount == 1);
    }

    @Override
    public void processMobilePlatform(@Nonnull Map<String, Object> infos) {
        throw new TcException("not impl yet");
    }

}

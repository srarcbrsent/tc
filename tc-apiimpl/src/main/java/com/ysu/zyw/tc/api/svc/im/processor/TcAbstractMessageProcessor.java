package com.ysu.zyw.tc.api.svc.im.processor;

import com.ysu.zyw.tc.api.dao.mappers.TcMessageMapper;
import com.ysu.zyw.tc.model.menum.TmMessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public abstract class TcAbstractMessageProcessor {

    @Resource
    protected TcMessageMapper tcMessageMapper;

    @Getter
    @Setter
    protected boolean enableWebPlatform;

    @Getter
    @Setter
    protected boolean enableMobilePlatform;

    @Getter
    @Setter
    protected TmMessageType tmMessageType;

    protected static final String C_RECEIVER_ACCOUNT_ID = "receiver_account_id";

    protected static final String C_RECEIVER_REGION_ID = "receiver_region_id";

    protected static final String C_BIZ_KEY = "biz_key";

    public void process(@Nonnull Map<String, Object> infos) {
        checkNotNull(infos);
        preProcessor(infos);
        if (enableWebPlatform) {
            try {
                processWebPlatform(infos);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        if (enableMobilePlatform) {
            try {
                processMobilePlatform(infos);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        postProcessor(infos);
    }

    public void preProcessor(@Nonnull Map<String, Object> infos) {
    }

    public void postProcessor(@Nonnull Map<String, Object> infos) {
    }

    public abstract void processWebPlatform(@Nonnull Map<String, Object> infos);

    public abstract void processMobilePlatform(@Nonnull Map<String, Object> infos);

}

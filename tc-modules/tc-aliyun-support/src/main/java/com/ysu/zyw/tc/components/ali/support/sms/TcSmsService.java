package com.ysu.zyw.tc.components.ali.support.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.ysu.zyw.tc.base.utils.TcSerializationUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TcSmsService implements InitializingBean {

    @Getter
    @Setter
    private String regionId;

    @Getter
    @Setter
    private String accessKey;

    @Getter
    @Setter
    private String accessSecret;

    private IAcsClient client;

    @SneakyThrows
    public void send(@Nonnull String signName,
                     @Nonnull String templateCode,
                     @Nullable Map<String, String> param,
                     @Nonnull String target) {
        SingleSendSmsRequest request = new SingleSendSmsRequest();
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        if (Objects.nonNull(param)) {
            request.setParamString(TcSerializationUtils.writeJson(param));
        }
        request.setRecNum(target);
        if (log.isInfoEnabled()) {
            log.info("start send sms to [{}] [{}] [{}]", target, templateCode, param);
        }
        SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
        if (log.isInfoEnabled()) {
            log.info("finish send sms to [{}] [{}]", target, httpResponse);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
        client = new DefaultAcsClient(profile);
    }

}

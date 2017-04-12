package com.ysu.zyw.tc.components.aliyun.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class TcAliyunMnsService implements InitializingBean {

    @Getter
    @Setter
    private String regionId;

    @Getter
    @Setter
    private String accessKey;

    @Getter
    @Setter
    private String accessSecret;

    @Override
    public void afterPropertiesSet() throws Exception {
    }

}

package com.ysu.zyw.tc.components.aliyun.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class TcAliyunMailService implements InitializingBean {

    @Getter
    @Setter
    private String regionId;

    @Getter
    @Setter
    private String accessKey;

    @Getter
    @Setter
    private String accessSecret;

    @Getter
    @Setter
    private String accountName;

    @Getter
    @Setter
    private String fromAlias;

    @Getter
    @Setter
    private String tagName;



    @Override
    public void afterPropertiesSet() throws Exception {
    }

}

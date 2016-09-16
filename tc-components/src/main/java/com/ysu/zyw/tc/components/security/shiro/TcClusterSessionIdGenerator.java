package com.ysu.zyw.tc.components.security.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
public class TcClusterSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        String id = UUID.randomUUID().toString() +
                DigestUtils.md5DigestAsHex(session.getHost().getBytes()) +
                DigestUtils.md5DigestAsHex(String.valueOf(session.getStartTimestamp().getTime()).getBytes()) +
                RandomStringUtils.random(16);
        if (log.isDebugEnabled()) {
            log.debug("tc cluster session id generator generate session id [{}]", id);
        }
        return id;
    }

}

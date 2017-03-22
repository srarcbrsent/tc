package com.ysu.zyw.tc.openapi.fk.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
public class TcClusterSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        String id = UUID.randomUUID().toString() + RandomStringUtils.randomNumeric(16);
        if (log.isDebugEnabled()) {
            log.debug("tc cluster session id generator generate session id [{}]", id);
        }
        return id;
    }

}

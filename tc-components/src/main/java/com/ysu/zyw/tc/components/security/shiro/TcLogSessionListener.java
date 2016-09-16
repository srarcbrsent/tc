package com.ysu.zyw.tc.components.security.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

@Slf4j
public class TcLogSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {
        if (log.isDebugEnabled()) {
            log.debug("session with id [{}] started ...", session.getId());
        }
    }

    @Override
    public void onStop(Session session) {
        if (log.isDebugEnabled()) {
            log.debug("session with id [{}] stopped ...", session.getId());
        }
    }

    @Override
    public void onExpiration(Session session) {
        if (log.isDebugEnabled()) {
            log.debug("session with id [{}] expiration ...", session.getId());
        }
    }
}

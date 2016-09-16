package com.ysu.zyw.tc.components.security.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;

public class TcSessionDao extends CachingSessionDAO {

    @Override
    public String getActiveSessionsCacheName() {
        return
                "TODO";
    }

    @Override
    protected void doUpdate(Session session) {
        // extends caching session dao, it will use cache manager to crud session
        // so we do not save session in another backend storage, do nothing
    }

    @Override
    protected void doDelete(Session session) {
        // extends caching session dao, it will use cache manager to crud session
        // so we do not save session in another backend storage, do nothing
    }

    @Override
    protected Serializable doCreate(Session session) {
        // extends caching session dao, it will use cache manager to crud session
        // so we do not save session in another backend storage, do nothing.
        // when we impl an caching service dao, we take care of generate service
        // id mission.
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        // extends caching session dao, it will use cache manager to crud session
        // so we do not save session in another backend storage. do nothing
        return null;
    }
}

package com.ysu.zyw.tc.platform.svc;

import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.platform.constant.TcSessionKey;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class TcSessionService {

    public void initSessionAfterSignup() {
        ToAccount toAccount = (ToAccount) SecurityUtils.getSubject().getPrincipal();
        org.apache.shiro.session.Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(TcSessionKey.S_ACCOUNT_ID, toAccount.getId());
    }

    public String getAccountId() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new TcException("not signup session do not have a account id");
        }
        return SecurityUtils.getSubject().getSession().getAttribute(TcSessionKey.S_ACCOUNT_ID).toString();
    }

}

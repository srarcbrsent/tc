package com.ysu.zyw.tc.platform.svc;

import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.sys.ex.TcException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

@Service
public class TcSessionService {

    public static final String SESSION_ACCOUNT_ID = "session_account_id";

    public void initSessionAfterSignup() {
        ToAccount toAccount = (ToAccount) SecurityUtils.getSubject().getPrincipal();
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(SESSION_ACCOUNT_ID, toAccount.getId());
    }

    public String getAccountId() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new TcException("not signup session do not have a account id");
        }
        return SecurityUtils.getSubject().getSession().getAttribute(SESSION_ACCOUNT_ID).toString();
    }

}

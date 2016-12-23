package com.ysu.zyw.tc.platform.svc;

import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class TcSessionService {

    public void initSessionAfterSignup() {
        ToAccount toAccount = (ToAccount) SecurityUtils.getSubject().getPrincipal();
        org.apache.shiro.session.Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(TcConstant.SessionKey.S_ACCOUNT_ID, toAccount.getId());
    }

    public String getAccountId() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new TcException("not signup session do not have a account id");
        }
        return SecurityUtils.getSubject().getSession().getAttribute(TcConstant.SessionKey.S_ACCOUNT_ID).toString();
    }

}

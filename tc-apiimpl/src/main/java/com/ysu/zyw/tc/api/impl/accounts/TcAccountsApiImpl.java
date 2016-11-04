package com.ysu.zyw.tc.api.impl.accounts;

import com.ysu.zyw.tc.api.TcAccountsApi;
import com.ysu.zyw.tc.model.accounts.TmAccount;
import com.ysu.zyw.tc.mw.TcR;

public class TcAccountsApiImpl implements TcAccountsApi {

    @Override
    public TcR<TmAccount, Void> findAccount() {
        return TcR.ok(new TmAccount());
    }

}

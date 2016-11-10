package com.ysu.zyw.tc.api.impl.accounts;

import com.ysu.zyw.tc.api.TcAccountApi;
import com.ysu.zyw.tc.model.accounts.TmAccount;
import com.ysu.zyw.tc.mw.TcR;

import java.util.Date;
import java.util.List;

public class TcAccountApiImpl implements TcAccountApi {

    @Override
    public TcR<Void, Void> createAccount(TmAccount tmAccount) {
        return null;
    }

    @Override
    public TcR<Void, Void> deleteAccount(String accountId) {
        return null;
    }

    @Override
    public TcR<Void, Void> updateAccount(TmAccount tmAccount) {
        return null;
    }

    @Override
    public TcR<TmAccount, Void> findAccount(String accountId) {
        return TcR.ok(new TmAccount().setLockReleaseTime(new Date()));
    }

    @Override
    public TcR<TmAccount, Void> findAccounts(List<String> accountIds) {
        return null;
    }

}

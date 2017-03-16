package com.ysu.zyw.tc.api.impl.accounts;

import com.ysu.zyw.tc.api.api.accounts.TcAccountApi;
import com.ysu.zyw.tc.api.svc.accounts.TcAccountService;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.api.i.accounts.TiFindAccountsTerms;
import com.ysu.zyw.tc.model.api.i.accounts.TuAccount;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;
import lombok.SneakyThrows;

import javax.annotation.Resource;
import java.util.List;

public class TcAccountApiImpl implements TcAccountApi {

    @Resource
    private TcAccountService tcAccountService;

    @SneakyThrows
    @Override
    public TcR<String> createAccount(TiAccount tiAccount) {

        String accountId = tcAccountService.createAccount(tiAccount);

        return TcR.ok(accountId);
    }

    @SneakyThrows
    @Override
    public TcR<Void> deleteAccount(String accountId, String delector) {

        tcAccountService.deleteAccount(accountId, delector);

        return TcR.ok();
    }

    @SneakyThrows
    @Override
    public TcR<Void> updateAccount(TuAccount tuAccount) {

        tcAccountService.updateAccount(tuAccount);

        return TcR.ok();
    }

    @SneakyThrows
    @Override
    public TcR<ToAccount> findAccount(String accountId) {

        ToAccount toAccount = tcAccountService.findAccount(accountId, false);

        return TcR.ok(toAccount);
    }

    @Override
    public TcR<Long> countAccounts(TiFindAccountsTerms tiFindAccountsTerms) {

        long count = tcAccountService.countAccounts(tiFindAccountsTerms);

        return TcR.ok(count);
    }

    @Override
    public TcP<List<ToAccount>> findAccounts(TiFindAccountsTerms tiFindAccountsTerms,
                                             Integer currentPage,
                                             Integer pageSize) {

        List<ToAccount> toAccounts = tcAccountService.findAccounts(tiFindAccountsTerms, currentPage, pageSize);

        int totalPage = (int) tcAccountService.countAccounts(tiFindAccountsTerms);

        return TcP.ok(toAccounts, currentPage, pageSize, totalPage);
    }

}

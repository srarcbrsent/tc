package com.ysu.zyw.tc.api.impl.accounts;

import com.ysu.zyw.tc.api.api.TcAccountApi;
import com.ysu.zyw.tc.api.svc.accounts.TcAccountService;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.api.i.accounts.TiFindAccountsTerms;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

public class TcAccountApiImpl implements TcAccountApi {

    @Resource
    private TcAccountService tcAccountService;

    @Override
    public TcR<String> createAccount(TiAccount tiAccount) {

        // throws TcUnProcessableEntityException
        String accountId = tcAccountService.createAccount(tiAccount);

        return TcR.ok(accountId);
    }

    @Override
    public TcR<Void> deleteAccount(String accountId, String delector) {

        // throws TcUnProcessableEntityException
        tcAccountService.deleteAccount(accountId, delector);

        return TcR.ok();
    }

    @Override
    public TcR<Void> updateAccount(TiAccount tiAccount) {

        // throws TcUnProcessableEntityException
        tcAccountService.updateAccount(tiAccount);

        return TcR.ok();
    }

    @Override
    public TcR<ToAccount> findAccount(String accountId) {

        // throws TcResourceNotFoundException
        ToAccount toAccount = tcAccountService.findAccount(accountId, false);

        if (Objects.nonNull(toAccount)) {
            return TcR.ok(toAccount);
        } else {
            return new TcR<>(TcR.R.NOT_FOUND);
        }
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

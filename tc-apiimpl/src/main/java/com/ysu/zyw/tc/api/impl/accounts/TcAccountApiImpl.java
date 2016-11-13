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
    public TcR<String, Object> createAccount(TiAccount tiAccount) {

        // throw TcVerifyFailureException
        String accountId = tcAccountService.createAccount(tiAccount);

        return TcR.ok(accountId);
    }

    @Override
    public TcR<Void, Object> deleteAccount(String accountId, String delector) {

        // throw TcVerifyFailureException
        tcAccountService.deleteAccount(accountId, delector);

        return TcR.ok();
    }

    @Override
    public TcR<Void, Object> updateAccount(TiAccount tiAccount) {

        // throw TcVerifyFailureException
        tcAccountService.updateAccount(tiAccount);

        return TcR.ok();
    }

    @Override
    public TcR<ToAccount, Object> findAccount(String accountId) {

        ToAccount toAccount = tcAccountService.findAccount(accountId, false);

        if (Objects.nonNull(toAccount)) {
            return TcR.ok(toAccount);
        } else {
            return new TcR<>(TcR.R.NOT_FOUND, TcR.R.NOT_FOUND_DESCRIPTION);
        }
    }

    @Override
    public TcR<Long, Object> countAccounts(TiFindAccountsTerms tiFindAccountsTerms) {

        long count = tcAccountService.countAccounts(tiFindAccountsTerms);

        return TcR.ok(count);
    }

    @Override
    public TcP<List<ToAccount>, Object> findAccounts(TiFindAccountsTerms tiFindAccountsTerms,
                                                     Integer currentPage,
                                                     Integer pageSize) {

        List<ToAccount> toAccounts = tcAccountService.findAccounts(tiFindAccountsTerms, currentPage, pageSize);

        int totalPage = (int) tcAccountService.countAccounts(tiFindAccountsTerms);

        return TcP.ok(toAccounts, currentPage, pageSize, totalPage);
    }

}

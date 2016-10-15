package com.ysu.zyw.tc.api.svc.account;

import com.ysu.zyw.tc.api.dao.mappers.TcAccountMapper;
import com.ysu.zyw.tc.api.dao.po.TcAccount;
import com.ysu.zyw.tc.api.dao.po.TcAccountExample;
import com.ysu.zyw.tc.model.api.account.TmAccount;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class TcAccountService {

    @Resource
    private TcAccountMapper tcAccountMapper;

    public void createAccount() {

    }

    public void deleteAccount() {

    }

    public void updateAccount() {

    }

    public void findAccounts() {

    }

    public TmAccount findAccount(String accountId) {
        checkNotNull(accountId);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andDelectedEqualTo(false);
        List<TcAccount> tcAccounts = tcAccountMapper.selectByExample(tcAccountExample);
        if (CollectionUtils.isEmpty(tcAccounts)) {
            return null;
        }
        checkArgument(tcAccounts.size() == 1);
        TmAccount tmAccount = new TmAccount();
        BeanUtils.copyProperties(tcAccounts.get(0), tmAccount);
        tmAccount.setPassword(null);
        return tmAccount;
    }

    public boolean existAccount(String accountId) {
        checkNotNull(accountId);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

}

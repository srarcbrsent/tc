package com.ysu.zyw.tc.api.svc.account;

import com.google.common.base.Throwables;
import com.ysu.zyw.tc.api.dao.mappers.TcAccountAssistMapper;
import com.ysu.zyw.tc.api.dao.mappers.TcAccountMapper;
import com.ysu.zyw.tc.api.dao.mappers.TcAccountPaymentMapper;
import com.ysu.zyw.tc.api.dao.po.TcAccount;
import com.ysu.zyw.tc.api.dao.po.TcAccountAssist;
import com.ysu.zyw.tc.api.dao.po.TcAccountExample;
import com.ysu.zyw.tc.api.dao.po.TcAccountPayment;
import com.ysu.zyw.tc.api.fk.ex.TcResourceNotFoundException;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.base.utils.TcPaginationUtils;
import com.ysu.zyw.tc.components.utils.validator.mode.TcCreateMode;
import com.ysu.zyw.tc.components.utils.validator.mode.TcUpdateMode;
import com.ysu.zyw.tc.model.api.account.TmAccount;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class TcAccountService {

    @Resource
    private TcAccountMapper tcAccountMapper;

    @Resource
    private TcAccountAssistMapper tcAccountAssistMapper;

    @Resource
    private TcAccountPaymentMapper tcAccountPaymentMapper;

    public @NotNull String createAccount(@NotNull @Validated(value = TcCreateMode.class) TmAccount tmAccount) {
        String accountId = TcIdWorker.upperCaseUuid();


        return accountId;
    }

    public void deleteAccount(@NotNull String accountId) {
        checkNotNull(accountId);
        if (!existAccount(accountId)) {
            throw new TcResourceNotFoundException("account [" + accountId + "] can not be deleted because it does" +
                    "not exists");
        }

        TcAccount tcAccount = new TcAccount();
        tcAccount.setId(accountId);
        tcAccount.setDelected(true);

        int count = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);
        checkArgument(count == 1);
    }

    public void updateAccount(@NotNull @Validated(value = TcUpdateMode.class) TmAccount tmAccount) {

    }

    public TmAccount findAccount(@NotNull String accountId,
                                 boolean includeAssistField,
                                 boolean includePaymentField) {
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
        try {

            BeanUtils.copyProperties(tmAccount, tcAccounts.get(0));
        } catch (IllegalAccessException | InvocationTargetException e) {
            Throwables.propagate(e);
        }
        tmAccount.setPassword(null);

        if (includeAssistField) {
            tmAccount = this.replenishAccountAssistField(tmAccount);
        }

        if (includePaymentField) {
            tmAccount = this.replenishAccountPaymentField(tmAccount);
        }

        return tmAccount;
    }

    public long countAccounts(List<String> ids,
                              String name,
                              String account,
                              String email,
                              String mobile) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        TcAccountExample.Criteria criteria = tcAccountExample.createCriteria();
        criteria.andDelectedEqualTo(false);
        if (CollectionUtils.isNotEmpty(ids)) {
            criteria.andIdIn(ids);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameEqualTo(name);
        }
        if (StringUtils.isNotBlank(account)) {
            criteria.andAccountEqualTo(account);
        }
        if (StringUtils.isNotBlank(email)) {
            criteria.andEmailEqualTo(email);
        }
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andEmailEqualTo(email);
        }
        return tcAccountMapper.countByExample(tcAccountExample);
    }

    public @NotNull List<TmAccount> findAccounts(List<String> ids,
                                        String name,
                                        String account,
                                        String email,
                                        String mobile,
                                        boolean includeAssistField,
                                        boolean includePaymentField,
                                        int currentPage,
                                        int pageSize) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        TcPaginationUtils.Pagination pagination = TcPaginationUtils.paging(currentPage, pageSize);
        tcAccountExample.setStartLine(pagination.getStartLine());
        tcAccountExample.setPageSize(pagination.getPageSize());
        TcAccountExample.Criteria criteria = tcAccountExample.createCriteria();
        criteria.andDelectedEqualTo(false);
        if (CollectionUtils.isNotEmpty(ids)) {
            criteria.andIdIn(ids);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameEqualTo(name);
        }
        if (StringUtils.isNotBlank(account)) {
            criteria.andAccountEqualTo(account);
        }
        if (StringUtils.isNotBlank(email)) {
            criteria.andEmailEqualTo(email);
        }
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andEmailEqualTo(email);
        }
        List<TcAccount> tcAccountList = tcAccountMapper.selectByExample(tcAccountExample);

        Stream<TmAccount> tmAccountsStream = tcAccountList.stream().map(tcAccount -> {
            TmAccount tmAccount = new TmAccount();
            try {
                BeanUtils.copyProperties(tmAccount, tcAccount);
            } catch (IllegalAccessException | InvocationTargetException e) {
                Throwables.propagate(e);
            }
            tmAccount.setPassword(null);
            return tmAccount;
        });

        if (includeAssistField) {
            tmAccountsStream.map(this::replenishAccountAssistField);
        }

        if (includePaymentField) {
            tmAccountsStream.map(this::replenishAccountPaymentField);
        }

        return tmAccountsStream.collect(Collectors.toList());
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

    private TmAccount replenishAccountAssistField(TmAccount tmAccount) {
        checkNotNull(tmAccount);
        String accountId = tmAccount.getId();

        TcAccountAssist tcAccountAssist = tcAccountAssistMapper.selectByPrimaryKey(accountId);
        checkNotNull(tcAccountAssist);

        try {
            BeanUtils.copyProperties(tmAccount, tcAccountAssist);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Throwables.propagate(e);
        }

        return tmAccount;
    }

    private TmAccount replenishAccountPaymentField(TmAccount tmAccount) {
        checkNotNull(tmAccount);
        String accountId = tmAccount.getId();

        TcAccountPayment tcAccountPayment = tcAccountPaymentMapper.selectByPrimaryKey(accountId);
        checkNotNull(tcAccountPayment);

        try {
            BeanUtils.copyProperties(tmAccount, tcAccountPayment);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Throwables.propagate(e);
        }

        return tmAccount;
    }

}

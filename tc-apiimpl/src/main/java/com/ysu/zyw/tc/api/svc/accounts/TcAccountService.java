package com.ysu.zyw.tc.api.svc.accounts;

import com.ysu.zyw.tc.api.dao.mappers.TcAccountAssistMapper;
import com.ysu.zyw.tc.api.dao.mappers.TcAccountMapper;
import com.ysu.zyw.tc.api.dao.penum.TcPlatform;
import com.ysu.zyw.tc.api.dao.po.TcAccount;
import com.ysu.zyw.tc.api.dao.po.TcAccountAssist;
import com.ysu.zyw.tc.api.dao.po.TcAccountExample;
import com.ysu.zyw.tc.api.fk.ex.TcVerifyFailureException;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.base.utils.TcPaginationUtils;
import com.ysu.zyw.tc.model.api.accounts.TmAccount;
import com.ysu.zyw.tc.model.validator.TcValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @Transactional(readOnly = true)
    public String canSignup(@Nonnull String username,
                            @Nonnull String password,
                            @Nonnull Boolean canAccountLogin,
                            @Nonnull Boolean canEmailLogin,
                            @Nonnull Boolean canMobileLogin) {
        checkArgument(canAccountLogin || canEmailLogin || canMobileLogin);

        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.setStartLine(0);
        tcAccountExample.setPageSize(1);
        TcAccountExample.Criteria criteria = tcAccountExample.createCriteria();
        if (canAccountLogin) {
            criteria.andAccountEqualTo(username);
        }
        if (canEmailLogin) {
            tcAccountExample.or().andEmailEqualTo(username);
        }
        if (canMobileLogin) {
            tcAccountExample.or().andMobileEqualTo(username);
        }
        List<TcAccount> tcAccounts = tcAccountMapper.selectByExample(tcAccountExample);

        if (CollectionUtils.isEmpty(tcAccounts)) {
            // 账号不存在
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请重试！"));
        }

        TcAccount tcAccount = tcAccounts.get(0);
        if (!Objects.equals(tcAccount.getPassword(), password)) {
            // 账号存在 密码不对
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("密码错误，请重试！"));
        }

        if (Objects.nonNull(tcAccount.getLockReleaseTime()) &&
                // 当前时间在解锁之前之前
                Calendar.getInstance().getTime().before(tcAccount.getLockReleaseTime())) {
            // 被锁定
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号被锁定，请联系管理员！"));
        }

        return tcAccount.getId();
    }

    @Transactional
    public String createAccount(@Nonnull TmAccount tmAccount, @Nonnull String creator) {
        // check
        if (existName(tmAccount.getName())) {
            throw new TcVerifyFailureException(
                    new TcValidator.TcVerifyFailure("名称【" + tmAccount.getName() + "】重复！"));
        }

        if (StringUtils.isNotBlank(tmAccount.getAccount()) && existAccount(tmAccount.getAccount())) {
            throw new TcVerifyFailureException(
                    new TcValidator.TcVerifyFailure("账号【" + tmAccount.getAccount() + "】重复！"));
        }

        if (StringUtils.isNotBlank(tmAccount.getEmail()) && existEmail(tmAccount.getEmail())) {
            throw new TcVerifyFailureException(
                    new TcValidator.TcVerifyFailure("邮箱【" + tmAccount.getEmail() + "】重复！"));
        }

        if (StringUtils.isNotBlank(tmAccount.getMobile()) && existAccount(tmAccount.getMobile())) {
            throw new TcVerifyFailureException(
                    new TcValidator.TcVerifyFailure("手机【" + tmAccount.getMobile() + "】重复！"));
        }

        // id
        String id = TcIdWorker.upperCaseUuid();
        Date now = Calendar.getInstance().getTime();

        // account
        TcAccount tcAccount = new TcAccount();

        BeanUtils.copyProperties(tmAccount, tcAccount);
        tcAccount
                .setId(id)
                .setDelected(false)
                .setLockReleaseTime(null)
                .setUpdatedPerson(creator)
                .setUpdatedTimestamp(now)
                .setCreatedPerson(creator)
                .setCreatedTimestamp(now);
        // if no mobile set, mobile activated auto set to false
        if (StringUtils.isBlank(tcAccount.getMobile())) {
            tcAccount.setMobileActivated(false);
        }
        // if no email set, email activated auto set to false
        if (StringUtils.isBlank(tcAccount.getEmail())) {
            tcAccount.setEmailActivated(false);
        }

        // account assist
        TcAccountAssist tcAccountAssist = new TcAccountAssist();

        BeanUtils.copyProperties(tmAccount, tcAccountAssist);
        tcAccountAssist
                .setId(id)
                .setSigninPlatform(TcPlatform.convert(tmAccount.getSignupPlatform()))
                .setSigninTimestamp(now)
                .setLastSignupPlatform(TcPlatform.convert(tmAccount.getSignupPlatform()))
                .setLastSignupTimestamp(now)
                .setUpdatedPerson(creator)
                .setUpdatedTimestamp(now)
                .setCreatedPerson(creator)
                .setCreatedTimestamp(now);

        // insert
        int cAccount = tcAccountMapper.insert(tcAccount);
        int cAccountAssist = tcAccountAssistMapper.insert(tcAccountAssist);

        checkArgument(cAccount == 1 && cAccountAssist == 1);

        return id;
    }

    @Transactional
    public void deleteAccount(@Nonnull String accountId, @Nonnull String delector) {
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在！"));
        }

        TcAccount tcAccount = new TcAccount();
        tcAccount
                .setId(accountId)
                .setDelected(true)
                .setUpdatedPerson(delector)
                .setUpdatedTimestamp(new Date());

        int count = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(count == 1);
    }

    @Transactional
    public void updateAccount(@Nonnull TmAccount tmAccount, @Nonnull String updator) {
        // check
        if (!existId(tmAccount.getId())) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        TcAccount originalTcAccount = this.findOriginalTcAccount(tmAccount.getId());

        Date now = Calendar.getInstance().getTime();

        // account
        TcAccount tcAccount = new TcAccount();

        BeanUtils.copyProperties(tmAccount, tcAccount);
        tcAccount
                // can not be updated, use spi instead.
                .setMobileActivated(null)
                // can not be updated, use spi instead.
                .setEmailActivated(null)
                // can not be updated, use spi instead.
                .setLockReleaseTime(null)
                // can not be updated, use spi instead.
                .setPassword(null)
                // can not be updated, use spi instead.
                .setDelected(null)
                .setUpdatedPerson(updator)
                .setUpdatedTimestamp(now);
        // if mobile changed, auto set mobile activated to false.
        if (StringUtils.isNotBlank(tcAccount.getMobile()) &&
                Objects.equals(tcAccount.getMobile(), originalTcAccount.getMobile())) {
            tcAccount.setMobileActivated(false);
        }
        // if email changed, auto set email activated to false.
        if (StringUtils.isBlank(tcAccount.getEmail()) &&
                Objects.equals(tcAccount.getEmail(), originalTcAccount.getEmail())) {
            tcAccount.setEmailActivated(false);
        }

        // update
        int cAccount = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(cAccount == 1);
    }

    @Transactional
    public void updatePassword(String accountId, String oPassword, String nPassword, @Nonnull String operator) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.setStartLine(0);
        tcAccountExample.setPageSize(1);
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andDelectedEqualTo(false);
        List<TcAccount> tcAccounts = tcAccountMapper.selectByExample(tcAccountExample);
        if (CollectionUtils.isEmpty(tcAccounts)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }
        TcAccount originalTcAccountWithPassword = tcAccounts.get(0);

        if (!Objects.equals(originalTcAccountWithPassword.getPassword(), oPassword)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("原密码错误，请更换重试！"));
        }

        TcAccount tcAccount = new TcAccount();
        tcAccount
                .setId(accountId)
                .setPassword(nPassword)
                .setUpdatedPerson(operator)
                .setUpdatedTimestamp(new Date());

        int cAccount = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(cAccount == 1);
    }

    @Transactional(readOnly = true)
    public TmAccount findAccount(@Nonnull String accountId) {
        checkNotNull(accountId);
        return convert2TmAccount(this.findOriginalTcAccount(accountId));
    }

    private TcAccount findOriginalTcAccount(@Nonnull String accountId) {
        checkNotNull(accountId);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.setStartLine(0);
        tcAccountExample.setPageSize(1);
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andDelectedEqualTo(false);
        List<TcAccount> tcAccounts = tcAccountMapper.selectByExample(tcAccountExample);
        if (CollectionUtils.isEmpty(tcAccounts)) {
            return null;
        }

        TcAccount tcAccount = tcAccounts.get(0);
        tcAccount.setPassword(null);

        return tcAccount;
    }

    // TODO builder
    @Transactional(readOnly = true)
    public long countAccounts(List<String> ids,
                              String name,
                              String account,
                              String mobile,
                              String email,
                              Boolean mobileActivated,
                              Boolean emailActivated,
                              Boolean locked

                              ) {
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

    @Transactional(readOnly = true)
    public List<TmAccount> findAccounts(List<String> ids,
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

            BeanUtils.copyProperties(tcAccount, tmAccount);
            tmAccount.setPassword(null);
            return tmAccount;
        });

        return tmAccountsStream.collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean existId(@Nonnull String id) {
        checkNotNull(id);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andIdEqualTo(id)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional(readOnly = true)
    public boolean existName(@Nonnull String name) {
        checkNotNull(name);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andNameEqualTo(name)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional(readOnly = true)
    public boolean existAccount(@Nonnull String account) {
        checkNotNull(account);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andAccountEqualTo(account)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional(readOnly = true)
    public boolean existEmail(@Nonnull String email) {
        checkNotNull(email);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andEmailEqualTo(email)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional(readOnly = true)
    public boolean existMobile(@Nonnull String mobile) {
        checkNotNull(mobile);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andMobileEqualTo(mobile)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional
    public void activeMobile(@Nonnull String accountId, @Nonnull String mobile, @Nonnull String operator) {
        checkNotNull(accountId);
        checkNotNull(mobile);
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在！"));
        }

        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andMobileEqualTo(mobile)
                .andDelectedEqualTo(false);

        TcAccount tcAccount = new TcAccount()
                .setMobileActivated(true)
                .setUpdatedPerson(operator)
                .setUpdatedTimestamp(new Date());

        int count = tcAccountMapper.updateByExampleSelective(tcAccount, tcAccountExample);

        checkArgument(count == 1);
    }

    @Transactional
    public void activeEmail(@Nonnull String accountId, @Nonnull String email, @Nonnull String operator) {
        checkNotNull(accountId);
        checkNotNull(email);
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在！"));
        }

        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andEmailEqualTo(email)
                .andDelectedEqualTo(false);

        TcAccount tcAccount = new TcAccount()
                .setEmailActivated(true)
                .setUpdatedPerson(operator)
                .setUpdatedTimestamp(new Date());

        int count = tcAccountMapper.updateByExampleSelective(tcAccount, tcAccountExample);

        checkArgument(count == 1);
    }

    @Transactional
    public void lockAccount(@Nonnull String accountId, @Nonnull Date lockReleaseTime, @Nonnull String operator) {
        // check
        TcAccount originalTcAccount = findOriginalTcAccount(accountId);
        if (Objects.isNull(originalTcAccount)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在！"));
        }

        if (Objects.nonNull(originalTcAccount.getLockReleaseTime()) &&
                originalTcAccount.getLockReleaseTime().after(lockReleaseTime)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号已经被锁定更长时间！"));
        }

        TcAccount tcAccount = new TcAccount()
                .setId(accountId)
                .setLockReleaseTime(lockReleaseTime)
                .setUpdatedPerson(operator)
                .setUpdatedTimestamp(new Date());
        int count = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(count == 1);
    }

    private TmAccount convert2TmAccount(TcAccount tcAccount) {
        return null;
    }

}

package com.ysu.zyw.tc.api.svc.account;

import com.google.common.base.Preconditions;
import com.ysu.zyw.tc.api.dao.mappers.TcAccountAssistMapper;
import com.ysu.zyw.tc.api.dao.mappers.TcAccountMapper;
import com.ysu.zyw.tc.api.dao.mappers.TcAccountPaymentMapper;
import com.ysu.zyw.tc.api.dao.penum.TcSignupPlatform;
import com.ysu.zyw.tc.api.dao.po.TcAccount;
import com.ysu.zyw.tc.api.dao.po.TcAccountAssist;
import com.ysu.zyw.tc.api.dao.po.TcAccountExample;
import com.ysu.zyw.tc.api.dao.po.TcAccountPayment;
import com.ysu.zyw.tc.api.fk.ex.TcVerifyFailureException;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.base.utils.TcPaginationUtils;
import com.ysu.zyw.tc.model.api.account.TmAccount;
import com.ysu.zyw.tc.model.validator.TcValidator;
import com.ysu.zyw.tc.model.validator.mode.TcCreateMode;
import com.ysu.zyw.tc.model.validator.mode.TcUpdateMode;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
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

    @Resource
    private TcAccountPaymentMapper tcAccountPaymentMapper;

    @Transactional(readOnly = true)
    public @NotEmpty String canSignin(@NotEmpty String username,
                                      @NotEmpty String password,
                                      @NotNull Boolean canAccountLogin,
                                      @NotNull Boolean canEmailLogin,
                                      @NotNull Boolean canMobileLogin) {
        Preconditions.checkArgument(canAccountLogin || canEmailLogin || canMobileLogin);

        TcAccountExample tcAccountExample = new TcAccountExample();
        // prevent some mistake leading to performance problem
        tcAccountExample.setStartLine(0);
        tcAccountExample.setPageSize(2);
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
    public @NotNull String createAccount(@NotNull @Validated(value = TcCreateMode.class) TmAccount tmAccount) {
        // check
        if (StringUtils.isNotBlank(tmAccount.getAccount()) && existAccount(tmAccount.getAccount())) {
            throw new TcVerifyFailureException(
                    new TcValidator.TcVerifyFailure("账号【" + tmAccount.getAccount() + "】重复，请更换重试！"));
        }

        if (StringUtils.isNotBlank(tmAccount.getEmail()) && existAccount(tmAccount.getEmail())) {
            throw new TcVerifyFailureException(
                    new TcValidator.TcVerifyFailure("邮箱【" + tmAccount.getEmail() + "】重复，请更换重试！"));
        }

        if (StringUtils.isNotBlank(tmAccount.getMobile()) && existAccount(tmAccount.getMobile())) {
            throw new TcVerifyFailureException(
                    new TcValidator.TcVerifyFailure("手机【" + tmAccount.getMobile() + "】重复，请更换重试！"));
        }

        // id
        String id = TcIdWorker.upperCaseUuid();
        Date now = Calendar.getInstance().getTime();

        // accountId
        TcAccount tcAccount = new TcAccount();
        BeanUtils.copyProperties(tmAccount, tcAccount);

        tcAccount.setId(id)
                .setDelected(false)
                .setLockReleaseTime(null)
                .setUpdatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setUpdatedTimestamp(now)
                .setCreatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setCreatedTimestamp(now);

        // accountId assist
        TcAccountAssist tcAccountAssist = new TcAccountAssist();

        BeanUtils.copyProperties(tmAccount, tcAccountAssist);
        tcAccountAssist.setSignupPlatform(TcSignupPlatform.convert(tmAccount.getSignupPlatform()));
        if (StringUtils.isBlank(tcAccount.getMobile())) {
            tcAccountAssist.setMobileActivated(false);
        }
        if (StringUtils.isBlank(tcAccount.getEmail())) {
            tcAccountAssist.setEmailActivated(false);
        }
        tcAccountAssist.setId(id)
                .setSignupTimestamp(now)
                .setLastLoginTimestamp(null)
                .setUpdatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setUpdatedTimestamp(now)
                .setCreatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setCreatedTimestamp(now);

        // accountId payment
        TcAccountPayment tcAccountPayment = new TcAccountPayment();

        BeanUtils.copyProperties(tmAccount, tcAccountPayment);
        if (StringUtils.isBlank(tcAccountPayment.getWeixinAccount())) {
            tcAccountPayment.setSupWeixin(false);
        }
        if (StringUtils.isBlank(tcAccountPayment.getZhifubaoAccount())) {
            tcAccountPayment.setSupZhifubao(false);
        }
        tcAccountPayment.setId(id)
                .setUpdatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setUpdatedTimestamp(now)
                .setCreatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setCreatedTimestamp(now);

        // insert
        int cAccount = tcAccountMapper.insert(tcAccount);
        int cAccountAssist = tcAccountAssistMapper.insert(tcAccountAssist);
        int cAccountPayment = tcAccountPaymentMapper.insert(tcAccountPayment);

        checkArgument(cAccount == 1 && cAccountAssist == 1 && cAccountPayment == 1);

        return id;
    }

    @Transactional
    public void deleteAccount(@NotNull String accountId) {
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        TcAccount tcAccount = new TcAccount();
        tcAccount.setId(accountId);
        tcAccount.setDelected(true);

        int count = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);
        checkArgument(count == 1);
    }

    @Transactional
    public void updateAccount(@NotNull @Validated(value = TcUpdateMode.class) TmAccount tmAccount) {
        // check
        if (!existId(tmAccount.getId())) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        Date now = Calendar.getInstance().getTime();

        // accountId
        TcAccount tcAccount = new TcAccount();

        BeanUtils.copyProperties(tmAccount, tcAccount);
        tcAccount
                .setLockReleaseTime(null)
                .setUpdatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setUpdatedTimestamp(now);

        // accountId assist
        TcAccountAssist tcAccountAssist = new TcAccountAssist();

        BeanUtils.copyProperties(tmAccount, tcAccountAssist);
        tcAccountAssist.setSignupPlatform(TcSignupPlatform.convert(tmAccount.getSignupPlatform()));
        if (StringUtils.isNotBlank(tcAccount.getMobile()) && Objects.isNull(tcAccountAssist.getMobileActivated())) {
            tcAccountAssist.setMobileActivated(false);
        }
        if (StringUtils.isBlank(tcAccount.getEmail()) && Objects.isNull(tcAccountAssist.getEmailActivated())) {
            tcAccountAssist.setEmailActivated(false);
        }
        tcAccountAssist
                .setUpdatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setUpdatedTimestamp(now);

        // accountId payment
        TcAccountPayment tcAccountPayment = new TcAccountPayment();

        BeanUtils.copyProperties(tmAccount, tcAccountPayment);
        tcAccountPayment
                .setUpdatedPerson(TcConstant.Sys.TC_ADMIN_ID)
                .setUpdatedTimestamp(now);

        // update
        int cAccount = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);
        int cAccountAssist = tcAccountAssistMapper.updateByPrimaryKeySelective(tcAccountAssist);
        int cAccountPayment = tcAccountPaymentMapper.updateByPrimaryKeySelective(tcAccountPayment);

        checkArgument(cAccount == 1 && cAccountAssist == 1 && cAccountPayment == 1);
    }

    @Transactional
    public void updatePassword(String accountId, String oPassword, String nPassword) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andDelectedEqualTo(false);
        List<TcAccount> tcAccounts = tcAccountMapper.selectByExample(tcAccountExample);
        if (CollectionUtils.isEmpty(tcAccounts)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }
        TcAccount tcAccount = tcAccounts.get(0);

        if (!Objects.equals(tcAccount.getPassword(), oPassword)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("原密码错误，请更换重试！"));
        }

        TcAccount mTcAccount = new TcAccount();
        mTcAccount.setId(accountId).setPassword(nPassword);

        int cAccount = tcAccountMapper.updateByPrimaryKeySelective(mTcAccount);

        checkArgument(cAccount == 1);
    }

    @Transactional(readOnly = true)
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

        BeanUtils.copyProperties(tcAccounts.get(0), tmAccount);
        tmAccount.setPassword(null);

        if (includeAssistField) {
            tmAccount = this.replenishAccountAssistField(tmAccount);
        }

        if (includePaymentField) {
            tmAccount = this.replenishAccountPaymentField(tmAccount);
        }

        return tmAccount;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

            BeanUtils.copyProperties(tcAccount, tmAccount);
            tmAccount.setPassword(null);
            return tmAccount;
        });

        if (includeAssistField) {
            tmAccountsStream = tmAccountsStream.map(this::replenishAccountAssistField);
        }

        if (includePaymentField) {
            tmAccountsStream = tmAccountsStream.map(this::replenishAccountPaymentField);
        }

        return tmAccountsStream.collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean existId(@NotNull String id) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andIdEqualTo(id)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional(readOnly = true)
    public boolean existAccount(@NotNull String account) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andAccountEqualTo(account)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional(readOnly = true)
    public boolean existEmail(@NotNull String mail) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andEmailEqualTo(mail)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional(readOnly = true)
    public boolean existMobile(@NotNull String mobile) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andMobileEqualTo(mobile)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    @Transactional
    public void activeMobile(@NotNull String accountId) {
        // check
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        TcAccountAssist tcAccountAssist = new TcAccountAssist()
                .setId(accountId).setMobileActivated(true);
        int count = tcAccountAssistMapper.updateByPrimaryKeySelective(tcAccountAssist);
        checkArgument(count == 1);
    }

    @Transactional
    public void activeEmail(@NotNull String accountId) {
        // check
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        TcAccountAssist tcAccountAssist = new TcAccountAssist()
                .setId(accountId).setEmailActivated(true);
        int count = tcAccountAssistMapper.updateByPrimaryKeySelective(tcAccountAssist);
        checkArgument(count == 1);
    }

    @Transactional
    public void activeSupWeixin(@NotNull String accountId) {
        // check
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        TcAccountPayment tcAccountPayment = new TcAccountPayment()
                .setId(accountId).setSupWeixin(true);
        int count = tcAccountPaymentMapper.updateByPrimaryKeySelective(tcAccountPayment);
        checkArgument(count == 1);
    }

    @Transactional
    public void activeSupZhifubao(@NotNull String accountId) {
        // check
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        TcAccountPayment tcAccountPayment = new TcAccountPayment()
                .setId(accountId).setSupZhifubao(true);
        int count = tcAccountPaymentMapper.updateByPrimaryKeySelective(tcAccountPayment);
        checkArgument(count == 1);
    }

    @Transactional
    public void activeSupCod(@NotNull String accountId) {
        // check
        if (!existId(accountId)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        TcAccountPayment tcAccountPayment = new TcAccountPayment()
                .setId(accountId).setSupCod(true);
        int count = tcAccountPaymentMapper.updateByPrimaryKeySelective(tcAccountPayment);
        checkArgument(count == 1);
    }

    @Transactional
    public void lockAccount(@NotNull String accountId, @NotNull Date lockReleaseTime) {
        // check
        TmAccount tmAccount = findAccount(accountId, false, false);
        if (Objects.isNull(tmAccount)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号不存在，请更换重试！"));
        }

        if (Objects.nonNull(tmAccount.getLockReleaseTime()) &&
                tmAccount.getLockReleaseTime().after(lockReleaseTime)) {
            throw new TcVerifyFailureException(new TcValidator.TcVerifyFailure("账号已经被锁定更长时间！"));
        }

        TcAccount tcAccount = new TcAccount()
                .setId(accountId)
                .setLockReleaseTime(lockReleaseTime);
        int count = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);
        checkArgument(count == 1);
    }

    private TmAccount replenishAccountAssistField(TmAccount tmAccount) {
        checkNotNull(tmAccount);
        String accountId = tmAccount.getId();

        TcAccountAssist tcAccountAssist = tcAccountAssistMapper.selectByPrimaryKey(accountId);
        checkNotNull(tcAccountAssist);

        BeanUtils.copyProperties(tcAccountAssist, tmAccount);
        tmAccount.setSignupPlatform(TcSignupPlatform.convert(tcAccountAssist.getSignupPlatform()));
        return tmAccount;
    }

    private TmAccount replenishAccountPaymentField(TmAccount tmAccount) {
        checkNotNull(tmAccount);
        String accountId = tmAccount.getId();

        TcAccountPayment tcAccountPayment = tcAccountPaymentMapper.selectByPrimaryKey(accountId);
        checkNotNull(tcAccountPayment);

        BeanUtils.copyProperties(tcAccountPayment, tmAccount);
        return tmAccount;
    }

}

package com.ysu.zyw.tc.api.svc.accounts;

import com.ysu.zyw.tc.api.dao.mappers.TcAccountAssistMapper;
import com.ysu.zyw.tc.api.dao.mappers.TcAccountMapper;
import com.ysu.zyw.tc.api.dao.po.TcAccount;
import com.ysu.zyw.tc.api.dao.po.TcAccountAssist;
import com.ysu.zyw.tc.api.dao.po.TcAccountExample;
import com.ysu.zyw.tc.api.fk.ex.TcUnProcessableEntityException;
import com.ysu.zyw.tc.api.svc.accounts.auth.TcAuthService;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.base.utils.TcPaginationUtils;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.api.i.accounts.TiFindAccountsTerms;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.mw.TcExtra;
import com.ysu.zyw.tc.sys.ex.TcResourceNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
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
    private TcAuthService tcAuthService;

    /**
     * @return 可登陆：可登陆账号的accountId
     * @throws TcUnProcessableEntityException 业务级不可登陆: code == 1 => 账号不存在; code == 2 => 账号被锁定;
     */
    @Transactional(readOnly = true)
    public String signup(@Nonnull String username,
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
            throw new TcUnProcessableEntityException(new TcExtra(1, "账号不存在！"));
        }

        TcAccount tcAccount = tcAccounts.get(0);

        // the password check is pass to caller

        if (Objects.nonNull(tcAccount.getLockReleaseTime()) &&
                // 当前时间在解锁之前之前
                new Date().before(tcAccount.getLockReleaseTime())) {
            // 被锁定
            throw new TcUnProcessableEntityException(new TcExtra(2, "账号被锁定！"));
        }

        return tcAccount.getId();
    }

    /**
     * @return 创建成功：可登陆账号的accountId
     * @throws TcUnProcessableEntityException 业务级创建失败: 内嵌TcExtra描述信息
     *                                        code == 1? => 数据库唯一键重复;
     */
    @Transactional
    public String createAccount(@Nonnull TiAccount tiAccount) {
        // check
        if (existName(tiAccount.getName())) {
            throw new TcUnProcessableEntityException(
                    new TcExtra(11, "名称【" + tiAccount.getName() + "】重复，请重试！"));
        }

        if (existAccount(tiAccount.getAccount())) {
            throw new TcUnProcessableEntityException(
                    new TcExtra(12, "账号【" + tiAccount.getAccount() + "】重复，请重试！"));
        }

        if (StringUtils.isNotBlank(tiAccount.getEmail()) && existEmail(tiAccount.getEmail())) {
            throw new TcUnProcessableEntityException(
                    new TcExtra(13, "邮箱【" + tiAccount.getEmail() + "】重复，请重试！"));
        }

        if (StringUtils.isNotBlank(tiAccount.getMobile()) && existAccount(tiAccount.getMobile())) {
            throw new TcUnProcessableEntityException(
                    new TcExtra(14, "手机【" + tiAccount.getMobile() + "】重复，请重试！"));
        }

        // id
        String id = TcIdWorker.upperCaseUuid();
        Date now = new Date();

        // account
        TcAccount tcAccount = new TcAccount();

        BeanUtils.copyProperties(tiAccount, tcAccount);
        tcAccount
                .setId(id)
                .setDelected(false)
                .setLockReleaseTime(now)
                // FIXME if random token is already exists, account creation failed.
                .setRandomToken(TcIdWorker.upperCaseUuid().substring(0, 16))
                .setUpdatedPerson(tiAccount.getOperatorAccountId())
                .setUpdatedTimestamp(now)
                .setCreatedPerson(tiAccount.getOperatorAccountId())
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

        BeanUtils.copyProperties(tiAccount, tcAccountAssist);
        tcAccountAssist
                .setId(id)
                .setSigninPlatform(tiAccount.getSigninPlatform())
                .setSigninTimestamp(now)
                .setLastSignupPlatform(tiAccount.getSigninPlatform())
                .setLastSignupTimestamp(now)
                .setUpdatedPerson(tiAccount.getOperatorAccountId())
                .setUpdatedTimestamp(now)
                .setCreatedPerson(tiAccount.getOperatorAccountId())
                .setCreatedTimestamp(now);

        // insert
        int cAccount = tcAccountMapper.insert(tcAccount);
        int cAccountAssist = tcAccountAssistMapper.insert(tcAccountAssist);

        checkArgument(cAccount == 1 && cAccountAssist == 1);

        return id;
    }

    /**
     * @throws TcUnProcessableEntityException 业务级删除失败: 内嵌TcExtra描述信息
     *                                        code == 1 => 账号不存在;
     */
    @Transactional
    public void deleteAccount(@Nonnull String accountId, @Nonnull String delector) {
        if (!existId(accountId)) {
            throw new TcUnProcessableEntityException(new TcExtra(1, "账号不存在，请重试！"));
        }

        // account
        TcAccount tcAccount = new TcAccount();
        tcAccount
                .setId(accountId)
                .setDelected(true)
                .setUpdatedPerson(delector)
                .setUpdatedTimestamp(new Date());

        // role
        tcAuthService.revokeAllRole(accountId);

        // permission
        tcAuthService.revokeAllPermission(accountId);

        int count = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(count == 1);
    }

    /**
     * @throws TcUnProcessableEntityException 业务级更新失败: 内嵌TcExtra描述信息
     *                                        code == 1 => 账号不存在; code == 1? => 数据库唯一键重复;
     */
    @Transactional
    public void updateAccount(@Nonnull TiAccount tiAccount) {
        TcAccount originalTcAccount = this.findOriginalTcAccount(tiAccount.getId(), false);

        if (Objects.isNull(originalTcAccount)) {
            throw new TcUnProcessableEntityException(new TcExtra(1, "账号不存在，请重试！"));
        }

        if (StringUtils.isNotEmpty(tiAccount.getName()) && existName(tiAccount.getName())) {
            throw new TcUnProcessableEntityException(new TcExtra(11, "名字已存在，请重试！"));
        }

        if (StringUtils.isNotEmpty(tiAccount.getAccount()) && existName(tiAccount.getAccount())) {
            throw new TcUnProcessableEntityException(new TcExtra(12, "账号已存在，请重试！"));
        }

        if (StringUtils.isNotEmpty(tiAccount.getMobile()) && existName(tiAccount.getMobile())) {
            throw new TcUnProcessableEntityException(new TcExtra(13, "手机已存在，请重试！"));
        }

        if (StringUtils.isNotEmpty(tiAccount.getEmail()) && existName(tiAccount.getEmail())) {
            throw new TcUnProcessableEntityException(new TcExtra(14, "邮箱已存在，请重试！"));
        }

        Date now = new Date();

        // account
        TcAccount tcAccount = new TcAccount();

        BeanUtils.copyProperties(tiAccount, tcAccount);
        tcAccount
                // mobile activated can not be update to true, use spi instead.
                .setMobileActivated(
                        BooleanUtils.isTrue(tiAccount.getMobileActivated()) ? null : tiAccount.getMobileActivated()
                )
                // email activated can not be update to true, use spi instead.
                .setEmailActivated(
                        BooleanUtils.isTrue(tiAccount.getEmailActivated()) ? null : tiAccount.getEmailActivated()
                )
                // can not be updated, use spi instead.
                .setLockReleaseTime(null)
                // can not be updated, use spi instead.
                .setPassword(null)
                // can not be updated, use spi instead.
                .setDelected(null)
                .setUpdatedPerson(tiAccount.getOperatorAccountId())
                .setUpdatedTimestamp(now);
        // if mobile changed, auto set mobile activated to false.
        if (StringUtils.isNotBlank(tcAccount.getMobile()) &&
                Objects.equals(tcAccount.getMobile(), originalTcAccount.getMobile())) {
            tcAccount.setMobileActivated(false);
        }
        // if email changed, auto set email activated to false.
        if (StringUtils.isNotBlank(tcAccount.getEmail()) &&
                Objects.equals(tcAccount.getEmail(), originalTcAccount.getEmail())) {
            tcAccount.setEmailActivated(false);
        }

        // update
        int cAccount = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(cAccount == 1);
    }

    /**
     * @throws TcUnProcessableEntityException 业务级更新失败: 内嵌TcExtra描述信息
     *                                        code == 1 => 账号不存在; code == 2 => 原密码错误;
     */
    @Transactional
    public void updatePassword(@Nonnull String accountId,
                               @Nonnull String oPassword,
                               @Nonnull String nPassword,
                               @Nonnull String operator) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.setStartLine(0);
        tcAccountExample.setPageSize(1);
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andDelectedEqualTo(false);
        List<TcAccount> tcAccounts = tcAccountMapper.selectByExample(tcAccountExample);
        if (CollectionUtils.isEmpty(tcAccounts)) {
            throw new TcUnProcessableEntityException(new TcExtra(1, "账号不存在，请重试！"));
        }
        TcAccount originalTcAccountWithPassword = tcAccounts.get(0);

        if (!Objects.equals(originalTcAccountWithPassword.getPassword(), oPassword)) {
            throw new TcUnProcessableEntityException(new TcExtra(2, "原密码错误，请重试！"));
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

    /**
     * @throws TcResourceNotFoundException 账号不存在
     */
    @Transactional(readOnly = true)
    public ToAccount findAccount(@Nonnull String accountId, @Nonnull Boolean containsPassword) {
        checkNotNull(accountId);
        TcAccount originalTcAccount = this.findOriginalTcAccount(accountId, containsPassword);
        if (Objects.isNull(originalTcAccount)) {
            throw new TcResourceNotFoundException("账号不存在");
        }
        return convert2ToAccount(originalTcAccount);
    }

    private TcAccount findOriginalTcAccount(@Nonnull String accountId, @Nonnull Boolean containsPassword) {
        checkNotNull(accountId);
        checkNotNull(containsPassword);
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
        if (BooleanUtils.isFalse(containsPassword)) {
            tcAccount.setPassword(null);
        }

        return tcAccount;
    }

    @Transactional(readOnly = true)
    public long countAccounts(@Nonnull TiFindAccountsTerms tiFindAccountsTerms) {
        TcAccountExample tcAccountExample =
                buildFindAccountCondition(
                        tiFindAccountsTerms.getIds(),
                        tiFindAccountsTerms.getName(),
                        tiFindAccountsTerms.getAccount(),
                        tiFindAccountsTerms.getMobile(),
                        tiFindAccountsTerms.getEmail(),
                        tiFindAccountsTerms.getMobileActivated(),
                        tiFindAccountsTerms.getEmailActivated(),
                        tiFindAccountsTerms.getLocked());
        return tcAccountMapper.countByExample(tcAccountExample);
    }

    @Transactional(readOnly = true)
    public List<ToAccount> findAccounts(@Nonnull TiFindAccountsTerms tiFindAccountsTerms,
                                        int currentPage,
                                        int pageSize) {
        TcAccountExample tcAccountExample =
                buildFindAccountCondition(
                        tiFindAccountsTerms.getIds(),
                        tiFindAccountsTerms.getName(),
                        tiFindAccountsTerms.getAccount(),
                        tiFindAccountsTerms.getMobile(),
                        tiFindAccountsTerms.getEmail(),
                        tiFindAccountsTerms.getMobileActivated(),
                        tiFindAccountsTerms.getEmailActivated(),
                        tiFindAccountsTerms.getLocked());
        TcPaginationUtils.Pagination pagination = TcPaginationUtils.paging(currentPage, pageSize);
        tcAccountExample.setStartLine(pagination.getStartLine());
        tcAccountExample.setPageSize(pagination.getPageSize());

        List<TcAccount> tcAccountList = tcAccountMapper.selectByExample(tcAccountExample);

        Stream<ToAccount> tmAccountsStream = tcAccountList.stream().map(this::convert2ToAccount);

        return tmAccountsStream.collect(Collectors.toList());
    }

    private TcAccountExample buildFindAccountCondition(
            @Nullable List<String> ids,
            @Nullable String name,
            @Nullable String account,
            @Nullable String mobile,
            @Nullable String email,
            @Nullable Boolean mobileActivated,
            @Nullable Boolean emailActivated,
            @Nullable Boolean locked) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        TcAccountExample.Criteria criteria = tcAccountExample.createCriteria();
        criteria.andDelectedEqualTo(false);
        if (CollectionUtils.isNotEmpty(ids)) {
            criteria.andIdIn(ids);
        }
        if (StringUtils.isNotEmpty(name)) {
            criteria.andNameEqualTo(name);
        }
        if (StringUtils.isNotEmpty(account)) {
            criteria.andAccountEqualTo(account);
        }
        if (StringUtils.isNotEmpty(email)) {
            criteria.andEmailEqualTo(email);
        }
        if (StringUtils.isNotEmpty(mobile)) {
            criteria.andEmailEqualTo(email);
        }
        if (Objects.nonNull(mobileActivated)) {
            criteria.andMobileActivatedEqualTo(mobileActivated);
        }
        if (Objects.nonNull(emailActivated)) {
            criteria.andEmailActivatedEqualTo(emailActivated);
        }
        if (Objects.nonNull(locked)) {
            Date now = new Date();
            if (BooleanUtils.isTrue(locked)) {
                criteria.andLockReleaseTimeGreaterThan(now);
            } else {
                criteria.andLockReleaseTimeLessThanOrEqualTo(now);
            }
        }
        return tcAccountExample;
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

    /**
     * @throws TcUnProcessableEntityException 业务级更新失败: 内嵌TcExtra描述信息
     *                                        code == 1 => 账号不存在;
     */
    @Transactional
    public void activeMobile(@Nonnull String accountId, @Nonnull String mobile, @Nonnull String operator) {
        checkNotNull(accountId);
        checkNotNull(mobile);
        if (!existId(accountId)) {
            throw new TcUnProcessableEntityException(new TcExtra(1, "账号不存在，请重试！"));
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

    /**
     * @throws TcUnProcessableEntityException 业务级更新失败: 内嵌TcExtra描述信息
     *                                        code == 1 => 账号不存在;
     */
    @Transactional
    public void activeEmail(@Nonnull String accountId, @Nonnull String email, @Nonnull String operator) {
        checkNotNull(accountId);
        checkNotNull(email);
        if (!existId(accountId)) {
            throw new TcUnProcessableEntityException(new TcExtra(1, "账号不存在，请重试！"));
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

    /**
     * @throws TcUnProcessableEntityException 业务级更新失败: 内嵌TcExtra描述信息
     *                                        code == 1 => 账号不存在; code == 2 => 账号已被锁定更长时间;
     */
    @Transactional
    public void lockAccount(@Nonnull String accountId, @Nonnull Date lockReleaseTime, @Nonnull String operator) {
        // check
        TcAccount originalTcAccount = findOriginalTcAccount(accountId, false);
        if (Objects.isNull(originalTcAccount)) {
            throw new TcUnProcessableEntityException(new TcExtra(1, "账号不存在，请重试！"));
        }

        if (Objects.nonNull(originalTcAccount.getLockReleaseTime()) &&
                originalTcAccount.getLockReleaseTime().after(lockReleaseTime)) {
            throw new TcUnProcessableEntityException(new TcExtra(2, "账号已经被锁定更长时间，请重试！"));
        }

        TcAccount tcAccount = new TcAccount()
                .setId(accountId)
                .setLockReleaseTime(lockReleaseTime)
                .setUpdatedPerson(operator)
                .setUpdatedTimestamp(new Date());
        int count = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(count == 1);
    }

    private ToAccount convert2ToAccount(@Nonnull TcAccount tcAccount) {
        checkNotNull(tcAccount);
        ToAccount toAccount = new ToAccount();
        BeanUtils.copyProperties(tcAccount, toAccount);
        return toAccount;
    }

}

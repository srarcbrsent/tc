package com.ysu.zyw.tc.api.svc.accounts;

import com.ysu.zyw.tc.api.dao.mappers.TcAccountMapper;
import com.ysu.zyw.tc.api.dao.po.TcAccount;
import com.ysu.zyw.tc.api.dao.po.TcAccountExample;
import com.ysu.zyw.tc.api.fk.ex.TcUnProcessableEntityException;
import com.ysu.zyw.tc.api.svc.accounts.auth.TcAuthService;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.base.utils.TcBeanUtils;
import com.ysu.zyw.tc.base.utils.TcPaginationUtils;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.api.i.accounts.TiFindAccountsTerms;
import com.ysu.zyw.tc.model.api.i.accounts.TuAccount;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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

@Slf4j
@Service
public class TcAccountService {

    @Resource
    private TcAccountMapper tcAccountMapper;

    @Resource
    private TcAuthService tcAuthService;

    /**
     * @return 可登陆账号的accountId
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     * @code code == 2 => 账号被锁定;
     */
    @Transactional(readOnly = true)
    public String login(@Nonnull String username,
                        @Nonnull Boolean canEmailLogin,
                        @Nonnull Boolean canMobileLogin) throws TcUnProcessableEntityException {
        checkArgument(canEmailLogin || canMobileLogin);

        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.setStartLine(0);
        tcAccountExample.setPageSize(1);
        if (canEmailLogin) {
            tcAccountExample.or().andEmailEqualTo(username).andDelectedEqualTo(false);
        }
        if (canMobileLogin) {
            tcAccountExample.or().andMobileEqualTo(username).andDelectedEqualTo(false);
        }
        List<TcAccount> tcAccounts = tcAccountMapper.selectByExample(tcAccountExample);

        if (CollectionUtils.isEmpty(tcAccounts)) {
            // 账号不存在
            throw new TcUnProcessableEntityException(1, "账号不存在！");
        }

        TcAccount tcAccount = tcAccounts.get(0);

        // 当前时间在解锁之前之前
        if (Objects.nonNull(tcAccount.getLockReleaseTime())
                && new Date().before(tcAccount.getLockReleaseTime())) {
            // 被锁定
            throw new TcUnProcessableEntityException(2, "账号被锁定！");
        }

        return tcAccount.getId();
    }

    /**
     * @return 创建成功：可登陆账号的accountId
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 11 => 昵称重复;
     * @code code == 12 => 邮箱重复;
     * @code code == 13 => 手机重复;
     */
    @Transactional
    public String createAccount(@Nonnull TiAccount inputAccount) throws TcUnProcessableEntityException {
        if (existNickname(inputAccount.getNickname())) {
            throw new TcUnProcessableEntityException(11, "昵称[{}]重复！", inputAccount.getNickname());
        }

        if (StringUtils.isNotBlank(inputAccount.getEmail()) && existEmail(inputAccount.getEmail())) {
            throw new TcUnProcessableEntityException(12, "邮箱[{}]重复！", inputAccount.getEmail());
        }

        if (StringUtils.isNotBlank(inputAccount.getMobile()) && existMobile(inputAccount.getMobile())) {
            throw new TcUnProcessableEntityException(13, "手机[{}]重复！", inputAccount.getMobile());
        }

        // id
        String id = TcIdGen.upperCaseUuid();
        Date now = new Date();

        // account
        TcAccount tcAccount = new TcAccount();

        TcBeanUtils.copyProperties(inputAccount, tcAccount);
        tcAccount
                .setDelected(false)
                .setLockReleaseTime(now)
                .setRandomToken(randomToken())
                .setUpdatedPerson(inputAccount.getCreatedPerson())
                .setUpdatedTimestamp(now)
                .setCreatedPerson(inputAccount.getCreatedPerson())
                .setCreatedTimestamp(now);

        // insert
        int cAccount = tcAccountMapper.insert(tcAccount);

        checkArgument(cAccount == 1);

        return id;
    }

    /**
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     */
    @Transactional
    public void deleteAccount(@Nonnull String accountId, @Nonnull String delector)
            throws TcUnProcessableEntityException {
        if (!existId(accountId)) {
            throw new TcUnProcessableEntityException(1, "账号不存在！");
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
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     * @code code == 11 => 昵称重复;
     * @code code == 12 => 邮箱重复;
     * @code code == 13 => 手机重复;
     */
    @Transactional
    public void updateAccount(@Nonnull TuAccount tuAccount) throws TcUnProcessableEntityException {
        TcAccount originalTcAccount = findOriginalTcAccount(tuAccount.getId(), false);

        if (Objects.isNull(originalTcAccount)) {
            throw new TcUnProcessableEntityException(1, "账号不存在！");
        }

        if (StringUtils.isNotEmpty(tuAccount.getNickname()) && existNickname(tuAccount.getNickname())) {
            throw new TcUnProcessableEntityException(11, "昵称已存在！");
        }

        if (StringUtils.isNotEmpty(tuAccount.getEmail()) && existNickname(tuAccount.getEmail())) {
            throw new TcUnProcessableEntityException(12, "邮箱已存在！");
        }

        if (StringUtils.isNotEmpty(tuAccount.getMobile()) && existNickname(tuAccount.getMobile())) {
            throw new TcUnProcessableEntityException(13, "手机已存在！");
        }

        Date now = new Date();

        // account
        TcAccount tcAccount = new TcAccount();

        TcBeanUtils.copyProperties(tuAccount, tcAccount);
        tcAccount
                // mobile activated can not be update to true, use spi instead.
                .setMobileActivated(
                        BooleanUtils.isTrue(tuAccount.getMobileActivated()) ? null : tuAccount.getMobileActivated()
                )
                // email activated can not be update to true, use spi instead.
                .setEmailActivated(
                        BooleanUtils.isTrue(tuAccount.getEmailActivated()) ? null : tuAccount.getEmailActivated()
                )
                // can not be updated, use spi instead.
                .setLockReleaseTime(null)
                // can not be updated, use spi instead.
                .setPassword(null)
                // can not be updated, use spi instead.
                .setDelected(null)
                .setUpdatedPerson(tuAccount.getUpdatedPerson())
                .setUpdatedTimestamp(now);
        // if mobile changed, auto set mobile activated to false.
        if (StringUtils.isNotBlank(tcAccount.getMobile())
                && Objects.equals(tcAccount.getMobile(), originalTcAccount.getMobile())) {
            tcAccount.setMobileActivated(false);
        }
        // if email changed, auto set email activated to false.
        if (StringUtils.isNotBlank(tcAccount.getEmail())
                && Objects.equals(tcAccount.getEmail(), originalTcAccount.getEmail())) {
            tcAccount.setEmailActivated(false);
        }

        // update
        int cAccount = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(cAccount == 1);
    }

    /**
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     * @code code == 2 => 原密码错误;
     */
    @Transactional
    public void updatePassword(@Nonnull String accountId,
                               @Nonnull String oPassword,
                               @Nonnull String nPassword,
                               @Nonnull String operator) throws TcUnProcessableEntityException {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.setStartLine(0);
        tcAccountExample.setPageSize(1);
        tcAccountExample.createCriteria()
                .andIdEqualTo(accountId)
                .andDelectedEqualTo(false);
        List<TcAccount> tcAccounts = tcAccountMapper.selectByExample(tcAccountExample);
        if (CollectionUtils.isEmpty(tcAccounts)) {
            throw new TcUnProcessableEntityException(1, "账号不存在！");
        }
        TcAccount originalTcAccountWithPassword = tcAccounts.get(0);

        if (!Objects.equals(originalTcAccountWithPassword.getPassword(), oPassword)) {
            throw new TcUnProcessableEntityException(2, "原密码错误！");
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
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     */
    @Transactional(readOnly = true)
    public ToAccount findAccount(@Nonnull String accountId, @Nonnull Boolean containsPassword)
            throws TcUnProcessableEntityException {
        checkNotNull(accountId);
        TcAccount originalTcAccount = findOriginalTcAccount(accountId, containsPassword);
        if (Objects.isNull(originalTcAccount)) {
            throw new TcUnProcessableEntityException(1, "账号不存在");
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

    /**
     * @return 数量
     */
    @Transactional(readOnly = true)
    public long countAccounts(@Nonnull TiFindAccountsTerms tiFindAccountsTerms) {
        TcAccountExample tcAccountExample =
                buildFindAccountCondition(
                        tiFindAccountsTerms.getIds(),
                        tiFindAccountsTerms.getRegion(),
                        tiFindAccountsTerms.getNickname(),
                        tiFindAccountsTerms.getEmail(),
                        tiFindAccountsTerms.getEmailActivated(),
                        tiFindAccountsTerms.getMobile(),
                        tiFindAccountsTerms.getMobileActivated(),
                        tiFindAccountsTerms.getLocked(),
                        tiFindAccountsTerms.getRandomToken());
        return tcAccountMapper.countByExample(tcAccountExample);
    }

    /**
     * @return 列表
     */
    @Transactional(readOnly = true)
    public List<ToAccount> findAccounts(@Nonnull TiFindAccountsTerms tiFindAccountsTerms,
                                        int currentPage,
                                        int pageSize) {
        TcAccountExample tcAccountExample =
                buildFindAccountCondition(
                        tiFindAccountsTerms.getIds(),
                        tiFindAccountsTerms.getRegion(),
                        tiFindAccountsTerms.getNickname(),
                        tiFindAccountsTerms.getEmail(),
                        tiFindAccountsTerms.getEmailActivated(),
                        tiFindAccountsTerms.getMobile(),
                        tiFindAccountsTerms.getMobileActivated(),
                        tiFindAccountsTerms.getLocked(),
                        tiFindAccountsTerms.getRandomToken());
        TcPaginationUtils.Pagination pagination = TcPaginationUtils.paging(currentPage, pageSize);
        tcAccountExample.setStartLine(pagination.getStartLine());
        tcAccountExample.setPageSize(pagination.getPageSize());

        List<TcAccount> tcAccountList = tcAccountMapper.selectByExample(tcAccountExample);

        Stream<ToAccount> tmAccountsStream = tcAccountList.stream().map(this::convert2ToAccount);

        return tmAccountsStream.collect(Collectors.toList());
    }

    private TcAccountExample buildFindAccountCondition(
            @Nullable List<String> ids,
            @Nullable String region,
            @Nullable String nickname,
            @Nullable String email,
            @Nullable Boolean emailActivated,
            @Nullable String mobile,
            @Nullable Boolean mobileActivated,
            @Nullable Boolean locked,
            @Nullable String randomToken) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        TcAccountExample.Criteria criteria = tcAccountExample.createCriteria();
        criteria.andDelectedEqualTo(false);
        if (CollectionUtils.isNotEmpty(ids)) {
            criteria.andIdIn(ids);
        }
        if (StringUtils.isNotBlank(region)) {
            criteria.andRegionEqualTo(region);
        }
        if (StringUtils.isNotBlank(nickname)) {
            criteria.andNicknameEqualTo(nickname);
        }
        if (StringUtils.isNotBlank(email)) {
            criteria.andEmailEqualTo(email);
        }
        if (Objects.nonNull(emailActivated)) {
            criteria.andEmailActivatedEqualTo(emailActivated);
        }
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andEmailEqualTo(email);
        }
        if (Objects.nonNull(mobileActivated)) {
            criteria.andMobileActivatedEqualTo(mobileActivated);
        }
        if (Objects.nonNull(locked)) {
            Date now = new Date();
            if (BooleanUtils.isTrue(locked)) {
                criteria.andLockReleaseTimeGreaterThan(now);
            } else {
                criteria.andLockReleaseTimeLessThanOrEqualTo(now);
            }
        }
        if (StringUtils.isNotBlank(randomToken)) {
            criteria.andRandomTokenEqualTo(randomToken);
        }
        return tcAccountExample;
    }

    /**
     * @return 账号id是否存在
     */
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

    /**
     * @return 昵称是否存在
     */
    @Transactional(readOnly = true)
    public boolean existNickname(@Nonnull String nickname) {
        checkNotNull(nickname);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andNicknameEqualTo(nickname)
                .andDelectedEqualTo(false);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        checkArgument(count < 2);
        return count == 1;
    }

    /**
     * @return 邮箱是否存在
     */
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

    /**
     * @return 电话是否存在
     */
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
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     */
    @Transactional
    public void activeMobile(@Nonnull String accountId, @Nonnull String mobile, @Nonnull String operator)
            throws TcUnProcessableEntityException {
        checkNotNull(accountId);
        checkNotNull(mobile);
        if (!existId(accountId)) {
            throw new TcUnProcessableEntityException(1, "账号不存在！");
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
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     */
    @Transactional
    public void activeEmail(@Nonnull String accountId, @Nonnull String email, @Nonnull String operator)
            throws TcUnProcessableEntityException {
        checkNotNull(accountId);
        checkNotNull(email);
        if (!existId(accountId)) {
            throw new TcUnProcessableEntityException(1, "账号不存在！");
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
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     * @code code == 2 => 账号已被锁定更长时间;
     */
    @Transactional
    public void lockAccount(@Nonnull String accountId, @Nonnull Date lockReleaseTime, @Nonnull String operator)
            throws TcUnProcessableEntityException {
        // check
        TcAccount originalTcAccount = findOriginalTcAccount(accountId, false);
        if (Objects.isNull(originalTcAccount)) {
            throw new TcUnProcessableEntityException(1, "账号不存在！");
        }

        if (Objects.nonNull(originalTcAccount.getLockReleaseTime())
                && originalTcAccount.getLockReleaseTime().after(lockReleaseTime)) {
            throw new TcUnProcessableEntityException(2, "账号已经被锁定更长时间！");
        }

        TcAccount tcAccount = new TcAccount()
                .setId(accountId)
                .setLockReleaseTime(lockReleaseTime)
                .setUpdatedPerson(operator)
                .setUpdatedTimestamp(new Date());
        int count = tcAccountMapper.updateByPrimaryKeySelective(tcAccount);

        checkArgument(count == 1);
    }

    private String randomToken() {
        String randomToken = TcIdGen.upperCaseUuid().substring(0, 16);
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria()
                .andRandomTokenEqualTo(randomToken);
        long count = tcAccountMapper.countByExample(tcAccountExample);
        return count == 0 ? randomToken : randomToken();
    }

    private ToAccount convert2ToAccount(@Nonnull TcAccount tcAccount) {
        checkNotNull(tcAccount);
        ToAccount toAccount = new ToAccount();
        TcBeanUtils.copyProperties(tcAccount, toAccount);
        return toAccount;
    }

}

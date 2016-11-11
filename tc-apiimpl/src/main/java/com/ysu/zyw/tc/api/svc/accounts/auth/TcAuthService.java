package com.ysu.zyw.tc.api.svc.accounts.auth;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.dao.mappers.*;
import com.ysu.zyw.tc.api.dao.po.*;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class TcAuthService {

    @Resource
    private TcRoleMapper tcRoleMapper;

    @Resource
    private TcPermissionMapper tcPermissionMapper;

    @Resource
    private TcMenuMapper tcMenuMapper;

    @Resource
    private TcAccountMapRoleMapper tcAccountMapRoleMapper;

    @Resource
    private TcAccountMapPermissionMapper tcAccountMapPermissionMapper;

    @Resource
    private TcRoleMapPermissionMapper tcRoleMapPermissionMapper;

    @Resource
    private TcRoleMapMenuMapper tcRoleMapMenuMapper;

    @Transactional
    public int grantRole(@Nonnull String accountId,
                         @Nonnull List<String> roleIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(roleIdList));
        Date now = Calendar.getInstance().getTime();
        List<TcAccountMapRole> tcAccountMapRoleList = roleIdList
                .parallelStream()
                .map(roleId ->
                        new TcAccountMapRole(
                                TcIdWorker.upperCaseUuid(),
                                accountId,
                                roleId,
                                TcConstant.Sys.TC_ADMIN_ID,
                                now,
                                TcConstant.Sys.TC_ADMIN_ID,
                                now
                        )
                )
                .collect(Collectors.toList());
        return tcAccountMapRoleMapper.batchInsert(tcAccountMapRoleList);
    }

    @Transactional
    public int grantPermission(@Nonnull String accountId,
                               @Nonnull List<String> permissionIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(permissionIdList));
        Date now = Calendar.getInstance().getTime();
        List<TcAccountMapPermission> tcAccountMapPermissionList = permissionIdList
                .parallelStream()
                .map(permissionId ->
                        new TcAccountMapPermission(
                                TcIdWorker.upperCaseUuid(),
                                accountId,
                                permissionId,
                                TcConstant.Sys.TC_ADMIN_ID,
                                now,
                                TcConstant.Sys.TC_ADMIN_ID,
                                now
                        )
                )
                .collect(Collectors.toList());
        return tcAccountMapPermissionMapper.batchInsert(tcAccountMapPermissionList);
    }

    @Transactional
    public int revokeRole(@Nonnull String accountId,
                          @Nonnull List<String> roleIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(roleIdList));
        TcAccountMapRoleExample tcAccountMapRoleExample = new TcAccountMapRoleExample();
        tcAccountMapRoleExample.createCriteria()
                .andAccountIdEqualTo(accountId)
                .andRoleIdIn(roleIdList);
        return tcAccountMapRoleMapper.deleteByExample(tcAccountMapRoleExample);
    }

    @Transactional
    public int revokePermission(@Nonnull String accountId,
                                @Nonnull List<String> permissionIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(permissionIdList));
        TcAccountMapPermissionExample tcAccountMapPermissionExample = new TcAccountMapPermissionExample();
        tcAccountMapPermissionExample.createCriteria()
                .andAccountIdEqualTo(accountId)
                .andPermissionIdIn(permissionIdList);
        return tcAccountMapPermissionMapper.deleteByExample(tcAccountMapPermissionExample);
    }

    @Transactional(readOnly = true)
    public List<TcRole> fetchRoleList(@Nonnull String accountId) {
        checkNotNull(accountId);

        TcAccountMapRoleExample tcAccountMapRoleExample = new TcAccountMapRoleExample();
        tcAccountMapRoleExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<String> holdingRoleIds =
                tcAccountMapRoleMapper.selectPrimaryKeyByExample(tcAccountMapRoleExample);

        if (CollectionUtils.isEmpty(holdingRoleIds)) {
            return Lists.newArrayList();
        }

        TcRoleExample tcRoleExample = new TcRoleExample();
        tcRoleExample.createCriteria()
                .andIdIn(holdingRoleIds);
        return tcRoleMapper.selectByExample(tcRoleExample);
    }

    @Transactional(readOnly = true)
    public List<TcPermission> fetchPermissionList(@Nonnull String accountId) {
        checkNotNull(accountId);

        // find role
        TcAccountMapRoleExample tcAccountMapRoleExample = new TcAccountMapRoleExample();
        tcAccountMapRoleExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<String> holdingRoleIds =
                tcAccountMapRoleMapper.selectPrimaryKeyByExample(tcAccountMapRoleExample);

        // find permission ids by role ids
        List<String> holdingPermissionIdsByRoleIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(holdingRoleIds)) {
            TcRoleMapPermissionExample tcRoleMapPermissionExample = new TcRoleMapPermissionExample();
            tcRoleMapPermissionExample.createCriteria()
                    .andRoleIdIn(holdingRoleIds);
            holdingPermissionIdsByRoleIds =
                    tcRoleMapPermissionMapper.selectPrimaryKeyByExample(tcRoleMapPermissionExample);
        }

        // find permission ids by account id
        TcAccountMapPermissionExample tcAccountMapPermissionExample = new TcAccountMapPermissionExample();
        tcAccountMapPermissionExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<String> holdingPermissionIdsByAccountId =
                tcAccountMapPermissionMapper.selectPrimaryKeyByExample(tcAccountMapPermissionExample);

        // merge permission ids
        holdingPermissionIdsByAccountId.removeAll(holdingPermissionIdsByRoleIds);
        holdingPermissionIdsByAccountId.addAll(holdingPermissionIdsByRoleIds);
        @SuppressWarnings("UnnecessaryLocalVariable")
        List<String> holdingAllPermissionIds = holdingPermissionIdsByAccountId;

        if (CollectionUtils.isEmpty(holdingAllPermissionIds)) {
            return Lists.newArrayList();
        }

        // find all
        TcPermissionExample tcPermissionExample = new TcPermissionExample();
        tcPermissionExample.createCriteria()
                .andIdIn(holdingAllPermissionIds);
        return tcPermissionMapper.selectByExample(tcPermissionExample);
    }

    @Transactional(readOnly = true)
    public List<TcMenu> fetchMenus(@Nonnull String accountId) {
        List<String> tcRoleIds =
                this.fetchRoleList(accountId).stream().map(TcRole::getId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(tcRoleIds)) {
            return Lists.newArrayList();
        }

        TcRoleMapMenuExample tcRoleMapMenuExample = new TcRoleMapMenuExample();
        tcRoleMapMenuExample.createCriteria()
                .andRoleIdIn(tcRoleIds);
        List<String> menuIds = tcRoleMapMenuMapper.selectPrimaryKeyByExample(tcRoleMapMenuExample);

        if (CollectionUtils.isEmpty(menuIds)) {
            return Lists.newArrayList();
        }

        TcMenuExample tcMenuExample = new TcMenuExample();
        tcMenuExample.createCriteria()
                .andIdIn(menuIds);
        return tcMenuMapper.selectByExample(tcMenuExample);
    }

}

package com.ysu.zyw.tc.api.svc.accounts.auth;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.dao.mappers.*;
import com.ysu.zyw.tc.api.dao.po.*;
import com.ysu.zyw.tc.api.fk.ex.TcUnProcessableEntityException;
import com.ysu.zyw.tc.api.svc.accounts.TcAccountService;
import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToMenu;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToPermission;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @Resource
    private TcAccountService tcAccountService;

    /**
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     */
    @Transactional
    public int grantRole(@Nonnull String accountId,
                         @Nonnull List<String> roleIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(roleIdList));

        if (!tcAccountService.existId(accountId)) {
            throw new TcUnProcessableEntityException(1, "账号不存在！");
        }

        Date now = new Date();
        List<TcAccountMapRole> tcAccountMapRoleList = roleIdList
                .parallelStream()
                .map(roleId ->
                        new TcAccountMapRole(
                                TcIdGen.upperCaseUuid(),
                                accountId,
                                roleId,
                                TcConstant.Sys.STR_32x0,
                                now,
                                TcConstant.Sys.STR_32x0,
                                now
                        )
                )
                .collect(Collectors.toList());
        return tcAccountMapRoleMapper.batchInsert(tcAccountMapRoleList);
    }

    /**
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 账号不存在;
     */
    @Transactional
    public int grantPermission(@Nonnull String accountId,
                               @Nonnull List<String> permissionIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(permissionIdList));

        if (!tcAccountService.existId(accountId)) {
            throw new TcUnProcessableEntityException(1, "账号不存在！");
        }

        Date now = new Date();
        List<TcAccountMapPermission> tcAccountMapPermissionList = permissionIdList
                .parallelStream()
                .map(permissionId ->
                        new TcAccountMapPermission(
                                TcIdGen.upperCaseUuid(),
                                accountId,
                                permissionId,
                                TcConstant.Sys.STR_32x0,
                                now,
                                TcConstant.Sys.STR_32x0,
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
    public int revokeAllRole(@Nonnull String accountId) {
        checkNotNull(accountId);
        TcAccountMapRoleExample tcAccountMapRoleExample = new TcAccountMapRoleExample();
        tcAccountMapRoleExample.createCriteria()
                .andAccountIdEqualTo(accountId);
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

    @Transactional
    public int revokeAllPermission(@Nonnull String accountId) {
        checkNotNull(accountId);
        TcAccountMapPermissionExample tcAccountMapPermissionExample = new TcAccountMapPermissionExample();
        tcAccountMapPermissionExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        return tcAccountMapPermissionMapper.deleteByExample(tcAccountMapPermissionExample);
    }

    @Transactional(readOnly = true)
    public List<ToRole> fetchRoleList(@Nonnull String accountId) {
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
        List<TcRole> tcRoles = tcRoleMapper.selectByExample(tcRoleExample);
        return tcRoles.stream().map(this::convert2ToRole).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ToPermission> fetchPermissions(@Nonnull String accountId) {
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
        List<TcPermission> tcPermissions = tcPermissionMapper.selectByExample(tcPermissionExample);
        return tcPermissions.stream().map(this::convert2ToPermission).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ToMenu> fetchMenus(@Nonnull String accountId) {
        List<String> tcRoleIds =
                fetchRoleList(accountId).stream().map(ToRole::getId).collect(Collectors.toList());

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
        List<TcMenu> tcMenus = tcMenuMapper.selectByExample(tcMenuExample);
        List<ToMenu> flatToMenus = tcMenus.stream().map(this::convert2ToMenu).collect(Collectors.toList());

        // group level
        return groupingToMenus(flatToMenus);
    }

    private List<ToMenu> groupingToMenus(List<ToMenu> flatToMenus) {
        List<ToMenu> rootList = Lists.newArrayList();

        // grouping
        flatToMenus.forEach(lToMenu -> {
            flatToMenus.forEach(rToMenu -> {
                if (Objects.equals(lToMenu.getLevel(), 1)) {
                    rootList.add(lToMenu);
                }

                // lToMenu is parent
                if (lToMenu.getLevel() - rToMenu.getLevel() == -1 &&
                        rToMenu.getStructure().contains(lToMenu.getStructure())) {
                    if (Objects.isNull(lToMenu.getSubMenus())) {
                        lToMenu.setSubMenus(Lists.newArrayList());
                    }
                    lToMenu.getSubMenus().add(rToMenu);
                }
            });
        });

        // sort level
        sortRecursive(rootList);

        return rootList;
    }

    private void sortRecursive(List<ToMenu> toMenus) {
        if (Objects.isNull(toMenus)) {
            return;
        }
        toMenus.stream()
                .sorted(Comparator.comparing(ToMenu::getStructure))
                .forEach(toMenu -> sortRecursive(toMenu.getSubMenus()));
    }

    private ToRole convert2ToRole(TcRole tcRole) {
        checkNotNull(tcRole);
        ToRole toRole = new ToRole();
        BeanUtils.copyProperties(tcRole, toRole);
        return toRole;
    }

    private ToMenu convert2ToMenu(TcMenu tcMenu) {
        checkNotNull(tcMenu);
        ToMenu toMenu = new ToMenu();
        BeanUtils.copyProperties(tcMenu, toMenu);
        return toMenu;
    }

    private ToPermission convert2ToPermission(TcPermission tcPermission) {
        checkNotNull(tcPermission);
        ToPermission toPermission = new ToPermission();
        BeanUtils.copyProperties(tcPermission, toPermission);
        return toPermission;
    }

}

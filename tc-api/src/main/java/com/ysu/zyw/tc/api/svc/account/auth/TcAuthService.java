package com.ysu.zyw.tc.api.svc.account.auth;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ysu.zyw.tc.api.dao.mappers.*;
import com.ysu.zyw.tc.api.dao.penum.TcPermissionType;
import com.ysu.zyw.tc.api.dao.po.*;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.model.api.account.auth.TmPermission;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class TcAuthService {

    @Resource
    private TcAccountMapPermissionSetMapper tcAccountMapPermissionSetMapper;

    @Resource
    private TcAccountMapPermissionMapper tcAccountMapPermissionMapper;

    @Resource
    private TcPermissionSetMapPermissionMapper tcPermissionSetMapPermissionMapper;

    @Resource
    private TcPermissionSetMapper tcPermissionSetMapper;

    @Resource
    private TcPermissionMapper tcPermissionMapper;

    public int grantPermissionSet(String accountId, List<String> permissionSetIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(permissionSetIdList));
        Date now = Calendar.getInstance().getTime();
        List<TcAccountMapPermissionSet> accountMapPermissionSetList = permissionSetIdList
                .parallelStream()
                .map(permissionSetId ->
                        new TcAccountMapPermissionSet(
                                TcIdWorker.upperCaseUuid(),
                                accountId,
                                permissionSetId,
                                now,
                                TcConstant.Sys.TC_ADMIN_ID,
                                now,
                                TcConstant.Sys.TC_ADMIN_ID))
                .collect(Collectors.toList());
        return tcAccountMapPermissionSetMapper.batchInsert(accountMapPermissionSetList);
    }

    public int grantPermission(String accountId, List<String> permissionIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(permissionIdList));
        Date now = Calendar.getInstance().getTime();
        List<TcAccountMapPermission> accountMapPermissionList = permissionIdList
                .parallelStream()
                .map(permissionId ->
                        new TcAccountMapPermission(
                                TcIdWorker.upperCaseUuid(),
                                accountId,
                                permissionId,
                                now,
                                TcConstant.Sys.TC_ADMIN_ID,
                                now,
                                TcConstant.Sys.TC_ADMIN_ID))
                .collect(Collectors.toList());
        return tcAccountMapPermissionMapper.batchInsert(accountMapPermissionList);
    }

    public int revokePermissionSet(String accountId, List<String> permissionSetIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(permissionSetIdList));
        TcAccountMapPermissionSetExample tcAccountMapPermissionSetExample = new TcAccountMapPermissionSetExample();
        tcAccountMapPermissionSetExample.createCriteria()
                .andAccountIdEqualTo(accountId)
                .andPermissionSetIdIn(permissionSetIdList);
        return tcAccountMapPermissionSetMapper.deleteByExample(tcAccountMapPermissionSetExample);
    }

    public int revokePermission(String accountId, List<String> permissionIdList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(permissionIdList));
        TcAccountMapPermissionExample tcAccountMapPermissionExample = new TcAccountMapPermissionExample();
        tcAccountMapPermissionExample.createCriteria()
                .andAccountIdEqualTo(accountId)
                .andPermissionIdIn(permissionIdList);
        return tcAccountMapPermissionMapper.deleteByExample(tcAccountMapPermissionExample);
    }

    public List<TcPermissionSet> fetchPermissionSetList(String accountId) {
        checkNotNull(accountId);
        TcAccountMapPermissionSetExample tcAccountMapPermissionSetExample = new TcAccountMapPermissionSetExample();
        tcAccountMapPermissionSetExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<String> holdPermissionSetIdList =
                tcAccountMapPermissionSetMapper.selectByExample(tcAccountMapPermissionSetExample)
                        .parallelStream()
                        .map(TcAccountMapPermissionSet::getPermissionSetId)
                        .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(holdPermissionSetIdList)) {
            return Lists.newArrayList();
        }

        TcPermissionSetExample tcPermissionSetExample = new TcPermissionSetExample();
        tcPermissionSetExample.createCriteria()
                .andIdIn(holdPermissionSetIdList);
        return tcPermissionSetMapper.selectByExample(tcPermissionSetExample);
    }

    public List<TcPermission> fetchPermissionList(String accountId, List<TcPermissionType> permissionTypeList) {
        checkNotNull(accountId);
        checkArgument(CollectionUtils.isNotEmpty(permissionTypeList));

        // find permission set
        TcAccountMapPermissionSetExample tcAccountMapPermissionSetExample = new TcAccountMapPermissionSetExample();
        tcAccountMapPermissionSetExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<String> holdPermissionSetIdList = tcAccountMapPermissionSetMapper.selectPrimaryKeyByExample
                (tcAccountMapPermissionSetExample);

        // find all permission id by permission set
        List<String> holdAllPermissionIdsByPermissionSets = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(holdPermissionSetIdList)) {
            TcPermissionSetMapPermissionExample tcPermissionSetMapPermissionExample =
                    new TcPermissionSetMapPermissionExample();
            tcPermissionSetMapPermissionExample.createCriteria()
                    .andPermissionSetIdIn(holdPermissionSetIdList);
            holdAllPermissionIdsByPermissionSets =
                    tcPermissionSetMapPermissionMapper
                            .selectByExample(tcPermissionSetMapPermissionExample)
                            .parallelStream()
                            .map(TcPermissionSetMapPermission::getPermissionId)
                            .collect(Collectors.toList());
        }

        // find all permission id by account
        TcAccountMapPermissionExample tcAccountMapPermissionExample = new TcAccountMapPermissionExample();
        tcAccountMapPermissionExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<String> holdAllPermissionIdsByAccount =
                tcAccountMapPermissionMapper.selectByExample
                        (tcAccountMapPermissionExample)
                        .parallelStream()
                        .map(TcAccountMapPermission::getId)
                        .collect(Collectors.toList());

        // merge
        HashSet<String> holdAllPermissionIds = Sets.newHashSet();
        holdAllPermissionIds.addAll(holdAllPermissionIdsByAccount);
        holdAllPermissionIds.addAll(holdAllPermissionIdsByPermissionSets);

        if (CollectionUtils.isEmpty(holdAllPermissionIds)) {
            return Lists.newArrayList();
        }

        // filter by permission type
        TcPermissionExample tcPermissionExample = new TcPermissionExample();
        tcPermissionExample.createCriteria()
                .andIdIn(Lists.newArrayList(holdAllPermissionIds))
                .andTypeIn(permissionTypeList);
        return tcPermissionMapper.selectByExample(tcPermissionExample);
    }

    public List<TmPermission> findMenus(String accountId) {
        List<TcPermission> tcPermissions =
                this.fetchPermissionList(accountId, Lists.newArrayList(TcPermissionType.MENU));
        return tcPermissions.stream().map(tcPermission -> {
            TmPermission tmPermission = new TmPermission();
            try {
                BeanUtils.copyProperties(tcPermission, tmPermission);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw Throwables.propagate(e);
            }
            return tmPermission;
        }).collect(Collectors.toList());
    }
}

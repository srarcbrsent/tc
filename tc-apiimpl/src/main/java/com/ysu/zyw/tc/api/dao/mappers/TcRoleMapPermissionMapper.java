package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcRoleMapPermission;
import com.ysu.zyw.tc.api.dao.po.TcRoleMapPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcRoleMapPermissionMapper {
    long countByExample(TcRoleMapPermissionExample example);

    int deleteByExample(TcRoleMapPermissionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcRoleMapPermission record);

    int insertSelective(TcRoleMapPermission record);

    List<TcRoleMapPermission> selectByExample(TcRoleMapPermissionExample example);

    TcRoleMapPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcRoleMapPermission record, @Param("example") TcRoleMapPermissionExample example);

    int updateByExample(@Param("record") TcRoleMapPermission record, @Param("example") TcRoleMapPermissionExample example);

    int updateByPrimaryKeySelective(TcRoleMapPermission record);

    int updateByPrimaryKey(TcRoleMapPermission record);

    List<String> selectPrimaryKeyByExample(TcRoleMapPermissionExample example);

    int batchInsert(List<TcRoleMapPermission> records);
}
package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcPermissionSetMapPermission;
import com.ysu.zyw.tc.api.dao.po.TcPermissionSetMapPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcPermissionSetMapPermissionMapper {
    long countByExample(TcPermissionSetMapPermissionExample example);

    int deleteByExample(TcPermissionSetMapPermissionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcPermissionSetMapPermission record);

    int insertSelective(TcPermissionSetMapPermission record);

    List<TcPermissionSetMapPermission> selectByExample(TcPermissionSetMapPermissionExample example);

    TcPermissionSetMapPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcPermissionSetMapPermission record, @Param("example")
            TcPermissionSetMapPermissionExample example);

    int updateByExample(@Param("record") TcPermissionSetMapPermission record, @Param("example")
            TcPermissionSetMapPermissionExample example);

    int updateByPrimaryKeySelective(TcPermissionSetMapPermission record);

    int updateByPrimaryKey(TcPermissionSetMapPermission record);

    List<String> selectPrimaryKeyByExample(TcPermissionSetMapPermissionExample example);

    int batchInsert(List<TcPermissionSetMapPermission> records);
}
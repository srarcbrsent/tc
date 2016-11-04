package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcAccountMapPermission;
import com.ysu.zyw.tc.api.dao.po.TcAccountMapPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcAccountMapPermissionMapper {
    long countByExample(TcAccountMapPermissionExample example);

    int deleteByExample(TcAccountMapPermissionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcAccountMapPermission record);

    int insertSelective(TcAccountMapPermission record);

    List<TcAccountMapPermission> selectByExample(TcAccountMapPermissionExample example);

    TcAccountMapPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcAccountMapPermission record, @Param("example") TcAccountMapPermissionExample example);

    int updateByExample(@Param("record") TcAccountMapPermission record, @Param("example") TcAccountMapPermissionExample example);

    int updateByPrimaryKeySelective(TcAccountMapPermission record);

    int updateByPrimaryKey(TcAccountMapPermission record);

    List<String> selectPrimaryKeyByExample(TcAccountMapPermissionExample example);

    int batchInsert(List<TcAccountMapPermission> records);
}
package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcAccountMapPermissionSet;
import com.ysu.zyw.tc.api.dao.po.TcAccountMapPermissionSetExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcAccountMapPermissionSetMapper {
    long countByExample(TcAccountMapPermissionSetExample example);

    int deleteByExample(TcAccountMapPermissionSetExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcAccountMapPermissionSet record);

    int insertSelective(TcAccountMapPermissionSet record);

    List<TcAccountMapPermissionSet> selectByExample(TcAccountMapPermissionSetExample example);

    TcAccountMapPermissionSet selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcAccountMapPermissionSet record, @Param("example")
            TcAccountMapPermissionSetExample example);

    int updateByExample(@Param("record") TcAccountMapPermissionSet record, @Param("example")
            TcAccountMapPermissionSetExample example);

    int updateByPrimaryKeySelective(TcAccountMapPermissionSet record);

    int updateByPrimaryKey(TcAccountMapPermissionSet record);

    List<String> selectPrimaryKeyByExample(TcAccountMapPermissionSetExample example);

    int batchInsert(List<TcAccountMapPermissionSet> records);
}
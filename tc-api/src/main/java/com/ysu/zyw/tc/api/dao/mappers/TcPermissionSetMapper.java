package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcPermissionSet;
import com.ysu.zyw.tc.api.dao.po.TcPermissionSetExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcPermissionSetMapper {
    long countByExample(TcPermissionSetExample example);

    int deleteByExample(TcPermissionSetExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcPermissionSet record);

    int insertSelective(TcPermissionSet record);

    List<TcPermissionSet> selectByExample(TcPermissionSetExample example);

    TcPermissionSet selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcPermissionSet record, @Param("example") TcPermissionSetExample
            example);

    int updateByExample(@Param("record") TcPermissionSet record, @Param("example") TcPermissionSetExample example);

    int updateByPrimaryKeySelective(TcPermissionSet record);

    int updateByPrimaryKey(TcPermissionSet record);

    List<String> selectPrimaryKeyByExample(TcPermissionSetExample example);

    int batchInsert(List<TcPermissionSet> records);
}
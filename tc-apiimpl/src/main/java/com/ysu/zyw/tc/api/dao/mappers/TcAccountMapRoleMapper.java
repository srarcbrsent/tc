package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcAccountMapRole;
import com.ysu.zyw.tc.api.dao.po.TcAccountMapRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcAccountMapRoleMapper {
    long countByExample(TcAccountMapRoleExample example);

    int deleteByExample(TcAccountMapRoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcAccountMapRole record);

    int insertSelective(TcAccountMapRole record);

    List<TcAccountMapRole> selectByExample(TcAccountMapRoleExample example);

    TcAccountMapRole selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcAccountMapRole record, @Param("example") TcAccountMapRoleExample example);

    int updateByExample(@Param("record") TcAccountMapRole record, @Param("example") TcAccountMapRoleExample example);

    int updateByPrimaryKeySelective(TcAccountMapRole record);

    int updateByPrimaryKey(TcAccountMapRole record);

    List<String> selectPrimaryKeyByExample(TcAccountMapRoleExample example);

    int batchInsert(List<TcAccountMapRole> records);
}
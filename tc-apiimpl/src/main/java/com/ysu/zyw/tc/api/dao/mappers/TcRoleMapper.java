package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcRole;
import com.ysu.zyw.tc.api.dao.po.TcRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcRoleMapper {
    long countByExample(TcRoleExample example);

    int deleteByExample(TcRoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcRole record);

    int insertSelective(TcRole record);

    List<TcRole> selectByExample(TcRoleExample example);

    TcRole selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcRole record, @Param("example") TcRoleExample example);

    int updateByExample(@Param("record") TcRole record, @Param("example") TcRoleExample example);

    int updateByPrimaryKeySelective(TcRole record);

    int updateByPrimaryKey(TcRole record);

    List<String> selectPrimaryKeyByExample(TcRoleExample example);

    int batchInsert(List<TcRole> records);
}
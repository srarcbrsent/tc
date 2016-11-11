package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcMenu;
import com.ysu.zyw.tc.api.dao.po.TcMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcMenuMapper {
    long countByExample(TcMenuExample example);

    int deleteByExample(TcMenuExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcMenu record);

    int insertSelective(TcMenu record);

    List<TcMenu> selectByExample(TcMenuExample example);

    TcMenu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcMenu record, @Param("example") TcMenuExample example);

    int updateByExample(@Param("record") TcMenu record, @Param("example") TcMenuExample example);

    int updateByPrimaryKeySelective(TcMenu record);

    int updateByPrimaryKey(TcMenu record);

    List<String> selectPrimaryKeyByExample(TcMenuExample example);

    int batchInsert(List<TcMenu> records);
}
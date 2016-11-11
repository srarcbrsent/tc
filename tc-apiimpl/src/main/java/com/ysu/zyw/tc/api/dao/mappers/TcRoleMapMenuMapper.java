package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcRoleMapMenu;
import com.ysu.zyw.tc.api.dao.po.TcRoleMapMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcRoleMapMenuMapper {
    long countByExample(TcRoleMapMenuExample example);

    int deleteByExample(TcRoleMapMenuExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcRoleMapMenu record);

    int insertSelective(TcRoleMapMenu record);

    List<TcRoleMapMenu> selectByExample(TcRoleMapMenuExample example);

    TcRoleMapMenu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcRoleMapMenu record, @Param("example") TcRoleMapMenuExample example);

    int updateByExample(@Param("record") TcRoleMapMenu record, @Param("example") TcRoleMapMenuExample example);

    int updateByPrimaryKeySelective(TcRoleMapMenu record);

    int updateByPrimaryKey(TcRoleMapMenu record);

    List<String> selectPrimaryKeyByExample(TcRoleMapMenuExample example);

    int batchInsert(List<TcRoleMapMenu> records);
}
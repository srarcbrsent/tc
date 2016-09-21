package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcPermission;
import com.ysu.zyw.tc.api.dao.po.TcPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcPermissionMapper {
    long countByExample(TcPermissionExample example);

    int deleteByExample(TcPermissionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcPermission record);

    int insertSelective(TcPermission record);

    List<TcPermission> selectByExample(TcPermissionExample example);

    TcPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcPermission record, @Param("example") TcPermissionExample example);

    int updateByExample(@Param("record") TcPermission record, @Param("example") TcPermissionExample example);

    int updateByPrimaryKeySelective(TcPermission record);

    int updateByPrimaryKey(TcPermission record);

    List<String> selectPrimaryKeyByExample(TcPermissionExample example);

    int batchInsert(List<TcPermission> records);
}
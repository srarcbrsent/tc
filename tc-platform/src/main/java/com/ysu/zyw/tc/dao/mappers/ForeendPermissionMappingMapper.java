package com.ysu.zyw.tc.dao.mappers;

import com.ysu.zyw.tc.dao.po.ForeendPermissionMapping;
import com.ysu.zyw.tc.dao.po.ForeendPermissionMappingExample;
import com.ysu.zyw.tc.dao.po.ForeendPermissionMappingKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ForeendPermissionMappingMapper {
    long countByExample(ForeendPermissionMappingExample example);

    int deleteByExample(ForeendPermissionMappingExample example);

    int deleteByPrimaryKey(ForeendPermissionMappingKey key);

    int insert(ForeendPermissionMapping record);

    int insertSelective(ForeendPermissionMapping record);

    List<ForeendPermissionMapping> selectByExample(ForeendPermissionMappingExample example);

    ForeendPermissionMapping selectByPrimaryKey(ForeendPermissionMappingKey key);

    int updateByExampleSelective(@Param("record") ForeendPermissionMapping record, @Param("example")
            ForeendPermissionMappingExample example);

    int updateByExample(@Param("record") ForeendPermissionMapping record, @Param("example")
            ForeendPermissionMappingExample example);

    int updateByPrimaryKeySelective(ForeendPermissionMapping record);

    int updateByPrimaryKey(ForeendPermissionMapping record);

    List<ForeendPermissionMappingKey> selectPrimaryKeyByExample(ForeendPermissionMappingExample example);

    int batchInsert(List<ForeendPermissionMapping> records);
}
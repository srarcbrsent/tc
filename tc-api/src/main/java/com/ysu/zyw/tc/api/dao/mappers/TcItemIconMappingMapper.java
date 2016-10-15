package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItemIconMapping;
import com.ysu.zyw.tc.api.dao.po.TcItemIconMappingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemIconMappingMapper {
    long countByExample(TcItemIconMappingExample example);

    int deleteByExample(TcItemIconMappingExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItemIconMapping record);

    int insertSelective(TcItemIconMapping record);

    List<TcItemIconMapping> selectByExample(TcItemIconMappingExample example);

    TcItemIconMapping selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItemIconMapping record, @Param("example") TcItemIconMappingExample example);

    int updateByExample(@Param("record") TcItemIconMapping record, @Param("example") TcItemIconMappingExample example);

    int updateByPrimaryKeySelective(TcItemIconMapping record);

    int updateByPrimaryKey(TcItemIconMapping record);

    List<String> selectPrimaryKeyByExample(TcItemIconMappingExample example);

    int batchInsert(List<TcItemIconMapping> records);
}
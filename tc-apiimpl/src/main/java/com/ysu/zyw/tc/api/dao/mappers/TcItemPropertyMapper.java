package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItemPropertyExample;
import com.ysu.zyw.tc.api.dao.po.TcItemProperty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemPropertyMapper {
    long countByExample(TcItemPropertyExample example);

    int deleteByExample(TcItemPropertyExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItemProperty record);

    int insertSelective(TcItemProperty record);

    List<TcItemProperty> selectByExample(TcItemPropertyExample example);

    TcItemProperty selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItemProperty record, @Param("example") TcItemPropertyExample example);

    int updateByExample(@Param("record") TcItemProperty record, @Param("example") TcItemPropertyExample example);

    int updateByPrimaryKeySelective(TcItemProperty record);

    int updateByPrimaryKey(TcItemProperty record);

    List<String> selectPrimaryKeyByExample(TcItemPropertyExample example);

    int batchInsert(List<TcItemProperty> records);
}
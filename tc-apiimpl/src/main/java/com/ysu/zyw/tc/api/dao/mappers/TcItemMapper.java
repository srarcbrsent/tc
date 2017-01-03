package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItem;
import com.ysu.zyw.tc.api.dao.po.TcItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemMapper {
    long countByExample(TcItemExample example);

    int deleteByExample(TcItemExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItem record);

    int insertSelective(TcItem record);

    List<TcItem> selectByExample(TcItemExample example);

    TcItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItem record, @Param("example") TcItemExample example);

    int updateByExample(@Param("record") TcItem record, @Param("example") TcItemExample example);

    int updateByPrimaryKeySelective(TcItem record);

    int updateByPrimaryKey(TcItem record);

    List<String> selectPrimaryKeyByExample(TcItemExample example);

    int batchInsert(List<TcItem> records);
}
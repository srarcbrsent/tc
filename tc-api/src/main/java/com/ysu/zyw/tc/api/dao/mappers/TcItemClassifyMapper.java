package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItemClassify;
import com.ysu.zyw.tc.api.dao.po.TcItemClassifyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemClassifyMapper {
    long countByExample(TcItemClassifyExample example);

    int deleteByExample(TcItemClassifyExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItemClassify record);

    int insertSelective(TcItemClassify record);

    List<TcItemClassify> selectByExample(TcItemClassifyExample example);

    TcItemClassify selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItemClassify record, @Param("example") TcItemClassifyExample example);

    int updateByExample(@Param("record") TcItemClassify record, @Param("example") TcItemClassifyExample example);

    int updateByPrimaryKeySelective(TcItemClassify record);

    int updateByPrimaryKey(TcItemClassify record);

    List<String> selectPrimaryKeyByExample(TcItemClassifyExample example);

    int batchInsert(List<TcItemClassify> records);
}
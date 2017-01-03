package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItemCover;
import com.ysu.zyw.tc.api.dao.po.TcItemCoverExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemCoverMapper {
    long countByExample(TcItemCoverExample example);

    int deleteByExample(TcItemCoverExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItemCover record);

    int insertSelective(TcItemCover record);

    List<TcItemCover> selectByExample(TcItemCoverExample example);

    TcItemCover selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItemCover record, @Param("example") TcItemCoverExample example);

    int updateByExample(@Param("record") TcItemCover record, @Param("example") TcItemCoverExample example);

    int updateByPrimaryKeySelective(TcItemCover record);

    int updateByPrimaryKey(TcItemCover record);

    List<String> selectPrimaryKeyByExample(TcItemCoverExample example);

    int batchInsert(List<TcItemCover> records);
}
package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItemDetail;
import com.ysu.zyw.tc.api.dao.po.TcItemDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemDetailMapper {
    long countByExample(TcItemDetailExample example);

    int deleteByExample(TcItemDetailExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItemDetail record);

    int insertSelective(TcItemDetail record);

    List<TcItemDetail> selectByExample(TcItemDetailExample example);

    TcItemDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItemDetail record, @Param("example") TcItemDetailExample example);

    int updateByExample(@Param("record") TcItemDetail record, @Param("example") TcItemDetailExample example);

    int updateByPrimaryKeySelective(TcItemDetail record);

    int updateByPrimaryKey(TcItemDetail record);

    List<String> selectPrimaryKeyByExample(TcItemDetailExample example);

    int batchInsert(List<TcItemDetail> records);
}
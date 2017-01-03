package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcShop;
import com.ysu.zyw.tc.api.dao.po.TcShopExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcShopMapper {
    long countByExample(TcShopExample example);

    int deleteByExample(TcShopExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcShop record);

    int insertSelective(TcShop record);

    List<TcShop> selectByExample(TcShopExample example);

    TcShop selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcShop record, @Param("example") TcShopExample example);

    int updateByExample(@Param("record") TcShop record, @Param("example") TcShopExample example);

    int updateByPrimaryKeySelective(TcShop record);

    int updateByPrimaryKey(TcShop record);

    List<String> selectPrimaryKeyByExample(TcShopExample example);

    int batchInsert(List<TcShop> records);
}
package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItemIcon;
import com.ysu.zyw.tc.api.dao.po.TcItemIconExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemIconMapper {
    long countByExample(TcItemIconExample example);

    int deleteByExample(TcItemIconExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItemIcon record);

    int insertSelective(TcItemIcon record);

    List<TcItemIcon> selectByExample(TcItemIconExample example);

    TcItemIcon selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItemIcon record, @Param("example") TcItemIconExample example);

    int updateByExample(@Param("record") TcItemIcon record, @Param("example") TcItemIconExample example);

    int updateByPrimaryKeySelective(TcItemIcon record);

    int updateByPrimaryKey(TcItemIcon record);

    List<String> selectPrimaryKeyByExample(TcItemIconExample example);

    int batchInsert(List<TcItemIcon> records);
}
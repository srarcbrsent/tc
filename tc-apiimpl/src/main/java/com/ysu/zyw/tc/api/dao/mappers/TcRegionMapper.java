package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcRegion;
import com.ysu.zyw.tc.api.dao.po.TcRegionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcRegionMapper {
    long countByExample(TcRegionExample example);

    int deleteByExample(TcRegionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcRegion record);

    int insertSelective(TcRegion record);

    List<TcRegion> selectByExample(TcRegionExample example);

    TcRegion selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcRegion record, @Param("example") TcRegionExample example);

    int updateByExample(@Param("record") TcRegion record, @Param("example") TcRegionExample example);

    int updateByPrimaryKeySelective(TcRegion record);

    int updateByPrimaryKey(TcRegion record);

    List<String> selectPrimaryKeyByExample(TcRegionExample example);

    int batchInsert(List<TcRegion> records);
}
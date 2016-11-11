package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcBehaviorTrace;
import com.ysu.zyw.tc.api.dao.po.TcBehaviorTraceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcBehaviorTraceMapper {
    long countByExample(TcBehaviorTraceExample example);

    int deleteByExample(TcBehaviorTraceExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcBehaviorTrace record);

    int insertSelective(TcBehaviorTrace record);

    List<TcBehaviorTrace> selectByExample(TcBehaviorTraceExample example);

    TcBehaviorTrace selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcBehaviorTrace record, @Param("example") TcBehaviorTraceExample example);

    int updateByExample(@Param("record") TcBehaviorTrace record, @Param("example") TcBehaviorTraceExample example);

    int updateByPrimaryKeySelective(TcBehaviorTrace record);

    int updateByPrimaryKey(TcBehaviorTrace record);

    List<String> selectPrimaryKeyByExample(TcBehaviorTraceExample example);

    int batchInsert(List<TcBehaviorTrace> records);
}
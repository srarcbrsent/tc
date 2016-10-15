package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcAccountAssist;
import com.ysu.zyw.tc.api.dao.po.TcAccountAssistExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcAccountAssistMapper {
    long countByExample(TcAccountAssistExample example);

    int deleteByExample(TcAccountAssistExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcAccountAssist record);

    int insertSelective(TcAccountAssist record);

    List<TcAccountAssist> selectByExample(TcAccountAssistExample example);

    TcAccountAssist selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcAccountAssist record, @Param("example") TcAccountAssistExample example);

    int updateByExample(@Param("record") TcAccountAssist record, @Param("example") TcAccountAssistExample example);

    int updateByPrimaryKeySelective(TcAccountAssist record);

    int updateByPrimaryKey(TcAccountAssist record);

    List<String> selectPrimaryKeyByExample(TcAccountAssistExample example);

    int batchInsert(List<TcAccountAssist> records);
}
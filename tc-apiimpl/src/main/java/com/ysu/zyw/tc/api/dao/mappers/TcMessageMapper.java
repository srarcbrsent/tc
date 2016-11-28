package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcMessage;
import com.ysu.zyw.tc.api.dao.po.TcMessageExample;
import com.ysu.zyw.tc.api.dao.po.TcMessageWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcMessageMapper {
    long countByExample(TcMessageExample example);

    int deleteByExample(TcMessageExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcMessageWithBLOBs record);

    int insertSelective(TcMessageWithBLOBs record);

    List<TcMessageWithBLOBs> selectByExampleWithBLOBs(TcMessageExample example);

    List<TcMessage> selectByExample(TcMessageExample example);

    TcMessageWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcMessageWithBLOBs record, @Param("example") TcMessageExample example);

    int updateByExampleWithBLOBs(@Param("record") TcMessageWithBLOBs record, @Param("example") TcMessageExample example);

    int updateByExample(@Param("record") TcMessage record, @Param("example") TcMessageExample example);

    int updateByPrimaryKeySelective(TcMessageWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TcMessageWithBLOBs record);

    int updateByPrimaryKey(TcMessage record);

    List<String> selectPrimaryKeyByExample(TcMessageExample example);

    int batchInsert(List<TcMessageWithBLOBs> records);
}
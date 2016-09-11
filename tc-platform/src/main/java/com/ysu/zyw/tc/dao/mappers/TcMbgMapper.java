package com.ysu.zyw.tc.dao.mappers;

import com.ysu.zyw.tc.dao.po.TcMbg;
import com.ysu.zyw.tc.dao.po.TcMbgExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcMbgMapper {
    long countByExample(TcMbgExample example);

    int deleteByExample(TcMbgExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcMbg record);

    int insertSelective(TcMbg record);

    List<TcMbg> selectByExample(TcMbgExample example);

    TcMbg selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcMbg record, @Param("example") TcMbgExample example);

    int updateByExample(@Param("record") TcMbg record, @Param("example") TcMbgExample example);

    int updateByPrimaryKeySelective(TcMbg record);

    int updateByPrimaryKey(TcMbg record);

    List<String> selectPrimaryKeyByExample(TcMbgExample example);

    int batchInsert(List<TcMbg> records);
}
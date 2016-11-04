package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcAccountExample;
import com.ysu.zyw.tc.api.dao.po.TcAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcAccountMapper {
    long countByExample(TcAccountExample example);

    int deleteByExample(TcAccountExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcAccount record);

    int insertSelective(TcAccount record);

    List<TcAccount> selectByExample(TcAccountExample example);

    TcAccount selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcAccount record, @Param("example") TcAccountExample example);

    int updateByExample(@Param("record") TcAccount record, @Param("example") TcAccountExample example);

    int updateByPrimaryKeySelective(TcAccount record);

    int updateByPrimaryKey(TcAccount record);

    List<String> selectPrimaryKeyByExample(TcAccountExample example);

    int batchInsert(List<TcAccount> records);
}
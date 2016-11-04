package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcAccountPayment;
import com.ysu.zyw.tc.api.dao.po.TcAccountPaymentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcAccountPaymentMapper {
    long countByExample(TcAccountPaymentExample example);

    int deleteByExample(TcAccountPaymentExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcAccountPayment record);

    int insertSelective(TcAccountPayment record);

    List<TcAccountPayment> selectByExample(TcAccountPaymentExample example);

    TcAccountPayment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcAccountPayment record, @Param("example") TcAccountPaymentExample example);

    int updateByExample(@Param("record") TcAccountPayment record, @Param("example") TcAccountPaymentExample example);

    int updateByPrimaryKeySelective(TcAccountPayment record);

    int updateByPrimaryKey(TcAccountPayment record);

    List<String> selectPrimaryKeyByExample(TcAccountPaymentExample example);

    int batchInsert(List<TcAccountPayment> records);
}
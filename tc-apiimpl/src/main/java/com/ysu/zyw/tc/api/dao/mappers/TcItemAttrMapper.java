package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItemAttr;
import com.ysu.zyw.tc.api.dao.po.TcItemAttrExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemAttrMapper {
    long countByExample(TcItemAttrExample example);

    int deleteByExample(TcItemAttrExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItemAttr record);

    int insertSelective(TcItemAttr record);

    List<TcItemAttr> selectByExample(TcItemAttrExample example);

    TcItemAttr selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItemAttr record, @Param("example") TcItemAttrExample example);

    int updateByExample(@Param("record") TcItemAttr record, @Param("example") TcItemAttrExample example);

    int updateByPrimaryKeySelective(TcItemAttr record);

    int updateByPrimaryKey(TcItemAttr record);

    List<String> selectPrimaryKeyByExample(TcItemAttrExample example);

    int batchInsert(List<TcItemAttr> records);
}
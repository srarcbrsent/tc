package com.ysu.zyw.tc.api.dao.mappers;

import com.ysu.zyw.tc.api.dao.po.TcItemPic;
import com.ysu.zyw.tc.api.dao.po.TcItemPicExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcItemPicMapper {
    long countByExample(TcItemPicExample example);

    int deleteByExample(TcItemPicExample example);

    int deleteByPrimaryKey(String id);

    int insert(TcItemPic record);

    int insertSelective(TcItemPic record);

    List<TcItemPic> selectByExample(TcItemPicExample example);

    TcItemPic selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TcItemPic record, @Param("example") TcItemPicExample example);

    int updateByExample(@Param("record") TcItemPic record, @Param("example") TcItemPicExample example);

    int updateByPrimaryKeySelective(TcItemPic record);

    int updateByPrimaryKey(TcItemPic record);

    List<String> selectPrimaryKeyByExample(TcItemPicExample example);

    int batchInsert(List<TcItemPic> records);
}
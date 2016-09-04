package com.ysu.zyw.tc.dao.mappers;

import com.ysu.zyw.tc.dao.po.Uc;
import com.ysu.zyw.tc.dao.po.UcExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UcMapper {
    long countByExample(UcExample example);

    int deleteByExample(UcExample example);

    int deleteByPrimaryKey(String id);

    int insert(Uc record);

    int insertSelective(Uc record);

    List<Uc> selectByExample(UcExample example);

    int updateByExampleSelective(@Param("record") Uc record, @Param("example") UcExample example);

    int updateByExample(@Param("record") Uc record, @Param("example") UcExample example);

    List<String> selectPrimaryKeyByExample(UcExample example);

    int batchInsert(List<Uc> records);
}
package com.ysu.zyw.tc.dao.mappers;

import com.ysu.zyw.tc.dao.po.Member;
import com.ysu.zyw.tc.dao.po.MemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {
    long countByExample(MemberExample example);

    int deleteByExample(MemberExample example);

    int deleteByPrimaryKey(String id);

    int insert(Member record);

    int insertSelective(Member record);

    List<Member> selectByExampleWithBLOBs(MemberExample example);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByExampleWithBLOBs(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKeyWithBLOBs(Member record);

    int updateByPrimaryKey(Member record);

    List<String> selectPrimaryKeyByExample(MemberExample example);

    int batchInsert(List<Member> records);
}
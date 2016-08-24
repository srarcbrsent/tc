package com.ysu.zyw.tc.component.mbg.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.ysu.zyw.tc.dao.mappers.ForeendPermissionMappingMapper;
import com.ysu.zyw.tc.dao.mappers.MemberMapper;
import com.ysu.zyw.tc.dao.po.ForeendPermissionMappingKey;
import com.ysu.zyw.tc.dao.po.Member;
import com.ysu.zyw.tc.dao.po.MemberExample;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class MbgTest {

    private DruidDataSource dataSource;

    private SqlSessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/zr_core_db");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        Configuration configuration = new Configuration();
        configuration.setEnvironment(new Environment("main", new JdbcTransactionFactory(), dataSource));
        List<String> resources = Lists.newArrayList(
                "mappers/ForeendPermissionMappingMapper.xml",
                "mappers/MemberMapper.xml");
        resources.forEach(resource -> {
            XMLMapperBuilder mapperParser = null;
            try {
                mapperParser = new XMLMapperBuilder(new ClassPathResource(resource).getInputStream(), configuration,
                        resource,
                        configuration
                                .getSqlFragments());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mapperParser != null) {
                mapperParser.parse();
            }
        });

        sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @After
    public void tearDown() throws Exception {
        dataSource.close();
    }

    @Test
    public void testPagination() {
        invokeWithSqlSession(sqlSession -> {
            MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);

            pagination(memberMapper, 1);
            pagination(memberMapper, 2);
            pagination(memberMapper, 3);
            pagination(memberMapper, 4);

            MemberExample memberExample1 = new MemberExample();
            memberExample1.setStartLine(0);
            memberExample1.setPageSize(2);
            List<Member> members1 = memberMapper.selectByExample(memberExample1);

            MemberExample memberExample2 = new MemberExample();
            memberExample2.setStartLine(1);
            memberExample2.setPageSize(1);
            List<Member> members2 = memberMapper.selectByExample(memberExample2);

            Assert.assertEquals(members1.get(1), members2.get(0));
        });
    }

    private void pagination(MemberMapper memberMapper, int pageSize) {
        MemberExample memberExample = new MemberExample();
        memberExample.setStartLine(0);
        memberExample.setPageSize(pageSize);
        List<Member> members = memberMapper.selectByExample(memberExample);
        Assert.assertTrue(members.size() == pageSize);
    }

    @Test
    public void testSelectPrimaryKey() {
        invokeWithSqlSession(sqlSession -> {
            MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);

            List<String> pk1 = memberMapper.selectPrimaryKeyByExample(null);
            System.out.println(pk1);

            MemberExample memberExample = new MemberExample();
            memberExample.setStartLine(1);
            memberExample.setPageSize(Integer.MAX_VALUE);
            List<String> pk2 = memberMapper.selectPrimaryKeyByExample(memberExample);

            Assert.assertEquals(pk1.get(1), pk2.get(0));
            Assert.assertTrue(pk1.size() == (pk2.size() + 1));

            ForeendPermissionMappingMapper foreendPermissionMappingMapper = sqlSession.getMapper
                    (ForeendPermissionMappingMapper.class);

            List<ForeendPermissionMappingKey> pk3 = foreendPermissionMappingMapper
                    .selectPrimaryKeyByExample(null);
            System.out.println(pk3);
        });
    }

    @Test
    public void testBatchInsert() {
        invokeWithSqlSession(sqlSession -> {
            MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);

            List<Member> memberList = Lists.newArrayList();
            for (int i = 0; i < 300; i++) {
                Member member = new Member();
                member.setUsername(UUID.randomUUID().toString().replace("-", "").substring(0, 9));
                member.setPassword(UUID.randomUUID().toString().replace("-", ""));
                member.setAvatar(UUID.randomUUID().toString().replace("-", ""));
                member.setEmail(UUID.randomUUID().toString().replace("-", ""));
                member.setId(UUID.randomUUID().toString().replace("-", ""));
                member.setLastLoginTime(new Date());
                member.setNickname(UUID.randomUUID().toString().replace("-", "").substring(0, 9));
                member.setSignupTime(new Date());
                memberList.add(member);
            }

            memberMapper.batchInsert(memberList);
        });
    }

    private void invokeWithSqlSession(Consumer<SqlSession> consumer) {
        SqlSession sqlSession = sessionFactory.openSession();
        consumer.accept(sqlSession);
        sqlSession.commit();
    }

}
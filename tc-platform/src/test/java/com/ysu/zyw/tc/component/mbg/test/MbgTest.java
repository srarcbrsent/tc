package com.ysu.zyw.tc.component.mbg.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tools.IdWorker;
import com.ysu.zyw.tc.dao.mappers.TcMbgMapper;
import com.ysu.zyw.tc.dao.po.TcMbg;
import com.ysu.zyw.tc.dao.po.TcMbgExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Slf4j
public class MbgTest {

    private DruidDataSource dataSource;

    private SqlSessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/tc_platform");
        Properties properties = new Properties();
        properties.put("useUnicode", Boolean.TRUE.toString());
        properties.put("characterEncoding", "UTF-8");
        properties.put("zeroDateTimeBehavior", "convertToNull");
        properties.put("useSSL", Boolean.FALSE.toString());
        dataSource.setConnectProperties(properties);
        dataSource.setUsername("tc_develop");
        dataSource.setPassword("123456");

        Configuration configuration = new Configuration();
        configuration.setEnvironment(new Environment("main", new JdbcTransactionFactory(), dataSource));
        List<String> resources = Lists.newArrayList(
                "mappers/TcMbgMapper.xml"
        );
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
    public void testInsert() {
        invokeWithSqlSession(sqlSession -> {
            TcMbgMapper tcMbgMapper = sqlSession.getMapper(TcMbgMapper.class);
            IntStream.range(0, 500).parallel().forEach(i -> {
                tcMbgMapper.insert(new TcMbg(
                        IdWorker.upperCaseUuid(),
                        IdWorker.upperCaseUuid(),
                        false,
                        LocalDateTime.of(
                                RandomUtils.nextInt(5000),
                                RandomUtils.nextInt(12) + 1,
                                RandomUtils.nextInt(28) + 1,
                                RandomUtils.nextInt(24),
                                RandomUtils.nextInt(60),
                                RandomUtils.nextInt(60)
                        ),
                        LocalDate.of(
                                RandomUtils.nextInt(5000),
                                RandomUtils.nextInt(12) + 1,
                                RandomUtils.nextInt(28) + 1
                        ),
                        LocalTime.of(
                                RandomUtils.nextInt(24),
                                RandomUtils.nextInt(60),
                                RandomUtils.nextInt(60)
                        ))
                );
            });
        });
    }

    @Test
    public void testSelectPrimaryKey() {
        invokeWithSqlSession(sqlSession -> {
            TcMbgMapper tcMbgMapper = sqlSession.getMapper(TcMbgMapper.class);
            long total = tcMbgMapper.countByExample(null);
            log.info("total [{}]", total);

            TcMbgExample tcMbgExample = new TcMbgExample();
            tcMbgExample.createCriteria().andBirthdayGreaterThan(LocalDateTime.now());
            long l = tcMbgMapper.countByExample(tcMbgExample);
            log.info("not valid [{}]", l);

            List<String> apks = tcMbgMapper.selectPrimaryKeyByExample(null);
            System.out.println(apks);

            List<String> lpks = tcMbgMapper.selectPrimaryKeyByExample(tcMbgExample);
            System.out.println(lpks);

            Collection subtract = CollectionUtils.subtract(apks, lpks);
            log.info("valid [{}]", subtract.size());
        });
    }

    @Test
    public void testBatchInsert() {
        invokeWithSqlSession(sqlSession -> {
            List<TcMbg> tcMbgList = Lists.newArrayList();
            IntStream.range(0, 50).parallel().forEach(i -> {
                tcMbgList.add(new TcMbg(
                        IdWorker.upperCaseUuid(),
                        IdWorker.upperCaseUuid(),
                        false,
                        LocalDateTime.of(
                                RandomUtils.nextInt(5000),
                                RandomUtils.nextInt(12) + 1,
                                RandomUtils.nextInt(28) + 1,
                                RandomUtils.nextInt(24),
                                RandomUtils.nextInt(60),
                                RandomUtils.nextInt(60)
                        ),
                        LocalDate.of(
                                RandomUtils.nextInt(5000),
                                RandomUtils.nextInt(12) + 1,
                                RandomUtils.nextInt(28) + 1
                        ),
                        LocalTime.of(
                                RandomUtils.nextInt(24),
                                RandomUtils.nextInt(60),
                                RandomUtils.nextInt(60)
                        )));
            });

            TcMbgMapper tcMbgMapper = sqlSession.getMapper(TcMbgMapper.class);
            long totalBefore = tcMbgMapper.countByExample(null);

            tcMbgMapper.batchInsert(tcMbgList);

            log.info("total before [{}], insert count [{}]", totalBefore, tcMbgList.size());
        });
        invokeWithSqlSession(sqlSession -> {
            TcMbgMapper tcMbgMapper = sqlSession.getMapper(TcMbgMapper.class);
            long totalAfter = tcMbgMapper.countByExample(null);

            log.info("total after [{}]", totalAfter);
        });
    }

    private void invokeWithSqlSession(Consumer<SqlSession> consumer) {
        SqlSession sqlSession = sessionFactory.openSession();
        consumer.accept(sqlSession);
        sqlSession.commit();
    }

}
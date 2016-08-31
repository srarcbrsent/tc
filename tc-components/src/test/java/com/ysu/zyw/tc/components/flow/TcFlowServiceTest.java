package com.ysu.zyw.tc.components.flow;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tool.IdWorker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-flow.xml",
        "classpath*:config/tc-spring-data-mongodb.xml",
        "classpath*:config/tc-spring-import-data-mongodb.xml"
})
public class TcFlowServiceTest {

    @Resource
    private TcFlowService tcFlowService;

    @Resource
    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void demo() {
        for (int i = 0; i < 5000; i++) {
            TcFlow<TcLeaveApplication> tcLeaveApplicationTcFlow = new TcFlow<>();
            tcLeaveApplicationTcFlow.setId(null);
            tcLeaveApplicationTcFlow.setFlowId(IdWorker.upperCaseUuid());
            tcLeaveApplicationTcFlow.setFlowType("leave");
            tcLeaveApplicationTcFlow.setFlowState("05");
            tcLeaveApplicationTcFlow.setFlowData(new TcLeaveApplication(IdWorker.upperCaseUuid(), "yaowu.zhang"));
            tcLeaveApplicationTcFlow.setFlowCandidateAssigneeList(Lists.newArrayList("yaowu.zhang.1", "yaowu.zhang.2"));
            tcLeaveApplicationTcFlow.setFlowCandidateRoleList(Lists.newArrayList("admin", "guest"));
            tcLeaveApplicationTcFlow.setBizKey(IdWorker.upperCaseUuid());
            tcLeaveApplicationTcFlow.setCreatedPerson(IdWorker.upperCaseUuid());
            tcLeaveApplicationTcFlow.setCreatedTimestamp(new Date());
            tcLeaveApplicationTcFlow.setUpdatedPerson(IdWorker.upperCaseUuid());
            tcLeaveApplicationTcFlow.setUpdatedTimestamp(new Date());

            mongoTemplate.insert(tcLeaveApplicationTcFlow);
        }
    }

    @Test
    public void executeFlow() throws Exception {
    }

    @Test
    public void updateFlowData() throws Exception {

    }

    @Test
    public void findFlowWithFilter() throws Exception {

    }

}
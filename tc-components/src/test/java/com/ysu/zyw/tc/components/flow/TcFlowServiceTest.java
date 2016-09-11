package com.ysu.zyw.tc.components.flow;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tools.IdWorker;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-module-flow.xml",
        "classpath*:config/tc-spring-data-mongodb.xml",
        "classpath*:config/tc-spring-import-mongodb.xml"
})
public class TcFlowServiceTest {

    @Resource
    private TcFlowService tcFlowService;

    @Resource
    private MongoTemplate mongoTemplate;

    // 1 -> 2 -> 99
    private static final String LEAVE_APPLICATION = "leave_application";

    // 1 -> 3 -> 4 -> 99
    private static final String SALARY_RAISE_APPLICATION = "salary_raise_application";

    // member and role

    private static final String USER_1 = "5950ea3d-aa64-4234-b9fe-d4ac55caf048";

    private static final String USER_2 = "546ec93a-093c-47d4-a603-0caaa40599e8";

    private static final String ROLE_1 = "6b90af92-eea0-4fc2-9c0d-427514ec674e";

    private static final String ROLE_2 = "8a44fa05-2c7c-4188-9fa4-7c0ad13bc6e8";

    // flow state

    private static final String STATE_01 = "01";

    private static final String STATE_02 = "02";

    private static final String STATE_03 = "03";

    private static final String STATE_04 = "04";

    private static final String STATE_99 = "99";


    @Before
    public void setUp() throws Exception {
        // clear mongo collection
        mongoTemplate.dropCollection(TcFlow.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testExecuteFlow1() throws Exception {

        assertCountFlowWithFilter(USER_1, Lists.newArrayList(), 1);
        assertCountFlowWithFilter(USER_2, Lists.newArrayList(), 1);
        assertCountFlowWithFilter(null, Lists.newArrayList(ROLE_1), 1);
        assertCountFlowWithFilter(null, Lists.newArrayList(ROLE_2), 1);
    }

    @Test
    public void testUpdateFlowData() throws Exception {

    }

    @Test
    public void testExistsFlow() {
        String flowId = IdWorker.upperCaseUuid();
        String bizKey = IdWorker.upperCaseUuid();
        TcFlow<TcLeaveApplication> tcLeaveApplicationTcFlow = new TcFlow<TcLeaveApplication>()
                .setId(null)
                .setFlowId(flowId)
                .setFlowType(LEAVE_APPLICATION)
                .setFlowState("05")
                .setFlowData(new TcLeaveApplication(bizKey, "yaowu.zhang", RandomUtils
                        .nextInt(5) + 1, new Date()))
                .setFlowCandidateAssigneeList(Lists.newArrayList())
                .setFlowCandidateRoleList(Lists.newArrayList())
                .setFlowBizKey(bizKey)
                .setCreatedPerson(IdWorker.upperCaseUuid())
                .setCreatedTimestamp(new Date())
                .setUpdatedPerson(IdWorker.upperCaseUuid())
                .setUpdatedTimestamp(new Date());

        mongoTemplate.insert(tcLeaveApplicationTcFlow);

        boolean flowExist = tcFlowService.existFlow(flowId);
        Assert.assertTrue(flowExist);

        Assert.assertFalse(tcFlowService.existFlow(IdWorker.upperCaseUuid()));
    }

    private void assertCountFlowWithFilter(String currUser, List<String> currRole, long expectCount) {
    }

    private TcFlow<TcLeaveApplication> buildTcLeaveApplicationFlow(String who,
                                                                   List<String> assigneeList,
                                                                   List<String> roleList,
                                                                   String state) {
        String bizKey = IdWorker.upperCaseUuid();
        return new TcFlow<TcLeaveApplication>()
                .setId(null)
                .setFlowId(IdWorker.upperCaseUuid())
                .setFlowType(LEAVE_APPLICATION)
                .setFlowState(state)
                .setFlowData(new TcLeaveApplication(bizKey, who, RandomUtils
                        .nextInt(5) + 1, new Date()))
                .setFlowCandidateAssigneeList(assigneeList)
                .setFlowCandidateRoleList(roleList)
                .setFlowBizKey(bizKey)
                .setCreatedPerson(IdWorker.upperCaseUuid())
                .setCreatedTimestamp(new Date())
                .setUpdatedPerson(IdWorker.upperCaseUuid())
                .setUpdatedTimestamp(new Date());
    }

    private TcFlow<TcSalaryRaiseApplication> buildTcSalaryRaiseApplicationFlow(String who,
                                                                               List<String> assigneeList,
                                                                               List<String> roleList,
                                                                               String state) {
        String bizKey = IdWorker.upperCaseUuid();
        return new TcFlow<TcSalaryRaiseApplication>()
                .setId(null)
                .setFlowId(IdWorker.upperCaseUuid())
                .setFlowType(SALARY_RAISE_APPLICATION)
                .setFlowState(state)
                .setFlowData(new TcSalaryRaiseApplication(IdWorker.upperCaseUuid(), who,
                        RandomUtils.nextInt(3000) + 3000L, RandomUtils.nextInt(3000) + 1000L))
                .setFlowCandidateAssigneeList(assigneeList)
                .setFlowCandidateRoleList(roleList)
                .setFlowBizKey(bizKey)
                .setCreatedPerson(IdWorker.upperCaseUuid())
                .setCreatedTimestamp(new Date())
                .setUpdatedPerson(IdWorker.upperCaseUuid())
                .setUpdatedTimestamp(new Date());
    }

    private TcFlowLog buildTcFlowLog(String log) {
        return new TcFlowLog()
                .setFlowLog(log);
    }

}
package com.ysu.zyw.tc.components.flow;

import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcFlowService {

    @Resource
    private MongoTemplate mongoTemplate;

    // or strategy

    public <T> void executeFlow(@Nonnull TcFlow<T> tcFlow,
                                @Nonnull TcFlowLog tcFlowLog) {
        // verify flow
        assertFlowLegal(tcFlow, tcFlowLog);

        // update flow
        if (flowExists(tcFlow.getFlowId())) {
            updateFlow(tcFlow);
        } else {
            createFlow(tcFlow);
        }

        // append log
        appendFlowLog(tcFlow, tcFlowLog);
    }

    public <T> void updateFlowData(@Nonnull String flowId,
                                   @Nonnull T flowData) {
        if (!flowExists(flowId)) {
            throw new TcException("flow doesn't exists", flowId);
        }
        // update data TODO
    }

    public List<TcFlow<?>> findFlowWithFilter(@Nullable String assignee,
                                              @Nullable List<String> roleList,
                                              @Nullable MultiValueMap mongoFilter) {
        // search TODO
        return null;
    }

    private <T> void assertFlowLegal(TcFlow<T> tcFlow, TcFlowLog tcFlowLog) {
        checkNotNull(tcFlow, "null flow is not allowed");
        checkNotNull(tcFlowLog, "null flow log is not allowed");

        // tcFlow
        checkNotNull(tcFlow.getFlowId(), "null flow id is not allowed");
        checkNotNull(tcFlow.getFlowType(), "null flow type is not allowed");
        checkNotNull(tcFlow.getFlowData(), "null flow data is not allowed");
        checkNotNull(tcFlow.getFlowState(), "null flow state is not allowed");

        boolean hasFlowCandidateRole = CollectionUtils.isNotEmpty(tcFlow.getFlowCandidateRoleList());
        boolean hasFLowCandidateAssignee = CollectionUtils.isNotEmpty(tcFlow.getFlowCandidateAssigneeList());
        checkArgument(hasFlowCandidateRole || hasFLowCandidateAssignee,
                "one of candidate role or assignee is required");

        checkArgument(CollectionUtils.isEmpty(tcFlow.getFlowLogList()), "flow log list not allowed here");

        checkNotNull(tcFlow.getUpdatedPerson(), "null updated person is not allowed");

        // tcFlowLog
        checkNotNull(tcFlowLog.getFlowLog(), "null flow log is not allowed");
    }

    private boolean flowExists(String flowId) {
        // search mongodb TODO
        return false;
    }

    private <T> void createFlow(TcFlow<T> tcFlow) {
        buildRequiredField(tcFlow);

        // build now
        tcFlow.setCreatedTimestamp(new Date());
        tcFlow.setUpdatedTimestamp(new Date());

        // insert TODO
    }

    private <T> void updateFlow(TcFlow<T> tcFlow) {
        removeNonRenewableField(tcFlow);

        // build now
        tcFlow.setUpdatedTimestamp(new Date());

        // update TODO
    }

    private <T> void buildRequiredField(TcFlow<T> tcFlow) {
        // id will build by mongodb
        // copy updated person to created person
        tcFlow.setCreatedPerson(tcFlow.getUpdatedPerson());
    }

    private <T> void removeNonRenewableField(TcFlow<T> tcFlow) {
        // id can't renew but we need it as primary key
        // flow type can't renew
        tcFlow.setFlowType(null);
        // flow data can't renew (call another open api to update)
        tcFlow.setFlowData(null);
        // created person can't renew
        tcFlow.setCreatedPerson(null);
        // created timestamp can't renew
        tcFlow.setCreatedTimestamp(null);
    }

    private <T> void appendFlowLog(TcFlow<T> tcFlow, TcFlowLog tcFlowLog) {
        tcFlowLog.setFlowId(tcFlow.getFlowId());
        tcFlowLog.setCreatedPerson(tcFlow.getUpdatedPerson());
        tcFlowLog.setCreatedTimestamp(tcFlow.getUpdatedTimestamp());

        // append log TODO
    }

}

package com.ysu.zyw.tc.components.flow;

import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcFlowService {

    private static final String STATE_INITIAL = "00";

    private static final String STATE_FINISH = "99";

    @Resource
    private MongoTemplate mongoTemplate;

    public <T> void initialFlow(@Nonnull TcFlow<T> tcFlow) {
        // verify argument
        checkNotNull(tcFlow, "null flow is not allowed");

        checkNotNull(tcFlow.getFlowId(), "null flow id is not allowed");
        checkNotNull(tcFlow.getFlowType(), "null flow type is not allowed");
        checkNotNull(tcFlow.getFlowData(), "null flow data is not allowed");
        checkNotNull(tcFlow.getFlowState(), "null flow state is not allowed");
        checkNotNull(tcFlow.getFlowBizKey(), "null flow biz key is not allowed");

        boolean hasFlowCandidateRole = CollectionUtils.isNotEmpty(tcFlow.getFlowCandidateRoleList());
        boolean hasFlowCandidateAssignee = CollectionUtils.isNotEmpty(tcFlow.getFlowCandidateAssigneeList());
        checkArgument(!hasFlowCandidateRole && !hasFlowCandidateAssignee,
                "candidate role or assignee is not allowed here");

        checkArgument(CollectionUtils.isEmpty(tcFlow.getFlowLogList()), "flow log list not allowed here");

        checkNotNull(tcFlow.getUpdatedPerson(), "null updated person is not allowed");
        checkNotNull(tcFlow.getCreatedPerson(), "null created person is not allowed");

        Date now = new Date();
        tcFlow
                .setFlowState(STATE_INITIAL)
                .setUpdatedTimestamp(now)
                .setCreatedTimestamp(now);

        mongoTemplate.insert(tcFlow);
    }

    public void executeFlow(@Nonnull String flowId,
                            @Nonnull String flowState,
                            @Nonnull List<String> flowCandidateRoleList,
                            @Nonnull List<String> flowCandidateAssigneeList,
                            @Nonnull String flowLog,
                            @Nonnull String performer) {
        checkNotNull(flowId, "null flow id is not allowed");
        checkNotNull(flowState, "null flow state is not allowed");
        checkNotNull(flowLog, "null flow log is not allowed");
        checkNotNull(performer, "null operation performer is not allowed");
        boolean hasFlowCandidateRole = CollectionUtils.isNotEmpty(flowCandidateRoleList);
        boolean hasFLowCandidateAssignee = CollectionUtils.isNotEmpty(flowCandidateAssigneeList);
        checkArgument(hasFlowCandidateRole || hasFLowCandidateAssignee,
                "one of candidate role or assignee is required");

        if (!existFlow(flowId)) {
            throw new TcException("flow doesn't exists", flowId);
        }

        Date now = new Date();


        Update updateTcFlowQuery = new Update()
                .addToSet("flowState", flowState)
                .addToSet("flowCandidateRoleList", flowCandidateRoleList)
                .addToSet("flowCandidateAssigneeList", flowCandidateAssigneeList)
                .addToSet("updatedPerson", performer)
                .addToSet("updatedTimestamp", now);

        mongoTemplate.updateFirst(new Query(Criteria.where("flowId").is(flowId)), updateTcFlowQuery, TcFlow.class);

        TcFlowLog tcFlowLog = new TcFlowLog()
                .setFlowId(flowId)
                .setFlowLog(flowLog)
                .setCreatedPerson(performer)
                .setCreatedTimestamp(now);

        mongoTemplate.insert(tcFlowLog);
    }

    public <T> void updateFlowData(@Nonnull String flowId,
                                   @Nonnull T flowData,
                                   @Nonnull Class<T> clazz,
                                   @Nonnull String performer) {
        checkNotNull(flowId, "null flow id is not allowed");
        checkNotNull(flowData, "null flow data is not allowed");
        checkNotNull(clazz, "null clazz is not allowed");
        checkNotNull(performer, "null operation performer is not allowed");

        if (!existFlow(flowId)) {
            throw new TcException("flow doesn't exists", flowId);
        }

        mongoTemplate.updateFirst(
                new Query(Criteria.where("flowId").is(flowId)),
                Update.update("flowData", flowData),
                clazz);
    }

    public boolean existFlow(@Nonnull String flowId) {
        checkNotNull(flowId, "null flow id is not allowed");

        return mongoTemplate.exists(new Query(Criteria.where("flowId").is(flowId)), TcFlow.class);
    }

    public <T> TcFlow<T> findFlow(@Nonnull String flowId,
                                  @Nonnull Class<T> clazz) {
        checkNotNull(flowId, "null flow id is not allowed");
        checkNotNull(clazz, "null clazz is not allowed");

        //noinspection unchecked
        return mongoTemplate.findOne(new Query(Criteria.where("flowId").is(flowId)), TcFlow.class);
    }

    public long countFlowWithFilter(@Nullable String assignee,
                                    @Nullable List<String> roleList,
                                    @Nullable Query query) {
        // TODO
        return 1;
    }

    public List<TcFlow<?>> findFlowWithFilter(@Nullable String assignee,
                                              @Nullable List<String> roleList,
                                              @Nullable Query query) {
        // TODO
        return null;
    }

}

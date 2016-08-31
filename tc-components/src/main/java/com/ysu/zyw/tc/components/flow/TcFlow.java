package com.ysu.zyw.tc.components.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tcFlow")
public class TcFlow<T> implements Serializable {

    @Id
    private String id;

    private String flowId;

    private String flowType;

    private String flowState;

    private String bizKey;

    private List<String> flowCandidateRoleList;

    private List<String> flowCandidateAssigneeList;

    private T flowData;

    private List<TcFlowLog> flowLogList;

    private String createdPerson;

    private Date createdTimestamp;

    private String updatedPerson;

    private Date updatedTimestamp;

}

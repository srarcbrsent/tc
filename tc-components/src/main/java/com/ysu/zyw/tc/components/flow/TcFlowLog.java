package com.ysu.zyw.tc.components.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TcFlowLog implements Serializable {

    private String flowId;

    private String flowLog;

    private String createdPerson;

    private Date createdTimestamp;

}

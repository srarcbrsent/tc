package com.ysu.zyw.tc.components.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tcFlowLog")
public class TcFlowLog implements Serializable {

    private String id;

    @Version
    private String version;

    private String flowId;

    private String flowLog;

    private String createdPerson;

    private Date createdTimestamp;

}

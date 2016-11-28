package com.ysu.zyw.tc.api.dao.po;

import com.ysu.zyw.tc.model.menum.TmMessageType;
import com.ysu.zyw.tc.model.menum.TmPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcMessage {
    private String id;

    private String receiverAccountId;

    private String receiverRegionId;

    private TmMessageType type;

    private TmPlatform platform;

    private Boolean read;

    private String bizKey;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;
}
package com.ysu.zyw.tc.model.api.o.im;

import com.ysu.zyw.tc.model.menum.TmMessageType;
import com.ysu.zyw.tc.model.menum.TmPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ToMessage implements Serializable {

    private static final long serialVersionUID = -7749323895963440772L;

    private String id;

    private String receiverAccountId;

    private String receiverRegionId;

    private TmMessageType type;

    private TmPlatform platform;

    private Boolean read;

    private String bizKey;

    private Date createdTimestamp;

}
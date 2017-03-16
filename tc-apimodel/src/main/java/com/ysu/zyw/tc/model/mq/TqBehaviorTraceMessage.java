package com.ysu.zyw.tc.model.mq;

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
public class TqBehaviorTraceMessage implements Serializable {

    private static final long serialVersionUID = -521313271991322610L;

    public static final int ACTION_TYPE_SIGNUP = 1;

    public static final int ACTION_TYPE_SIGNIN = 2;

    public static final int ACTION_TYPE_SIGNOUT = 3;

    private String accountId;

    private TmPlatform tmPlatform;

    private int actionType;

    private String msg;

    private Date doneTime;

}

package com.ysu.zyw.tc.components.websocket.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.security.Principal;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class TcWebSocketAgent implements Principal {

    protected boolean authenticated = false;

    protected String httpSessionId;

    protected String accountId;

    @Override
    public String getName() {
        return httpSessionId;
    }

}

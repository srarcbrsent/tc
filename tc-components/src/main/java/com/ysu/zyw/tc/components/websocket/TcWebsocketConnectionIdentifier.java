package com.ysu.zyw.tc.components.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcWebsocketConnectionIdentifier {

    private String httpSessionId;

    private String websocketSessionId;

    private boolean signin;

    private String accountId;

    private WebSocketSession webSocketSession;

    private WebSocketHandler webSocketHandler;

}

package com.ysu.zyw.tc.components.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TcWebSocketSessionManager {

    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void onConnected(WebSocketSession webSocketSession) {

    }

    public void onClosed(WebSocketSession webSocketSession) {

    }

    public WebSocketSession find() {
        return null;
    }

}

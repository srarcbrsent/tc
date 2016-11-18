package com.ysu.zyw.tc.platform.fk.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

@Slf4j
public class TcWebSocketListener implements ApplicationListener<AbstractSubProtocolEvent> {

    @Override
    public void onApplicationEvent(AbstractSubProtocolEvent event) {
        String name = event.toString();
        if (log.isDebugEnabled()) {
            log.debug("websocket session state changed, [{}]", name);
        }
    }

}

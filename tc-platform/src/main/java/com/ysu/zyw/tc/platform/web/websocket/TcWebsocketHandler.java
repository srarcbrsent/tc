package com.ysu.zyw.tc.platform.web.websocket;

import com.ysu.zyw.tc.model.mw.TcR;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping(value = "/websocket")
public class TcWebsocketHandler {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping(value = "/speak")
    @SendTo(value = "/dtp/chartroom")
    public String speak(TcR<String> msg) {
        System.out.println(msg.getBody());
        return new Date().toString();
    }

    @MessageMapping(value = "/speakTo")
    public void speakTo(TcR<String> msg) {
        System.out.println(msg.getBody());
        messagingTemplate.convertAndSendToUser("123123", "/chart", TcR.ok("helo world"));
    }

}

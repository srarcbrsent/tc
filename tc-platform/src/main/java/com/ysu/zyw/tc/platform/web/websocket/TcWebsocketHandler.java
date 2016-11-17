package com.ysu.zyw.tc.platform.web.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping(value = "/websocket")
public class TcWebsocketHandler {

    @RequestMapping(value = "/demo.html")
    public String demoPage(HttpServletRequest request) {
        return "/WEB-INF/templates/common/websocket.ftl";
    }

    @MessageMapping(value = "/handle")
    @SendTo(value = "/topic/greetings")
    public String handle(String msg) {
        System.out.println(msg);
        return new Date().toString();
    }

}

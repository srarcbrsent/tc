package com.ysu.zyw.tc.platform.web.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping(value = "/websocket")
public class TcWebsocketHandler {

    @RequestMapping(value = "/demo.html")
    public String demoPage(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        System.out.println(userPrincipal);
        return "/WEB-INF/templates/common/websocket.ftl";
    }

    @MessageMapping(value = "/tcendpoint.ws")
    public String handle(String msg) {
        System.out.println(msg);
        return new Date().toString();
    }

}

package com.ysu.zyw.tc.web.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping(value = "/websocket")
@MessageMapping(value = "tcws")
public class TcWebsocketController {

    @RequestMapping(value = "/demo.html")
    public String demoPage(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        System.out.println(userPrincipal);
        return "/WEB-INF/templates/common/websocket.ftl";
    }

    @MessageMapping(value = "handle")
    public void handle(String msg) {
        System.out.println(msg);
    }

}

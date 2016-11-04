package com.ysu.zyw.tc.web.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/websocket")
public class TcWebsocketController {

    @RequestMapping(value = "/demo.html")
    public String demoPage(HttpServletRequest request) {
        System.out.println(request.getSession().getId());

        return "/WEB-INF/templates/common/websocket.ftl";
    }

}

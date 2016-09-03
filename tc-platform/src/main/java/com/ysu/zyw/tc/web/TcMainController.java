package com.ysu.zyw.tc.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TcMainController {

    @RequestMapping(value = "/")
    public ModelAndView idx() {
        return index();
    }

    @RequestMapping(value = "index.html")
    public ModelAndView index() {
        return new ModelAndView("/WEB-INF/templates/index.ftl");
    }

    @RequestMapping(value = "/not_found.html")
    public ModelAndView resourceNotFoundPage() {
        // TODO
        return new ModelAndView("1");
    }

    @RequestMapping(value = "server_error.html")
    public ModelAndView internalServerErrorPage() {
        // TODO
        return new ModelAndView("2");
    }

    @RequestMapping(value = "/not_found.json")
    public ResponseEntity<?> resourceNotFoundJson() {
        // TODO
        return null;
    }

    @RequestMapping(value = "server_error.json")
    public ResponseEntity<?> internalServerErrorJson() {
        // TODO
        return null;
    }

}

package com.ysu.zyw.tc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TcMainController {

    @RequestMapping(value = "/")
    public ModelAndView idx() {
        return index();
    }

    @RequestMapping(value = "index")
    public ModelAndView index() {
        return new ModelAndView("/WEB-INF/templates/index.ftl");
    }

    @RequestMapping(value = "/not_found")
    public ModelAndView resourceNotFoundPage() {
        // TODO
        return new ModelAndView("1");
    }

    @RequestMapping(value = "server_error")
    public ModelAndView internalServerErrorPage() {
        // TODO
        return new ModelAndView("2");
    }

}

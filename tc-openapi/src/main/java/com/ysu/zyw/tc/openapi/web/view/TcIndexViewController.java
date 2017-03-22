package com.ysu.zyw.tc.openapi.web.view;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Api(value = "核心控制器")
@Controller

public class TcIndexViewController {

    @ApiOperation(
            value = "首页",
            notes = "首页")
    @RequestMapping(value = "/")
    public ModelAndView idx() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return new ModelAndView(new RedirectView("/home"));
        }
        return new ModelAndView("index");
    }

    @ApiOperation(
            value = "首页",
            notes = "首页")
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        System.out.println(SecurityUtils.getSubject().getSession().getId());
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return new ModelAndView(new RedirectView("/home"));
        }
        return new ModelAndView("index");
    }

    @ApiOperation(
            value = "主页",
            notes = "主页")
    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

}

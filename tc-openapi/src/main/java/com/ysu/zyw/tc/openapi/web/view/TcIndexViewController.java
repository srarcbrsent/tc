package com.ysu.zyw.tc.openapi.web.view;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Api(value = "核心控制器")
@Controller

public class TcIndexViewController {

    @ApiOperation(
            value = "首页",
            notes = "首页")
    @RequestMapping(value = "/")
    public ModelAndView idx() {
        return new ModelAndView("index");
    }

    @ApiOperation(
            value = "首页",
            notes = "首页")
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @ApiOperation(
            value = "主页",
            notes = "主页")
    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @ApiOperation(
            value = "未授权页",
            notes = "跳转到未授权页")
    @RequestMapping(value = "/unauthorized")
    public ModelAndView unauthorized() {
        // TODO
        return new ModelAndView("hll");
    }

}

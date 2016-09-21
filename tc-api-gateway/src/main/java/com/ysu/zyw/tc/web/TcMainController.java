package com.ysu.zyw.tc.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Api(value = "核心控制器")
@Controller
public class TcMainController {

    @ApiOperation(
            value = "首页",
            notes = "跳转到首页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/")
    public ModelAndView idx() {
        return index();
    }

    @ApiOperation(
            value = "首页",
            notes = "跳转到首页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @ApiModelProperty
    @RequestMapping(value = "index.html")
    public ModelAndView index() {
        return new ModelAndView("/WEB-INF/templates/index.ftl");
    }

    @ApiOperation(
            value = "404错误页",
            notes = "跳转到404错误页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/not_found.html")
    public ModelAndView resourceNotFoundPage() {
        // TODO
        return new ModelAndView("1");
    }

    @ApiOperation(
            value = "500错误页",
            notes = "跳转到500错误页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "server_error.html")
    public ModelAndView internalServerErrorPage() {
        // TODO
        return new ModelAndView("2");
    }

    @ApiOperation(
            value = "404错误页",
            notes = "跳转到404错误页",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/not_found.json")
    public ResponseEntity<?> resourceNotFoundJson() {
        // TODO
        return null;
    }

    @ApiOperation(
            value = "500错误页",
            notes = "跳转到500错误页",
            response = ModelAndView.class,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "server_error.json")
    public ResponseEntity<?> internalServerErrorJson() {
        // TODO
        return null;
    }

}

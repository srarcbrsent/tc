package com.ysu.zyw.tc.platform.web;

import com.ysu.zyw.tc.platform.svc.TcVerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;

@Api(value = "核心控制器")
@Controller
public class TcMainController {

    @Resource
    private TcVerificationCodeService tcVerificationCodeService;

    @ApiOperation(
            value = "首页",
            notes = "跳转到首页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/")
    public ModelAndView idx() {
        // must redirect, because / is not take cared by shiro, only *.html
        return new ModelAndView(new RedirectView("/index.html"));
    }

    @ApiOperation(
            value = "首页",
            notes = "跳转到首页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return new ModelAndView(new RedirectView("/home.html"));
        }
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/templates/index.ftl");
        modelAndView.addObject("verificationCode", tcVerificationCodeService.generateVerificationCodeAndSet2Session());
        return modelAndView;
    }

    @ApiOperation(
            value = "主页",
            notes = "跳转到主页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return new ModelAndView("/WEB-INF/templates/home.ftl");
    }

    @ApiOperation(
            value = "404错误页",
            notes = "跳转到404错误页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/h_not_found")
    public ModelAndView resourceNotFoundPage() {
        // TODO
        return new ModelAndView("1");
    }

    @ApiOperation(
            value = "500错误页",
            notes = "跳转到500错误页",
            produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/h_server_error")
    public ModelAndView internalServerErrorPage() {
        // TODO
        return new ModelAndView("2");
    }

    @ApiOperation(
            value = "404错误页",
            notes = "跳转到404错误页",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/j_not_found")
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
    @RequestMapping(value = "/j_server_error")
    public ResponseEntity<?> internalServerErrorJson() {
        // TODO
        return null;
    }

}

package com.ysu.zyw.tc.platform.web.upload;

import com.ysu.zyw.tc.model.mw.TcR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/upload")
@Api(value = "上传控制器")
@Slf4j
public class TcUploadController {

    @ApiOperation(
            value = "上传",
            notes = "跳转到上传页")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "")
    public ModelAndView upload() {
        return new ModelAndView("/WEB-INF/templates/upload.ftl");
    }

    @ApiOperation(
            value = "上传",
            notes = "上传")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<TcR<Void>> upload(HttpServletRequest request) {
        return ResponseEntity.ok(TcR.ok());
    }

}

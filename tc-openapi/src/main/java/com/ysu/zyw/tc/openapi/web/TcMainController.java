package com.ysu.zyw.tc.openapi.web;

import com.ysu.zyw.tc.model.mw.TcR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "核心控制器")
@Controller
public class TcMainController {

    // the server 404 & 500 page is static page in webapp.

    @ApiOperation(
            value = "404错误页",
            notes = "跳转到404错误页")
    @RequestMapping(value = "/not_found")
    public ResponseEntity<?> resourceNotFoundJson() {
        return ResponseEntity.ok(TcR.errs());
    }

    @ApiOperation(
            value = "500错误页",
            notes = "跳转到500错误页")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/server_error")
    public ResponseEntity<?> internalServerErrorJson() {
        return ResponseEntity.ok(TcR.errs());
    }

}

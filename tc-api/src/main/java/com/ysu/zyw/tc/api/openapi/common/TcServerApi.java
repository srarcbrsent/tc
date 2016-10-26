package com.ysu.zyw.tc.api.openapi.common;

import com.ysu.zyw.tc.model.c.TcR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping(value = "/server")
@Api(value = "服务器信息控制器")
@Slf4j
public class TcServerApi {

    @ApiOperation(
            value = "查询服务器状态",
            notes = "查询服务器状态",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_health_state", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Void, Void>> findHealthState() {
        return ResponseEntity.ok(TcR.ok());
    }

    @ApiOperation(
            value = "查询服务器当前时间",
            notes = "查询服务器当前时间",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_current_time", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<Date, Void>> findCurrentTime() {
        return ResponseEntity.ok(TcR.ok(new Date()));
    }

}

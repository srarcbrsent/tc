package com.ysu.zyw.tc.openapi.web.api.common;

import com.ysu.zyw.tc.model.mw.TcR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping(value = "/servers")
@Api(value = "服务器信息控制器")
@Slf4j
public class TcServerController {

    @ApiOperation(
            value = "查询服务器状态",
            notes = "查询服务器状态")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_health_state", method = RequestMethod.GET,
            headers = "X-ApiVersion=1.0&X-ApiKey=state")
    public ResponseEntity<TcR<Void>> findHealthState() {
        return ResponseEntity.ok(TcR.ok());
    }

    @ApiOperation(
            value = "查询服务器当前时间",
            notes = "查询服务器当前时间")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_current_time", method = RequestMethod.GET,
            headers = "X-ApiVersion=1.0&X-ApiKey=time")
    public ResponseEntity<TcR<Date>> findCurrentTime() {
        return ResponseEntity.ok(TcR.ok(new Date()));
    }

}

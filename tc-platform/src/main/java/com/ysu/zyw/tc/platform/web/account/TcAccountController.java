package com.ysu.zyw.tc.platform.web.account;

import com.ysu.zyw.tc.api.api.TcAccountApi;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.mw.TcR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Slf4j
@Api(value = "认证控制器")
@Controller
@RequestMapping(value = "/accounts")
public class TcAccountController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private TcAccountApi tcAccountApi;

    @ApiOperation(
            value = "获取登陆用一次性token",
            notes = "获取登陆用一次性token",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/create_account", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> createAccount(TiAccount tiAccount) {

        tcAccountApi.createAccount(tiAccount);

        return ResponseEntity.ok(TcR.ok(null));
    }

}

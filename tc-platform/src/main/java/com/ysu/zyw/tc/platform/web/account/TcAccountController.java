package com.ysu.zyw.tc.platform.web.account;

import com.ysu.zyw.tc.api.api.TcAccountApi;
import com.ysu.zyw.tc.model.api.i.accounts.TiAccount;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.model.validator.mode.TcC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Slf4j
@Api(value = "账号控制器")
@Controller
@RequestMapping(value = "/accounts")
public class TcAccountController {

    @Resource
    private TcAccountApi tcAccountApi;

    @ApiOperation(
            value = "创建账号页面",
            notes = "创建账号页面")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/signin")
    public ModelAndView signin() {
        return new ModelAndView("/WEB-INF/templates/accounts/signin.ftl");
    }

    @ApiOperation(
            value = "创建账号",
            notes = "创建账号")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "/create_account", method = RequestMethod.POST)
    public ResponseEntity<TcR<String>> createAccount(
            @RequestBody @Validated(value = TcC.class) TiAccount tiAccount) {

        TcR<String> tcR = tcAccountApi.createAccount(tiAccount);

        return ResponseEntity.ok(TcR.ok(null));
    }

}

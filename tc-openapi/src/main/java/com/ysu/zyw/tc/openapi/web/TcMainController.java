package com.ysu.zyw.tc.openapi.web;

import com.ysu.zyw.tc.model.mw.Rc;
import com.ysu.zyw.tc.model.mw.TcR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "核心控制器")
@Controller
public class TcMainController {

    // only this 7777 code requires api, because shiro's interceptor is higher level
    // than spring's aspect(all other exception is handled by spring aspect), and
    // when an AuthorizationException has been thrown, shiro will redirect to this.
    @ApiOperation(
            value = "未授权页",
            notes = "跳转到未授权页")
    @RequestMapping(value = "/unauthorized")
    public ResponseEntity<?> unauthorizedJson() {
        return ResponseEntity.ok(TcR.code(Rc.UNAUTHORIZED, Rc.UNAUTHORIZED_DESCRIPTION));
    }

}

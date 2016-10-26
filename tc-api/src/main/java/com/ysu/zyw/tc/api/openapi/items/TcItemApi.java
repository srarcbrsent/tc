package com.ysu.zyw.tc.api.openapi.items;

import com.ysu.zyw.tc.model.api.accounts.TmAccount;
import com.ysu.zyw.tc.model.c.TcP;
import com.ysu.zyw.tc.model.c.TcR;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/items")
@Api(value = "商品控制器")
@Slf4j
public class TcItemApi {

    @ApiOperation(
            value = "创建商品",
            notes = "创建商品",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @RequestMapping(value = "/create_item", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<String, Void>> createItem() {

        // TODO

        return ResponseEntity.ok(TcR.ok(null));
    }

    @ApiOperation(
            value = "改变商品状态",
            notes = "改变商品状态",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @RequestMapping(value = "/change_item_state/{id}", method = RequestMethod.POST, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcP<List<TmAccount>, Void>> changeItemState() {

        return ResponseEntity.ok(TcP.ok(null));
    }

    @ApiOperation(
            value = "查询商品/搜索引擎",
            notes = "查询商品/搜索引擎",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @RequestMapping(value = "/find_item/{id}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcP<List<TmAccount>, Void>> findItem() {

        return ResponseEntity.ok(TcP.ok(null));
    }

    @ApiOperation(
            value = "查询商品列表/搜索引擎",
            notes = "查询商品列表/搜索引擎",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @RequestMapping(value = "/find_items", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcP<List<TmAccount>, Void>> findItems() {

        return ResponseEntity.ok(TcP.ok(null));
    }

}

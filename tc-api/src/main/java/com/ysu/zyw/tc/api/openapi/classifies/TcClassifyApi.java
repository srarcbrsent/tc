package com.ysu.zyw.tc.api.openapi.classifies;

import com.ysu.zyw.tc.api.svc.classifies.TcClassifyService;
import com.ysu.zyw.tc.components.cache.codis.TcCodisService;
import com.ysu.zyw.tc.model.api.classifies.TmClassify;
import com.ysu.zyw.tc.model.c.TcR;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/classifies")
@Api(value = "类目控制器")
@Slf4j
public class TcClassifyApi {

    private static final long GLOABL_CLASSIFY_CACHE_TIMEOUT = 30000;

    @Resource
    private TcClassifyService tcClassifyService;

    @Resource
    private TcCodisService tcCodisService;

    @ApiOperation(
            value = "查询类目",
            notes = "查询类目",
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
    @RequestMapping(value = "/find_classify/{id}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<TmClassify, Void>> findClassify(
            @ApiParam(value = "分类id") @PathVariable(value = "id") String classifyId,
            @ApiParam(value = "包含父分类") @RequestParam(value = "includeParentClassify", defaultValue = "false")
                    Boolean includeParentClassify) {

        final String group = "OpenApi_com_ysu_zyw_tc_api_openapi_classifies_TcClassifyApi_findClassify";
        final String key = classifyId + "_" + includeParentClassify;
        TmClassify tmClassify = tcCodisService.opsForGroupedValue().get(group, key, () -> {
            log.info("tc codis service try to load value with key [{}]", (group + ":" + key));
            return tcClassifyService.findClassify(classifyId, includeParentClassify);
        }, GLOABL_CLASSIFY_CACHE_TIMEOUT);

        return ResponseEntity.ok(TcR.ok(tmClassify));
    }

    @ApiOperation(
            value = "查询类目树",
            notes = "查询类目树",
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
    @RequestMapping(value = "/find_classifies", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<TmClassify, Void>> findClassifies() {

        final String group = "OpenApi_com_ysu_zyw_tc_api_openapi_classifies_TcClassifyApi_findClassifies";
        final String key = "All";
        TmClassify tmClassify = tcCodisService.opsForGroupedValue().get(group, key, () -> {
            log.info("tc codis service try to load value with key [{}]", (group + ":" + key));
            return tcClassifyService.findClassifies();
        }, GLOABL_CLASSIFY_CACHE_TIMEOUT);

        return ResponseEntity.ok(TcR.ok(tmClassify));
    }

    @ApiOperation(
            value = "查询子类目树",
            notes = "查询子类目树",
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
    @RequestMapping(value = "/find_sub_classifies/{id}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<TmClassify, Void>> findSubClassifies(
            @ApiParam(value = "分类id") @PathVariable(value = "id") String classifyId,
            @ApiParam(value = "递归子分类") @RequestParam(value = "recursive", defaultValue = "false") Boolean recursive) {

        final String group = "OpenApi_com_ysu_zyw_tc_api_openapi_classifies_TcClassifyApi_findSubClassifies";
        final String key = classifyId + "_" + recursive;
        TmClassify subClassifies = tcCodisService.opsForGroupedValue().get(group, key, () -> {
            log.info("tc codis service try to load value with key [{}]", (group + ":" + key));
            return tcClassifyService.findSubClassifies(classifyId, recursive);
        }, GLOABL_CLASSIFY_CACHE_TIMEOUT);

        return ResponseEntity.ok(TcR.ok(subClassifies));
    }

}

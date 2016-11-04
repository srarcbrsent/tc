package com.ysu.zyw.tc.api.impl.common;

import com.ysu.zyw.tc.components.region.TcRegionService;
import com.ysu.zyw.tc.mw.TcR;
import com.ysu.zyw.tc.model.components.region.TcProvince;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@RequestMapping(value = "/regions")
@Api(value = "区域控制器")
@Slf4j
public class TcRegionApi {

    @Resource
    private TcRegionService tcRegionService;

    @ApiOperation(
            value = "查询省地区信息",
            notes = "查询省地区信息",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_provinces", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<List<TcProvince>, Void>> findProvinces() {
        List<TcProvince> provinceList;
        try {
             provinceList = tcRegionService.findProvinceList();
        } catch (NullPointerException e) {
            return ResponseEntity.ok(new TcR<>(TcR.R.NOT_FOUND, TcR.R.NOT_FOUND_DESCRIPTION));
        }
        checkNotNull(provinceList);
        return ResponseEntity.ok(TcR.ok(provinceList));
    }

    @ApiOperation(
            value = "查询市地区信息",
            notes = "查询市地区信息",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_cities/{code}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<List<TcProvince.TcCity>, Void>> findCities(
            @PathVariable(value = "code") String provinceCode) {
        List<TcProvince.TcCity> cityList;
        try {
            cityList = tcRegionService.findCityList(provinceCode);
        } catch (NullPointerException e) {
            return ResponseEntity.ok(new TcR<>(TcR.R.NOT_FOUND, TcR.R.NOT_FOUND_DESCRIPTION));
        }
        checkNotNull(cityList);
        return ResponseEntity.ok(TcR.ok(cityList));
    }

    @ApiOperation(
            value = "查询区地区信息",
            notes = "查询区地区信息",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_districts/{code}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<List<TcProvince.TcCity.TcDistrict>, Void>> findDistricts(
            @PathVariable(value = "code") String cityCode) {
        List<TcProvince.TcCity.TcDistrict> districtList;
        try {
            districtList = tcRegionService.findDistrictList(cityCode);
        } catch (NullPointerException e) {
            return ResponseEntity.ok(new TcR<>(TcR.R.NOT_FOUND, TcR.R.NOT_FOUND_DESCRIPTION));
        }
        checkNotNull(districtList);
        return ResponseEntity.ok(TcR.ok(districtList));
    }

    @ApiOperation(
            value = "查询指定区地区信息",
            notes = "查询指定区地区信息",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(
            value = "Api版本号",
            paramType = "header",
            name = "X-ApiVersion",
            required = true,
            defaultValue = "1.0")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(value = "/find_district/{code}", method = RequestMethod.GET, headers = "X-ApiVersion=1.0")
    public ResponseEntity<TcR<TcProvince.TcCity.TcDistrict, Void>> findDistrict(
            @PathVariable(value = "code") String code) {
        TcProvince.TcCity.TcDistrict district;
        try {
            district = tcRegionService.findDistrict(code);
        } catch (NullPointerException e) {
            return ResponseEntity.ok(new TcR<>(TcR.R.NOT_FOUND, TcR.R.NOT_FOUND_DESCRIPTION));
        }
        checkNotNull(district);
        return ResponseEntity.ok(TcR.ok(district));
    }

}

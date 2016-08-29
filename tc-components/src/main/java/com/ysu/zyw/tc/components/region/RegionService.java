package com.ysu.zyw.tc.components.region;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ysu.zyw.tc.base.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
public class RegionService implements InitializingBean {

    @Resource(name = "regionResource")
    private org.springframework.core.io.Resource regionResource;

    private static List<TcProvince> tcProvinceList;

    public List<TcProvince> find() {
        return tcProvinceList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String regionJson = FileUtils.readFileToString(regionResource.getFile(), "UTF-8");
        tcProvinceList = JsonUtil.deserialize(regionJson, new TypeReference<List<TcProvince>>() {
        });
        tcProvinceList = buildTwoWayLink(tcProvinceList);
        checkArgument(CollectionUtils.isNotEmpty(tcProvinceList), "empty region is not allowed");
        log.info("load region json file success ...");
    }

    private List<TcProvince> buildTwoWayLink(List<TcProvince> tcProvinceList) {
        tcProvinceList.parallelStream().forEach(tcProvince -> {
            tcProvince.getTcCityList().parallelStream().forEach(tcCity -> {
                tcCity.getTcDistrictList().parallelStream().forEach(tcDistrict -> {
                    tcDistrict.setTcCity(tcCity);
                });
                tcCity.setTcProvince(tcProvince);
            });
        });
        return tcProvinceList;
    }

}

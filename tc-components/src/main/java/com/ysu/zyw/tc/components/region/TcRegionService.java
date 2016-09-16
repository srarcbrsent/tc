package com.ysu.zyw.tc.components.region;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ysu.zyw.tc.base.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


@Slf4j
public class TcRegionService implements InitializingBean {

    @Resource(name = "regionResource")
    private org.springframework.core.io.Resource regionResource;

    private static List<TcProvince> tcCompletedProvinceList;

    private static List<TcProvince> tcProvinceList;

    private static Map<String, List<TcProvince.TcCity>> tcCityListGroup;

    private static Map<String, List<TcProvince.TcCity.TcDistrict>> tcDistrictListGroup;

    public List<TcProvince> findAll() {
        checkNotNull(tcCompletedProvinceList);
        return tcCompletedProvinceList;
    }

    public List<TcProvince> findProvinceList() {
        checkNotNull(tcProvinceList);
        return tcProvinceList;
    }

    public List<TcProvince.TcCity> findCityList(String provinceCode) {
        checkNotNull(tcCityListGroup);
        List<TcProvince.TcCity> tcCityList = tcCityListGroup.get(provinceCode);
        checkNotNull(tcCityList);
        return tcCityList;
    }

    public List<TcProvince.TcCity.TcDistrict> findDistrictList(String cityCode) {
        checkNotNull(tcDistrictListGroup);
        List<TcProvince.TcCity.TcDistrict> tcDistrictList = tcDistrictListGroup.get(cityCode);
        checkNotNull(tcDistrictList);
        return tcDistrictList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String regionJson = FileUtils.readFileToString(regionResource.getFile(), "UTF-8");
        tcCompletedProvinceList = JsonUtil.deserialize(regionJson, new TypeReference<List<TcProvince>>() {
        });
        // build completed province list
        tcCompletedProvinceList = buildTwoWayLink(tcCompletedProvinceList);
        checkArgument(CollectionUtils.isNotEmpty(tcCompletedProvinceList), "empty region is not allowed");
        // build province list
        tcProvinceList = buildProvinceList(tcCompletedProvinceList);
        checkArgument(CollectionUtils.isNotEmpty(tcProvinceList), "empty region is not allowed");
        // build city list
        tcCityListGroup = buildCityListGroup(tcCompletedProvinceList);
        checkArgument(tcCityListGroup.size() > 0, "empty region is not allowed");
        // build district list
        tcDistrictListGroup = buildDistrictListGroup(tcCompletedProvinceList);
        checkArgument(tcDistrictListGroup.size() > 0, "empty region is not allowed");
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

    private List<TcProvince> buildProvinceList(List<TcProvince> tcCompletedProvinceList) {
        return tcCompletedProvinceList.parallelStream().map(TcProvince::copy).collect(Collectors.toList());
    }

    private Map<String, List<TcProvince.TcCity>> buildCityListGroup(List<TcProvince> tcCompletedProvinceList) {
        Map<String, List<TcProvince.TcCity>> cityListGroup = Maps.newLinkedHashMap();
        tcCompletedProvinceList.parallelStream().forEach(tcProvince -> {
            List<TcProvince.TcCity> tcCityListCopy = Lists.newLinkedList();
            tcProvince.getTcCityList().parallelStream().forEach(tcCity -> {
                tcCityListCopy.add(tcCity.copy());
            });
            cityListGroup.put(tcProvince.getCode(), tcCityListCopy);
        });
        return cityListGroup;
    }

    private Map<String, List<TcProvince.TcCity.TcDistrict>> buildDistrictListGroup(
            List<TcProvince> tcCompletedProvinceList) {
        Map<String, List<TcProvince.TcCity.TcDistrict>> districtListGroup = Maps.newLinkedHashMap();
        tcCompletedProvinceList.parallelStream().forEach(tcProvince -> {
            tcProvince.getTcCityList().parallelStream().forEach(tcCity -> {
                List<TcProvince.TcCity.TcDistrict> tcDistrictListCopy = Lists.newLinkedList();
                tcCity.getTcDistrictList().parallelStream().forEach(tcDistrict -> {
                    tcDistrictListCopy.add(tcDistrict.copy());
                });
                districtListGroup.put(tcCity.getCode(), tcDistrictListCopy);
            });
        });
        return districtListGroup;
    }

}

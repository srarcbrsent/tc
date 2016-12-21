package com.ysu.zyw.tc.api.svc.publics;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.dao.mappers.TcRegionMapper;
import com.ysu.zyw.tc.api.dao.po.TcRegion;
import com.ysu.zyw.tc.api.dao.po.TcRegionExample;
import com.ysu.zyw.tc.model.api.o.publics.ToCity;
import com.ysu.zyw.tc.model.api.o.publics.ToProvince;
import com.ysu.zyw.tc.model.api.o.publics.ToRegion;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class TcRegionService {

    @Resource
    private TcRegionMapper tcRegionMapper;

    public List<ToProvince> findProvinces(@Nonnull String countryId) {
        checkNotNull(countryId);
        TcRegionExample tcRegionExample = new TcRegionExample();
        tcRegionExample.createCriteria()
                .andParentIdEqualTo(countryId);
        List<TcRegion> provinces = tcRegionMapper.selectByExample(tcRegionExample);
        return provinces.stream()
                .map(this::convert2ToProvince)
                .sorted(Comparator.comparing(ToProvince::getId))
                .collect(Collectors.toList());
    }

    public List<ToCity> findCities(@Nonnull String provinceId) {
        checkNotNull(provinceId);
        TcRegion province = tcRegionMapper.selectByPrimaryKey(provinceId);
        if (Objects.isNull(province)) {
            return Lists.newArrayList();
        }

        TcRegionExample tcRegionExample = new TcRegionExample();
        tcRegionExample.createCriteria()
                .andParentIdEqualTo(provinceId);
        List<TcRegion> cities = tcRegionMapper.selectByExample(tcRegionExample);

        return cities.stream()
                .map(city -> convert2ToCity(province, city))
                .sorted(Comparator.comparing(ToCity::getId))
                .collect(Collectors.toList());
    }

    public List<ToRegion> findRegions(@Nonnull String cityId) {
        checkNotNull(cityId);
        TcRegion city = tcRegionMapper.selectByPrimaryKey(cityId);
        if (Objects.isNull(city)) {
            return Lists.newArrayList();
        }

        TcRegion province = tcRegionMapper.selectByPrimaryKey(city.getParentId());

        TcRegionExample tcRegionExample = new TcRegionExample();
        tcRegionExample.createCriteria()
                .andParentIdEqualTo(cityId);
        List<TcRegion> regions = tcRegionMapper.selectByExample(tcRegionExample);

        return regions.stream()
                .map(region -> convert2ToRegion(province, city, region))
                .sorted(Comparator.comparing(ToRegion::getId))
                .collect(Collectors.toList());
    }

    public ToRegion findRegion(@Nonnull String regionId) {
        checkNotNull(regionId);

        TcRegion region = tcRegionMapper.selectByPrimaryKey(regionId);

        if (Objects.isNull(region)) {
            return null;
        }

        TcRegion city = tcRegionMapper.selectByPrimaryKey(region.getParentId());

        TcRegion province = tcRegionMapper.selectByPrimaryKey(city.getParentId());

        return convert2ToRegion(province, city, region);
    }

    private ToProvince convert2ToProvince(TcRegion province) {
        checkNotNull(province);
        return new ToProvince(province.getId(), province.getName());
    }

    private ToCity convert2ToCity(TcRegion province, TcRegion city) {
        checkNotNull(province);
        checkNotNull(city);
        return new ToCity(
                city.getId(),
                city.getName(),
                province.getId(),
                province.getName()
        );
    }

    private ToRegion convert2ToRegion(TcRegion province, TcRegion city, TcRegion region) {
        checkNotNull(province);
        checkNotNull(city);
        checkNotNull(region);
        return new ToRegion(
                region.getId(),
                region.getName(),
                city.getId(),
                city.getName(),
                province.getId(),
                province.getName()
        );
    }

}

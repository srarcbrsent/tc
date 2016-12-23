package com.ysu.zyw.tc.api.impl.publics;

import com.ysu.zyw.tc.api.api.publics.TcRegionApi;
import com.ysu.zyw.tc.api.svc.publics.TcRegionService;
import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.components.cache.api.TcCacheService;
import com.ysu.zyw.tc.model.api.o.publics.ToCity;
import com.ysu.zyw.tc.model.api.o.publics.ToProvince;
import com.ysu.zyw.tc.model.api.o.publics.ToRegion;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcRegionApiImpl implements TcRegionApi {

    @Resource
    private TcRegionService tcRegionService;

    @Resource(name = TcConstant.BeanNames.SS_REDIS_SERVICE)
    private TcCacheService tcCacheService;

    private static final long CACHE_REGION_TIMEOUT = 60 * 60 * 1000;

    private static final String CACHE_KEY_PROVINCES = "cache_key_m_region_t_provinces";

    private static final String CACHE_KEY_CITIES = "cache_key_m_region_t_cities";

    private static final String CACHE_KEY_REGIONS = "cache_key_m_region_t_regions";

    private static final String CACHE_KEY_REGION = "cache_key_m_region_t_region";

    private static final ReentrantLock CACHE_LOCK_PROVINCES = new ReentrantLock();

    private static final ReentrantLock CACHE_LOCK_CITIES = new ReentrantLock();

    private static final ReentrantLock CACHE_LOCK_REGIONS = new ReentrantLock();

    private static final ReentrantLock CACHE_LOCK_REGION = new ReentrantLock();

    @Override
    public TcP<List<ToProvince>> findProvinces(String countryId) {
        checkNotNull(countryId);

        List<ToProvince> provinces =
                tcCacheService.get(
                        tcCacheService.buildLogicKey(CACHE_KEY_PROVINCES, countryId),
                        () -> tcRegionService.findProvinces(countryId),
                        CACHE_REGION_TIMEOUT,
                        CACHE_LOCK_PROVINCES
                );

        return TcP.ok(provinces);
    }

    @Override
    public TcP<List<ToCity>> findCities(String provinceId) {
        checkNotNull(provinceId);

        List<ToCity> cities =
                tcCacheService.get(
                        tcCacheService.buildLogicKey(CACHE_KEY_CITIES, provinceId),
                        () -> tcRegionService.findCities(provinceId),
                        CACHE_REGION_TIMEOUT,
                        CACHE_LOCK_CITIES
                );

        return TcP.ok(cities);
    }

    @Override
    public TcP<List<ToRegion>> findRegions(String cityId) {
        checkNotNull(cityId);

        List<ToRegion> regions =
                tcCacheService.get(
                        tcCacheService.buildLogicKey(CACHE_KEY_REGIONS, cityId),
                        () -> tcRegionService.findRegions(cityId),
                        CACHE_REGION_TIMEOUT,
                        CACHE_LOCK_REGIONS
                );

        return TcP.ok(regions);
    }

    @Override
    public TcR<ToRegion> findRegion(String regionId) {
        checkNotNull(regionId);

        ToRegion region =
                tcCacheService.get(
                        tcCacheService.buildLogicKey(CACHE_KEY_REGION, regionId),
                        () -> tcRegionService.findRegion(regionId),
                        CACHE_REGION_TIMEOUT,
                        CACHE_LOCK_REGION
                );

        return TcP.ok(region);
    }

}

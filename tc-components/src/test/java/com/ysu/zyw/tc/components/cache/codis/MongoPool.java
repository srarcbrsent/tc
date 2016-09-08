package com.ysu.zyw.tc.components.cache.codis;

import com.google.common.collect.Maps;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class MongoPool {

    public static int insertCall = 0;

    public static int deleteCall = 0;

    public static int updateCall = 0;

    public static int selectCall = 0;

    public static int selectOneCall = 0;

    private static final Map<String, Mongo> pool = Maps.newConcurrentMap();

    @CachePut(cacheNames = {TcConstant.Sys.DEFAULT_CACHE_NAME}, key = "#root.args[0].id")
    public Mongo insert(Mongo mongo) {
        insertCall++;
        log.info("[{}] times real call insert", insertCall);
        pool.put(mongo.getId(), mongo);
        return mongo;
    }

    @CacheEvict(cacheNames = {TcConstant.Sys.DEFAULT_CACHE_NAME}, key = "#root.args[0]")
    public void delete(String mongoId) {
        deleteCall++;
        log.info("[{}] times real call delete", deleteCall);
        pool.remove(mongoId);
    }

    @CachePut(cacheNames = {TcConstant.Sys.DEFAULT_CACHE_NAME}, key = "#root.args[0].id")
    public Mongo update(Mongo mongo) {
        updateCall++;
        log.info("[{}] times real call update", updateCall);
        pool.put(mongo.getId(), mongo);
        return mongo;
    }

    @Cacheable(cacheNames = {TcConstant.Sys.DEFAULT_CACHE_NAME}, key = "#root.args[0]")
    public Mongo select(String mongoId) {
        selectOneCall++;
        log.info("[{}] times real call select one", selectOneCall);
        return pool.values()
                .parallelStream()
                .filter(mongo -> java.util.Objects.equals(mongo.getId(), mongoId))
                .findFirst()
                .orElseGet(() -> null);
    }

    public List<Mongo> select(List<String> mongoIdList) {
        selectCall++;
        log.info("[{}] times real call select", selectCall);
        return mongoIdList.parallelStream()
                .map(((MongoPool) AopContext.currentProxy())::select)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // codis not support this operation
    @CacheEvict(cacheNames = {TcConstant.Sys.DEFAULT_CACHE_NAME}, allEntries = true)
    public void clearCache() {
    }

}

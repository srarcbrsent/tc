package com.ysu.zyw.tc.components.security.shiro;

import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class TcShiroCacheManager implements CacheManager {

    @Getter
    @Setter
    Map<String, TcShiroCache> shiroCacheContainer;

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        checkArgument(shiroCacheContainer.containsKey(name), "cache with name [" + name + "] not exists");
        return (Cache<K, V>) shiroCacheContainer.get(name);
    }

    public static class TcShiroCache implements Cache<Serializable, Serializable> {

        @Getter
        @Setter
        private org.springframework.cache.Cache cacheToUse;

        public TcShiroCache(org.springframework.cache.Cache cacheToUse) {
            this.cacheToUse = cacheToUse;
        }

        @Override
        public Serializable get(Serializable key) throws CacheException {
            return cacheToUse.get(key, Serializable.class);
        }

        @Override
        public Serializable put(Serializable key, Serializable value) throws CacheException {
            cacheToUse.put(key, value);
            return value;
        }

        @Override
        public Serializable remove(Serializable key) throws CacheException {
            Serializable value = cacheToUse.get(key, Serializable.class);
            cacheToUse.evict(key);
            return value;
        }

        @Override
        public void clear() throws CacheException {
            cacheToUse.clear();
        }

        @Override
        public int size() {
            throw new TcException("size is a not supported operation");
        }

        @Override
        public Set<Serializable> keys() {
            throw new TcException("keys is a not supported operation");
        }

        @Override
        public Collection<Serializable> values() {
            throw new TcException("values is a not supported operation");
        }

    }

}

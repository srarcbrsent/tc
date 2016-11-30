package com.ysu.zyw.tc.components.cache.codis.ops;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class TcCodisServerDelegate implements InitializingBean {

    @Getter
    @Setter
    List<RedisTemplate> redisTemplateList;

    public void doInEveryCodisServer(Consumer<RedisTemplate> worker) {
        redisTemplateList.forEach(worker);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("lazy init codis server delegate ... [{}] factory created ...", redisTemplateList.size());
    }

}

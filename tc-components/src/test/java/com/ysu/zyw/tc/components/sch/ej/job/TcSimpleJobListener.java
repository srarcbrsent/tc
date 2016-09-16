package com.ysu.zyw.tc.components.sch.ej.job;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcSimpleJobListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("before job executed ...");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("after job executed ...");
    }

}

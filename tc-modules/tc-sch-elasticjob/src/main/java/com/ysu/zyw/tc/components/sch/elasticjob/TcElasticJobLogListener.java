package com.ysu.zyw.tc.components.sch.elasticjob;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcElasticJobLogListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("before job [{}] executed ...", shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("after job [{}] executed ...", shardingContexts.getJobName());
    }

}

package com.ysu.zyw.tc.components.sch.ej.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("do work ... [{}] [{}] [{}]",
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobParameter());
    }

}
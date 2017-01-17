package com.ysu.zyw.tc.components.sch.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.util.Date;

public class TcSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(new Date());
        System.out.println(shardingContext.getJobName());
    }

}

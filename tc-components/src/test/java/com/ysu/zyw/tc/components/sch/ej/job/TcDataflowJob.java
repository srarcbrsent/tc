package com.ysu.zyw.tc.components.sch.ej.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TcDataflowJob implements DataflowJob<String> {

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        return Lists.newArrayList("hello");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        data.stream().forEach(d -> {
            log.info("data -> [{}]", d);
        });
    }

}

package com.ysu.zyw.tc.components.sch.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TcDataflowJob implements DataflowJob<Date> {

    private List<Date> dates = LongStream.range(9998, 10009).mapToObj(Date::new).collect(Collectors.toList());

    @Override
    public List<Date> fetchData(ShardingContext shardingContext) {
        return dates.isEmpty() ? Lists.newArrayList() : Lists.newArrayList(dates.remove(0));
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Date> data) {
        System.out.println(data);
    }

}

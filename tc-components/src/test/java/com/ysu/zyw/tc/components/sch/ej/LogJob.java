package com.ysu.zyw.tc.components.sch.ej;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.exception.JobException;
import com.dangdang.ddframe.job.internal.schedule.JobFacade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogJob implements ElasticJob {

    @Override
    public void execute() {
        log.info("...");
    }

    @Override
    public void handleJobExecutionException(JobException jobException) {

    }

    @Override
    public JobFacade getJobFacade() {
        return null;
    }

    @Override
    public void setJobFacade(JobFacade jobFacade) {

    }

}
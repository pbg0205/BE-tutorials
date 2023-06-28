package com.cooper.springbatchtutorial.batch.job_listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class JobLoggerListener implements JobExecutionListener {

    private static String BEFORE_MESSAGE = "=== {} Job is Running";
    private static String AFTER_MESSAGE = "=== {} Job is Done. (Status: {})";

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(BEFORE_MESSAGE, jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(AFTER_MESSAGE,
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus());

        /**
         * Job 이 실패한 경우에 관한 처리를 할 수 있다.
         * <p>
         * 별도의 실패시에 관한 Listener 는 없는 것일까?
         */
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            // email or message
            log.info("=== Job is Failed");
        }
    }
}

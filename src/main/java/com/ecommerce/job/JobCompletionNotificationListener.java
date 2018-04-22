package com.ecommerce.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * Listener for the job completion
 */
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
        LOGGER.info("Job Started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("Job Completed");
        }
    }
}

package com.ecommerce.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * Listener for the job step completion
 */
public class StepExecutionNotificationListener extends StepExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepExecutionNotificationListener.class);

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("After step");
        return super.afterStep(stepExecution);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Before step");
        super.beforeStep(stepExecution);
    }
}

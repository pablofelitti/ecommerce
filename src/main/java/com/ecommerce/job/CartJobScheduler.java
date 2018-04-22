package com.ecommerce.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for the cart processing job
 */
@Component
public class CartJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job cartJob;

    @Autowired
    CartJobScheduler(final JobLauncher jobLauncher,
                     final @Qualifier("cartJob") Job cartJob) {
        this.jobLauncher = jobLauncher;
        this.cartJob = cartJob;
    }

    @Scheduled(fixedDelayString = "${cart.job.fixed-rate}")
    public void reportCurrentTime() throws Exception {
        jobLauncher.run(cartJob, new JobParametersBuilder().
                addLong("time", System.currentTimeMillis()).toJobParameters());
    }
}

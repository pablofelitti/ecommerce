package com.ecommerce.config;

import com.ecommerce.entity.Cart;
import com.ecommerce.job.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class JobConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private AttemptReader attemptReader;
    private AttemptProcessor attemptProcessor;

    @Value("${cart.job.max-threads}")
    private int maxThreads;

    @Value("${cart.job.chunk-size}")
    private int chunkSize;

    @Autowired
    JobConfiguration(final JobBuilderFactory jobBuilderFactory,
                     final StepBuilderFactory stepBuilderFactory,
                     final AttemptReader attemptReader,
                     final AttemptProcessor attemptProcessor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.attemptReader = attemptReader;
        this.attemptProcessor = attemptProcessor;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(maxThreads);
        return taskExecutor;
    }

    @Bean
    public JobCompletionNotificationListener jobExecutionListener() {
        return new JobCompletionNotificationListener();
    }

    @Bean
    public StepExecutionNotificationListener stepExecutionListener() {
        return new StepExecutionNotificationListener();
    }

    @Bean("cartJob")
    public Job processAttemptJob() {
        return jobBuilderFactory.get("cart-process-job")
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener())
                .flow(step()).end().build();
    }

    @Bean
    public ChunkExecutionListener chunkListener() {
        return new ChunkExecutionListener();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Cart, Cart>chunk(chunkSize)
                .reader(attemptReader)
                .processor(attemptProcessor)
                .taskExecutor(taskExecutor())
                .listener(stepExecutionListener())
                .listener(chunkListener())
                .throttleLimit(maxThreads).build();
    }
}

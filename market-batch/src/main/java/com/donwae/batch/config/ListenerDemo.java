package com.donwae.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Job的嵌套示例
 * @author Jeremy Zhang
 * 2020/4/8 下午10:59
 */
@Configuration
@EnableBatchProcessing
public class NestedDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Job childJobOne;

    @Autowired
    private Job childJobTwo;

    @Autowired
    private JobLauncher launcher;

    @Bean
    public Job parentJob(JobRepository repository, PlatformTransactionManager transactionManager){
        return jobBuilderFactory.get("parentJob")
                .start(childJobOne(repository, transactionManager))
                .next(childJobTwo(repository, transactionManager))
                .build();
    }

    private Step childJobTwo(JobRepository repository, PlatformTransactionManager transactionManager) {
        return  new JobStepBuilder(new StepBuilder("childJobTwo"))
                .job(childJobTwo)
                .launcher(launcher)
                .repository(repository)
                .transactionManager(transactionManager)
                .build();
    }

    private Step childJobOne(JobRepository repository, PlatformTransactionManager transactionManager) {
        return  new JobStepBuilder(new StepBuilder("childJobOne"))
                .job(childJobOne)
                .launcher(launcher)
                .repository(repository)
                .transactionManager(transactionManager)
                .build();
    }
}

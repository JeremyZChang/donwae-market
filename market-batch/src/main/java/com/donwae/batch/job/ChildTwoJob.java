package com.donwae.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/4/23 下午5:14
 */
@Configuration
public class ChildTwoJob {
    // 注入创建任务的工厂
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    // 任务执行由Step决定 注入创建Step的工厂
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step childJobTwoStepOne(){
        return stepBuilderFactory.get("childJobTwoStepOne")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("childJobTwoStepOne");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step childJobTwoStepTwo(){
        return stepBuilderFactory.get("childJobTwoStepTwo")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("childJobTwoStepTwo");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job childJobTwo(){
        return  jobBuilderFactory.get("childJobTwo")
                .start(childJobTwoStepOne())
                .next(childJobTwoStepTwo())
                .build();
    }

}

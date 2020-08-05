package com.donwae.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
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
public class ChildOneJob {
    // 注入创建任务的工厂
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    // 任务执行由Step决定 注入创建Step的工厂
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step childJobOneStepOne(){
        return stepBuilderFactory.get("childJobOneStepOne")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("childJobOneStepOne");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job childJobOne(){
        return  jobBuilderFactory.get("childJobOne")
                .start(childJobOneStepOne())
                .build();
    }

}

package com.donwae.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 批处理任务配置
 * @author Jeremy Zhang
 * 2020/4/8 下午10:14
 */
@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    // 注入创建任务的工厂
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    // 任务执行由Step决定 注入创建Step的工厂
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    // 创建任务对象
    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("firstJob").start(firstStep()).build();
    }

    // 创建Step
    @Bean
    public Step firstStep() {
        return stepBuilderFactory.get("firstStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("Hello first step.");
            return null;
        }).build();
    }

}

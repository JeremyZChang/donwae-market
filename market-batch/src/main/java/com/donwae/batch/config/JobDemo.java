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
 * 批处理任务示例
 * @author Jeremy Zhang
 * 2020/4/8 下午10:42
 */
@Configuration
@EnableBatchProcessing
public class JobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job demoJob(){
        return jobBuilderFactory.get("demoJob")
                .start(firstStep())
                .next(secondStep())
                .next(thirdStep()).build();
    }

    @Bean
    public Job onCompletedJob(){
        return jobBuilderFactory.get("onCompletedJob")
                .start(firstStep()).on("COMPLETE").to(secondStep())
                .on("ERROR").fail()
                .on("RESTART").stopAndRestart(firstStep())
                .from(secondStep()).on("COMPLETE").to(thirdStep())
                .from(thirdStep()).end()
                .build();
    }

    @Bean
    public Step thirdStep() {
        return stepBuilderFactory.get("thirdStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("I'm third step.");
            return null;
        }).build();
    }

    @Bean
    public Step secondStep() {
        return stepBuilderFactory.get("secondStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("I'm second step.");
            return null;
        }).build();
    }

    // 创建Step
    @Bean
    public Step firstStep() {
        return stepBuilderFactory.get("firstStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("I'm first step.");
            return null;
        }).build();
    }
}

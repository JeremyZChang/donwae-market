package com.donwae.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/4/8 下午10:59
 */
@Configuration
@EnableBatchProcessing
public class FlowDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job demoFlowJob(){
        return jobBuilderFactory.get("demoFlowJob")
                .start(flowDemoFlow())
                .next(thirdFlowStep())
                .end().build();
    }

    @Bean
    public Flow flowDemoFlow(){
        return new FlowBuilder<Flow>("flowDemoFlow")
                .start(firstFlowStep())
                .next(secondFlowStep())
                .build();
    }

    @Bean
    public Step thirdFlowStep() {
        return stepBuilderFactory.get("thirdFlowStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("I'm third Flow step.");
            return null;
        }).build();
    }

    @Bean
    public Step secondFlowStep() {
        return stepBuilderFactory.get("secondFlowStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("I'm second Flow step.");
            return null;
        }).build();
    }

    // 创建Step
    @Bean
    public Step firstFlowStep() {
        return stepBuilderFactory.get("firstFlowStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("I'm first Flow Step.");
            return null;
        }).build();
    }

}

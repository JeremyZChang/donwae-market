package com.donwae.batch.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Job传递参数示例
 * @author Jeremy Zhang
 * 2020/4/8 下午10:59
 */
@Configuration
@EnableBatchProcessing
public class ParametersDemo implements StepExecutionListener {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> parameters;

    @Bean
    public Job parameterJob(){
        return jobBuilderFactory.get("parameterJob")
                .start(parameterStep())
                .build();
    }

    // 可以使用Step级别的监听 对step传递数据
    @Bean
    public Step parameterStep() {
        return stepBuilderFactory.get("parameterStep")
                .listener(this)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(parameters.get("info"));
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

package com.donwae.batch.config;

import com.donwae.batch.listener.MyChuckListener;
import com.donwae.batch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

/**
 * 使用监听器示例
 * @author Jeremy Zhang
 * 2020/4/8 下午10:59
 */
@Configuration
@EnableBatchProcessing
public class ListenerDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job listenerJob(){
        return jobBuilderFactory.get("listenerJob")
                .start(listenerStepOne())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step listenerStepOne() {
        return stepBuilderFactory.get("listenerStepOne")
                .<String, String>chunk(2)
                .faultTolerant()
                .listener(new MyChuckListener())
                .reader(read())
                .writer(write())
                .build();

    }

    @Bean
    public ItemWriter<String> write() {
        return items -> {
            for(String item:items){
                System.out.println(item);
            }
        };
    }

    @Bean
    public ItemReader<String> read() {

        return new ListItemReader<>(Arrays.asList("Java","Test","Jone"));
    }

}

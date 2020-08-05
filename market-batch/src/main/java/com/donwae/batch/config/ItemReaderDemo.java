package com.donwae.batch.config;

import com.donwae.batch.listener.MyChuckListener;
import com.donwae.batch.listener.MyJobListener;
import com.donwae.batch.reader.MyReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * ItemReader使用示例
 * @author Jeremy Zhang
 * 2020/4/26 下午4:49
 */
@Configuration
public class ItemReaderDemo {



    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemReaderJob(){
        return jobBuilderFactory.get("itemReaderJob")
                .start(itemReaderJobStep())
                .build();
    }

    @Bean
    public Step itemReaderJobStep() {
        return stepBuilderFactory.get("itemReaderJobStep")
                .<String, String>chunk(2)
                .reader(itemReaderRead())
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
    public MyReader itemReaderRead() {
        List<String> data = Arrays.asList("Java","Test","Jone");
        return new MyReader(data);
    }



}

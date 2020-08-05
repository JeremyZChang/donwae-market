package com.donwae.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing // 在Application类种注解就不用在每个批处理中注解了
public class MarketBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketBatchApplication.class, args);
	}

}

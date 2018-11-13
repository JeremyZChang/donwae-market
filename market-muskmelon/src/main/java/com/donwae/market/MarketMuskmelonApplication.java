package com.donwae.market;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.donwae.market.dao")
public class MarketMuskmelonApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketMuskmelonApplication.class, args);
	}
}

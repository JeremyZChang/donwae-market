package com.donwae.market.activiti;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.donwae.market.activiti.repository")
@EnableProcessApplication
@SpringBootApplication
public class ActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiApplication.class, args);
	}
}

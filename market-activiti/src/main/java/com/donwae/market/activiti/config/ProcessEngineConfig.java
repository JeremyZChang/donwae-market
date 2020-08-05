package com.donwae.market.activiti.config;

import com.donwae.market.activiti.controller.DemoMain;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/17 下午7:11
 */
@Configuration
public class ProcessEngineConfig {
    private static final Logger logger = LoggerFactory.getLogger(ProcessEngineConfig.class);

//    @Bean
//    public static ProcessEngine mineProcessEngine() {
//        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();   //创建默认的基于内存数据库的流程引擎配置对象
//        ProcessEngine processEngine = cfg.buildProcessEngine();    //构造流程引擎
//        String engineName = processEngine.getName();   //获取流程引擎的name
//        String version = ProcessEngine.VERSION;    //获取流程引擎的版本信息
//
//        logger.info("流程引擎名称 [{}], 版本 [{}]", engineName, version);
//        return processEngine;
//    }
}

package com.donwae.market.activiti.controller;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/17 下午7:16
 */
@Controller
@Slf4j
@RequestMapping("/leave")
public class LeaveController {
    private static final Logger logger = LoggerFactory.getLogger(LeaveController.class);

    @Autowired
    private ProcessEngine mineProcessEngine;

    /**
     * 部署流程定义文件
     * @return
     */
    private ProcessDefinition getProcessDefinition() {
        RepositoryService repositoryService = mineProcessEngine.getRepositoryService();   //创建一个对流程编译库操作的Service
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();   //获取一个builder
        deploymentBuilder.addClasspathResource("processes/leave.bpmn20.xml");   //这里写上流程编译路径
        Deployment deployment = deploymentBuilder.deploy();    //部署
        String deploymentId = deployment.getId();    //获取deployment的ID
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();     //根据deploymentId来获取流程定义对象
        logger.info("流程定义文件 [{}] , 流程ID [{}]", processDefinition.getName(), processDefinition.getId());
        return processDefinition;
    }

}

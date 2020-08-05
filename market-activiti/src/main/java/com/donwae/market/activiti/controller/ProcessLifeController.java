package com.donwae.market.activiti.controller;

import com.donwae.market.activiti.entity.Response;
import com.donwae.market.activiti.model.DeployModel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/30 下午5:25
 */
@Slf4j
@RequestMapping("/deploy")
@RestController
public class ProcessLifeController {

    @Autowired
    private ProcessEngine processEngine;

    //部署流程资源 部署单个
    @PostMapping("/single")
    public Response deploy(@RequestBody DeployModel model){
        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name(model.getName())//声明流程的名称
                .addClasspathResource(model.getBpmnPath())//加载资源文件，一次只能加载一个文件 "processes/serviceTask.bpmn20.xml"
                .addClasspathResource(model.getPicPath())//"processes/serviceTask.png
                .deploy();//完成部署

        Map<String, Object> res = Maps.newHashMap();
        res.put("部署ID", deployment.getId());
        res.put("部署时间", deployment.getDeploymentTime());
        return Response.success(res);
    }

    @GetMapping("/findProcessInstance")
    public Response findProcessInstance(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义查询
                /*指定查询条件,where条件*/
                //.deploymentId(deploymentId)//使用部署对象ID查询
                //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
                //.processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
                //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /*排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                .list();//返回一个集合列表，封装流程定义
        //.singleResult();//返回唯一结果集
        //.count();//返回结果集数量
        //.listPage(firstResult, maxResults)//分页查询
        List<Map> resList = Lists.newArrayList();
        if(list != null && list.size()>0){
            for(ProcessDefinition processDefinition:list){
                System.out.println("流程定义ID:"+processDefinition.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义名称:"+processDefinition.getName());//对应HelloWorld.bpmn文件中的name属性值
                System.out.println("流程定义的key:"+processDefinition.getKey());//对应HelloWorld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+processDefinition.getVersion());//当流程定义的key值相同的情况下，版本升级，默认从1开始
                System.out.println("资源名称bpmn文件:"+processDefinition.getResourceName());
                System.out.println("资源名称png文件:"+processDefinition.getDiagramResourceName());
                System.out.println("部署对象ID:"+processDefinition.getDeploymentId());
                System.out.println("################################");
                Map<String, Object> res = Maps.newHashMap();
                res.put("流程ID", processDefinition.getId());
                res.put("流程定义名称", processDefinition.getName());
                res.put("流程定义的key", processDefinition.getKey());
                res.put("流程定义的版本", processDefinition.getVersion());
                res.put("资源名称bpmn文件", processDefinition.getResourceName());
                res.put("资源名称png文件", processDefinition.getDiagramResourceName());
                res.put("部署对象ID", processDefinition.getDeploymentId());
                resList.add(res);
            }
        }
        return Response.success(resList);
    }

}

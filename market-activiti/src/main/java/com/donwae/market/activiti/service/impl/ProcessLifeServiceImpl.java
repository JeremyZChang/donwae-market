package com.donwae.market.activiti.service.impl;

import com.donwae.market.activiti.model.DeployModel;
import com.donwae.market.activiti.service.ProcessLifeService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.persistence.entity.JobEntity;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流程生命周期服务
 * @author Jeremy Zhang
 * 2020/5/30 下午5:44
 */
@Slf4j
@Service
public class ProcessLifeServiceImpl implements ProcessLifeService {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    // 单一部署流程
    @Override
    public Map<String, Object> singleDeployProcess(DeployModel model) {
        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name(model.getName())//声明流程的名称
                .addClasspathResource(model.getBpmnPath())//加载资源文件，一次只能加载一个文件 "processes/serviceTask.bpmn20.xml"
                .addClasspathResource(model.getPicPath())//"processes/serviceTask.png
                .deploy();//完成部署

        Map<String, Object> deployInfo = Maps.newHashMap();
        deployInfo.put("部署ID", deployment.getId());
        deployInfo.put("部署时间", deployment.getDeploymentTime());
        return deployInfo;
    }

    // 单一部署流程
    @Override
    public Map<String, Object> deployXmlProcess(DeployModel model) {
        //字符串
//        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions>...</definitions>";

        Deployment deployment = repositoryService//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name(model.getResourceName()+".xml")
                //添加ResourceName Xml
                .addString(model.getResourceName()+".xml", model.getXml())
                .deploy();//完成部署

        Map<String, Object> deployInfo = Maps.newHashMap();
        deployInfo.put("部署ID", deployment.getId());
        deployInfo.put("部署时间", deployment.getDeploymentTime());
        deployInfo.put("部署Name", deployment.getName());
        return deployInfo;
    }

    // 查询流程实例
    @Override
    public List findProcessInstance() {
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
                Map<String, Object> res = Maps.newHashMap();
                res.put("id", processDefinition.getId()); //流程定义的key+版本+随机生成数 流程ID
                res.put("name", processDefinition.getName());//bpmn文件中的name属性值 流程定义名称
                res.put("key", processDefinition.getKey());//对应Hbpmn文件中的id属性值 流程定义的key
                res.put("version", processDefinition.getVersion());//当流程定义的key值相同的情况下，版本升级，默认从1开始 流程定义的版本
                res.put("bpmn", processDefinition.getResourceName()); //资源名称bpmn文件
                res.put("png", processDefinition.getDiagramResourceName()); //资源名称png文件
                res.put("deploymentId", processDefinition.getDeploymentId());//部署对象ID
                resList.add(res);
            }
        }

        return resList;
    }

    // 删除流程实例
    @Override
    public void removeProcessInstance(String id) {
        //使用部署ID，完成删除
//        String deploymentId = id;
        /*
         * 不带级联的删除
         * 只能删除没有启动的流程，如果流程启动，就会抛出异常
         */
//        processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
//                        .deleteDeployment(deploymentId);
        /*
         * 能级联的删除
         * 能删除启动的流程，会删除和当前规则相关的所有信息，正在执行的信息，也包括历史信息
         */
        processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .deleteDeployment(id, true);

        log.debug("删除成功: {}", id);
    }
}

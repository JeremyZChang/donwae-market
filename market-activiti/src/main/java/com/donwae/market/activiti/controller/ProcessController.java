package com.donwae.market.activiti.controller;

import com.donwae.market.activiti.entity.Response;
import com.donwae.market.activiti.model.ComplateProcessModel;
import com.donwae.market.activiti.model.ProcessViewModel;
import com.donwae.market.activiti.model.StartProcessModel;
import com.donwae.market.activiti.service.ProcessLifeService;
import com.donwae.market.activiti.service.ProcessService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务流程API
 * @author Jeremy Zhang
 * 2020/5/30 下午5:36
 */
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 启动流程实例
     * 2020/05/30 19:20:36
     * @author Jeremy Zhang
     */
    @PostMapping("/start")
    public Response startProcessInstance(@RequestBody StartProcessModel model) {
        String res = processService.startProcessInstance(model);
        return Response.success(res);
    }

    /**
     * 取当前用户的所有任务
     * 2020/05/30 17:37:01
     * @author Jeremy Zhang
     */
    @GetMapping("/find/task")
    public Response findPersonalTask(@RequestParam("user") String user){
        List resList = processService.findTask(user);
        return Response.success(resList);

    }


    @GetMapping("/instance/{id}")
    public Response processInstance(@PathVariable("id") String processDefinitionId){
        List resList = processService.findProcess1(processDefinitionId);
        return Response.success(resList);

    }

    /**
     * 取流程的信息
     * 2020/05/30 17:37:01
     * @author Jeremy Zhang
     */
    @GetMapping("/task/{id}")
    public Response findTask(@PathVariable("id") String id){
        Map res = processService.findTaskById(id);
        return Response.success(res);
    }

    /**
     * 取流程的信息
     * 2020/05/30 17:37:01
     * @author Jeremy Zhang
     */
    @GetMapping("/{id}")
    public Response findProcess(@PathVariable("id") String id){
        Map res = processService.findProcess(id);
        return Response.success(res);
    }

    @GetMapping("/history/{id}")
    public Response historyActInstanceList(@PathVariable("id") String id){
        List<Map> res = processService.findHistory(id);
        return Response.success(res);
    }

    @GetMapping("/history/instance/{id}")
    public Response queryProcessHistory(@PathVariable("id") String id) throws Exception {
        List<Map> res = processService.findHistoryInstance(id);
        return Response.success(res);

    }

    //查询历史任务记录ACT_HI_TASKINST
    @GetMapping("/history/task/{id}")
    public Response queryTaskHistory(@PathVariable("id") String id) throws Exception {
        List<Map> res = processService.findHistoryTask(id);
        return Response.success(res);
    }

    @GetMapping("/history/var/{id}")
    public Response queryVariableHistory(@PathVariable("id") String id) throws Exception {
        List<Map> res = processService.findVariableTask(id);
        return Response.success(res);
    }

    //查询历史任务记录ACT_HI_DETAIL
    @GetMapping("/history/detail/{id}")
    public Response queryDetailHistory(@PathVariable("id") String id) throws Exception {
        List<Map> res = processService.findHistoryDetail(id);
        return Response.success(res);
    }

    //查询任务流程节点与信息 processInstanceId
    @GetMapping("/model/info/{id}")
    public Response findModelInfo(@PathVariable("id") String id) throws Exception {
        ProcessViewModel res = processService.getAllElements(id);
        return Response.success(res);
    }

    /**
     * 取流程当前节点的所有信息
     * 2020/05/30 17:37:01
     * @author Jeremy Zhang
     */

    // 完成我的任务
    @PostMapping("/completeTask")
    public String completeManagerTask( @RequestBody ComplateProcessModel model) {
        processEngine.getTaskService()// 与正在执行的任务管理相关的Service
                .complete(model.getTaskId(), model.getVariables());
        return "完成任务：任务ID：" + model.getTaskId();
    }
}

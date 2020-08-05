package com.donwae.market.activiti.service;

import com.donwae.market.activiti.model.DeployModel;
import com.donwae.market.activiti.model.ProcessViewModel;
import com.donwae.market.activiti.model.StartProcessModel;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/30 下午5:43
 */
public interface ProcessService {

    /**
     * 启动流程实例
     * 2020/05/30 19:31:58
     * @author Jeremy Zhang
     */
    String startProcessInstance(StartProcessModel model);
    /**
     * 取用户的所有任务
     * 2020/05/30 19:53:14
     * @author Jeremy Zhang
     */
    List findTask(String user);

    List<Map> findProcess1(String processDefinitionId);

    /**
     * id取任务信息
     * 2020/05/30 20:00:27
     * @author Jeremy Zhang
     */
    Map<String, Object> findTaskById(String taskId);

    Map findProcess(String id);

    List<Map> findHistory(String id);

    List<Map> findHistoryInstance(String id);

    List<Map> findHistoryTask(String id);

    List<Map> findVariableTask(String id);

    List<Map> findHistoryDetail(String id);

    ProcessViewModel getAllElements(String processInstanceId);
}

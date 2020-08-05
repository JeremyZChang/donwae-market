package com.donwae.market.activiti.service;

import com.donwae.market.activiti.model.DeployModel;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/30 下午5:43
 */
public interface ProcessLifeService {
    /**
     * 单一部署流程
     * @author Jeremy Zhang
     */
    Map<String, Object> singleDeployProcess(DeployModel model);
    /**
     * 以xml格式部署流程
     * @author Jeremy Zhang
     */
    Map<String, Object> deployXmlProcess(DeployModel model);

    /**
     * 查询流程实例
     * @author Jeremy Zhang
     */
    List findProcessInstance();
    /**
     * 删除流程实例
     * @author Jeremy Zhang
     */
    void removeProcessInstance(String id);
}

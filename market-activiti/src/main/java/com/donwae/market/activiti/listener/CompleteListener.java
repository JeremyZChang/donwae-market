package com.donwae.market.activiti.listener;


import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompleteListener implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        System.out.println("delegateTask.getEventName() = " + delegateTask.getEventName());
        Map<String, Object> variables = delegateTask.getVariables();
        String submit = (String)variables.get("submit");
         //添加会签的人员，所有的都审批通过才可进入下一环节
        if("Y".equals(submit)) {
            Integer yNo = (Integer)variables.get("yNo");
            // 赋值时不可直接操作Variable, 底层
            delegateTask.setVariable("yNo", yNo+1);
        } else if ("N".equals(submit)) {
            Integer nNo = (Integer)variables.get("nNo");
            delegateTask.setVariable("nNo", nNo+1);
        }
//        delegateTask.setVariable("publicityList",assigneeList);
    }
}
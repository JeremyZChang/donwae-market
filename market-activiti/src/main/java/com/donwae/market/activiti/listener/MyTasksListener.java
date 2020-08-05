package com.donwae.market.activiti.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;


import java.util.ArrayList;
import java.util.List;

public class MyTasksListener implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        System.out.println("delegateTask.getEventName() = " + delegateTask.getEventName());

         //添加会签的人员，所有的都审批通过才可进入下一环节

        List<String> assigneeList = new ArrayList<String>();
        assigneeList.add("user2");
        assigneeList.add("user3");
        delegateTask.setVariable("publicityList",assigneeList);
    }
}
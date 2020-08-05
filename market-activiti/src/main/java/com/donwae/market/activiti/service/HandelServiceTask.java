package com.donwae.market.activiti.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.List;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/20 下午5:03
 */
public class HandelServiceTask implements JavaDelegate {
    //流程变量
    private Expression text1;
    //重写委托的提交方法
    @Override
    public void execute(DelegateExecution execution) {

//        List<DelegateExecution> list = execution.getSuperExecution();
//        for(DelegateExecution e : list){
////            e.
//        }

        System.out.println("serviceTask已经执行已经执行！");
        String value1 = (String) text1.getValue(execution);
        System.out.println(value1);
        execution.setVariable("var1", value1);
    }

}

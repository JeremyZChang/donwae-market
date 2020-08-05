package com.donwae.market.activiti.service.task;

import com.donwae.market.activiti.service.ServiceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/20 下午5:03
 */
@Slf4j
@Service(value = "calHandleService")
public class CalHandleService implements ServiceTaskService {
    //流程变量
    private Expression text1;
    //重写委托的提交方法
    @Override
    public void execute(DelegateExecution execution) {

//        List<? extends DelegateExecution> list = execution.getExecutions();
//        for(DelegateExecution e : list){
////            e.
//        }

        log.info("CalHandleTask已经执行！");
        String value1 = (String) text1.getValue(execution);
        log.info(value1);
        execution.setVariable("calStart", "Y".equals(value1));
    }

}

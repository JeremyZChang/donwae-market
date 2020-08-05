package com.donwae.market.activiti.service.task;

import com.donwae.market.activiti.entity.Response;
import com.donwae.market.activiti.model.StartProcessModel;
import com.donwae.market.activiti.service.ProcessService;
import com.donwae.market.activiti.service.ServiceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Autowired
    private ProcessService processService;
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
        StartProcessModel model = new StartProcessModel();
        String res = processService.startProcessInstance(model);
        log.info(res);
        execution.setVariable("calStart", "Y".equals(value1));
    }

}

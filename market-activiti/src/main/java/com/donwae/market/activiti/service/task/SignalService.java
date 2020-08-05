package com.donwae.market.activiti.service.task;

import com.donwae.market.activiti.model.StartProcessModel;
import com.donwae.market.activiti.service.ProcessService;
import com.donwae.market.activiti.service.ServiceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/20 下午5:03
 */
@Slf4j
@Service(value = "signalService")
public class SignalService implements ServiceTaskService {

    @Autowired
    private RuntimeService runtimeService;
    //重写委托的提交方法
    @Override
    public void execute(DelegateExecution execution) {
        log.info("signalService已经执行！");

        String singalProcessId = (String)execution.getVariable("signalProcessId");

        List<Execution> executions =  runtimeService.createExecutionQuery()
            .signalEventSubscriptionName(singalProcessId).list();
        for(Execution e:executions){
            execution.getProcessEngineServices().getRuntimeService().signalEventReceived(singalProcessId, e.getId());
        }
    }

}

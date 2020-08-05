package com.donwae.market.activiti.service.task;

import com.donwae.market.activiti.service.ServiceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/20 下午5:03
 */
@Slf4j
@Service(value = "autoCalService")
public class AutoCalService implements ServiceTaskService {

    @Override
    public void execute(DelegateExecution execution) {
        int calCount = execution.getVariable("calCount")!=null
                ? (int)execution.getVariable("calCount") : 0;
        int calResult = execution.getVariable("calResult")!=null
                ? (int)execution.getVariable("calResult") : 0;
        log.info("autoCalService已经执行第{}次", calCount);

        int a = (int)Math.floor(Math.random() * (4 - 1)) + 1;
        calResult += a;

        log.info("获得的随机数为: {}", a);
        log.info("流程累计计算结果为: {}", calResult);

        execution.setVariable("calResult", calResult);
        execution.setVariable("calCount", calCount + 1);
    }

}

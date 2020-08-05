package com.donwae.market.activiti.service.task;

import com.donwae.market.activiti.entity.Response;
import com.donwae.market.activiti.model.StartProcessModel;
import com.donwae.market.activiti.service.ProcessService;
import com.donwae.market.activiti.service.ServiceTaskService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.impl.el.FixedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/20 下午5:03
 */
@Slf4j
@Service(value = "calHandleService")
public class CalHandleService implements ServiceTaskService {

    public static Integer count = 1;
    //流程变量
    private Expression text1;

    private FixedValue autoStartService;

    @Autowired
    private ProcessService processService;
    //重写委托的提交方法
    @Override
    public void execute(DelegateExecution execution) {

//        List<? extends DelegateExecution> list = execution.getExecutions();
//        for(DelegateExecution e : list){
////            e.
//        }

//        String value1 = (String) text1.getValue(execution);

        log.info("CalHandleTask已经执行！");
        String value1 = (String)autoStartService.getValue(execution);
        log.info(value1);
        StartProcessModel model = new StartProcessModel();
        model.setId(value1);
        Map<String, Object> v = Maps.newHashMap();
        v.put("signalProcessId", value1);


        Map<String, Object> map = execution.getVariables();
        if(value1.startsWith("ProcessRizhi")){
            if(count%2==1){
                v.put("cumlRate", "0.5");
            }else {
                v.put("cumlRate", "1");
            }
            count ++;
        }
        log.info("cumlRate:{}",v.get("cumlRate")==null?"null":v.get("cumlRate").toString());
        v.putAll(map);
        model.setVariables(v);
        String res = processService.startProcessInstance(model);
        log.info(res);
        execution.setVariable(value1, res);
    }

}

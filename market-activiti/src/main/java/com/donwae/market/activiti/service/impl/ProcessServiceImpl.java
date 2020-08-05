package com.donwae.market.activiti.service.impl;

import com.donwae.market.activiti.model.*;
import com.donwae.market.activiti.service.ProcessService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.org.apache.xerces.internal.dom.DeferredTextImpl;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaField;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaString;
import org.camunda.bpm.model.xml.Model;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 流程生命周期服务
 * @author Jeremy Zhang
 * 2020/5/30 下午5:44
 */
@Slf4j
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;



    // 启动流程实例
    @Override
    public String startProcessInstance(StartProcessModel model) {
        ProcessInstance processInstance = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
//                .startProcessInstanceByKey() // 使用流程定义的key启动流程实例，key对应leave.bpmn文件中id的属性
                .startProcessInstanceById(model.getId(),model.getVariables());// 使用流程定义的id启动流程实例
//        return "流程实例ID:" + processInstance.getId() +// 流程实例ID:
//                "流程定义ID:" + processInstance.getProcessDefinit
        return processInstance.getId();// 流程实例ID:
    }

    @Override
    public List findTask(String user) {
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssignee(user)//指定个人任务查询，指定办理人
//						.taskCandidateUser(candidateUser)//组任务的办理人查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.executionId(executionId)//使用执行对象ID查询
                /**排序*/
                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                /**返回结果集*/
//						.singleResult()//返回惟一结果集
//						.count()//返回结果集的数量
//						.listPage(firstResult, maxResults);//分页查询
                .list();//返回列表

        List<Map> resList = Lists.newArrayList();
        if(list!=null && list.size()>0){
            for(Task task:list){
                Map<String, Object> res = Maps.newHashMap();
                res.put("taskId", task.getId());
                res.put("name", task.getName());
                res.put("createTime", task.getCreateTime());
                res.put("assignee", task.getAssignee());
                res.put("processInstanceId", task.getProcessInstanceId());
                res.put("executionId", task.getExecutionId());
                res.put("processDefinitionId", task.getProcessDefinitionId());
                resList.add(res);
            }
        }

        return resList;
    }

    @Override
    public List<Map> findProcess1(String processDefinitionId) {
        List<ProcessInstance> ins = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processDefinitionId(processDefinitionId)
                /**查询条件（where部分）*/
                .list();

        List<Map> resList = Lists.newArrayList();
        if(ins!=null && ins.size()>0){
            for(ProcessInstance instance:ins){
                Map<String, Object> res = Maps.newHashMap();
                res.put("id", instance.getId());
                res.put("processInstanceId", instance.getProcessInstanceId());// 流程实例ID
                res.put("processDefinitionId", instance.getProcessDefinitionId());// 流程定义ID
                resList.add(res);
            }
        }

        return resList;
    }

    @Override
    public Map<String, Object> findTaskById(String taskId) {
        Task task = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskId(taskId).singleResult();
        Map<String, Object> res = Maps.newHashMap();
        res.put("任务ID", task.getId());
        res.put("任务名称", task.getName());
        res.put("任务创建时间", task.getCreateTime());
        res.put("任务办理人", task.getAssignee());
        res.put("流程实例ID", task.getProcessInstanceId());
        res.put("执行对象ID", task.getExecutionId());
        res.put("流程定义ID", task.getProcessDefinitionId());
        res.put("variables", task.getFormKey());
        res.put("taskVariables", task.getTaskDefinitionKey());

        return res;
    }

    @Override
    public Map findProcess(String id) {
        Task task = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                .processInstanceId(id)
                /**查询条件（where部分）*/
                .singleResult();
        Map<String, Object> res = Maps.newHashMap();
        res.put("任务ID", task.getId());
        res.put("任务名称", task.getName());
        res.put("任务创建时间", task.getCreateTime());
        res.put("任务办理人", task.getAssignee());
        res.put("流程实例ID", task.getProcessInstanceId());
        res.put("执行对象ID", task.getExecutionId());
        res.put("流程定义ID", task.getProcessDefinitionId());
        res.put("variables", task.getPriority());
        res.put("taskVariables", task.getTaskDefinitionKey());

        return res;
    }

    @Override
    public List<Map> findHistory(String id) {
        List<HistoricActivityInstance>  list= historyService // 历史相关Service
                .createHistoricActivityInstanceQuery() // 创建历史活动实例查询
                .processInstanceId(id)
//                .processInstanceId("2501") // 执行流程实例id
                .orderByHistoricActivityInstanceEndTime()
                .desc()
                .finished()
                .list();
        List<Map> resList = Lists.newArrayList();
        for(HistoricActivityInstance hai:list){
            Map<String, Object> res = Maps.newHashMap();

            res.put("id", hai.getId());
            res.put("processInstanceId:", hai.getProcessInstanceId());
            res.put("activityName", hai.getActivityName());//活动名称
            res.put("assignee", hai.getAssignee()); //办理人
            res.put("startTime", hai.getStartTime()); // 开始时间
            res.put("endTime",hai.getEndTime());//结束时间
            res.put("activityId",hai.getActivityId());//当前节点ID
            resList.add(res);
        }
        return resList;
    }

    @Override
    public List<Map> findHistoryInstance(String id) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        query.processInstanceId(id);
        query.orderByProcessDefinitionId().desc();
        query.orderByProcessInstanceEndTime().asc();
        List<HistoricProcessInstance> list = query.list();
        List<Map> resList = Lists.newArrayList();
        for(HistoricProcessInstance hpi:list){
            Map<String, Object> res = Maps.newHashMap();

            res.put("活动ID", hpi.getId());
            res.put("流程实例ID", hpi.getProcessDefinitionId());
            res.put("variables", hpi.getProcessDefinitionVersion());
            res.put("实例NAME", hpi.getState());
            res.put("开始时间：", hpi.getStartTime());
            res.put("结束时间：",hpi.getEndTime());
            res.put("KEY",hpi.getProcessDefinitionKey());
            resList.add(res);
        }
        return resList;
    }

    @Override
    public List<Map> findHistoryTask(String id) {
        HistoricTaskInstanceQuery query = processEngine.getHistoryService().createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> list = query.list();
        for ( HistoricTaskInstance hpi : list){
            System.out.println(hpi.getId() + ":" + hpi.getAssignee() + ":" + hpi.getName());
        }
        List<Map> resList = Lists.newArrayList();
        for(HistoricTaskInstance hti:list){
            Map<String, Object> res = Maps.newHashMap();

            res.put("活动ID", hti.getId());
            res.put("流程实例ID", hti.getProcessDefinitionId());
            res.put("variables", hti.getProcessDefinitionKey());
            res.put("实例NAME", hti.getName());
            res.put("开始时间：", hti.getStartTime());
            res.put("结束时间：",hti.getEndTime());
            res.put("Priority",hti.getPriority());
            res.put("Category",hti.getCaseDefinitionId());
            res.put("Assignee",hti.getAssignee());
            res.put("TaskLocalVariables",hti.getTaskDefinitionKey());
            res.put("ClaimTime",hti.getCaseDefinitionKey());
            res.put("DurationInMillis",hti.getDurationInMillis());
            res.put("ExecutionId",hti.getExecutionId());
            res.put("Owner",hti.getOwner());
            resList.add(res);
        }
        return resList;
    }

    @Override
    public List<Map> findVariableTask(String id) {
        HistoricVariableInstanceQuery query = historyService.createHistoricVariableInstanceQuery();
        List<HistoricVariableInstance> list = query.processInstanceId(id).orderByVariableName().desc().list();

        List<Map> resList = Lists.newArrayList();
        for(HistoricVariableInstance hvi:list){
            Map<String, Object> res = Maps.newHashMap();

            res.put("VariableInstanceID", hvi.getId());
            res.put("TaskID", hvi.getTaskId());
            res.put("variableName", hvi.getVariableName());
            res.put("value", hvi.getValue());
            res.put("time", hvi.getCreateTime());
            res.put("LastUpdatedTime：",hvi.getRemovalTime());
            res.put("ProcessInstanceId",hvi.getProcessInstanceId());
            res.put("CreateTime",hvi.getCreateTime());
            res.put("VariableTypeName",hvi.getVariableTypeName());
            resList.add(res);
        }
        return resList;
    }

    @Override
    public List<Map> findHistoryDetail(String id) {
        HistoricDetailQuery query = historyService.createHistoricDetailQuery();
        List<HistoricDetail> list = query.processInstanceId(id).list();
        // formProperties().list(); // 只取form参数 实体为HistoricFormProperty
        List<Map> resList = Lists.newArrayList();
        for(HistoricDetail hd:list){
            HistoricVariableUpdate hvu = (HistoricVariableUpdate)hd;
            Map<String, Object> res = Maps.newHashMap();

            res.put("historicDetailID", hvu.getId());
            res.put("TaskID", hvu.getTaskId());
            res.put("ActivityInstanceId", hvu.getActivityInstanceId());
            res.put("ExecutionId", hvu.getExecutionId());
            res.put("Time", hvu.getTime());
            res.put("ProcessInstanceId",hvu.getProcessInstanceId());
            res.put("value",hvu.getValue());
            res.put("Revision",hvu.getRevision());
            res.put("variableName",hvu.getVariableName());
            res.put("VariableTypeName",hvu.getVariableTypeName());
            resList.add(res);
        }
        return resList;
    }

    @Override
    public ProcessViewModel getAllElements(String processInstanceId){
        String processDefinitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();
        BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);

        if(ObjectUtils.isEmpty(bpmnModel)){
            return null;
        }

        Collection<ServiceTask> tasks = this.getAllServiceTask(bpmnModel);

        if(ObjectUtils.isEmpty(tasks)){
            for(ServiceTask task : tasks){
                ProcessViewModel taskSubViewModel = this.analysisServiceTaskModel(task);

            }
        }



        Model model = bpmnModel.getModel();
        Collection<ModelElementType> types = model.getTypes();

        for (ModelElementType type : types){
            log.info(type.getTypeName());
        }

//        Process process = bpmnModel.getgetProcesses().get(0);
//获取所有节点
//        Collection<FlowElement> flowElements = process.getFlowElements();
        return this.getMockData();
    }

    private Collection<ServiceTask> getAllServiceTask(BpmnModelInstance bpmnModel) {

        return bpmnModel.getModelElementsByType(ServiceTask.class);

    }

    private ProcessViewModel analysisServiceTaskModel(ServiceTask task) {

        ExtensionElements es = task.getExtensionElements();
        Collection<CamundaField> fields = es.getChildElementsByType(CamundaField.class);
        for(CamundaField field: fields){
            CamundaString cs = field.getCamundaString();
            log.info(cs.getTextContent());
            ProcessViewModel subProcessModel = this.getSubProcessModel(cs.getTextContent());
        }
        log.info(es.toString());
        return null;
    }

    private ProcessViewModel getSubProcessModel(String processDefinitionId) {
        BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
        List<NodeModel> nodes = Lists.newArrayList();
        if(ObjectUtils.isEmpty(bpmnModel)){
            return null;
        }

        Collection<ServiceTask> tasks = this.getAllServiceTask(bpmnModel);
        if(!ObjectUtils.isEmpty(tasks)){
            for(ServiceTask task : tasks){
                ProcessViewModel subProcessModel = this.analysisServiceTaskModel(task);
            }
        }
        Collection<UserTask> userTasks = this.getAllUserTask(bpmnModel);
        if(!ObjectUtils.isEmpty(userTasks)){

            for(UserTask userTask : userTasks){
                nodes.add(new NodeModel(userTask.getId(),"alps", userTask.getName(),null));
                Collection<SequenceFlow> inFlows = userTask.getIncoming();
                for (SequenceFlow inFlow : inFlows){
                    FlowNode flowNode = inFlow.getSource();
//                    flowNode.
                }

            }
        }
        ProcessViewModel model = new ProcessViewModel();
        model.setNodes(nodes);
        return model;
    }

    private Collection<UserTask> getAllUserTask(BpmnModelInstance bpmnModel) {
        return bpmnModel.getModelElementsByType(UserTask.class);
    }

    private ProcessViewModel getMockData() {
        ProcessViewModel result = new ProcessViewModel();
        List<NodeModel> nodes = Lists.newArrayList(
                new NodeModel("1","alps","开始", this.getNodeConfList(
                        Lists.newArrayList("日期"),
                        Lists.newArrayList("2020/3/3")
                )),
                new NodeModel("2","alps","施工方案", this.getNodeConfList(
                        Lists.newArrayList("施工人","日期"),
                        Lists.newArrayList("user1","2020/3/4")
                )),
                new NodeModel("3","alps","并行节点", null),
                new NodeModel("4","sql","开工申报表", this.getNodeConfList(
                        Lists.newArrayList("处理人","日期"),
                        Lists.newArrayList("user2","2020/3/5")
                )),
                new NodeModel("5","sql","执行时间", this.getNodeConfList(
                        Lists.newArrayList("备注"),
                        Lists.newArrayList("下午五点")
                )),
                new NodeModel("6","feature_etl","施工日志", this.getNodeConfList(
                        Lists.newArrayList("处理人","日期"),
                        Lists.newArrayList("user3","2020/3/6")
                )),
                new NodeModel("7","feature_etl","判断累计完成", null),
                new NodeModel("8","feature_extractor","合并执行", null),
                new NodeModel("9","feature_extractor","材料需求", this.getNodeConfList(
                        Lists.newArrayList("处理人","日期"),
                        Lists.newArrayList("user2","2020/3/7")
                )),
                new NodeModel("10","feature_extractor","采购子场景", this.getNodeConfList(
                        Lists.newArrayList("处理人","日期"),
                        Lists.newArrayList("user5","2020/3/8")
                )),
                new NodeModel("11","feature_extractor","到货确认", this.getNodeConfList(
                        Lists.newArrayList("处理人","日期"),
                        Lists.newArrayList("user6","2020/3/9")
                )),
                new NodeModel("12","feature_extractor","是否完成材料到场", null),
                new NodeModel("13","feature_extractor","结束", null)
        );
        List<EdgeModel> edges = Lists.newArrayList(
                new EdgeModel("1","2"),
                new EdgeModel("2","3"),
                new EdgeModel("3","4"),
                new EdgeModel("4","5"),
                new EdgeModel("5","6"),
                new EdgeModel("6","7"),
                new EdgeModel("7","8"),
                new EdgeModel("7","5"),
                new EdgeModel("3","9"),
                new EdgeModel("9","10"),
                new EdgeModel("10","11"),
                new EdgeModel("12","11"),
                new EdgeModel("11","12"),
                new EdgeModel("12","8"),
                new EdgeModel("8","13")
        );
        result.setNodes(nodes);
        result.setEdges(edges);
        return result;
    }

    private List<NodeConfModel> getNodeConfList(List<String> labels, List<String> values) {
        List<NodeConfModel> nodeConfModels = Lists.newArrayList();
        for (String label : labels){
            String value = values.get(labels.indexOf(label));
            nodeConfModels.add(new NodeConfModel(label,value));
        }
        return nodeConfModels;
    }

    public void triggerSignal(){
        List<Execution> executions =  runtimeService.createExecutionQuery().signalEventSubscriptionName("startEventSignal").list();
        for(Execution e:executions){
            runtimeService.signalEventReceived("signal", e.getId());
         }
    }

}

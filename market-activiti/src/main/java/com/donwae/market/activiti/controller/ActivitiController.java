package com.donwae.market.activiti.controller;

import com.donwae.market.activiti.entity.Response;
import com.donwae.market.activiti.model.DeployModel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/11 下午4:53
 */
@RestController
@Slf4j
@RequestMapping("/active")
public class ActivitiController {
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    //查看流程图
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void image(HttpServletResponse response,
                      @RequestParam String processInstanceId) {
        try {
            InputStream is = getDiagram(processInstanceId);
            if (is == null)
                return;

            response.setContentType("image/png");

            BufferedImage image = ImageIO.read(is);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);

            is.close();
            out.close();
        } catch (Exception ex) {
            log.error("查看流程图失败", ex);
        }
    }


    public InputStream getDiagram(String processInstanceId) {
        //获得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = StringUtils.EMPTY;
        if (processInstance == null) {
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory =
                    historyService.createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstanceId).singleResult();
            if (processInstanceHistory == null)
                return null;
            else
                processDefinitionId = processInstanceHistory.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        //使用宋体
        String fontName = "宋体";
        //获取BPMN模型对象
        BpmnModelInstance model = repositoryService.getBpmnModelInstance(processDefinitionId);
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = Collections.EMPTY_LIST;
        if (processInstance != null)
            currentActs = runtimeService.getActiveActivityIds(processInstance.getId());

        return null;
//                processEngine.getProcessEngineConfiguration()
//                .getProcessDiagramGenerator()
//                .generateDiagram(model, "png", currentActs, new ArrayList<String>(),
//                        fontName, fontName, fontName, null, 1.0);
    }

    //部署流程资源 部署单个
    @PostMapping("/deploy")
    public void deploy(@RequestBody DeployModel model){
        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name("Service审核流程")//声明流程的名称
                .addClasspathResource("processes/serviceTask.bpmn20.xml")//加载资源文件，一次只能加载一个文件
                .addClasspathResource("processes/serviceTask.png")//
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署时间："+deployment.getDeploymentTime());

    }

    //部署流程资源【第一种方式：classpath】
    @GetMapping("/deploy1")
    public void deploy1( ){
        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name("Service审核流程")//声明流程的名称
                .addClasspathResource("processes/serviceTask.bpmn20.xml")//加载资源文件，一次只能加载一个文件
                .addClasspathResource("processes/serviceTask.png")//
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署时间："+deployment.getDeploymentTime());

    }

    //部署流程资源【第二种方式：InputStream】
    @RequestMapping("/deploy2")
    public void deploy2( ) throws FileNotFoundException{
        //获取资源相对路径
        String bpmnPath = "processes/leave.bpmn";
        String pngPath = "processes/leave.png";
        //读取资源作为一个输入流
        FileInputStream bpmnfileInputStream = new FileInputStream(bpmnPath);
        FileInputStream pngfileInputStream = new FileInputStream(pngPath);

        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addInputStream("leave.bpmn",bpmnfileInputStream)
                .addInputStream("leave.png", pngfileInputStream)
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署时间："+deployment.getDeploymentTime());
    }

    //部署流程资源【第三种方式：InputStream】
    @RequestMapping("/deploy3")
    public void deploy3( ) throws FileNotFoundException {
        //字符串
        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions>...</definitions>";

        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addString("leave.bpmn",text)
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署时间："+deployment.getDeploymentTime());

    }

    //部署流程资源【第四种方式：zip/bar格式压缩包方式】
    @RequestMapping("/deploy4")
    public void deploy4( ) throws FileNotFoundException{
        //从classpath路径下读取资源文件
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("processes/leave.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addZipInputStream(zipInputStream)//使用zip方式部署，将leave.bpmn和leave.png压缩成zip格式的文件
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署时间："+deployment.getDeploymentTime());

    }

    @GetMapping("/findProcessInstance")
    public Response findProcessInstance(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义查询
                /*指定查询条件,where条件*/
                //.deploymentId(deploymentId)//使用部署对象ID查询
                //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
                //.processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
                //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /*排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                .list();//返回一个集合列表，封装流程定义
        //.singleResult();//返回唯一结果集
        //.count();//返回结果集数量
        //.listPage(firstResult, maxResults)//分页查询
        List<Map> resList = Lists.newArrayList();
        if(list != null && list.size()>0){
            for(ProcessDefinition processDefinition:list){
                System.out.println("流程定义ID:"+processDefinition.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义名称:"+processDefinition.getName());//对应HelloWorld.bpmn文件中的name属性值
                System.out.println("流程定义的key:"+processDefinition.getKey());//对应HelloWorld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+processDefinition.getVersion());//当流程定义的key值相同的情况下，版本升级，默认从1开始
                System.out.println("资源名称bpmn文件:"+processDefinition.getResourceName());
                System.out.println("资源名称png文件:"+processDefinition.getDiagramResourceName());
                System.out.println("部署对象ID:"+processDefinition.getDeploymentId());
                System.out.println("################################");
                Map<String, Object> res = Maps.newHashMap();
                res.put("流程ID", processDefinition.getId());
                res.put("流程定义名称", processDefinition.getName());
                res.put("流程定义的key", processDefinition.getKey());
                res.put("流程定义的版本", processDefinition.getVersion());
                res.put("资源名称bpmn文件", processDefinition.getResourceName());
                res.put("资源名称png文件", processDefinition.getDiagramResourceName());
                res.put("部署对象ID", processDefinition.getDeploymentId());
                resList.add(res);
            }
        }
        return Response.success(resList);
    }

    /**
     * 卸载流程定义
     * 2020/05/20 17:54:07
     * @author Jeremy Zhang
     */
    @GetMapping("/removeProcessInstance/{id}")
    public Response removeProcessInstance(@PathVariable("id") String id){
        //使用部署ID，完成删除
//        String deploymentId = id;
        /*
         * 不带级联的删除
         * 只能删除没有启动的流程，如果流程启动，就会抛出异常
         */
//        processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
//                        .deleteDeployment(deploymentId);
        /*
         * 能级联的删除
         * 能删除启动的流程，会删除和当前规则相关的所有信息，正在执行的信息，也包括历史信息
         */
        processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .deleteDeployment(id, true);

        System.out.println("删除成功");
        return Response.success("删除成功");
    }

    //10、启动流程实例--使用流程变量 userId
    @GetMapping("/startProcessInstance2")
    public String startProcessInstance2(@RequestParam("key") String yn) {
        // 流程定义的Key
        String processDefinitionKey = "ServiceTask";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userId", "user1");//这里传入id为user1的用户
        variables.put("chargeId", "user2");
        variables.put("managerId", "user3");
        variables.put("test", yn);
        ProcessInstance processInstance = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey,variables);// 使用流程定义的key启动流程实例，key对应leave.bpmn文件中id的属性
        return "流程实例ID:" + processInstance.getId() +// 流程实例ID:
        "流程定义ID:" + processInstance.getProcessDefinitionId();// 流
    }

    @RequestMapping("/findMyPersonalTask")
    public Response findMyPersonalTask(@RequestParam("user") String user){
        String assignee = user;
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
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
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("########################################################");
                Map<String, Object> res = Maps.newHashMap();
                res.put("任务ID", task.getId());
                res.put("任务名称", task.getName());
                res.put("任务创建时间", task.getCreateTime());
                res.put("任务办理人", task.getAssignee());
                res.put("流程实例ID", task.getProcessInstanceId());
                res.put("执行对象ID", task.getExecutionId());
                res.put("流程定义ID", task.getProcessDefinitionId());
                resList.add(res);
            }
        }

        return Response.success(resList);
    }

    //13、完成我的任务
    @GetMapping("/completeMyPersonalTask/{id}")
    public String completeMyPersonalTask(@PathVariable("id") String taskId, @RequestParam("sub") String submit) {
        // 任务ID
//        String taskId = "5007";
        //完成任务的同时，设置流程变量，根据流程变量的结果来节点进入哪一个节点任务
        Map<String, Object> args = new HashMap<>();
        args.put("submitType", submit);
        args.put("name", "Jack");
        args.put("submitTime", new Date().toString());
        args.put("message", "请假了");


        processEngine.getTaskService()// 与正在执行的任务管理相关的Service
                .complete(taskId, args);
        return "完成任务：任务ID：" + taskId;
    }

}
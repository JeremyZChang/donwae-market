package com.donwae.market.activiti.controller;

import com.donwae.market.activiti.entity.DiagramXml;
import com.donwae.market.activiti.entity.Response;
import com.donwae.market.activiti.model.DeployModel;
import com.donwae.market.activiti.repository.DiagramDao;
import com.donwae.market.activiti.service.ProcessLifeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/30 下午5:25
 */
@Slf4j
@RequestMapping("/life")
@RestController
public class ProcessLifeController {

    @Autowired
    private ProcessLifeService processLifeService;

    @Autowired
    private DiagramDao diagramDao;

    //部署流程资源 部署单个
    @PostMapping("/deploy/file")
    public Response deploySingleFile(@RequestBody DeployModel model){
        Map<String, Object> res = processLifeService.singleDeployProcess(model);
        return Response.success(res);
    }

    @PostMapping
    //部署流程资源【第三种方式：InputStream】
    @RequestMapping("/deploy/xml")
    public Response deployXml(@RequestBody DeployModel model) {
        DiagramXml dx = new DiagramXml();
        dx.setDiagramName(model.getResourceName());
        dx.setDiagramXml(model.getXml());
        diagramDao.addDiagramXml(dx);

        Map<String, Object> res = processLifeService.deployXmlProcess(model);
        return Response.success(res);
    }

    // 查询流程实例定义
    @GetMapping("/instance")
    public Response findProcessInstance(){
        List res = processLifeService.findProcessInstance();
        return Response.success(res);
    }

    /**
     * 卸载流程定义
     * 2020/05/20 17:54:07
     * @author Jeremy Zhang
     */
    @GetMapping("/remove/{id}")
    public Response removeProcessInstance(@PathVariable("id") String id){
        processLifeService.removeProcessInstance(id);
        return Response.success("删除成功");
    }

}

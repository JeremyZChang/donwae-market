package com.donwae.market.activiti.model;

import lombok.Data;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/25 上午12:15
 */
@Data
public class DeployModel {

    private String name;

    private String bpmnPath;

    private String picPath;

    private String xml;

    private String resourceName;

}

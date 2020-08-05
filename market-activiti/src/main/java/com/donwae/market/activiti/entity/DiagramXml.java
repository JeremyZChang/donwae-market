package com.donwae.market.activiti.entity;

import lombok.Data;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/7/1 下午1:36
 */
@Data
public class DiagramXml {

    private Integer id;
    // 流程xml文件
    private String diagramXml;
    // 流程名称
    private String diagramName;
}

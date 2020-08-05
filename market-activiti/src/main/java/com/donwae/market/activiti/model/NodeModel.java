package com.donwae.market.activiti.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/7/28 上午9:54
 */
@Data
@AllArgsConstructor
public class NodeModel {

    private String id;
    private String dataType;
    private String name;
    private List<NodeConfModel> conf;

}

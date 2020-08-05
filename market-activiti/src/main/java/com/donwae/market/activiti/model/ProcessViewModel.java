package com.donwae.market.activiti.model;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/7/28 上午9:51
 */
@Data
public class ProcessViewModel {

    private List<NodeModel> nodes;

    private List<EdgeModel> edges;
}

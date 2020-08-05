package com.donwae.market.activiti.model;

import lombok.Data;

import java.util.Map;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/25 上午12:15
 */
@Data
public class StartProcessModel {

    private String id;

    private Map<String, Object> variables;

}

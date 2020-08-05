package com.donwae.market.activiti.model;

import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/25 上午12:15
 */
@Data
public class ComplateProcessModel {
    private String taskId;
    private Map<String, Object> variables;

}

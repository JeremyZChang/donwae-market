package com.donwae.market.activiti.repository;

import com.donwae.market.activiti.entity.DiagramXml;

import java.util.List;


public interface DiagramDao {

	List<DiagramXml> getAll();

	Integer addDiagramXml(DiagramXml diagramXml);
}

/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.math.BigDecimal;

/**
 * <p>
 * Title: 执行计划
 * </p>
 * <p>
 * Description: DbManager sql 语句执行计划
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author YuanBaoKun
 * @version 1.0
 */
public class Db2DataRecord {

	private String operation; // Operation -- OPERATOR_TYPE
	private BigDecimal subCost; // Subtree Cost --
	private String nodeCost; // Node Cost --
	private String ioCost; // IO Cost -- IO_COST;
	private String cpuCost; // CPU Cost -- CPU_COST;
	private String objSchema; // Obj Schema -- OBJECT_SCHEMA
	private String objName; // Obj Name -- OBJECT_NAME
	private String firstRowCost; // First Row Cost -- FIRST_ROW_COST
	private String reTotalCost; // RE Total Cost-- RE_TOTAL_COST
	private String reIOCost; // RE IO Cost -- RE_IO_COST
	private String reCpuCost; // RE CPU Cost -- RE_CPU_COST
	private String comCost; // Commun.Cost -- COMM_COST;
	private String firstComCost; // First Commun.Cost -- FIRST_COMM_COST
	private String remoteComCost; // Remote Commun.Cost -- REMOTE_COMM_COST
	private String remoteTotalCost; // Remote Total Cost -- REMOTE_TOTAL_COST
	private String streamCount; // Stream Count -- STREAM_COUNT
	private String buffers; // Buffers -- BUFFERS
	private String columnCount; // Column Count -- COLUMN_COUNT
	private String expRequester; // Explain REquester -- EXPLAIN_REQUESTER
	private String explainTime; // Explain Time -- EXPLAIN_TIME
	private String sourceName; // Source Name -- SOURCE_NAME
	private String sourceSchema; // Source Schema-- SOURCE_SCHEMA
	private String explianLevel; // Explain Level-- EXPLAIN_LEVEL
	private String sectionNumber; // Section Number -- SECTNO
	private String streamId; // Stream ID -- S.STREAM_ID
	private String sourceType; // Source Type -- SOURCE_TYPE
	private String targetType; // Target Type -- TARGET_TYPE
	private String columnNames; // Column Names -- COLUMN_NAMES
	private String pmid; // PMID -- PMID
	private String singleNode; // Single Node -- SINGLE_NODE
	private String partitionColumns;// Partition Columns -- PARTITION_COLUMNS
	private String operatorType; // Operator Type-- OPERATOR_TYPE

	private String operatorId; // OPERATOR_ID
	private String sourceId; // SOURCE_ID
	private String targetId; // TARGET_ID

	/**
	 * 以下为该类的所有属性的 getter 和 setter 方法
	 */

	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getBuffers() {
		return buffers;
	}
	public void setBuffers(String buffers) {
		this.buffers = buffers;
	}
	public String getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(String columnCount) {
		this.columnCount = columnCount;
	}
	public String getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}
	public String getComCost() {
		return comCost;
	}
	public void setComCost(String comCost) {
		this.comCost = comCost;
	}
	public String getCpuCost() {
		return cpuCost;
	}
	public void setCpuCost(String cpuCost) {
		this.cpuCost = cpuCost;
	}
	public String getExplainTime() {
		return explainTime;
	}
	public void setExplainTime(String explainTime) {
		this.explainTime = explainTime;
	}
	public String getExplianLevel() {
		return explianLevel;
	}
	public void setExplianLevel(String explianLevel) {
		this.explianLevel = explianLevel;
	}
	public String getExpRequester() {
		return expRequester;
	}
	public void setExpRequester(String expRequester) {
		this.expRequester = expRequester;
	}
	public String getFirstComCost() {
		return firstComCost;
	}
	public void setFirstComCost(String firstComCost) {
		this.firstComCost = firstComCost;
	}
	public String getFirstRowCost() {
		return firstRowCost;
	}
	public void setFirstRowCost(String firstRowCost) {
		this.firstRowCost = firstRowCost;
	}
	public String getIoCost() {
		return ioCost;
	}
	public void setIoCost(String ioCost) {
		this.ioCost = ioCost;
	}
	public String getNodeCost() {
		return nodeCost;
	}
	public void setNodeCost(String nodeCost) {
		this.nodeCost = nodeCost;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getObjSchema() {
		return objSchema;
	}
	public void setObjSchema(String objSchema) {
		this.objSchema = objSchema;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	public String getPartitionColumns() {
		return partitionColumns;
	}
	public void setPartitionColumns(String partitionColumns) {
		this.partitionColumns = partitionColumns;
	}
	public String getPmid() {
		return pmid;
	}
	public void setPmid(String pmid) {
		this.pmid = pmid;
	}
	public String getReCpuCost() {
		return reCpuCost;
	}
	public void setReCpuCost(String reCpuCost) {
		this.reCpuCost = reCpuCost;
	}
	public String getReIOCost() {
		return reIOCost;
	}
	public void setReIOCost(String reIOCost) {
		this.reIOCost = reIOCost;
	}
	public String getRemoteComCost() {
		return remoteComCost;
	}
	public void setRemoteComCost(String remoteComCost) {
		this.remoteComCost = remoteComCost;
	}
	public String getRemoteTotalCost() {
		return remoteTotalCost;
	}
	public void setRemoteTotalCost(String remoteTotalCost) {
		this.remoteTotalCost = remoteTotalCost;
	}
	public String getReTotalCost() {
		return reTotalCost;
	}
	public void setReTotalCost(String reTotalCost) {
		this.reTotalCost = reTotalCost;
	}
	public String getSectionNumber() {
		return sectionNumber;
	}
	public void setSectionNumber(String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}
	public String getSingleNode() {
		return singleNode;
	}
	public void setSingleNode(String singleNode) {
		this.singleNode = singleNode;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getSourceSchema() {
		return sourceSchema;
	}
	public void setSourceSchema(String sourceSchema) {
		this.sourceSchema = sourceSchema;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getStreamCount() {
		return streamCount;
	}
	public void setStreamCount(String streamCount) {
		this.streamCount = streamCount;
	}
	public String getStreamId() {
		return streamId;
	}
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	public BigDecimal getSubCost() {
		return subCost;
	}
	public void setSubCost(BigDecimal subCost) {
		this.subCost = subCost;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

}

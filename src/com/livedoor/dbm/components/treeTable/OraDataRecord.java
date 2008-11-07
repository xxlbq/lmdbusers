/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

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
public class OraDataRecord {

	private String operation; // operation -- operation

	private String cost; // Node Cost -- cost

	private String ioCost; // IO Cost -- io_cost

	private String cpuCost; // CPU Cost -- cpu_cost

	private String cardinality; // Cardinality -- cardinality

	private String bytes; // Bytes -- bytes

	private String position; // Position -- position

	private String objOwner; // Obj Owner -- object_owner

	private String objName; // Obj Name -- object_name

	private String options; // Options -- options

	private String objType; // Obj Type -- object_type

	private String optimizer; // Optimizer -- optimizer

	private String objInstance; // Obj Instance -- object_instance

	private String remarks; // Remarks -- remarks

	private String objNode; // Obj Node -- object_node

	private String searchColumns; // Search Columns -- search_columns

	private String otherTag; // Other Tag -- other_tag

	private String partitionStart; // Partition Start --partition_start

	private String partitionStop; // Partition Stop --partition_stop

	private String partitionId; // Partition_Id -- partition_id

	private String other; // Other -- other

	private String distribution; // Distribution -- distribution

	private String tempSpace; // Temp Space -- temp_space

	private String id;

	private String parent;

	/**
	 * 以下为该类的所有属性的 getter 和 setter 方法
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	public String getCardinality() {
		return cardinality;
	}

	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCpuCost() {
		return cpuCost;
	}

	public void setCpuCost(String cpuCost) {
		this.cpuCost = cpuCost;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public String getIoCost() {
		return ioCost;
	}

	public void setIoCost(String ioCost) {
		this.ioCost = ioCost;
	}

	public String getObjInstance() {
		return objInstance;
	}

	public void setObjInstance(String objInstance) {
		this.objInstance = objInstance;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getObjNode() {
		return objNode;
	}

	public void setObjNode(String objNode) {
		this.objNode = objNode;
	}

	public String getObjOwner() {
		return objOwner;
	}

	public void setObjOwner(String objOwner) {
		this.objOwner = objOwner;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOptimizer() {
		return optimizer;
	}

	public void setOptimizer(String optimizer) {
		this.optimizer = optimizer;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getOtherTag() {
		return otherTag;
	}

	public void setOtherTag(String otherTag) {
		this.otherTag = otherTag;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	public String getPartitionStart() {
		return partitionStart;
	}

	public void setPartitionStart(String partitionStart) {
		this.partitionStart = partitionStart;
	}

	public String getPartitionStop() {
		return partitionStop;
	}

	public void setPartitionStop(String partitionStop) {
		this.partitionStop = partitionStop;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSearchColumns() {
		return searchColumns;
	}

	public void setSearchColumns(String searchColumns) {
		this.searchColumns = searchColumns;
	}

	public String getTempSpace() {
		return tempSpace;
	}

	public void setTempSpace(String tempSpace) {
		this.tempSpace = tempSpace;
	}

}

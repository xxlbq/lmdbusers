/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.util.List;

import com.livedoor.dbm.i18n.ResourceI18n;

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
public class Db2DataModel extends AbstractTreeTableModel implements TreeTableModel {

	// Names of the columns.
	static protected String[] cNames = {"Operation", "Subtree Cost", "Node Cost", "IO Cost", "CPU Cost", "Obj Schema", "Obj Name",
			"First Row Cost", "RE Total Cost", "RE IO Cost", "RE CPU Cost", "Commun. Cost", "First Commun. Cost", "Remote Commun. Cost",
			"Remote Total Cost", "Stream Count", "Buffers", "Column Count", "Explain Requester", "Explain Time", "Source Name",
			"Source Schema", "Explain Level", "Section Number", "Stream ID", "Source Type", "Target Type", "Column Names", "PMID",
			"Single Node", "Partition Columns", "Operator Type"};

	// Types of the columns.
	static protected Class[] cTypes = {TreeTableModel.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};

	// The returned file length for directories.
	public static final Integer ZERO = new Integer(0);

	private List listData;

	/**
	 * [功 能] 查询计划数据处理
	 * <p>
	 * 
	 * @param listData
	 *            结果集
	 */
	public Db2DataModel(List listData) {
		super(new Db2DataNode((Db2DataRecord) listData.get(0)));
		this.listData = listData;
	}

	/**
	 * [功 能] 获取节点对象
	 * <p>
	 * [说 明] 获取节点对象
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param node
	 *            节点
	 *            <p>
	 * 
	 * @return Db2DataRecord
	 *         <p>
	 */
	protected Db2DataRecord getNode(Object node) {
		Db2DataNode dataNode = ((Db2DataNode) node);
		return dataNode.getDataRecord();
	}

	/**
	 * [功 能] 获取子节点
	 * <p>
	 * [说 明] 获取子节点
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param node
	 *            节点
	 *            <p>
	 * 
	 * @return Object[] 子节点
	 *         <p>
	 */
	protected Object[] getChildren(Object node) {
		Db2DataNode dataNode = ((Db2DataNode) node);
		return dataNode.getChildren(this.listData);
	}

	/**
	 * [功 能] 获取子节点数量
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param node
	 *            节点
	 *            <p>
	 * 
	 * @return int 数量
	 *         <p>
	 */
	public int getChildCount(Object node) {
		Object[] children = getChildren(node);
		return (children == null) ? 0 : children.length;
	}

	/**
	 * [功 能] 获取指定顺序的子节点
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param node
	 *            节点
	 * @param int
	 *            节点顺序
	 *            <p>
	 * 
	 * @return Object 子节点
	 *         <p>
	 */
	public Object getChild(Object node, int i) {
		return getChildren(node)[i];
	}

	/**
	 * [功 能] 判断是否为叶子节点
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param node
	 *            节点
	 *            <p>
	 * 
	 * @return boolean
	 *         <p>
	 */
	public boolean isLeaf(Object node) {

		boolean bRet = true;
		Db2DataRecord dr = getNode(node);

		for (int i = 0; i < listData.size(); i++) {
			if (((Db2DataRecord) listData.get(i)).getTargetId().equals(dr.getOperatorId())) {
				bRet = false;
				break;
			}
		}

		if (bRet == false) {
			((DataNode) node).setCollapseIcon(ResourceI18n.getImage("TREETAB_NOT_LEAF_COL"));
			((DataNode) node).setExpandedIcon(ResourceI18n.getImage("TREETAB_NOT_LEAF_EXP"));
		} else {
			((DataNode) node).setCollapseIcon(ResourceI18n.getImage("TREETAB_LEAF"));
		}

		return bRet;
	}

	/**
	 * [功 能] 获取列数量
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return int 数量
	 *         <p>
	 */
	public int getColumnCount() {
		return cNames.length;
	}

	/**
	 * [功 能] 获取指定列的名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param int
	 *            列
	 *            <p>
	 * 
	 * @return String 列名
	 *         <p>
	 */
	public String getColumnName(int column) {
		return cNames[column];
	}

	/**
	 * [功 能] 获取指定列的类型
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param int
	 *            列
	 *            <p>
	 * 
	 * @return class 类型
	 *         <p>
	 */
	public Class getColumnClass(int column) {
		return cTypes[column];
	}

	/**
	 * [功 能] 获取单元格的值
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param node
	 *            节点
	 * @param int
	 *            列
	 *            <p>
	 * 
	 * @return object 值
	 *         <p>
	 */
	public Object getValueAt(Object node, int column) {
		Db2DataRecord dataRecord = getNode(node);

		try {
			switch (column) {
				case 0 :
					return dataRecord.getOperation();
				case 1 :
					return dataRecord.getSubCost();
				case 2 :
					return dataRecord.getNodeCost();
				case 3 :
					return dataRecord.getIoCost();
				case 4 :
					return dataRecord.getCpuCost();
				case 5 :
					return dataRecord.getObjSchema();
				case 6 :
					return dataRecord.getObjName();
				case 7 :
					return dataRecord.getFirstRowCost();
				case 8 :
					return dataRecord.getReTotalCost();
				case 9 :
					return dataRecord.getReIOCost();
				case 10 :
					return dataRecord.getReCpuCost();
				case 11 :
					return dataRecord.getComCost();
				case 12 :
					return dataRecord.getFirstComCost();
				case 13 :
					return dataRecord.getRemoteComCost();
				case 14 :
					return dataRecord.getRemoteTotalCost();
				case 15 :
					return dataRecord.getStreamCount();
				case 16 :
					return dataRecord.getBuffers();
				case 17 :
					return dataRecord.getColumnCount();
				case 18 :
					return dataRecord.getExpRequester();
				case 19 :
					return dataRecord.getExplainTime();
				case 20 :
					return dataRecord.getSourceName();
				case 21 :
					return dataRecord.getSourceSchema();
				case 22 :
					return dataRecord.getExplianLevel();
				case 23 :
					return dataRecord.getSectionNumber();
				case 24 :
					return dataRecord.getStreamId();
				case 25 :
					return dataRecord.getSourceType();
				case 26 :
					return dataRecord.getTargetType();
				case 27 :
					return dataRecord.getColumnNames();
				case 28 :
					return dataRecord.getPmid();
				case 29 :
					return dataRecord.getSingleNode();
				case 30 :
					return dataRecord.getPartitionColumns();
				case 31 :
					return dataRecord.getOperatorType();
			}
		} catch (SecurityException se) {
		}

		return null;
	}
}
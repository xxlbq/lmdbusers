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
public class OracleDataModel extends AbstractTreeTableModel implements
		TreeTableModel {

	// Names of the columns.
	static protected String[] cNames = { "Operation", "Node Cost", "IO Cost",
			"CPU Cost", "Cardinality", "Bytes", "Position", "Obj Owner",
			"Obj Name", "Options", "Obj Type", "Optimizer", "Obj Instance",
			"Remarks", "Obj Node", "Search Columns", "Other Tag",
			"Partition Start", "Partition Stop", "Partition Id", "Other",
			"Distribution", "Temp Space" };

	// Types of the columns.
	static protected Class[] cTypes = { TreeTableModel.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class };

	// The the returned file length for directories.
	public static final Integer ZERO = new Integer(0);

	private List listData;

	/**
	 * [功 能] 查询计划数据处理
	 * <p>
	 * @param listData 结果集
	 */
	public OracleDataModel(List listData) {
		super(new OracleDataNode((OraDataRecord) listData.get(0)));
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
	 * @param node 节点
	 *            <p>
	 * 
	 * @return Db2DataRecord
	 *         <p>
	 */
	protected OraDataRecord getNode(Object node) {
		OracleDataNode dataNode = ((OracleDataNode) node);
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
	 * @param node 节点
	 *            <p>
	 * 
	 * @return Object[] 子节点
	 *         <p>
	 */
	protected Object[] getChildren(Object node) {
		OracleDataNode dataNode = ((OracleDataNode) node);
		return dataNode.getChildren(this.listData);
	}

	/**
	 * [功 能] 获取子节点数量
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * @param node 节点
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
	 * @param node 节点
	 * @param int 节点顺序
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
	 * @param node 节点
	 *            <p>
	 * 
	 * @return boolean
	 *         <p>
	 */
	public boolean isLeaf(Object node) {

		boolean bRet = true;
		OraDataRecord dr = getNode(node);

		for (int i = 0; i < listData.size(); i++) {
			if (((OraDataRecord) listData.get(i)).getParent()
					.equals(dr.getId())) {
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
	 * @param int 列
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
	 * @param int 列
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
	 * @param node 节点
	 * @param int 列
	 *            <p>
	 * 
	 * @return object 值
	 *         <p>
	 */
	public Object getValueAt(Object node, int column) {
		OraDataRecord dataRecord = getNode(node);

		try {
			switch (column) {
			case 0:
				return dataRecord.getOperation();
			case 1:
				return dataRecord.getCost();
			case 2:
				return dataRecord.getIoCost();
			case 3:
				return dataRecord.getCpuCost();
			case 4:
				return dataRecord.getCardinality();
			case 5:
				return dataRecord.getBytes();
			case 6:
				return dataRecord.getPosition();
			case 7:
				return dataRecord.getObjOwner();
			case 8:
				return dataRecord.getObjName();
			case 9:
				return dataRecord.getOptions();
			case 10:
				return dataRecord.getObjType();
			case 11:
				return dataRecord.getOptimizer();
			case 12:
				return dataRecord.getObjInstance();
			case 13:
				return dataRecord.getRemarks();
			case 14:
				return dataRecord.getObjNode();
			case 15:
				return dataRecord.getSearchColumns();
			case 16:
				return dataRecord.getOtherTag();
			case 17:
				return dataRecord.getPartitionStart();
			case 18:
				return dataRecord.getPartitionStop();
			case 19:
				return dataRecord.getPartitionId();
			case 20:
				return dataRecord.getOther();
			case 21:
				return dataRecord.getDistribution();
			case 22:
				return dataRecord.getTempSpace();
			}
		} catch (SecurityException se) {
		}

		return null;
	}
}
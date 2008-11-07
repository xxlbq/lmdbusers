/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.util.ArrayList;
import java.util.List;

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
public class OracleDataNode extends DataNode{
	
	private OraDataRecord dataRecord;

	private Object[] children;

	public OracleDataNode(OraDataRecord dataRecord) {
		//super("", null, Color.black, "TREE_CONN_COLUMN", "TREE_CONN_COLUMN");
		this.dataRecord = dataRecord;

		//super.setCollapseIcon(ResourceI18n.getImage("TREE_CONN_COLUMN"));
		//super.setExpandedIcon(ResourceI18n.getImage("TREE_CONN_COLUMN"));
	}

	/**
	 * 返回节点的值
	 */
	public String toString() {
		return dataRecord.getOperation();
	}
	
	/**
	 * [功 能] 获取节点的对象
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return Db2DataRecord 节点对象
	 *         <p>
	 */
	public OraDataRecord getDataRecord() {
		return dataRecord;
	}

	/**
	 * [功 能] 获取子节点
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param list 所有的节点
	 * 
	 * @return Object[] 子节点
	 *         <p>
	 */
	protected Object[] getChildren(List ll) {
		if (children != null) {
			return children;
		}
		try {
			
			int ii = Integer.parseInt(dataRecord.getId());
			List temList = new ArrayList();
			
			for(int i = 0; i < ll.size(); i++)
			{
				OraDataRecord temDR = (OraDataRecord)ll.get(i);
				
				String str = temDR.getParent();
				
				if(temDR.getParent() == null || str.trim().length() == 0)
				{
					continue;
				}
				else if(Integer.parseInt(temDR.getParent()) == ii)
				{
					temList.add(new OracleDataNode(temDR));
				}
			}
			
			children = temList.toArray();
			
		} catch (SecurityException se) {
		}
		return children;
	}

}

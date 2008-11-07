/**
 * 
 */
package com.livedoor.dbm.components.mainframe.createdt.model;

import java.util.List;

/**
 * <p>
 * Title:修改mysql表字段扩展属性的模型
 * <p>
 * Description:修改mysql表字段扩展属性的模型
 * <p>
 * Copyright: Copyright (c) 2006-10-18
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */

public class AlterMysqlSlaveTableModel extends MysqlSlaveTableModel {

	private static final long serialVersionUID = -4222067961775644764L;

	/**
	 * @param masterPropertyValueList
	 * @param slavePropertyValueList
	 */
	public AlterMysqlSlaveTableModel(List masterPropertyValueList, List<List> slavePropertyValueList) {
		super(masterPropertyValueList, slavePropertyValueList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.MysqlSlaveTableModel#isCellEditable(int,
	 *      int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return super.isCellEditable(rowIndex, columnIndex);
	}

}

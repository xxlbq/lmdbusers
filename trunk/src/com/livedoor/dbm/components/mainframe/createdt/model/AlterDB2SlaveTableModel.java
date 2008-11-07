/**
 * 
 */
package com.livedoor.dbm.components.mainframe.createdt.model;

import java.util.List;

/**
 * <p>
 * Title: 修改DB2数据库表字段定义的扩展信息的TableModel
 * <p>
 * Description: 提供了修改DB2数据库表字段扩展信息的操作
 * <p>
 * Copyright: Copyright (c) 2006-10-17
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class AlterDB2SlaveTableModel extends DB2SlaveTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6929060607969360185L;

	/**
	 * @param paramsNameList
	 * @param paramsValueList
	 */
	public AlterDB2SlaveTableModel(List paramsNameList, List paramsValueList) {
		super(paramsNameList, paramsValueList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (super.isOldRow() && (rowIndex == 0 || rowIndex == 3))
			return false;
		else
			return super.isCellEditable(rowIndex, columnIndex);
	}

}

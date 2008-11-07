/**
 * 
 */
package com.livedoor.dbm.components.mainframe.createdt.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.ColumnProperty;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.scripts.AbstractSqlBuilder;
import com.livedoor.dbm.scripts.SqlBuilderFactory;

/**
 * <p>
 * Title: db2数据库修改表字段定义的扩展信息的TableModel
 * <p>
 * Description: 实现了修改db2数据库字段扩展信息的操作
 * <p>
 * Copyright: Copyright (c) 2006-10-17
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */

public class AlterDB2MasterTableModel extends CreateDB2MasterTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5628518856872054523L;
	private int existsFieldCount;// 数据库原有的字段数量
	private List<Integer> updatedRow;// 记录所有修改过的字段的行号
	private List<List> oldMasterPropertyValueList;// 保存原始的主要属性值
	private List<List> oldSlavePropertyValueList;// 保存原始的扩展属性值
	private List<String> oldPrimaryKeyList; // 保存原始主键列名

	/**
	 * @param masterPropertyValueList
	 * @param slavePropertyValueList
	 */
	@SuppressWarnings("unchecked")
	public AlterDB2MasterTableModel(List<List> masterPropertyValueList, List<List> slavePropertyValueList) {
		super(masterPropertyValueList, slavePropertyValueList);
		existsFieldCount = masterPropertyValueList.size() - 1;
		updatedRow = new ArrayList();
		this.oldMasterPropertyValueList = super.cloneList(masterPropertyValueList);
		this.oldSlavePropertyValueList = super.cloneList(slavePropertyValueList);
		this.oldMasterPropertyValueList.remove(this.oldMasterPropertyValueList.size() - 1);
		this.oldSlavePropertyValueList.remove(this.oldSlavePropertyValueList.size() - 1);
		oldPrimaryKeyList = super.getParamKey();
		existsFieldCount = masterPropertyValueList.size() - 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#deleteRow(int)
	 */
	@Override
	public void deleteRow(int rowIndex) {
		if (rowIndex >= this.existsFieldCount)
			super.deleteRow(rowIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#getColorAt(int,
	 *      int)
	 */
	@Override
	public Color getColorAt(int rowIndex, int colIndex) {
		if (rowIndex >= this.existsFieldCount)
			return Color.GREEN;
		Integer rowId = Integer.valueOf(rowIndex);
		if (this.updatedRow.contains(rowId))
			return Color.BLUE;

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		if (rowIndex >= this.existsFieldCount ||  columnIndex == 2 || columnIndex == 4)
			return super.isCellEditable(rowIndex, columnIndex);
		else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);

		// 如果修改了已经存在的字段,记录修改的字段的行号
		if (rowIndex < this.existsFieldCount && columnIndex < 4) {
			Integer rowId = Integer.valueOf(rowIndex);
			if (!this.updatedRow.contains(rowId))
				this.updatedRow.add(rowId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#getSql(com.livedoor.dbm.connection.ConnectionInfo,
	 *      com.livedoor.dbm.db.DBSession, com.livedoor.dbm.db.DBMDataMetaInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getSql(ConnectionInfo connInfo, DBSession dbSession, DBMDataMetaInfo dataMetaInfo) {
		List tmp;
		AbstractSqlBuilder sqlBuider = SqlBuilderFactory.createSqlBuilder(connInfo, dbSession, dataMetaInfo);
		String sql = "";


		// 取得所有修改过的字段信息
		List updated = new ArrayList();
		HashMap rowInfo;
		for (int i = 0; i < this.updatedRow.size(); i++) {
			int rowIndex = this.updatedRow.get(i);
			tmp = this.oldMasterPropertyValueList.get(rowIndex);
			rowInfo = super.getRowData(rowIndex);
			rowInfo.put(ColumnProperty.OLD_COLUMN_NAME, tmp.get(0));
			rowInfo.put(ColumnProperty.ALLOW_NULL,tmp.get(3));
			updated.add(rowInfo);
		}

		for (int i = 0; i < this.oldMasterPropertyValueList.size(); i++) {
			tmp = this.oldSlavePropertyValueList.get(i);
			if (!this.updatedRow.contains(Integer.valueOf(i)) && !tmp.equals(super.getSlavePropertyValueList().get(i))) {
				rowInfo = super.getRowData(i);
				tmp = this.oldMasterPropertyValueList.get(i);
				rowInfo.put(ColumnProperty.OLD_COLUMN_NAME, tmp.get(0));
				rowInfo.put(ColumnProperty.ALLOW_NULL,tmp.get(3));
				updated.add(rowInfo);
			}
		}
		sql = sql + sqlBuider.alterModify(updated);

		// 取得所有新增字段信息
		List insert = new ArrayList();
		for (int i = this.existsFieldCount; i < super.getRowCount(); i++) {
			rowInfo = super.getRowData(i);
			insert.add(rowInfo);
		}
		sql = sql + sqlBuider.alterAdd(insert);

		// 取得主键是否更改过
		List newPrimaryKeyList = super.getParamKey();
		if (!super.objectEquals(oldPrimaryKeyList, newPrimaryKeyList)) {
			sql = sql + sqlBuider.alterPrimary(newPrimaryKeyList);
		}

		return sql;
	}
}

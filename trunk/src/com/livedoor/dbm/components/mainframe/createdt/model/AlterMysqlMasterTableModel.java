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
 * Title:修改mysql表字段主要属性的模型
 * <p>
 * Description:修改mysql表字段主要属性的模型
 * <p>
 * Copyright: Copyright (c) 2006-10-18
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class AlterMysqlMasterTableModel extends CreateMysqlMasterTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1626155326410240143L;

	private int existsColumnCount;// 表中原有字段数量

	private List<Integer> updatedRow;// 记录所有修改过的字段的行号
	private List<Integer> deletedRow;// 记录所有标记为删除的字段的行号
	private List<List> oldMasterPropertyValueList;// 保存原始字段主要属性值
	private List<List> oldSlavePropertyValueList;// 保存原始字段扩展属性值
	private List<String> oldPrimaryKeyList; // 保存原始主键信息

	/**
	 * 构造函数
	 * 
	 * @param masterPropertyValueList
	 * @param slavePropertyValueList
	 */
	@SuppressWarnings("unchecked")
	public AlterMysqlMasterTableModel(List<List> masterPropertyValueList, List<List> slavePropertyValueList) {
		super(masterPropertyValueList, slavePropertyValueList);

		this.updatedRow = new ArrayList();
		this.deletedRow = new ArrayList();

		// 复制原始字段信息
		this.oldMasterPropertyValueList = super.cloneList(masterPropertyValueList);
		this.oldSlavePropertyValueList = super.cloneList(slavePropertyValueList);
		// 删除空行
		this.oldMasterPropertyValueList.remove(this.oldMasterPropertyValueList.size() - 1);
		this.oldSlavePropertyValueList.remove(this.oldSlavePropertyValueList.size() - 1);
		// 取得原始主键信息
		oldPrimaryKeyList = super.getParamKey();
		existsColumnCount = masterPropertyValueList.size() - 1;
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

		// 取得所有删除的字段名
		List deleted = new ArrayList();
		for (int i = 0; i < this.deletedRow.size(); i++) {
			tmp = this.oldMasterPropertyValueList.get(this.deletedRow.get(i));
			deleted.add(tmp.get(0));
		}
		sql = sql + sqlBuider.alterDrop(deleted);
		// 取得所有修改过的字段信息
		List updated = new ArrayList();
		HashMap rowInfo;
		for (int i = 0; i < this.updatedRow.size(); i++) {
			int rowIndex = this.updatedRow.get(i);
			// 过滤掉包含在删除队列里的修改操作
			if (!deletedRow.contains(Integer.valueOf(rowIndex))) {
				tmp = this.oldMasterPropertyValueList.get(rowIndex);
				rowInfo = super.getRowData(rowIndex);
				rowInfo.put(ColumnProperty.OLD_COLUMN_NAME, tmp.get(0));
				rowInfo.put(ColumnProperty.ALLOW_NULL, tmp.get(3));
				updated.add(rowInfo);
			}
		}

		// 检查所有从属性是否有修改
		for (int i = 0; i < this.oldMasterPropertyValueList.size(); i++) {
			tmp = this.oldSlavePropertyValueList.get(i);
			if (!this.deletedRow.contains(Integer.valueOf(i)) && !this.updatedRow.contains(Integer.valueOf(i))
					&& !equalsSlavePropertyValueList(tmp,(List)getSlavePropertyValueList().get(i))) {
				rowInfo = super.getRowData(i);
				tmp = this.oldMasterPropertyValueList.get(i);
				rowInfo.put(ColumnProperty.OLD_COLUMN_NAME, tmp.get(0));
				rowInfo.put(ColumnProperty.ALLOW_NULL, tmp.get(3));
				updated.add(rowInfo);
			}
		}
		sql = sql + sqlBuider.alterModify(updated);

		// 取得所有新增字段信息
		List insert = new ArrayList();
		for (int i = this.existsColumnCount; i < super.getRowCount(); i++) {
			rowInfo = super.getRowData(i);
			insert.add(rowInfo);
		}
		sql = sql + sqlBuider.alterAdd(insert);

		// 取得主键是否更改过
		List newPrimaryKeyList = super.getParamKey();
		if (!super.objectEquals(oldPrimaryKeyList, newPrimaryKeyList)) {
			sql = sql + sqlBuider.alterPrimary(newPrimaryKeyList);
		}

		// 将多条sql合并为一条,这样可以解决出错之后不能回滚的问题
		if (sql != null) {
			String tableName = dataMetaInfo.getTableName();
			String[] sqls = sql.split("\n");
			if (sqls != null && sqls.length > 1) {
				sql = sqls[0];
				for (int i = 1; i < sqls.length; i++) {
					int beginIndex = sqls[i].indexOf(tableName) + tableName.length() + 1;
					sql = sql + "," + sqls[i].substring(beginIndex);
				}
			}
		}
		return sql;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#deleteRow(int)
	 */
	@Override
	public void deleteRow(int rowIndex) {
		// 如果删除的字段是数据库已经存在的字段,记录删除的字段的行号
		if (rowIndex < this.existsColumnCount) {
			Integer rowId = Integer.valueOf(rowIndex);
			if (!this.deletedRow.contains(rowId))
				this.deletedRow.add(rowId);
		} else {
			// 如果删除的字段是用户新添加的字段,直接删除
			super.deleteRow(rowIndex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#getColorAt(int,
	 *      int)
	 */
	@Override
	public Color getColorAt(int rowIndex, int colIndex) {
		if (rowIndex >= this.existsColumnCount)
			return Color.GREEN;
		Integer rowId = Integer.valueOf(rowIndex);
		if (this.deletedRow.contains(rowId))
			return Color.RED;
		if (this.updatedRow.contains(rowId))
			return Color.BLUE;

		return null;
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

		if (rowIndex < this.existsColumnCount && columnIndex < 4) {

			Integer rowId = Integer.valueOf(rowIndex);
			List oldRowData = oldMasterPropertyValueList.get(rowId);
			if (super.equalsMasterRow(oldRowData, rowIndex)) {
				if (this.updatedRow.contains(rowId))
					this.updatedRow.remove(rowId);
			} else if (!this.updatedRow.contains(rowId))
				this.updatedRow.add(rowId);
		}
	}

	/**
	 * 比较扩展的属性值是否有过修改,比较过程中发生任何异常都认为有过修改
	 * 
	 * @param oldValueList
	 *            原始的扩展的属性列表
	 * @param currentValueList
	 *            当前的扩展的属性列表
	 * @return true or false
	 */
	private boolean equalsSlavePropertyValueList(List oldValueList, List currentValueList) {
		try {
			String oldValue = oldValueList.get(3).toString();
			String currentValue = currentValueList.get(3).toString();
			Object tmp;
			// 自增属性有可能是"","NO","YES". 取值为"","NO"这两种情况认为是相同的.需要特殊处理
			if ((!ColumnProperty.IS_IDENTITY.equals(oldValue) && !ColumnProperty.IS_IDENTITY.equals(currentValue))
					|| oldValueList.equals(currentValueList)) {
				
				for (int i = 0; i < 3; i++) {
					tmp = oldValueList.get(i);
					if(tmp == null )
						oldValue = "";
					else
						oldValue = tmp.toString();
					tmp = currentValueList.get(i);
					if(tmp == null)
						currentValue = "";
					else
						currentValue = tmp.toString();
					if (!oldValue.equals(currentValue))
						return false;
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}

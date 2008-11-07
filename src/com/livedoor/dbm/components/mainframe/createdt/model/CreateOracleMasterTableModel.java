package com.livedoor.dbm.components.mainframe.createdt.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.livedoor.dbm.constants.ColumnProperty;

/**
 * <p>
 * Title:创建Oracle表使用的主要属性模型
 * <p>
 * Description:创建Oracle表使用的主要属性模型
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class CreateOracleMasterTableModel extends AbsMasterTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2407907052839811876L;

	/**
	 * @param masterPropertyValueList
	 * @param slavePropertyValueList
	 */
	public CreateOracleMasterTableModel(List<List> masterPropertyValueList, List<List> slavePropertyValueList) {

		super(masterPropertyValueList, slavePropertyValueList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#getColorAt(int,
	 *      int)
	 */
	@Override
	public Color getColorAt(int rowIndex, int colIndex) {
		return Color.GREEN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#generateTypesMap()
	 */
	@Override
	protected HashMap generateTypesMap() {
		String typeStr = "BFILE:-1,BLOB:-1,CHAR:25,CLOB:-1,DATE:-1,FLOAT:-1,LONG:-1,NCHAR:25,"
				+ "NCLOB:-1,NUMBER:-1:15:5,NVARCHAR2:25,RAW:50,ROWID:-1,VARCHAR:25,VARCHAR2:25";
		HashMap types = new HashMap();
		String[] fieldStr;
		String[] fieldParams;
		fieldStr = typeStr.split(",");
		for (int i = 0; i < fieldStr.length; i++) {
			fieldParams = fieldStr[i].split(":");
			types.put(fieldParams[0], Integer.valueOf(fieldParams[1]));
		}
		return types;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#generateSlavePropertyNameList()
	 */
	@Override
	protected List generateSlavePropertyNameList() {

		List paramsNameList = new ArrayList();
		paramsNameList.add(ColumnProperty.DEFAULT);
		paramsNameList.add(ColumnProperty.PRECISION);
		paramsNameList.add(ColumnProperty.SCALE);
		return paramsNameList;

	}

	/* (non-Javadoc)
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#getDefaultFiledType()
	 */
	@Override
	protected String getDefaultFiledType() {
		return "VARCHAR2";
	}

	/* (non-Javadoc)
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#getDefaultFieldSize()
	 */
	@Override
	protected String getDefaultFieldSize() {
		return "20";
	}

}

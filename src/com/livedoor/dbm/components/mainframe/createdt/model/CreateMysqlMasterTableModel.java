package com.livedoor.dbm.components.mainframe.createdt.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.livedoor.dbm.constants.ColumnProperty;

/**
 * <p>
 * Title:创建Mysql表使用的主要属性模型
 * <p>
 * Description:创建Mysql表使用的主要属性模型
 * <p>
 * Copyright: Copyright (c) 2006-10-18
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class CreateMysqlMasterTableModel extends AbsMasterTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2407907052839811876L;

	/**
	 * @param masterPropertyValueList
	 * @param slavePropertyValueList
	 */
	public CreateMysqlMasterTableModel(List<List> masterPropertyValueList, List<List> slavePropertyValueList) {

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
		String typeStr = "bigint:56,blob:-1,char:1,date:-1,datetime:-1,decimal:-1:10:0,double:-1,float:-1,int:11,"
				+ "integer:11,longblob:-1,longtext:-1,numeric:-1:10:0,real:-1,smallint:6,text:-1,time:-1,timestamp:-1,tinyblob:-1,"
				+ "tinyint:1,tinytext:-1,varchar:20";
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
		paramsNameList.add(ColumnProperty.IDENTITY);
		return paramsNameList;

	}

	/* (non-Javadoc)
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#getDefaultFiledType()
	 */
	@Override
	protected String getDefaultFiledType() {
		
		return "varchar";
	}

	/* (non-Javadoc)
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel#getDefaultFieldSize()
	 */
	@Override
	protected String getDefaultFieldSize() {
		return "20";
	}

}

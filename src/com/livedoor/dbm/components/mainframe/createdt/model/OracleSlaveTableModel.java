/**
 * 
 */
package com.livedoor.dbm.components.mainframe.createdt.model;

import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.constants.ColumnProperty;

/**
 * <p>
 * Title:Oracle字段扩展属性模型
 * <p>
 * Description:Oracle字段扩展属性模型
 * <p>
 * Copyright: Copyright (c) 2006-10-18
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class OracleSlaveTableModel extends AbsSlaveTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3159957736096129058L;

	/**
	 * @param masterPropertyValueList
	 * @param slavePropertyValueList
	 */
	@SuppressWarnings("unchecked")
	public OracleSlaveTableModel(List masterPropertyValueList, List slavePropertyValueList) {
		super(masterPropertyValueList, slavePropertyValueList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsSlaveTableModel#getComboBoxValues()
	 */
	@Override
	public String[] getComboBoxValues() {
		String[] values = new String[2];
		values[0] = ColumnProperty.IS_IDENTITY;
		values[1] = ColumnProperty.NOT_IDEYTITY;
		return values;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsSlaveTableModel#havePrecisionTypeName()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List havePrecisionTypeName() {
		List havePrecision = new ArrayList();
		havePrecision.add("NUMBER");
		return havePrecision;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsSlaveTableModel#getPrecisionParamIndex()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getPrecisionParamIndex() {
		List haveCheckParamNames = new ArrayList();
		haveCheckParamNames.add(1);
		haveCheckParamNames.add(2);

		return haveCheckParamNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.livedoor.dbm.components.mainframe.createdt.model.AbsSlaveTableModel#haveComboBoxParamName()
	 */
	@Override
	public String haveComboBoxParamName() {
		return "";
	}

}

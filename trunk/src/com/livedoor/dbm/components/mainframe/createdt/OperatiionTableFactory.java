/*
 * 創建期日 2006-09-16
 */
package com.livedoor.dbm.components.mainframe.createdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.AbsSlaveTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.AlterDB2MasterTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.AlterDB2SlaveTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.AlterMysqlMasterTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.AlterMysqlSlaveTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.AlterOracleMasterTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.CreateDB2MasterTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.CreateMysqlMasterTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.CreateOracleMasterTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.DB2SlaveTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.MysqlSlaveTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.OracleSlaveTableModel;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.ColumnProperty;
import com.livedoor.dbm.constants.DBColumnType;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;

/**
* <p>
* Title:创建和修改表用的Model的工厂
* <p>
* Description:创建和修改表用的Model的工厂
* <p>
* Copyright: Copyright (c) 2006-10-18
* <p>
* Company: 英極軟件開發（大連）有限公司
* 
* @author WangHuiTang
* @version 1.0
*/
public class OperatiionTableFactory {

	/**
	 * 生成创建表的主要属性model
	 * @param conInfo
	 * @return 对应当前数据库的主要属性model
	 */
	public static AbsMasterTableModel generateCreateMasterTableModel(ConnectionInfo conInfo) {

		String stDbType = conInfo.getDbType();
		AbsMasterTableModel fieldTableModel = null;

		if (stDbType.equals(DBServerType.DB2) == true) {
			fieldTableModel = new CreateDB2MasterTableModel(null, null);
		} else if (stDbType.equals(DBServerType.MYSQL) == true) {
			fieldTableModel = new CreateMysqlMasterTableModel(null, null);
		} else if (stDbType.equals(DBServerType.ORACLE9i) == true) {
			fieldTableModel = new CreateOracleMasterTableModel(null, null);
		}

		return fieldTableModel;
	}

	/**
	 * 生成修改表的主要属性model
	 * @param connInfo
	 * @param dbSession
	 * @param dbMetaInfo
	 * @return 对应当前数据库的主要属性model
	 * @throws DBMException
	 */
	@SuppressWarnings("unchecked")
	public static AbsMasterTableModel generateAlterMasterTableModel(ConnectionInfo connInfo, DBSession dbSession, DBMDataMetaInfo dbMetaInfo)
			throws DBMException {

		String stDbType = connInfo.getDbType();
		AbsMasterTableModel fieldTableModel = null;
		List<List> info = getFields(connInfo, dbSession, dbMetaInfo);
		List<List> fields;
		List<List> params;
		fields = info.get(0);
		params = info.get(1);
		if (stDbType.equals(DBServerType.DB2) == true) {
			fieldTableModel = new AlterDB2MasterTableModel(fields, params);
		} else if (stDbType.equals(DBServerType.MYSQL) == true) {
			fieldTableModel = new AlterMysqlMasterTableModel(fields, params);
		} else if (stDbType.equals(DBServerType.ORACLE9i) == true) {
			fieldTableModel = new AlterOracleMasterTableModel(fields, params);
		}

		return fieldTableModel;
	}

	/**
	 * 生成创建表的扩展属性model
	 * @param conInfo
	 * @param paramsNameList
	 * @param paramsValueList
	 * @return 对应当前数据库的扩展属性model
	 */
	public static AbsSlaveTableModel generateCreateSlaveTableModel(ConnectionInfo conInfo, List paramsNameList, List paramsValueList) {

		String stDbType = conInfo.getDbType();
		AbsSlaveTableModel fieldTableModel = null;

		if (stDbType.equals(DBServerType.DB2) == true) {
			fieldTableModel = new DB2SlaveTableModel(paramsNameList, paramsValueList);
		} else if (stDbType.equals(DBServerType.MYSQL) == true) {
			fieldTableModel = new MysqlSlaveTableModel(paramsNameList, paramsValueList);
		} else if (stDbType.equals(DBServerType.ORACLE9i) == true) {
			fieldTableModel = new OracleSlaveTableModel(paramsNameList, paramsValueList);
		}

		return fieldTableModel;

	}

	/**
	 * 生成修改表的扩展属性model
	 * @param conInfo
	 * @param paramsNameList
	 * @param paramsValueList
	 * @return 对应当前数据库的扩展属性model
	 */
	@SuppressWarnings("unchecked")
	public static AbsSlaveTableModel generateAlterSlaveTableModel(ConnectionInfo conInfo, List paramsNameList, List paramsValueList) {

		String stDbType = conInfo.getDbType();
		AbsSlaveTableModel fieldTableModel = null;

		if (stDbType.equals(DBServerType.DB2) == true) {
			fieldTableModel = new AlterDB2SlaveTableModel(paramsNameList, paramsValueList);
		} else if (stDbType.equals(DBServerType.MYSQL) == true) {
			fieldTableModel = new AlterMysqlSlaveTableModel(paramsNameList, paramsValueList);
		} else if (stDbType.equals(DBServerType.ORACLE9i) == true) {
			fieldTableModel = new OracleSlaveTableModel(paramsNameList, paramsValueList);
		}

		return fieldTableModel;

	}

	/**
	 * 
	 * @param connInfo
	 * @param dbSession
	 * @param dbMetaInfo
	 * @return
	 * @throws DBMException
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap> getTableInfo(ConnectionInfo connInfo, DBSession dbSession, DBMDataMetaInfo dbMetaInfo) throws DBMException {
		List dbResult;
		HashMap resultRow;
		AbsMasterTableModel fieldTableModel = generateCreateMasterTableModel(connInfo);
		AbsSlaveTableModel paramTableModel = generateCreateSlaveTableModel(connInfo, null, null);
		List primaryKeys = new ArrayList();
		DBMSqlExecuter sqlExecuter = DBMSqlExecuterFactory.createExecuter(connInfo, dbSession);
		sqlExecuter.changeDatabase(dbMetaInfo.getDatabaseName());
		// 取得所有主键名
		dbResult = sqlExecuter.getPrimaryKeys(dbMetaInfo).getData();
		for (int i = 0; i < dbResult.size(); i++) {
			resultRow = (HashMap) dbResult.get(i);
			primaryKeys.add(resultRow.get(DBColumnType.COLUMN_NAME));
		}

		List autoIncrementColumns = sqlExecuter.getAutoIncrementColumns(dbMetaInfo);
		
		dbResult = sqlExecuter.getColumns(dbMetaInfo).getData();
		HashMap row;
		List<HashMap> info = new ArrayList();
		for (int i = 0; i < dbResult.size(); i++) {

			row = new HashMap();
			resultRow = (HashMap) dbResult.get(i);
			Object columnName = resultRow.get(DBColumnType.COLUMN_NAME);
			row.put(ColumnProperty.NAME, columnName);
			Object typeName = resultRow.get(DBColumnType.TYPE_NAME);
			row.put(ColumnProperty.TYPE, typeName);
			if (fieldTableModel.haveLength(typeName.toString()) != -1)
				row.put(ColumnProperty.LENGTH, resultRow.get(DBColumnType.COLUMN_SIZE));
			else
				row.put(ColumnProperty.LENGTH, "");
			String nullableValue = resultRow.get(DBColumnType.NULLABLE).toString();
			Boolean nullable = Boolean.valueOf("1".equals(nullableValue));
			row.put(ColumnProperty.NULLABLE, nullable);
			row.put(ColumnProperty.PK, primaryKeys.contains(columnName));
			
			Object defaultValue =  resultRow.get(DBColumnType.COLUMN_DEF);
			if(defaultValue != null){
				String value = defaultValue.toString();
				defaultValue = value.replaceAll("'","");
			}
				
			row.put(ColumnProperty.DEFAULT, defaultValue);

			if (paramTableModel.havePrecisionTypeName().contains(typeName)) {
				Object tmpObj = resultRow.get(DBColumnType.COLUMN_SIZE);
				if (tmpObj == null)
					tmpObj = "0";

				row.put(ColumnProperty.PRECISION, tmpObj);

				tmpObj = resultRow.get(DBColumnType.DECIMAL_DIGITS);
				if (tmpObj == null)
					tmpObj = "0";

				row.put(ColumnProperty.SCALE, tmpObj);
			} else {
				row.put(ColumnProperty.PRECISION, "");
				row.put(ColumnProperty.SCALE, "");
			}

			if(autoIncrementColumns.contains(columnName))
				row.put(ColumnProperty.IDENTITY,ColumnProperty.IS_IDENTITY);
			else
				row.put(ColumnProperty.IDENTITY,ColumnProperty.NOT_IDEYTITY);

			info.add(row);

		}
		return info;
	}

	
	/**
	 * @param connInfo
	 * @param dbSession
	 * @param dbMetaInfo
	 * @return
	 * @throws DBMException
	 */
	@SuppressWarnings("unchecked")
	public static List<List> getFields(ConnectionInfo connInfo, DBSession dbSession, DBMDataMetaInfo dbMetaInfo) throws DBMException {
		List<HashMap> info = getTableInfo(connInfo, dbSession, dbMetaInfo);
		List<List> fieldsList = new ArrayList(info.size());
		List<List> paramsList = new ArrayList(info.size());
		List<List> result = new ArrayList(2);
		List field;
		List param;
		HashMap resultRow;
		for (int i = 0; i < info.size(); i++) {
			field = new ArrayList();
			param = new ArrayList();
			resultRow = info.get(i);
			field.add(resultRow.get(ColumnProperty.NAME));
			field.add(resultRow.get(ColumnProperty.TYPE));
			field.add(resultRow.get(ColumnProperty.LENGTH));
			field.add(resultRow.get(ColumnProperty.NULLABLE));
			field.add(resultRow.get(ColumnProperty.PK));
			fieldsList.add(field);
			param.add(resultRow.get(ColumnProperty.DEFAULT));
			param.add(resultRow.get(ColumnProperty.PRECISION));
			param.add(resultRow.get(ColumnProperty.SCALE));
			param.add(resultRow.get(ColumnProperty.IDENTITY));
			paramsList.add(param);
		}
		result.add(fieldsList);
		result.add(paramsList);
		return result;

	}

}

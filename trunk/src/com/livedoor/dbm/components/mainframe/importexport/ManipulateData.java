package com.livedoor.dbm.components.mainframe.importexport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBColumnType;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMDataResult;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.i18n.ResourceI18n;
import com.livedoor.dbm.scripts.SqlBuilderFactory;
import com.livedoor.dbm.util.ConvertObject;
import com.livedoor.dbm.util.Convertor;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Title: 数据处理
 * <p>
 * Description: 数据处理，将数据导入到数据库或写入到本地文件
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
class ManipulateData implements Runnable {
	private static String START_SEPARATE_SIGN = "[";
	private static String END_SEPARATE_SIGN = "]";
	private String result;

	private int columnNumber; // 数据表列总数

	private int[] columnType; // 数据表列类型，与java.sql.Type的静态常量对应

	private String[] columnName; // 数据表列名

	private ConvertObject[] parementer;

	private ConnectionInfo connInfo;

	private DBSession session;

	private UserInputInfo userInputInfo;

	private DBMSqlExecuter executer;

	private Convertor convertor;

	private boolean importData;

	private boolean exportData;

	private JFrame mainFrame;

	private volatile boolean stop;
	/**
	 * Function: 构造一个新数据处理对象
	 * <p>
	 * Description: 构造一个新数据处理对象
	 * <p>
	 * 
	 * @param userInputInfo
	 *            用户在前台界面输入的信息
	 * @param connInfo
	 *            数据库连接信息
	 * @param session
	 *            数据库连接session
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	public ManipulateData(JFrame mainFrame, UserInputInfo userInputInfo, ConnectionInfo connInfo, DBSession session) {
		this.connInfo = connInfo;
		this.session = session;
		this.userInputInfo = userInputInfo;
		this.mainFrame = mainFrame;
		executer = DBMSqlExecuterFactory.createExecuter(connInfo, session);
	}
	/**
	 * Function: 设置转换器信息
	 * <p>
	 * Description: 设置转换器的日期时间的格式
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	public void setConvertorInfo() {
		convertor = new Convertor();
		convertor.setDatetimeFormat(userInputInfo.getDateTimeFormat());
		convertor.setDateFormat(userInputInfo.getDateFormat());
		convertor.setTimeFormat(userInputInfo.getTimeFormat());
	}
	/**
	 * Function: 获取所有database名
	 * <p>
	 * Description: 获取当前database连接的所有database名
	 * <p>
	 * 
	 * @return 保存所有database名字的列表
	 * @throws DBMException
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	List getDatabasesName() throws DBMException {
		executer.changeDatabase(userInputInfo.getDatabaseName());
		return executer.getDatabase();
	}

	/**
	 * Function: 获取所有schema名
	 * <p>
	 * Description: 获取当前schema连接的所有schema名
	 * <p>
	 * 
	 * @return 保存所有schema名字的列表
	 * @throws DBMException
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	List getSchemasName() throws DBMException {
		executer.changeDatabase(userInputInfo.getDatabaseName());
		return executer.getSchemas();
	}

	/**
	 * Function: 获取所有table名
	 * <p>
	 * Description: 获取当前table连接的所有table名
	 * <p>
	 * 
	 * @return 保存所有table名字的列表
	 * @throws DBMException
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	List getTablesName() throws DBMException {
		executer.changeDatabase(userInputInfo.getDatabaseName());
		DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
		dbMetaInfo.setSchemaName(userInputInfo.getSchemaName());
		dbMetaInfo.setDatabaseName(userInputInfo.getDatabaseName());
		return executer.getTableNames(dbMetaInfo);
	}

	/**
	 * Function: 获取所有table名
	 * <p>
	 * Description: 获取当前table连接的所有table名
	 * <p>
	 * 
	 * @param schemaName
	 *            指定的schema
	 * @param databaseName
	 *            指定的database
	 * @return 保存所有table名字的列表
	 * @throws DBMException
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	List getTablesName(String schemaName, String databaseName) throws DBMException {
		executer.changeDatabase(databaseName);
		DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
		dbMetaInfo.setSchemaName(schemaName);
		dbMetaInfo.setDatabaseName(databaseName);
		return executer.getTableNames(dbMetaInfo);
	}

	/**
	 * Function: 获取数据表的列信息
	 * <p>
	 * Description: 获取数据表的列信息
	 * <p>
	 * 
	 * @throws DBMException
	 */
	void getColumnsInfo() throws DBMException {
		executer.changeDatabase(userInputInfo.getDatabaseName());
		DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();

		dbMetaInfo.setDatabaseName(userInputInfo.getDatabaseName());
		dbMetaInfo.setSchemaName(userInputInfo.getSchemaName());
		dbMetaInfo.setTableName(userInputInfo.getTableName());

		DBMDataResult dbmDataResult = executer.getColumns(dbMetaInfo);
		List columns = dbmDataResult.getData();
		columnNumber = columns.size();
		columnType = new int[columnNumber];
		columnName = new String[columnNumber];

		for (int i = 0; i < columnNumber; i++) {
			Map columnMap = (HashMap) (columns.get(i));
			columnType[i] = Integer.parseInt((columnMap.get(DBColumnType.DATA_TYPE)).toString());
			columnName[i] = (String) columnMap.get(DBColumnType.COLUMN_NAME);
		}
	}

	/**
	 * Function: 导入数据表
	 * <p>
	 * Description: 将本地数据文件导入到数据表中，导入成功返回true，否则返回false.
	 * <p>
	 * 
	 * @return 如果数据导入成功就返回true,否则返回false.
	 */
	private boolean importDataToDataBase() {
		result = null;
		try {
			DBMConnectionManager.setAutoCommit(connInfo, session, true);
		} catch (DBMException e2) {
			e2.printStackTrace();
		}
		setConvertorInfo();
		try {
			getColumnsInfo();
		} catch (DBMException e1) {
			e1.printStackTrace();
		}
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(userInputInfo.getFile()), userInputInfo
					.getEncoding()));
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			unsupportedEncodingException.printStackTrace();

		} catch (FileNotFoundException fileNotfoundException) {
			fileNotfoundException.printStackTrace();
		}
		parementer = new ConvertObject[columnNumber];
		int insertNumber = 0;
		DBMDataMetaInfo dateMetaInfo = new DBMDataMetaInfo();
		dateMetaInfo.setDatabaseName(userInputInfo.getDatabaseName());
		dateMetaInfo.setSchemaName(userInputInfo.getSchemaName());
		dateMetaInfo.setTableName(userInputInfo.getTableName());
		String sql = SqlBuilderFactory.createSqlBuilder(connInfo, session, dateMetaInfo).insertDynamic();
		String line = null;
		String[] cell = null;
		String[] column = null;
		
		while (!stop) {
			try {
				line = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line == null || line.length() < 1)
				break;
			insertNumber++;
			//按用户指定分隔符对行进行分隔
			cell = line.split(userInputInfo.getDelimited());
			column = new String[columnNumber];
			int deleteCellNumber = 0; //记录被合并的单元数
			//将按分隔符分隔的数据转换为列数据
			for (int i = 0; i < cell.length; i++) {
				//如果被分隔单元以特殊分隔符开始，那么就将其后面单元与其合并
				if (cell[i].startsWith(ManipulateData.START_SEPARATE_SIGN)) {
					StringBuffer tempString = new StringBuffer();
					cell[i] = cell[i].substring(1);
					for (int j = i; j < cell.length; j++) {
						//如果被分隔单元以特殊分隔符结束，那么停止合并单元
						if (cell[j].endsWith(ManipulateData.END_SEPARATE_SIGN)) {
							cell[j] = cell[j].substring(0, (cell[j].length() - 1));
							tempString.append(cell[j]);
							cell[j] = tempString.toString();
							if (j > i)
								deleteCellNumber += j - i;
							i = j;
							break;
						}
						tempString.append(cell[j]);
						tempString.append(userInputInfo.getDelimited());
					}
				}
				column[i - deleteCellNumber] = cell[i];
			}
			for (int i = 0; i < columnNumber; i++) {
				parementer[i] = convertor.string2ConvertObject(column[i], columnType[i]);
				if (parementer[i].isError()) {
					result = "Import data error!";
					userInputInfo.setMessage(ResourceI18n.getText("DATA_TYPE_ERROR"));
					return false;
				}
			}
			try {
				executer.executeUpdate(sql, parementer);
			} catch (DBMException e) {
				e.printStackTrace();
				result = "Import data error!";
				userInputInfo.setMessage(e.getMessage());
				return false;
			}
			((ImportFrame) mainFrame).currentStatusLabel.setText("" + insertNumber + " rows inserted");
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		result = "" + insertNumber + " rows inserted";
		return true;
	}

	private boolean isAddSign(int type) {
		switch (type) {
			case Types.CHAR :
			case Types.DATE :
			case Types.TIMESTAMP :
			case Types.TIME :
			case Types.VARCHAR :
			case Types.LONGVARCHAR :
				return true;
			default :
				return false;
		}
	}
	/**
	 * Function: 生成导出文件的行
	 * <p>
	 * Description: 将数据表数据组合成导出文件的行
	 * <p>
	 * 
	 * @param column
	 *            数据行的值
	 * @return 写入本地文件的每一行
	 */
	private String createLine(Map column) {
		Properties tempProperties = System.getProperties();
		String endLine = (String) tempProperties.getProperty("line.separator");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columnNumber; i++) {
			if (isAddSign(columnType[i])) {
				sb.append(ManipulateData.START_SEPARATE_SIGN);
				sb.append((convertor.object2ConvertObject(column.get(columnName[i]), columnType[i])).toFile());
				sb.append(ManipulateData.END_SEPARATE_SIGN);
			} else {
				sb.append((convertor.object2ConvertObject(column.get(columnName[i]), columnType[i])).toFile());
			}
			if (i < columnNumber - 1) {
				sb.append(userInputInfo.getDelimited());
			} else {
				sb.append(endLine);
			}
		}
		return sb.toString();
	}

	/**
	 * Function: 导出数据表
	 * <p>
	 * Description: 将数据表中数据导出到本地文件，导出成功返回true，否则返回false.
	 * <p>
	 * 
	 * @return 如果数据导出成功就返回true,否则返回false.
	 */
	private boolean exportDataToFile() {

		result = null;
		try {
			DBMConnectionManager.setAutoCommit(connInfo, session, true);
		} catch (DBMException e2) {
			e2.printStackTrace();
		}
		setConvertorInfo();
		try {
			getColumnsInfo();
		} catch (DBMException e1) {
			e1.printStackTrace();
		}
		BufferedWriter bufferedWriter = null;
		List dataSelected = null;
		Map dataColumn = null;
		DBMDataResult dbmDataResult = null;
		String stSql = null;
		if (userInputInfo.getSqlStatement() == null) {
			DBMDataMetaInfo dataMetaInfo = new DBMDataMetaInfo();
			dataMetaInfo.setSchemaName(userInputInfo.getSchemaName());
			dataMetaInfo.setTableName(userInputInfo.getTableName());
			stSql = SqlBuilderFactory.createSqlBuilder(connInfo, session, dataMetaInfo).selectAll();
		} else {
			DBMDataMetaInfo dataMetaInfo = new DBMDataMetaInfo();
			dataMetaInfo.setSchemaName(userInputInfo.getSchemaName());
			dataMetaInfo.setTableName(userInputInfo.getTableName());
			stSql = StringUtil.cleanString(userInputInfo.getSqlStatement());
		}

		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userInputInfo.getFile()), userInputInfo
					.getEncoding()));
			dbmDataResult = executer.executeQuery(stSql);
			dataSelected = dbmDataResult.getData();
			for (int i = 0; i < dataSelected.size(); i++) {
				if (stop)
					break;
				dataColumn = (Map) dataSelected.get(i);
				bufferedWriter.write(createLine(dataColumn));
				((ExportFrame) mainFrame).currentStatusLabel.setText("" + i + " rows writed");
			}
			result = "" + dataSelected.size() + " rows writed";
			bufferedWriter.close();
		} catch (DBMException e) {
			result = "Export data error!";
			userInputInfo.setMessage(e.getMessage());
			try {
				bufferedWriter.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Function: 获取数据处理结果信息
	 * <p>
	 * Description: 获取数据处理结果信息
	 * <p>
	 * 
	 * @return 返回数据处理结果信息
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	public String getResult() {
		return result;
	}
	/**
	 * Function: 执行数据处理
	 * <p>
	 * Description: 执行数据处理
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	public void run() {
		long usedTime = System.currentTimeMillis();
		boolean manipulateReturn = false;
		if (importData) {
			ImportFrame importFrame = (ImportFrame) mainFrame;
			manipulateReturn = importDataToDataBase();
			if (manipulateReturn && stop) {
				importFrame.statusMessageTextArea.append("\n\n");
				importFrame.statusMessageTextArea.append(ResourceI18n.getText("IMPORT_DATA_CANCELED"));
				importFrame.statusMessageTextArea.append("\n\n");
				importFrame.statusMessageTextArea.append(ResourceI18n.getText("USED_TIME") + (System.currentTimeMillis() - usedTime) + "ms");
			} else if (!manipulateReturn && !stop) {
				importFrame.statusMessageTextArea.append("\n\n");
				importFrame.statusMessageTextArea.append(userInputInfo.getMessage());
				importFrame.statusMessageTextArea.append("\n\n");
				importFrame.statusMessageTextArea.append(ResourceI18n.getText("USED_TIME") + (System.currentTimeMillis() - usedTime) + "ms");
			} else {
				importFrame.statusMessageTextArea.append("\n\n");
				importFrame.statusMessageTextArea.append(ResourceI18n.getText("IMPORT_DATA_COMPLETED"));
				importFrame.statusMessageTextArea.append("\n\n");
				importFrame.statusMessageTextArea.append(ResourceI18n.getText("USED_TIME") + (System.currentTimeMillis() - usedTime) + "ms");
			}
			importFrame.closeButton.setText("Close");
			importFrame.previousButton.setEnabled(true);
			importFrame.currentStatusLabel.setText(result);
		}
		if (exportData) {
			ExportFrame exportFrame = (ExportFrame) mainFrame;
			manipulateReturn = exportDataToFile();
			if (manipulateReturn && stop) {
				exportFrame.statusMessageTextArea.append("\n\n");
				exportFrame.statusMessageTextArea.append(ResourceI18n.getText("EXPORT_FILE_CANCELED"));
				exportFrame.statusMessageTextArea.append("\n\n");
				exportFrame.statusMessageTextArea.append(ResourceI18n.getText("USED_TIME") + (System.currentTimeMillis() - usedTime) + "ms");
			} else if (!manipulateReturn && !stop) {
				exportFrame.statusMessageTextArea.append("\n\n");
				exportFrame.statusMessageTextArea.append(userInputInfo.getMessage());
				exportFrame.statusMessageTextArea.append("\n\n");
				exportFrame.statusMessageTextArea.append(ResourceI18n.getText("USED_TIME") + (System.currentTimeMillis() - usedTime) + "ms");
			} else {
				exportFrame.statusMessageTextArea.append("\n\n");
				exportFrame.statusMessageTextArea.append(ResourceI18n.getText("EXPORT_FILE_COMPLETED"));
				exportFrame.statusMessageTextArea.append("\n\n");
				exportFrame.statusMessageTextArea.append(ResourceI18n.getText("USED_TIME") + (System.currentTimeMillis() - usedTime) + "ms");
			}
			exportFrame.closeButton.setText("Close");
			exportFrame.previousButton.setEnabled(true);
			exportFrame.currentStatusLabel.setText(result);
		}
		stop = false;
	}
	/**
	 * Function: 设定数据处理为导入数据
	 * <p>
	 * Description: 设定数据处理为导入数据
	 * <p>
	 * 
	 * @param importData
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	public void setImportData(boolean importData) {
		this.importData = importData;
	}

	/**
	 * Function: 设定数据处理为导出数据
	 * <p>
	 * Description: 设定数据处理为导出数据
	 * <p>
	 * 
	 * @param exportData
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	public void setExportData(boolean exportData) {
		this.exportData = exportData;
	}
	/**
	 * Function: 停止线程
	 * <p>
	 * Description: 停止数据处理线程
	 * <p>
	 * 
	 * @param exportData
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	public void requestStop() {
		this.stop = true;
	}
}

package com.livedoor.dbm.components.mainframe.importexport;

import java.io.File;

import com.livedoor.dbm.i18n.ResourceI18n;
/**
 * <p>
 * Title: 验证数据
 * <p>
 * Description: 在进行数据操作前对用户输入进行验证
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
class ValidateUserInput {
	/**
	 * <p>
	 * Function: 导入文件验证
	 * <p>
	 * Description: 验证导入文件是否存在
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 * @param userInputInfo 用户在主窗体输入的信息
	 * 
	 * @return 验证是否同过
	 */
	boolean validateImportFileInput(UserInputInfo userInputInfo) {
		if (userInputInfo.getFile() == null
				|| userInputInfo.getFile().length() < 1) {
			userInputInfo.setMessage(ResourceI18n.getText("IMPORT_FILE_NOT_EXIST"));
			return false;
		}
		File inputFile = new File(userInputInfo.getFile());
		if (!inputFile.exists()) {
			userInputInfo.setMessage(ResourceI18n.getText("IMPORT_FILE_INPUTED_NOT_EXIST"));
			return false;
		}
		return true;
	}
	/**
	 * <p>
	 * Function: 导出文件验证
	 * <p>
	 * Description: 验证导出文件或路径是否合法
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 * @param userInputInfo 用户在主窗体输入的信息
	 * 
	 * @return 验证是否同过
	 */
	boolean validateExportFileInput(UserInputInfo userInputInfo) {
		if (userInputInfo.getFile() == null
				|| userInputInfo.getFile().length() < 1) {
			userInputInfo.setMessage(ResourceI18n.getText("EXPORT_FILE_NOT_EXIST"));
			return false;
		}
		File outputFile = new File(userInputInfo.getFile());
		if (outputFile.isDirectory()) {
			userInputInfo
					.setMessage(ResourceI18n.getText("EXPORT_FILE_IS_DIRECTORY"));
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * Function: 验证用户输入sql语句
	 * <p>
	 * Description: 验证用户输入sql语句是否合法
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 * @param userInputInfo 用户在主窗体输入的信息
	 * 
	 * @return 验证是否同过
	 */
	boolean validateExportSQLStatement(UserInputInfo userInputInfo) {
		String sql = userInputInfo.getSqlStatement().toLowerCase().trim();
		if (sql.startsWith("select"))
			return true;
		userInputInfo.setMessage(ResourceI18n.getText("EXPORT_SQLSTATEMENT_ERROR"));
		return false;
	}
}

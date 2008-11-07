package com.livedoor.dbm.action.tree;

import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.scripts.SqlBuilderFactory;
import com.livedoor.dbm.util.DBMComponentUtil;

public class ScriptTableSelectAction extends ScriptBaseAction {

	public ScriptTableSelectAction() {
		super("SCRIPT_TABLE_SELECT");
		// TODO 自动生成构造函数存根
	}

	@Override
	public String generateSqlScript(ConnectionInfo connInfo, DBMTreeNode currentNode, DBSession dbSession) {
		DBMDataMetaInfo dateMetaInfo=new DBMDataMetaInfo();
		dateMetaInfo.setSchemaName(DBMComponentUtil.getSchemaName(connInfo, currentNode));
		dateMetaInfo.setTableName(DBMComponentUtil.getTableName(connInfo, currentNode));
		dateMetaInfo.setDatabaseName(DBMComponentUtil.getDatabaseName(connInfo, currentNode));
		//得到创建表的脚本语句
		return SqlBuilderFactory.createSqlBuilder(connInfo, dbSession,dateMetaInfo).select();
	}


}

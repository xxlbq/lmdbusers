package com.livedoor.dbm.action.tree;


import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.util.DBMComponentUtil;

public class ScriptDatabaseDropAction extends ScriptBaseAction {

	public ScriptDatabaseDropAction() {
		super("SCRIPT_DATABASE_DROP");
	}

	@Override
	public String generateSqlScript(ConnectionInfo connInfo, DBMTreeNode currentNode, DBSession dbSession) {
		String dbName = DBMComponentUtil.getDatabaseName(connInfo, currentNode);
		String scriptSql = "DROP DATABASE "+dbName;
		return scriptSql;
	}

}

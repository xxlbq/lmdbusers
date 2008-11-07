package com.livedoor.dbm.components.tree.oracle;

import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.SchemaBaseNode;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * Description: OracleSchemaNode
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司 
 * @author 车鹏
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleSchemaNode extends SchemaBaseNode {
	/**
	 * [機 能] get Children List 
	 * [解 説] get Children List 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new OracleTablesNode(connectionInfo));
		childrenList.add(new OracleViewsNode(connectionInfo));
		childrenList.add(new OracleFunctionsNode(connectionInfo));
		childrenList.add(new OracleTriggersNode(connectionInfo));
		childrenList.add(new OracleProceduresNode(connectionInfo));

		return childrenList;
	}
	/**
	 * [機 能] OracleSchemaNode 
	 * [解 説] OracleSchemaNode 。
	 * 
	 * @param s
	 */
	public OracleSchemaNode(ConnectionInfo connInfo, String dbName) {
		super(connInfo, dbName);

	}

}

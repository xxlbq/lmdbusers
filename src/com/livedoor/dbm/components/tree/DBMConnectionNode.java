/*
 * 
*/
package com.livedoor.dbm.components.tree;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.tree.db2.DB2ManagementNode;
import com.livedoor.dbm.components.tree.db2.DB2SchemasNode;
import com.livedoor.dbm.components.tree.db2.DB2SecurityNode;
import com.livedoor.dbm.components.tree.mysql.MySqlDatabasesNode;
import com.livedoor.dbm.components.tree.mysql.MySqlManagementNode;
import com.livedoor.dbm.components.tree.mysql.MySqlSecurityNode;
import com.livedoor.dbm.components.tree.oracle.OracleManagementNode;
import com.livedoor.dbm.components.tree.oracle.OracleSchemasNode;
import com.livedoor.dbm.components.tree.oracle.OracleSecurityNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.util.DBMConnectionUtil;

/**
 * <p>Description: DBMConnectionNode    </p>
 * Copyright: Copyright (c) 2006 
 * Company: 英極軟件開發（大連）有限公司
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DBMConnectionNode extends DBMTreeNodeList {
	private ConnectionInfo connectionInfo;
	
	public DBMConnectionNode(String s){
		super(s, null, Color.black, "TREE_CONN_SERVER_DISCONNECTED", "TREE_CONN_SERVER_DISCONNECTED");
		try {
			connectionInfo = DBMConnectionUtil.readConnectionInfo(s);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * [機 能] 获取子节点列表 
	 * <p>
	 * [解 説] 获取子节点列表 。
	 * <p>
	 * [備 考] なし
	 * @return 	List childrenList <p>
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		try {
			DBMConnectionManager.getConnection(connectionInfo,getDBSession());
		} catch (DBMException e) {
			DBMMessageDialog.showErrorMessageDialog(e.getMessage());
			return childrenList;
		}
		if(DBServerType.MYSQL.equals(connectionInfo.getDbType())){
			childrenList.add(new MySqlDatabasesNode(connectionInfo));
			childrenList.add(new MySqlManagementNode(connectionInfo));
			childrenList.add(new MySqlSecurityNode(connectionInfo));
		}else if (DBServerType.DB2.equals(connectionInfo.getDbType())){
			childrenList.add(new DB2SchemasNode(connectionInfo));
			childrenList.add(new DB2ManagementNode(connectionInfo));
			childrenList.add(new DB2SecurityNode(connectionInfo));
		}else{
			childrenList.add(new OracleSchemasNode(connectionInfo));
			childrenList.add(new OracleManagementNode(connectionInfo));
			childrenList.add(new OracleSecurityNode(connectionInfo));
		}
		return childrenList;
	}
	
	/**
	 * [機 能] 获取连接信息 
	 * <p>
	 * [解 説] 获取连接信息 。
	 * <p>
	 * [備 考] なし
	 * @return 	ConnectionInfo connectionInfo <p>
	 */
	public ConnectionInfo getConnectionInfo(){
		return connectionInfo;
	}
//	关闭连接的方法已经被移出该类
//	/**
//	 * [機 能] 断开连接 
//	 * <p>
//	 * [解 説] 断开连接 。
//	 * <p>
//	 * [備 考] なし
//	 */
//	public void disconnection(){		
//		DBMConnectionManager.releaseConnection(connectionInfo,getDBSession());
//		/**
//		 * [解 説]移出所有的子节点
//		 */
//		updateList(new ArrayList());
//	}
	/**
	 * 重载基类方法，移走所有的子节点
	 */
	public void removeAllChildren(){
		updateList(new ArrayList());
	}
	/**
	 * 克隆数据库连接信息
	 * @param connInfo
	 */
	public void cloneConnectionInfo(ConnectionInfo connInfo){
		connectionInfo.clone(connInfo);
	}
}


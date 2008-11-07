package com.livedoor.dbm.components.tree.db2;

import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.TableBaseNode;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: DB2TableNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2TableNode extends TableBaseNode {
	/**
	 * [機 能] DB2TableNode 
	 * [解 説] DB2TableNode 。
	 * 
	 * @param s
	 */
	public DB2TableNode(ConnectionInfo connInfo, String tblName) {
		super(connInfo, tblName);
	}

	/**
	 * [機 能] getChildrenList 
	 * [解 説] getChildrenList 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new DB2ColumnsNode(connectionInfo));
		return childrenList;
	}

}

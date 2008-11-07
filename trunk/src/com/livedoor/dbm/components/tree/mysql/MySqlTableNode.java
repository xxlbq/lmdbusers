package com.livedoor.dbm.components.tree.mysql;

import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.TableBaseNode;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: MySqlTableNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlTableNode extends TableBaseNode {

	/**
	 * [機 能] MySqlTableNode 
	 * [解 説] MySqlTableNode 。
	 * 
	 * @param s
	 */
	public MySqlTableNode(ConnectionInfo connInfo, String tblName) {
		super(connInfo, tblName);
	}
	/**
	 * [機 能] get Children List 
	 * [解 説] get Children List 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new MySqlColumnsNode(connectionInfo));
		return childrenList;
	}

}

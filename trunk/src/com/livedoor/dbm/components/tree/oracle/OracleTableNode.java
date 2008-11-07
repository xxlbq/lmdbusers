package com.livedoor.dbm.components.tree.oracle;

import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.TableBaseNode;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * Description: OracleTableNode Copyright: Copyright (c) 2006 Company:
 * 英極軟件開發（大連）有限公司
 * 
 * @author 车鹏
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleTableNode extends TableBaseNode {
	/**
	 * [機 能] OracleTableNode [解 説] OracleTableNode 。
	 * 
	 * @param s
	 */
	public OracleTableNode(ConnectionInfo connInfo, String tblName) {
		super(connInfo, tblName);
	}

	/**
	 * [機 能] get Children List [解 説] get Children List 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new OracleColumnsNode(connectionInfo));
		return childrenList;
	}

}

package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import java.sql.ResultSet;
import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: OracleLocksNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleLocksNode extends DBMResultsetList {
	String dba_lock;
	/**
	 * [機 能] OracleLocksNode 
	 * [解 説] OracleLocksNode 。
	 * 
	 * @param connInfo
	 */
	public OracleLocksNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Locks / Object", null, Color.black, "TREE_CONN_LOCKOBJECTFOLDER", "TREE_CONN_LOCKOBJECTFOLDER");
		dba_lock = "(select sid session_id, decode(type,'MR', 'Media Recovery','RT', 'Redo Thread','UN', 'User Name','TX', 'Transaction','TM', 'DML','UL', 'PL/SQL User Lock','DX', 'Distributed Xaction','CF', 'Control File','IS', 'Instance State','FS', 'File Set','IR', 'Instance Recovery','ST', 'Disk Space Transaction','TS', 'Temp Segment','IV', 'Library Cache Invalidation','LS', 'Log Start or Switch','RW', 'Row Wait','SQ', 'Sequence Number','TE', 'Extend Table','TT', 'Temp Table',type) lock_type,decode(lmode,0, 'None',1, 'Null',2, 'Row-S (SS)',3, 'Row-X (SX)',4, 'Share',5, 'S/Row-X (SSX)',6, 'Exclusive',to_char(lmode)) mode_held,decode(request,0, 'None',1, 'Null',2, 'Row-S (SS)',3, 'Row-X (SX)',4, 'Share',5, 'S/Row-X (SSX)',6, 'Exclusive',to_char(request)) mode_requested,to_char(id1) lock_id1, to_char(id2) lock_id2,ctime last_convert,decode(block,0, 'Not Blocking',1, 'Blocking',2, 'Global',to_char(block)) blocking_others from v$lock)";
		
	}

	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {

		return "select distinct  s.sid, ao.object_name || ' - ' || ao.object_type myname from v$locked_object lo,"+dba_lock+", all_objects ao, v$session s where ao.object_id = lo.object_id and lo.session_id = s.sid";
	}

	/**
	 * [解 説]生成当前节点的子节点 及OracleLockNode
	 * 
	 * @param rs
	 * @return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {

		DBMTreeNode treeNode = null;
		String s = null;
		try {

			s = String.valueOf(rs.getObject(1)) +"："+ String.valueOf(rs.getObject(2));
		} catch (Exception e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new OracleLockNode(s);
		}

		return treeNode;

	}

}

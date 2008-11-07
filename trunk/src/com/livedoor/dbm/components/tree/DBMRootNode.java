/*
 * 
 */
package com.livedoor.dbm.components.tree;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.mainframe.DBMFrame;

/**
 * @author <a href="mailto:chepeng@livedoor.cn">chepeng </a>
 * @version 1.0
 *          <p>
 *          Description:根节点
 *          </p>
 */
@SuppressWarnings("serial")
public class DBMRootNode extends DBMTreeNodeList {
	@SuppressWarnings("unused")
	private DBMFrame frame;
	private File connDir;
	/**
	 * [機 能] 构造 节点对象
	 * 
	 * @param frame
	 * @param fileName
	 */
	public DBMRootNode(DBMFrame frame, String fileName) {
		this(frame, fileName, null, Color.black, "TREE_CONN_SERVERGROUP", "TREE_CONN_SERVERGROUP");
	}
	/**
	 * [機 能] 构造 动态DBMRootNode节点对象 [解 説] 动态DBMRootNode节点对象，需要根据指定目录文件 获取其子节点
	 * 
	 * @param frame
	 * @param s
	 * @param font
	 * @param color
	 * @param expandedKey
	 *            expandedIcon的索引值
	 * @param collapseKey
	 *            collapseIcon的索引值
	 */
	public DBMRootNode(DBMFrame frame, String s, Font font, Color color, String expandedKey, String collapseKey) {
		super(s, font, color, expandedKey, collapseKey);
		this.frame = frame;
		connDir = new File(s);
		if (!connDir.exists()) {
			connDir.mkdir();
		}
	}
	/**
	 * [機 能] 获得孩子节点
	 * <p>
	 * [解 説] 获得孩子节点 。
	 * <p>
	 * [備 考] なし
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		if (connDir == null)
			return null;
		File[] connFiles = connDir.listFiles();
		if (connFiles == null)
			return null;
		List fileList = new ArrayList();
		for (int i = 0; i < connFiles.length; i++) {
			if (connFiles[i].isFile() && connFiles[i].getName().endsWith(".conn")) {
				DBMConnectionNode connectionNode = new DBMConnectionNode(connFiles[i].getName().substring(0,
						connFiles[i].getName().length() - 5));
				// connectionNode.setDBSession(new DBSession());
				fileList.add(connectionNode);
			}
		}
		return fileList;
	}

	public String toString() {
		return "Database Servers";
	}

}

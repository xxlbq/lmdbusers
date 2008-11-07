/*
 * 
 */
package com.livedoor.dbm.components.tree;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.i18n.ResourceI18n;
import com.livedoor.dbm.util.DBMPopupMenuList;
/**
 * <p>
 * Description: DBMTreeNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DBMTreeNode extends DefaultMutableTreeNode {
	private static DBSession dbSession = new DBSession();
	String id;
	Font font;
	Color color;
	ImageIcon collapseIcon;
	ImageIcon expandedIcon;
	DBMTreeModel treeModel;
	JPopupMenu popupMenu;
	
	/**
	 * [機 能] 构建DBMTreeNode
	 * [解 説] 构建DBMTreeNode
	 * @param s
	 * @param font
	 * @param color
	 * @param expandedKey
	 *            expandedIcon的索引值
	 * @param collapseKey
	 *            collapseIcon的索引值
	 */
	public DBMTreeNode(String s, Font font, Color color, String expandedKey, String collapseKey) {
		this.id = s;
		this.font = font;
		this.color = color;
		this.collapseIcon = ResourceI18n.getImage(collapseKey);
		this.expandedIcon = ResourceI18n.getImage(expandedKey);
		this.popupMenu = DBMPopupMenuList.getPopupMenu(getClass().getName());
	}
	public ImageIcon getCollapseIcon() {
		return collapseIcon;
	}
	public void setCollapseIcon(ImageIcon collapseIcon) {
		this.collapseIcon = collapseIcon;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public ImageIcon getExpandedIcon() {
		return expandedIcon;
	}
	public void setExpandedIcon(ImageIcon expandedIcon) {
		this.expandedIcon = expandedIcon;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}
	public void setPopupMenu(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}
	public DBMTreeModel getTreeModel() {
		return treeModel;
	}
	public void setTreeModel(DBMTreeModel treeModel) {
		this.treeModel = treeModel;
	}
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof DBMTreeNode) {
			String s = ((DBMTreeNode) obj).getId();
			if (s == null)
				return false;
			return this.id.equals(s);
		}
		return false;
	}
	public boolean isLeaf() {
		return true;
	}
	public String toString() {
		return this.id;
	}

	public DBSession getDBSession() {
		return dbSession;
	}

}

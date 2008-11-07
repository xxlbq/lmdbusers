/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * <p>
 * Title: 执行计划
 * </p>
 * <p>
 * Description: DbManager sql 语句执行计划
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author YuanBaoKun
 * @version 1.0
 */
public abstract class DataNode extends DefaultMutableTreeNode{
		
	String id;
	Font font;
	Color color;
	ImageIcon collapseIcon;
	ImageIcon expandedIcon;
	
	/**
	 * [功 能] 抽象方法，获得子节点
	 * <p>
	 * [说 明] 抽象方法，获得子节点，请使用子类中的方法
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param List 所有的节点
	 *            <p>
	 * 
	 * @return Object[] 所有的子节点
	 *         <p>
	 */
	abstract Object[] getChildren(List list);
	
	public DataNode()
	{
	}
	
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
	public DataNode(String s, Font font, Color color, String expandedKey, String collapseKey) {
		this.id = s;
		this.font = font;
		this.color = color;
		this.collapseIcon = ResourceI18n.getImage(collapseKey);
		this.expandedIcon = ResourceI18n.getImage(expandedKey);
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
	
	public boolean isLeaf() {
		return true;
	}
	public String toString() {
		return this.id;
	}
}
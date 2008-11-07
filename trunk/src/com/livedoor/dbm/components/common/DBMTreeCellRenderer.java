/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;

import com.livedoor.dbm.components.tree.DBMTreeNode;

/**
 * <p>
 * Title: 树装饰器
 * </p>
 * <p>
 * Description: 通用树装饰器组件.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author chepeng
 * @version 1.0
 */
public final class DBMTreeCellRenderer extends JLabel implements TreeCellRenderer {

	private static final long serialVersionUID = 1L;

	protected static final Color _selectedBackgroundColor;

	protected boolean _selected;

	protected boolean _hasFocus;

	public DBMTreeCellRenderer() {
	}

	/**
	 * [功 能] 得到树装饰器组件.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 树装饰器组件
	 *         <p>
	 */
	public Component getTreeCellRendererComponent(JTree jtree, Object obj, boolean flag, boolean flag1, boolean flag2, int i, boolean flag3) {
		String s = jtree.convertValueToText(obj, flag, flag1, flag2, i, flag3);
		setText(s);
		setToolTipText(s);
		DBMTreeNode dbmtreenode = null;
		try {
			dbmtreenode = (DBMTreeNode) obj;
		} catch (Exception exception) {
			exception.printStackTrace();
			return this;
		}
		if (flag1)
			setIcon(dbmtreenode.getExpandedIcon());
		else if (!flag2)
			setIcon(dbmtreenode.getCollapseIcon());
		else
			setIcon(dbmtreenode.getCollapseIcon());
		if (flag)
			setForeground(Color.white);
		else
			setForeground(dbmtreenode.getColor());
		if (dbmtreenode.getFont() == null)
			setFont(getFont());
		else
			setFont(dbmtreenode.getFont());
		_selected = flag;
		_hasFocus = flag3;
		return this;
	}
	/**
	 * [功 能] 重绘.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param g
	 *            Graphics
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void paint(Graphics g) {
		Icon icon = getIcon();
		Color color;
		if (_selected) {
			if (_hasFocus)
				color = _selectedBackgroundColor;
			else
				color = Color.gray;
		} else if (getParent() != null)
			color = getParent().getBackground();
		else
			color = getBackground();
		g.setColor(color);
		if (icon != null && getText() != null) {
			int i = icon.getIconWidth() + getIconTextGap();
			g.fillRect(i, 0, getWidth() - 1 - i, getHeight() - 1);
		} else {
			g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
		super.paint(g);
	}

	static {
		_selectedBackgroundColor = Color.blue;
	}
}

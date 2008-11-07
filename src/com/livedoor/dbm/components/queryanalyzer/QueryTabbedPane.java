/**
 * $Id: QueryTabbedPane.java,v 1.15 2006/12/04 02:55:14 lijc Exp $
 * 查询分析器相关
 */
package com.livedoor.dbm.components.queryanalyzer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.queryanalyzer.file.DBMFileChooser;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * <p>
 * Title: 查询分析器标签
 * </p>
 * <p>
 * Description: 显示多个查询分析器,并可关闭
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * </p>
 * 
 * @author <a href="mailto:yuanbk@livedoor.cn">YuanBaoKun</a>
 * @version 1.0
 */
public class QueryTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	private static Image _closeImageSelected = ResourceI18n.getImage("QUERY_CLOSE").getImage();
	private static Image _closeImage = ResourceI18n.getImage("QUERY_CLOSE").getImage();

	private HashMap<Integer, Rectangle> hashMap = null;

	public QueryTabbedPane() {
		super();
		initialize();
	}

	/**
	 * 初始化
	 */
	private void initialize() {
		this.setSize(300, 200);
		addMouseListener(new DefaultMouseAdapter());
		hashMap = new HashMap<Integer, Rectangle>();
	}

	public void addTab(String s, QueryBasePanel component) {
		super.addTab(s + "   ", component);
	}

	public void addTab(String s, Icon icon, QueryBasePanel component) {
		super.addTab(s + "   ", icon, component);
	}

	public void addTab(String s, Icon icon, QueryBasePanel component, String s1) {
		super.addTab(s + "   ", icon, component, s1);
	}

	private Rectangle getCloseButtonBoundsAt(int i) {
		Rectangle rectangle = getBoundsAt(i);
		if (rectangle == null)
			return null;
		rectangle = new Rectangle(rectangle);
		Dimension dimension = getSize();
		if (rectangle.x + rectangle.width >= dimension.width || rectangle.y + rectangle.height >= dimension.height)
			return null;
		else
			return new Rectangle((rectangle.x + rectangle.width) - 13, (rectangle.y + rectangle.height / 2) - 5, 10, 9);
	}

	public void paint(Graphics g) {
		super.paint(g);

		hashMap.clear();

		int i = getSelectedIndex();
		int j = 0;
		for (int k = getTabCount(); j < k; j++) {
			Rectangle rectangle = getCloseButtonBoundsAt(j);
			if (rectangle == null)
				continue;

			if (true) {
				if (j != i)
					g.setColor(new Color(214, 214, 206));
				else
					g.setColor(new Color(153, 153, 153));
				g.fillRect(rectangle.x + 1, rectangle.y + 0, rectangle.width, rectangle.height);
			}

			if (j != i)
				g.drawImage(_closeImage, rectangle.x + 1, rectangle.y + 1, this);
			else
				g.drawImage(_closeImageSelected, rectangle.x + 1, rectangle.y + 1, this);

			hashMap.put(hashMap.size(), rectangle);
		}
	}

	private void mclick(int x, int y) {
		if (hashMap.size() > 0 && ((Rectangle) hashMap.get(this.getSelectedIndex())).contains(x, y) == true) {
			removeComponent(this.getSelectedIndex());
		}
	}

	class DefaultMouseAdapter extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			int index = indexAtLocation(e.getX(), e.getY());
			mclick(e.getX(), e.getY());
		}
	}
    
	/**
	 * 从面板上移去指定的索引的组件,并释放连接到连接池中
	 * @param index
     * @author <a href="lijc@livedoor.cn">LiJicheng</a>
	 */
	public void removeComponent(int index) {
        QueryAnalyzerPanel queryPanel = (QueryAnalyzerPanel)getComponentAt(index);
        if (queryPanel.isDirty()) {
            int result = DBMMessageDialog.showConfirmDialogYNC(this, "ASK_IF_SAVE_QUERY");
            if(result == JOptionPane.DEFAULT_OPTION 
                    || result == JOptionPane.CANCEL_OPTION)
                return;
            
            if(result == JOptionPane.YES_OPTION) {
                int result1 = queryPanel.saveQuery();
                if(result1==DBMFileChooser.CANCEL_OPTION)
                    return;
            }
        }
        
        // 释放数据库连接
        queryPanel.releaseConnection();
        removeTabAt(index);
    }
    
	/**
	 * 从面板上移去指定的索引的组件,并关闭数据库连接
	 * @param index
	 * @author <a href="lijc@livedoor.cn">LiJicheng</a>
	 */
	private void closeComponent(int index) {
        QueryAnalyzerPanel queryPanel = (QueryAnalyzerPanel)getComponentAt(index);
        if (queryPanel.isDirty()) {
		    int result = -1;
            result = DBMMessageDialog.showConfirmDialog("ASK_IF_SAVE_QUERY");
            if(result == JOptionPane.YES_OPTION) {
                queryPanel.saveQuery();
            }
		}
            
		// 关闭数据库连接
		queryPanel.closeConnection();
		removeTabAt(index);
	}

	/**
	 * 从面板上删除指定的连接别名的查询分析器，同时关闭数据库连接
	 * @param conAliasName
	 * @author <a href="lijian@livedoor.cn">LiJian</a>
	 */
	public void removeComponent(String conAliasName) {
        for (int i = getComponentCount() - 1; i >= 0; i--) {
            QueryAnalyzerPanel panel = (QueryAnalyzerPanel) getComponent(i);
            ConnectionInfo connInfo = panel.getConnInfo();
            if (conAliasName.equalsIgnoreCase(connInfo.getAliasName())) {
                closeComponent(i);
            }
        }
	}
}

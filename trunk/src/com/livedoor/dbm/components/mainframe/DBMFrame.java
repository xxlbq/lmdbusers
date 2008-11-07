/*
 * 创建时间 2006/09/18
 */
package com.livedoor.dbm.components.mainframe;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultFocusManager;
import javax.swing.FocusManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.livedoor.dbm.components.queryanalyzer.QueryBasePanel;
import com.livedoor.dbm.components.queryanalyzer.QueryTabbedPane;
import com.livedoor.dbm.components.tree.DBMTree;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.db.DBMConnectionManager;

/**
 * <p> Title: 主窗体 </p>
 * <p> Description: 主窗体类. </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: 英极软件开发（大连）有限公司 </p>
 * 
 * @author lijian
 * @version 1.0
 */
public class DBMFrame extends JFrame {

	private static final long serialVersionUID = 4661697153896872919L;

	private JMenuBar jMenuBar;

	private JToolBar jToolBar;

	private JPanel statusBar;

	private JSplitPane jSplitPane1;

	private JTabbedPane treeTabbedPane;

	private QueryTabbedPane queryTabbedPane;
    
	/**
     * [功 能] 构造器. 
     * <p> [作成日期] 2006/09/18 <p>
     * 
     * @param title 标题
     * @param imageIcon 图标
     * @param menuBar 菜单条
     * @param toolBar 工具条
     * @param statusBar 状态条 
     */
	public DBMFrame(String title, ImageIcon imageIcon, JMenuBar menuBar, JToolBar toolBar, JPanel statusBar) {
		this.jMenuBar = menuBar;
		this.jToolBar = toolBar;
		this.statusBar = statusBar;
		this.setTitle(title);
		this.setIconImage(imageIcon.getImage());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DBMConnectionManager.closeAllConnection();
				System.exit(0);
			}
		});
		byte byte0 = 10;
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		if (dimension.width > 925) {
			setBounds(byte0, byte0, dimension.width-50,  dimension.height-50);
		} else {
			setBounds(byte0, byte0, dimension.width - byte0 * 2 - 50, dimension.height - byte0 * 2 - 100);
		}
		treeTabbedPane = new JTabbedPane();
		treeTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		queryTabbedPane = new QueryTabbedPane();
		initComponents();

	}
    
	/**
     * [功 能] 初使化主窗体的组件. <p> [作成日期] 2006/09/18 <p>
     * 
     * @param 无 
     * @return 无
     */
	private void initComponents() {
		this.setJMenuBar(this.jMenuBar);
		this.getContentPane().setLayout(new BorderLayout());
		getContentPane().add(this.jToolBar, "North");
		getContentPane().add(this.statusBar, "South");

		jSplitPane1 = new JSplitPane();
		jSplitPane1.setDividerLocation(250);
		jSplitPane1.add(treeTabbedPane, JSplitPane.LEFT);
		jSplitPane1.add(queryTabbedPane, JSplitPane.RIGHT);

		getContentPane().add(jSplitPane1, "Center");
		//处理全局键盘事件
		FocusManager.setCurrentManager(new DefaultFocusManager(){
			public void processKeyEvent(Component component, KeyEvent keyevent){
				 if(!keyevent.isControlDown() && !keyevent.isAltDown() && !keyevent.isShiftDown() ){
					 int i = keyevent.getKeyCode();
					 if(i == KeyEvent.VK_F5){
						 Object obj = keyevent.getSource();
						 if(obj instanceof DBMTree){
							 DBMTreeNode node = ((DBMTree)obj).getSelectedNode();
							 if(node instanceof DBMTreeNodeList){
								((DBMTreeNodeList)node).updateList();
							 }else{
								 keyevent.consume();
							 }
						 }
					 }else{
						 super.processKeyEvent(component,keyevent);
					 }
				 }
			}
			
		}
		) ;

	}
    
	/**
     * [功 能] 添加存放树的标签面板. <p> [作成日期] 2006/09/18 <p>
     * 
     * @param title 标题
     * @param imageIcon 图标
     * @param treePane 树面板 <p>
     * @return 无 <p>
     */

    public void addTreeTabPanel(String title, ImageIcon imageIcon, JPanel treePane) {
        treeTabbedPane.addTab(title, treePane);
    }
    
    /**
     * [功 能] 添加存放查询分析器的标签面板. 
     * <p> [作成日期] 2006/09/18 <p>
     * 
     * @param queryPanel 查询分析器面板 <p>
     * @return 无 <p>
     */
    public void addQueryPanel(QueryBasePanel queryPanel) {
        queryTabbedPane.addTab(queryPanel.getTitle(), queryPanel.getIcon(), queryPanel);
        queryTabbedPane.setSelectedComponent(queryPanel);
    }
    
    /**
     * [功 能] 得到存放树的面板. 
     * <p> [作成日期] 2006/09/18 <p>
     * 
     * @param 无 <p>
     * @return JPanel <p>
     */
    public JPanel getTreePanel() {
        return (JPanel) treeTabbedPane.getSelectedComponent();
    }
    
    /**
     * [功 能] 得到存放查询分析器的面板. 
     * <p> [作成日期] 2006/09/18 <p>
     * 
     * @param 无 <p>
     * @return QueryBasePanel <p>
     */
    public QueryBasePanel getQueryPanel() {
        return (QueryBasePanel) queryTabbedPane.getSelectedComponent();
    }
    
    /**
     * [功 能] 更新查询分析面板的标题. 
     * <p> [作成日期] 2006/09/18 <p>
     * 
     * @param queryPanel 查询分析器面板 <p>
     * @return 无 <p>
     */
    public void updateQueryPanelTitle(QueryBasePanel queryPanel) {
        int index = queryTabbedPane.indexOfComponent(queryPanel);
        queryTabbedPane.setTitleAt(index, queryPanel.getTitle() + "  ");
    }

    /**
     * [功 能] 根据连接别名关闭查询分析器组件 
     * <p> [作成日期] 2006/09/18 <p>
     * 
     * @param title 标题 <p>
     * @return 无 <p>
     */
    public void removeQueryPanelByConnAliasName(String connAliasName) {
        queryTabbedPane.removeComponent(connAliasName);
    }

    /**
     * @return 菜单栏
     */
    public JMenuBar getJMenuBar() {
        return jMenuBar;
    }

    /**
     * @return 工具栏
     */
    public JToolBar getJToolBar() {
        return jToolBar;
    }
}

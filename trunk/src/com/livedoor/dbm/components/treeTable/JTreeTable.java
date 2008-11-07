/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;

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
public class JTreeTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected TreeTableCellRenderer tree;
	private JTreeTable table;

	/**
	 * [功 能] JTreeTable构建器，给定相应的表模式，生成JTreeTable
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param TreeTableModel
	 *            <p>
	 */
	public JTreeTable(TreeTableModel treeTableModel) {
		super();
		table = this;

		/*
		 * UIManager.put("Tree.closedIcon",
		 * ResourceI18n.getImage("TREETAB_NOT_LEAF_COL"));
		 * UIManager.put("Tree.openIcon",
		 * ResourceI18n.getImage("TREETAB_NOT_LEAF_COL"));
		 * UIManager.put("Tree.leafIcon",
		 * ResourceI18n.getImage("TREETAB_NOT_LEAF_COL"));
		 */

		// Create the tree. It will be used as a renderer and editor.
		tree = new TreeTableCellRenderer(treeTableModel);

		/*
		 * DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)
		 * tree.getCellRenderer();
		 * renderer.setLeafIcon(ResourceI18n.getImage("TREETAB_LEAF"));
		 * renderer.setClosedIcon(ResourceI18n.getImage("TREETAB_NOT_LEAF_COL"));
		 * renderer.setOpenIcon(ResourceI18n.getImage("TREETAB_NOT_LEAF_EXP"));
		 */

		// Install a tableModel representing the visible rows in the tree.
		super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

		// Force the JTable and JTree to share their row selection models.
		tree.setSelectionModel(new DefaultTreeSelectionModel() {
			// Extend the implementation of the constructor, as if:
			/* public this() */{
				setSelectionModel(listSelectionModel);
			}
		});

		// Make the tree and table row heights the same.
		tree.setRowHeight(getRowHeight());
		tree.setTreeTable(this);
		
		//tree.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Install the tree editor renderer and editor.
		setDefaultRenderer(TreeTableModel.class, tree);
		setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());

		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));

		MouseAdapter mouseAdapter = new MouseAdapter() {

			public void mousePressed(MouseEvent mouseevent) {
				// int iRow = table.getSelectedRow();

				int iRow = table.rowAtPoint(mouseevent.getPoint());

				table.setRowSelectionInterval(iRow, iRow);

			}
		};
		addMouseListener(mouseAdapter);
	}

	/**
	 * [功 能] 获取表格中正在选定的行
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return int 返回所需要的行
	 *         <p>
	 */
	public int getEditingRow() {
		return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1 : editingRow;
	}

	/**
	 * [功 能] CellRenderer用于显示tree节点
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public class TreeTableCellRenderer extends JTree implements TableCellRenderer {

		protected int visibleRow;
		private final JDBMTreeCellRenderer _treeCellRenderer = new JDBMTreeCellRenderer();
		private TreeTableCellRenderer tree;
		private JTreeTable treeTab;

		/**
		 * [功 能] CellRenderer用于显示tree节点
		 * <p>
		 * [作成日期] 2006/09/26
		 * <p>
		 * 
		 * @param TreeModel
		 *            树模式
		 *            <p>
		 */
		public TreeTableCellRenderer(TreeModel model) {
			super(model);
			setCellRenderer(_treeCellRenderer);

			tree = this;
			MouseAdapter mouseAdapter = new MouseAdapter() {

				public void mousePressed(MouseEvent mouseevent) {

					int i = tree.getRowForLocation(mouseevent.getX(), mouseevent.getY());

					if (i < 0) {
						treeTab.repaint();
					}
				}
			};
			addMouseListener(mouseAdapter);

		}

		/**
		 * [功 能] 设定treeTable控件
		 * <p>
		 * [作成日期] 2006/09/26
		 * <p>
		 * 
		 * @param treeTab
		 *            JTreeTable
		 *            <p>
		 * 
		 * @return 无
		 *         <p>
		 */
		protected void setTreeTable(JTreeTable treeTab) {
			this.treeTab = treeTab;
		}
		/**
		 * [功 能] 设定边框
		 * <p>
		 * [作成日期] 2006/09/26
		 * <p>
		 * 
		 * @param x
		 *            横坐标
		 * @param y
		 *            纵坐标
		 * @param w
		 *            宽度
		 * @param h
		 *            高度
		 *            <p>
		 * 
		 * @return 无
		 *         <p>
		 */
		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x, 0, w, JTreeTable.this.getHeight());
		}

		/**
		 * [功 能] 过载的paint 方法
		 * <p>
		 * [作成日期] 2006/09/26
		 * <p>
		 * 
		 * @param Graphics
		 * 
		 * @return 无
		 *         <p>
		 */
		public void paint(Graphics g) {
			g.translate(0, -visibleRow * getRowHeight());
			super.paint(g);
		}

		/**
		 * [功 能] 根据给定的参数，返回Component组件
		 * <p>
		 * [作成日期] 2006/09/26
		 * <p>
		 * 
		 * @param JTable
		 *            表格
		 * @param Object
		 *            值
		 * @param boolean
		 *            true or false 标识是否可选
		 * @param boolean
		 *            设置焦点
		 * @param row
		 *            行
		 * @param column
		 *            列
		 *            <p>
		 * 
		 * @return Component
		 *         <p>
		 */
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (isSelected)
				setBackground(table.getSelectionBackground());
			else
				setBackground(table.getBackground());

			visibleRow = row;
			return this;
		}
	}

	/**
	 * [功 能] TreeTableCellEditor，继承自AbstractCellEditor
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public class TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {
		/**
		 * [功 能] 根据给定的参数，返回Component组件
		 * <p>
		 * [作成日期] 2006/09/26
		 * <p>
		 * 
		 * @param JTable
		 *            表格
		 * @param Object
		 *            值
		 * @param boolean
		 *            true or false 标识是否可选
		 * @param r
		 *            行
		 * @param c
		 *            列
		 *            <p>
		 * 
		 * @return Component
		 *         <p>
		 */
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) {
			return tree;
		}
	}
	
	/*public void addNotify()
	 {
	     super.addNotify();
	     if(getParent().getParent() instanceof JScrollPane)
	     {
	         JScrollPane jscrollpane = (JScrollPane)getParent().getParent();
	         jscrollpane.getViewport().setBackground(getBackground());
	         jscrollpane.getColumnHeader().setBackground(getBackground());
	     }
	 }*/

}

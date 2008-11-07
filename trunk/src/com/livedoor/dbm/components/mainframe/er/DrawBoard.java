package com.livedoor.dbm.components.mainframe.er;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;

import com.livedoor.dbm.components.common.DBMDialog;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * <p>
 * Title: Er图的画板类
 * <p>
 * Description: 绘制Er图
 * <p>
 * Copyright: Copyright (c) 2006-11-1
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */

public class DrawBoard extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -886293724850257541L;

	private List<Entity> entitys;
	private List<Relationship> relationList;
	Entity selectedEntitys;
	private boolean init = true;
	private PopupMenu menu;

	private JFileChooser fileChooser;

	/**
	 * 构造方法
	 * 
	 * @param listEntitys
	 *            包含所有实体的List
	 * @param listRelation
	 *            包含所有关系的list
	 */
	@SuppressWarnings("unchecked")
	public DrawBoard(List listEntitys, List listRelation) {
		entitys = listEntitys;
		relationList = listRelation;

		menu = new PopupMenu();
		String itemName = ResourceI18n.getText("ER_DIAGRAM_SAVE");
		MenuItem menuItem = new MenuItem(itemName);
		menu.add(menuItem);
		this.add(menu);
		
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory() || (f.canWrite() && f.getName().endsWith(".jpg")))
					return true;
				else
					return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "jpg";
			}
		});
		
		for (int i = 0; i < listEntitys.size(); i++) {

			autoResize((Entity) listEntitys.get(i));

		}

		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				saveERD();
			}
		});
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				
				if (e.getButton() == MouseEvent.BUTTON3)
					showMenu(e);

			}

			public void mousePressed(MouseEvent e) {
				click(e);
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				dragged(e);
			}

			public void mouseMoved(MouseEvent e) {

			}
		});

	}

	protected void showMenu(MouseEvent e) {
		menu.show(this, e.getPoint().x, e.getPoint().y);
	}

	protected void saveERD() {
		
		int state = fileChooser.showSaveDialog(this);
		if (state != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File file = fileChooser.getSelectedFile();
		FileOutputStream outputStream;
		try {
			String fileName =file.getPath();
			if(!fileName.endsWith(".jpg"))
				fileName += ".jpg";
			
			outputStream = new FileOutputStream(fileName);
			Dimension size = computerSize();
			BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_3BYTE_BGR);
			image.getGraphics().setColor(Color.WHITE);
			image.getGraphics().fillRect(0, 0, size.width, size.height);
			draw(image.getGraphics());
			ImageIO.write(image, "jpg", outputStream);
			outputStream.close();

		} catch (Exception e) {
			DBMMessageDialog.showErrorMessageDialog(this,"save failed");
			e.printStackTrace();
		}

	}

	/**
	 * 计算显示所有实体需要的大小
	 * 
	 * @param entity
	 * @return
	 */
	public void autoResize(Entity entity) {
		int width = this.getWidth();
		int height = this.getHeight();
		if (entity.getWidth() + entity.getX() > width) {
			width = entity.getWidth() + entity.getX();
		}
		if (entity.getHeight() + entity.getY() > height)
			height = entity.getHeight() + entity.getY();
		if (width != this.getWidth() || height != this.getHeight()) {
			this.setPreferredSize(new Dimension(width, height));
			this.setSize(new Dimension(width, height));
		}

		

	}
	public Dimension computerSize() {
		int width = 0;
		int height = 0;
		Entity entity;
		for(int i=0; i<this.entitys.size(); i++){
			entity = this.entitys.get(i);
			if (entity.getWidth() + entity.getX() > width) 
				width = entity.getWidth() + entity.getX();
			
			if (entity.getHeight() + entity.getY() > height)
				height = entity.getHeight() + entity.getY();
				
		}

		return new Dimension(width, height);
		
	}

	/**
	 * 鼠标点击
	 * 
	 * @param e
	 */
	private void click(MouseEvent e) {
		Entity entity;
		for (int i = entitys.size() - 1; i >= 0; i--) {
			entity = entitys.get(i);
			if (entity.contains(e.getPoint())) {
				if (selectedEntitys != null) {
					selectedEntitys.lostFocus();
					this.repaint(selectedEntitys.getRect());
				}
				entity.setFocus();

				selectedEntitys = entitys.remove(i);
				entitys.add(selectedEntitys);
				this.repaint(entity.getRect());
				return;
			}
		}
		if (selectedEntitys != null) {
			selectedEntitys.lostFocus();
			this.repaint(selectedEntitys.getRect());
			selectedEntitys = null;
		}
	}

	/**
	 * 鼠标拖拽
	 * 
	 * @param e
	 */
	private void dragged(MouseEvent e) {
		if (selectedEntitys != null) {
			selectedEntitys.mouseDrag(e);
			autoResize(selectedEntitys);
			this.repaint(this.getVisibleRect());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);

	}

	private void draw(Graphics g) {
		g.setColor(Color.WHITE);
		Rectangle rect = this.getVisibleRect();
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(Color.BLACK);
		if (init) {
			autoLayout();
			init = false;
		}
		for (int i = 0; i < relationList.size(); i++) {

			relationList.get(i).paint(g);

		}

		for (int i = 0; i < entitys.size(); i++)
			entitys.get(i).paint(g);
		if (selectedEntitys != null)
			selectedEntitys.paint(g);
	};

	/**
	 * 自动布局
	 */
	public void autoLayout() {
		int max_width = 0;
		int tmpWidth = 0;
		int max_height = 0;
		int rowSpan = 20;
		int colSpan = 20;
		int x = colSpan;
		int y = rowSpan;
		int yPoint = 0;
		Entity entity;
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		for (int j = 0; j < entitys.size(); j++) {

			entity = (Entity) entitys.get(j);
			entity.setX(x);
			entity.setY(y);
			// 测量最大宽度
			tmpWidth = g2d.getFontMetrics().stringWidth(entity.name);
			if (tmpWidth > max_width)
				max_width = tmpWidth;
			for (int i = 0; i < entity.fields.size(); i++) {
				tmpWidth = g2d.getFontMetrics().stringWidth((String) entity.fields.get(i));
				if (tmpWidth > max_width)
					max_width = tmpWidth;
			}
			entity.setWidth(max_width);
			x = x + max_width + colSpan;
			max_width = 0;
			// 测量最大高度
			int fontheight = g2d.getFontMetrics().getHeight();
			max_height = fontheight + 2;
			max_height += fontheight * entity.fields.size();
			entity.setHeight(max_height);
			if (yPoint < max_height)
				yPoint = max_height;

			if ((j + 1) % 4 == 0) {
				y = y + yPoint + rowSpan;
				x = colSpan;
				yPoint = 0;
			}
			autoResize(entity);

		}
		this.repaint(this.getVisibleRect());
	}

}

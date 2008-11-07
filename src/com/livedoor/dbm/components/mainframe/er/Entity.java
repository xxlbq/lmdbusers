package com.livedoor.dbm.components.mainframe.er;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: Er图的实体类
 * <p>
 * Description: 实体类
 * <p>
 * Copyright: Copyright (c) 2006-11-1
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */

public class Entity implements IErObject {

	private Rectangle rect;

	private int mouseX;
	
	private int mouseY;

	private int zOrder;

	private int dragFlag;

	private int mouseXPoint;
	
	private int mouseYPoint;
	
	private boolean hasFocus = false;
	
	public static final int moveFlag = 0;

	public static final int resizeFlag = 1;
	
	public static final int iconWidth = 16;

	List fields;
	
	String name;

	private Rectangle dragArea;

	/**
	 * 构造函数
	 * @param name 实体名
	 */
	public Entity(String name) {

		this.fields = new ArrayList();
		this.name = name;
		this.rect = new Rectangle();
		rect.x = 0;
		rect.y = 0;
		rect.width = 100;
		rect.height = 200;
		this.dragArea = new Rectangle();
		resetDragarea();

	}

	/**
	 * 重新调整响应拖拽的区域
	 */
	private void resetDragarea() {
		this.dragArea.x = rect.x + rect.width - 5;
		this.dragArea.y = rect.y + rect.height - 5;
		this.dragArea.width = 5;
		this.dragArea.height = 5;
	}

	/**
	 * 添加一个字段
	 * @param field 字段名
	 */
	@SuppressWarnings("unchecked")
	public void addField(String field) {
		if(field == null)
			return;
		this.fields.add("      "+field);
	}
	
	/**
	 * 添加一个主键
	 * @param field 主键名
	 */
	public void addPk(String field){
		
		if(field == null)
			return;
		this.fields.add(" (pk) " +field );
	}

	
	/* (non-Javadoc)
	 * @see com.livedoor.dbm.components.mainframe.er.IErObject#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create(rect.x, rect.y, rect.width, rect.height);
		g2d.setColor(new Color(255,255,205));
		g2d.fillRect(0, 0, rect.width, rect.height);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, rect.width - 1, rect.height - 1);

		int fontheight = g2d.getFontMetrics().getHeight();
		int yPoint = fontheight - 2;

		g2d.drawString(" "+name, 0, yPoint);

		yPoint += 2;
		g2d.drawLine(0, yPoint, rect.width, yPoint);


		for (int i = 0; i < this.fields.size(); i++)
			g2d.drawString((String) fields.get(i), 0, yPoint += fontheight);
		if (hasFocus){
			g2d.fillRect(rect.width - 5, rect.height - 5, 5, 5);
		}
		g2d.dispose();
	}

	/**
	 * 拖拽的响应
	 * @param e
	 */
	public void mouseDrag(MouseEvent e) {
		if (Entity.moveFlag == dragFlag)
			move(e.getX(), e.getY());
		else
			reSize(e.getX(), e.getY());

	}
	
	/**
	 * 重设大小
	 * @param width 宽
	 * @param height 高
	 */
	public void reSize(int width, int height) {
		setWidth(rect.width + width - rect.x - rect.width);
		setHeight(rect.height + height - rect.y - rect.height);
		

	}


	/**
	 * 移动位置
	 * @param x
	 * @param y
	 */
	public void move(int x, int y) {
		setX( x - mouseXPoint);
		setY( y - mouseYPoint);
	}

	


	/**
	 * 得到zOrder
	 * @return zOrder
	 */
	public int getZOrder() {
		return zOrder;
	}

	/**
	 * 设置zOrder
	 * @param order 
	 */
	public void setZOrder(int order) {
		zOrder = order;
	}

	/**
	 * 得到高度
	 * @return 高度
	 */
	public int getHeight() {
		return rect.height;
	}

	/**
	 * 设置高度
	 * @param height 高度
	 */
	public void setHeight(int height) {
		if(height <10)
			height = 10;
		rect.height = height;
	}

	/**
	 * 得到宽度 
	 * @return 宽度 
	 */
	public int getWidth() {
		return rect.width;
	}

	/**
	 * 设置宽度
	 * @param width 宽度 
	 */
	public void setWidth(int width) {
		if(width <10 )
			width =10;
		rect.width = width;
	}

	/**
	 * 得到x坐标
	 * @return x坐标
	 */
	public int getX() {
		return rect.x;
	}

	/**
	 * 设置x坐标
	 * @param x x坐标
	 */
	public void setX(int x) {
		if(x < 0 )
			x = 0;
		rect.x = x;
	}

	/**
	 * 得到Y坐标
	 * @return y坐标
	 */
	public int getY() {
	
		return rect.y;
	}

	/**
	 * 设置y坐标
	 * @param y y坐标
	 */
	public void setY(int y) {
		if(y<0)
			y=0;
		rect.y = y;
	}

	/**
	 * 得到全部字段
	 * @return 全部字段
	 */
	public List getFields() {
		return fields;
	}

	
	/**
	 * 设置全部字段
	 * @param fields 全部字段名的list
	 */
	public void setFields(List fields) {
		this.fields = fields;
	}

	
	/**
	 * 得到实体名
	 * @return 实体名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置实体名
	 * @param name 实体名
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	/* (non-Javadoc)
	 * @see com.livedoor.dbm.components.mainframe.er.IErObject#setFocus()
	 */
	public void setFocus() {
		hasFocus = true;
		zOrder = 0;
	}
	
	/* (non-Javadoc)
	 * @see com.livedoor.dbm.components.mainframe.er.IErObject#contains(java.awt.Point)
	 */
	public boolean contains(Point p) {
		if (rect.contains(p)) {
			resetDragarea();
			if (dragArea.contains(p))
				dragFlag = Entity.resizeFlag;
			else {
				dragFlag = Entity.moveFlag;
				mouseXPoint = p.x - getX();
				mouseYPoint = p.y - getY();
			}
			return true;
		} else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.livedoor.dbm.components.mainframe.er.IErObject#lostFocus()
	 */
	public void lostFocus() {
		hasFocus = false;
		zOrder -= 1;
	}

	/**
	 * 得到实体对应的Rectangle区域
	 * @return 实体对应的Rectangle区域
	 */
	public Rectangle getRect() {
		return rect;
	}

	/**
	 * 设置实体对应的Rectangle区域
	 * @param rect 实体对应的Rectangle区域
	 */
	public void setRect(Rectangle rect) {
		
		this.rect = rect;
	}

	
	/**
	 * 检测两个实体是否重叠
	 * @param slave 参照的实体
	 * @return True 或者 False
	 */
	public boolean overlap(Entity slave) {
		Rectangle2D r = this.rect.createIntersection(slave.getRect());
		if (r.getWidth() < 1 && r.getHeight() < 1)
			return false;
		else
			return true;
	}


}

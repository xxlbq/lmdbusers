package com.livedoor.dbm.components.mainframe.er;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JComponent;

/**
 * <p>
 * Title: Er图的关系类
 * <p>
 * Description: 关系类
 * <p>
 * Copyright: Copyright (c) 2006-11-1
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class Relationship extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -225633450509466512L;
	
	private Entity master;
	private Entity slave;
	private Point2D.Double beginPoint;
	private Point2D.Double endPoint;
	
	/**
	 * 构造函数
	 * @param master 主表的实体对象
	 * @param slave 从表的实体对象
	 */
	public Relationship(Entity master,Entity slave) {
		super();
		this.master = master;
		this.slave  = slave;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		
		g.setColor(Color.BLACK);
		RelationPoint rp = new RelationPoint();
		List points = rp.getPoint(master.getRect(),slave.getRect());
		beginPoint = (Point2D.Double)points.get(0);
		endPoint = (Point2D.Double)points.get(1);
		g.drawLine((int)beginPoint.x,(int)beginPoint.y,(int)endPoint.x,(int)endPoint.y);
		int X1,Y1,X0,Y0;
		X1 = (int)endPoint.x;
		Y1 = (int)endPoint.y;
		X0 = (int)beginPoint.x;
		Y0 = (int)beginPoint.y;
		double Xa=0,Ya=0,Xb=0,Yb=0;
		
		double D = Math.abs(Point2D.distance(X1, Y1, X0, Y0));
		if (D > 0.0000000001 ){
		Xa = X1 + 10* ((X0 - X1) + (Y0 - Y1) / 2) / D;
		Ya = Y1 + 10* ((Y0 - Y1) - (X0 - X1) / 2) / D;
		Xb = X1 + 10* ((X0 - X1) - (Y0 - Y1) / 2) / D;
		Yb = Y1 + 10* ((Y0 - Y1) + (X0 - X1) / 2) / D;
		g.drawLine((int)X1,(int)Y1,(int)Xa,(int)Ya);
		g.drawLine((int)X1,(int)Y1,(int)Xb,(int)Yb);
		}
		
		Rectangle rect = new Rectangle();
		rect.x =(int)beginPoint.x - 4;
		rect.y =(int)beginPoint.y -4;
		rect.width = 8;
		rect.height = 8;
		g.drawOval(rect.x,rect.y,rect.width,rect.height);

		
	}

	
	
}

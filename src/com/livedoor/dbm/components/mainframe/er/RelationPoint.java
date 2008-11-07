package com.livedoor.dbm.components.mainframe.er;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * Title: 计算关系线的端点
 * <p>
 * Description: 计算关系线的端点
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
public class RelationPoint {
	
	/**
	 * Function: 坐标转换
	 * <p>
	 * Description: 将坐标转换到给定的点
	 * <p>
	 * 
	 * @param origin 给定原点
	 * @param x 需要转换点的x坐标
	 * @param y 需要转换点的y坐标
	 * @return 转换后的点
	 */
	private Point2D.Double coordinateTranslate(Point2D.Double origin, double x, double y) {
		double targetX = (x - origin.getX());
		double targetY = (-y - origin.getY());
		return new Point2D.Double(targetX, targetY);
	}
	
	/**
	 * Function: 坐标转换
	 * <p>
	 * Description: 将坐标转换到给定的点
	 * <p>
	 * 
	 * @param origin 给定原点
	 * @param target 需要转换的点
	 * @return 转换后的点
	 */
	private Point2D.Double coordinateTranslate(Point2D.Double origin, Point target) {
		return coordinateTranslate(origin, target.getX(), target.getY());
	}
	/**
	 * Function: 坐标转换
	 * <p>
	 * Description: 将被转换过的点转换回原坐标的点
	 * <p>
	 * 
	 * @param origin 给定原点
	 * @param x 被转换过的点的x坐标
	 * @param y 被转换过的点的y坐标
	 * @return 元始坐标的点
	 */
	private Point2D.Double targetTranslate(Point2D.Double origin, double x, double y) {
		double targetX = (x + origin.getX());
		double targetY = -(y + origin.getY());
		return new Point2D.Double(targetX, targetY);
	}

	/**
	 * Function: 获得关系线的端点
	 * <p>
	 * Description: 获得关系线的端点
	 * <p>
	 * 
	 * @param r1
	 * @param r2
	 * @return 关系线的端点
	 */
	@SuppressWarnings("unchecked")
	public List getPoint(Rectangle r1, Rectangle r2) {

		List container = new ArrayList();

		Point2D.Double origin = new Point2D.Double(r1.getCenterX(), -r1.getCenterY());
		Point2D.Double rectangleCenter = coordinateTranslate(origin, r2.getCenterX(), r2.getCenterY());

		if (Math.abs(rectangleCenter.getX()) <= 1 && rectangleCenter.getY() >= 0) {
			container.add(new Point2D.Double(r1.getCenterX(), r1.getY()));
			container.add(new Point2D.Double(r2.getCenterX(), r2.getY() + r2.getHeight()));
			return container;
		} else if (Math.abs(rectangleCenter.getX()) <= 1 && rectangleCenter.getY() <= 0) {
			container.add(new Point2D.Double(r1.getCenterX(), r1.getY() + r1.getHeight()));
			container.add(new Point2D.Double(r2.getCenterX(), r2.getY()));
			return container;
		}
		container.add(getOnePoint(r1, r2));
		container.add(getOnePoint(r2, r1));
		return container;
	}

	@SuppressWarnings("unchecked")
	private Point2D.Double getOnePoint(Rectangle r1, Rectangle r2) {
		Point2D.Double point = new Point2D.Double();

		Point2D.Double origin = new Point2D.Double(r1.getCenterX(), -r1.getCenterY());
		Point2D.Double rectangleCenter = coordinateTranslate(origin, r2.getCenterX(), r2.getCenterY());
		double tagOrigin = r1.getHeight() / r1.getWidth();
		double tagRectangle = rectangleCenter.getY() / rectangleCenter.getX();

		if (tagRectangle < 0)
			tagOrigin = -tagOrigin;

		double resultX1 = 0.0;
		double resultY1 = 0.0;

		Point2D.Double tempPoint = coordinateTranslate(origin, r2.getCenterX(), r2.getCenterY());

		if (tagRectangle <= tagOrigin && tempPoint.getX() >= 0.0 && tempPoint.getY() >= 0) {
			// 1象限下
			resultX1 = r1.getWidth() / 2;
			resultY1 = tagRectangle * resultX1;
			point = targetTranslate(origin, resultX1, resultY1);

		} else if (tagRectangle < tagOrigin && tempPoint.getX() <= 0.0 && tempPoint.getY() <= 0) {
			// 3象限上
			resultX1 = -r1.getWidth() / 2;
			resultY1 = tagRectangle * resultX1;
			point = targetTranslate(origin, resultX1, resultY1);

		} else if (tagRectangle >= tagOrigin && tempPoint.getX() >= 0.0 && tempPoint.getY() >= 0) {
			// 1象限上
			resultY1 = r1.getHeight() / 2;
			resultX1 = resultY1 / tagRectangle;
			point = targetTranslate(origin, resultX1, resultY1);

		} else if (tagRectangle >= tagOrigin && tempPoint.getX() <= 0.0 && tempPoint.getY() <= 0) {
			// 3象限下
			resultY1 = -r1.getHeight() / 2;
			resultX1 = resultY1 / tagRectangle;
			point = targetTranslate(origin, resultX1, resultY1);

		} else if (tagRectangle <= tagOrigin && tempPoint.getX() <= 0 && tempPoint.getY() >= 0.0) {
			// 2象限上
			resultY1 = r1.getHeight() / 2;
			resultX1 = resultY1 / tagRectangle;
			point = targetTranslate(origin, resultX1, resultY1);

		} else if (tagRectangle <= tagOrigin && tempPoint.getX() >= 0 && tempPoint.getY() <= 0.0) {
			// 4象限下
			resultY1 = -r1.getHeight() / 2;
			resultX1 = resultY1 / tagRectangle;
			point = targetTranslate(origin, resultX1, resultY1);

		} else if (tagRectangle >= tagOrigin && tempPoint.getX() <= 0 && tempPoint.getY() >= 0.0) {
			// 2象限下
			resultX1 = -r1.getWidth() / 2;
			resultY1 = tagRectangle * resultX1;
			point = targetTranslate(origin, resultX1, resultY1);

		} else if (tagRectangle >= tagOrigin && tempPoint.getX() >= 0 && tempPoint.getY() <= 0.0) {
			// 4象限上
			resultX1 = r1.getWidth() / 2;
			resultY1 = tagRectangle * resultX1;
			point = targetTranslate(origin, resultX1, resultY1);

		}
		return point;
	}
}

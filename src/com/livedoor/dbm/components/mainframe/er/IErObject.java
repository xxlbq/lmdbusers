package com.livedoor.dbm.components.mainframe.er;

import java.awt.Graphics;
import java.awt.Point;
/**
 * <p>
 * Title: Er对象的接口
 * <p>
 * Description: Er对象的接口
 * <p>
 * Copyright: Copyright (c) 2006-11-1
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public interface IErObject {

	/**
	 * 绘制自己的方法
	 * @param g 图像对象
	 */
	public void paint(Graphics g);
	
	/**
	 * 得到焦点
	 *
	 */
	public void setFocus();
	
	/**
	 * 失去焦点
	 */
	public void lostFocus();
	
	/**
	 * 测试一个点是不是包含在自己内部
	 * @param p
	 * @return
	 */
	public boolean contains(Point p);
	
	
}

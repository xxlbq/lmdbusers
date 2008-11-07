/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.action;

import java.awt.event.ActionListener;
import com.livedoor.dbm.components.mainframe.DBMFrame;

/**
 * <p>
 * Title: Action共同类
 * </p>
 * <p>
 * Description: 所有菜单条目的事件注册类接口,用于处理菜单条目的事件.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author zhangys
 * @version 1.0
 */
public interface IActionHandler extends ActionListener {

	public abstract void setFrame(DBMFrame frame);
	public abstract DBMFrame getFrame();
}

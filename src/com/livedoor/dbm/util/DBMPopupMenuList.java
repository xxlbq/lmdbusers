package com.livedoor.dbm.util;

import java.util.HashMap;
import javax.swing.JPopupMenu;


/**
 *根据指定关键子获取弹出菜单
 */
public class DBMPopupMenuList {
 
	/**
	 *该字段按关键子key＝popupmenu对象，保存有所有的弹出
	 *菜单对象JPopupMenu
	 */
	private static  HashMap _popupMenuList = new HashMap(50);
	 
	/**
	 *获取指定的JPopupmenu对象
	 */
	public static JPopupMenu getPopupMenu(String str) {
		 JPopupMenu jpopupmenu = (JPopupMenu)_popupMenuList.get(str);
	        return jpopupmenu;
	}
	 
	/**
	 *增加新的JPopupMenu对象
	 */
	public static void setPopupMenu(String str, JPopupMenu popupMenu) {
		_popupMenuList.put(str, popupMenu);
	}
	 
}
 

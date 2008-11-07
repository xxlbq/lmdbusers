/*
 * 
 */
package com.livedoor.dbm.util;

import javax.swing.ImageIcon;

/**
 * <p>
 * Description: 图形操作类
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author lijian
 * @version 1.0
 */
public class ImagesUtil {
	/**
	 * [功 能] 得到图形.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param s
	 *            文件路径
	 * 
	 * <p>
	 * 
	 * @return ImageIcon
	 *         <p>
	 */
	public static ImageIcon getImage(String s) {
		ImageIcon imageicon = null;
		String s1 = s.replace('.', '/');
		int i = s1.lastIndexOf(47);
		s1 = s1.substring(0, i) + "." + s1.substring(i + 1, s1.length());
		try {
			imageicon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(s1));
		} catch (Exception exception) {
			System.out.println("Warning: failed to create image: " + s1);
			imageicon = new ImageIcon();
		}
		return imageicon;
	}

}

/*
 * 
 */
package com.livedoor.dbm.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import com.livedoor.dbm.util.StringUtil;
import com.livedoor.dbm.util.DBMPropertiesUtil;
import com.livedoor.dbm.util.ImagesUtil;

/**
 * @author <a href="mailto:lijian@livedoor.cn">Jian Li </a>
 * @version 1.0
 *          <p>
 *          Description:
 *          </p>
 */
public class ResourceI18n {
	public static final ResourceI18n resource = new ResourceI18n();

	private static ResourceBundle _textBundle = null;

	private static ResourceBundle _imageBundle = null;

	private static HashMap<String, String> _textHash = new HashMap<String, String>(50);

	private static HashMap<String, ImageIcon> _imageHash = new HashMap<String, ImageIcon>(50);

	private static Locale locale = null;
	static {
		Properties properties = DBMPropertiesUtil.getUIProperties();

		String localeProperties = properties.getProperty("Language");
		if (StringUtil.isEmpty(localeProperties)) {
			localeProperties = Locale.getDefault().getLanguage().toLowerCase();
		}
		locale = new Locale(localeProperties, "", "");
	}

	private ResourceI18n() {
	}

	public static ResourceI18n getInstance() {
		return resource;
	}

	public static void setTextBundle(String s) {
		_textBundle = ResourceBundle.getBundle(s, locale);
	}

	public static final String getText(String key) {
		String s1 = null;
		s1 = (String) _textHash.get(key);
		if (s1 == null) {
			s1 = _textBundle.getString(key);
			if (s1 != null) {
				try {
					s1 = new String(s1.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException uef) {
					s1 = "Encoding Error";
				}
				_textHash.put(key, s1);
			}
		}
		return s1;
	}

	public static final String getText(String key, String defaultValue) {
		String value = getText(key);
		if (value == null)
			value = defaultValue;
		return value;
	}

	public static final int getInt(String key, int defaultValue) {
		String value = getText(key, String.valueOf(defaultValue));
		int intValue = 0;
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			intValue = defaultValue;
		}
		return intValue;
	}

	public static final int getCharInt(String s) {
		String s1 = null;
		s1 = (String) _textHash.get(s);
		if (s1 == null) {
			s1 = _textBundle.getString(s);
			if (s1 != null)
				_textHash.put(s, s1);
		}
		if (s1 != null)
			return s1.charAt(0);
		else
			return -1;
	}

	public static void setImageBundle(String s) {

		_imageBundle = ResourceBundle.getBundle(s, locale);

		try {
			for (Enumeration enumeration = _imageBundle.getKeys(); enumeration.hasMoreElements();) {
				String s2 = (String) enumeration.nextElement();
				String s1 = _imageBundle.getString(s2);
				ImageIcon imageicon = ImagesUtil.getImage(s1);
				_imageHash.put(s2, imageicon);

			}
		} catch (Exception exception1) {
			exception1.printStackTrace();
		}
	}

	public static ImageIcon getImage(String s) {
		ImageIcon imageicon = null;
		imageicon = (ImageIcon) _imageHash.get(s);
		if (imageicon == null) {
			String s1 = _imageBundle.getString(s);
			imageicon = ImagesUtil.getImage(s1);
			if (imageicon != null)
				_imageHash.put(s, imageicon);
		}
		return imageicon;
	}

	/**
	 * 设定当前的Locale
	 * 
	 * @param locale
	 */
	public static void setLocale(Locale locale) {
		ResourceI18n.locale = locale;
	}

}

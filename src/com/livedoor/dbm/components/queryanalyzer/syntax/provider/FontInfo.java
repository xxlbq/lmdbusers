package com.livedoor.dbm.components.queryanalyzer.syntax.provider;

import java.awt.Font;

public class FontInfo implements Cloneable
{
	public interface IPropertyNames
	{
		String FAMILY = "family";
		String IS_BOLD = "isBold";
		String IS_ITALIC = "isItalic";
		String SIZE = "size";
	}

	private static String DEFAULT_FAMILY = "Monospaced";

	private String _familyName;
	private boolean _isBold;
	private boolean _isItalic;
	private int _size;

	public FontInfo()
	{
		super();
		setFamily(DEFAULT_FAMILY);
		setSize(12);
	}

	public FontInfo(Font font)
	{
		super();
		if (font == null)
		{
			throw new IllegalArgumentException("Null Font passed");
		}
		setFont(font);
	}

	/**
	 * Return a copy of this object.
	 */
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			throw new InternalError(ex.getMessage()); // Impossible.
		}
	}

	public String getFamily()
	{
		return _familyName;
	}

	public void setFamily(String value)
	{
		_familyName = value != null ? value : DEFAULT_FAMILY;
	}

	public boolean isBold()
	{
		return _isBold;
	}

	public void setIsBold(boolean value)
	{
		_isBold = value;
	}

	public boolean isItalic()
	{
		return _isItalic;
	}

	public void setIsItalic(boolean value)
	{
		_isItalic = value;
	}

	public int getSize()
	{
		return _size;
	}

	public void setSize(int value)
	{
		_size = value;
	}

	public void setFont(Font font) throws IllegalArgumentException
	{
		if (font == null)
		{
			throw new IllegalArgumentException("Null Font passed");
		}
		_familyName = font.getFamily();
		_isBold = font.isBold();
		_isItalic = font.isItalic();
		_size = font.getSize();
	}

	public boolean doesFontMatch(Font font)
	{
		if (font == null)
		{
			return false;
		}
		return font.getFamily().equals(_familyName)
			&& font.getSize() == getSize()
			&& font.getStyle() == generateStyle();
	}

	public int generateStyle()
	{
		int style = 0;
		if (!_isBold && !_isItalic)
		{
			style = Font.PLAIN;
		}
		else
		{
			if (_isBold)
			{
				style |= Font.BOLD;
			}
			if (_isItalic)
			{
				style |= Font.ITALIC;
			}
		}
		return style;
	}

	public Font createFont()
	{
		return new Font(_familyName, generateStyle(), _size);
	}

	// i18n ? What is this used for?
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append(_familyName).append(", " + _size);
		if (_isBold)
		{
			buf.append(", bold");
		}
		if (_isItalic)
		{
			buf.append(", italic");
		}
		return buf.toString();
	}
}

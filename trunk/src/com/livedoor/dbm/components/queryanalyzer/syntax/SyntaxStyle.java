package com.livedoor.dbm.components.queryanalyzer.syntax;


import java.awt.Color;

public class SyntaxStyle {
	/** name fo this style. */
	private String _name;

	/** Is this an italic style. */
	private boolean _isItalic = false;

	/** Is this an bold style. */
	private boolean _isBold = false;

	private int _textRGB = Color.black.getRGB();

	private int _backgroundRGB = Color.white.getRGB();

	/**
	 * Default ctor.
	 */
	public SyntaxStyle() {
		super();
	}

	/**
	 * Copy ctor.
	 */
	public SyntaxStyle(SyntaxStyle rhs) {
		super();
		setName(rhs.getName());
		setItalic(rhs.isItalic());
		setBold(rhs.isBold());
		setTextRGB(rhs.getTextRGB());
		setBackgroundRGB(rhs.getBackgroundRGB());
	}

	/**
	 * Retrieve the name of this style.
	 * 
	 * @return The name of this style.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Set the name of this style.
	 * 
	 * @value The name of this style.
	 */
	public void setName(String value) {
		_name = value;
	}

	/**
	 * Is this an italic style?
	 * 
	 * @return <TT>true</TT> if this is an italic style.
	 */
	public boolean isItalic() {
		return _isItalic;
	}

	/**
	 * Specify whether this is an italic style.
	 * 
	 * @param value
	 *            <TT>true</TT> if this is an italic style.
	 */
	public void setItalic(boolean value) {
		_isItalic = value;
	}

	/**
	 * Is this a bold style?
	 * 
	 * @return <TT>true</TT> if this is a bold style.
	 */
	public boolean isBold() {
		return _isBold;
	}

	/**
	 * Specify whether this is a bold style.
	 * 
	 * @param value
	 *            <TT>true</TT> if this is a bold style.
	 */
	public void setBold(boolean value) {
		_isBold = value;
	}

	/**
	 * Retrieve the RGB value for the text color.
	 * 
	 * @return RGB value for text color.
	 */
	public int getTextRGB() {
		return _textRGB;
	}

	/**
	 * Set the RGB value for the text color.
	 * 
	 * @param value
	 *            The RGB value for text color.
	 */
	public void setTextRGB(int value) {
		_textRGB = value;
	}

	/**
	 * Retrieve the RGB value for the background color.
	 * 
	 * @return RGB value for text color.
	 */
	public int getBackgroundRGB() {
		return _backgroundRGB;
	}

	/**
	 * Set the RGB value for the background color.
	 * 
	 * @param value
	 *            The RGB value for text color.
	 */
	public void setBackgroundRGB(int value) {
		_backgroundRGB = value;
	}
}

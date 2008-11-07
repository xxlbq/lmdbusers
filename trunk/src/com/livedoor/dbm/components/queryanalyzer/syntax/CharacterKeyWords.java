/**
 * $Id: CharacterKeyWords.java,v 1.1 2006/10/17 01:10:36 lijian Exp $
 * 着色关键字
 */
package com.livedoor.dbm.components.queryanalyzer.syntax;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: 着色关键字</p> 
 * <p>Description:
 *		着色关键字在着色之前维护,在着色时用来匹配编辑器中记号以决定着那种颜色
 * </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class CharacterKeyWords {

	/**
	 * 预留关键字
	 */
	public  Map<String, String> RESERVED_WORD_MAP = new HashMap<String, String>();

	/**
	 * 数据类型
	 */
	public Map<String, String> DATA_TYPE_MAP = new HashMap<String, String>();

	/**
	 * 列名 
	 */
	public  Map<String, String> COLUMN_MAP = new HashMap<String, String>();
	
	/**
	 * 表名
	 */
	public  Map<String, String> TABLE_ARRAY = new HashMap<String, String>();

	
	/**
	 * 构造器
	 * com.Ostermiller.Syntax.Lexer.SQLLexer返回的记号已经准确准确描述了预留关键字,数据类型关键字
	 * 因此不需要在RESERVED_WORD_MAP,DATA_TYPE_MAP中放置数据
	 * TABLE_ARRAY 和 COLUMN_MAP 需要实时修改,也不需要在此放置数据
	 */
	public  CharacterKeyWords(){
//		RESERVED_WORD_MAP.put("SELECT", "SELECT");
//		RESERVED_WORD_MAP.put("WHERE", "WHERE");
//		RESERVED_WORD_MAP.put("FROM", "FROM");

//		DATA_TYPE_MAP.put("INTEGER", "INTEGER");
//		DATA_TYPE_MAP.put("VCHAR", "VCHAR");
//		DATA_TYPE_MAP.put("BOLB", "BOLB");
	}
	
}

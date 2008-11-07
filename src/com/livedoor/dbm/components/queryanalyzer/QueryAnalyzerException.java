/**
 * $Id: QueryAnalyzerException.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * 查询分析器异常
 */
package com.livedoor.dbm.components.queryanalyzer;

/**
 * <p>Title: 查询分析器异常</p> 
 * <p>Description:
 * 		所有查询分析器相关产生异常
 * </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class QueryAnalyzerException extends RuntimeException {

	public QueryAnalyzerException() {
		super();
	}

	/**
	 * @param message
	 */
	public QueryAnalyzerException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public QueryAnalyzerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public QueryAnalyzerException(Throwable cause) {
		super(cause);
	}

}

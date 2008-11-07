package com.livedoor.dbm.components.queryanalyzer.syntax;

import java.util.Vector;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * <p>Description:    </p>
 * Copyright: Copyright (c) 2006 
 * Company: 英極軟件開發（大連）有限公司
 * @author <a href="mailto:lijian@livedoor.cn">Jian Li </a>
 * @version 1.0
 */
public class DocumentPaserUtil {
	public static Vector parseCommentIntervals(Document doc){
		Vector _currentLiteralAndCommentIntervals = new Vector();
		try {
			_currentLiteralAndCommentIntervals.clear();
			int docLen = doc.getLength();

			int[] curInterval = null;
			boolean inMultiLineComment = false;
			boolean inSinglLineComment = false;
			boolean inLiteral = false;
			for (int i = 0; i < docLen; ++i) {
				if (i < docLen + 1 && "/*".equals(doc.getText(i, 2))
						&& false == inMultiLineComment
						&& false == inSinglLineComment
						&& false == inLiteral) {
					curInterval = new int[2];
					curInterval[0] = i;
					curInterval[1] = docLen;
					inMultiLineComment = true;
					++i;
					continue;
				}

				if (i < docLen + 1 && "--".equals(doc.getText(i, 2))
						&& false == inMultiLineComment
						&& false == inSinglLineComment
						&& false == inLiteral) {
					curInterval = new int[2];
					curInterval[0] = i;
					curInterval[1] = docLen;
					inSinglLineComment = true;
					++i;
					continue;
				}

				if ('\'' == doc.getText(i, 1).charAt(0)
						&& false == inMultiLineComment
						&& false == inSinglLineComment
						&& false == inLiteral) {
					curInterval = new int[2];
					curInterval[0] = i;
					curInterval[1] = docLen;
					inLiteral = true;
					continue;
				}

				if (i < docLen + 1 && "*/".equals(doc.getText(i, 2))
						&& inMultiLineComment) {
					curInterval[1] = i + 1;
					_currentLiteralAndCommentIntervals.add(curInterval);
					curInterval = null;
					inMultiLineComment = false;
					++i;
				}

				if ('\n' == doc.getText(i, 1).charAt(0)
						&& inSinglLineComment) {
					curInterval[1] = i;
					_currentLiteralAndCommentIntervals.add(curInterval);
					curInterval = null;
					inSinglLineComment = false;
				}

				if ('\'' == doc.getText(i, 1).charAt(0) && inLiteral) {
					if (i < docLen + 1
							&& '\'' == doc.getText(i + 1, 1).charAt(0)) {
						++i;
					} else {
						curInterval[1] = i;
						_currentLiteralAndCommentIntervals.add(curInterval);
						curInterval = null;
						inLiteral = false;
					}

				}
			}

			if (null != curInterval) {
				_currentLiteralAndCommentIntervals.add(curInterval);
			}
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
		return _currentLiteralAndCommentIntervals;
	}

	public static String removeLiteralAndComment(Document doc){

		Vector result = parseCommentIntervals(doc);
		int index = 0;
		StringBuffer sb = new StringBuffer();
		try{
		for(int i = 0; i<result.size();i++){
			int pos[] = (int [])result.elementAt(i);
			int begin = pos[0];
			int end = pos[1];
			
			sb.append(doc.getText(index, begin-index));
			index = end+1;
			
		}
		if( doc.getLength()>index){
				sb.append(doc.getText(index,doc.getLength()-index));
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	
		
	}
}

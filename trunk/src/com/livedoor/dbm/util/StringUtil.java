/**
 * $Id: StringUtil.java,v 1.6 2006/12/08 03:54:08 lijian Exp $
 * 字符串工具类
 */
package com.livedoor.dbm.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.sql.Blob;
import java.sql.Clob;
import java.util.List;

/**
 * <p>Title: 字符串工具类</p> 
 * <p>Description:
 * 		字符串工具类
 * </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class StringUtil {

	/**
	 * 如果src长度小于blanks,在src左边补空格 
	 * 否则直接返回src
	 * 
	 * @param src
	 * @param blanks
	 */
	public static String leftPad(String src, int blanks) {
		if (src.length() < blanks) {
			int repeats = blanks - src.length();
			StringBuilder sb = new StringBuilder();
			sb.append(fillString(' ', repeats));
			sb.append(src);
			return sb.toString();
		}

		return src;
	}

	/**
	 * 如果src长度小于blanks,在src右边补空格 
	 * 否则直接返回src
	 * 
	 * @param src
	 * @param blanks
	 */
	public static String rightPad(String src, int blanks) {
		if (src.length() < blanks) {
			int repeats = blanks - src.length();
			StringBuilder sb = new StringBuilder();
			sb.append(src);
			sb.append(fillString(' ', repeats));
			return sb.toString();
		}
		return src;

	}

	/**
	 * 在src两边补空格 
	 * 如果blanks大于src的长度，分别在左边和右边补空格 
	 * 如果blanks-src.length为2n,左边和右边补n个空格
	 * 如果blanks-src.length为2n+1,左边补n个空格，右边补n+1个空格
	 * 
	 * @param src
	 * @param blanks
	 */
	public static String pad(String src, int blanks) {
		if (src.length() < blanks) {
			StringBuilder sb = new StringBuilder();
			int repeats = (blanks - src.length()) / 2;
			sb.append(fillString(' ', repeats));
			sb.append(src);
			sb.append(fillString(' ', blanks - repeats));
			return sb.toString();
		}

		return src;
	}

	/**
	 * @param ch
	 * @param repeats
	 * @return repeats个ch构成的字符串
	 */
	public static String fillString(char ch, int repeats) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < repeats; i++) {
			sb.append(ch);
		}
		return sb.toString();
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static String cleanString(String str) {
		final StringBuffer buf = new StringBuffer(str.length());
		char prevCh = ' ';

		for (int i = 0, limit = str.length(); i < limit; ++i) {
			char ch = str.charAt(i);
			if (Character.isWhitespace(ch)) {
				if (!Character.isWhitespace(prevCh)) {
					buf.append(' ');
				}
			} else {
				buf.append(ch);
			}
			prevCh = ch;
		}

		return buf.toString();
	}
	/**
	 * 判断字符串是不是空.
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		return (param == null || param.length() < 1);
	}
	/**
	 * 判断字符串是不是空.
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(String param) {
		return !(isEmpty(param));
	}
	/**
	 * 判断list是不是空.
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(List param) {
		return (param==null || param.isEmpty());
	}
	/**
	 * 判断list是不是空.
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(List param) {
		return !isEmpty(param);
	}
//    public static Object  blobToObject(Blob obj){
//          Object result ="Err";
//          Blob blob = (Blob)obj;
//          long l = 0L;
//          java.io.InputStream inputstream = null;
//          try
//          {
//              inputstream = blob.getBinaryStream();
//              l = blob.length();
//          }
//          catch(Exception exception9)
//          {
//              inputstream = null;
//          }
//          int k = 8192;
//          Object obj3 = null;
//          byte abyte1[] = new byte[(int)l];
//          if(inputstream == null)
//          {
//              result = "Err";
//          } else
//          {
//              BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
//              try
//              {
//                  int i = -1;
//                  int i1 = 0;
//                  int j1 = 0;
//                  boolean flag5 = false;
//                  if(i != 0)
//                      do
//                      {
//                          int k1;
//                          if((k1 = bufferedinputstream.read(abyte1, j1, k)) == -1)
//                              break;
//                          i1++;
//                          j1 += k1;
//                          if(i > 0 && i1 >= i)
//                              break;
//                          if(l - (long)j1 < (long)k)
//                              k = (int)l - j1;
//                      } while(k != 0);
//                  bufferedinputstream.close();
//                  byte abyte0[] = new byte[j1];
//                  System.arraycopy(abyte1, 0, abyte0, 0, abyte0.length);
//                  result = abyte0;
//              }
//              catch(Exception exception11)
//              {
//                  result = "Err";
//              }
//          }
//          return result;
//      }
    /**
     * 取出CLOB类型中的内容
     * @param obj
     * @return
     */
    public static String clobToString(Clob obj){
        String result ="Err";
        Clob clob = (Clob)obj;
        java.io.Reader reader = null;
        try{
            reader = clob.getCharacterStream();
        }catch(Exception exception){
            exception.printStackTrace();
            reader = null;
        }
        if(reader == null){
            result = "Err";
        } else{
            StringBuffer stringbuffer = new StringBuffer();
            BufferedReader bufferedreader = new BufferedReader(reader);
            try{
                String tmp = bufferedreader.readLine() ;
                while(tmp!= null ){
                    stringbuffer.append(tmp);
                    stringbuffer.append('\n');
                    tmp = bufferedreader.readLine() ;
                }
             
                bufferedreader.close();
                result = stringbuffer.toString();
            }
            catch(Exception e){
                result = "Err";
            }
        }
        return result;
    }
}

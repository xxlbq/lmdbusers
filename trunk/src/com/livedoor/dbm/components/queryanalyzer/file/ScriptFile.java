/**
 * $Id: ScriptFile.java,v 1.4 2006/10/30 08:41:12 lijc Exp $
 * 脚本保存工具类
 */
package com.livedoor.dbm.components.queryanalyzer.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.livedoor.dbm.components.queryanalyzer.QueryAnalyzerException;
import com.livedoor.dbm.util.StringUtil;

/**
 * <p>Title: 脚本保存工具类</p> 
 * <p>Description:
 * 		保存脚本到文件中
 * 		或从文件中取出脚本
 * </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class ScriptFile {

	/**
     * 把脚本保存到文件中
     * @param fileName 文件名
     * @param script SQL脚本
     * @param charset 字符集
     */
    public static void save(String fileName, String script, String charset) {
        OutputStreamWriter writer = null;
        try {
            OutputStream out = new FileOutputStream(fileName);
            if (StringUtil.isEmpty(charset))
                writer = new OutputStreamWriter(out);
            else
                writer = new OutputStreamWriter(out, charset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryAnalyzerException(e);
        }

        try {
            writer.write(script);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new QueryAnalyzerException(e);
        }
    }

    /**
     * 从文件中取出脚本
     * @param fileName 文件名
     * @param charset 字符集
     * @return SQL脚本
     */
    public static String get(String fileName, String charset) {
        InputStreamReader reader = null;
        try {
            InputStream in = new FileInputStream(fileName);
            if (StringUtil.isEmpty(charset))
                reader = new InputStreamReader(in);
            else 
                reader = new InputStreamReader(in, charset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryAnalyzerException(e);
        }

        StringBuilder sbScript = new StringBuilder();
        try {
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                sbScript.append((char) ch);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new QueryAnalyzerException(e);
        }

        return sbScript.toString();
    }
    
    /**
     * 添加文件扩展名
     * @param name
     * @param extension
     * @return
     */
    public static String appendExtension(String name, String extension) {
        if(extension==null) 
            return name;

        if(name.toLowerCase().endsWith(extension))
            return name;

        return name + extension;
    }
}

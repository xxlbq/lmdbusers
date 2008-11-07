/**
 * $Id: ScriptTokenizer.java,v 1.5 2006/11/22 07:20:35 lijc Exp $
 * 查询分析器
 */
package com.livedoor.dbm.components.queryanalyzer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.livedoor.dbm.util.StringUtil;

/**
 * <p>Title: 脚本处理</p> 
 * <p>Description:
 * 		脚本提交到数据库前做处理:
 * 		去掉注释,拆分成多条sql语句,去掉'\n',去掉多余空格
 * </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class ScriptTokenizer {
	private String script;

    //SQL脚本注释规则
	private CommentSpec[] commentSpecs = {
            new CommentSpec("--", "\n"), 
            new CommentSpec("/*", "*/")};

    //sql语句分隔符,分割SQL脚本为多个SQL语句
	private String stmtSeparator = "go";

	private Iterator iterator;

	private List<String> scripts;

	public ScriptTokenizer(String script) {
		this(script, null, null);
	}

	public ScriptTokenizer(String script, CommentSpec[] commentSpecs) {
		this(script, commentSpecs, null);
	}

	public ScriptTokenizer(String script, CommentSpec[] commentSpecs, String stmtSeparator) {
		this.script = script;
		if (commentSpecs != null) {
			this.commentSpecs = commentSpecs;
		}
		if (stmtSeparator != null) {
			this.stmtSeparator = stmtSeparator;
		}

		// 去掉注释
		removeComment();

		// 拆分成多条sql语句
		this.scripts = toScripts();
		this.iterator = scripts.iterator();
	}

	// 去掉sql脚本中的注释
	private void removeComment() {
		String lineCommentBegin = commentSpecs[0].commentBegin;
		String lineCommentEnd = commentSpecs[0].commentEnd;
		String multiLineCommentBegin = commentSpecs[1].commentBegin;
		String multiLineCommentEnd = commentSpecs[1].commentEnd;

		StringBuilder sbScript = new StringBuilder();

		boolean isInLineComment = false;
		boolean isInMultiLineComent = false;
		boolean isInLiteral = false;

		for (int i = 0; i < script.length(); i++) {
			char ch = script.charAt(i);

			if (isInLineComment && script.startsWith(lineCommentEnd, i - lineCommentEnd.length())) {
				isInLineComment = false;
			}
			if (isInMultiLineComent && script.startsWith(multiLineCommentEnd, i - multiLineCommentEnd.length())) {
				isInMultiLineComent = false;
			}
			if (isInLiteral && script.startsWith("'", i - 1)) {
				isInLiteral = false;
			}
			if (!isInMultiLineComent && !isInLineComment && !isInLiteral) {
				isInLineComment = script.startsWith(lineCommentBegin, i);
				isInMultiLineComent = script.startsWith(multiLineCommentBegin, i);
				isInLiteral = script.startsWith("'", i);
			}

			if (isInLineComment || isInMultiLineComent) {
				continue;
			}

			sbScript.append(ch);
		}

		this.script = sbScript.toString();
	}

	private List<String> toScripts() {
		List<String> pieces = new ArrayList<String>();
		while (script.length() > 0) {
			for (int i = 0; i < script.length(); i++) {
				int index = script.toLowerCase().indexOf(stmtSeparator);
				String _script = "";
				int preIndex = index + stmtSeparator.length();
                if ( ((index != -1) && isReachEnd(preIndex)) || 
                      ((index != -1) && !isReachEnd(preIndex) && Character.isWhitespace(script.charAt(preIndex))) ) { 
    					_script = script.substring(0, index);
    					script = script.substring(preIndex);
				} else {
					_script = script;
					script = "";
				}
                _script = StringUtil.cleanString(_script);
                if(StringUtil.isNotEmpty(_script))
                    pieces.add(_script);
			}
		}

		return pieces;
	}

    /**
     * 是否到达脚本结尾
     */
    private boolean isReachEnd(int index) {
        return index==script.length();
    }

	public boolean hasScripts() {
		return iterator.hasNext();
	}

	public String nextScript() {
		return (String) iterator.next();
	}

	public String[] toArray() {
		String[] _scripts = new String[scripts.size()];
		return scripts.toArray(_scripts);
	}
}

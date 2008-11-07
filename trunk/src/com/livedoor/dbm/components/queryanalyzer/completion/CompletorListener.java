/**
 * $Id: CompletorListener.java,v 1.1 2006/10/17 01:10:35 lijian Exp $ 
 * SQL编辑器帮助
 */
package com.livedoor.dbm.components.queryanalyzer.completion;

public interface CompletorListener {

    /**
     * @param completion
     * @param replaceBegin This parameter is usefull only when the editor and
     *        the filter editor are the same.
     * @param keyCode
     */
    void completionSelected(CompletionInfo completion, int replaceBegin, int keyCode);
    
}

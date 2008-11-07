/**
 * $Id: ICompletorModel.java,v 1.1 2006/10/17 01:10:35 lijian Exp $ 
 * SQL编辑器帮助
 */
package com.livedoor.dbm.components.queryanalyzer.completion;


public interface ICompletorModel {
    
    /**
     * @param textTillCarret Is only the text till carret if editor and filter
     *        are the same. If there is an extra filter text field the complete
     *        text in this text field is passed.
     * @return
     */
    public CompletionCandidates getCompletionCandidates(String textTillCarret);
    
}

/**
 * $Id: ToolsPopupController.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * SQL编辑器帮助
 */
package  com.livedoor.dbm.components.queryanalyzer.completion;

import java.awt.Color;
import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.text.JTextComponent;

/**
 * <p>Title: 弹出控制类</p> 
 * <p>Description: 该类负责把查询分析器和SQL辅助模块关联起来</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class ToolsPopupController {
    // 弹出菜单条目模型
    private ToolsPopupCompletorModel _toolsPopupCompletorModel;

    private Completor _toolsCompletor;

    public ToolsPopupController(final JEditorPane queryAnaylizerEdiotText) {

        _toolsPopupCompletorModel = new ToolsPopupCompletorModel();

        _toolsCompletor = new Completor((JTextComponent) queryAnaylizerEdiotText, _toolsPopupCompletorModel, new Color(255, 204, 204), true);

    }
    public void show() {
        _toolsCompletor.show();
    }

    /**
     * 该方法为扩展目的，便于以后使用
     * 
     * @param selectionString
     * @param action
     */
    public void addAction(String selectionString, Action action) {
        _toolsPopupCompletorModel.addAction(selectionString, action);
    }
    /**
     * 向弹出的菜单中添加新的条目
     * 
     * @param selectionString 要添加的弹出菜单的条目描述
     */
    public void addAction(String selectionString) {
        _toolsPopupCompletorModel.addAction(selectionString);
    }
    public ToolsPopupCompletorModel getCompletorModel() {
        return this._toolsPopupCompletorModel;
    }
    public void clear() {
        _toolsPopupCompletorModel.clear();
    }
}

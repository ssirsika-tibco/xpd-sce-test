/**
 * 
 */
package com.tibco.xpd.processwidget.tools;

import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.tools.PanningSelectionTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Cursor;

import com.tibco.xpd.resources.util.ElapsedTimerHelper;
import com.tibco.xpd.ui.properties.util.ShowViewUtil;

/**
 * @author wzurek
 * 
 */
public class PanningSelectionToolWithSpyglass extends PanningSelectionTool {

    private static final int SPYGLASS = MAX_STATE << 1;

    private boolean isAltDown;

    @Override
    protected boolean handleButtonDown(int which) {
        ElapsedTimerHelper.initTiming(75);
        ElapsedTimerHelper.timedMsg("\n==> "+this.getClass().getSimpleName() + ": Select @");   //$NON-NLS-1$//$NON-NLS-2$

        boolean ret = super.handleButtonDown(which);
        
        return ret;
    }
    
    protected boolean acceptLeftAlt(KeyEvent e) {
        if (e.keyCode == SWT.ALT) {
            e.doit = false;
            return true;
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.gef.tools.AbstractTool#handleDoubleClick(int)
     */
    @Override
    protected boolean handleDoubleClick(int button) {
        ShowViewUtil.showOrDetachPropertiesView(null, null);
        return true;
    }

    protected boolean handleKeyDown(KeyEvent e) {
        if (acceptLeftAlt(e)) {
            isAltDown = true;
            if (stateTransition(STATE_INITIAL, SPYGLASS)) {
                refreshCursor();
            }
            return true;
        }
        return super.handleKeyDown(e);
    }

    protected boolean handleKeyUp(KeyEvent e) {
        if (acceptLeftAlt(e)) {
            isAltDown = false;
            if (stateTransition(SPYGLASS, STATE_INITIAL)) {
                refreshCursor();
            }
            return true;
        }
        return super.handleKeyUp(e);
    }

    /**
     * Returns the cursor used under normal conditions.
     * 
     * @see #setDefaultCursor(Cursor)
     * @return the default cursor
     */
    protected Cursor getDefaultCursor() {
        if (isInState(SPYGLASS))
            return SharedCursors.CROSS;
        return super.getDefaultCursor();
    }

    /**
     * @see org.eclipse.gef.tools.SelectionTool#handleFocusLost()
     */
    protected boolean handleFocusLost() {
        if (stateTransition(SPYGLASS, STATE_INITIAL)) {
            refreshCursor();
            return true;
        }
        return super.handleFocusLost();
    }
    
    protected boolean handleHover() {
        return super.handleHover();
    }
    
    protected boolean handleHoverStop() {
        return super.handleHoverStop();
    }
}

/**
 * MultilineTextCellEditor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * MultilineTextCellEditor
 * 
 * Although Eclipse jface TextCellEditor allows you to use the style SWT.MULTI
 * etc it does not size the control so that it looks multi-line.
 * <p>
 * This class extends TextCellEditor just to force a height upon the edit box.
 * </p>
 */
public class MultilineTextCellEditor extends TextCellEditor {

    private class ForceMinHeightControlListener implements
            ControlListener {
        
        private boolean alreadyHere = false; // Prevent recursion!

        private Control control;
        
        private int minimumHeight = 100;

        public ForceMinHeightControlListener(Control control) {
            super();
            this.control = control;
        }
        
        public void controlResized(ControlEvent e) {
            if (!alreadyHere) {
                alreadyHere = true; // Prevent recursion.
                
                if (!control.isDisposed()) {
                    Point sz = control.getSize();
                    
                    if (sz.y < minimumHeight) {
                        sz.y = minimumHeight;
                        control.setSize(sz);
                    }
                }
                
                alreadyHere = false;
            }
            
            return;
        }
        
        public void controlMoved(ControlEvent e) {
        }

    }

    public static final int DEFAULT_STYLE =
            SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL;

    private ForceMinHeightControlListener forceHeightListener;
    
    public MultilineTextCellEditor(Composite parent) {
        super(parent, DEFAULT_STYLE);
    }

    public MultilineTextCellEditor(Composite parent, int style) {
        super(parent, style);
    }

    @Override
    protected Control createControl(Composite parent) {
        Control ctrl = super.createControl(parent);

        forceHeightListener = new ForceMinHeightControlListener(ctrl);
        
        // Add a resize listener that forces a height upon
        ctrl.addControlListener(forceHeightListener);

        return ctrl;
    }

    /**
     * @return the minimumHeight
     */
    public int getMinimumHeight() {
        return forceHeightListener.minimumHeight;
    }

    /**
     * @param minimumHeight
     *            the minimumHeight to set
     */
    public void setMinimumHeight(int minimumHeight) {
        forceHeightListener.minimumHeight = minimumHeight;
    }
}


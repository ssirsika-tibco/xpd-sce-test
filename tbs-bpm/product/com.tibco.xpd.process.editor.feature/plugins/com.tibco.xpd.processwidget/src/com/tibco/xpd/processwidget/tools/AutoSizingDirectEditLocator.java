/**
 * AutoSizingDirectEditLocator.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.tools;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * AutoSizingDirectEditLocator
 * <p>
 * <li>Direct edit locator that resize control as more text is entered.</li>
 * <li>The width of the edit box control is set to the text bounds given by sub-class</li>
 * <li>The height is adjusted as the user types to fit the whole text</li> 
 */
public abstract class AutoSizingDirectEditLocator implements CellEditorLocator {
    boolean autoResizeWidth = false;
    boolean verticalCentre = false;
    
    /**
     * 
     */
    public AutoSizingDirectEditLocator(boolean resizeWidth, boolean verticalCentre) {
        autoResizeWidth = resizeWidth;
        this.verticalCentre = verticalCentre;
    }
    
    /**
     * 
     */
    public AutoSizingDirectEditLocator() {
        this(false, false);
    }
    
    /**
     * Return the text bounds for on-diagram figure
     * (translated to absolute).
     * @return
     */
    public abstract Rectangle getTextBoundsLocation();
    
    /**
     * Return the desired width of edit box.
     * @return
     */
    public abstract int getDesiredWidth();
    
    public final void relocate(CellEditor celleditor) {
        Rectangle textBounds = getTextBoundsLocation();

        Point loc = textBounds.getTopLeft();

        Text t = (Text) celleditor.getControl();

        int wHint = getDesiredWidth();
        
        if (autoResizeWidth) {
            wHint = SWT.DEFAULT;
        }
        
        org.eclipse.swt.graphics.Point prefSize = t.computeSize(wHint,
                SWT.DEFAULT, true);
        org.eclipse.swt.graphics.Rectangle ctrlBnds = t.getBounds();

        int height = prefSize.y; // + t.getLineHeight();

        if (verticalCentre) {
            loc.y += textBounds.height / 2;
            loc.y -= height / 2;
        }
        
        if (autoResizeWidth && (t.getStyle() & SWT.CENTER) != 0) {
            if (prefSize.x != ctrlBnds.width) {
                // recenter auto-sized and centered direct edit control.
         
                loc.x = (loc.x + (textBounds.width/2)) - (prefSize.x / 2);
                
            }
        }
        
        if (loc.x != ctrlBnds.x || loc.y != ctrlBnds.y
                || prefSize.x != ctrlBnds.width || height != ctrlBnds.height) {
            t.setBounds(loc.x, loc.y, prefSize.x, height);

            t.update();
        }
    }
}


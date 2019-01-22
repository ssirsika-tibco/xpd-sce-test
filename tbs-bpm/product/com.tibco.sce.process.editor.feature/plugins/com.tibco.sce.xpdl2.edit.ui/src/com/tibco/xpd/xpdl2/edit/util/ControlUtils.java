package com.tibco.xpd.xpdl2.edit.util;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;

/**
 * Util class for performing common operations on UI Controls displayed throughout the program whether 
 * it be in sections, dialogs or pagebooks and so on.
 * @author glewis
 */
public class ControlUtils {
    
    /**
     * Sometimes controls can be hidden or half shown and so this calculates the width of
     * a controls depending on the current font being used and the string it should be displaying.
     * The size could change if user sets differnt fonts in preferences or if they change locale and so on.
     * @param control
     * @param text
     */
    public static void forceWidgetVisible(Control control,String text){
        if (control.getLayoutData() instanceof GridData){
            GridData gridData = (GridData)control.getLayoutData();
            int minWidth = FigureUtilities.getTextExtents(text,control.getFont()).width;
            gridData.widthHint = minWidth + 10;
            control.setLayoutData(gridData);
        }
    }
    
}

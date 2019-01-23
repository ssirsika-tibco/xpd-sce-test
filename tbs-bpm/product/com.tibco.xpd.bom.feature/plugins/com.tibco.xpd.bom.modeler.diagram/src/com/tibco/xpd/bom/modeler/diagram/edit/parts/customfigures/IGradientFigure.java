/**
 * 
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import org.eclipse.swt.graphics.Color;

/**
 * A figure that has methods to update gradient colors.
 * 
 * @author wzurek
 */
public interface IGradientFigure {

    /**
     * Set start gradient color.
     * 
     * @param gradColor1
     */
    public void setGradientStart(Color gradColor1);

    /**
     * Set end gradient color.
     * 
     * @param gradColor1
     */
    public void setGradientEnd(Color gradColor2);
}

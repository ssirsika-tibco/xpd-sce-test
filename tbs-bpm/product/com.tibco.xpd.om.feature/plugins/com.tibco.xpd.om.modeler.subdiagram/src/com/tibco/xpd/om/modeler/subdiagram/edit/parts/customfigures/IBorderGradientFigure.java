/**
 * 
 */
package com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures;

import org.eclipse.swt.graphics.Color;

/**
 * A figure that has methods to update gradient colors using the
 * BorderGradientStyle.
 * 
 * @author rgreen
 */
public interface IBorderGradientFigure extends IGradientFigure {

    /**
     * Set start gradient color.
     * 
     * @param gradColor1
     */
    public void setBorderGradientStart(Color gradColor1);

    /**
     * Set end gradient color.
     * 
     * @param gradColor1
     */
    public void setBorderGradientEnd(Color gradColor2);
}

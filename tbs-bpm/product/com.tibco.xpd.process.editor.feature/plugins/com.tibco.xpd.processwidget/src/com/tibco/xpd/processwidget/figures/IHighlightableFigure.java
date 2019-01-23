/**
 * IBorderHighLightFigure.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.IFigure;


/**
 * IBorderHighLightFigure
 * 
 */
public interface IHighlightableFigure extends IFigure {

    /**
     * Set highlight on / off.
     * 
     * @param on
     */
    void setHighlight(boolean on);

}

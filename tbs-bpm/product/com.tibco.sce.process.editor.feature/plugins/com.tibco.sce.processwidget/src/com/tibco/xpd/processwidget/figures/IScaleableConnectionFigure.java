/**
 * IScaleableConnectionFigure.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures;

/**
 * IScaleableConnectionFigure
 *
 */
public interface IScaleableConnectionFigure {

    /**
     * Source and target decorations need to be scaled to the
     * source/targetobject's level.
     * 
     * @param sourceDecorationScale
     *            the sourceDecorationScale to set
     */
    void setSourceDecorationScale(double sourceDecorationScale);

    /**
     * Source and target decorations need to be scaled to the
     * source/targetobject's level.
     * 
     * @param targetDecorationScale
     *            the targetDecorationScale to set
     */
    void setTargetDecorationScale(double targetDecorationScale);

}

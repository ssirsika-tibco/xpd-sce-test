package com.tibco.xpd.processwidget.neatstuff;

import org.eclipse.draw2d.IFigure;

/**
 * IFadeableFigure
 * <p>
 * Simple interface that will allow us to fade figures up and down.
 * <p>
 * <b>Note that it is up to the interface implementor to store the last set
 * alpha and take it into account during its paintFigure().
 * 
 * @author aallway
 */
public interface IFadeableFigure extends IFigure {
    void setAlpha(int alpha);

    Integer getAlpha();
}
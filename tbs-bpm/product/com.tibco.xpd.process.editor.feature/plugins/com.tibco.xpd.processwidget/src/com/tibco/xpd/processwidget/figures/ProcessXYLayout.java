/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.figures;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author wzurek
 */
public class ProcessXYLayout extends XYLayout {

    protected Dimension calculatePreferredSize(IFigure f, int wHint, int hHint) {
        Dimension result = super.calculatePreferredSize(f, wHint, hHint);
        return result;
    }

    public void layout(IFigure parent) {
        Iterator children = parent.getChildren().iterator();
        Point offset = getOrigin(parent).getCopy();//.translate(-minx, -miny);

        IFigure f;
        while (children.hasNext()) {
            f = (IFigure) children.next();
            Rectangle bounds = (Rectangle) getConstraint(f);
            if (bounds == null)
                continue;

            if (bounds.width == -1 || bounds.height == -1) {
                Dimension prefSize = f.getPreferredSize(bounds.width,
                        bounds.height).getCopy();
                bounds = bounds.getCopy();
                if (bounds.width == -1)
                    bounds.width = prefSize.width;
                if (bounds.height == -1)
                    bounds.height = prefSize.height;
            }
            bounds = bounds.getTranslated(offset);
            f.setBounds(bounds);
        }
    }
}

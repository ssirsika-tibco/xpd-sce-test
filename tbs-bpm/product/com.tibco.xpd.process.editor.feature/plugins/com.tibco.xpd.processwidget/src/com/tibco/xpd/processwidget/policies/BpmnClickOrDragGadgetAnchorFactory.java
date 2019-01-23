/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;

import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.anchors.ChopShapeAnchor;
import com.tibco.xpd.processwidget.figures.anchors.ChopShapeAnchor.ShapeAnchorLinesProvider;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.BaseClickOrDragGadgetAnchorFactory;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class BpmnClickOrDragGadgetAnchorFactory extends
        BaseClickOrDragGadgetAnchorFactory {

    public static final BpmnClickOrDragGadgetAnchorFactory INSTANCE = new BpmnClickOrDragGadgetAnchorFactory();
    
    @Override
    public ConnectionAnchor getAnchor(IFigure owner) {
        if (owner instanceof ShapeWithDescriptionFigure) {
            ShapeWithDescriptionFigure fig = (ShapeWithDescriptionFigure)owner;
            
            IFigure shape = fig.getShape();
            if (shape instanceof Ellipse) {
                return new EllipseAnchor(shape);
            }
            
            if (shape instanceof ShapeAnchorLinesProvider) {
                return new ChopShapeAnchor(shape);
            }
            
        }
        return super.getAnchor(owner);
    }
}

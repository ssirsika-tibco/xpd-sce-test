/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;

/**
 * Generic gadget info (always points down and up from host edit part)
 * @author aallway
 * @since 
 */
public class GenericClickOrDragGadgetInfo extends AbstractClickOrDragGadgetInfo {

    public GenericClickOrDragGadgetInfo(String clickOrDragRequestType, String description,
            ConnectionAnchor srcAnchor) {
        super(clickOrDragRequestType, description, null);
    }

    public GenericClickOrDragGadgetInfo(String clickOrDragRequestType, String description,
            Image referenceTypeImage) {
        super(clickOrDragRequestType, description, referenceTypeImage);
    }
    
    public GenericClickOrDragGadgetInfo(String clickOrDragRequestType, String description,
            Image referenceTypeImage, GADGET_SHAPE gadgetShape) {
        super(clickOrDragRequestType, description, referenceTypeImage, gadgetShape);
    }

}

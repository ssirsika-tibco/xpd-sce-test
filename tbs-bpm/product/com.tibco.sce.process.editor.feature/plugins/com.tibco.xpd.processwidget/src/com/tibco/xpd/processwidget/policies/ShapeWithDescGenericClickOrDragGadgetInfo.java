package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.GenericClickOrDragGadgetInfo;

/**
 * generic gadget info that is aware of shapeWithDescriptionFigures(same as
 * standard except knows what areas the shape with description figure occupies
 * and can therefore exclude those areas from possible positions for gadget.
 * 
 * @author aallway
 * @since 3.2
 */
public class ShapeWithDescGenericClickOrDragGadgetInfo extends
        GenericClickOrDragGadgetInfo {
    public ShapeWithDescGenericClickOrDragGadgetInfo(
            String clickOrDragRequestType, String description,
            Image referenceTypeImage, GADGET_SHAPE gadgetShape) {
        super(clickOrDragRequestType, description, referenceTypeImage, gadgetShape);
    }

    public ShapeWithDescGenericClickOrDragGadgetInfo(
            String clickOrDragRequestType, String description,
            Image referenceTypeImage) {
        super(clickOrDragRequestType, description, referenceTypeImage, GADGET_SHAPE.CIRCLE);
    }

    @Override
    public Collection<Rectangle> getGadgetPositionExclusionZones(
            GraphicalEditPart hostEditPart) {
        if (hostEditPart.getFigure() instanceof ShapeWithDescriptionFigure) {
            ShapeWithDescriptionFigure fig =
                    (ShapeWithDescriptionFigure) hostEditPart.getFigure();

            Collection<Rectangle> zones = new ArrayList<Rectangle>();

            Rectangle b = fig.getShape().getBounds().getCopy();
            fig.getShape().translateToAbsolute(b);
            zones.add(b);

            b = fig.getTextFigure().getBounds().getCopy();
            fig.getTextFigure().translateToAbsolute(b);
            zones.add(b);
            return zones;
        }
        return super.getGadgetPositionExclusionZones(hostEditPart);
    }

}
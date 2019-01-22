package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetPolicy;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.CrossReferenceClickOrDragGadgetInfo;

/**
 * Event cross reference gadget info (same as standard except knows what areas
 * the shape with description figure occupies and can therefore exclude those
 * areas from possible positions for gadget.
 * 
 * @author aallway
 * @since 3.2
 */
public class ShapeWithDescCrossReferenceClickOrDragGadgetInfo extends
        CrossReferenceClickOrDragGadgetInfo {
    public ShapeWithDescCrossReferenceClickOrDragGadgetInfo(
            String clickOrDragRequestType,
            GraphicalEditPart referencedEditPart,
            String description,
            Image referenceTypeImage,
            BpmnClickOrDragGadgetAnchorFactory bpmnClickOrDragGadgetAnchorFactory) {
        super(clickOrDragRequestType, referencedEditPart, description,
                referenceTypeImage, bpmnClickOrDragGadgetAnchorFactory);
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
            fig.getShape().translateToAbsolute(b);
            zones.add(b);

            return zones;
        }
        return super.getGadgetPositionExclusionZones(hostEditPart);
    }

    @Override
    public Point getHandleDirectionAbsoluteReferencePoint() {
        if (AbstractClickOrDragGadgetPolicy.NEW_GADGET_LAYOUT) {
            return null;
        } else {
            if (getReferencedEditPart() != null) {
                IFigure targetFigure = getReferencedEditPart().getFigure();
                if (targetFigure instanceof ShapeWithDescriptionFigure) {
                    // get The target referenced figure centre (In the same
                    // coords system as anchor works in (which is absolute
                    // coords)
                    Point targetCentre =
                            ((ShapeWithDescriptionFigure) targetFigure)
                                    .getShape().getBounds().getCenter();
                    targetFigure.translateToAbsolute(targetCentre);

                    return targetCentre;
                }
            }
        }

        return super.getHandleDirectionAbsoluteReferencePoint();
    }

}
/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class CrossReferenceClickOrDragGadgetInfo extends
        AbstractClickOrDragGadgetInfo {

    private GraphicalEditPart referencedEditPart;

    private Polyline referenceLine = null;

    private BaseClickOrDragGadgetAnchorFactory anchorFactory;

    public CrossReferenceClickOrDragGadgetInfo(String clickOrDragRequestType,
            GraphicalEditPart referencedEditPart, String description,
            Image referenceTypeImage,
            BaseClickOrDragGadgetAnchorFactory anchorFactory) {
        super(clickOrDragRequestType, description, referenceTypeImage);

        this.referencedEditPart = referencedEditPart;
        this.anchorFactory = anchorFactory;
    }

    @Override
    public Point getHandleDirectionAbsoluteReferencePoint() {
        if (AbstractClickOrDragGadgetPolicy.NEW_GADGET_LAYOUT) {
            return null;
        } else {
            if (referencedEditPart != null) {
                IFigure targetFigure = referencedEditPart.getFigure();

                // get The target referenced figure centre (In the same coords
                // system as anchor works in (which is absolute coords)
                Point targetCentre = targetFigure.getBounds().getCenter();
                targetFigure.translateToAbsolute(targetCentre);

                return targetCentre;
            }

            return new Point(0, 0);
        }
    }

    /**
     * @return the referencedEditPart
     */
    public GraphicalEditPart getReferencedEditPart() {
        return referencedEditPart;
    }

    @Override
    public void showGadgetFeedback(ClickOrDragGadgetRequest request,
            IFigure feedbackLayer, IFigure gadget) {
        super.showGadgetFeedback(request, feedbackLayer, gadget);

        if (ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_MOUSEENTER
                .equals(request.getType())) {
            if (referencedEditPart != null) {
                if (referenceLine == null) {
                    referenceLine = new Polyline() {
                        @Override
                        public void paintFigure(Graphics graphics) {
                            int oldA = graphics.getAlpha();
                            int oldAA = graphics.getAntialias();
                            graphics.setAlpha(175);
                            graphics.setAntialias(SWT.ON);
                            super.paintFigure(graphics);
                            graphics.setAntialias(oldAA);
                            graphics.setAlpha(oldA);
                        }
                    };
                    referenceLine.setForegroundColor(ColorConstants.lightGray);
                    referenceLine.setLineStyle(Graphics.LINE_DASH);
                    referenceLine.setLineWidth(2);
                    feedbackLayer.add(referenceLine);
                }

                Point gadgetCentre = gadget.getBounds().getCenter();
                gadget.translateToAbsolute(gadgetCentre);

                Point refCentre =
                        referencedEditPart.getFigure().getBounds().getCenter();
                referencedEditPart.getFigure().translateToAbsolute(refCentre);

                ConnectionAnchor refAnchor =
                        anchorFactory.getAnchor(referencedEditPart.getFigure());
                Point endLine = refAnchor.getLocation(gadgetCentre);

                ConnectionAnchor gadgetAnchor = anchorFactory.getAnchor(gadget);
                Point startLine = gadgetAnchor.getLocation(endLine);

                feedbackLayer.translateToRelative(startLine);
                feedbackLayer.translateToRelative(endLine);

                referenceLine.setStart(startLine);
                referenceLine.setEnd(endLine);
            }

        } else if (ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_DRAGGED
                .equals(request.getType())) {
            // On start dragging remove our reference line feedback.
            if (referenceLine != null) {
                if (referenceLine != null) {
                    feedbackLayer.remove(referenceLine);
                }

                referenceLine = null;
            }
        }

        return;
    }

    @Override
    public void eraseGadgetFeedback(ClickOrDragGadgetRequest request,
            IFigure feedbackLayer, IFigure gadget) {
        super.eraseGadgetFeedback(request, feedbackLayer, gadget);

        if (ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_MOUSEENTER
                .equals(request.getType())) {
            if (referenceLine != null) {
                feedbackLayer.remove(referenceLine);
            }

            referenceLine = null;
        }

        return;
    }
}

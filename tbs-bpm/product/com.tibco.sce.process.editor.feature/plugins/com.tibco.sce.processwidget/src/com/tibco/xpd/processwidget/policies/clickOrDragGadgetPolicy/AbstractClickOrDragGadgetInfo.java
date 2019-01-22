/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import java.util.Collection;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Image;

/**
 * Information for each click or drag gadget.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractClickOrDragGadgetInfo {

    public static enum GADGET_SHAPE {
        CIRCLE, SQUARE
    }

    private Image referenceTypeImage = null;

    private GADGET_SHAPE gadgetShape = GADGET_SHAPE.CIRCLE;

    /**
     * @return the referenceTypeImage
     */
    public Image getReferenceTypeImage() {
        return referenceTypeImage;
    }

    /**
     * @param referenceTypeImage
     *            the referenceTypeImage to set
     */
    public void setReferenceTypeImage(Image referenceTypeImage) {
        this.referenceTypeImage = referenceTypeImage;
    }

    private String description;

    private String clickOrDragRequestType;

    public AbstractClickOrDragGadgetInfo(String clickOrDragRequestType,
            String description) {
        this(clickOrDragRequestType, description, null, GADGET_SHAPE.CIRCLE);
    }

    public AbstractClickOrDragGadgetInfo(String clickOrDragRequestType,
            String description, Image referenceTypeImage) {
        this(clickOrDragRequestType, description, referenceTypeImage,
                GADGET_SHAPE.CIRCLE);
    }

    public AbstractClickOrDragGadgetInfo(String clickOrDragRequestType,
            String description, Image referenceTypeImage,
            GADGET_SHAPE gadgetShape) {
        super();
        this.referenceTypeImage = referenceTypeImage;
        this.description = description;
        this.clickOrDragRequestType = clickOrDragRequestType;
        this.gadgetShape = gadgetShape;
    }

    /**
     * The click or drag type is passed to the gadget info constructor and can
     * be accessed at any time (for instance from the
     * CliockOrDragGadgetRequest.getClickOrDragGadgetInfo()
     * 
     * @return the clickOrDragType
     */
    public String getClickOrDragRequestType() {
        return clickOrDragRequestType;
    }

    /**
     * The clickOrDragGadget feedback 'handle' needs to know what direction to
     * 'point' in.
     * <p>
     * The implementer of this method can provide a reference point outside of
     * the source edit part figure (in absolute coords). The handle will be
     * placed outside of the object in a straight line drawn between the border
     * position and the returned reference point.
     * <p>
     * By default the gadget locator will position down and right of the host
     * edit part's figure.
     * 
     * @return the reference point that handle should 'point' to (in absolute
     *         coords.
     */
    public Point getHandleDirectionAbsoluteReferencePoint() {
        return null;
    }

    /**
     * @return list of rectangles where the gagdet figure cannot be placed (or
     *         null if the host figure should be used) (in ABSOLUTE
     *         coordinates).
     */
    public Collection<Rectangle> getGadgetPositionExclusionZones(
            GraphicalEditPart hostEditPart) {
        return null;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * The subclass can add extra feedback to do with the gadget's operations
     * 
     * @param request
     * @param feedbackLayer
     * @param gadget
     */
    public void showGadgetFeedback(ClickOrDragGadgetRequest request,
            IFigure feedbackLayer, IFigure gadget) {
        return;
    }

    /**
     * The subclass should remove any extra feedback to do with the gadget's
     * operations
     * 
     * @param request
     * @param feedbackLayer
     * @param gadget
     */
    public void eraseGadgetFeedback(ClickOrDragGadgetRequest request,
            IFigure feedbackLayer, IFigure gadget) {
    }

    /**
     * @return true if gadget should appear when there is multiple selection in
     *         edit part viewer
     */
    public boolean enabledForMultiSelect() {
        return false;
    }

    /**
     * @return the gadgetShape
     */
    public GADGET_SHAPE getGadgetShape() {
        return gadgetShape;
    }

    /**
     * @param gadgetShape
     *            the gadgetShape to set
     */
    public void setGadgetShape(GADGET_SHAPE gadgetShape) {
        this.gadgetShape = gadgetShape;
    }

    /**
     * This method gives a specific implementation of click-drag gadget edit
     * policy the opportunity to adjust the location of the request from the
     * original mouse location.
     * <p>
     * When dragging a gadget, this is called AFTER tool's location is set in
     * the request and the target edit part has been ascertained from it and
     * BEFORE the edit policy is asked to create the command and show feed back.
     * <p>
     * Therefore this can be used for performing adjustments such as
     * snap-to-grid and so on. The edit policy that creates and returns rthe
     * gadget info's can subclass this method to put its own twist on the
     * location.
     * 
     * @param creq
     */
    public void adjustLocation(ClickOrDragGadgetRequest creq) {
        return;
    }
}

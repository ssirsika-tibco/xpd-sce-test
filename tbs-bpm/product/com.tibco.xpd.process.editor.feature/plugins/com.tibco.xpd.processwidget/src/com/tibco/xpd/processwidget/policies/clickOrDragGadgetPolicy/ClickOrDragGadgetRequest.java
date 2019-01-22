/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.tools.AbstractTool.Input;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class ClickOrDragGadgetRequest extends Request implements DropRequest {

    public static final String REQ_CLICKORDRAG_GADGET_CLICKED =
            "REQ_COD_GADGET_CLICKED"; //$NON-NLS-1$

    public static final String REQ_CLICKORDRAG_GADGET_DRAGGED =
            "REQ_COD_GADGET_DRAGGED"; //$NON-NLS-1$

    public static final String REQ_CLICKORDRAG_GADGET_HOVER =
            "REQ_COD_GADGET_HOVER"; //$NON-NLS-1$

    public static final String REQ_CLICKORDRAG_GADGET_MOUSEENTER =
            "REQ_COD_GADGET_MOUSE_ENTER"; //$NON-NLS-1$

    private EditPart hostEditPart;

    private AbstractClickOrDragGadgetInfo clickOrDragGadgetInfo;

    private EditPart dragTarget;

    private Point location;

    private GadgetHandleFeedback clickOrDragHandle;

    private boolean noDragCommand = true;

    private Input currentInput;

    private boolean isSnappedToLocation = false;

    public ClickOrDragGadgetRequest() {
        super();
    }

    /**
     * @return the host edit part
     */
    public EditPart getHostEditPart() {
        return hostEditPart;
    }

    /**
     * @param hostEditPart
     *            the referenceSource to set
     */
    public void setHostEditPart(EditPart hostEditPart) {
        this.hostEditPart = hostEditPart;
    }

    /**
     * @return the clickOrDragGadgetInfo
     */
    public AbstractClickOrDragGadgetInfo getClickOrDragGadgetInfo() {
        return clickOrDragGadgetInfo;
    }

    /**
     * @param clickOrDragGadgetInfo
     *            the clickOrDragGadgetInfo to set
     */
    public void setClickOrDragGadgetInfo(
            AbstractClickOrDragGadgetInfo clickOrDragGadgetInfo) {
        this.clickOrDragGadgetInfo = clickOrDragGadgetInfo;
    }

    /**
     * @return the absolute location
     */
    @Override
    public Point getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * @return the drag target (for REQ_CLICKORDRAG_DRAGGED
     */
    public EditPart getDragTarget() {
        return dragTarget;
    }

    /**
     * @param dragTarget
     *            the drag target to set
     */
    public void setDragTarget(EditPart dragTarget) {
        this.dragTarget = dragTarget;
    }

    /**
     * @return the ClickOrDragGadgetHandle
     */
    public GadgetHandleFeedback getClickOrDragGadgetHandle() {
        return clickOrDragHandle;
    }

    /**
     * @param clickOrDragHandle
     *            the ClickOrDragGadgetHandle to set
     */
    void setClickOrDragGadgetHandle(GadgetHandleFeedback clickOrDragHandle) {
        this.clickOrDragHandle = clickOrDragHandle;
    }

    /**
     * @return the noDragCommand
     */
    public boolean isNoDragCommand() {
        return noDragCommand;
    }

    /**
     * @param noDragCommand
     *            the noDragCommand to set
     */
    public void setNoDragCommand(boolean noDragCommand) {
        this.noDragCommand = noDragCommand;
    }

    /**
     * @param currentInput
     *            from the drag-tracker tool that created this request.
     */
    public void setTrackerInput(Input currentInput) {
        this.currentInput = currentInput;
    }

    /**
     * @return
     */
    public Input getTrackerInput() {
        return currentInput;
    }

    /**
     * @return <code>true</code> if the request's location has been snapped into
     *         position using a snap helper. the isSnappedToLocation to set
     */
    public boolean isSnappedToLocation() {
        return isSnappedToLocation;
    }

    /**
     * Set during {@link #getSnapLocation(ClickOrDragGadgetRequest, Dimension)}
     * if the {@link ClickOrDragGadgetRequest} is a create anew object and
     * connect to it type request.
     * 
     * @param isSnappedToLocation
     *            <code>true</code> if the request's location has been snapped
     *            into position using a snap helper. the isSnappedToLocation to
     *            set
     */
    public void setIsSnappedToLocation(boolean isSnappedToLocation) {
        this.isSnappedToLocation = isSnappedToLocation;
    }
}

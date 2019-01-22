/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import java.util.Collections;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.AutoexposeHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.tools.SimpleDragTracker;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * Drag tracker for the cross click or drag handle gadget
 * 
 * @author aallway
 * @since 3.2
 */
public class ClickOrDragGadgetTracker extends SimpleDragTracker {

    private EditPart gadgetHostEditPart;

    private AbstractClickOrDragGadgetInfo clickOrDragGadgetInfo;

    private boolean startedDragging = false;

    private GadgetHandleFeedback clickOrDragHandle;

    private AutoexposeHelper exposeHelper;

    /**
     * CLick or drag gadget tracker
     * 
     * @param gadgetHostEditPart
     *            Source edit part of reference.
     * @param referenceTarget
     *            Current target edit part of reference (or <code>null</code> it
     *            no current target).
     * @param clickOrDragHandle
     *            The handle that this drag tra
     */
    ClickOrDragGadgetTracker(EditPart gadgetHostEditPart,
            AbstractClickOrDragGadgetInfo clickOrDragGadgetInfo,
            GadgetHandleFeedback clickOrDragHandle) {
        super();
        this.gadgetHostEditPart = gadgetHostEditPart;
        this.clickOrDragGadgetInfo = clickOrDragGadgetInfo;
        this.clickOrDragHandle = clickOrDragHandle;

        setDisabledCursor(Cursors.NO);
    }

    @Override
    public void deactivate() {
        setAutoexposeHelper(null);
        super.deactivate();
        return;
    }

    /**
     * Called to perform an iteration of the autoexpose process. If the expose
     * helper is set, it will be asked to step at the current mouse location. If
     * it returns true, another expose iteration will be queued. There is no
     * delay between autoexpose events, other than the time required to perform
     * the step().
     */
    protected void doAutoexpose() {
        if (exposeHelper == null)
            return;
        if (exposeHelper.step(getLocation())) {
            handleAutoexpose();
            Display.getCurrent().asyncExec(new QueuedAutoexpose());
        } else
            setAutoexposeHelper(null);
    }

    /**
     * This method is called whenever an autoexpose occurs. When an autoexpose
     * occurs, it is possible that everything in the viewer has moved a little.
     * Therefore, by default, {@link AbstractTool#handleMove() handleMove()} is
     * called to simulate the mouse moving even though it didn't.
     */
    protected void handleAutoexpose() {
        handleMove();
        updateSourceRequest();
        showSourceFeedback();
        setCurrentCommand(getCommand());
    }

    @Override
    protected boolean handleHover() {
        super.handleHover();

        if (startedDragging) {
            updateAutoexposeHelper();
        }

        return true;
    }

    /**
     * Sets the active autoexpose helper to the given helper, or
     * <code>null</code>. If the helper is not <code>null</code>, a runnable is
     * queued on the event thread that will trigger a subsequent
     * {@link #doAutoexpose()}. The helper is typically updated only on a hover
     * event.
     * 
     * @param helper
     *            the new autoexpose helper or <code>null</code>
     */
    protected void setAutoexposeHelper(AutoexposeHelper helper) {
        exposeHelper = helper;
        if (exposeHelper == null)
            return;
        Display.getCurrent().asyncExec(new QueuedAutoexpose());

        return;
    }

    /**
     * Updates the active {@link AutoexposeHelper}. Does nothing if there is
     * still an active helper. Otherwise, obtains a new helper (possible
     * <code>null</code>) at the current mouse location and calls
     * {@link #setAutoexposeHelper(AutoexposeHelper)}.
     */
    protected void updateAutoexposeHelper() {
        if (exposeHelper != null) {
            return;
        }
        AutoexposeHelper.Search search;
        search = new AutoexposeHelper.Search(getLocation());
        getCurrentViewer().findObjectAtExcluding(getLocation(),
                Collections.EMPTY_LIST,
                search);
        setAutoexposeHelper(search.result);
    }

    @Override
    protected Request createSourceRequest() {
        ClickOrDragGadgetRequest crr = new ClickOrDragGadgetRequest();
        crr.setHostEditPart(gadgetHostEditPart);
        crr.setClickOrDragGadgetInfo(clickOrDragGadgetInfo);
        crr.setClickOrDragGadgetHandle(clickOrDragHandle);

        crr.setTrackerInput(getCurrentInput());

        return crr;
    }

    @Override
    protected String getCommandName() {
        Point mouseDownLocation = getStartLocation();
        Point currentLocation = getLocation();

        // If the cursor hasn't moved much from the
        if (XPDLineUtilities.getLineLength(mouseDownLocation, currentLocation) < 10
                && !startedDragging) {
            return ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_CLICKED;
        }
        // If we've ever moved far enough away from start location then allow
        // user to drag onto another edit part.
        else {
            startedDragging = true;

            return ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_DRAGGED;
        }
    }

    @Override
    protected boolean handleButtonDown(int button) {
        if (button == 1) {
            startedDragging = false;
            super.handleButtonDown(button);

            // Pretend drag already so that mouse up does something even if user
            // doesn't move.
            setState(STATE_DRAG_IN_PROGRESS);
            handleDragInProgress();

            return true;
        }

        return true;
    }

    @Override
    protected Command getCommand() {
        return gadgetHostEditPart.getCommand(getSourceRequest());
    }

    @Override
    protected void updateSourceRequest() {
        // The super will (amongst possible other things, update the request
        // type via getCommandName().
        super.updateSourceRequest();

        ClickOrDragGadgetRequest req =
                (ClickOrDragGadgetRequest) getSourceRequest();

        req.setLocation(getLocation());

        if (startedDragging) {
            EditPartViewer viewer = getCurrentViewer();
            EditPart target =
                    viewer.findObjectAtExcluding(getLocation(), Collections
                            .singletonList(clickOrDragHandle.getGadgetHandle()));
            req.setDragTarget(target);

            /*
             * Give the owner of the gadget info a chance to adjust the location
             * for things like snap to grid.
             */
            clickOrDragGadgetInfo.adjustLocation(req);

        }

        return;
    }

    private class QueuedAutoexpose implements Runnable {
        @Override
        public void run() {
            if (exposeHelper != null)
                doAutoexpose();
        }
    }

}

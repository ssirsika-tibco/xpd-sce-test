/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.figures.IHighlightableFigure;
import com.tibco.xpd.processwidget.neatstuff.FigureFadeUpMouseListener;
import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;

/**
 * Edit policy for clickOrDrag gadgets.
 * <p>
 * A click or drag gadget is a small widget that appears alongside the host edit
 * part when it is selected. The click or drag gadget will allow the sub-class
 * to provide a command when the user:
 * <li>Clicks on the gadget</li>
 * <li>Drags the gadget elsewhere.</li> The gadget is faded down until the user
 * moves the mouse over it.
 * <p>
 * Each instance of a policy asks the subclass for a list of gadget infos. If
 * there are multiple infos then only ONE gadget is shown (in order to remove
 * unnecessary clutter). When the user moves the mouse over the ONE gadget then
 * the rest of the gadgets in the list are made visible (until a short period
 * after the mosue exits any one of the gadgets).
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractClickOrDragGadgetPolicy extends
        SelectionHandlesEditPolicy implements CommandStackListener {

    public static final boolean NEW_GADGET_LAYOUT = true;

    private static final int UNSELECTED_GADGET_ALPHA = 80;

    private static final int SECONDARY_GADGETS_HIDE_TIMEOUT = 1500;

    private List<GadgetHandleFeedback> gadgetFigureHandles =
            Collections.emptyList();

    private EditingDomain editingDomain;

    private FigureFadeUpMouseListener mouseOverFadeUp = null;

    private TimedExposeSecondaryGadgetsMouseMotionListener fadeUpSecondaryGadgetsListener;

    private BaseClickOrDragGadgetAnchorFactory anchorFactory;

    private IHighlightableFigure previousHighlightedTargetFigure = null;

    private EditPolicyEnablementProvider policyEnablementProvider;

    private String enablementPolicyId;

    public AbstractClickOrDragGadgetPolicy(EditingDomain editingDomain,
            BaseClickOrDragGadgetAnchorFactory anchorFactory,
            EditPolicyEnablementProvider policyEnabledmentProvider,
            String enablementPolicyId) {
        super();
        this.editingDomain = editingDomain;
        this.anchorFactory = anchorFactory;
        this.policyEnablementProvider = policyEnabledmentProvider;
        this.enablementPolicyId = enablementPolicyId;
    }

    @Override
    public void activate() {
        super.activate();

        editingDomain.getCommandStack().addCommandStackListener(this);
    }

    @Override
    public void deactivate() {
        editingDomain.getCommandStack().removeCommandStackListener(this);
        disposeGadgetHandles();
        super.deactivate();
    }

    public void commandStackChanged(EventObject event) {
        // After command is executed remove and recreate handles (unless the
        // host has been deselected)
        if (getHost().getSelected() != EditPart.SELECTED_NONE) {
            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    if (getHost().getSelected() != EditPart.SELECTED_NONE) {
                        removeSelectionHandles();
                        addSelectionHandles();
                    }
                }
            });
        }

        return;
    }

    /**
     * This is called each time the host edit part is selected.
     * 
     * @return Return the list of referenced objects (in sets called
     *         GotoReferenceGroup) each group will be faded up/down together
     */
    protected abstract List<AbstractClickOrDragGadgetInfo> getClickOrDragGadgetInfos();

    /**
     * Return the command to action a user clicking on the gadget.
     * <p>
     * Nominally, the subclass should check that the clickOrDragInfoType is
     * something it deals with - this is the value that was set for the
     * {@link AbstractClickOrDragGadgetInfo} clickDragType and only return a
     * command for the correct type.
     * 
     * @param clickOrDragInfoType
     * @param creq
     * @return Command to action the click, Unexecutable command if the subclass
     *         normally deals with this clickOrDragType but cannot currently,
     *         null if the subclass doesn't deal with the given clir or drag
     *         type.
     */
    protected abstract Command createGadgetClickedCommand(
            String clickOrDragInfoType, ClickOrDragGadgetRequest creq);

    /**
     * Return the command to action a user dragging the gadget.
     * <p>
     * Nominally, the subclass should check that the clickOrDragInfoType is
     * something it deals with - this is the value that was set for the
     * {@link AbstractClickOrDragGadgetInfo} clickDragType and only return a
     * command for the correct type.
     * 
     * @param clickOrDragInfoType
     * @param creq
     * @return Command to action the the drag if the user drops the gadget at
     *         its current location (see
     *         {@link ClickOrDragGadgetRequest#getDragTarget()}, Unexecutable
     *         command if the subclass normally deals with this clickOrDragType
     *         but cannot currently, null if the subclass doesn't deal with the
     *         given clir or drag type.
     */
    protected abstract Command createGadgetDraggedCommand(
            String clickOrDragInfoType, ClickOrDragGadgetRequest creq);

    /**
     * @param clickOrDragInfoType
     * @param creq
     * @return true if the given gadget can EVER be dragged false if it can
     *         NEVER be dragged
     */
    protected abstract boolean canDrag(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq);

    @Override
    protected List createSelectionHandles() {
        List<AbstractHandle> handles = Collections.emptyList();

        boolean multiSel = false;

        EditPartViewer viewer = getHost().getViewer();

        if (viewer instanceof BpmnScrollingGraphicalViewer) {
            List<EditPart> sel =
                    ((BpmnScrollingGraphicalViewer) viewer).getPreSelection();
            if (sel.size() > 1) {
                multiSel = true;
            }
        }

        if (policyEnablementProvider == null || enablementPolicyId == null
                || policyEnablementProvider.isPolicyEnabled(enablementPolicyId)) {

            List<AbstractClickOrDragGadgetInfo> nominalGadgets =
                    getClickOrDragGadgetInfos();
            List<AbstractClickOrDragGadgetInfo> gadgets =
                    new ArrayList<AbstractClickOrDragGadgetInfo>();

            if (multiSel) {
                for (AbstractClickOrDragGadgetInfo gadgetInfo : nominalGadgets) {
                    if (gadgetInfo.enabledForMultiSelect()) {
                        gadgets.add(gadgetInfo);
                    }
                }
            } else {
                gadgets.addAll(nominalGadgets);
            }

            if (gadgets != null && gadgets.size() > 0) {
                gadgetFigureHandles = createGadgetHandles(gadgets);
            } else {
                gadgetFigureHandles = Collections.emptyList();
            }

            IFigure feedbackLayer = getFeedbackLayer();

            handles = new ArrayList<AbstractHandle>();
            for (GadgetHandleFeedback gadgetFeedback : gadgetFigureHandles) {
                feedbackLayer.add(gadgetFeedback.getGadgetDragLine());
                handles.add(gadgetFeedback.getGadgetHandle());
            }
        }

        return handles;
    }

    @Override
    protected void removeSelectionHandles() {
        disposeGadgetHandles();

        super.removeSelectionHandles();
    }

    /**
     * Dispose the gadget handles.
     */
    private void disposeGadgetHandles() {
        if (!gadgetFigureHandles.isEmpty()) {
            IFigure feedbackLayer = getFeedbackLayer();

            boolean first = true;

            for (GadgetHandleFeedback gadgetFeedback : gadgetFigureHandles) {
                ClickOrDragGadgetHandle handle =
                        gadgetFeedback.getGadgetHandle();

                if (first && fadeUpSecondaryGadgetsListener != null) {
                    handle
                            .removeMouseMotionListener(fadeUpSecondaryGadgetsListener);
                    fadeUpSecondaryGadgetsListener.dispose();
                }

                if (mouseOverFadeUp != null) {
                    handle.removeMouseMotionListener(mouseOverFadeUp);
                }

                feedbackLayer.remove(gadgetFeedback.getGadgetDragLine());

                handle.dispose();
            }
        }

        gadgetFigureHandles = Collections.emptyList();
    }

    @Override
    public Command getCommand(Request request) {

        if (request instanceof ClickOrDragGadgetRequest) {
            ClickOrDragGadgetRequest creq = (ClickOrDragGadgetRequest) request;

            if (ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_CLICKED
                    .equals(request.getType())) {
                return createGadgetClickedCommand(creq
                        .getClickOrDragGadgetInfo().getClickOrDragRequestType(),
                        creq);

            } else if (ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_DRAGGED
                    .equals(request.getType())) {
                if (canDrag(creq.getClickOrDragGadgetInfo()
                        .getClickOrDragRequestType(), creq)) {
                    creq.setNoDragCommand(false);

                    if (creq.getDragTarget() != null) {
                        Command c =
                                createGadgetDraggedCommand(creq
                                        .getClickOrDragGadgetInfo()
                                        .getClickOrDragRequestType(), creq);
                        return c;
                    }
                    return UnexecutableCommand.INSTANCE;
                }
            }
        }
        return null;
    }

    @Override
    public void showSourceFeedback(Request request) {
        super.showSourceFeedback(request);

        if (request instanceof ClickOrDragGadgetRequest) {
            ClickOrDragGadgetRequest creq = (ClickOrDragGadgetRequest) request;

            IHighlightableFigure newHighlightTargetFigure = null;

            if (ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_DRAGGED
                    .equals(request.getType())
                    && !creq.isNoDragCommand()) {
                // Move gadget handle to mouse location (set so that it is
                // pointing away from host AND the 'point' of the gadget is on
                // the mouse point.
                Point absMouseLoc = creq.getLocation();
                Point relativeMouseLoc = absMouseLoc.getCopy();

                getFeedbackLayer().translateToRelative(relativeMouseLoc);

                GadgetHandleFeedback gadgetFeedback =
                        creq.getClickOrDragGadgetHandle();

                ClickOrDragGadgetHandle handle =
                        gadgetFeedback.getGadgetHandle();
                handle.setAlpha(125);

                Dimension sz = handle.getSize();

                // handle.setLocation(new Point(relativeMouseLoc.x - sz.width,
                // relativeMouseLoc.y - sz.height));
                handle.setLocation(new Point(relativeMouseLoc.x
                        - (int) (sz.width * 0.8f), relativeMouseLoc.y
                        - (int) (sz.height * 0.8f)));

                Rectangle b = handle.getBounds().getCopy();
                handle.translateToAbsolute(b);

                Point dragLineStart =
                        gadgetFeedback.getHostAnchor().getLocation(b
                                .getCenter());
                Point dragLineEnd =
                        gadgetFeedback.getGadgetAnchor()
                                .getLocation(dragLineStart);
                getFeedbackLayer().translateToRelative(dragLineStart);
                getFeedbackLayer().translateToRelative(dragLineEnd);

                GadgetDragLine dragLine = gadgetFeedback.getGadgetDragLine();
                dragLine.setStart(dragLineStart);
                dragLine.setEnd(dragLineEnd);

                if (creq.getDragTarget() != null
                        && creq.getDragTarget() instanceof GraphicalEditPart
                        && ((GraphicalEditPart) creq.getDragTarget())
                                .getFigure() instanceof IHighlightableFigure) {
                    newHighlightTargetFigure =
                            (IHighlightableFigure) ((GraphicalEditPart) creq
                                    .getDragTarget()).getFigure();
                }

            } else {
                GadgetHandleFeedback gadgetFeedback =
                        creq.getClickOrDragGadgetHandle();

                ClickOrDragGadgetHandle handle =
                        gadgetFeedback.getGadgetHandle();
                handle.setAlpha(UNSELECTED_GADGET_ALPHA);

            }

            // Allow the gadget info to contribute any other feedback.
            creq.getClickOrDragGadgetInfo().showGadgetFeedback(creq,
                    getFeedbackLayer(),
                    creq.getClickOrDragGadgetHandle().getGadgetHandle());

            if (newHighlightTargetFigure != previousHighlightedTargetFigure) {
                if (previousHighlightedTargetFigure != null) {
                    previousHighlightedTargetFigure.setHighlight(false);
                }

                if (newHighlightTargetFigure != null) {
                    newHighlightTargetFigure.setHighlight(true);
                }
                previousHighlightedTargetFigure = newHighlightTargetFigure;
            }
        }
    }

    @Override
    public void eraseSourceFeedback(Request request) {
        super.eraseSourceFeedback(request);

        if (request instanceof ClickOrDragGadgetRequest) {
            ClickOrDragGadgetRequest creq = (ClickOrDragGadgetRequest) request;

            if (ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_DRAGGED
                    .equals(request.getType())) {
                // Put the handle back where it should be.
                ClickOrDragGadgetHandle gotoCrossReferenceHandle =
                        creq.getClickOrDragGadgetHandle().getGadgetHandle();
                gotoCrossReferenceHandle.getLocator()
                        .relocate(gotoCrossReferenceHandle);
                gotoCrossReferenceHandle.setAlpha(UNSELECTED_GADGET_ALPHA);
            }

            // Allow the gadget info to contribute any other feedback.
            creq.getClickOrDragGadgetInfo().eraseGadgetFeedback(creq,
                    getFeedbackLayer(),
                    creq.getClickOrDragGadgetHandle().getGadgetHandle());

            if (previousHighlightedTargetFigure != null) {
                previousHighlightedTargetFigure.setHighlight(false);
                previousHighlightedTargetFigure = null;
            }
        }
    }

    protected List<GadgetHandleFeedback> createGadgetHandles(
            List<AbstractClickOrDragGadgetInfo> gadgetInfos) {

        List<GadgetHandleFeedback> gadgetHandles =
                new ArrayList<GadgetHandleFeedback>();

        IFigure hostFigure = getHostFigure();
        ClickOrDragGadgetHandle firstHandle = null;

        for (AbstractClickOrDragGadgetInfo gadgetInfo : gadgetInfos) {
            GadgetHandleFeedback gadgetFeedback =
                    new GadgetHandleFeedback((GraphicalEditPart) getHost(),
                            gadgetInfo, anchorFactory);

            ClickOrDragGadgetHandle handleFig =
                    gadgetFeedback.getGadgetHandle();

            if (firstHandle == null) {
                firstHandle = handleFig;
            }

            handleFig.setMainGadgetInGroup(firstHandle);

            Image img = gadgetInfo.getReferenceTypeImage();
            if (img != null) {
                handleFig
                        .setToolTip(new Label(gadgetInfo.getDescription(), img));
            } else {
                handleFig.setToolTip(new Label(gadgetInfo.getDescription()));
            }

            gadgetHandles.add(gadgetFeedback);

        }

        if (gadgetHandles.size() > 0) {

            addFadeUpController(gadgetHandles);
        }

        return gadgetHandles;
    }

    /**
     * Create the fadeup controller for the collection of handles.
     */
    private void addFadeUpController(List<GadgetHandleFeedback> gadgetHandles) {

        // Initial alpha is set to zero so that by default, handles are
        // invisible. Then for the 1st handle we will set a minimum alpha so
        // that it never quite disappears.
        int initialAlpha = 0;
        int fadeUpTime = 125;
        int fadeDownTime = 250;
        int maxAlpha = 200;

        ClickOrDragGadgetHandle firstHandle = null;
        boolean first = true;

        List<IFadeableFigure> fadeUpFigs = new ArrayList<IFadeableFigure>();

        for (GadgetHandleFeedback gadgetFeedback : gadgetHandles) {
            if (first) {
                first = false;
                firstHandle = gadgetFeedback.getGadgetHandle();
                // Set up the fade controller such that initially we only show
                // the first handle but give it all the other handles so that
                // they fade up
                // when the first is moused-over
                gadgetFeedback.setAlpha(UNSELECTED_GADGET_ALPHA);
                gadgetFeedback.setMinimumAlpha(UNSELECTED_GADGET_ALPHA);
            } else {
                gadgetFeedback.setMinimumAlpha(0);
                gadgetFeedback.setAlpha(initialAlpha);
                gadgetFeedback.setVisible(false);
            }

            fadeUpFigs.add(gadgetFeedback.getGadgetHandle());
            fadeUpFigs.add(gadgetFeedback.getGadgetDragLine());
        }

        mouseOverFadeUp =
                new FigureFadeUpMouseListener(fadeUpTime, fadeDownTime,
                        initialAlpha, maxAlpha, fadeUpFigs);
        firstHandle.addMouseMotionListener(mouseOverFadeUp);

        if (gadgetHandles.size() > 1 || NEW_GADGET_LAYOUT) {
            fadeUpSecondaryGadgetsListener =
                    new TimedExposeSecondaryGadgetsMouseMotionListener(
                            firstHandle);
            firstHandle.addMouseMotionListener(fadeUpSecondaryGadgetsListener);
        } else {
            fadeUpSecondaryGadgetsListener = null;
        }

        return;
    }

    /**
     * When nmouse enters the first in a group of gadgets add the fade up
     * listener to the other (previously invisible) gadgets.
     * <p>
     * When mouse exits, start a timed job to remove the mouse listeners (giving
     * the user a small window bef0ore the handles become fully invisible again.
     * 
     * @author aallway
     * @since
     */
    private class TimedExposeSecondaryGadgetsMouseMotionListener implements
            MouseMotionListener {
        private boolean active = false;

        private Map<ClickOrDragGadgetHandle, MouseInGadgetRecorder> mouseInGadgetRecorders =
                Collections.emptyMap();

        private long startOfHideTimeoutTime = 0;

        private boolean forceHideSecondaryGadgets = false;

        private ClickOrDragGadgetHandle firstHandle;

        public TimedExposeSecondaryGadgetsMouseMotionListener(
                ClickOrDragGadgetHandle firstHandle) {
            this.firstHandle = firstHandle;
        }

        /**
         * When the mouse enters, expose the secondary gadgets and start a timer
         * that will hide them again a short time after the mouse has exited the
         * gadgets.
         */
        public void mouseEntered(MouseEvent me) {
            if (!active) {

                if (NEW_GADGET_LAYOUT) {
                    // Fade out secondary gadgets of other gadget groups
                    // immediately.
                    List siblings = firstHandle.getParent().getChildren();
                    for (Iterator iterator = siblings.iterator(); iterator
                            .hasNext();) {
                        IFigure child = (IFigure) iterator.next();

                        if (child instanceof ClickOrDragGadgetHandle) {
                            ClickOrDragGadgetHandle gadget =
                                    (ClickOrDragGadgetHandle) child;

                            if (!ClickOrDragGadgetHandle
                                    .isMainGadgetInGroup(gadget)) {
                                if (gadget.getMainGadgetInGroup() != firstHandle
                                        .getMainGadgetInGroup()) {
                                    if (gadget.getAlpha() > 0) {
                                        gadget.setMinimumAlpha(0);
                                        gadget.setAlpha(0);
                                        gadget.repaint();
                                    }
                                }
                            }
                        }
                    }
                }

                if (gadgetFigureHandles.size() > 1) {
                    active = true;

                    mouseInGadgetRecorders =
                            new HashMap<ClickOrDragGadgetHandle, MouseInGadgetRecorder>();

                    startOfHideTimeoutTime = System.currentTimeMillis();

                    boolean first = true;
                    for (GadgetHandleFeedback gadgetFeedback : gadgetFigureHandles) {
                        ClickOrDragGadgetHandle handle =
                                gadgetFeedback.getGadgetHandle();

                        if (!first) {
                            handle.addMouseMotionListener(mouseOverFadeUp);
                            gadgetFeedback
                                    .setMinimumAlpha(UNSELECTED_GADGET_ALPHA);

                            MouseInGadgetRecorder rec =
                                    new MouseInGadgetRecorder(false);
                            mouseInGadgetRecorders.put(handle, rec);
                            handle.addMouseMotionListener(rec);
                        } else {
                            first = false;

                            MouseInGadgetRecorder rec =
                                    new MouseInGadgetRecorder(true);
                            mouseInGadgetRecorders.put(handle, rec);
                            handle.addMouseMotionListener(rec);
                        }
                    }

                    startHideTimeoutTimer();
                }
            }
        }

        /**
         * Start the timer that will wait for a short time after the user has
         * exited any one of the gadgets.
         * <p>
         * If the user exits and re-enters the gadget then the timeout is
         * restarted.
         */
        private void startHideTimeoutTimer() {
            final Display d = Display.getCurrent();

            if (d != null && !d.isDisposed()) {
                // Spin round until hide timeout occurs (since last gadget
                // exited.
                d.timerExec(100, new Runnable() {

                    public void run() {
                        // First check if the mouse is in any of the gadgets, if
                        // so, just reset the hide timeout start time
                        if (!forceHideSecondaryGadgets) {
                            boolean mouseInOne = false;
                            for (MouseInGadgetRecorder rec : mouseInGadgetRecorders
                                    .values()) {
                                if (rec.mouseInGadget) {
                                    mouseInOne = true;
                                    break;
                                }
                            }

                            if (mouseInOne) {
                                startOfHideTimeoutTime =
                                        System.currentTimeMillis();
                            }
                        }

                        if (forceHideSecondaryGadgets
                                || (System.currentTimeMillis() - startOfHideTimeoutTime) >= 500) {
                            active = false;

                            if (gadgetFigureHandles.size() > 1
                                    && mouseOverFadeUp != null) {

                                List<IFadeableFigure> otherHandles =
                                        new ArrayList<IFadeableFigure>();

                                boolean first = true;

                                for (GadgetHandleFeedback gadgetFeedback : gadgetFigureHandles) {
                                    if (!first) {
                                        otherHandles.add(gadgetFeedback
                                                .getGadgetHandle());

                                        gadgetFeedback.setMinimumAlpha(0);

                                        gadgetFeedback.getGadgetDragLine()
                                                .setVisible(false);

                                    } else {
                                        first = false;
                                    }

                                    MouseInGadgetRecorder rec =
                                            mouseInGadgetRecorders
                                                    .get(gadgetFeedback
                                                            .getGadgetHandle());
                                    if (rec != null) {
                                        gadgetFeedback.getGadgetHandle()
                                                .removeMouseMotionListener(rec);
                                    }
                                }

                                mouseOverFadeUp
                                        .removeListenerAndFadeDown(otherHandles);
                            }

                        } else if (!d.isDisposed()) {
                            // GO around again until n millisecs after user as
                            // exited any of the gadgets.
                            d.timerExec(100, this);
                        }

                        return;
                    }
                });
            }

        }

        public void dispose() {
            if (active) {
                forceHideSecondaryGadgets = true;

                for (GadgetHandleFeedback gadgetFeedback : gadgetFigureHandles) {
                    MouseInGadgetRecorder rec =
                            mouseInGadgetRecorders.get(gadgetFeedback
                                    .getGadgetHandle());
                    if (rec != null) {
                        gadgetFeedback.getGadgetHandle()
                                .removeMouseMotionListener(rec);
                    }
                }

                mouseInGadgetRecorders = Collections.emptyMap();
            }
        }

        /**
         * Simple mouse motion listener to keep track of whether the the mouse
         * is in or out of a gadget.
         * <p>
         * This is used as a helper to decide when the secondary gadgets can be
         * removed.
         * 
         * @author aallway
         * @since
         */
        private class MouseInGadgetRecorder implements MouseMotionListener {
            boolean mouseInGadget = false;

            public MouseInGadgetRecorder(boolean initState) {
                mouseInGadget = initState;
            }

            public void mouseEntered(MouseEvent me) {
                mouseInGadget = true;
            }

            public void mouseExited(MouseEvent me) {
                mouseInGadget = false;
            }

            public void mouseHover(MouseEvent me) {
            }

            public void mouseMoved(MouseEvent me) {
            }

            public void mouseDragged(MouseEvent me) {
            }
        }

        public void mouseDragged(MouseEvent me) {
        }

        public void mouseHover(MouseEvent me) {
        }

        public void mouseMoved(MouseEvent me) {
        }

        public void mouseExited(MouseEvent me) {
        }

    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.dragdrop;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;

import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.fragments.dnd.FragmentTransferDropTargetListener;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.commands.internal.XPDPasteCommand;
import com.tibco.xpd.processwidget.dragdrop.DropTargetUtil.DropTargetEditPartInfo;
import com.tibco.xpd.processwidget.figures.IHighlightableFigure;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * @author rsomayaj
 * 
 */
public class ProcessFragmentDropTargetListener extends
        FragmentTransferDropTargetListener {

    private ModelAdapterEditPart lastCommandTarget = null;

    private Point lastCommandOffsetToStartFlow = null;

    private ProcessWidgetImpl processWidgetImpl;

    private DropDiagramObjectFeedbackHelper feedbackHelper;

    private GraphicalEditPart lastFeedbackTarget = null;

    private XPDPasteCommand xpdPasteCmd;

    /**
     * @param viewer
     */
    public ProcessFragmentDropTargetListener(ProcessWidgetImpl processwidgetImpl) {
        super(processwidgetImpl.getGraphicalViewer());
        this.processWidgetImpl = processwidgetImpl;
        feedbackHelper = new DropDiagramObjectFeedbackHelper(processWidgetImpl);
    }

    /**
     * @see com.tibco.xpd.fragments.dnd.FragmentTransferDropTargetListener#doDragOver(org.eclipse.swt.dnd.DropTargetEvent,
     *      org.eclipse.gef.EditPart)
     * 
     * @param event
     * @param target
     */
    @Override
    protected void doDragOver(DropTargetEvent event, EditPart target) {
        CompoundCommand cmd = getDropCommand(event);

        if (cmd != null && cmd.canExecute()) {
            event.detail = DND.DROP_COPY;
        } else {
            event.detail = DND.DROP_NONE;
        }

        updateFeedbackFigure(event);

        /***/
    }

    private void updateFeedbackFigure(DropTargetEvent event) {
        IFigure feebackLayer = processWidgetImpl.getFeedbackLayer();

        // Reset the location of the feedback figure.
        org.eclipse.swt.graphics.Point tmpLoc =
                processWidgetImpl.getGraphicalViewer().getControl()
                        .toControl(event.x, event.y);
        Point absLoc = new Point(tmpLoc.x, tmpLoc.y);

        absLoc =
                DropTargetUtil.getAdjustedDropLocation(absLoc,
                        lastFeedbackTarget);

        Point relLoc = absLoc.getCopy();
        feebackLayer.translateToRelative(relLoc);

        if (lastCommandOffsetToStartFlow != null) {
            relLoc.x -= lastCommandOffsetToStartFlow.x;
            relLoc.y -= lastCommandOffsetToStartFlow.y;
        }

        feedbackHelper.updateFeedbackFigures(relLoc);

        if (lastCommandTarget != lastFeedbackTarget) {
            highlightTargetFigure(false);

        }

        if (lastCommandTarget instanceof GraphicalEditPart) {
            lastFeedbackTarget = (GraphicalEditPart) lastCommandTarget;

            highlightTargetFigure(true);
        }

        return;
    }

    /**
     * @see com.tibco.xpd.fragments.dnd.FragmentTransferDropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
     * 
     * @param event
     */
    @Override
    public void dragEnter(DropTargetEvent event) {
        super.dragEnter(event);
        createFeedbackFigure();
    }

    /**
     * @see com.tibco.xpd.fragments.dnd.FragmentTransferDropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
     * 
     * @param event
     */
    @Override
    public void dragLeave(DropTargetEvent event) {
        disposeFeedbackFigure();
    }

    /**
     * @see com.tibco.xpd.fragments.dnd.FragmentTransferDropTargetListener#dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
     * 
     * @param event
     */
    @Override
    public void dragOperationChanged(DropTargetEvent event) {
        super.dragOperationChanged(event);
        CompoundCommand cmd = getDropCommand(event);

        if (cmd != null && cmd.canExecute()) {
            event.detail = DND.DROP_COPY;
        } else {
            event.detail = DND.DROP_NONE;
        }

    }

    /**
     * @see com.tibco.xpd.fragments.dnd.FragmentTransferDropTargetListener#doDrop(org.eclipse.swt.dnd.DropTargetEvent,
     *      java.lang.String, org.eclipse.gef.EditPart)
     * 
     * @param event
     * @param fragmentData
     * @param targetEditPart
     */
    @Override
    protected void doDrop(DropTargetEvent event, String fragmentData,
            EditPart targetEditPart) {
        disposeFeedbackFigure();
        CompoundCommand cmd = getDropCommand(event);

        if (cmd != null && cmd.canExecute()) {
            cmd
                    .setLabel(Messages.ProcessFragmentDropTargetListener_IsertFragment_menu);

            processWidgetImpl.getEditingDomain().getCommandStack().execute(cmd);

            // Select the model objects.
            processWidgetImpl.setSelFromPastedObjects(xpdPasteCmd);

            event.detail = DND.DROP_COPY;
        } else {
            event.detail = DND.DROP_NONE;
        }

    }

    /**
     * Create the command that will be used to drop the objects.
     * 
     * @param event
     */
    private CompoundCommand getDropCommand(DropTargetEvent event) {
        CompoundCommand compCmd =
                new CompoundCommand(
                        Messages.ProcessFragmentDropTargetListener_DroFragCmdDesc_shortDesc);
        lastCommandTarget = null;
        lastCommandOffsetToStartFlow = null;
        // TODO-Find out why the event doesn't have the data to be dropped,
        // needs to be picked from the FragmentLocalSelectionTransfer Object
        // which doesn't correct.

        ISelection selection =
                FragmentLocalSelectionTransfer.getTransfer().getSelection();
        if (selection instanceof IStructuredSelection) {
            Object firstElement =
                    ((IStructuredSelection) selection).getFirstElement();
            if (firstElement instanceof IFragment) {
                IFragment fragment = (IFragment) firstElement;

                Collection<EObject> dndObject =
                        Xpdl2ProcessorUtil.getFragmentDropObjects(fragment
                                .getLocalizedData());

                if (dndObject instanceof Collection) {
                    //
                    // Get the drop target edit part information.
                    DropTargetEditPartInfo dropInfo =
                            DropTargetUtil
                                    .getDropTargetEditPartInfo(processWidgetImpl,
                                            event);

                    if (dropInfo != null) {
                        // Create the command that will paste in the given
                        // objects.
                        ProcessDiagramAdapter adapter =
                                (ProcessDiagramAdapter) processWidgetImpl
                                        .getWidgetAdapter(processWidgetImpl
                                                .getInput());

                        //
                        // Replace unique id's and references to them, to ensure
                        // that
                        // the new objects are uniquely id'd
                        xpdPasteCmd =
                                new XPDPasteCommand(processWidgetImpl
                                        .getEditingDomain(), adapter,
                                        dropInfo.targetEP,
                                        dropInfo.containerRelativeLocation,
                                        dropInfo.targetContainer,
                                        (Collection) dndObject);
                        compCmd.append(xpdPasteCmd);
                        HashMap<String, String> destinationEnvs =
                                Xpdl2ProcessorUtil.getDestinationEnvs(fragment);
                        if (destinationEnvs != null) {
                            Command globalDestinationEnvironmentCommand =
                                    adapter
                                            .getSetGlobalDestinationEnvironmentCommand(processWidgetImpl
                                                    .getEditingDomain(),
                                                    destinationEnvs);
                            if (globalDestinationEnvironmentCommand
                                    .canExecute()) {
                                compCmd
                                        .append(globalDestinationEnvironmentCommand);
                            }

                            //
                            // Save the last command target (useful for when
                            // updating
                            // feedback.
                            lastCommandTarget = dropInfo.targetEP;
                            lastCommandOffsetToStartFlow =
                                    xpdPasteCmd.getPasteDropOffset();
                        }
                    }
                }
            }
        }
        return compCmd;
    }

    @SuppressWarnings("unchecked")
    private void createFeedbackFigure() {
        Collection<Rectangle> feedbackRects = getFeedbackRect();

        feedbackHelper.setupFeedbackFigures(feedbackRects);
    }

    private Collection getFeedbackRect() {
        ISelection selection =
                FragmentLocalSelectionTransfer.getTransfer().getSelection();
        if (selection instanceof IStructuredSelection) {
            Object firstElement =
                    ((IStructuredSelection) selection).getFirstElement();
            if (firstElement instanceof IFragment) {
                IFragment fragment = (IFragment) firstElement;
                Collection<Rectangle> feedbackRectangles =
                        Xpdl2ProcessorUtil.getFeedbackRectangles(fragment);
                return feedbackRectangles;
            }
        }
        return null;
    }

    private void disposeFeedbackFigure() {
        highlightTargetFigure(false);

        feedbackHelper.disposeFeedbackFigures();
    }

    /**
     * 
     */
    private void highlightTargetFigure(boolean highlightOn) {
        if (lastFeedbackTarget != null) {
            if (lastFeedbackTarget.getFigure() instanceof IHighlightableFigure) {
                ((IHighlightableFigure) lastFeedbackTarget.getFigure())
                        .setHighlight(highlightOn);

            } else if (lastFeedbackTarget.getFigure() instanceof SequenceFlowFigure) {
                if (highlightOn) {
                    ((SequenceFlowFigure) lastFeedbackTarget.getFigure())
                            .setLineWidth(2);
                } else {
                    ((SequenceFlowFigure) lastFeedbackTarget.getFigure())
                            .setLineWidth(1);
                }
            }
        }
    }

}
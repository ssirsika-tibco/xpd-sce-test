/**
 * XPDPasteCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.CopyPasteScope;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand.PasteCancelledByUserException;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * <b>XPDPasteCommand</b>
 * <p>
 * Paste the clipboard objects into target command.
 * 
 * Resize the given target lane or embedded sub-process if it is not large
 * enough to fully contain it's contents.
 * </p>
 * 
 */
public class XPDPasteCommand extends AbstractCommand {

    private CompoundCommand cmd = null;

    private boolean pasteCancelledByUser = false;

    private Dimension makeSpaceOffset = null;

    private Collection pasteObjects = null;

    private ProcessDiagramAdapter processAdapter = null;

    private EditingDomain editingDomain;

    private static boolean ALLOW_SPLICE_INTO_SEQFLOW = true;

    private Point pasteDropOffset = null;

    public XPDPasteCommand(EditingDomain editingDomain,
            ProcessDiagramAdapter processAdapter,
            ModelAdapterEditPart targetEditPart, Point location,
            ModelAdapterEditPart targetContainer, // Target container if
            // targetEditPart is
            // sequence flow else MUST
            // be null
            Collection clipboardObjects) {

        this.processAdapter = processAdapter;
        this.editingDomain = editingDomain;

        cmd = new CompoundCommand();
        cmd.setLabel(Messages.XPDPasteCommand_Paste_menu);

        //
        // Check for paste onto sequence flow (i.e. insert objects into
        // sequence flow.
        boolean isSpliceIntoSeqFlow = false;

        if (targetEditPart instanceof SequenceFlowEditPart) {
            SequenceFlowEditPart seqFlow =
                    (SequenceFlowEditPart) targetEditPart;

            if (ALLOW_SPLICE_INTO_SEQFLOW) {

                if (isValidSeqFlowSpliceTarget(seqFlow, targetContainer)) {
                    //
                    // Switch edit parts.
                    isSpliceIntoSeqFlow = true;
                }
            }

            // If target is sequence flow and we didn't manage to set things up.
            // then we can't do anything.
            if (!isSpliceIntoSeqFlow) {
                cmd.append(UnexecutableCommand.INSTANCE);
                return;
            }

        } else {
            if (targetEditPart instanceof ProcessEditPart) {
                /*
                 * Occasionally we get passed ProcessEditPart instead of
                 * LaneEditPart for right-click on lane content (pageflow
                 * processes usually).
                 * 
                 * When this happens, use the first (hidden) lane edit part as
                 * target.
                 */
                List pools = targetEditPart.getChildren();
                if (pools.size() > 0 && pools.get(0) instanceof PoolEditPart) {
                    List lanes = ((EditPart) pools.get(0)).getChildren();
                    if (lanes.size() > 0
                            && lanes.get(0) instanceof LaneEditPart) {
                        targetEditPart = (LaneEditPart) lanes.get(0);
                    }
                }
            }

            // Not a sequence flow so the target edit part is the container.
            targetContainer = targetEditPart;
        }

        ProcessPasteCommand pasteCommand =
                processAdapter.getPasteObjectsCommand(editingDomain,
                        targetContainer.getModel(),
                        location,
                        clipboardObjects);

        //
        // Disallow paste of pools into task library alternate view (which
        // doesn't have pools and lanes.
        int pasteScope = pasteCommand.getPasteScope().getCopyScope();

        boolean validPasteForProcessType =
                isValidPasteScopeForDiagramType(targetEditPart, pasteScope);

        if (!validPasteForProcessType) {
            cmd.append(UnexecutableCommand.INSTANCE);
        } else {
            cmd.append(pasteCommand);

            pasteObjects = pasteCommand.getPasteObjects();

            if (pasteObjects != null && pasteObjects.size() > 0) {
                if (pasteScope == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
                    if (targetContainer instanceof LaneEditPart
                            || targetContainer instanceof TaskEditPart) {

                        Object pasteStartActivity = null;
                        Object pasteEndActivity = null;

                        if (isSpliceIntoSeqFlow) {
                            // Get the start/end of flow activities in paste
                            // set.
                            pasteStartActivity =
                                    pasteCommand.getFlowStartActivity();
                            pasteEndActivity =
                                    pasteCommand.getFlowEndActivity();

                            if (pasteStartActivity != null) {
                                pasteDropOffset =
                                        pasteCommand
                                                .getOffsetForLocation(pasteStartActivity,
                                                        location);

                                pasteCommand
                                        .offsetToLocation(pasteStartActivity,
                                                location);
                            }

                        }

                        if (isSpliceIntoSeqFlow
                                && (pasteStartActivity == null
                                        || pasteEndActivity == null
                                        || isStartEndEvent(pasteStartActivity) || isStartEndEvent(pasteEndActivity))) {
                            // Attempting to splice a paste selection that does
                            // not
                            // have a start / end activity OR the start/end is a
                            // Start/End event - disallow.

                            cmd.append(UnexecutableCommand.INSTANCE);

                        } else {
                            Rectangle occupiedArea =
                                    pasteCommand.getOccupiedArea();

                            if (occupiedArea != null) {
                                MakeSpaceInParentCommand makeSpaceCmd =
                                        new MakeSpaceInParentCommand(
                                                editingDomain,
                                                occupiedArea,
                                                (BaseGraphicalEditPart) targetContainer,
                                                0,
                                                MakeSpaceInParentCommand.DEFAULT_EXTRA_OVERLAY_MARGIN,
                                                pasteObjects);

                                cmd.append(makeSpaceCmd);

                                //
                                // Make space may have needed to shift the place
                                // where the pasted objects go so that they
                                // don't
                                // overlap things above and/or left.
                                // We'll need to do something about that later.
                                makeSpaceOffset =
                                        makeSpaceCmd.getSpaceBoundsOffset();

                            }

                            //
                            // If this is a splice into a sequence flow then do
                            // it.
                            if (isSpliceIntoSeqFlow) {
                                addSpliceSeqFlowCommand((SequenceFlowEditPart) targetEditPart,
                                        pasteCommand,
                                        targetContainer);
                            }
                        }
                    }

                } else {
                    // Copying other than activitys and artifacts can't be done
                    // on
                    // seq flow.
                    if (isSpliceIntoSeqFlow) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    }
                }
            }
        }
    }

    /**
     * @param targetEditPart
     * @param pasteScope
     * @return
     */
    private boolean isValidPasteScopeForDiagramType(
            ModelAdapterEditPart targetEditPart, int pasteScope) {
        boolean validPasteForProcessType = true;

        DiagramViewType viewType = DiagramViewType.PROCESS;

        if (targetEditPart instanceof BaseGraphicalEditPart) {
            viewType =
                    ((BaseGraphicalEditPart) targetEditPart)
                            .getDiagramViewType();
        } else if (targetEditPart instanceof BaseConnectionEditPart) {
            viewType =
                    ((BaseConnectionEditPart) targetEditPart)
                            .getDiagramViewType();
        }

        if (pasteScope == CopyPasteScope.COPY_POOLS) {
            // Cannot paste whole Pool into pageflow or Task library diagrams
            // (they do not 'have' pools (at least, they don't show them
            // anyway).
            if (DiagramViewType.TASK_LIBRARY_ALTERNATE.equals(viewType)
                    || DiagramViewType.NO_POOLS.equals(viewType)) {
                validPasteForProcessType = false;
            }
        } else if (pasteScope == CopyPasteScope.COPY_LANES) {
            // Cannot paste whole Pool into pageflow diagrams
            // (they do not 'have' pools (at least, they don't show them
            // anyway).
            if (DiagramViewType.NO_POOLS.equals(viewType)) {
                validPasteForProcessType = false;
            }
        }
        return validPasteForProcessType;
    }

    /**
     * @param pasteStartActivity
     * @return
     */
    private boolean isStartEndEvent(Object pasteActivity) {
        boolean isStartEndEvent = false;

        AdapterFactory adapterFactory = processAdapter.getAdapterFactory();

        try {
            Object objAdp =
                    adapterFactory.adapt(pasteActivity,
                            ProcessWidgetConstants.ADAPTER_TYPE);

            if (objAdp instanceof EventAdapter) {
                EventAdapter ea = (EventAdapter) objAdp;

                if (EventFlowType.FLOW_START_LITERAL.equals(ea.getFlowType())
                        || EventFlowType.FLOW_END_LITERAL.equals(ea
                                .getFlowType())) {
                    isStartEndEvent = true;
                }
            }
        } catch (IllegalArgumentException e) {

        }

        return isStartEndEvent;
    }

    /**
     * This method returns the paste drop offset that will be applied when
     * positioning objects when the command is executed.
     * <p>
     * For instance, nominally the paste objects are positioned so that top-left
     * of paste object bounding rectangle is placed at the original location
     * given.
     * <p>
     * However, if the target edit part for pase is a sequence flow then the
     * object positions are offset so that the centre of the object considered
     * to be the 'Activity that is at the start of flow represented by the paste
     * objects' is centred on the original location.
     * 
     * 
     * @return the pasteDropOffset
     */
    public Point getPasteDropOffset() {
        if (pasteDropOffset == null) {
            return new Point(0, 0);
        }
        return pasteDropOffset;
    }

    /**
     * @param editingDomain
     * @param targetEditPart
     * @param pasteCommand
     * @param cmd
     * @param targetContainer
     */
    public void addSpliceSeqFlowCommand(SequenceFlowEditPart seqFlow,
            ProcessPasteCommand pasteCommand,
            ModelAdapterEditPart targetContainer) {

        Object pasteStartActivity = pasteCommand.getFlowStartActivity();
        Object pasteEndActivity = pasteCommand.getFlowEndActivity();

        SequenceFlowAdapter seqAdp = seqFlow.getSequenceFlowAdapter();

        if (pasteStartActivity != null && pasteEndActivity != null) {

            // Create a New sequence flow from current source of sequence flow
            // to start in paste buffer.
            BaseProcessAdapter baseAdapter =
                    ((BaseGraphicalEditPart) seqFlow.getSource())
                            .getModelAdapter();
            SequenceFlowNodeAdapter srcAdp =
                    (SequenceFlowNodeAdapter) baseAdapter;

            ProcessWidgetColors colors =
                    ProcessWidgetColors.getInstance(baseAdapter);

            cmd.append(srcAdp.getCreateSequenceFlowCommand(editingDomain,
                    (EObject) pasteStartActivity,
                    seqAdp.getFlowType(),
                    seqAdp.getCondition(),
                    seqAdp.getExistingSetScriptGrammarId(),
                    null,
                    seqAdp.getStartAnchorPosition(),
                    null,
                    seqAdp.getName(),
                    seqAdp.getLabelPosition(),
                    colors.getGraphicalNodeColor(seqAdp,
                            seqFlow.getLineColorIDForPartType()).toString()));

            // retarget existing sequence flow from end of flow in paste
            // buffer.

            cmd.append(seqAdp.getSetSourceCommand(editingDomain,
                    (EObject) pasteEndActivity,
                    null));

            // And unset various bits.
            cmd.append(seqAdp.getSetFlowTypeCommand(editingDomain,
                    SequenceFlowType.UNCONTROLLED_LITERAL));
            cmd.append(seqAdp.getSetNameCommand(editingDomain, null));

            // Remove all bendpoints.
            List bends = seqAdp.getBendpoints();
            if (bends != null && bends.size() > 0) {
                for (int i = 0; i < bends.size(); i++) {
                    cmd.append(seqAdp.getDeleteBendpointCommand(editingDomain,
                            i));
                }
            }

        }
    }

    /**
     * We have to make sure that the container behind click pos is either the
     * same as the container for either the source or target (or any lane in
     * same pool) of sequence flow. Otherwise we can't splice selection into it.
     * 
     * @param seqFlow
     * @param tgtContainer
     * @return
     */
    private boolean isValidSeqFlowSpliceTarget(SequenceFlowEditPart seqFlow,
            ModelAdapterEditPart tgtContainer) {
        boolean ret = false;

        ModelAdapterEditPart srcEP = (ModelAdapterEditPart) seqFlow.getSource();

        if (tgtContainer instanceof TaskEditPart) {
            // Sequence flow cannot cross embedded subproc task boundaries so
            // source of sequence flow MUST be in same embedded subproc.
            //
            // This covers the case where the sequenceflow crosses over an
            // embedded sub-proc when going between objects outside of the
            // embedded sub-proc.
            if (tgtContainer == srcEP.getParent()) {
                ret = true;
            }

        } else if (tgtContainer instanceof LaneEditPart) {
            // Sequence flows cannot cross pool boundaries.
            // Target lane must be in same pool as the parent lane of current
            // source of sequence flow.
            if (srcEP.getParent() instanceof LaneEditPart) {
                if (((LaneEditPart) tgtContainer).getParentPool() == ((LaneEditPart) srcEP
                        .getParent()).getParentPool()) {
                    ret = true;
                }
            }
        }

        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    @Override
    public void execute() {
        try {
            cmd.execute();
        } catch (PasteCancelledByUserException e) {
            pasteCancelledByUser = true;
            /*
             * return as user cancelled the paste by cancelling project
             * references add.
             */
            return;
        }

        if (makeSpaceOffset != null
                && (makeSpaceOffset.width != 0 || makeSpaceOffset.height != 0)) {
            // Make space may have needed to shift the place wheere the
            // pasted objects go so that they don't overlap things above
            // and/or left.
            AdapterFactory adapterFactory = processAdapter.getAdapterFactory();

            for (Iterator iter = pasteObjects.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                try {
                    Object objAdp =
                            adapterFactory.adapt(obj,
                                    ProcessWidgetConstants.ADAPTER_TYPE);

                    if (objAdp instanceof BaseGraphicalNodeAdapter) {
                        BaseGraphicalNodeAdapter nodeAdp =
                                (BaseGraphicalNodeAdapter) objAdp;

                        Point loc = nodeAdp.getLocation().getCopy();

                        loc.x += makeSpaceOffset.width;
                        loc.y += makeSpaceOffset.height;

                        cmd.appendAndExecute(nodeAdp
                                .getSetLocationCommand(editingDomain,
                                        loc,
                                        nodeAdp.getSize()));
                    }

                } catch (IllegalArgumentException e) {
                    // If we try to adapt an object that is in paste buffer that
                    // we don't have widget adapter for (such as activity set
                    // etc) then adapt will throw illegal argument.
                    // So just ignore it.
                }

            }
        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
     */
    @Override
    protected boolean prepare() {
        return cmd.canExecute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    @Override
    public void redo() {
        if (!pasteCancelledByUser) {
            cmd.redo();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#undo()
     */
    @Override
    public void undo() {
        if (!pasteCancelledByUser) {
            cmd.undo();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#getAffectedObjects()
     */
    @Override
    public Collection<?> getAffectedObjects() {
        if (!pasteCancelledByUser) {
            return cmd.getAffectedObjects();
        }
        return Collections.emptyList();
    }

    /**
     * Get the actual model objects that will be pasted into the process.
     * <p>
     * Returned collection is unmodifiable.
     * 
     * @return the pasteObjects
     */
    public Collection<Object> getPasteModelObjects() {
        if (pasteObjects != null && !pasteCancelledByUser) {
            return Collections.unmodifiableCollection(pasteObjects);
        }
        return Collections.unmodifiableCollection(new ArrayList<Object>());
    }

    /**
     * @see org.eclipse.emf.common.command.AbstractCommand#getLabel()
     * 
     * @return
     */
    @Override
    public String getLabel() {

        return Messages.XPDPasteCommand_Paste_menu;
    }

}

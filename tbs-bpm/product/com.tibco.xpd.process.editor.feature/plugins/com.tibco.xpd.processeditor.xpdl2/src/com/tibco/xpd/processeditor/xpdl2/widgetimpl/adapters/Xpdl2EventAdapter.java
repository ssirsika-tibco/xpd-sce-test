/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;

/**
 * This adapter is for events.
 * 
 * @author wzurek
 */
public class Xpdl2EventAdapter extends Xpdl2BaseFlowObjectAdapter
        implements EventAdapter {

    /**
     * 
     */
    private static final String DURATION_DISPLAY_FORMAT = "%1$s %2$s"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String NEW_LINE_CHARACTER = "\n"; //$NON-NLS-1$

    /**
     * DelayedSetLocationCommand Little delegation class - holds off setting of
     * location until execution time so that if event attached to border is
     * changing to not attached it will allow the set location thru.
     */
    private class DelayedSetLocationCommand implements Command {
        CompoundCommand cmd = null;

        Point loc = null;

        Dimension dim = null;

        EditingDomain ed = null;

        /**
         * 
         */
        public DelayedSetLocationCommand(EditingDomain ed, Point loc,
                Dimension dim) {
            this.ed = ed;
            this.loc = loc;
            this.dim = dim;
            cmd = new CompoundCommand();

        }

        /**
         * @return
         * @see org.eclipse.emf.common.command.Command#canExecute()
         */
        @Override
        public boolean canExecute() {
            return true;
        }

        /**
         * @return
         * @see org.eclipse.emf.common.command.Command#canUndo()
         */
        @Override
        public boolean canUndo() {
            return true;
        }

        /**
         * @param command
         * @return
         * @see org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command.Command)
         */
        @Override
        public Command chain(Command command) {
            return cmd.chain(command);
        }

        /**
         * 
         * @see org.eclipse.emf.common.command.Command#dispose()
         */
        @Override
        public void dispose() {
            cmd.dispose();
        }

        /**
         * 
         * @see org.eclipse.emf.common.command.Command#execute()
         */
        @Override
        public void execute() {
            // Don't allow set location on attached events, just keep as 0,0
            if (isAttachedToTask()) {
                loc = new Point(0, 0);
            }

            CompoundCommand tmpCmd = new CompoundCommand();

            GraphicalNode act = getGraphicalNode();

            NodeGraphicsInfo gInfo =
                    Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act, ed, tmpCmd);

            tmpCmd.append(SetCommand.create(ed,
                    gInfo,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Coordinates(),
                    Xpdl2Factory.eINSTANCE.createCoordinates(loc.x, loc.y)));

            tmpCmd.append(SetCommand.create(ed,
                    gInfo,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                    new Double(dim.height)));
            tmpCmd.append(SetCommand.create(ed,
                    gInfo,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Width(),
                    new Double(dim.width)));

            tmpCmd.setLabel(Messages.Xpdl2EventAdapter_SetLocation);

            cmd.appendAndExecute(tmpCmd);
        }

        /**
         * @return
         * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
         */
        @Override
        public Collection getAffectedObjects() {
            return cmd.getAffectedObjects();
        }

        /**
         * @return
         * @see org.eclipse.emf.common.command.Command#getDescription()
         */
        @Override
        public String getDescription() {
            return cmd.getDescription();
        }

        /**
         * @return
         * @see org.eclipse.emf.common.command.Command#getLabel()
         */
        @Override
        public String getLabel() {
            return cmd.getLabel();
        }

        /**
         * @return
         * @see org.eclipse.emf.common.command.Command#getResult()
         */
        @Override
        public Collection getResult() {
            return cmd.getResult();
        }

        /**
         * 
         * @see org.eclipse.emf.common.command.Command#redo()
         */
        @Override
        public void redo() {
            cmd.redo();
        }

        /**
         * 
         * @see org.eclipse.emf.common.command.Command#undo()
         */
        @Override
        public void undo() {
            cmd.undo();
        }

    }

    private OclBasedNotifier eventNodeListener;

    private OclBasedNotifier eventTriggerTypeListener = null;

    private EObject curTriggerTypeNode = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.EventAdapter#getBorderAttachmentTask
     * ()
     */
    @Override
    public Object getBorderAttachmentTask() {
        if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(getFlowType())) {
            String taskId = ((IntermediateEvent) getEvent()).getTarget();

            if (taskId != null && taskId.length() != 0) {
                // Task we're attached to must be a sibling of this event.
                FlowContainer container = getActivity().getFlowContainer();

                return container.getActivity(taskId);
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.
     * Xpdl2BaseFlowAdapter
     * #getDeleteCommand(org.eclipse.emf.edit.domain.EditingDomain)
     */
    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        CompoundCommand cmd = new CompoundCommand();

        cmd.setLabel(Messages.Xpdl2EventAdapter_DeleteEvent);

        Command delCmd = super.getDeleteCommand(editingDomain);

        if (delCmd == null) {
            return UnexecutableCommand.INSTANCE;
        }

        cmd.append(delCmd);
        return cmd;
    }

    @Override
    public String getErrorCode() {
        ResultError err = EventObjectUtil.getResultError(getActivity());
        return err == null ? null : err.getErrorCode();
    }

    @Override
    public EventTriggerType getEventTriggerType() {
        return EventObjectUtil.getEventTriggerType(getActivity());
    }

    @Override
    public EventFlowType getFlowType() {
        return (EventObjectUtil.getFlowType(getActivity()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#getLinkEventId()
     */
    @Override
    public String getLinkEventId() {
        return EventObjectUtil.getLinkEventId(getActivity());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#
     * getSetEventLinkIdCommand (org.eclipse.emf.edit.domain.EditingDomain,
     * java.lang.String)
     */
    @Override
    public Command getSetEventLinkIdCommand(EditingDomain ed,
            String linkEventId, String processRef) {
        return EventObjectUtil.getSetEventLinkIdCommand(ed,
                getActivity(),
                linkEventId,
                processRef);
    }

    @Override
    public Command getSetEventTriggerTypeCommand(EditingDomain ed,
            EventTriggerType eventType) {
        return EventObjectUtil
                .getSetEventTriggerTypeCommand(ed, getActivity(), eventType);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.
     * Xpdl2BaseGraphicalNodeAdapter
     * #getSetLocationCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.draw2d.geometry.Point, org.eclipse.draw2d.geometry.Dimension)
     */
    @Override
    public Command getSetLocationCommand(EditingDomain ed, Point loc,
            Dimension dim) {
        return new DelayedSetLocationCommand(ed, loc, dim);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.EventAdapter#
     * getSetTaskBorderAttachmentCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, java.lang.String,
     * java.lang.Double)
     */
    @Override
    public Command getSetTaskBorderAttachmentCommand(EditingDomain ed,
            String taskId, Double positionOnBorder) {
        CompoundCommand cmd = new CompoundCommand();

        if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(getFlowType())) {
            if (taskId != null) {
                cmd.setLabel(Messages.Xpdl2EventAdapter_AttachEventToBorder);

                // If we are already attached to the border of task we may have
                // also changed parent (we have to be sibling of task we're
                // attached to.
                Package pkg = Xpdl2ModelUtil.getPackage(getActivity());
                Object newTask = pkg.findNamedElement(taskId);
                if (!(newTask instanceof Activity)) {
                    return UnexecutableCommand.INSTANCE;
                }

                FlowContainer currContainer = getActivity().getFlowContainer();

                FlowContainer newContainer =
                        ((Activity) newTask).getFlowContainer();

                if (currContainer != newContainer) {
                    // Switch containers.
                    cmd.append(RemoveCommand.create(ed,
                            currContainer,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Activities(),
                            getActivity()));

                    cmd.append(AddCommand.create(ed,
                            newContainer,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Activities(),
                            getActivity()));
                }

                // Add the position on border node graphics info.
                NodeGraphicsInfo gi = Xpdl2ModelUtil
                        .getOrCreateNodeGraphicsInfo(getActivity(),
                                ed,
                                cmd,
                                Xpdl2ModelUtil.BORDER_EVENTPOS_IDSUFFIX);

                // Set percent position aroundtask border as X co-ordinate
                Coordinates coords = gi.getCoordinates();
                coords = Xpdl2Factory.eINSTANCE
                        .createCoordinates(positionOnBorder.doubleValue(), 0.0);
                cmd.append(SetCommand.create(ed,
                        gi,
                        Xpdl2Package.eINSTANCE
                                .getNodeGraphicsInfo_Coordinates(),
                        coords));

                cmd.append(SetCommand.create(ed,
                        getEvent(),
                        Xpdl2Package.eINSTANCE.getIntermediateEvent_Target(),
                        taskId));

                // Unset the normal coordinates to 0,0
                gi = Xpdl2ModelUtil
                        .getOrCreateNodeGraphicsInfo(getActivity(), ed, cmd);
                coords = gi.getCoordinates();
                if (coords != null) {
                    coords = Xpdl2Factory.eINSTANCE.createCoordinates(0.0, 0.0);
                    cmd.append(SetCommand.create(ed,
                            gi,
                            Xpdl2Package.eINSTANCE
                                    .getNodeGraphicsInfo_Coordinates(),
                            coords));
                }

                NodeGraphicsInfo taskGi =
                        Xpdl2ModelUtil.getNodeGraphicsInfo((Activity) newTask);
                if (taskGi != null) {
                    // Set the lane id to that of the task we're attaching to.
                    cmd.append(SetCommand.create(ed,
                            gi,
                            Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                            taskGi.getLaneId()));
                }

            } else {
                cmd.setLabel(Messages.Xpdl2EventAdapter_DetachEventFromBorder);

                // Remove the position on border node graphics info.
                NodeGraphicsInfo gi = Xpdl2ModelUtil
                        .getOrCreateNodeGraphicsInfo(getActivity(),
                                ed,
                                cmd,
                                Xpdl2ModelUtil.BORDER_EVENTPOS_IDSUFFIX);

                cmd.append(RemoveCommand.create(ed, gi));

                cmd.append(SetCommand.create(ed,
                        getEvent(),
                        Xpdl2Package.eINSTANCE.getIntermediateEvent_Target(),
                        null));
            }
        } else {
            return UnexecutableCommand.INSTANCE;
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#isEventContainedDirectlyUnderProcess()
     * 
     * @return
     */
    @Override
    public boolean isEventContainedDirectlyUnderProcess() {

        Activity activity = getActivity();

        if (activity != null) {

            if (activity.getFlowContainer() instanceof Process) {
                return true;
            }

        }

        return false;
    }

    /**
     * Return a link event model objects that have this event as given event as
     * a target
     * 
     * @param task
     * @return
     */
    @Override
    public Collection<EObject> getSourceLinkEvents() {
        List<EObject> srcLinkEvents = new ArrayList<EObject>();

        Activity evAct = getActivity();

        if (evAct != null) {
            String thisId = evAct.getId();

            // Get a list of all other link events in the process (including
            // embedded subproc.
            Collection<Activity> lnkEvents = EventObjectUtil
                    .getProcessLinkEvents(Xpdl2ModelUtil.getProcess(evAct));

            // And look for any that have our event id as their link target id.
            for (Iterator iterator = lnkEvents.iterator(); iterator
                    .hasNext();) {
                Activity act = (Activity) iterator.next();

                String tgtId = ((TriggerResultLink) (act.getEvent()
                        .getEventTriggerTypeNode())).getName();
                if (thisId.equals(tgtId)) {
                    srcLinkEvents.add(act);
                }
            }
        }

        return srcLinkEvents;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.EventAdapter#
     * getTaskBorderAttachmentPosition()
     */
    @Override
    public Double getTaskBorderAttachmentPosition() {
        NodeGraphicsInfo gi = Xpdl2ModelUtil.getNodeGraphicsInfo(getActivity(),
                Xpdl2ModelUtil.BORDER_EVENTPOS_IDSUFFIX);

        if (gi != null) {
            Coordinates coords = gi.getCoordinates();

            if (coords != null) {
                return new Double(coords.getXCoordinate());
            }
        }

        return new Double(50.0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#isAttachedToTask()
     */
    @Override
    public boolean isAttachedToTask() {
        return EventObjectUtil.isAttachedToTask(getActivity());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.
     * Xpdl2BaseGraphicalNodeAdapter
     * #setTarget(org.eclipse.emf.common.notify.Notifier)
     */
    @Override
    public void setTarget(Notifier newTarget) {
        super.setTarget(newTarget);

        // Add listener for Event Trigger type changing
        if (target != null) {
            OclQueryListener lstnr = new OclQueryListener() {
                @Override
                public void oclNotifyChanged(EObject base, Object target,
                        Notification n) {

                    // Check for and add listener to trigger type specific child
                    // node.
                    setTriggerTypeListener();

                    /*
                     * Sid XPD-8302 - pass message in so can ignore adapter
                     * removal
                     */
                    fireDiagramNotification(n);
                }
            };

            if (eventNodeListener != null) {
                eventNodeListener.getTarget().eAdapters()
                        .remove(eventNodeListener);
            } else {
                eventNodeListener = new OclBasedNotifier("self.event", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getActivity());
                eventNodeListener.addListener(lstnr);
            }
            getTarget().eAdapters().add(eventNodeListener);

            setTriggerTypeListener();

        }

    }

    private Event getEvent() {
        Activity activity = getActivity();
        if (activity != null) {
            return activity.getEvent();
        }

        return null;
    }

    /**
     * Adds ocl listener for model changes within the event's trigger type
     * specific child node (such as triggerResultLink etc).
     */
    private void setTriggerTypeListener() {
        Event event = getEvent();

        EObject triggerTypeNode = event.getEventTriggerTypeNode();

        // If the trigger type node has changed completely (i.e. change of
        // trigger type)
        // Then remove the existing listener.
        if (triggerTypeNode != curTriggerTypeNode) {

            if (eventTriggerTypeListener != null) {
                eventTriggerTypeListener.getTarget().eAdapters()
                        .remove(eventTriggerTypeListener);
                eventTriggerTypeListener = null;
            }

            curTriggerTypeNode = triggerTypeNode;

            if (curTriggerTypeNode != null) {
                OclQueryListener lstnr = new OclQueryListener() {
                    @Override
                    public void oclNotifyChanged(EObject base, Object target,
                            Notification n) {
                        /*
                         * Sid XPD-8302 - pass message in so can ignore adapter
                         * removal
                         */
                        fireDiagramNotification(n);
                    }
                };

                eventTriggerTypeListener = new OclBasedNotifier(
                        "self.event.getEventTriggerTypeNode()", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getActivity());

                eventTriggerTypeListener.addListener(lstnr);
                getTarget().eAdapters().add(eventTriggerTypeListener);
            }
        }
    }

    @Override
    public String getSignalName() {
        Event ev = getEvent();
        if (ev != null && ev
                .getEventTriggerTypeNode() instanceof TriggerResultSignal) {
            return ((TriggerResultSignal) ev.getEventTriggerTypeNode())
                    .getName();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#getSetInverseCatchThrowOnLinkCommand(org.eclipse.emf.edit.domain.EditingDomain)
     * 
     * @param ed
     * @return
     */
    @Override
    public Command getSetInverseCatchThrowOnLinkCommand(EditingDomain ed) {
        return EventObjectUtil.getSetInverseCatchThrowOnLinkCommand(ed,
                getActivity());
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#getSetSignalNameCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.String)
     * 
     * @param ed
     * @param signalName
     * @return
     */
    @Override
    public Command getSetSignalNameCommand(EditingDomain ed,
            String signalName) {
        return EventObjectUtil
                .getSetSignalNameCommand(ed, getActivity(), signalName);
    }

    @Override
    public Command getSetRequestActivityCommand(EditingDomain ed,
            String requestActivityId) {
        Event event = getEvent();
        if (event != null) {
            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(getEventTriggerType())) {
                if (!EventFlowType.FLOW_START_LITERAL.equals(getFlowType())) {
                    return ReplyActivityUtil
                            .getSetRequestActivityForReplyActivityCommand(ed,
                                    getActivity(),
                                    requestActivityId);
                }

            } else if (EventTriggerType.EVENT_ERROR_LITERAL
                    .equals(getEventTriggerType())) {
                if (EventFlowType.FLOW_END_LITERAL.equals(getFlowType())) {
                    return ThrowErrorEventUtil
                            .getConfigureAsFaultMessageErrorCommand(ed,
                                    getActivity(),
                                    requestActivityId,
                                    null);
                }
            } else if (EventTriggerType.EVENT_NONE_LITERAL
                    .equals(getEventTriggerType())) {
                if (EventFlowType.FLOW_END_LITERAL.equals(getFlowType())) {
                    return SetEndNoneEventReplyCommand
                            .create(ed, getActivity(), requestActivityId);
                }
            }

        }
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Collection<String> getReplyActivityIds() {
        List<String> ids = Collections.emptyList();

        Activity act = getActivity();
        if (act != null) {
            List<Activity> replies = ReplyActivityUtil.getReplyActivities(act);
            if (replies != null && !replies.isEmpty()) {
                ids = new ArrayList<String>();

                for (Activity reply : replies) {
                    ids.add(reply.getId());
                }

            }
        }
        return ids;
    }

    @Override
    public String getRequestActivityId() {
        Event event = getEvent();
        if (event != null) {
            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(getEventTriggerType())) {
                if (!EventFlowType.FLOW_START_LITERAL.equals(getFlowType())) {
                    return ReplyActivityUtil
                            .getRequestActivityIdForReplyActivity(
                                    getActivity());
                }
            } else if (EventTriggerType.EVENT_ERROR_LITERAL
                    .equals(getEventTriggerType())) {
                if (EventFlowType.FLOW_END_LITERAL.equals(getFlowType())) {
                    return ThrowErrorEventUtil
                            .getRequestActivityId(getActivity());
                }
            }
        }

        return null;
    }

    @Override
    public boolean isImplementingEvent() {
        if (getActivity() != null) {
            if (ProcessInterfaceUtil.isEventImplemented(getActivity())) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.EventAdapter#
     * getThrowErrorForRequestActivityIds()
     */
    @Override
    public Collection<String> getThrowErrorIdsForRequestActivity() {
        if (getActivity() != null) {
            List<Activity> throwErrorEvents =
                    ThrowErrorEventUtil.getThrowErrorEvents(getActivity());
            if (!throwErrorEvents.isEmpty()) {
                List<String> ids = new ArrayList<String>();

                for (Activity activity : throwErrorEvents) {
                    ids.add(activity.getId());
                }
                return ids;
            }
        }
        return Collections.emptyList();
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#isAutoGenerated()
     * 
     * @return
     */
    @Override
    public boolean isAutoGenerated() {
        return Xpdl2ModelUtil.isGeneratedRequestActivity(getActivity());
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#getDeadlineCondition()
     * 
     * @return
     */
    @Override
    public String getDeadlineCondition() {

        Deadline deadline = EventObjectUtil.getDeadline(getActivity());

        if (deadline != null) {
            String scriptGrammarId = EventObjectUtil
                    .getExistingSetScriptGrammarId(getActivity());

            if (EventObjectUtil.CONSTANTPERIOD_SCRIPTGRAMMAR
                    .equals(scriptGrammarId)) {
                ConstantPeriod constantPeriod = EventObjectUtil
                        .getTimerEventConstantPeriodScript(getActivity());
                if (constantPeriod != null) {
                    StringBuffer constPeriod = new StringBuffer();
                    // Years
                    if (constantPeriod.getYears() != null
                            && constantPeriod.getYears().longValue() > 0) {
                        constPeriod.append(String.format(
                                DURATION_DISPLAY_FORMAT,
                                constantPeriod.getYears().longValue(),
                                Messages.Xpdl2EventAdapter_Years_label));
                    }
                    // Months
                    if (constantPeriod.getMonths() != null
                            && constantPeriod.getMonths().longValue() > 0) {
                        if (constPeriod.length() > 0) {
                            // New line character
                            constPeriod.append(NEW_LINE_CHARACTER);
                        }
                        constPeriod.append(String.format(
                                DURATION_DISPLAY_FORMAT,
                                constantPeriod.getMonths().longValue(),
                                Messages.Xpdl2EventAdapter_Months_label));

                    }
                    // Weeks
                    if (constantPeriod.getWeeks() != null
                            && constantPeriod.getWeeks().longValue() > 0) {
                        if (constPeriod.length() > 0) {
                            // New line character
                            constPeriod.append(NEW_LINE_CHARACTER);
                        }
                        constPeriod.append(String.format(
                                DURATION_DISPLAY_FORMAT,
                                constantPeriod.getWeeks().longValue(),
                                Messages.Xpdl2EventAdapter_Weeks_label));
                    }
                    // Days
                    if (constantPeriod.getDays() != null
                            && constantPeriod.getDays().longValue() > 0) {
                        if (constPeriod.length() > 0) {
                            // New line character
                            constPeriod.append(NEW_LINE_CHARACTER);
                        }
                        constPeriod
                                .append(String.format(DURATION_DISPLAY_FORMAT,
                                        constantPeriod.getDays().longValue(),
                                        Messages.Xpdl2EventAdapter_Days_label));
                    }
                    // Hours
                    if (constantPeriod.getHours() != null
                            && constantPeriod.getHours().longValue() > 0) {
                        if (constPeriod.length() > 0) {
                            // New line character
                            constPeriod.append(NEW_LINE_CHARACTER);
                        }
                        constPeriod.append(String.format(
                                DURATION_DISPLAY_FORMAT,
                                constantPeriod.getHours().longValue(),
                                Messages.Xpdl2EventAdapter_Hours_label));
                    }
                    // Minutes
                    if (constantPeriod.getMinutes() != null
                            && constantPeriod.getMinutes().longValue() > 0) {
                        if (constPeriod.length() > 0) {
                            // New line character
                            constPeriod.append(NEW_LINE_CHARACTER);
                        }
                        constPeriod.append(String.format(
                                DURATION_DISPLAY_FORMAT,
                                constantPeriod.getMinutes().longValue(),
                                Messages.Xpdl2EventAdapter_Minute_label));
                    }
                    // Seconds
                    if (constantPeriod.getSeconds() != null
                            && constantPeriod.getSeconds().longValue() > 0) {
                        if (constPeriod.length() > 0) {
                            // New line character
                            constPeriod.append(NEW_LINE_CHARACTER);
                        }
                        constPeriod.append(String.format(
                                DURATION_DISPLAY_FORMAT,

                                constantPeriod.getSeconds().longValue(),
                                Messages.Xpdl2EventAdapter_Seconds_label));
                    }
                    // Microseconds
                    if (constantPeriod.getMicroSeconds() != null
                            && constantPeriod.getMicroSeconds()
                                    .longValue() > 0) {
                        if (constPeriod.length() > 0) {
                            // New line character
                            constPeriod.append(NEW_LINE_CHARACTER);
                        }
                        constPeriod.append(String.format(
                                DURATION_DISPLAY_FORMAT,
                                constantPeriod.getMicroSeconds().longValue(),
                                Messages.Xpdl2EventAdapter_Microseconds_label));
                    }

                    return constPeriod.toString();
                }

            } else if (deadline.getDeadlineDuration() != null) {
                return deadline.getDeadlineDuration().getText();

            }

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#getTriggerTimerType()
     * 
     * @return
     */
    @Override
    public String getTriggerTimerType() {
        TriggerTimer triggerTimer =
                EventObjectUtil.getTriggerTimer(getActivity());

        if (triggerTimer.getTimeCycle() != null) {
            return Messages.Xpdl2EventAdapter_TimerTypeCycle_label;
        }
        return Messages.Xpdl2EventAdapter_TimerTypeDateTime_label;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#isNonCancellingEvent()
     * 
     * @return
     */
    @Override
    public boolean isNonCancellingEvent() {
        return EventObjectUtil.isAttachedToTask(getActivity())
                && EventObjectUtil.isNonCancellingEvent(getActivity());
    }

    /**
     * Return <code>true</code> if the specified start event activity is inside
     * an event sub process and is non-interrupting, <code>false</code>
     * otherwise.
     * 
     * @return <code>true</code> if the specified start event activity is inside
     *         an event sub process and is non-interrupting, <code>false</code>
     *         otherwise.
     */
    @Override
    public boolean isNonInterruptingEvent() {

        return EventObjectUtil
                .isNonInterruptingEventSubProcessStartEvent(getActivity());
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.EventAdapter#isLocalSignalEvent()
     * 
     * @return
     */
    @Override
    public boolean isLocalSignalEvent() {
        Activity activity = getActivity();
        if (activity != null) {
            return EventObjectUtil.isLocalSignalEvent(activity);
        }
        return false;
    }

}

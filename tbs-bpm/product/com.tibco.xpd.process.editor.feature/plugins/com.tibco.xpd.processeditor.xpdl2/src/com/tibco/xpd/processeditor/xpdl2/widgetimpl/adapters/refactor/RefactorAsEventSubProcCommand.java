/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.TriggerMultiple;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command to refactor an event handler flow to an event sub-process.
 * 
 * @author sajain
 * @since Oct 7, 2014
 */
public class RefactorAsEventSubProcCommand extends
        AbstractRefactorAsSubProcCommand {

    /**
     * @param editingDomain
     * @param objects
     * @param imageProvider
     */
    public RefactorAsEventSubProcCommand(EditingDomain editingDomain,
            List<Object> objects, DiagramModelImageProvider imageProvider) {

        super(editingDomain, objects, imageProvider);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#getRefactorCommand()
     * 
     * @return
     */
    @Override
    protected Command getRefactorCommand() {

        Command retCmd = UnexecutableCommand.INSTANCE;

        CompoundCommand cmd = new CompoundCommand();

        Command superRefactorCmd = super.getRefactorCommand();

        cmd.append(superRefactorCmd);

        /*
         * XPD-6853: Saket: Make Refactor into Embedded sub-process smart enough
         * to detect it has been called for an event handler flow and create an
         * event sub-process instead - converting the event handler into start
         * event along the way. i.e. would detect that there was only one ‘start
         * of flow’ activity in selection and that is an intermediate event and
         * the selection is in main process.
         */
        appendCmdToConvertEventHdlrsToEqvStartEvents(cmd);

        retCmd = cmd;

        return retCmd;
    }

    /**
     * Appends commands to convert the only event handler present in the list of
     * selected objects to it's equivalent start event.
     * 
     * @param cmd
     *            {@link CompoundCommand} to which the commands will be
     *            appended.
     */
    private void appendCmdToConvertEventHdlrsToEqvStartEvents(
            CompoundCommand cmd) {

        List<EObject> objectList = getObjectList();

        for (EObject nodeObject : objectList) {

            if (nodeObject instanceof Activity) {
                /*
                 * Get activity.
                 */
                Activity act = (Activity) nodeObject;

                /*
                 * Check if it's an event handler activity.
                 */
                if (Xpdl2ModelUtil.isEventHandlerActivity(act)) {

                    /*
                     * Get event.
                     */
                    Event event = act.getEvent();

                    /*
                     * Get intermediate event which is to be replaced by a new
                     * start event.
                     */
                    IntermediateEvent oldIntermediateEvent =
                            (IntermediateEvent) event;

                    /*
                     * Create a new start event.
                     */
                    StartEvent newStartEvent =
                            Xpdl2Factory.eINSTANCE.createStartEvent();

                    /*
                     * Append commands to set up the new start event.
                     */
                    appendCommandsToSetUpNewStartEvent(cmd,
                            editingDomain,
                            oldIntermediateEvent,
                            newStartEvent);

                    /*
                     * Get trigger type node.
                     */
                    EObject obj = event.getEventTriggerTypeNode();

                    /*
                     * (1) Type catch message. (2) Type catch signal. (3) Type
                     * catch timer. (4) Type catch conditional. (5) Type catch
                     * multiple.
                     */
                    EventTriggerType eventTriggerType =
                            EventObjectUtil.getEventTriggerType(act);
                    TriggerType oldTrigger = oldIntermediateEvent.getTrigger();

                    if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                            .equals(eventTriggerType)) {

                        if (obj instanceof TriggerResultMessage) {

                            /*
                             * Get trigger result message.
                             */
                            TriggerResultMessage trm =
                                    (TriggerResultMessage) obj;

                            /*
                             * Add trigger result message to the newly created
                             * start event.
                             */
                            TriggerResultMessage trmCopy = EcoreUtil.copy(trm);

                            newStartEvent.setTriggerResultMessage(trmCopy);

                            /*
                             * SId XPD-6853 - now made all event sub-proc non
                             * interrupting by default (except signal which
                             * takes original similar setting from event handler
                             */
                        }

                    } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(eventTriggerType)) {

                        if (obj instanceof TriggerResultSignal) {

                            /*
                             * Get trigger result signal.
                             */
                            TriggerResultSignal trs = (TriggerResultSignal) obj;

                            /*
                             * Add trigger result signal to the newly created
                             * start event.
                             */
                            TriggerResultSignal trsCopy = EcoreUtil.copy(trs);

                            newStartEvent.setTriggerResultSignal(trsCopy);

                            /*
                             * Sid XPD-6853 : Transfer the interrupting /
                             * non-interrupting flag equivalents to new start
                             * event.
                             */
                            boolean isNonInterrupting =
                                    Xpdl2ModelUtil
                                            .getOtherAttributeAsBoolean(trs,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_SignalHandlerAsynchronous());

                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            newStartEvent,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_NonInterruptingEvent(),
                                            isNonInterrupting));

                        }

                    } else if (EventTriggerType.EVENT_TIMER_LITERAL
                            .equals(eventTriggerType)) {

                        if (obj instanceof TriggerTimer) {

                            /*
                             * Get trigger timer.
                             */
                            TriggerTimer tt = (TriggerTimer) obj;

                            /*
                             * Add trigger timer to the newly created start
                             * event.
                             */
                            TriggerTimer ttCopy = EcoreUtil.copy(tt);

                            newStartEvent.setTriggerTimer(ttCopy);
                        }

                    } else if (EventTriggerType.EVENT_CONDITIONAL_LITERAL
                            .equals(eventTriggerType)) {

                        if (obj instanceof TriggerConditional) {

                            /*
                             * Get trigger conditional.
                             */
                            TriggerConditional tc = (TriggerConditional) obj;

                            /*
                             * Add trigger result message to the newly created
                             * start event.
                             */
                            TriggerConditional tcCopy = EcoreUtil.copy(tc);

                            newStartEvent.setTriggerConditional(tcCopy);
                        }

                    } else if (EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL
                            .equals(eventTriggerType)) {

                        if (obj instanceof TriggerMultiple) {

                            /*
                             * Get trigger result message.
                             */
                            TriggerMultiple tm = (TriggerMultiple) obj;

                            /*
                             * Add trigger result message to the newly created
                             * start event.
                             */
                            TriggerMultiple tmCopy = EcoreUtil.copy(tm);

                            newStartEvent.setTriggerMultiple(tmCopy);
                        }

                    } else {

                        oldTrigger = TriggerType.NONE_LITERAL;

                    }

                    /*
                     * Append command to set new start event trigger.
                     */
                    cmd.append(SetCommand.create(editingDomain,
                            newStartEvent,
                            Xpdl2Package.eINSTANCE.getStartEvent_Trigger(),
                            oldTrigger));
                    /*
                     * Finally, append command to add the newly created start
                     * event to the activity.
                     */
                    cmd.append(SetCommand.create(editingDomain,
                            act,
                            Xpdl2Package.eINSTANCE.getActivity_Event(),
                            newStartEvent));

                    /*
                     * Break out of the loop because there's we expect just one
                     * event handler activity here as we already know that we
                     * are refactoring to an event sub-process.
                     */
                    break;
                }
            }
        }

    }

    /**
     * Append commands to set up the new start event to the specified compound
     * command.
     * 
     * @param compoundCmd
     *            Compound command to which the commands are to be appended.
     * @param newStartEvent
     *            New start event.
     * @param oldIntermediateEvent
     *            Old intermediate event.
     * @param editingDomain
     *            Editing domain.
     */
    private void appendCommandsToSetUpNewStartEvent(
            CompoundCommand compoundCmd, EditingDomain editingDomain,
            IntermediateEvent oldIntermediateEvent, StartEvent newStartEvent) {

        /*
         * Append command to set new start event implementation.
         */
        compoundCmd.append(SetCommand.create(editingDomain,
                newStartEvent,
                Xpdl2Package.eINSTANCE.getStartEvent_Implementation(),
                oldIntermediateEvent.getImplementation()));

        /*
         * Append command to set new start event implementation type.
         */
        compoundCmd
                .append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                newStartEvent,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType(),
                                Xpdl2ModelUtil
                                        .getOtherAttribute(oldIntermediateEvent,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType())));

        /*
         * Append command to set new start event's xpdExt:NonInterruptingEvent
         * to true (default).
         * 
         * SId XPD-6853 - now made all event sub-proc non interrupting by
         * default (except signal which takes original similar setting from
         * event handler
         */
        compoundCmd.append(Xpdl2ModelUtil
                .getSetOtherAttributeCommand(editingDomain,
                        newStartEvent,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_NonInterruptingEvent(),
                        true));

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#getCommandLabel()
     * 
     * @return Label to put on the refactor command.
     */
    @Override
    protected String getCommandLabel() {
        return Messages.RefactorAsEventSubProcCommand_RefactorAsEventSubproc_menu;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#createSubProcTask(org.eclipse.draw2d.geometry.Rectangle,
     *      com.tibco.xpd.processwidget.WidgetRGB,
     *      com.tibco.xpd.processwidget.WidgetRGB)
     * 
     * @param contentBounds2
     * @param fillColor
     * @param lineColor
     * @return new SubProcess Task Activity.
     */
    @Override
    protected Activity createSubProcTask(WidgetRGB fillColor,
            WidgetRGB lineColor) {

        Rectangle contentBounds = getContentBounds();

        return ElementsFactory.createTask(contentBounds.getCenter(),
                contentBounds.getSize(),
                "", //$NON-NLS-1$
                TaskType.EVENT_SUBPROCESS_LITERAL,
                fillColor.toString(),
                lineColor.toString());
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#getUniqueSubProcName(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     * @return Unique Subprocess Name.
     */
    @Override
    protected String getUniqueSubProcName(Process process) {

        return Xpdl2ModelUtil
                .getUniqueLabelInSet(TaskType.EVENT_SUBPROCESS_LITERAL
                        .toString(), Xpdl2ModelUtil
                        .getAllActivitiesInProc(process));
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#getRefactorWizard()
     * 
     * @return Refactor Wizard.
     */
    @Override
    protected BaseRefactorAsSubProcWizard getRefactorWizard() {

        RefactorAsSubProcWizardInfo refactorInfo = getRefactorInfo();

        /* Start Event not required for Event Sub Process */
        refactorInfo.validationInfo |=
                RefactorAsSubProcWizardInfo.SUBPROC_HIDE_INSERT_STARTEVENT_OPTION;
        /* END Event not required for Event Sub Process */
        refactorInfo.validationInfo |=
                RefactorAsSubProcWizardInfo.SUBPROC_HIDE_INSERT_ENDEVENT_OPTION;
        /* SubProcess IS Transaction not required for Event Sub Process */
        refactorInfo.validationInfo |=
                RefactorAsSubProcWizardInfo.SUBPROC_HIDE_SUBPROC_IS_TRANSACTION_OPTION;

        return new RefactorAsEventSubProcWizard(refactorInfo, editingDomain);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#addStartEvent(org.eclipse.emf.common.command.CompoundCommand)
     *      Does nothing here as the Event SubProcess does not need a Start
     *      Event.
     * @param cmd
     */
    @Override
    protected void addStartEvent(CompoundCommand cmd) {
        /*
         * Do nothing as we don't need to explicitly add a start event while
         * refactoring to an event sub-process.
         */
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand#addEndEvent(org.eclipse.emf.common.command.CompoundCommand)
     *      Does nothing here as the Event SubProcess does not need an End Event
     * @param cmd
     */
    @Override
    protected void addEndEvent(CompoundCommand cmd) {
        /*
         * Do nothing as we don't need to explicitly add an end event while
         * refactoring to an event sub-process.
         */
    }

    /**
     * @return
     */
    @Override
    protected boolean shouldConnectToIncomingFlow() {
        /*
         * In both the cases for Single Event handler selection or Event Handler
         * Flow Selection, no other incoming to this flow should be drawn
         */
        return false;
    }

    /**
     * This method allows implicitly selecting additional objects for refactor,
     * related to the actually selected Objects.Subclasess can provide their
     * implementation for such additional implicit selection.Default does
     * nothing and returns the same list which is passed.
     * 
     * @param objects
     * @return List of Implicitly and explicitly selected objects for refactor.
     */
    @Override
    protected List<Object> selectRelatedObjects(List<Object> objects) {
        /*
         * Select all elements of the Event Handler Flow if the selection is a
         * Single Event Handler selection
         */

        if (objects != null && objects.size() == 1) {

            Object object = objects.get(0);
            if (object instanceof Activity) {

                Activity activity = (Activity) object;

                if (Xpdl2ModelUtil.isEventHandlerActivity(activity)) {

                    /* get all downstream activities for this Event Handler */
                    ProcessFlowAnalyser analyser =
                            new ProcessFlowAnalyser(true,
                                    activity.getFlowContainer());

                    Set<Activity> allEventHandlerFlowActivities =
                            analyser.getDownstreamActivities(activity.getId(),
                                    false);

                    for (Activity activity2 : allEventHandlerFlowActivities) {

                        if (!objects.contains(activity2)) {
                            objects.add(activity2);
                            /*
                             * Collect attached Boundary Events if any.
                             */

                            Collection<Activity> attachedEvents =
                                    Xpdl2ModelUtil.getAttachedEvents(activity2);

                            if (attachedEvents != null
                                    && !attachedEvents.isEmpty()) {

                                objects.addAll(attachedEvents);
                            }
                        }

                    }

                }
            }
        }
        return objects;
    }

}

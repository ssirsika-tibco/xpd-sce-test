/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.resources.AbstractActivityPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit listener to maintain flags specific to event handler activities.
 * 
 * @author aallway
 * @since 23 May 2012
 */
public class EventHandlerPreCommitListener extends
        AbstractActivityPreCommitContributor {

    public EventHandlerPreCommitListener() {
    }

    /**
     * @see com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand(org.eclipse.emf.transaction.ResourceSetChangeEvent,
     *      java.util.Collection)
     * 
     * @param event
     * @param notifications
     * @return
     * @throws RollbackException
     */
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        CompoundCommand cmd = new CompoundCommand();

        maintainEventHandlerFlowStrategy(notifications,
                event.getEditingDomain(),
                cmd);

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Maintain the event handler flow strategy model.
     * 
     * @param notifications
     * @param editingDomain
     * @param cmd
     */
    private void maintainEventHandlerFlowStrategy(
            Collection<ENotificationImpl> notifications,
            EditingDomain editingDomain, CompoundCommand cmd) {

        Set<Activity> alreadyProcessed = new HashSet<Activity>();

        for (Notification notification : notifications) {
            /*
             * add SerializeConcurrent flow strategy to event handlers that
             * don't have it set yet.
             */
            Activity activity = isActivityContentChange(notification);
            if (activity == null) {
                activity = isAddActivity(notification);
                if (activity == null) {
                    /*
                     * Not a direct change under an activity, but an activity
                     * can become or un-become an event handler by
                     * removing/adding connection to it as well.
                     */
                    activity = isRemoveConnectionTo(notification);
                    if (activity == null) {
                        activity = isAddToConnection(notification);
                    }
                }
            }

            if (activity != null && !alreadyProcessed.contains(activity)) {
                alreadyProcessed.add(activity);

                /*
                 * ABPM-911: Saket: Event subprocess start events should also be
                 * considered as event handlers now.
                 */
                EventTriggerType eventTriggerType = EventObjectUtil.getEventTriggerType(activity);

                /*
                 * Sid ACE-2019 - Start request (aka type none) event in event
                 * sub-process eeds to have flow strategy defaulted.
                 */
                boolean isEventHandlerActivity = (Xpdl2ModelUtil.isEventHandlerActivity(activity)
                        && (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL.equals(eventTriggerType)
                                || EventTriggerType.EVENT_NONE_LITERAL.equals(eventTriggerType)))
                        || EventObjectUtil.isEventSubProcessMessageStartEvent(activity)
                        || EventObjectUtil.isEventSubProcessStartRequestEvent(activity);


                if (isEventHandlerActivity && !Xpdl2ModelUtil.isPageflowOrSubType(activity.getProcess())) {
                    /*
                     * Make sure message event handler activities have flow
                     * strategy attribute.
                     */
                    addMissingFlowStrategy(activity, editingDomain, cmd);

                } else {
                    /*
                     * Make sure non message event handler activities have flow
                     * strategy attribute removed.
                     */

                    removeFlowStrategy(activity, editingDomain, cmd);
                }

                /*
                 * Remove 'SignalHandlerAsynchronous' flag from a catch-signal
                 * event if its no more an event-handler (i.e., has an incoming
                 * flow)
                 */
                if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(eventTriggerType)) {

                    if (!isEventHandlerActivity
                            || !EventObjectUtil.isLocalSignalEvent(activity)) {
                        /*
                         * if the catch signal event is no longer an event
                         * handler or if its no longer a local signal then
                         * remove the Async Flag
                         */
                        removeSignalHandlerAsynchronousFlag(activity,
                                editingDomain,
                                cmd);
                    }
                }
            }
        }
    }

    /**
     * Make sure non message event handler activities have flow strategy
     * attribute removed.
     * 
     * @param activity
     * @param editingDomain
     * @param cmd
     */
    private void removeFlowStrategy(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {

        if (activity.getEvent() != null) {

            /*
             * Sid ACE-2019 attribute might be on TriggerResultMessage OR on
             * StartEvent/IntermediateEvent now.
             */
            if (activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {

                TriggerResultMessage triggerResultMessage =
                        (TriggerResultMessage) activity.getEvent().getEventTriggerTypeNode();

                EventHandlerFlowStrategy flowStrategy =
                        (EventHandlerFlowStrategy) Xpdl2ModelUtil.getOtherAttribute(triggerResultMessage,
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy());
                if (flowStrategy != null) {
                    cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                            triggerResultMessage,
                                    XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_EventHandlerFlowStrategy(),
                            null));
                }

            } else {
                EventHandlerFlowStrategy flowStrategy =
                        (EventHandlerFlowStrategy) Xpdl2ModelUtil.getOtherAttribute(activity.getEvent(),
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy());

                if (flowStrategy != null) {
                    cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                            activity.getEvent(),
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy(),
                            null));
                }
            }
        }
    }

    /**
     * Make sure message event handler activities have flow strategy attribute.
     * 
     * @param activity
     * @param editingDomain
     * @param cmd
     */
    private void addMissingFlowStrategy(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {

        if (activity.getEvent() != null) {

            /*
             * Sid ACE-2019 attribute might be on TriggerResultMessage OR on
             * StartEvent/IntermediateEvent now.
             */
            if (activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {

                TriggerResultMessage triggerResultMessage =
                        (TriggerResultMessage) activity.getEvent().getEventTriggerTypeNode();

                /*
                 * Get the event flow strategy.
                 */
                EventHandlerFlowStrategy flowStrategy =
                        (EventHandlerFlowStrategy) Xpdl2ModelUtil.getOtherAttribute(triggerResultMessage,
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy());

                /*
                 * If the flow strategy isn't there yet, then add it.
                 */
                if (flowStrategy == null) {
                    cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                            triggerResultMessage,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy(),
                            EventHandlerFlowStrategy.SERIALIZE_CONCURRENT));
                }

            } else {
                /*
                 * Get the event flow strategy.
                 */
                EventHandlerFlowStrategy flowStrategy =
                        (EventHandlerFlowStrategy) Xpdl2ModelUtil.getOtherAttribute(activity.getEvent(),
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy());

                /*
                 * If the flow strategy isn't there yet, then add it.
                 */
                if (flowStrategy == null) {
                    cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                            activity.getEvent(),
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy(),
                            EventHandlerFlowStrategy.SERIALIZE_CONCURRENT));
                }

            }
        }
    }

    /**
     * Remove 'SignalHandlerAsynchronous' flag from catch-signal event-handler
     * as it has now incoming flow
     * 
     * @param activity
     * @param editingDomain
     * @param cmd
     */
    private void removeSignalHandlerAsynchronousFlag(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {

        if (activity.getEvent() != null) {

            TriggerResultSignal triggerResultSignal =
                    EventObjectUtil.getTriggerSignal(activity);

            if (triggerResultSignal != null
                    && CatchThrow.CATCH.equals(triggerResultSignal
                            .getCatchThrow())) {

                Object signalHandlerAsynchronous =
                        Xpdl2ModelUtil
                                .getOtherAttribute(triggerResultSignal,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_SignalHandlerAsynchronous());
                if (signalHandlerAsynchronous != null) {
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    triggerResultSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SignalHandlerAsynchronous(),
                                    null));
                }
            }
        }
    }
}

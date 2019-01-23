/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
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
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.resources.AbstractActivityPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit contributor to maintain event sub process start event settings.
 * 
 * @author sajain
 * @since Aug 12, 2014
 */
public class EventSubProcessStartEventPreCommitContributor extends
        AbstractActivityPreCommitContributor {

    public EventSubProcessStartEventPreCommitContributor() {
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

        /*
         * Maintain event subprocess interruption settings.
         */
        maintainEventSubProcessInterruptionSettings(notifications,
                event.getEditingDomain(),
                cmd);

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Maintain the event sub process interruption/continuation settings.
     * 
     * @param notifications
     * @param editingDomain
     * @param cmd
     */
    private void maintainEventSubProcessInterruptionSettings(
            Collection<ENotificationImpl> notifications,
            EditingDomain editingDomain, CompoundCommand cmd) {

        Set<Activity> alreadyProcessed = new HashSet<Activity>();

        for (Notification notification : notifications) {

            /*
             * Add xpdExt:NonInterruptingEvent attribute to start event
             * activities inside an event sub process which don't have it yet.
             */
            Activity activity = isActivityContentChange(notification);

            if (activity == null) {

                activity = isAddActivity(notification);

                if (activity == null) {

                    activity = isRemoveConnectionTo(notification);

                    if (activity == null) {

                        activity = isAddToConnection(notification);
                    }
                }
            }

            if (activity != null && !alreadyProcessed.contains(activity)) {
                alreadyProcessed.add(activity);

                /*
                 * Handle start events and sub-process activities separately.
                 */
                if (EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                        .getFlowType(activity))) {

                    handleStartEvents(activity, editingDomain, cmd);

                } else if (TaskType.EVENT_SUBPROCESS_LITERAL
                        .equals(TaskObjectUtil.getTaskTypeStrict(activity))
                        || TaskType.EMBEDDED_SUBPROCESS_LITERAL
                                .equals(TaskObjectUtil
                                        .getTaskTypeStrict(activity))) {

                    handleSubProcesses(activity, editingDomain, cmd);
                }
            }
        }
    }

    /**
     * Handle embedded/event sub-processes. Basically here we check whether the
     * start events inside the sub-processes are configured correctly.
     * 
     * @param activity
     *            Embedded/Event sub-process activity.
     * @param editingDomain
     *            Editing domain.
     * @param cmd
     *            Compound command to append commands to.
     */
    private void handleSubProcesses(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {

        /*
         * Check if it's an embedded sub-process OR an event sub-process.
         */
        if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {

            /*
             * Go through all the activities inside the sub-process and look for
             * a start event.
             */
            for (Activity eachActivity : Xpdl2ModelUtil
                    .getAllActivitiesInEmbeddedSubProc(activity)) {

                if (EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                        .getFlowType(eachActivity))) {

                    /*
                     * Add non-interrrupting event attribute to an event
                     * sub-process start event.
                     */
                    addNonInterruptingEventAttribute(eachActivity,
                            editingDomain,
                            cmd);

                }
            }
        } else if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {

            /*
             * Go through all the activities inside the sub-process and look for
             * a start event.
             */
            for (Activity eachActivity : Xpdl2ModelUtil
                    .getAllActivitiesInEmbeddedSubProc(activity)) {

                if (EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                        .getFlowType(eachActivity))) {

                    /*
                     * Remove non-interrrupting event attribute from an embedded
                     * sub-process start event.
                     */
                    removeNonInterruptingEventAttribute(eachActivity,
                            editingDomain,
                            cmd);

                }
            }
        }

    }

    /**
     * Handle start events. Here we check whether the start events themselves
     * are configured correctly.
     * 
     * @param activity
     *            Embedded/Event sub-process activity.
     * @param editingDomain
     *            Editing domain.
     * @param cmd
     *            Compound command to append commands to.
     */
    private void handleStartEvents(Activity act, EditingDomain editingDomain,
            CompoundCommand cmd) {

        boolean isContainerEventSubProcess =
                com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil
                        .isEventSubProcessStartEvent(act);

        if (isContainerEventSubProcess) {

            /*
             * Make sure start events inside an event sub process have
             * xpdExt:NonInterruptingEvent attribute.
             */
            addNonInterruptingEventAttribute(act, editingDomain, cmd);

        } else {

            /*
             * Make sure start events NOT inside an event sub process have
             * xpdExt:NonInterruptingEvent attribute removed.
             */
            removeNonInterruptingEventAttribute(act, editingDomain, cmd);
        }
    }

    /**
     * Remove xpdExt:NonInterruptingEvent from the specified start event if it
     * is present.
     * 
     * @param activity
     *            Start event activity.
     * @param editingDomain
     *            Editing domain.
     * @param cmd
     *            Compound command instance.
     */
    private void removeNonInterruptingEventAttribute(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {
        Event ev = activity.getEvent();

        if (ev instanceof StartEvent) {

            StartEvent startEv = (StartEvent) ev;

            /*
             * Check if the attribute is there or not.
             */
            if (Xpdl2ModelUtil.getOtherAttribute(startEv,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_NonInterruptingEvent()) != null) {

                /*
                 * If it is, then remove it.
                 */
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                startEv,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_NonInterruptingEvent(),
                                null));
            }

        }
    }

    /**
     * Add xpdExt:NonInterruptingEvent to the specified start event and set it
     * to <code>false</code> if it is NOT ALREADY THERE.
     * 
     * @param activity
     *            Start event activity.
     * @param editingDomain
     *            Editing domain.
     * @param cmd
     *            Compound command instance.
     */
    private void addNonInterruptingEventAttribute(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {
        Event ev = activity.getEvent();

        if (ev instanceof StartEvent) {

            StartEvent startEv = (StartEvent) ev;

            /*
             * Check if the attribute is already there.
             */
            if (Xpdl2ModelUtil.getOtherAttribute(startEv,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_NonInterruptingEvent()) == null) {

                /*
                 * If it is not, then add it.
                 */

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                startEv,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_NonInterruptingEvent(),
                                new Boolean(false)));
            }

        }
    }
}

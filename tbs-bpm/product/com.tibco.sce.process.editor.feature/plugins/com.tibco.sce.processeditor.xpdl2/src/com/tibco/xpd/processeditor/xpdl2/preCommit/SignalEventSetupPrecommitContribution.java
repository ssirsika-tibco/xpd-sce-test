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
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit listener to deal with signal events.
 * 
 * @author aallway
 * @since 19 Apr 2012
 * 
 *        The default implicit data association for throw signal event
 *        previously being handled here has been moved to the ElementsFactory
 *        class
 */
public class SignalEventSetupPrecommitContribution extends
        AbstractProcessPreCommitContributor {

    /**
     * @see com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand(org.eclipse.emf.transaction.ResourceSetChangeEvent,
     *      java.util.Collection)
     * 
     * @param event
     * @param notifications
     * @return
     * @throws RollbackException
     */
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

        TransactionalEditingDomain editingDomain = event.getEditingDomain();

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.SignalEventSetupPrecommitContribution_EnsureSignalConsistency_menu);

        /*
         * Remove NonCancelling flag from catch signal IF it is removed from
         * task boundary - as it is no longer applicable.
         */
        removeInappropriateNonCancellingSignalFlags(editingDomain,
                cmd,
                notifications);
        /*
         * <li>Check if the Label of the Throw/Catch Events have default value
         * as "Throw Signal Event" or "Catch Signal event" resp.</li> <li>If the
         * signalName for the Throw/Catch Events is set, Then the Label should
         * be set to "Throw: <signalName>" or "Catch: <signalName>" resp.</li>
         */

        synchLabelWithSignalNameChanges(editingDomain, cmd, notifications);

        /*
         * SID XPD-3874 - removed code to remove OverwriteWorkItemData flag when
         * no catch signal's left on activity. We shouldn't do this anymore
         * because the flag can be set (and used at runtime) on user task itself
         * (in advanced properties) and independently of whether it has attached
         * catch signals.
         * 
         * The user task advanced properties contribution for this property will
         * take care of removing flag when no longer applicable to user task
         * (for intanceof if changed to task type none).
         */

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * * Remove NonCancelling flag from catch signal IF it is removed from task
     * boundary - as it is no longer applicable.
     * 
     * @param editingDomain
     * @param cmd
     * @param notifications
     */
    private void removeInappropriateNonCancellingSignalFlags(
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            Collection<ENotificationImpl> notifications) {

        /* Get set of non-cancelling signal activities */
        Set<TriggerResultSignal> incorrectNonCancellingSignals =
                new HashSet<TriggerResultSignal>();

        for (ENotificationImpl notification : notifications) {

            Activity activity =
                    (Activity) getTypedAncestor(notification, Activity.class);

            if (activity != null) {
                Event event = activity.getEvent();
                if (event != null
                        && event.getEventTriggerTypeNode() instanceof TriggerResultSignal) {
                    /* It's a signal event. */
                    TriggerResultSignal trs =
                            (TriggerResultSignal) event
                                    .getEventTriggerTypeNode();

                    if (Xpdl2ModelUtil.getOtherAttribute(trs,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_NonCancelling()) != null) {
                        /*
                         * With some (true or false) value set for
                         * NonCancelling.
                         */

                        if (!EventObjectUtil.isAttachedToTask(activity)
                                || !CatchThrow.CATCH.equals(EventObjectUtil
                                        .getCatchThrowType(activity))) {
                            /* That isn't attached to task or isn't a catch. */
                            incorrectNonCancellingSignals.add(trs);
                        }
                    }
                }
            }
        }

        /*
         * Remove nonCancelling extension attribute if this is has been removed
         * from task boundary or is no longer a catch-signal.
         */
        for (TriggerResultSignal trs : incorrectNonCancellingSignals) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            trs,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_NonCancelling(),
                            null));

            /*
             * Also need to remove the reschedule timers info (as that only
             * applies to non-cancelling events on task boundary (and controls
             * to delete it won't be there otherwise).
             */
            SignalData signalData =
                    (SignalData) Xpdl2ModelUtil.getOtherElement(trs,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SignalData());

            if (signalData != null && signalData.getRescheduleTimers() != null) {
                cmd.append(SetCommand.create(editingDomain,
                        signalData,
                        XpdExtensionPackage.eINSTANCE
                                .getSignalData_RescheduleTimers(),
                        SetCommand.UNSET_VALUE));
            }
        }
    }

    /**
     * <li>Check if the Label of the Throw/Catch Events have default value as
     * "Throw Signal Event" or "Catch Signal event" resp.</li> <li>If the
     * signalName for the Throw/Catch Events is set, Then the Label should be
     * set to "Throw: <signalName>" or "Catch: <signalName>" resp.</li>
     * 
     * @param editingDomain
     * @param cmd
     * @param notifications
     */
    private void synchLabelWithSignalNameChanges(
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            Collection<ENotificationImpl> notifications) {

        for (ENotificationImpl notification : notifications) {
            String labelName = null;
            Activity signalActivity =
                    (Activity) getTypedAncestor(notification, Activity.class);

            /*
             * XPD-7075: Sync Labels only for Local Signals, Global Signals will
             * do that in its own plugin.
             */
            if (signalActivity != null
                    && EventObjectUtil.isLocalSignalEvent(signalActivity)) {
                /*
                 * Checks if 1. The 'SET' event was called 2. If the notifier
                 * class is 'TriggerResultSignal' 3. The displayName was
                 * changed.
                 */
                String oldLabelName =
                        Xpdl2ModelUtil.getDisplayName(signalActivity);

                if ((notification.getNotifier() instanceof TriggerResultSignal)) {

                    String defaultEventName =
                            ElementsFactory
                                    .getDefaultNameForEventType(EventObjectUtil
                                            .getFlowType(signalActivity),
                                            EventObjectUtil
                                                    .getEventTriggerType(signalActivity));

                    if ((Notification.SET == notification.getEventType())
                            && Xpdl2Package.eINSTANCE
                                    .getTriggerResultSignal_Name()
                                    .equals(notification.getFeature())) {

                        /*
                         * If the Old SignalName is equal to Current Label Name
                         * Or the Current Label name Starts With Throw Signal
                         * Event Then Update LabelName, i.e. Set SignalName as
                         * Label Name by appending a 'Throw:' keyword in the
                         * Label name.
                         */

                        if (CatchThrow.THROW.equals(EventObjectUtil
                                .getCatchThrowType(signalActivity))) {
                            String getOldSignalName = null;

                            if (notification.getOldStringValue() == null
                                    || notification.getOldStringValue()
                                            .length() == 0) {
                                getOldSignalName = defaultEventName;

                            } else {
                                getOldSignalName =
                                        Messages.SignalEventSetupPrecommitContribution_ThrowEventSignal_label
                                                + " " //$NON-NLS-1$
                                                + notification
                                                        .getOldStringValue();

                            }
                            /*
                             * Checks if old labelName starts with oldSignalName
                             * or old labelName starts with DefaultEvent Name If
                             * so, then Set new signalName as new labelName
                             */

                            if (oldLabelName.startsWith(getOldSignalName)
                                    || oldLabelName
                                            .startsWith(defaultEventName)) {

                                if (notification.getNewStringValue() == null
                                        || notification.getNewStringValue()
                                                .length() == 0) {
                                    labelName = defaultEventName;

                                } else {

                                    labelName =
                                            Messages.SignalEventSetupPrecommitContribution_ThrowEventSignal_label
                                                    + " " //$NON-NLS-1$
                                                    + notification
                                                            .getNewStringValue();
                                }
                            }
                        }
                        /*
                         * If the Old SignalName is equal to Current Label Name
                         * Or the Current Label name Starts With Catch Signal
                         * Event Then Update LabelName, i.e. Set SignalName as
                         * Label Name by appending a 'Catch:' keyword in the
                         * Label name.
                         */else {
                            String getOldSignalName = null;

                            if (notification.getOldStringValue() == null
                                    || notification.getOldStringValue()
                                            .length() == 0) {
                                getOldSignalName = defaultEventName;

                            } else {
                                getOldSignalName =
                                        Messages.SignalEventSetupPrecommitContribution_CatchEventSignal_label
                                                + " " //$NON-NLS-1$
                                                + notification
                                                        .getOldStringValue();
                            }

                            if (oldLabelName.startsWith(getOldSignalName)
                                    || oldLabelName
                                            .startsWith(defaultEventName)) {
                                if (notification.getNewStringValue() == null
                                        || notification.getNewStringValue()
                                                .length() == 0) {
                                    labelName = defaultEventName;
                                } else {
                                    labelName =
                                            Messages.SignalEventSetupPrecommitContribution_CatchEventSignal_label
                                                    + " " //$NON-NLS-1$
                                                    + notification
                                                            .getNewStringValue();
                                }
                            }
                        }
                    } else if (XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_SignalType()
                            .equals(notification.getFeature())) {

                        if (notification.getNewStringValue() != null
                                && !notification
                                        .getNewStringValue()
                                        .equals(notification.getOldStringValue())) {

                            if (oldLabelName.startsWith(defaultEventName
                                    .substring(0, 5))) {

                                String signalName =
                                        EventObjectUtil
                                                .getSignalName(signalActivity);
                                /*
                                 * If the Signal Type is changed and we already
                                 * have a new Signal Name then make the new
                                 * label as Catch: signalName or Throw:
                                 * signalName
                                 */
                                if (signalName != null
                                        && signalName.length() != 0) {

                                    if (CatchThrow.THROW.equals(EventObjectUtil
                                            .getCatchThrowType(signalActivity))) {
                                        labelName =
                                                Messages.SignalEventSetupPrecommitContribution_ThrowEventSignal_label
                                                        + " " //$NON-NLS-1$
                                                        + signalName;
                                    } else {
                                        labelName =
                                                Messages.SignalEventSetupPrecommitContribution_CatchEventSignal_label
                                                        + " " //$NON-NLS-1$
                                                        + signalName;
                                    }

                                } else {
                                    /*
                                     * If we do not have a signal name and If
                                     * the old name starts with "Throw" or
                                     * "Catch" then set back to default name.
                                     */
                                    labelName = defaultEventName;
                                }
                            }
                        }
                    }
                    /*
                     * Update the Label field to signalName using a Command
                     */
                    if (labelName != null) {

                        Collection<Activity> allActivities =
                                Xpdl2ModelUtil
                                        .getAllActivitiesInProc(signalActivity
                                                .getProcess());
                        allActivities.remove(signalActivity);
                        /*
                         * Check if the LabelName of the Event is unique, if not
                         * then append a trailing digit to the LabelName
                         */

                        String newLabelName =
                                Xpdl2ModelUtil.getUniqueLabelInSet(labelName,
                                        allActivities);

                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        signalActivity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName(),
                                        newLabelName));
                        /*
                         * If the Previous LabelName and Name fields have same
                         * value then update the Name field using a Command,
                         * else if user has explicitly set the Name field then
                         * do not update the Name field.
                         */

                        String nameFromPreviousLabelName =
                                com.tibco.xpd.ui.util.NameUtil
                                        .getInternalName(Xpdl2ModelUtil
                                                .getDisplayName(signalActivity),
                                                false);

                        if (nameFromPreviousLabelName.equals(signalActivity
                                .getName()) && labelName != null) {

                            cmd.append(SetCommand.create(editingDomain,
                                    signalActivity,
                                    Xpdl2Package.eINSTANCE
                                            .getNamedElement_Name(),
                                    com.tibco.xpd.ui.util.NameUtil
                                            .getInternalName(newLabelName,
                                                    false)));
                        }
                    }
                }
            }
        }
    }
}

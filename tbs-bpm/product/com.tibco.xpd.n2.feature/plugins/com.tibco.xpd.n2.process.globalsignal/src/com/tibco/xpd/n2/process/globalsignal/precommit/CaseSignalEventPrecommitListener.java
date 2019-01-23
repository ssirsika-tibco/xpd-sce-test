package com.tibco.xpd.n2.process.globalsignal.precommit;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractActivityPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Precommit listener for Case Signal Events configurations.
 * 
 * @author sajain
 * @since Mar 16, 2015
 */
public class CaseSignalEventPrecommitListener extends
        AbstractActivityPreCommitContributor {

    public CaseSignalEventPrecommitListener() {

    }

    /**
     * Return <code>true</code> if the passed activity is a Case Data event.
     * 
     * @param activity
     *            Activity to be checked.
     * 
     * @return <code>true</code> if the passed activity is a Case Data event.
     */
    private boolean isCaseDataEvent(Activity activity) {

        Event event = activity.getEvent();

        if (event != null) {

            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                Object otherAttribute =
                        Xpdl2ModelUtil.getOtherAttribute(signal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalType());

                if (otherAttribute instanceof SignalType) {

                    SignalType sigType = (SignalType) otherAttribute;

                    return SignalType.CASE_DATA.equals(sigType) ? true : false;
                }
            }
        }

        return false;
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

        TransactionalEditingDomain editingDomain = event.getEditingDomain();

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

            if (activity != null) {

                /*
                 * Sync the label with the name of the signal.
                 */
                synchLabelWithSignalNameChanges(activity,
                        editingDomain,
                        cmd,
                        notification);

            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * <li>Check if the Label of the Catch Events have default value as
     * "Catch Signal event".</li> <li>If the signalName for the Catch Events is
     * set, then the Label should be set to
     * "Catch <Case Ref Field Label> Changed".</li>
     * 
     * @param editingDomain
     * @param cmd
     * @param notifications
     */
    private void synchLabelWithSignalNameChanges(Activity signalActivity,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            Notification notification) {

        String labelName = null;

        if (signalActivity != null && isCaseDataEvent(signalActivity)) {

            String oldLabelName = Xpdl2ModelUtil.getDisplayName(signalActivity);

            String defaultEventName =
                    ElementsFactory.getDefaultNameForEventType(EventObjectUtil
                            .getFlowType(signalActivity), EventObjectUtil
                            .getEventTriggerType(signalActivity));

            /*
             * Checks if 1. The 'SET' event was called 2. If the notifier class
             * is 'TriggerResultSignal' 3. The displayName was changed.
             */

            if ((notification.getNotifier() instanceof TriggerResultSignal)
                    && (Notification.SET == notification.getEventType())) {

                if (Xpdl2Package.eINSTANCE.getTriggerResultSignal_Name()
                        .equals(notification.getFeature())) {

                    if (CatchThrow.CATCH.equals(EventObjectUtil
                            .getCatchThrowType(signalActivity))) {

                        /*
                         * If the Old SignalName is equal to Current Label Name
                         * Or the Current Label name Starts With Catch Signal
                         * Event, then Update LabelName.
                         */
                        String getOldSignalName = null;
                        String oldStringValue =
                                notification.getOldStringValue();

                        if (oldStringValue == null
                                || oldStringValue.length() == 0) {
                            getOldSignalName = defaultEventName;

                        } else {

                            Collection<ProcessRelevantData> allData =
                                    ProcessInterfaceUtil
                                            .getAllDataDefinedInProcess(Xpdl2ModelUtil
                                                    .getProcess(signalActivity));

                            String oldLabel = null;

                            for (ProcessRelevantData eachPRD : allData) {

                                if (oldStringValue.equals(eachPRD.getName())) {

                                    oldLabel =
                                            Xpdl2ModelUtil
                                                    .getDisplayName(eachPRD);
                                }
                            }

                            if (oldLabel != null) {

                                getOldSignalName =
                                        Messages.CaseSignalEventPrecommitListener_CatchGlobalSignalEvent_label
                                                + " " //$NON-NLS-1$
                                                + oldLabel
                                                + " " //$NON-NLS-1$
                                                + Messages.CaseSignalEventPrecommitListener_Changed_label;
                            } else {

                                getOldSignalName = defaultEventName;
                            }

                        }

                        if (oldLabelName.startsWith(getOldSignalName)
                                || oldLabelName.startsWith(defaultEventName)) {

                            String newStringValue =
                                    notification.getNewStringValue();

                            if (newStringValue == null
                                    || newStringValue.length() == 0) {
                                labelName = defaultEventName;
                            } else {

                                Collection<ProcessRelevantData> allData =
                                        ProcessInterfaceUtil
                                                .getAllDataDefinedInProcess(Xpdl2ModelUtil
                                                        .getProcess(signalActivity));

                                String newLabel = null;

                                for (ProcessRelevantData eachPRD : allData) {

                                    if (newStringValue
                                            .equals(eachPRD.getName())) {

                                        newLabel =
                                                Xpdl2ModelUtil
                                                        .getDisplayName(eachPRD);
                                    }
                                }

                                if (newLabel != null) {

                                    labelName =
                                            Messages.CaseSignalEventPrecommitListener_CatchGlobalSignalEvent_label
                                                    + " " //$NON-NLS-1$
                                                    + newLabel
                                                    + " " //$NON-NLS-1$
                                                    + Messages.CaseSignalEventPrecommitListener_Changed_label;
                                } else {

                                    labelName = defaultEventName;
                                }
                            }
                        }
                    }

                } else if (XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_SignalType()
                        .equals(notification.getFeature())) {

                    if (notification.getNewStringValue() != null
                            && !notification.getNewStringValue()
                                    .equals(notification.getOldStringValue())) {

                        if (oldLabelName.startsWith(defaultEventName
                                .substring(0, 5))) {

                            String signalName =
                                    EventObjectUtil
                                            .getSignalName(signalActivity);

                            if (signalName != null && signalName.length() != 0) {
                                /*
                                 * If the Signal Type is changed and we already
                                 * have a new Signal Name then make the new
                                 * label as Catch <Case Ref Field Label>
                                 * Changed.
                                 */

                                Collection<ProcessRelevantData> allData =
                                        ProcessInterfaceUtil
                                                .getAllDataDefinedInProcess(Xpdl2ModelUtil
                                                        .getProcess(signalActivity));

                                String newLabel = null;

                                for (ProcessRelevantData eachPRD : allData) {

                                    if (signalName.equals(eachPRD.getName())) {

                                        newLabel =
                                                Xpdl2ModelUtil
                                                        .getDisplayName(eachPRD);
                                    }
                                }

                                if (newLabel != null) {

                                    if (CatchThrow.CATCH.equals(EventObjectUtil
                                            .getCatchThrowType(signalActivity))) {

                                        labelName =
                                                Messages.CaseSignalEventPrecommitListener_CatchGlobalSignalEvent_label
                                                        + " " //$NON-NLS-1$
                                                        + newLabel
                                                        + " " //$NON-NLS-1$
                                                        + Messages.CaseSignalEventPrecommitListener_Changed_label;

                                    }

                                } else {

                                    labelName = defaultEventName;
                                }

                            } else {

                                /*
                                 * If we do not have a signal name and If the
                                 * old name starts with "Catch" then set back to
                                 * default name.
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
                     * If the Previous LabelName and Name fields have same value
                     * then update the Name field using a Command, else if user
                     * has explicitly set the Name field then do not update the
                     * Name field.
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
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                com.tibco.xpd.ui.util.NameUtil
                                        .getInternalName(newLabelName, false)));
                    }
                }
            }
        }
    }
}

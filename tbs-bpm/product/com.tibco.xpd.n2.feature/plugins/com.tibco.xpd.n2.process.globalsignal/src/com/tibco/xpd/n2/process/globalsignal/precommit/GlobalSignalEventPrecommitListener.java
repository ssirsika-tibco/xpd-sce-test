package com.tibco.xpd.n2.process.globalsignal.precommit;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractActivityPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Precommit listener for Global Signal Events.
 * 
 * @author kthombar
 * @since Jan 30, 2015
 */
public class GlobalSignalEventPrecommitListener extends
        AbstractActivityPreCommitContributor {

    public GlobalSignalEventPrecommitListener() {

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
                 * XPD-8000: Saket: We need <xpdExt:SignalData> to be created
                 * for Global Signal events as it's our ScriptDataMapper
                 * container.
                 */
                if (GlobalSignalUtil.isGlobalSignalEvent(activity)) {
                    setUpSignalData(cmd, editingDomain, activity);
                }

                /*
                 * Add Or Remove event handler initialiser data for valid or
                 * invalid events resp.
                 */
                addRemoveGlobalSignalEventHandlerInitialerData(activity,
                        editingDomain,
                        cmd);

                /*
                 * Sync the label with the name of the signal.
                 */
                synchLabelWithSignalNameChanges(activity,
                        editingDomain,
                        cmd,
                        notification);

                /*
                 * Unset or remove Associated params if they are no longer
                 * applicable
                 */
                unsetOrRemoveAssociatedParameters(activity,
                        editingDomain,
                        cmd,
                        notification);

                /*
                 * Remove the Mappings and Script info if they are no longer
                 * applicable.
                 */
                removeInvalidMappingsAndScriptInfo(activity,
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
     * Set up <xpdExt:SignalData> for the specified signal activity.
     * 
     * @param cmd
     * @param editingDomain
     * @param activity
     */
    private void setUpSignalData(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain, Activity activity) {

        TriggerResultSignal trs =
                (TriggerResultSignal) activity.getEvent()
                        .getEventTriggerTypeNode();

        if (trs != null) {

            Object signalData =
                    Xpdl2ModelUtil.getOtherElement(trs,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SignalData());

            if (signalData == null) {

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                trs,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalData(),
                                XpdExtensionFactory.eINSTANCE
                                        .createSignalData()));
            }
        }
    }

    /**
     * Appends the passed command with the command to remove the Signal Mapping
     * and Script info if the Signal type of the signal event has changed.
     * 
     * @param activity
     * @param editingDomain
     * @param cmd
     * @param notification
     */
    private void removeInvalidMappingsAndScriptInfo(Activity activity,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            Notification notification) {
        if ((notification.getNotifier() instanceof TriggerResultSignal)) {

            /*
             * proceed only when TriggerResultSignal is the notifier
             */

            if (XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalType()
                    .equals(notification.getFeature())) {
                /*
                 * Proceed omly if the signal type has changed
                 */
                if (notification.getNewValue() != null
                        && !notification.getNewValue()
                                .equals(notification.getOldValue())) {
                    /*
                     * Proceed if the old and new signal types are different.
                     */
                    Event event = activity.getEvent();
                    if (event != null) {

                        EObject eventTriggerTypeNode =
                                event.getEventTriggerTypeNode();

                        if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                            TriggerResultSignal signal =
                                    (TriggerResultSignal) eventTriggerTypeNode;

                            /*
                             * get previous mappings
                             */
                            Object otherElement =
                                    Xpdl2ModelUtil
                                            .getOtherElement(signal,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_SignalData());
                            /*
                             * Remove any previous mappings
                             */
                            if (otherElement != null) {
                                cmd.append(Xpdl2ModelUtil
                                        .getRemoveOtherElementCommand(editingDomain,
                                                signal,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_SignalData(),
                                                otherElement));
                            }
                            /*
                             * get previous scripts
                             */
                            Object scriptElement =
                                    Xpdl2ModelUtil.getOtherElement(activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_Script());
                            /*
                             * If the activity has script then remove it after
                             * type is changed.
                             */
                            if (scriptElement != null) {
                                cmd.append(Xpdl2ModelUtil
                                        .getRemoveOtherElementCommand(editingDomain,
                                                activity,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_Script(),
                                                scriptElement));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * <li>Check if the Label of the Throw/Catch Events have default value as
     * "Throw Signal Event" or "Catch Signal event" resp.</li> <li>If the
     * signalName for the Throw/Catch Events is set, Then the Label should be
     * set to "Throw Global: <signalName>" or "Catch Global: <signalName>" resp.
     * </li>
     * 
     * @param editingDomain
     * @param cmd
     * @param notifications
     */
    private void synchLabelWithSignalNameChanges(Activity signalActivity,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            Notification notification) {

        String labelName = null;

        if (signalActivity != null
                && GlobalSignalUtil.isGlobalSignalEvent(signalActivity)) {

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

                    /*
                     * If the Old SignalName is equal to Current Label Name Or
                     * the Current Label name Starts With Throw Signal Event
                     * Then Update LabelName, i.e. Set SignalName as Label Name
                     * by appending a 'Throw:' keyword in the Label name.
                     */

                    if (CatchThrow.THROW.equals(EventObjectUtil
                            .getCatchThrowType(signalActivity))) {
                        String getOldSignalName = null;

                        String oldStringValue =
                                notification.getOldStringValue();

                        if (oldStringValue == null
                                || oldStringValue.length() == 0) {
                            getOldSignalName = defaultEventName;

                        } else {
                            int indexOfHash = oldStringValue.indexOf("#"); //$NON-NLS-1$
                            if (indexOfHash != -1) {
                                getOldSignalName =
                                        Messages.GlobalSignalEventPrecommitListener_ThrowGlobalSignalEvent_label
                                                + " " //$NON-NLS-1$
                                                + oldStringValue
                                                        .substring(indexOfHash + 1,
                                                                oldStringValue
                                                                        .length());

                            } else {
                                getOldSignalName = defaultEventName;
                            }
                        }
                        /*
                         * Checks if old labelName starts with oldSignalName or
                         * old labelName starts with DefaultEvent Name If so,
                         * then Set new signalName as new labelName
                         */

                        if (oldLabelName.startsWith(getOldSignalName)
                                || oldLabelName.startsWith(defaultEventName)) {

                            String newStringValue =
                                    notification.getNewStringValue();

                            if (newStringValue == null
                                    || newStringValue.length() == 0) {
                                labelName = defaultEventName;

                            } else {
                                int indexOfHash = newStringValue.indexOf("#"); //$NON-NLS-1$
                                if (indexOfHash != -1) {
                                    labelName =
                                            Messages.GlobalSignalEventPrecommitListener_ThrowGlobalSignalEvent_label
                                                    + " " //$NON-NLS-1$
                                                    + newStringValue
                                                            .substring(indexOfHash + 1,
                                                                    newStringValue
                                                                            .length());
                                } else {
                                    labelName = defaultEventName;
                                }
                            }
                        }
                    }
                    /*
                     * If the Old SignalName is equal to Current Label Name Or
                     * the Current Label name Starts With Catch Signal Event
                     * Then Update LabelName, i.e. Set SignalName as Label Name
                     * by appending a 'Catch:' keyword in the Label name.
                     */else {
                        String getOldSignalName = null;
                        String oldStringValue =
                                notification.getOldStringValue();

                        if (oldStringValue == null
                                || oldStringValue.length() == 0) {
                            getOldSignalName = defaultEventName;

                        } else {
                            int indexOfHash = oldStringValue.indexOf("#"); //$NON-NLS-1$
                            if (indexOfHash != -1) {
                                getOldSignalName =
                                        Messages.GlobalSignalEventPrecommitListener_CatchGlobalSignalEvent_label
                                                + " " //$NON-NLS-1$
                                                + oldStringValue
                                                        .substring(indexOfHash + 1,
                                                                oldStringValue
                                                                        .length());
                                ;
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
                                int indexOfHash = newStringValue.indexOf("#"); //$NON-NLS-1$
                                if (indexOfHash != -1) {
                                    labelName =
                                            Messages.GlobalSignalEventPrecommitListener_CatchGlobalSignalEvent_label
                                                    + " " //$NON-NLS-1$
                                                    + newStringValue
                                                            .substring(indexOfHash + 1,
                                                                    newStringValue
                                                                            .length());
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
                                 * label as Catch Global: signalName or Throw
                                 * Global: signalName
                                 */
                                int indexOfHash = signalName.indexOf("#"); //$NON-NLS-1$
                                if (indexOfHash != -1) {

                                    if (CatchThrow.THROW.equals(EventObjectUtil
                                            .getCatchThrowType(signalActivity))) {
                                        labelName =
                                                Messages.GlobalSignalEventPrecommitListener_ThrowGlobalSignalEvent_label
                                                        + " " //$NON-NLS-1$
                                                        + signalName
                                                                .substring(indexOfHash + 1,
                                                                        signalName
                                                                                .length());
                                    } else {
                                        labelName =
                                                Messages.GlobalSignalEventPrecommitListener_CatchGlobalSignalEvent_label
                                                        + " " //$NON-NLS-1$
                                                        + signalName
                                                                .substring(indexOfHash + 1,
                                                                        signalName
                                                                                .length());
                                    }
                                }
                            } else {
                                /*
                                 * If we do not have a signal name and If the
                                 * old name starts with "Throw" or "Catch" then
                                 * set back to default name.
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

    /**
     * Populates the passed command to add or remove the global signal event
     * handler related model elements/attributes.
     * 
     * @param activity
     *            the activity
     * @param editingDomain
     * @param cmd
     */
    private void addRemoveGlobalSignalEventHandlerInitialerData(
            Activity activity, TransactionalEditingDomain editingDomain,
            CompoundCommand cmd) {

        boolean isEventHandlerActivity =
                Xpdl2ModelUtil.isEventHandlerActivity(activity);

        /*
         * Event handler controls are applicable for global/case catch signal
         * event handlers and global/case start signal events in event sub
         * process.
         */
        if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(EventObjectUtil
                .getEventTriggerType(activity))) {

            if ((isEventHandlerActivity || EventObjectUtil
                    .isEventSubProcessStartEvent(activity))
                    && (GlobalSignalUtil.isGlobalSignalEvent(activity) || isCaseDataEvent(activity))) {
                /*
                 * For Global/case Signals add flow strategy.
                 */
                addFlowStrategy(activity, editingDomain, cmd);
            } else {
                /*
                 * If the signal is no longer a global signal then remove the
                 * flow strategy and Initilaizer Activities as they are not
                 * applicable.
                 */
                removeFlowStrategy(activity, editingDomain, cmd);
            }

            if ((isEventHandlerActivity || EventObjectUtil
                    .isEventSubProcessStartEvent(activity))
                    && (!GlobalSignalUtil.isGlobalSignalEvent(activity))) {

                removeInitializerActivities(activity, editingDomain, cmd);
            }
        }
    }

    /**
     * Appends the passed command with a command to remove the initializer
     * activities set for the specified signal event.
     * 
     * @param activity
     * @param editingDomain
     * @param cmd
     */
    private void removeInitializerActivities(Activity activity,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {

        if (activity.getEvent() != null
                && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

            TriggerResultSignal triggerResultSignal =
                    (TriggerResultSignal) activity.getEvent()
                            .getEventTriggerTypeNode();

            EventHandlerInitialisers evtHdlInitialisers =
                    (EventHandlerInitialisers) Xpdl2ModelUtil
                            .getOtherElement(triggerResultSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_EventHandlerInitialisers());

            if (evtHdlInitialisers != null) {
                cmd.append(Xpdl2ModelUtil
                        .getRemoveOtherElementCommand(editingDomain,
                                triggerResultSignal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_EventHandlerInitialisers(),
                                evtHdlInitialisers));
            }
        }

    }

    /**
     * Appends the passed command with command that removes OR Unsets the
     * Associated Parameters with the Signal Event.
     * 
     * @param activity
     * @param editingDomain
     * @param cmd
     * @param notification
     */
    private void unsetOrRemoveAssociatedParameters(Activity activity,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            Notification notification) {

        if ((notification.getNotifier() instanceof TriggerResultSignal)) {

            /*
             * proceed only when TriggerResultSignal is the notifier
             */

            if (XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalType()
                    .equals(notification.getFeature())) {
                /*
                 * Proceed only if the signal type has changed
                 */

                if (!(GlobalSignalUtil.isGlobalSignalEvent(activity) || isCaseDataEvent(activity))) {
                    /*
                     * remove or unset associated params only for non
                     * global/case signals
                     */
                    EventTriggerType eventTriggerType =
                            EventObjectUtil.getEventTriggerType(activity);

                    Object assoParam =
                            Xpdl2ModelUtil
                                    .getOtherElement(activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedParameters());

                    if (assoParam instanceof AssociatedParameters) {

                        if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                .equals(eventTriggerType)) {

                            Event event = activity.getEvent();
                            if (event instanceof StartEvent) {
                                /*
                                 * For non global start signal events remove the
                                 * Associated Params because when we create a
                                 * fresh new start signal event they too dont
                                 * have Associated params.
                                 */
                                cmd.append(Xpdl2ModelUtil
                                        .getRemoveOtherElementCommand(editingDomain,
                                                activity,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_AssociatedParameters(),
                                                assoParam));

                            }

                        } else if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                                .equals(eventTriggerType)) {

                            AssociatedParameters associatedParameters =
                                    (AssociatedParameters) assoParam;

                            if (!associatedParameters
                                    .isDisableImplicitAssociation()) {

                                /*
                                 * For non global throw signal events set
                                 * disable implicit association to true because
                                 * when we create a fresh new throw signal event
                                 * they too have the same configuration.
                                 */
                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                associatedParameters,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getAssociatedParameters_DisableImplicitAssociation(),
                                                Boolean.TRUE));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Appends the passed command with commands that remove the flow strategy
     * and event handler initialiser activities.
     * 
     * @param activity
     * @param editingDomain
     * @param cmd
     */
    private void removeFlowStrategy(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {

        if (activity.getEvent() != null
                && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

            TriggerResultSignal triggerResultSignal =
                    (TriggerResultSignal) activity.getEvent()
                            .getEventTriggerTypeNode();

            EventHandlerFlowStrategy flowStrategy =
                    (EventHandlerFlowStrategy) Xpdl2ModelUtil
                            .getOtherAttribute(triggerResultSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_EventHandlerFlowStrategy());
            if (flowStrategy != null) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                triggerResultSignal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_EventHandlerFlowStrategy(),
                                null));
            }
        }
    }

    /**
     * Adds flow strategy to the activity to the catch global Signal Events.
     * 
     * @param activity
     * @param editingDomain
     * @param cmd
     */
    private void addFlowStrategy(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {

        if (activity.getEvent() != null
                && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

            /*
             * Get the event trigger type node.
             */
            TriggerResultSignal triggerResultSignal =
                    (TriggerResultSignal) activity.getEvent()
                            .getEventTriggerTypeNode();

            /*
             * Get the event flow strategy.
             */
            EventHandlerFlowStrategy flowStrategy =
                    (EventHandlerFlowStrategy) Xpdl2ModelUtil
                            .getOtherAttribute(triggerResultSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_EventHandlerFlowStrategy());

            /*
             * If the flow strategy isn't there yet, then add it.
             */
            if (flowStrategy == null) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                triggerResultSignal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_EventHandlerFlowStrategy(),
                                EventHandlerFlowStrategy.SERIALIZE_CONCURRENT));
            }
        }
    }
}

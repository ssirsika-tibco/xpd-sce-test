/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.SimpleFeatureMapEntry;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit listener that will run any tidy-up required when a Business
 * Process is converted to a Page Flow process.
 * 
 * @author njpatel
 */
public class BusinessProcessToPageFlowConvertPreCommitListener implements
        IProcessPreCommitContributor {

    public BusinessProcessToPageFlowConvertPreCommitListener() {
    }

    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        boolean isPageFlowConversion = false;
        Process process = null;
        for (ENotificationImpl notification : notifications) {
            if (isConversionToPageFlowNotification(notification)) {
                process = (Process) notification.getNotifier();
                isPageFlowConversion = true;
                break;
            }
        }

        if (isPageFlowConversion) {
            /*
             * Get all the commands to tidy up this model.
             */
            List<Command> commands =
                    contributeCommands(event.getEditingDomain(),
                            process,
                            event,
                            notifications);

            if (!commands.isEmpty()) {
                return commands.size() == 1 ? commands.get(0)
                        : new CompoundCommand(commands);
            }
        }

        return null;
    }

    /**
     * Get the commands to tidy this model up.
     * 
     * @param ed
     * @param process
     * @param event
     * @param notifications
     * @return
     */
    private List<Command> contributeCommands(TransactionalEditingDomain ed,
            Process process, ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications) {
        List<Command> commands = new ArrayList<Command>();

        /*
         * Remove any calendar reference elements from the model
         */
        commands.addAll(getRemoveAllCalendarReferencesCommand(ed, process));

        return commands;
    }

    /**
     * Commands to remove all calendar reference elements from the process.
     * 
     * @param ed
     * 
     * @param process
     * @return
     */
    private List<Command> getRemoveAllCalendarReferencesCommand(
            TransactionalEditingDomain ed, Process process) {
        List<Command> commands = new ArrayList<Command>();

        // Remove calendar reference from process
        CalendarReference ref = getCalendarReference(process);
        if (ref != null) {
            commands.add(getRemoveCalendarReferenceCommand(ed, process, ref));
        }

        /*
         * Remove calendar references from all timer events
         */
        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            EventTriggerType triggerType =
                    EventObjectUtil.getEventTriggerType(activity);

            if (triggerType == EventTriggerType.EVENT_TIMER_LITERAL) {
                TriggerTimer triggerTimer =
                        EventObjectUtil.getTriggerTimer(activity);
                if (triggerTimer != null) {
                    ref = getCalendarReference(triggerTimer);
                    if (ref != null) {
                        commands.add(getRemoveCalendarReferenceCommand(ed,
                                triggerTimer,
                                ref));
                    }
                }
            }
        }

        return commands;
    }

    /**
     * Get the command to remove the given {@link CalendarReference} from the
     * container.
     * 
     * @param ed
     * @param container
     * @param ref
     * @return
     */
    private Command getRemoveCalendarReferenceCommand(
            TransactionalEditingDomain ed, OtherElementsContainer container,
            CalendarReference ref) {
        return Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                container,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_CalendarReference(),
                ref);
    }

    /**
     * Get the {@link CalendarReference} from the given container.
     * 
     * @param container
     * @return
     */
    private CalendarReference getCalendarReference(
            OtherElementsContainer container) {
        if (container != null) {
            Object element =
                    Xpdl2ModelUtil.getOtherElement(container,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CalendarReference());

            return (CalendarReference) (element instanceof CalendarReference ? element
                    : null);
        }
        return null;
    }

    /**
     * Check if this notification is the addition of the page flow model type
     * extended attribute to a Process.
     * 
     * @param notification
     * @return
     */
    private boolean isConversionToPageFlowNotification(
            ENotificationImpl notification) {
        if (notification.getEventType() == Notification.ADD
                && notification.getNotifier() instanceof Process
                && notification.getFeature() == Xpdl2Package.eINSTANCE
                        .getOtherAttributesContainer_OtherAttributes()) {

            if (notification.getNewValue() instanceof SimpleFeatureMapEntry) {
                SimpleFeatureMapEntry mapEntry =
                        (SimpleFeatureMapEntry) notification.getNewValue();

                if (mapEntry.getEStructuralFeature() == XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_XpdModelType()
                        && mapEntry.getValue() == XpdModelType.PAGE_FLOW) {

                    return true;

                }
            }
        }
        return false;
    }

}

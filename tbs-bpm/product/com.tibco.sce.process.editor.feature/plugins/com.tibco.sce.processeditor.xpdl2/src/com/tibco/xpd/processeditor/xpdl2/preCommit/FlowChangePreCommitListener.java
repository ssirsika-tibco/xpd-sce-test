/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ValidationControlUtil;
import com.tibco.xpd.xpdExtension.ValidationControl;
import com.tibco.xpd.xpdExtension.ValidationIssueOverride;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Handles maintenance of the XPDL model for things that need to happen when any
 * aspect of a process' flow changes.
 * <p>
 * For now this means specifically handling removal of
 * {@link ValidationIssueOverride} elements set to
 * {@link ValidationIssueOverrideType#SUPPRESS_UNTIL_NEXT_FLOW_CHANGE} when we
 * detect that the flow changes.
 * 
 * @author aallway
 * @since 18 Jul 2012
 */
public class FlowChangePreCommitListener extends
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
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        /*
         * Create set of processes whose flow has been changed by the given edit
         * notifications.
         */
        Set<Process> flowChangeProcesses = new HashSet<Process>();
        Package pkg = null;

        for (ENotificationImpl notification : notifications) {

            Process process = isProcessFlowChange(notification);

            if (process != null) {
                flowChangeProcesses.add(process);

                if (process.getPackage() != null) {
                    pkg = process.getPackage();
                }
            }
        }

        if (!flowChangeProcesses.isEmpty()) {
            CompoundCommand cmd = new CompoundCommand();

            for (Process process : flowChangeProcesses) {
                /*
                 * Remove any validation issue removal of
                 * ValidationIssueOverride elements set to
                 * SUPPRESS_UNTIL_NEXT_FLOW_CHANGE
                 */
                removeFlowChangeValidationIssueOverrides(event.getEditingDomain(),
                        cmd,
                        process);
            }

            /*
             * If there is a "until flow-change suppression" on the package then
             * remove that if any process flow change occurs
             */
            if (pkg != null) {
                appendFlowChangeValidationIssueOverridesCommands(event.getEditingDomain(),
                        cmd,
                        pkg);
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }

        return null;
    }

    /**
     * Check if the given notification indicates a change to the process flow.
     * 
     * @param notification
     * 
     * @return {@link Process} if the notification indicates a flow change or
     *         <code>null</code> if not.
     */
    private Process isProcessFlowChange(ENotificationImpl notification) {
        /* The process affected by change. */
        Process process =
                (Process) getTypedAncestor(notification, Process.class);

        if (process != null) {
            int eventType = notification.getEventType();
            Object feature = notification.getFeature();

            /* Check for add one or more activities / flows. */
            if (Notification.ADD == eventType
                    || Notification.ADD_MANY == eventType) {
                if (Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                        .equals(feature)) {
                    return process;

                } else if (Xpdl2Package.eINSTANCE
                        .getFlowContainer_Transitions().equals(feature)) {
                    return process;

                }
            }
            /* Check for remove one or more activities / flows. */
            else if (Notification.REMOVE == eventType
                    || Notification.REMOVE_MANY == eventType) {
                if (Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                        .equals(feature)) {
                    return process;

                } else if (Xpdl2Package.eINSTANCE
                        .getFlowContainer_Transitions().equals(feature)) {
                    return process;

                }
            }
            /* Check for re-connect of a flow */
            else if (Notification.SET == eventType
                    && (Xpdl2Package.eINSTANCE.getTransition_To()
                            .equals(feature) || Xpdl2Package.eINSTANCE
                            .getTransition_From().equals(feature))) {
                return process;
            }
            /* Check for flow type change */
            else if (Notification.SET == eventType
                    && (Xpdl2Package.eINSTANCE.getTransition_Condition()
                            .equals(feature))) {
                return process;
            }
            /* Check for gateway type change */
            else if (Notification.SET == eventType
                    && (Xpdl2Package.eINSTANCE.getActivity_Route()
                            .equals(feature)
                            || Xpdl2Package.eINSTANCE.getRoute_ExclusiveType()
                                    .equals(feature) || Xpdl2Package.eINSTANCE
                            .getRoute_GatewayType().equals(feature))) {
                return process;
            }
            /* Check event attached / detached from task boundary */
            else if (Notification.SET == eventType
                    && (Xpdl2Package.eINSTANCE.getIntermediateEvent_Target()
                            .equals(feature))) {
                return process;
            }

        }

        return null;
    }

    /**
     * Append commands to remove any validation issue removal of
     * ValidationIssueOverride elements set to SUPPRESS_UNTIL_NEXT_FLOW_CHANGE
     * 
     * @param editingDomain
     * @param cmd
     * @param process
     */
    private void removeFlowChangeValidationIssueOverrides(
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            Process process) {
        /*
         * Remove any from process itself
         */
        appendFlowChangeValidationIssueOverridesCommands(editingDomain,
                cmd,
                process);

        /*
         * Then from any activities.
         */
        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            appendFlowChangeValidationIssueOverridesCommands(editingDomain,
                    cmd,
                    activity);
        }

    }

    /**
     * Append commands to remove any validation issue removal of
     * ValidationIssueOverride elements set to SUPPRESS_UNTIL_NEXT_FLOW_CHANGE
     * 
     * @param editingDomain
     * @param cmd
     * @param process
     */
    private void appendFlowChangeValidationIssueOverridesCommands(
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            OtherElementsContainer container) {

        ValidationControl validationControl =
                ValidationControlUtil.getValidationControl(container);

        if (validationControl != null) {
            Set<ValidationIssueOverride> toRemove =
                    new HashSet<ValidationIssueOverride>();

            for (Iterator iterator =
                    validationControl.getValidationIssueOverrides().iterator(); iterator
                    .hasNext();) {

                ValidationIssueOverride issueOverride =
                        (ValidationIssueOverride) iterator.next();

                if (ValidationIssueOverrideType.SUPPRESS_UNTIL_NEXT_FLOW_CHANGE
                        .equals(issueOverride.getOverrideType())) {
                    toRemove.add(issueOverride);
                }
            }

            if (!toRemove.isEmpty()) {
                RemoveCommand removeCmd =
                        new RemoveCommand(
                                editingDomain,
                                validationControl,
                                XpdExtensionPackage.eINSTANCE
                                        .getValidationControl_ValidationIssueOverrides(),
                                toRemove);

                cmd.append(removeCmd);
            }
        }

    }

}

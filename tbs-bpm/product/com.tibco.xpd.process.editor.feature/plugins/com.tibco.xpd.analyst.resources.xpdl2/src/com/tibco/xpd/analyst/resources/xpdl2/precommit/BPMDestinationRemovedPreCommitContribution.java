/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.precommit;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.resources.AbstractDestinationChangedPreCommitContribution;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Although technically this class should be in the N2 (BPM destination) feature
 * the fact is that right now, there is UI in nbase process editor features that
 * is BPM specific.
 * <p>
 * This class clears up any model elements defined by the BPM specific UI's that
 * do not appear unless BPOM destination is selected (so that we don't leave
 * them lying around when there is no UI to unset them etc).
 * 
 * @author aallway
 * @since 24 Aug 2012
 */
public class BPMDestinationRemovedPreCommitContribution extends
        AbstractDestinationChangedPreCommitContribution {

    /**
     * @see com.tibco.xpd.xpdl2.resources.AbstractDestinationChangedPreCommitContribution#getDestinationAddedCommand(com.tibco.xpd.xpdl2.Process,
     *      java.lang.String)
     * 
     * @param editingDomain
     * @param process
     * @param globalDestinationId
     * @return
     */
    @Override
    protected Command getDestinationAddedCommand(EditingDomain editingDomain,
            Process process, String globalDestinationId) {
        if (ProcessDestinationUtil.BPM_DESTINATION_ID
                .equals(globalDestinationId)) {
            CompoundCommand cmd = new CompoundCommand();

            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {

                getDestAddedActivityCommands(editingDomain, activity, cmd);

            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }
        return null;
    }

    /**
     * Append Commands needed to clean up activity when BPM destination added.
     * 
     * @param editingDomain
     * @param activity
     * @param cmd
     */
    private void getDestAddedActivityCommands(EditingDomain editingDomain,
            Activity activity, CompoundCommand cmd) {
        /* Handle sub-process task. */
        if (activity.getImplementation() instanceof SubFlow) {
            SubFlow subFlow = (SubFlow) activity.getImplementation();

            Object startStrategy =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_StartStrategy());

            if (startStrategy == null) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_StartStrategy(),
                                SubProcessStartStrategy.START_IMMEDIATELY));
            }

            Object suspendWithParent =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SuspendResumeWithParent());

            if (suspendWithParent == null) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SuspendResumeWithParent(),
                                Boolean.TRUE));
            }

        }

    }

    /**
     * @see com.tibco.xpd.xpdl2.resources.AbstractDestinationChangedPreCommitContribution#getDestinationRemovedCommand(com.tibco.xpd.xpdl2.Process,
     *      java.lang.String)
     * 
     * @param editingDomain
     * @param process
     * @param globalDestinationId
     * @return
     */
    @Override
    protected Command getDestinationRemovedCommand(EditingDomain editingDomain,
            Process process, String globalDestinationId) {
        if (ProcessDestinationUtil.BPM_DESTINATION_ID
                .equals(globalDestinationId)) {
            CompoundCommand cmd = new CompoundCommand();

            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {
                Command c =
                        getDestRemovedActivityCommands(editingDomain, activity);

                if (c != null) {
                    cmd.append(c);
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }
        return null;
    }

    /**
     * @param activity
     * @param editingDomain
     * @return Commands needed to clean up activity when BPM destination
     *         removed.
     */
    private Command getDestRemovedActivityCommands(EditingDomain editingDomain,
            Activity activity) {
        CompoundCommand cmd = new CompoundCommand();

        /* Handle event activities */
        if (activity.getEvent() != null) {

            if (activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {
                getDestRemovedMessageEventCommands(editingDomain,
                        activity,
                        (TriggerResultMessage) activity.getEvent()
                                .getEventTriggerTypeNode(),
                        cmd);
            }
        }

        /* Handle sub-process task. */
        else if (activity.getImplementation() instanceof SubFlow) {
            SubFlow subFlow = (SubFlow) activity.getImplementation();

            Object startStrategy =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_StartStrategy());

            if (startStrategy != null) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_StartStrategy(),
                                null));
            }

            Object suspendWithParent =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SuspendResumeWithParent());

            if (suspendWithParent != null) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SuspendResumeWithParent(),
                                null));
            }

        }

        /* Handle xpdExt:ActivityDeadlineEventId attribute */
        if (Xpdl2ModelUtil.getOtherAttribute(activity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ActivityDeadlineEventId()) != null) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ActivityDeadlineEventId(),
                            null));
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * append commands to clean up message events when BPM dest removed.
     * 
     * @param editingDomain
     * @param activity
     * @param trm
     * @param cmd
     */
    protected void getDestRemovedMessageEventCommands(
            EditingDomain editingDomain, Activity activity,
            TriggerResultMessage trm, CompoundCommand cmd) {

        /*
         * remove reply immediately model elements
         */
        Object replyImmediately =
                Xpdl2ModelUtil.getOtherAttribute(trm,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ReplyImmediately());

        if (replyImmediately != null) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            trm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ReplyImmediately(),
                            null));
        }

        if (trm.getMessage() != null) {
            Object replyMappings =
                    Xpdl2ModelUtil
                            .getOtherElement(trm.getMessage(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ReplyImmediateDataMappings());

            if (replyMappings != null) {
                cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(editingDomain,
                        trm.getMessage(),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ReplyImmediateDataMappings(),
                        replyMappings));
            }
        }

        /*
         * Remove Event handler model elements.
         */
        Object flowStrategy =
                Xpdl2ModelUtil.getOtherAttribute(trm,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_EventHandlerFlowStrategy());

        if (flowStrategy != null) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            trm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_EventHandlerFlowStrategy(),
                            null));
        }

        Object eventHandlerInitialisers =
                Xpdl2ModelUtil.getOtherElement(trm,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_EventHandlerInitialisers());

        if (eventHandlerInitialisers != null) {
            cmd.append(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(editingDomain,
                            trm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_EventHandlerInitialisers(),
                            eventHandlerInitialisers));
        }
    }
}

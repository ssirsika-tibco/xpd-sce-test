/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.precommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.AssociationDirectionType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit listener for general fix-ups of XPDL models to attempt to ensure
 * validation against XPDL schema.
 * 
 * @author aallway
 * @since 3.3 (27 Jan 2010)
 */
public class ValidateXpdlStructurePreCommitContribution extends
        AbstractProcessPreCommitContributor {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand
     * (org.eclipse.emf.transaction.ResourceSetChangeEvent,
     * java.util.Collection)
     */
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {
        CompoundCommand cmd = null;

        Set<Package> packagesToCheck = new HashSet<Package>();

        for (ENotificationImpl notification : notifications) {
            EObject o = getTypedAncestor(notification, Package.class);
            if (o instanceof Package) {
                packagesToCheck.add((Package) o);
            }
        }

        TransactionalEditingDomain editingDomain = event.getEditingDomain();

        for (Package pkg : packagesToCheck) {
            for (Association association : pkg.getAssociations()) {
                cmd =
                        appendOrCreateCommand(cmd,
                                validateAssociation(association, editingDomain));
            }

            for (Process process : pkg.getProcesses()) {
                cmd =
                        appendOrCreateCommand(cmd, validateProcess(process,
                                editingDomain));
            }
        }

        return cmd;
    }

    /**
     * Validate the configuration of the association.
     * 
     * @param association
     * @param editingDomain
     * 
     * @return Command to fix association and related activities or null if no
     *         changes required.
     */
    private Command validateAssociation(Association association,
            TransactionalEditingDomain editingDomain) {
        CompoundCommand cmd = null;

        Process process = Xpdl2ModelUtil.getProcess(association);
        if (process != null) {
            /*
             * Association between 2 activities is only valid if it is a
             * compensation.
             * 
             * If it is a compensation it's direction must be in the To
             * direction and the source must be the compensation event activity.
             * 
             * Also the target activity must have the isCompensation flag set.
             */
            Activity sourceActivity =
                    Xpdl2ModelUtil.getActivityById(process, association
                            .getSource());
            Activity targetActivity =
                    Xpdl2ModelUtil.getActivityById(process, association
                            .getTarget());

            if (sourceActivity != null && targetActivity != null) {
                /*
                 * Source must be the compensation event.
                 */
                Event targetEvent = targetActivity.getEvent();
                Event sourceEvent = sourceActivity.getEvent();
                if ((targetEvent instanceof IntermediateEvent && TriggerType.COMPENSATION_LITERAL
                        .equals(((IntermediateEvent) targetEvent).getTrigger()))
                        && (!(sourceEvent instanceof IntermediateEvent) || !TriggerType.COMPENSATION_LITERAL
                                .equals(((IntermediateEvent) targetEvent)
                                        .getTrigger()))) {

                    /*
                     * The Target is a compensation activity and the source is
                     * not - so SWAP them
                     */
                    Activity tmp = sourceActivity;
                    sourceActivity = targetActivity;
                    targetActivity = tmp;
                    targetEvent = targetActivity.getEvent();
                    sourceEvent = sourceActivity.getEvent();

                    cmd =
                            appendOrCreateCommand(cmd, SetCommand
                                    .create(editingDomain,
                                            association,
                                            Xpdl2Package.eINSTANCE
                                                    .getAssociation_Source(),
                                            sourceActivity.getId()));
                    cmd =
                            appendOrCreateCommand(cmd, SetCommand
                                    .create(editingDomain,
                                            association,
                                            Xpdl2Package.eINSTANCE
                                                    .getAssociation_Target(),
                                            targetActivity.getId()));
                }

                /*
                 * If the target of association hasn't got the isForCompensation
                 * flag set it should have.
                 */
                if (!targetActivity.isSetIsForCompensation()
                        || !targetActivity.isIsForCompensation()) {
                    cmd =
                            appendOrCreateCommand(cmd,
                                    SetCommand
                                            .create(editingDomain,
                                                    targetActivity,
                                                    Xpdl2Package.eINSTANCE
                                                            .getActivity_IsForCompensation(),
                                                    Boolean.TRUE));
                }

                /*
                 * Direction must be To.
                 */
                if (!AssociationDirectionType.TO_LITERAL.equals(association
                        .getAssociationDirection())) {
                    cmd =
                            appendOrCreateCommand(cmd,
                                    SetCommand
                                            .create(editingDomain,
                                                    association,
                                                    Xpdl2Package.eINSTANCE
                                                            .getAssociation_AssociationDirection(),
                                                    AssociationDirectionType.TO_LITERAL));
                }
            }
        }

        return cmd;
    }

    /**
     * Validate the process and its contents.
     * 
     * @param process
     * @param transactionalEditingDomain
     * 
     * @return Command to fix process and its contents or null if no changes
     *         required.
     */
    private Command validateProcess(Process process,
            TransactionalEditingDomain editingDomain) {
        CompoundCommand cmd = null;

        /*
         * Make sure process still part of model.
         */
        if (Xpdl2ModelUtil.getPackage(process) != null) {
            /*
             * Validate the process itself.
             */
            cmd =
                    appendOrCreateCommand(cmd,
                            validateProcessStructure(process, editingDomain));

            /*
             * Then the activities.
             */
            for (Activity activity : process.getActivities()) {
                cmd =
                        appendOrCreateCommand(cmd,
                                validateActivityStructure(activity,
                                        editingDomain));
            }

            for (ActivitySet activitySet : process.getActivitySets()) {
                for (Activity activity : activitySet.getActivities()) {
                    cmd =
                            appendOrCreateCommand(cmd,
                                    validateActivityStructure(activity,
                                            editingDomain));
                }
            }
        }

        return cmd;
    }

    /**
     * Validate the process structure and return command to fix it if it wasn't
     * valid.
     * 
     * @param process
     * @param editingDomain
     * 
     * @return Command to fix process or null if no changes required.
     */
    private Command validateProcessStructure(Process process,
            TransactionalEditingDomain editingDomain) {
        CompoundCommand cmd = null;

        /*
         * Because of a peculiarity of XPDL model, if activity has ##other
         * elements then it MUST have an xpdl2:Extensions element
         */
        if (!process.getOtherElements().isEmpty()) {
            if (process.getExtensions() == null) {

                cmd =
                        appendOrCreateCommand(cmd, SetCommand
                                .create(editingDomain,
                                        process,
                                        Xpdl2Package.eINSTANCE
                                                .getProcess_Extensions(),
                                        EcoreFactory.eINSTANCE.createEObject()));
            }
        }

        /*
         * Sometimes Process is created without process header, if so, add one.
         */
        if (process.getProcessHeader() == null) {
            cmd =
                    appendOrCreateCommand(cmd, SetCommand.create(editingDomain,
                            process,
                            Xpdl2Package.eINSTANCE.getProcess_ProcessHeader(),
                            Xpdl2Factory.eINSTANCE.createProcessHeader()));
        }
        return cmd;
    }

    /**
     * Validate the activity and return command to fix it if it wasn't valid.
     * 
     * @param activity
     * @param transactionalEditingDomain
     * @return Command to fix activity or null if no changes required.
     */
    private Command validateActivityStructure(Activity activity,
            TransactionalEditingDomain editingDomain) {
        CompoundCommand cmd = null;

        /*
         * Because of a peculiarity of XPDL model, if activity has ##other
         * elements then it MUST have an xpdl2:Extensions element
         */
        if (!activity.getOtherElements().isEmpty()) {
            if (activity.getExtensions() == null) {
                cmd =
                        appendOrCreateCommand(cmd, SetCommand
                                .create(editingDomain,
                                        activity,
                                        Xpdl2Package.eINSTANCE
                                                .getActivity_Extensions(),
                                        EcoreFactory.eINSTANCE.createEObject()));
            }
        }

        /*
         * Any WebServiceOperation element when defined must have an operation
         * name even if it's ""
         * 
         * Also xpdl2:Service needs ServiceName and PortName
         */
        WebServiceOperation wso =
                Xpdl2ModelUtil.getWebServiceOperation(activity);
        if (wso != null) {
            if (!wso.eIsSet(Xpdl2Package.eINSTANCE
                    .getWebServiceOperation_OperationName())) {
                cmd =
                        appendOrCreateCommand(cmd,
                                SetCommand
                                        .create(editingDomain,
                                                wso,
                                                Xpdl2Package.eINSTANCE
                                                        .getWebServiceOperation_OperationName(),
                                                "")); //$NON-NLS-1$
            }

            Service service = wso.getService();
            if (service != null) {
                if (!service.eIsSet(Xpdl2Package.eINSTANCE
                        .getService_ServiceName())) {
                    cmd =
                            appendOrCreateCommand(cmd, SetCommand
                                    .create(editingDomain,
                                            service,
                                            Xpdl2Package.eINSTANCE
                                                    .getService_ServiceName(),
                                            "")); //$NON-NLS-1$
                }

                if (!service.eIsSet(Xpdl2Package.eINSTANCE
                        .getService_PortName())) {
                    cmd =
                            appendOrCreateCommand(cmd, SetCommand
                                    .create(editingDomain,
                                            service,
                                            Xpdl2Package.eINSTANCE
                                                    .getService_PortName(),
                                            "")); //$NON-NLS-1$
                }
            }
        }

        /*
         * TriggerResultSignal must have at least one xpdl2:Properties element
         */
        if (activity.getEvent() != null) {
            EObject eventTriggerTypeNode =
                    activity.getEvent().getEventTriggerTypeNode();
            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                if (signal.getProperties().isEmpty()) {
                    cmd =
                            appendOrCreateCommand(cmd,
                                    AddCommand
                                            .create(editingDomain,
                                                    signal,
                                                    Xpdl2Package.eINSTANCE
                                                            .getTriggerResultSignal_Properties(),
                                                    Xpdl2Factory.eINSTANCE
                                                            .createExpression()));
                }
            }
        }

        /*
         * If activity has isForCompensation set make sure that there is an
         * incoming compensation association.
         */
        if (activity.isSetIsForCompensation() && activity.isIsForCompensation()) {
            boolean foundCompensationAssociation = false;

            EList<Association> incomingAssociations =
                    activity.getIncomingAssociations();

            for (Association association : incomingAssociations) {
                Activity sourceAct =
                        Xpdl2ModelUtil.getActivityById(activity.getProcess(),
                                association.getSource());
                if (sourceAct != null
                        && sourceAct.getEvent() instanceof IntermediateEvent) {
                    if (TriggerType.COMPENSATION_LITERAL
                            .equals(((IntermediateEvent) sourceAct.getEvent())
                                    .getTrigger())) {
                        foundCompensationAssociation = true;
                        break;
                    }
                }
            }

            if (!foundCompensationAssociation) {
                cmd =
                        appendOrCreateCommand(cmd,
                                SetCommand
                                        .create(editingDomain,
                                                activity,
                                                Xpdl2Package.eINSTANCE
                                                        .getActivity_IsForCompensation(),
                                                SetCommand.UNSET_VALUE));
            }

        }

        return cmd;
    }

}

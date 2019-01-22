/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

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

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExecutionType;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractActivityPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit contribution for maintaining the Sub-process invocation tasks's
 * start strategy selection.
 * 
 * @author aallway
 * @since 17 May 2012
 */
public class SubProcessTaskConfigurationPreCommitContributor extends
        AbstractActivityPreCommitContributor {

    public SubProcessTaskConfigurationPreCommitContributor() {
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

        TransactionalEditingDomain editingDomain = event.getEditingDomain();

        CompoundCommand cmd = new CompoundCommand();

        for (ENotificationImpl notification : notifications) {

            if (checkForAddOrSetSubProcess(notification, editingDomain, cmd)) {
                continue;
            }

            if (checkForSetStartStrategy(notification, editingDomain, cmd)) {
                continue;
            }

            if (checkForSetInvocationMode(notification, editingDomain, cmd)) {
                continue;
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Check for reset of start start strategy on sub-process task. Reset
     * priority accordingly (null for start immediate, inherit from parent if
     * schedule start
     * 
     * @param notification
     * @param editingDomain
     * @param cmd
     * 
     * @return <code>true</code> if notification was for set start strategy
     */
    private boolean checkForSetStartStrategy(ENotificationImpl notification,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {

        if (Notification.SET == notification.getEventType()) {

            if (XpdExtensionPackage.eINSTANCE.getDocumentRoot_StartStrategy()
                    .equals(notification.getFeature())) {

                Object newStrategy = notification.getNewValue();

                if (notification.getNotifier() instanceof SubFlow
                        && newStrategy instanceof SubProcessStartStrategy) {

                    SubFlow subFlow = (SubFlow) notification.getNotifier();

                    Activity activity = subFlow.getActivity();

                    if (activity != null) {
                        if (SubProcessStartStrategy.START_IMMEDIATELY
                                .equals(newStrategy)) {
                            /*
                             * If set to Start Immediately then remove the
                             * priority setting as that has no meaning in this
                             * case.
                             */
                            cmd.append(SetCommand.create(editingDomain,
                                    activity,
                                    Xpdl2Package.eINSTANCE
                                            .getActivity_Priority(),
                                    SetCommand.UNSET_VALUE));

                            /*
                             * AMX BPM Process Engine only supports suspend
                             * resume with parent = true with StartImmediately
                             */
                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            subFlow,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_SuspendResumeWithParent(),
                                            Boolean.TRUE));

                        } else if (SubProcessStartStrategy.SCHEDULE_START
                                .equals(newStrategy)) {
                            /*
                             * If set to Schedule Start then add default
                             * priority of "inherit from parent (which is a
                             * priority element with no value.
                             */
                            Priority priority =
                                    Xpdl2Factory.eINSTANCE.createPriority();
                            priority.setValue(""); //$NON-NLS-1$

                            cmd.append(SetCommand.create(editingDomain,
                                    activity,
                                    Xpdl2Package.eINSTANCE
                                            .getActivity_Priority(),
                                    priority));

                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            subFlow,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_SuspendResumeWithParent(),
                                            Boolean.FALSE));
                        }

                    }

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check for sub-process/interface being set on a sub-process task. Add the
     * default start strategy if none selected - according to type selected if.
     * 
     * @param notification
     * @param editingDomain
     * @param cmd
     * 
     * @return <code>true</code> if notification was for set sub-process.
     */
    private boolean checkForAddOrSetSubProcess(ENotificationImpl notification,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {

        SubFlow newSubFlow = null;

        if (Notification.SET == notification.getEventType()
                && Xpdl2Package.eINSTANCE.getActivity_Implementation()
                        .equals(notification.getFeature())
                && notification.getNewValue() instanceof SubFlow) {

            newSubFlow = (SubFlow) notification.getNewValue();

        } else if (Notification.ADD == notification.getEventType()
                && Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                        .equals(notification.getFeature())) {
            if (notification.getNewValue() instanceof Activity) {
                Activity activity = (Activity) notification.getNewValue();

                if (activity.getImplementation() instanceof SubFlow) {
                    newSubFlow = (SubFlow) activity.getImplementation();
                }
            }
        }

        if (newSubFlow != null) {

            AsyncExecutionMode previousAsyncExecutionMode =
                    getAsyncExecutionMode(notification.getOldValue());

            AsyncExecutionMode currAsyncExecutionMode =
                    getAsyncExecutionMode(newSubFlow);

            /*
             * If there was already an execution mode chosen on previous
             * sub-flow then preserve it unless it has been explicitly set on
             * new one for some reason.
             */
            if (previousAsyncExecutionMode != null
                    && currAsyncExecutionMode == null) {

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                newSubFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AsyncExecutionMode(),
                                previousAsyncExecutionMode));

            }

            SubProcessStartStrategy previousStartStrategy =
                    getStartStrategy(notification.getOldValue());

            SubProcessStartStrategy currentStartStrategy =
                    getStartStrategy(newSubFlow);

            /*
             * If there was already a strategy chosen on previous sub-flow then
             * preserve it unless it has been explicitly set on new one for some
             * reason.
             */
            if (previousStartStrategy != null && currentStartStrategy == null) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                newSubFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_StartStrategy(),
                                previousStartStrategy));

            }
            /*
             * Else if there is no current strategy and a sub-process is
             * selected then add an appropriate default.
             */
            else if (currentStartStrategy == null) {
                Activity activity = newSubFlow.getActivity();

                setStartStrategyDefaults(activity,
                        newSubFlow,
                        editingDomain,
                        cmd);
            }

            /*
             * Same again to set SuspendResumeWithParent=true on new sub-process
             * tasks (or any that doesn't have it set).
             */
            Boolean previousSuspendResume =
                    getSuspendResumeWihParent(notification.getOldValue());
            Boolean currentSuspendResume =
                    getSuspendResumeWihParent(newSubFlow);

            if (currentSuspendResume == null) {

                if (previousSuspendResume != null) {

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    newSubFlow,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SuspendResumeWithParent(),
                                    previousSuspendResume));
                } else {
                    setSuspendResumeWithParentDefault(editingDomain,
                            cmd,
                            newSubFlow);
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Set the default model attributes for start strategy and priority
     * according to the selected process / interface.
     * 
     * @param activity
     * @param subFlow
     * @param editingDomain
     * @param cmd
     */
    private void setStartStrategyDefaults(Activity activity, SubFlow subFlow,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {

        if (activity != null) {

            /*
             * Get activity's parent process.
             */
            Process parentProcess = Xpdl2ModelUtil.getProcess(activity);

            /*
             * Get the process or process interface being referenced from the
             * call sub-process activity.
             */
            EObject calledProcOrProcIfc =
                    TaskObjectUtil.getSubProcessOrInterface(activity);

            /*
             * Set the start strategy according to what the valid configuration
             * should be.
             */
            if (isStartImmediateStrategyRequired(calledProcOrProcIfc,
                    parentProcess)) {

                appendCommandsToSetStartStrategyToStartImmediate(activity,
                        subFlow,
                        editingDomain,
                        cmd);

            } else if (isScheduleStartStrategyRequired(calledProcOrProcIfc,
                    parentProcess)) {

                appendCommandsToSetStartStrategyToScheduleStart(activity,
                        subFlow,
                        editingDomain,
                        cmd);
            }

        }
    }

    /**
     * Return <code>true</code> if the valid Start Strategy for the specified
     * sub-process call combination is 'Start Immediately', <code>false</code>
     * otherwise.
     * 
     * @param calledProcOrProcIfc
     * @param parentProcess
     * 
     * @return <code>true</code> if the valid Start Strategy configuration for
     *         the specified sub-process call combination is 'Start
     *         Immediately', <code>false</code> otherwise.
     */
    private boolean isStartImmediateStrategyRequired(
            EObject calledProcOrProcIfc, Process parentProcess) {

        if (Xpdl2ModelUtil.isServiceProcess(parentProcess)
                && Xpdl2ModelUtil
                        .isServiceProcessOrProcessInterface(calledProcOrProcIfc)) {

            return true;
        }

        if (Xpdl2ModelUtil.isBusinessProcess(parentProcess)
                && Xpdl2ModelUtil
                        .isBusinessProcessOrProcessInterface(calledProcOrProcIfc)) {

            return true;
        }

        if (Xpdl2ModelUtil.isPageflowOrSubType(parentProcess)
                && calledProcOrProcIfc instanceof Process
                && !Xpdl2ModelUtil
                        .isBusinessProcess((Process) calledProcOrProcIfc)) {

            return true;
        }

        return false;
    }

    /**
     * Return <code>true</code> if the valid Start Strategy for the specified
     * sub-process call combination is 'Scheduled Start', <code>false</code>
     * otherwise.
     * 
     * @param calledProcOrProcIfc
     * @param parentProcess
     * 
     * @return <code>true</code> if the valid Start Strategy configuration for
     *         the specified sub-process call combination is 'Scheduled Start',
     *         <code>false</code> otherwise.
     */
    private boolean isScheduleStartStrategyRequired(
            EObject calledProcOrProcIfc, Process parentProcess) {

        if (Xpdl2ModelUtil.isServiceProcess(parentProcess)) {

            if (Xpdl2ModelUtil
                    .isBusinessProcessOrProcessInterface(calledProcOrProcIfc)) {

                return true;
            }

        } else if (Xpdl2ModelUtil.isBusinessProcess(parentProcess)) {

            if (Xpdl2ModelUtil
                    .isServiceProcessOrProcessInterface(calledProcOrProcIfc)
                    || (calledProcOrProcIfc instanceof Process && Xpdl2ModelUtil
                            .isBusinessProcess((Process) calledProcOrProcIfc))) {

                return true;
            }

        } else if (Xpdl2ModelUtil.isPageflowOrSubType(parentProcess)) {

            if (calledProcOrProcIfc instanceof Process
                    && Xpdl2ModelUtil
                            .isBusinessProcess((Process) calledProcOrProcIfc)) {

                return true;
            }
        }

        return false;
    }

    /**
     * Append commands to set the call sub-proc start strategy to 'Schedule
     * Start' and set the priority to inherit from parent.
     * 
     * @param activity
     * @param subFlow
     * @param editingDomain
     * @param cmd
     */
    private void appendCommandsToSetStartStrategyToScheduleStart(
            Activity activity, SubFlow subFlow,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {

        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                subFlow,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_StartStrategy(),
                SubProcessStartStrategy.SCHEDULE_START));

        Priority priority = Xpdl2Factory.eINSTANCE.createPriority();

        priority.setValue(""); //$NON-NLS-1$

        cmd.append(SetCommand.create(editingDomain,
                activity,
                Xpdl2Package.eINSTANCE.getActivity_Priority(),
                priority));
    }

    /**
     * Append commands to set the call sub-proc start strategy to 'Start
     * Immediate' and unset the priority if it's set.
     * 
     * @param activity
     * @param subFlow
     * @param editingDomain
     * @param cmd
     */
    private void appendCommandsToSetStartStrategyToStartImmediate(
            Activity activity, SubFlow subFlow,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {

        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                subFlow,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_StartStrategy(),
                SubProcessStartStrategy.START_IMMEDIATELY));

        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                subFlow,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_SuspendResumeWithParent(),
                Boolean.TRUE));

        if (activity.getPriority() != null) {

            cmd.append(SetCommand.create(editingDomain,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Priority(),
                    SetCommand.UNSET_VALUE));
        }
    }

    /**
     * Check for reset of invocation mode on sub-process task. Reset
     * suspend-resume accordingly if it's a pageflow process. (since we don't
     * display the suspend-resume check box on the UI for call sub-process
     * activitites placed inside a pageflow).
     * 
     * @param notification
     * @param editingDomain
     * @param cmd
     * 
     * @return <code>true</code> if notification was for set invocation mode
     */
    private boolean checkForSetInvocationMode(ENotificationImpl notification,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {

        if (Notification.SET == notification.getEventType()
                || Notification.UNSET == notification.getEventType()) {

            if (XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_AsyncExecutionMode()
                    .equals(notification.getFeature())) {

                Object newInvocationMode = notification.getNewValue();

                if (notification.getNotifier() instanceof SubFlow
                        && newInvocationMode instanceof AsyncExecutionMode) {

                    SubFlow subFlow = (SubFlow) notification.getNotifier();

                    Activity activity = subFlow.getActivity();

                    if (activity != null) {

                        if (null != activity.getProcess()) {

                            if (AsyncExecutionMode.DETACHED
                                    .equals(newInvocationMode)) {

                                /*
                                 * Keep the XPDL ExecutionType synch/asynch in
                                 * synch with the xpd extension.
                                 */
                                if (!subFlow.isSetExecution()) {

                                    cmd.append(SetCommand.create(editingDomain,
                                            subFlow,
                                            Xpdl2Package.eINSTANCE
                                                    .getSubFlow_Execution(),
                                            ExecutionType.ASYNCHR_LITERAL));
                                }

                                if (Xpdl2ModelUtil.isPageflow(activity
                                        .getProcess())) {
                                    /*
                                     * If set to Async Detached then set
                                     * 'suspend/resume with parent process' to
                                     * false.
                                     */
                                    if (Xpdl2ModelUtil
                                            .getOtherAttributeAsBoolean(subFlow,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_SuspendResumeWithParent())) {

                                        cmd.append(Xpdl2ModelUtil
                                                .getSetOtherAttributeCommand(editingDomain,
                                                        subFlow,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_SuspendResumeWithParent(),
                                                        Boolean.FALSE));
                                    }
                                }

                            } else if (AsyncExecutionMode.ATTACHED
                                    .equals(newInvocationMode)) {

                                /*
                                 * Keep the XPDL ExecutionType synch/asynch in
                                 * synch with the xpd extension.
                                 */
                                if (!subFlow.isSetExecution()) {

                                    cmd.append(SetCommand.create(editingDomain,
                                            subFlow,
                                            Xpdl2Package.eINSTANCE
                                                    .getSubFlow_Execution(),
                                            ExecutionType.ASYNCHR_LITERAL));
                                }

                                if (Xpdl2ModelUtil.isPageflow(activity
                                        .getProcess())) {
                                    /*
                                     * If set to Async Attached then set
                                     * 'suspend/resume with parent process' to
                                     * true.
                                     */
                                    if (!Xpdl2ModelUtil
                                            .getOtherAttributeAsBoolean(subFlow,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_SuspendResumeWithParent())) {
                                        cmd.append(SetCommand
                                                .create(editingDomain,
                                                        subFlow,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_SuspendResumeWithParent(),
                                                        Boolean.TRUE));
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Set the xpdExt:SuspendResumeWithparent attibute on the subFlow to its
     * default value (true) if it is not already set.
     * 
     * @param editingDomain
     * @param cmd
     * @param subFlow
     */
    private void setSuspendResumeWithParentDefault(
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            SubFlow subFlow) {
        Object suspendResumeWithParent =
                Xpdl2ModelUtil.getOtherAttribute(subFlow,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SuspendResumeWithParent());

        if (suspendResumeWithParent == null) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SuspendResumeWithParent(),
                            Boolean.TRUE));
        }
    }

    /**
     * @param activityImplementation
     *            Object that may be a SubFlow.
     * 
     * @return The {@link SubProcessStartStrategy} defined for the given
     *         activity implemetnation or <code>null</code> if none defined or
     *         the implementation element isn't a subFlow
     */
    private SubProcessStartStrategy getStartStrategy(
            Object activityImplementation) {
        if (activityImplementation instanceof SubFlow) {
            return (SubProcessStartStrategy) Xpdl2ModelUtil
                    .getOtherAttribute((SubFlow) activityImplementation,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_StartStrategy());
        }
        return null;
    }

    /**
     * @param activityImplementation
     *            Object that may be a SubFlow.
     * 
     * @return The {@link AsyncExecutionMode} defined for the given activity
     *         implemetnation or <code>null</code> if none defined or the
     *         implementation element isn't a subFlow
     */
    private AsyncExecutionMode getAsyncExecutionMode(
            Object activityImplementation) {
        if (activityImplementation instanceof SubFlow) {
            Object asyncExecModeObject =
                    Xpdl2ModelUtil
                            .getOtherAttribute((SubFlow) activityImplementation,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AsyncExecutionMode());

            if (asyncExecModeObject instanceof AsyncExecutionMode) {
                return ((AsyncExecutionMode) asyncExecModeObject);
            }
        }
        return null;
    }

    /**
     * @param activityImplementation
     *            Object that may be a SubFlow.
     * 
     * @return The value of the xpdExt:SuspendResumeWihParent defined for the
     *         given activity or <code>null</code> if none defined or the
     *         implementation element isn't a subFlow
     */
    private Boolean getSuspendResumeWihParent(Object activityImplementation) {
        if (activityImplementation instanceof SubFlow) {
            Object suspendResumeWithParent =
                    Xpdl2ModelUtil
                            .getOtherAttribute((SubFlow) activityImplementation,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SuspendResumeWithParent());

            if (suspendResumeWithParent instanceof Boolean) {
                return (Boolean) suspendResumeWithParent;
            }
        }
        return null;
    }

}

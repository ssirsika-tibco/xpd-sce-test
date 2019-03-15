/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rules for Call Sub-Process activity. This will add the following
 * markers:
 * <p>
 * 1. All Synchronous Pageflow Process to Pageflow Process invocations must be
 * configured to Start Immediately.
 * <p>
 * 2. All Business Process to Service Process invocations must be configured as
 * Scheduled Start.
 * <p>
 * 3. All Synchronous Pageflow Process to Service Process invocations must be
 * configured to Start Immediately.
 * <p>
 * 4. All Synchronous Service Process to Service Process invocations must be
 * configured to Start Immediately.
 * <p>
 * 5. Asynchronous Detached invocations of sub-process must use Scheduled Start
 * configuration.
 * <p>
 * 6. A Business process cannot invoke call a Service process that does not have
 * Process engine as a deployment target.
 * <p>
 * 7. A Service process can only synchronously call a Service process at least
 * the same deployment targets selected
 * <p>
 * 8. A Service process can only call a Business process in
 * asynchronous-detached mode.
 * <p>
 * 9. In asynchronous-detached mode a Service process can only call a Service
 * process that has the Process engine deployment target selected.
 * <p>
 * 10. A Service process cannot invoke processes in asynchronous-attached mode.
 * <p>
 * 11. Asynchronous-detached sub-process calls can not suspend/resume with
 * parent process.
 * <p>
 * 12. A process should not invoke itself as a sub-process (can cause infinite
 * loop).
 * <p>
 * 
 * @author sajain
 * @since Jan 1, 2015
 */
public class CallSubProcessRule extends ProcessActivitiesValidationRule {

    /**
     * All Synchronous Pageflow Process to Pageflow Process invocations must be
     * configured to Start Immediately.
     */
    private static final String ISSUE_SYNC_PF_TO_PF_MUST_BE_STARTIMMEDIATE =
            "bx.syncPfToPfMustBeStartImmediately"; //$NON-NLS-1$

    /**
     * All Business Process to Service Process invocations must be configured as
     * Scheduled Start.
     */
    private static final String ISSUE_BP_TO_SP_MUST_BE_SCHEDULED_START =
            "bx.bpToSpMustBeScheduledStart"; //$NON-NLS-1$

    /**
     * All Synchronous Pageflow Process to Service Process invocations must be
     * configured to Start Immediately.
     */
    private static final String ISSUE_SYNC_PF_TO_SP_MUST_BE_START_IMMEIDATE =
            "bx.syncPfToSpMustBeStartImmediate"; //$NON-NLS-1$

    /**
     * All Synchronous Service Process to Service Process invocations must be
     * configured to Start Immediately.
     */
    private static final String ISSUE_SYNC_SP_TO_SP_MUST_BE_STARTIMMEDIATE =
            "bx.syncSpToSpMustBeStartImmediate"; //$NON-NLS-1$

    /**
     * Asynchronous Detached invocations of sub-process must use Scheduled Start
     * configuration.
     */
    private static final String ISSUE_ASYNC_DETACHED_CSP_MUST_BE_SCHEDULED_START =
            "bx.asyncDetachedCSPMustBeScheduledStart"; //$NON-NLS-1$

    /**
     * A Business process cannot invoke call a Service process that does not
     * have Process engine as a deployment target.
     */
    private static final String ISSUE_BP_CANNOT_CALL_SP_WITHOUT_PE_DEPLOY_TARGET =
            "bx.bPCannotCallSpWithoutPEDeployTarget"; //$NON-NLS-1$

    /**
     * A Service process can only synchronously call a Service process with at
     * least the same deployment targets selected.
     */
    private static final String ISSUE_SYNC_SP_CAN_CALL_SP_WITH_SAME_DEPLOY_TARGETS =
            "bx.syncSpCanCallSpWithSameDeployTargets"; //$NON-NLS-1$

    /**
     * A Service process can only call a Business process in
     * asynchronous-detached mode.
     */
    private static final String ISSUE_SP_CAN_CALL_BP_IN_ASYNC_DETACHED_MODE =
            "bx.spCanCallBpInAsyncDetachedMode"; //$NON-NLS-1$

    /**
     * In asynchronous-detached mode a Service process can only call a Service
     * process that has the Process engine deployment target selected.
     */
    private static final String ISSUE_ASYNC_DETACHED_SP_CAN_CALL_SP_WITH_PE_DEPLOY_TAREGT =
            "bx.asyncDetachedSpCanCallSpWithPEDeployTarget"; //$NON-NLS-1$

    /**
     * A Service process cannot invoke processes in asynchronous-attached mode.
     */
    private static final String ISSUE_SP_CANNOT_CALL_IN_ASYNC_ATTACHED_MODE =
            "bx.spCannotCallInAsyncDetachedMode"; //$NON-NLS-1$

    /**
     * Asynchronous-detached sub-process calls can not suspend/resume with
     * parent process.
     */
    private static final String ISSUE_ASYNC_DETACHED_CANNOT_SUSPEND_RESUME =
            "bx.asyncDetachedCannotSuspendResume"; //$NON-NLS-1$

    /**
     * A business process cannot invoke a pageflow process
     */
    private static final String ISSUE_BUSINESS_PROCESS_CANNOT_INVOKE_PAGEFLOW_PROCESS =
            "bx.businessProcessCannotInvokePageflowProcess"; //$NON-NLS-1$

    private static final String ISSUE_SERVICE_PROCESS_CANNOT_INVOKE_PAGEFLOW =
            "bx.serviceProcessCannotInvokePageflow"; //$NON-NLS-1$

    /**
     * A process should not invoke itself as a sub-process (can cause infinite
     * loop).
     */
    private static final String ISSUE_PROCESS_DIRECT_RECURSIVE_SUBPROCESS =
            "bx.directRecursiveSubprocess"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        /*
         * Filter out for Call sub-process activities.
         */
        if (activity.getImplementation() instanceof SubFlow) {
            /*
             * Get sub-flow.
             */
            SubFlow subFlow = (SubFlow) activity.getImplementation();

            /*
             * Get sub-process being referenced.
             */
            EObject subProcOrIntfcObject =
                    TaskObjectUtil.getSubProcessOrInterface(activity);

            /*
             * Sid XPD-8457. Validate against direct recursion process calling
             * itself.
             */
            if (activity.getProcess() != null
                    && activity.getProcess().equals(subProcOrIntfcObject)) {
                addIssue(ISSUE_PROCESS_DIRECT_RECURSIVE_SUBPROCESS, activity);
            }

            /*
             * Get start strategy object.
             */
            Object startStrategyObject =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_StartStrategy());

            /*
             * Start strategy must be there.
             */
            if (startStrategyObject == null) {

                startStrategyObject = SubProcessStartStrategy.START_IMMEDIATELY;
            }

            if (startStrategyObject instanceof SubProcessStartStrategy) {

                SubProcessStartStrategy startStrategy =
                        (SubProcessStartStrategy) startStrategyObject;
                /*
                 * Get execution mode object.
                 */
                Object execModeObject =
                        Xpdl2ModelUtil.getOtherAttribute(subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AsyncExecutionMode());
                /*
                 * Get process activity is contained in.
                 */
                Process parentProcess = activity.getProcess();

                if (null != subProcOrIntfcObject) {

                    /*
                     * 1. Calling process: Business process.
                     */
                    if (Xpdl2ModelUtil.isBusinessProcess(parentProcess)) {

                        validateSubProcOrIntfInBusinessProcess(activity,
                                startStrategy,
                                subProcOrIntfcObject);
                    }

                    /*
                     * 2. Calling process: Service process.
                     */
                    if (Xpdl2ModelUtil.isServiceProcess(parentProcess)) {

                        validateCallSubProcOrIfcInServiceProcess(activity,
                                startStrategy,
                                execModeObject,
                                parentProcess,
                                subProcOrIntfcObject);
                    }

                    /*
                     * 3. Calling process: Pageflow.
                     */
                    if (Xpdl2ModelUtil.isPageflowOrSubType(parentProcess)) {

                        validateCallSubProcOrIfcInPageflowProcess(activity,
                                startStrategy,
                                execModeObject,
                                subProcOrIntfcObject);
                    }

                    /*
                     * Rules which don't have anything to do with calling/called
                     * processes.
                     */
                    if (execModeObject instanceof AsyncExecutionMode) {

                        AsyncExecutionMode executionMode =
                                (AsyncExecutionMode) execModeObject;

                        validateCallSubProc(activity,
                                subFlow,
                                startStrategy,
                                executionMode);
                    }
                }
            }
        }
    }

    /**
     * Validate call to a process/process interface via a call sub-process
     * activity placed in a business process.
     * 
     * @param activity
     * @param startStrategy
     * @param subProcOrIntfcObject
     */
    private void validateSubProcOrIntfInBusinessProcess(Activity activity,
            SubProcessStartStrategy startStrategy,
            EObject subProcOrIntfcObject) {

        if (Xpdl2ModelUtil
                .isServiceProcessOrProcessInterface(subProcOrIntfcObject)
                && !ProcessInterfaceUtil
                        .isProcessEngineServiceProcOrServiceProcIntfc(
                                subProcOrIntfcObject)) {

            addIssue(ISSUE_BP_CANNOT_CALL_SP_WITHOUT_PE_DEPLOY_TARGET,
                    activity);
            /*
             * no point in validating other stuff on a service process if the
             * required deploy target is not available
             */
            return;
        }

        if (ProcessInterfaceUtil.isProcessEngineServiceProcOrServiceProcIntfc(
                subProcOrIntfcObject)) {
            /*
             * All Business Process to Service Process invocations must be
             * configured as Scheduled Start.
             */
            if (!SubProcessStartStrategy.SCHEDULE_START.equals(startStrategy)) {

                addIssue(ISSUE_BP_TO_SP_MUST_BE_SCHEDULED_START, activity);

            }
        }
        if (subProcOrIntfcObject instanceof Process && Xpdl2ModelUtil
                .isPageflowOrSubType((Process) subProcOrIntfcObject)) {

            /* A business process cannot invoke a pageflow process */
            addIssue(ISSUE_BUSINESS_PROCESS_CANNOT_INVOKE_PAGEFLOW_PROCESS,
                    activity);
        }
    }

    /**
     * Validate call sub-process.
     * 
     * @param activity
     * @param subFlow
     * @param startStrategy
     * @param executionMode
     */
    private void validateCallSubProc(Activity activity, SubFlow subFlow,
            SubProcessStartStrategy startStrategy,
            AsyncExecutionMode executionMode) {

        if (AsyncExecutionMode.DETACHED.equals(executionMode)) {
            /*
             * Asynchronous Detached invocations of sub-process must use
             * Scheduled Start configuration.
             */
            if (!SubProcessStartStrategy.SCHEDULE_START.equals(startStrategy)) {

                addIssue(ISSUE_ASYNC_DETACHED_CSP_MUST_BE_SCHEDULED_START,
                        activity);
            }
            /*
             * Asynchronous-detached sub-process calls can not suspend/resume
             * with parent process.
             */
            if (Xpdl2ModelUtil.getOtherAttributeAsBoolean(subFlow,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_SuspendResumeWithParent())) {

                addIssue(ISSUE_ASYNC_DETACHED_CANNOT_SUSPEND_RESUME, activity);
            }
        }
    }

    /**
     * Validate call sub-process/process interface placed in a pageflow process.
     * 
     * @param activity
     * @param startStrategy
     * @param execModeObject
     * @param subProc
     */
    private void validateCallSubProcOrIfcInPageflowProcess(Activity activity,
            SubProcessStartStrategy startStrategy, Object execModeObject,
            EObject subProc) {

        if (Xpdl2ModelUtil.isServiceProcessOrProcessInterface(subProc)) {

            /*
             * 3.2. Pageflow to Service Process...
             */

            /*
             * All Synchronous Pageflow Process to Service Process invocations
             * must be configured to Start Immediately.
             */
            if (!SubProcessStartStrategy.START_IMMEDIATELY.equals(startStrategy)
                    && execModeObject == null) {

                addIssue(ISSUE_SYNC_PF_TO_SP_MUST_BE_START_IMMEIDATE, activity);
            }
        }

        if (subProc instanceof Process) {

            if (Xpdl2ModelUtil.isPageflowOrSubType((Process) subProc)) {

                /*
                 * 3.1. Pageflow to Pageflow...
                 */

                /*
                 * All Synchronous Pageflow Process to Pageflow Process
                 * invocations must be configured to Start Immediately.
                 */
                if (!SubProcessStartStrategy.START_IMMEDIATELY
                        .equals(startStrategy) && execModeObject == null) {

                    addIssue(ISSUE_SYNC_PF_TO_PF_MUST_BE_STARTIMMEDIATE,
                            activity);
                }

            }
        }
    }

    /**
     * Validate call sub-process/process interface placed in a service process.
     * 
     * @param activity
     * @param startStrategy
     * @param execModeObject
     * @param proc
     * @param subProcOrIfcObj
     */
    private void validateCallSubProcOrIfcInServiceProcess(Activity activity,
            SubProcessStartStrategy startStrategy, Object execModeObject,
            Process proc, EObject subProcOrIfcObj) {

        /*
         * SubProc object is a Process.
         */

        if (Xpdl2ModelUtil
                .isBusinessProcessOrProcessInterface(subProcOrIfcObj)) {

            /*
             * 2.1. Service Process to Business Process/Process Interface...
             */

            /*
             * A Service process can only call a Business process in
             * asynchronous-detached mode.
             */
            if (execModeObject == null
                    || !AsyncExecutionMode.DETACHED.equals(execModeObject)) {

                /*
                 * execModeObject is null which implies that execution mode is
                 * 'synchronous'.
                 */
                addIssue(ISSUE_SP_CAN_CALL_BP_IN_ASYNC_DETACHED_MODE, activity);
            }
        }

        if (subProcOrIfcObj instanceof Process) {

            if (Xpdl2ModelUtil.isPageflowOrSubType((Process) subProcOrIfcObj)) {

                /* A pageflow process cannot invoke a service process */

                addIssue(ISSUE_SERVICE_PROCESS_CANNOT_INVOKE_PAGEFLOW,
                        activity);
            }
        }

        if (Xpdl2ModelUtil
                .isServiceProcessOrProcessInterface(subProcOrIfcObj)) {

            /*
             * Service Process to Service Process/Ifc...
             */

            /*
             * All Synchronous Service Process to Service Process invocations
             * must be configured to Start Immediately.
             */
            if (!SubProcessStartStrategy.START_IMMEDIATELY.equals(startStrategy)
                    && execModeObject == null) {

                addIssue(ISSUE_SYNC_SP_TO_SP_MUST_BE_STARTIMMEDIATE, activity);
            }

            /*
             * A Service process can only synchronously call a Service process
             * with at least the same deployment targets selected.
             */
            if (!isSubProcessDeployTargetCompatible(proc, subProcOrIfcObj)
                    && execModeObject == null) {

                addIssue(ISSUE_SYNC_SP_CAN_CALL_SP_WITH_SAME_DEPLOY_TARGETS,
                        activity);
            }

            if (execModeObject instanceof AsyncExecutionMode) {

                AsyncExecutionMode execMode =
                        (AsyncExecutionMode) execModeObject;

                /*
                 * In asynchronous-detached mode a Service process can only call
                 * a Service process that has the Process engine deployment
                 * target selected.
                 */
                if (AsyncExecutionMode.DETACHED.equals(execMode)
                        && Xpdl2ModelUtil.isServiceProcessOrProcessInterface(
                                subProcOrIfcObj)
                        && !ProcessInterfaceUtil
                                .isProcessEngineServiceProcOrServiceProcIntfc(
                                        subProcOrIfcObj)) {

                    addIssue(
                            ISSUE_ASYNC_DETACHED_SP_CAN_CALL_SP_WITH_PE_DEPLOY_TAREGT,
                            activity);
                }

                /*
                 * A Service process cannot invoke processes in
                 * asynchronous-attached mode.
                 */
                if (AsyncExecutionMode.ATTACHED.equals(execMode)) {

                    addIssue(ISSUE_SP_CANNOT_CALL_IN_ASYNC_ATTACHED_MODE,
                            activity);
                }
            }
        }
    }

    /**
     * Return <code>true</code> if the specified Process and Process/Process
     * Interface are service process/Process Interfaces with same deploy
     * targets, <code>false</code> otherwise.
     * 
     * @param parentProc
     *            1st process.
     * @param subProcOrIfcObj
     *            2nd process/process interface.
     * 
     * @return <code>true</code> if the specified Process and Process/Process
     *         Interface are service process/Process Interfaces with same deploy
     *         targets, <code>false</code> otherwise.
     */
    private boolean isSubProcessDeployTargetCompatible(Process parentProc,
            EObject subProcOrIfcObj) {

        /*
         * Previous code did exact match between parent and sub process deploy
         * targets. Sub-Pro4ess only needs to have _At least_ same targets (i.e.
         * if sub-proc as both then it is good for process only and pageflow
         * only parent
         */
        if (ProcessInterfaceUtil.isProcessEngineServiceProcess(parentProc)) {
            if (!ProcessInterfaceUtil
                    .isProcessEngineServiceProcOrServiceProcIntfc(
                            subProcOrIfcObj)) {
                return false;
            }
        }

        if (ProcessInterfaceUtil.isPageflowEngineServiceProcess(parentProc)) {
            if (!ProcessInterfaceUtil
                    .isPageflowEngineServiceProcOrServiceProcIntfc(
                            subProcOrIfcObj)) {
                return false;
            }
        }

        return true;
    }
}

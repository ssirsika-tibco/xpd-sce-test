/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pageflow validation rules for Call Sub-Process activity. This will add the following markers:
 * <p>
 * 1. A Pageflow process can only synchronously call a Service process that has the Pageflow engine deployment target
 * selected.
 * <p>
 * 2. A Pageflow process can only call a Business process in asynchronous-detached mode.
 * <p>
 * 3. In asynchronous-detached mode a Pageflow process can only call a Service process that has the Process engine
 * deployment target selected.
 * <p>
 * 4. A Pageflow process cannot call processes in asynchronous-attached mode.
 * <p>
 * 5. You cannot make asynchronous calls to processes that are executed in the pageflow run-time.
 * 
 * @author sajain
 * @since Jan 15, 2015
 */
public class PageflowCallSubProcessRule extends ProcessActivitiesValidationRule {

    /**
     * A Pageflow process can only synchronously call a Service process that has
     * the Pageflow engine deployment target selected.
     */
    private static final String ISSUE_SYNC_PF_CAN_CALL_SP_WITH_PFE =
            "pageflow.syncPfCanCallSpWithPFE"; //$NON-NLS-1$

    /**
     * A Pageflow process can only call a Business process in
     * asynchronous-detached mode.
     */
    private static final String ISSUE_PF_CAN_CALL_BP_ONLY_IN_ASYNC_DETACHED =
            "pageflow.pfCanCallBpOnlyInAsyncDetached"; //$NON-NLS-1$

    /**
     * In asynchronous-detached mode a Pageflow process can only call a Service
     * process that has the Process engine deployment target selected.
     */
    private static final String ISSUE_ASYNC_DETACHED_PF_CAN_CALL_SP_WITH_PE =
            "pageflow.asyncDetachedPfCanCallSpWithPE"; //$NON-NLS-1$

    /**
     * A Pageflow process cannot call processes in asynchronous-attached mode.
     */
    private static final String ISSUE_PF_CANNOT_CALL_IN_ASYNC_ATTACHED =
            "pageflow.pfCannotCallInAsyncAttached"; //$NON-NLS-1$

    /**
     * Sid ACE-6063 Validate against asynch calls to pageflow-engine-executed processes
     * 
     * You cannot make asynchronous calls to processes that are executed in the pageflow run-time.
     */
    private static final String ISSUE_PF_CANNOT_CALL_PAGEFLOW_ASYNC = "pageflow.pfCannotCallPageflowAsynch"; //$NON-NLS-1$

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
             * Get execution mode object.
             */
            Object execModeObject =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AsyncExecutionMode());

            /*
             * Get process activity is contained in.
             */
            Process proc = activity.getProcess();

            /*
             * Get sub-process being referenced.
             */
            EObject subProcObject =
                    TaskObjectUtil.getSubProcessOrInterface(activity);

            /*
             * Sid ACE-6063 Validate against ANY ASYNCH call where we know that the target is run in pageflow engine
             */
            if (subProcObject instanceof Process && Xpdl2ModelUtil.isPageflowOrSubType((Process) subProcObject)
                    && execModeObject instanceof AsyncExecutionMode
                    && (AsyncExecutionMode.ATTACHED.equals(execModeObject)
                            || AsyncExecutionMode.DETACHED.equals(execModeObject))) {
                addIssue(ISSUE_PF_CANNOT_CALL_PAGEFLOW_ASYNC, activity);
            }
            /*
             * Check if the calling process is a pageflow.
             */
            else if (Xpdl2ModelUtil.isPageflow(proc)) {

                if (Xpdl2ModelUtil
                        .isServiceProcessOrProcessInterface(subProcObject)) {
                    /*
                     * Pageflow to Service Process...
                     */

                    validatePageflowToServiceProcessOrIfcCalls(activity,
                            execModeObject,
                            subProcObject);

                }

                /*
                 * XPD-7357: Saket: We should only validate Pageflow to Business
                 * process invocations here, NOT Pageflow to Process Interface.
                 */
                if (subProcObject instanceof Process) {

                    if (Xpdl2ModelUtil
                            .isBusinessProcess((Process) subProcObject)) {

                        /*
                         * Pageflow to Business Process...
                         */

                        validatePageflowToBusinessProcCalls(activity,
                                execModeObject);
                    }
                }

                /*
                 * A Pageflow process cannot call processes in
                 * asynchronous-attached mode.
                 */
                if (execModeObject instanceof AsyncExecutionMode) {

                    AsyncExecutionMode execMode =
                            (AsyncExecutionMode) execModeObject;

                    if (AsyncExecutionMode.ATTACHED.equals(execMode)) {

                        addIssue(ISSUE_PF_CANNOT_CALL_IN_ASYNC_ATTACHED,
                                activity);
                    }
                }
            }
        }
    }

    /**
     * Validate pageflow to business process calls.
     * 
     * @param activity
     * @param execModeObject
     */
    private void validatePageflowToBusinessProcCalls(Activity activity,
            Object execModeObject) {
        /*
         * A Pageflow process can only call a Business process in
         * asynchronous-detached mode.
         */
        if (execModeObject instanceof AsyncExecutionMode) {

            AsyncExecutionMode execMode = (AsyncExecutionMode) execModeObject;

            if (!AsyncExecutionMode.DETACHED.equals(execMode)) {

                addIssue(ISSUE_PF_CAN_CALL_BP_ONLY_IN_ASYNC_DETACHED, activity);
            }
        } else if (execModeObject == null) {

            /*
             * execModeObject is null, so it signifies that the mode is
             * synchronous.
             */

            addIssue(ISSUE_PF_CAN_CALL_BP_ONLY_IN_ASYNC_DETACHED, activity);
        }
    }

    /**
     * Validate pageflow to service process/service process interface calls.
     * 
     * @param activity
     * @param execModeObject
     * @param subProcOrIfcObj
     */
    private void validatePageflowToServiceProcessOrIfcCalls(Activity activity,
            Object execModeObject, EObject subProcOrIfcObj) {
        /*
         * A Pageflow process can only synchronously call a Service process/Ifc
         * that has the Pageflow engine deployment target selected.
         */
        if (execModeObject == null
                && Xpdl2ModelUtil
                        .isServiceProcessOrProcessInterface(subProcOrIfcObj)
                && !ProcessInterfaceUtil
                        .isPageflowEngineServiceProcOrServiceProcIntfc(subProcOrIfcObj)) {

            addIssue(ISSUE_SYNC_PF_CAN_CALL_SP_WITH_PFE, activity);
        }

        /*
         * In asynchronous-detached mode a Pageflow process can only call a
         * Service process/Ifc that has the Process engine deployment target
         * selected.
         */
        if (execModeObject instanceof AsyncExecutionMode) {

            AsyncExecutionMode execMode = (AsyncExecutionMode) execModeObject;

            if (AsyncExecutionMode.DETACHED.equals(execMode)
                    && Xpdl2ModelUtil
                            .isServiceProcessOrProcessInterface(subProcOrIfcObj)
                    && !ProcessInterfaceUtil
                            .isProcessEngineServiceProcOrServiceProcIntfc(subProcOrIfcObj)) {

                addIssue(ISSUE_ASYNC_DETACHED_PF_CAN_CALL_SP_WITH_PE, activity);
            }
        }
    }
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.ProcessFlowAnalyserPreProcessor;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rules specific to case data signals. Following problem markers are
 * added in this rule:
 * <p>
 * 1. Case Data Signals can only be caught in Business Process signal event
 * sub-processes and event handlers.
 * <p>
 * 2. Case Data Signal Events must specify a case reference field / parameter.
 * <p>
 * 3. The case reference field / parameter for Case Data signal could not be
 * found.
 * <p>
 * 4. The case reference field / parameter selected in the catch signal event
 * should not be an array field.
 * <p>
 * 
 * @author sajain
 * @since Mar 17, 2015
 */
public class CaseDataSignalRule extends ProcessActivitiesValidationRule {

    /**
     * Changing a case object downstream of a case signal handler for the same
     * object could cause and infinite event handler loop.
     */
    private static final String ISSUE_CASE_DATA_SIG_WITH_DOWNSTREAM_GLOBAL_DATA_TASK =
            "bx.caseDataSigWithDownstreamGlobalDataTask"; //$NON-NLS-1$

    /**
     * Case Data Signals can only be caught in Business Process signal event
     * sub-processes and event handlers.
     */
    private static final String ISSUE_CASE_DATA_SIG_IN_INVALID_ACTS =
            "bx.caseDataSigsMustBeInSigEventHandlersAndESPStartEvents"; //$NON-NLS-1$

    /**
     * Case Data Signal Events must specify a case reference field / parameter.
     */
    private static final String ISSUE_CASE_DATA_SIG_EVENTS_MUST_SPECIFY_CASE_REF_DATA =
            "bx.caseDataSigEventsMustSpecifyCaseRefData"; //$NON-NLS-1$

    /**
     * The case reference field / parameter for Case Data signal could not be
     * found.
     */
    private static final String ISSUE_CASE_REF_DATA_NOT_FOUND =
            "bx.caseRefDataNotFound"; //$NON-NLS-1$

    /**
     * The case reference field / parameter selected in the catch signal event
     * should not be an array field.
     */
    private static final String ISSUE_CASE_REF_DATA_SHOULD_NOT_BE_ARRAY_IN_CATCH =
            "bx.caseRefDataInCatchEventShouldNotBeArray"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        Event event = activity.getEvent();

        /*
         * Activity must be event.
         */
        if (event != null) {

            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            /*
             * Event must be signal event.
             */
            if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                /*
                 * Event should also be a case data event.
                 */
                if (isCaseDataEvent(activity)) {

                    /*
                     * Check if the specified event is neither an event handler
                     * nor an event sub-process start event.
                     */
                    if (!Xpdl2ModelUtil.isBusinessProcess(Xpdl2ModelUtil
                            .getProcess(activity))
                            || !(Xpdl2ModelUtil
                                    .isEventHandlerActivity(activity) || EventObjectUtil
                                    .isEventSubProcessStartEvent(activity))) {

                        /*
                         * Case Data Signals can only be caught in Business
                         * Process signal event sub-processes and event
                         * handlers.
                         */
                        addIssue(ISSUE_CASE_DATA_SIG_IN_INVALID_ACTS, activity);

                    } else {

                        TriggerResultSignal signal =
                                (TriggerResultSignal) eventTriggerTypeNode;

                        if (signal.getName() == null
                                || signal.getName().equals("")) { //$NON-NLS-1$

                            /*
                             * Case Data Signal Events must specify a case
                             * reference field / parameter.
                             */
                            addIssue(ISSUE_CASE_DATA_SIG_EVENTS_MUST_SPECIFY_CASE_REF_DATA,
                                    activity);
                        } else {

                            /*
                             * The case reference field / parameter for Case
                             * Data signal could not be found.
                             */
                            ProcessRelevantData prdFound =
                                    checkIfProcRelDataExists(activity, signal);

                            if (prdFound != null) {
                                if (!(prdFound.getDataType() instanceof RecordType)) {
                                    /*
                                     * XPD-7554: Case signal proposal says that
                                     * "must specify a case ref field" should be
                                     * raised if not set OR if it is not caseref
                                     * type - this was missed in initial
                                     * implemenation.
                                     */
                                    addIssue(ISSUE_CASE_DATA_SIG_EVENTS_MUST_SPECIFY_CASE_REF_DATA,
                                            activity);

                                } else if (prdFound.isIsArray()) {
                                    /*
                                     * The case reference field / parameter
                                     * selected in the catch signal event should
                                     * not be an array field.
                                     */
                                    addIssue(ISSUE_CASE_REF_DATA_SHOULD_NOT_BE_ARRAY_IN_CATCH,
                                            activity);
                                }

                                /*
                                 * XPD-7454: If the case data signal event is
                                 * followed by a Global Data Task with the same
                                 * Case Ref Process Relevent Data and with
                                 * link/unlink/update/delete operation then
                                 * raise a error so that the user knows that
                                 * having such flows can lead to un-ending
                                 * cycles.
                                 */
                                /*
                                 * Sid XPD-7863 - changed to have methdo raise
                                 * rules so can add updating activity named in
                                 * issue.
                                 */
                                checkDownstreamActivitiesChangeCaseData(activity,
                                        prdFound);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Look for downstream activities of the case signal event handler that may
     * alter the case data object referenced by the case signal. If that were
     * the case then we could go into an infinite loop catching our own
     * updates!!
     * <p>
     * Checks if the Case Data Signal Event has a downstream Global Data
     * Task(with link/unlink/delete/update operation) with the same Case Ref as
     * that of the passed Case Data Signal event, OR user task that has case ref
     * associated as Out or Inout (as that can change the case data object too.
     * 
     * @param activity
     *            the Case Data Signal Event
     * @param caseSignalCaseRefField
     *            the Process Relevant Data used by the Case Data Signal
     */
    private void checkDownstreamActivitiesChangeCaseData(Activity activity,
            ProcessRelevantData caseSignalCaseRefField) {
        /*
         * the flow container will either be process or the Event sub-Process
         */
        FlowContainer flowContainer = activity.getFlowContainer();

        if (flowContainer != null) {

            ProcessFlowAnalyser flowAnalyser =
                    getTool(ProcessFlowAnalyserPreProcessor.class,
                            flowContainer).getProcessFlowAnalyser();

            if (flowAnalyser != null) {
                /*
                 * get all the downstream activities to the Case Signal Data
                 * Event.
                 */
                Set<Activity> downstreamOfCaseDataSignalActs =
                        flowAnalyser.getDownstreamActivities(activity.getId(),
                                false);

                for (Activity downstreamActivity : downstreamOfCaseDataSignalActs) {
                    /*
                     * Sid XPD-7936 Refactored activity checking so that we can
                     * recursively do embedded sub-procfesses
                     */
                    checkActivityDoesntChangeCaseData(caseSignalCaseRefField,
                            downstreamActivity);
                }
            }
        }
    }

    /**
     * Add problem marker if the given activity (or its descendents) perform
     * operations which may change the given case signal reference field (as
     * this would cause infinite case signal loop).
     * 
     * @param caseSignalCaseRefField
     * @param downstreamActivity
     */
    private void checkActivityDoesntChangeCaseData(
            ProcessRelevantData caseSignalCaseRefField,
            Activity downstreamActivity) {

        /*
         * Sid XPD-7936 Refactored activity checking so that we can recursively
         * do embedded sub-processes
         */
        if (downstreamActivity.getBlockActivity() != null) {
            ActivitySet embeddedSubProcessActivitySet =
                    Xpdl2ModelUtil
                            .getEmbeddedSubProcessActivitySet(downstreamActivity);
            if (embeddedSubProcessActivitySet != null) {
                for (Activity activity : embeddedSubProcessActivitySet
                        .getActivities()) {
                    checkActivityDoesntChangeCaseData(caseSignalCaseRefField,
                            activity);
                }
            }
            return;
        }

        /*
         * get the case ref operation from the downstream activities
         */
        CaseReferenceOperationsType cacOperationFromActivity =
                getCaseRefOperationFromActivity(downstreamActivity);

        /*
         * Proceed only if we have a Global Data Task that updates case object
         * via case reference
         */
        if (cacOperationFromActivity != null) {
            /*
             * Sid XPD-7863 - there may be other tasks downstream that update
             * the case ref for case signal, so don't quit with false just
             * because THIS activity doesn't (changed to only return if
             * condition true.
             */

            if (cacOperationFromActivity.getCaseRefField() != null
                    && caseSignalCaseRefField.getName()
                            .equals(cacOperationFromActivity.getCaseRefField())) {
                addIssue(ISSUE_CASE_DATA_SIG_WITH_DOWNSTREAM_GLOBAL_DATA_TASK,
                        downstreamActivity,
                        Collections.singletonList(cacOperationFromActivity
                                .getCaseRefField()));
            }
        }
        /*
         * Sid XPD-7863 User tasks can also change global data if they have a
         * case-ref of OUT / INOUT mode.
         */
        else if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(downstreamActivity))) {
            Collection<ActivityInterfaceData> interfaceData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(downstreamActivity);

            for (ActivityInterfaceData ifcData : interfaceData) {
                ProcessRelevantData data = ifcData.getData();

                if (data != null && data.equals(caseSignalCaseRefField)
                        && data.getDataType() instanceof RecordType) {

                    if (ModeType.OUT_LITERAL.equals(ifcData.getMode())
                            || ModeType.INOUT_LITERAL.equals(ifcData.getMode())) {

                        addIssue(ISSUE_CASE_DATA_SIG_WITH_DOWNSTREAM_GLOBAL_DATA_TASK,
                                downstreamActivity,
                                Collections.singletonList(Xpdl2ModelUtil
                                        .getDisplayNameOrName(data)));

                    }
                }
            }
        }
    }

    /**
     * 
     * @param activity
     *            the activity whose Case Ref Operation is to be fetched
     * @return the <{@link CaseReferenceOperationsType} of the passed activity
     *         if it is a Global Data Service Task with Case Ref operation type,
     *         else return <code>null</code>
     */
    private CaseReferenceOperationsType getCaseRefOperationFromActivity(
            Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {

            Task task = (Task) implementation;
            if (task.getTaskService() != null) {

                EObject extendedModel =
                        TaskServiceExtUtil.getExtendedModel(task
                                .getTaskService(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_GlobalDataOperation());
                if (extendedModel instanceof GlobalDataOperation) {

                    /* We are interested only in Global Data Operations */
                    GlobalDataOperation globalDataOp =
                            (GlobalDataOperation) extendedModel;
                    /*
                     * Return the CaseReferenceOperations if its defined, else
                     * will return null.
                     */
                    return globalDataOp.getCaseReferenceOperations();
                }
            }
        }
        return null;
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
     * Checks if the process relevant data being referred by the case signal
     * event actually exists in the process and if NOT, then adds a problem
     * marker.
     * 
     * @param activity
     * @param signal
     */
    private ProcessRelevantData checkIfProcRelDataExists(Activity activity,
            TriggerResultSignal signal) {

        boolean dataFound = false;

        ProcessRelevantData prdFound = null;

        List<ProcessRelevantData> allData =
                ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(activity);

        /*
         * Go through every Process Relevant Data.
         */
        for (ProcessRelevantData eachPRD : allData) {

            /*
             * Check if the signal name matches witht the data name.
             */
            if (signal.getName().equals(eachPRD.getName())) {

                dataFound = true;
                prdFound = eachPRD;
                break;
            }
        }

        /*
         * If no data found, then add problem marker.
         */
        if (!dataFound) {

            List<String> msgs = new ArrayList<String>();

            msgs.add(signal.getName());

            addIssue(ISSUE_CASE_REF_DATA_NOT_FOUND, activity, msgs);
        }

        return prdFound;
    }
}

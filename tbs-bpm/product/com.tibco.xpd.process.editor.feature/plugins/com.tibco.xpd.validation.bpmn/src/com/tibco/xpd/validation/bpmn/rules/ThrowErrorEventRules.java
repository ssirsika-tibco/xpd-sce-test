/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.resources.util.EqualityHelperXpd;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rules for throw end error events.
 * 
 * @author aallway
 * @since 3.2
 */
public class ThrowErrorEventRules extends ProcessValidationRule {

    private static final String ISSUE_THROW_FAULT_MUST_ENTER_FAULT_NAME =
            "bpmn.throwErrorFaultMustEnterFaultName"; //$NON-NLS-1$

    private static final String ISSUE_REQUEST_ACTIVITY_OUT_OF_SCOPE =
            "bpmn.throwErrorRequestActivityOutOfScope"; //$NON-NLS-1$

    private static final String ISSUE_NO_REQUEST_ACTIVITY_SPECIFIED =
            "bpmn.throwErrorNoRequestActivitySpecified"; //$NON-NLS-1$

    private static final String ISSUE_NO_THROW_PROCESS_ERROR_CODE =
            "bpmn.throwErrorMustSpecifyErrorCode"; //$NON-NLS-1$

    private static final String ISSUE_THROW_ERROR_GENERATORS_SAME_PARAMS =
            "bpmn.throwErrorGeneratorsMustHaveSameParams"; //$NON-NLS-1$

    private static final String ISSUE_CANT_THROW_FAULT_FOR_REQUEST_ONLY =
            "bpmn.throwErrorCantThrowFaultForRequestOnlyOperation"; //$NON-NLS-1$

    /*
     * Sid ACE-473 Disable WSDL error event related rules. Only disabling rather
     * than removing in case we need to bring them back for the ACE equivalent
     * of Process-as-a-Service later on.
     */
    private boolean disableWSDLFaultIssues = true;

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            if (activity.getEvent() instanceof EndEvent) {
                EndEvent endEvent = (EndEvent) activity.getEvent();

                if (ResultType.ERROR_LITERAL.equals(endEvent.getResult())) {

                    if (ThrowErrorEventUtil
                            .isThrowFaultMessageErrorEvent(activity)) {
                        checkThrowFaultErrorRules(activity);
                    } else {
                        checkThrowProcessErrorRules(activity);
                    }
                }
            }

            if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
                checkIncomingRequestRules(activity);
            }
        }

        return;
    }

    /**
     * Check throw error rules from the perspective of an incoming request
     * activity.
     * 
     * @param requestActivity
     */
    private void checkIncomingRequestRules(Activity requestActivity) {
        if (disableWSDLFaultIssues) {
            return;
        }

        /*
         * All Throw Fault Message Error Events for the same auto-generated
         * request with the same fault name MUST have the same associated
         * parameters.
         * 
         * This is because each will HAVE to use the same fault message payload
         * schema - i.e. they are all exactly the same fault thrown from
         * different places.
         */
        List<Activity> throwErrorEvents =
                ThrowErrorEventUtil.getThrowErrorEvents(requestActivity);

        if (!Xpdl2ModelUtil.isEventImplemented(requestActivity)) {

            for (Activity errorEvent : throwErrorEvents) {
                /*
                 * Compare the effective fault names derived from error code
                 * rather than the error code.
                 */
                String faultName =
                        ThrowErrorEventUtil.getThrowErrorFaultName(errorEvent);
                if (faultName != null && faultName.length() > 0) {
                    boolean badParams = false;
                    LinkedHashSet<Activity> badEvents =
                            new LinkedHashSet<Activity>();
                    badEvents.add(errorEvent);

                    for (Activity otherErrorEvent : throwErrorEvents) {
                        if (otherErrorEvent != errorEvent) {
                            if (faultName.equals(ThrowErrorEventUtil
                                    .getThrowErrorFaultName(otherErrorEvent))) {
                                if (!compareAssociatedParams(errorEvent,
                                        otherErrorEvent)) {
                                    badParams = true;
                                    badEvents.add(otherErrorEvent);
                                }
                            }
                        }
                    }

                    if (badParams) {
                        addIssue(ISSUE_THROW_ERROR_GENERATORS_SAME_PARAMS,
                                errorEvent,
                                Collections
                                        .singletonList(getEventNames(badEvents)));
                    }
                }
            }
        }

        /*
         * If the request activity is not a request response then you cannot
         * have Fault Message Replies either!
         * 
         * RS- Applicable to those with WSDL generate destinations set
         */
        if (ProcessDestinationUtil
                .shouldGenerateWSDLForProcessDestinations(requestActivity
                        .getProcess())) {
            if (!ImplementInterfaceUtil.isRequestResponse(requestActivity)) {
                for (Activity errorEvent : throwErrorEvents) {
                    addIssue(ISSUE_CANT_THROW_FAULT_FOR_REQUEST_ONLY,
                            errorEvent);
                }
            }
        }

        return;
    }

    /**
     * @param badEvents
     * @return comma separate ;ist of event names.
     */
    private String getEventNames(LinkedHashSet<Activity> badEvents) {
        String names = ""; //$NON-NLS-1$

        for (Activity activity : badEvents) {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(activity);
            if (name == null || name.length() == 0) {
                name = Messages.ThrowErrorEventRules_UnnamedEvent_label;
            }

            if (names.length() == 0) {
                names = name;
            } else {
                names += ", " + name; //$NON-NLS-1$
            }
        }
        return names;
    }

    /**
     * @param errorEvent
     * @param otherErrorEvent
     * 
     * @return return true if the associated parameter configurations of the two
     *         activities is the same.
     */
    private boolean compareAssociatedParams(Activity errorEvent,
            Activity otherErrorEvent) {

        List<EObject> assocParams1 =
                new ArrayList<EObject>(ProcessInterfaceUtil
                        .getActivityAssociatedParameters(errorEvent));
        List<EObject> assocParams2 =
                new ArrayList<EObject>(ProcessInterfaceUtil
                        .getActivityAssociatedParameters(otherErrorEvent));

        EqualityHelperXpd eEqualityHelper = new EqualityHelperXpd();

        eEqualityHelper.addFeatureToIgnore(Xpdl2Package.eINSTANCE
                .getDescribedElement_Description());

        return eEqualityHelper.equals(assocParams1, assocParams2);
    }

    /**
     * Check rules for throw error end event configured to throw fault for
     * incoming request.
     * 
     * @param throwFaultErrorEvent
     */
    private void checkThrowFaultErrorRules(Activity throwFaultErrorEvent) {
        if (disableWSDLFaultIssues) {
            return;
        }

        if (!Xpdl2ModelUtil.isEventImplemented(throwFaultErrorEvent)) {

            Activity requestActivity =
                    ThrowErrorEventUtil
                            .getRequestActivity(throwFaultErrorEvent);
            if (requestActivity == null) {
                addIssue(ISSUE_NO_REQUEST_ACTIVITY_SPECIFIED,
                        throwFaultErrorEvent);

            } else {
                if (!ReplyActivityUtil
                        .isIncomingRequestActivity(requestActivity)) {
                    addIssue(ISSUE_NO_REQUEST_ACTIVITY_SPECIFIED,
                            throwFaultErrorEvent);
                }

                if (requestActivity.eContainer() != throwFaultErrorEvent
                        .eContainer()) {
                    addIssue(ISSUE_REQUEST_ACTIVITY_OUT_OF_SCOPE,
                            throwFaultErrorEvent);
                }

                String faultName =
                        ThrowErrorEventUtil
                                .getThrowErrorFaultName(throwFaultErrorEvent);
                if (faultName == null || faultName.trim().length() == 0) {
                    if (Xpdl2ModelUtil
                            .isGeneratedRequestActivity(requestActivity)
                            && !Xpdl2ModelUtil
                                    .isEventImplemented(requestActivity)) {
                        addIssue(ISSUE_THROW_FAULT_MUST_ENTER_FAULT_NAME,
                                throwFaultErrorEvent);
                    }
                }
            }
        }

        return;
    }

    /**
     * Check throw error events configured to throw process / sub-process error.
     * 
     * @param throwProcesErrorEvent
     */
    private void checkThrowProcessErrorRules(Activity throwProcesErrorEvent) {
        if (!Xpdl2ModelUtil.isEventImplemented(throwProcesErrorEvent)) {

            String errorCode =
                    ThrowErrorEventUtil
                            .getThrowErrorCode(throwProcesErrorEvent);
            if (errorCode != null) {
                errorCode = errorCode.trim();
            }

            if (errorCode == null
                    || errorCode.length() == 0
                    || ThrowErrorEventUtil.getFaultNameForErrorCode(errorCode)
                            .length() == 0) {
                addIssue(ISSUE_NO_THROW_PROCESS_ERROR_CODE,
                        throwProcesErrorEvent);
            }
        }
        return;
    }

}

/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.wsdl.Fault;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ThrowErrorEventRules
 * 
 * 
 * @author aallway
 * @since 3.3 (4 Dec 2009)
 */
public class ThrowErrorEventRules extends ProcessValidationRule {

    private static final String ISSUE_THROW_ERROR_NO_FAULT_SELECTED =
            "bpmn.dev.throwErrorNoFaultSelected"; //$NON-NLS-1$

    private static final String ISSUE_THROW_ERROR_BAD_FAULT_SELECTED =
            "bpmn.dev.throwErrorBadFaultSelected"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
                checkFaultThrown(activity);
            }
        }

        return;
    }

    /**
     * Check that a fault is selected from request activity's opoeration if the
     * given throw fault message error event references a user defined wsdl (or
     * is a process-defined non-implementing event that user has set to
     * reference an process-interface method implementing incoming request
     * event).
     * 
     * @param activity
     */
    private void checkFaultThrown(Activity activity) {
        Activity requestActivity =
                ThrowErrorEventUtil.getRequestActivity(activity);
        /*
         * Only care if request activity is found, someone else will complain
         * about that and until that's selcted, the user can't select a fault
         * message anyway!
         */
        if (requestActivity != null) {
            /*
             * If the request activity ref's an operation in a user defined WSDL
             * operation then the user has to select a fault from that
             * operation.
             * 
             * OR The throw error is one created by user that they have manually
             * configured to reference an implementing request activity.
             */
            if (!Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)
                    || (Xpdl2ModelUtil.isEventImplemented(requestActivity) && !Xpdl2ModelUtil
                            .isEventImplemented(activity))) {
                /*
                 * Then the throw error must reference an error for the
                 * operation in WSDL.
                 */
                String faultName =
                        ThrowErrorEventUtil.getThrowErrorFaultName(activity);

                if (faultName == null || faultName.trim().length() == 0) {
                    addIssue(ISSUE_THROW_ERROR_NO_FAULT_SELECTED, activity);

                } else {
                    Set<String> faultMessageNames =
                            getFaultMessageNames(requestActivity);
                    if (!faultMessageNames.contains(faultName)) {
                        addIssue(ISSUE_THROW_ERROR_BAD_FAULT_SELECTED,
                                activity,
                                Collections.singletonList(faultName));
                    }
                }
            }
        }
        return;
    }

    private Set<String> getFaultMessageNames(Activity webServiceActivity) {
        Set<String> faultNames = new HashSet<String>();

        Set<Fault> faults =
                Xpdl2WsdlUtil.getWsdlOperationFaults(webServiceActivity);
        if (faults != null) {
            for (Fault fault : faults) {
                faultNames.add(fault.getName());
            }
        }
        return faultNames;
    }
}

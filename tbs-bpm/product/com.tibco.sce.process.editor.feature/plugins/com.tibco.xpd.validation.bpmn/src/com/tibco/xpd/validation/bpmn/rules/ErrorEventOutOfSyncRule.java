/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class ErrorEventOutOfSyncRule extends ProcessValidationRule {

    private static final String ERROR_EVENT_OUT_OF_SYNC =
            "bpmn.errorEventOutOfSync";//$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            // If the activity is an implementing error activity, check if the
            // implemented activity is of Message type, and if it is verify
            // whether it is a Throw Fault Message Error Event
            if (Xpdl2ModelUtil.isEventImplemented(activity)) {
                ErrorMethod implementedErrorMethod =
                        ProcessInterfaceUtil
                                .getImplementedErrorMethod(activity);
                if (implementedErrorMethod != null) {
                    if (implementedErrorMethod.eContainer() instanceof InterfaceMethod) {
                        InterfaceMethod interfaceMethod =
                                (InterfaceMethod) implementedErrorMethod
                                        .eContainer();
                        boolean isMessageOperation =
                                TriggerType.MESSAGE_LITERAL
                                        .equals(interfaceMethod.getTrigger());
                        boolean isThrowFaultMessage =
                                ThrowErrorEventUtil
                                        .isThrowFaultMessageErrorEvent(activity);

                        if (isMessageOperation != isThrowFaultMessage) {
                            addIssue(ERROR_EVENT_OUT_OF_SYNC, activity);
                        }
                    }

                }
            }
        }
    }
}

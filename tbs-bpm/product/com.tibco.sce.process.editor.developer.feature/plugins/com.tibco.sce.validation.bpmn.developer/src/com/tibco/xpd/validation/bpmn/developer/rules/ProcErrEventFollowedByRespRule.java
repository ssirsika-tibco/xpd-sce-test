/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This rule is to validate that for any process that has its WSDL operation
 * generated, if an activity has a Throw End event associated, it must be of
 * type Request-Response, i.e it should either have a Reply Activity associated
 * or must have INOUT or IN and OUT params associated.
 * 
 * @author rsomayaj
 * @since 3.3 (17 Feb 2010)
 */
public class ProcErrEventFollowedByRespRule extends ProcessValidationRule {

    private static final String PROC_ERR_RESPONSE_FOLLOWING_RULE =
            "bpmn.dev.procErrResponseRequired"; //$NON-NLS-1$

    @Override
    public void validate(Object o) {
        if (o instanceof com.tibco.xpd.xpdl2.Process) {
            com.tibco.xpd.xpdl2.Process proc = (com.tibco.xpd.xpdl2.Process) o;
            if (ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessDestinations(proc)) {
                Collection<Activity> activitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                for (Activity activity : activitiesInProc) {
                    if (!ThrowErrorEventUtil.getThrowErrorEvents(activity)
                            .isEmpty()) {
                        if (!ImplementInterfaceUtil.isRequestResponse(activity)) {
                            addIssue(PROC_ERR_RESPONSE_FOLLOWING_RULE, activity);
                        }
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

    }
}

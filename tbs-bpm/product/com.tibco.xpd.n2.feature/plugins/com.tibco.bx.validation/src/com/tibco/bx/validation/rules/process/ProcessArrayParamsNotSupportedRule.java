/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check that array parameters/data fields are not present in
 * BPM destination packages.
 * 
 * @author rsomayaj
 * @since 3.3 (5 May 2010)
 */
public class ProcessArrayParamsNotSupportedRule extends ProcessValidationRule {

    /**
     * Array parameters are not supported, instead please create a business
     * object class to contain the array.
     */
    private static final String ARRAY_NOT_SUPPORTED =
            "cds.process.arrayfieldsNotAllowed"; //$NON-NLS-1$

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

        if (Xpdl2ModelUtil.isBusinessProcess(process)
                || ProcessInterfaceUtil.isProcessEngineServiceProcess(process)) {

            /* Only applies to business processes */
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : allActivitiesInProc) {

                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {

                    validateAssocParamsNotArray(activity);
                }
            }
        }
    }

    /**
     * @param activity
     */
    private void validateAssocParamsNotArray(Activity activity) {
        List<FormalParameter> associatedFormalParameters =
                ProcessInterfaceUtil.getAssociatedFormalParameters(activity);

        for (FormalParameter formalParameter : associatedFormalParameters) {
            if (formalParameter.isIsArray()) {
                addIssue(ARRAY_NOT_SUPPORTED, activity);
            }
        }
    }

}

/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * PageflowResourcePatternRules
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Nov 2009)
 */
public class PageflowResourcePatternRules extends ProcessValidationRule {

    private static final String ISSUE_CHAINED_EXECUTION_MENAINGLESS =
            "pageflow.chainedExecutionMeaningless"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            if (activity.getBlockActivity() != null) {
                if (Xpdl2ModelUtil.getOtherAttributeAsBoolean(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_IsChained())) {
                    addIssue(ISSUE_CHAINED_EXECUTION_MENAINGLESS, activity);
                }
            }
        }

        return;
    }

}

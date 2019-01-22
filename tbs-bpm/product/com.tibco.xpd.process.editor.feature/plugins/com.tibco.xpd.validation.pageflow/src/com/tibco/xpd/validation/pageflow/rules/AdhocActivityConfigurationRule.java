/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import java.util.Collection;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pageflow destination specific validation rule on Ad hoc activities that
 * raises the following problem(s):
 * 
 * <li>
 * Ad-Hoc user/sub-process activities are not supported in Pageflow processes.</li>
 * 
 * @author bharge
 * @since 26 Feb 2015
 */
public class AdhocActivityConfigurationRule extends ProcessValidationRule {

    private static final String ISSUE_ADHOC_ACTIVITY_SUPPORTED_FOR_BUSINESS_PROCESS =
            "pageflow.adhocActivitiesOnlySupportedForBusinessProcess"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        if (CapabilityUtil.isDeveloperActivityEnabled()) {

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : allActivitiesInProc) {

                Object adhocConfig =
                        Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AdHocTaskConfiguration());

                if (adhocConfig instanceof AdHocTaskConfigurationType) {

                    if (Xpdl2ModelUtil.isPageflowOrSubType(process)
                            || ProcessInterfaceUtil
                                    .isPageflowEngineServiceProcess(process)) {

                        addIssue(ISSUE_ADHOC_ACTIVITY_SUPPORTED_FOR_BUSINESS_PROCESS,
                                activity);
                    }
                }
            }
        }
    }
}

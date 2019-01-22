/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class specifies the validation restrictions on a Service Process
 * Interface.
 * <p>
 * Provides the following restrictions
 * <li>At least one deployment target configuration property must be selected
 * for Service Process Interface (if no target deployment configuration property
 * is selected on the Service Process Interface)</li>
 * 
 * @author bharge
 * @since 10 Feb 2015
 */
public class ServiceProcessInterfaceValidationRules extends
        InterfaceBaseValidationRule {

    /**
     * At least one deployment target configuration property must be selected
     * for Service Process
     */
    private static final String DEPLOYMENT_TARGET_NOT_SELECTED_ISSUE_ID =
            "bx.deploymentTargetNotSelectedOnServiceProcessInterface"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule#validate(com.tibco.xpd.xpdExtension.ProcessInterface)
     * 
     * @param processInterface
     */
    @Override
    public void validate(ProcessInterface processInterface) {

        if (Xpdl2ModelUtil.isServiceProcessInterface(processInterface)) {

            boolean isPageflowEngineTarget =
                    ProcessInterfaceUtil
                            .isPageflowEngineServiceProcessInterface(processInterface);
            boolean isProcessEngineTarget =
                    ProcessInterfaceUtil
                            .isProcessEngineServiceProcessInterface(processInterface);
            if (!isPageflowEngineTarget && !isProcessEngineTarget) {

                addIssue(DEPLOYMENT_TARGET_NOT_SELECTED_ISSUE_ID,
                        processInterface);
            }
        }
    }

}

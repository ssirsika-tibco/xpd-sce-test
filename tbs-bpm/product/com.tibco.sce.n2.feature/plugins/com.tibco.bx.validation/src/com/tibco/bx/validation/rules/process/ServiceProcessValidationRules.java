/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class specifies the validation restrictions on a Service Process.
 * <p>
 * Provides the following restrictions
 * 
 * <li>At least one deployment target configuration property must be selected
 * for Service Process (raised if no target deployment configuration property is
 * selected on the Service Process)</li>
 * 
 * <li>A Service Process can only implement Service Process Interface (raised
 * when a Service Process implements a normal Process Interface)</li>
 * 
 * <li>Any other process type can only implement Process Interface but not
 * Service Process Interface</li>
 * 
 * <li>Publish as REST Service is not supported for Service Processes</li>
 * 
 * </p>
 * 
 * @author bharge
 * @since 10 Feb 2015
 */
public class ServiceProcessValidationRules extends ProcessValidationRule {

    /**
     * At least one deployment target configuration property must be selected
     * for Service Process
     */
    private static final String DEPLOYMENT_TARGET_NOT_SELECTED_ISSUE_ID =
            "bx.deploymentTargetNotSelectedOnServiceProcess"; //$NON-NLS-1$

    /**
     * A Service Process can only implement Service Process Interface
     */
    private static final String IMPLEMENTS_INTERFACE_ISSUE_ID =
            "bx.serviceProcessMustImplementServiceProcessInterface"; //$NON-NLS-1$

    /**
     * Any other process type can only implement Process Interface
     */
    private static final String IMPLEMENTS_SERVICE_PROCESS_INTERFACE_ISSUE_ID =
            "bx.otherProcessCannotImplementServiceProcessInterface"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        if (Xpdl2ModelUtil.isServiceProcess(process)) {

            /* If a Service Process implements a normal process interface */
            ProcessInterface implementedInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);
            if (null != implementedInterface
                    && !Xpdl2ModelUtil
                            .isServiceProcessInterface(implementedInterface)) {

                addIssue(IMPLEMENTS_INTERFACE_ISSUE_ID, process);
                return;
            }

            boolean isPageflowEngineTarget =
                    ProcessInterfaceUtil
                            .isPageflowEngineServiceProcess(process);
            boolean isProcessEngineTarget =
                    ProcessInterfaceUtil.isProcessEngineServiceProcess(process);
            /* If none of the deployment target is selected, raise an issue */
            if (!isPageflowEngineTarget && !isProcessEngineTarget) {

                addIssue(DEPLOYMENT_TARGET_NOT_SELECTED_ISSUE_ID, process);
            }

            /*
             * XPD-7256: Publish As REST Service on Service Process is now
             * supported.
             */

        } else {

            /* If any other process type implements a Service Process Interface */
            ProcessInterface implementedInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);
            if (null != implementedInterface
                    && Xpdl2ModelUtil
                            .isServiceProcessInterface(implementedInterface)) {

                addIssue(IMPLEMENTS_SERVICE_PROCESS_INTERFACE_ISSUE_ID, process);
            }
        }
    }
}

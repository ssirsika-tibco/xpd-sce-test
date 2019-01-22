/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * 
 * @author mtorres
 */
public class JsWebServiceUnmappedScriptsRule extends
        FlowContainerValidationRule {
    private static final String UNMAPPED_UDS_DATA_NOT_SUPPORTED =
            "bx.unmappedUDSNotSupported"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            List<ScriptInformation> activityUnmappedScriptInformations =
                    ProcessScriptUtil
                            .getActivityUnmappedScriptInformations(activity,
                                    JsConsts.JAVASCRIPT_GRAMMAR);
            if (activityUnmappedScriptInformations != null
                    && !activityUnmappedScriptInformations.isEmpty()) {
                for (ScriptInformation scriptInformation : activityUnmappedScriptInformations) {
                    addIssue(UNMAPPED_UDS_DATA_NOT_SUPPORTED, scriptInformation);
                }
            }
        }
    }
}
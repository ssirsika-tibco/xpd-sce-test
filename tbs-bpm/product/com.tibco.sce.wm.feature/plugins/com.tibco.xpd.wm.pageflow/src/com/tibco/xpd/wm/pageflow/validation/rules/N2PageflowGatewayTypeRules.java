/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.GatewayObjectUtil;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * N2PageflowGatewayTypeRules
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Nov 2009)
 */
public class N2PageflowGatewayTypeRules extends ProcessValidationRule {

    private static final String ISSUE_GATEWAYTYPE_NOT_SUPPORTED =
            "wm.pageflow.gatewayTypeNotSupported"; //$NON-NLS-1$

    /**
     * GatewayTypes supported by Page flows.
     */
    private static final List<GatewayType> SUPPORTED_GATEWAY_TYPES = Arrays
            .asList(GatewayType.EXCLUSIVE_DATA_LITERAL,
                    GatewayType.PARALLEL_LITERAL,
                    GatewayType.INCLUSIVE_LITERAL);

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            if (activity.getRoute() != null) {
                validateGateway(activity);
            }
        }

        return;
    }

    /**
     * @param activity
     */
    private void validateGateway(Activity activity) {
        GatewayType type = GatewayObjectUtil.getGatewayType(activity);

        if (!SUPPORTED_GATEWAY_TYPES.contains(type)) {
            addIssue(ISSUE_GATEWAYTYPE_NOT_SUPPORTED,
                    activity,
                    Collections.singletonList(type.toString()));
        }

        return;
    }
}

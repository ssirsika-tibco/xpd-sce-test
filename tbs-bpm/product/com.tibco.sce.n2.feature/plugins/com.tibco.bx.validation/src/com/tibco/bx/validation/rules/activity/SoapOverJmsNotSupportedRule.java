/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * (temporarily) Validate against use of soap over jms transport on process api
 * activities (web service invoke activities are supported).
 * 
 * @author aallway
 * @since 3.3 (13 May 2010)
 */
public class SoapOverJmsNotSupportedRule extends FlowContainerValidationRule {

    private static final String ISSUE_JMS_NOT_SUPPORTED =
            "bx.soapOverJmsNotSupported"; //$NON-NLS-1$

    @Override
    public void validate(FlowContainer container) {

        for (Activity activity : container.getActivities()) {

            if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
                String transport =
                        Xpdl2ModelUtil.getWebServiceTransport(activity);
                if (Xpdl2WsdlUtil.XML_OVER_JMS_URL.equals(transport)) {
                    addIssue(ISSUE_JMS_NOT_SUPPORTED, activity);
                }
            }
        }

        return;
    }
}

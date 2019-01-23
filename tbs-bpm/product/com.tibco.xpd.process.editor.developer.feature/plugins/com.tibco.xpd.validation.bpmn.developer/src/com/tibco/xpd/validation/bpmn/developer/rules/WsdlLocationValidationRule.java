/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.implementer.resources.xpdl2.precommit.FixWsdlEndpointLocationPreCommitListener;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * WsdlLocationValidationRule
 * <p>
 * Rule to ensure that the WSDL external reference location in a web-service
 * related activity is correct for the rest of the activity's setup.
 * 
 * @author aallway
 * @since 3.3 (8 Oct 2009)
 */
public class WsdlLocationValidationRule extends ProcessValidationRule {

    private static final String WRONG_WSDL_LOCATION_FOR_CONFIG =
            "bpmn.dev.wrongWsdlLocationForActivity"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            ActivityMessageProvider activityMessageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (activityMessageProvider != null) {
                /*
                 * It's a web-service related task/event. Allow auto-generating
                 * / reply activities to look after themselves.
                 */
                if (!ReplyActivityUtil.isReplyActivity(activity)
                        && !Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {

                    checkCorrectWsdlLocation(activity, activityMessageProvider);

                }
            }
        }
    }

    /**
     * @param activity
     * @param activityMessageProvider
     */
    private void checkCorrectWsdlLocation(Activity activity,
            ActivityMessageProvider activityMessageProvider) {
        String correctLocation =
                FixWsdlEndpointLocationPreCommitListener
                        .getCorrectWsdlExternalReferenceLocation(activity);
        if (correctLocation != null && correctLocation.length() > 0) {
            ExternalReference endpointLocation = null;
            ExternalReference portTypeOperationLocation = null;

            WebServiceOperation wso =
                    activityMessageProvider.getWebServiceOperation(activity);
            if (wso != null) {
                Service ws = wso.getService();
                if (ws != null) {
                    EndPoint endpoint = ws.getEndPoint();
                    if (endpoint != null) {
                        endpointLocation = endpoint.getExternalReference();
                    }
                }
            }

            PortTypeOperation pto =
                    activityMessageProvider.getPortTypeOperation(activity);
            if (pto != null) {
                portTypeOperationLocation = pto.getExternalReference();
            }

            if ((endpointLocation != null && !correctLocation
                    .equals(endpointLocation.getLocation()))
                    || (portTypeOperationLocation != null && !correctLocation
                            .equals(portTypeOperationLocation.getLocation()))) {
                addIssue(WRONG_WSDL_LOCATION_FOR_CONFIG, activity);
            }
        }

        return;
    }
}

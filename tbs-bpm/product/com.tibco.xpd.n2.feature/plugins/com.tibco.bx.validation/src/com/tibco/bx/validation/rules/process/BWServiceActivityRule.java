/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BWServiceActivityRule
 * <p>
 * Check the WebServices of activities and ensures they are not BW Services.
 * 
 * @author glewis
 */
public class BWServiceActivityRule extends ProcessValidationRule {

    private static final String BWSERVICE_NOT_SUPPORTED =
            "bx.bwNotAllowed"; //$NON-NLS-1$
   
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        validateProcessActivities(activities);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com
     * .tibco.xpd.xpdl2.Process)
     */
    private void validateProcessActivities(EList<Activity> activities) {

        for (Activity activity : activities) {
            /*
             * Attempt to get the web-service message provider for activity - if
             * it isn't a web-service related task/event then we get null.
             */
            ActivityMessageProvider messageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (messageProvider != null
                    && messageProvider.isActualWebServiceActivity()) {
                /*
                 * Don't bother with reply activity (as content is copied from
                 * request activity, there is no point complaining about the
                 * reply).
                 */
                if (!ReplyActivityUtil.isReplyActivity(activity)) {
                    checkIfBWService(activity,
                            messageProvider);

                }
            }
        } /* Next Activity */

        return;
    }

    /**
     * Checks the web service activity to see if it is a BW Service.
     * 
     * @param activity
     * @param messageProvider
     */
    private void checkIfBWService(Activity activity,
            ActivityMessageProvider messageProvider) {

        WebServiceOperation webServiceOperation =
                messageProvider.getWebServiceOperation(activity);

        PortTypeOperation portTypeOperation =
                messageProvider.getPortTypeOperation(activity);

        if (webServiceOperation != null
                && isWebServiceOperationAssigned(webServiceOperation,
                        portTypeOperation)) {
        	
        	Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskService taskService = task.getTaskService();
                if (taskService != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (TaskImplementationTypeDefinitions.BW_SERVICE.equals(type)) {
                		addIssue(BWSERVICE_NOT_SUPPORTED, activity);
                	}
                }
            }            
        }
        return;
    }

    /**
     * @param webServiceOperation
     * @param portTypeOperation
     * 
     * @return true if the activity has a web service operation configured.
     */
    private boolean isWebServiceOperationAssigned(
            WebServiceOperation webServiceOperation,
            PortTypeOperation portTypeOperation) {
        String operationName = null;

        if (portTypeOperation != null) {
            operationName = portTypeOperation.getOperationName();
        }

        if (operationName == null || operationName.length() == 0) {
            if (webServiceOperation != null) {
                operationName = webServiceOperation.getOperationName();
            }
        }

        if (operationName != null && operationName.length() > 0) {
            return true;
        }

        return false;
    }
}

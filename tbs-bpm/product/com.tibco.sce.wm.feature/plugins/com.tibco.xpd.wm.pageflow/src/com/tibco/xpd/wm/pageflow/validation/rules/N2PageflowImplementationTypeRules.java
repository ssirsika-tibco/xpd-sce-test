/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.wm.pageflow.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * N2PageflowImplementationTypeRules
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Nov 2009)
 */
public class N2PageflowImplementationTypeRules extends ProcessValidationRule {

    private static final String ISSUE_UNSUPPORTED_IMPLEMENTTION_TYPE =
            "wm.pageflow.unsupportedImplementationType"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        Set<String> supportedImplementationTypes =
                getSupportedImplementationTypes();

        for (Activity activity : activities) {
            String implementationType = getImplementationType(activity);

            if (implementationType != null)
                if (!supportedImplementationTypes.contains(implementationType)) {
                    addIssue(ISSUE_UNSUPPORTED_IMPLEMENTTION_TYPE,
                            activity,
                            Collections.singletonList(implementationType));
                }
        }

        return;
    }

    /**
     * @return
     */
    private Set<String> getSupportedImplementationTypes() {
        Set<String> supportedImplementationTypes = new HashSet<String>();
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.WEB_SERVICE);
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.WEB_SERVICE_V2_0);
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.JAVA_SERVICE);
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.DATABASE_SERVICE);
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.EMAIL_SERVICE);
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.DECISION_SERVICE);
        // XPD-5110 Support for global data task type
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.GLOBAL_DATA);
        /* XPD-6411 Document Operations Task Type */
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.DOCUMENT_OPERATIONS);
        // XPD-288
        supportedImplementationTypes.add(ActionUtil.INVOKE_BUSINESS_PROCESS);

        /* XPD-7074 - support REST service. */
        supportedImplementationTypes
                .add(TaskImplementationTypeDefinitions.REST_SERVICE);

        return supportedImplementationTypes;
    }

    /**
     * @param activity
     * @return implementation type of activity
     */
    private String getImplementationType(Activity activity) {
        String implementationType = null;
        boolean implementableActivityType = false;

        if (activity.getEvent() != null) {
            if (activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {
                implementableActivityType = true;
                implementationType =
                        (String) Xpdl2ModelUtil.getOtherAttribute(activity
                                .getEvent(), XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());

            }

        } else if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();

            if (task.getTaskService() != null) {
                implementableActivityType = true;
                implementationType =
                        (String) Xpdl2ModelUtil.getOtherAttribute(task
                                .getTaskService(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());

            } else if (task.getTaskSend() != null) {
                implementableActivityType = true;
                implementationType =
                        (String) Xpdl2ModelUtil.getOtherAttribute(task
                                .getTaskSend(), XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());

            } else if (task.getTaskReceive() != null) {
                implementableActivityType = true;
                implementationType =
                        (String) Xpdl2ModelUtil.getOtherAttribute(task
                                .getTaskReceive(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());
            }
        }

        if (implementableActivityType && implementationType == null) {
            implementationType =
                    Messages.N2PageflowImplementationTypeRules_Unspecified_label;
        }

        return implementationType;
    }

}

/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * UserTaskNoFormURIRule - validates if a user task with User Defined URL option
 * has not specified any url pointing to any form
 * 
 * 
 * @author bharge
 * @since 3.3 (12 Feb 2010)
 */
public class UserTaskNoFormURIRule extends ProcessValidationRule {
    private static final String NO_FORM_URI_ISSUE_ID =
            "bpmn.userTaskNoUserDefinedURL"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskUser taskUser = task.getTaskUser();
                if (null != taskUser) {
                    Object otherElement =
                            Xpdl2ModelUtil
                                    .getOtherElement(taskUser,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_FormImplementation());
                    if (otherElement instanceof FormImplementation) {
                        FormImplementation formImplementation =
                                (FormImplementation) otherElement;
                        if (formImplementation.getFormType()
                                .equals(FormImplementationType.USER_DEFINED)) {
                            if (formImplementation.getFormURI().length() == 0
                                    || formImplementation.getFormURI()
                                            .equals("")) { //$NON-NLS-1$
                                addIssue(NO_FORM_URI_ISSUE_ID, activity);
                            }
                        }
                    }
                }
            }
        }
    }
}

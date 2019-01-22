/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.n2.ut.resources.rules;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
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
 * UserTaskNoFormsFolderRule - validates if a user task invoking forms has no
 * forms special folder on the project
 * 
 * 
 * @author bharge
 * @since 3.3 (9 Feb 2010)
 */
public class UserTaskNoFormsFolderRule extends ProcessValidationRule {
    private static final String NO_FORMS_FOLDER_ISSUE_ID =
            "n2.ut.userTaskWithoutFormsFolder"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
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

                    boolean isFormImpl =
                            isUserTaskInvokingForm(taskUser, otherElement);

                    if (null == otherElement || isFormImpl) {
                        WorkingCopy workingCopyFor =
                                WorkingCopyUtil.getWorkingCopyFor(process);
                        IResource resource =
                                workingCopyFor.getEclipseResources().get(0);
                        IProject project = resource.getProject();
                        ProjectConfig config =
                                XpdResourcesPlugin.getDefault()
                                        .getProjectConfig(project);
                        if (config != null) {
                            EList<SpecialFolder> folders =
                                    config
                                            .getSpecialFolders()
                                            .getFoldersOfKind(Xpdl2ResourcesConsts.FORMS_SPECIAL_FOLDER_KIND);
                            if (folders == null || folders.isEmpty()) {
                                addIssue(NO_FORMS_FOLDER_ISSUE_ID, activity);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isUserTaskInvokingForm(TaskUser taskUser,
            Object otherElement) {
        if (otherElement instanceof FormImplementation) {
            FormImplementation formImplementation =
                    (FormImplementation) otherElement;
            if (formImplementation.getFormType()
                    .equals(FormImplementationType.FORM)) {
                return true;
            } else if (formImplementation.getFormType()
                    .equals(FormImplementationType.PAGEFLOW)) {
                return false;
            }

            /* SID XPD-1924: User defined form url is now to be supported. */
            // if (formImplementation.getFormType()
            // .equals(FormImplementationType.USER_DEFINED)) {
            // addIssue(NO_FORM_URI_SUPPORTED_ISSUE_ID, taskUser);
            // }
        }
        return false;
    }
}

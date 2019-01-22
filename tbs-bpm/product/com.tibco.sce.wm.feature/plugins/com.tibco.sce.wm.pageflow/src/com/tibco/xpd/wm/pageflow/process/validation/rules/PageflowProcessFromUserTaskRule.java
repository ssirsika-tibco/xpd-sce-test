/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.process.validation.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * validates if a user task (in business process) is invoking a business service
 * 
 * 
 * @author bharge
 * @since 3.3 (13 May 2010)
 */
public class PageflowProcessFromUserTaskRule extends ProcessValidationRule {

    private static final String ISSUE_ID =
            "wm.pageflowProcess.businessServiceIgnoredFromUserTask"; //$NON-NLS-1$

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
        for (Activity activity : process.getActivities()) {
            if (activity.getImplementation() instanceof Task) {
                TaskUser taskUser =
                        ((Task) activity.getImplementation()).getTaskUser();
                if (null != taskUser) {
                    Process pageflowProcess =
                            TaskObjectUtil.getUserTaskPageflowProcess(activity);
                    if (null != pageflowProcess
                            && Xpdl2ModelUtil
                                    .isPageflowBusinessService(pageflowProcess)) {
                        addIssue(ISSUE_ID, pageflowProcess);
                    }
                }
            }
        }
    }

}

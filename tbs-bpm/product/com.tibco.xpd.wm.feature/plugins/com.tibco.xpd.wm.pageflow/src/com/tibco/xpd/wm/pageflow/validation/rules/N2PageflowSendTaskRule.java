/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Task;

/**
 * rule that disallows cross project business process invocation from send task
 * 
 * 
 * 
 * @author bharge
 * @since 3.3 (10 May 2010)
 */
public class N2PageflowSendTaskRule extends FlowContainerValidationRule {

    private static final String SEND_TASK_ISSUE_ID =
            "wm.pageflow.crossProjInvBizProcNotSupported"; //$NON-NLS-1$

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
            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
            if (TaskType.SEND_LITERAL.equals(taskType)) {
                if (WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(((Task) activity
                                .getImplementation()).getTaskSend())) {

                    EObject businessProcessRefd =
                            TaskObjectUtil.getSubProcessOrInterface(activity);
                    if (null != businessProcessRefd
                            && !isInSameProject(activity, businessProcessRefd)) {
                        addIssue(SEND_TASK_ISSUE_ID, activity);
                    }
                }
            }
        }
    }

    /**
     * @param activity
     * @param subProcessOrInterface
     * 
     * @return true if the two objects are in the same project
     */
    private boolean isInSameProject(EObject referencer, EObject referencee) {

        IProject referencerProject = WorkingCopyUtil.getProjectFor(referencer);
        IProject referenceeProject = WorkingCopyUtil.getProjectFor(referencee);

        if (referencerProject != null && referenceeProject != null) {
            return referenceeProject.equals(referencerProject);
        }

        return false;
    }
}

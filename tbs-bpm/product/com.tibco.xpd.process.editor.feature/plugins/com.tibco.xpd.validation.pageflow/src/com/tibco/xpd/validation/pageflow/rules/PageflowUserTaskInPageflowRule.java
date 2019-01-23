/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * PageflowuserTaskInPageflowRule
 * 
 * 
 * @author aallway
 * @since 3.3 (4 Nov 2009)
 */
public class PageflowUserTaskInPageflowRule extends ProcessValidationRule {

    private static final String PAGEFLOWTASK_IN_PAGEFLOWPROC =
            "pageflow.pageflowTaskInPageflowProcess"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            if (Xpdl2ModelUtil.isPageflow(activity.getProcess())) {
                FormImplementation formImplementation =
                        TaskObjectUtil.getUserTaskFormImplementation(activity);
                if (formImplementation != null) {
                    if (FormImplementationType.PAGEFLOW
                            .equals(formImplementation.getFormType())) {

                        addIssue(PAGEFLOWTASK_IN_PAGEFLOWPROC, activity);
                    }
                }
            }
        }

        return;
    }

}

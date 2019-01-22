/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * SubProcessRules
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Nov 2009)
 */
public class N2PageflowSubProcessRules extends ProcessValidationRule {

    private static final String ISSUE_TRANSACTION_SUBPROCESS_NOT_SUPPORTED =
            "wm.pageflow.transactionSubProcNotSupported"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

            if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType)
                    || TaskType.SUBPROCESS_LITERAL.equals(taskType)) {
                if (activity.isIsATransaction()) {
                    addIssue(ISSUE_TRANSACTION_SUBPROCESS_NOT_SUPPORTED,
                            activity);
                }
            }
        }

        return;
    }
}

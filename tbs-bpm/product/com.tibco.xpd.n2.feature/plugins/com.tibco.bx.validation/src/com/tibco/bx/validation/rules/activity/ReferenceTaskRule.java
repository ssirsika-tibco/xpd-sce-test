/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * Validate against the use of reference tasks.
 * 
 * @author aallway
 * @since 3.3 (29 Apr 2010)
 */
public class ReferenceTaskRule extends FlowContainerValidationRule {

    public static final String ISSUE_REFERENCE_TASK_NOT_SUPPORTED =
            "bx.referenceTasksNotSupported"; //$NON-NLS-1$

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
            if (TaskType.REFERENCE_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {
                addIssue(ISSUE_REFERENCE_TASK_NOT_SUPPORTED, activity);
            }
        }
        return;
    }
}

/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * N2PageflowTaskLoopRules
 * 
 * 
 * @author aallway
 * @since 3.3 (11 Nov 2009)
 */
public class N2PageflowTaskLoopRules extends ProcessValidationRule {

    private static final String ISSUE_STANDARD_LOOP_EXPRESSION_REQUIRED =
            "wm.pageflow.standardLoopExpressionRequired"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {

            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

            if (taskType != null) {
                /*
                 * Sid XPD-1930. Multi-instance now supported. (removed check
                 * for it).
                 */

                checkStandardLoop(activity);
            }
        }
        return;
    }

    /**
     * Check standard loop setup.
     * 
     * @param activity
     */
    private void checkStandardLoop(Activity activity) {
        Loop loop = activity.getLoop();
        if (loop != null) {
            if (LoopType.STANDARD_LITERAL.equals(loop.getLoopType())) {
                String loopExpr = null;

                if (loop.getLoopStandard() != null) {
                    Expression expr = loop.getLoopStandard().getLoopCondition();
                    if (expr != null && expr.getText() != null) {
                        loopExpr = expr.getText();
                    }
                }

                if (loopExpr == null || loopExpr.trim().length() == 0) {
                    addIssue(ISSUE_STANDARD_LOOP_EXPRESSION_REQUIRED, activity);
                }
            }
        }

        return;
    }

    private boolean isMultiInstance(Activity activity) {
        Loop loop = activity.getLoop();
        if (loop != null) {
            if (LoopType.MULTI_INSTANCE_LITERAL.equals(loop.getLoopType())) {
                return true;
            }
        }

        return false;
    }
}

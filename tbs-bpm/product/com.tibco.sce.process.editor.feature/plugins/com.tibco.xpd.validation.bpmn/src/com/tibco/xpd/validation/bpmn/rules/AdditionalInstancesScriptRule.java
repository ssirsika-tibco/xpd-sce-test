/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.MIFlowConditionType;

/**
 * 
 * <p>
 * <i>Created: 14 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class AdditionalInstancesScriptRule extends FlowContainerValidationRule {
    /** The issue id. */
    private static final String ALL_FLOW_CONDITION_SCRIPT_IGNORED =
            "bpmn.AllFlowConditionScriptWillBeIgnored"; //$NON-NLS-1$

    private static final String MI_LOOP_EXPR_REQUIRED =
            "bpmn.MILoopExpressionIsRequired"; //$NON-NLS-1$

    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            MIFlowConditionType flowCondition =
                    TaskObjectUtil.getMIFlowCondition(activity);
            if (flowCondition == null) {
                continue;
            }

            Expression additionalInstancesExpr =
                    ProcessScriptUtil.getMIAdditionalLoopExpression(activity);

            Expression loopExpression =
                    ProcessScriptUtil.getMILoopExpression(activity);

            if (loopExpression == null || loopExpression.getText() == null
                    || loopExpression.getText().trim().length() == 0) {
                addIssue(MI_LOOP_EXPR_REQUIRED, activity);
            }
            if (MIFlowConditionType.ALL_LITERAL != flowCondition) {
                if (additionalInstancesExpr != null
                        && additionalInstancesExpr.getText() != null
                        && additionalInstancesExpr.getText().trim().length() != 0) {
                    // raise a warning, script will be ignored as
                    // the flow condition is not ALL
                    addIssue(ALL_FLOW_CONDITION_SCRIPT_IGNORED, activity);
                }
            } else {
                /*
                 * SID XPD-112 Not sure why any one ever said that
                 * "Additional instances script is required as flow condition is 'All'."
                 * but it isn't true (nmay be just have assumed that opposite of
                 * "Additional instances script will be ignored as flow condition is not 'All'."
                 * which probably is true because if you've already stopped
                 * evaluating loop conditions it will be too late to add
                 * additionals and "All" is the only one that is guaranteed to
                 * process scripts until all instnces completed.
                 */
                String expressionAsString = null;
                if (additionalInstancesExpr != null) {
                    expressionAsString =
                            ProcessScriptUtil
                                    .getExpressionAsString(additionalInstancesExpr);
                }

                // if (expressionAsString == null
                // || expressionAsString.length() < 1) {
                // // raise a warning, script is required as the condition
                // // is ALL
                // addIssue(ALL_FLOW_CONDITION_SCRIPT_REQUIRED, activity);
                // }
            }

        }

        return;
    }

}

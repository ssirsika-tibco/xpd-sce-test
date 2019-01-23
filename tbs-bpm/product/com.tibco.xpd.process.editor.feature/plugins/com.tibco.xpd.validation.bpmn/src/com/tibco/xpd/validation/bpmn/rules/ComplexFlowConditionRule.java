/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.MIFlowConditionType;
import com.tibco.xpd.xpdl2.Process;

/**
 * This class validates whether the complex flow condition script has been
 * defined when the flowCondition attribute has been set to Complex.
 * <p>
 * <i>Created: 14 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class ComplexFlowConditionRule extends FlowContainerValidationRule {

    /** The issue id. */
    private static final String COMPLEX_FLOW_CONDITION_SCRIPT_IGNORED =
            "bpmn.ComplexFlowConditionScriptWillBeIgnored"; //$NON-NLS-1$

    private static final String COMPLEX_FLOW_CONDITION_SCRIPT_REQUIRED =
            "bpmn.ComplexFlowConditionScriptIdsRequired"; //$NON-NLS-1$

    @Override
    public void validate(FlowContainer container) {
        if (container instanceof Process) {
            Process process = (Process) container;
            List<Activity> activities =
                    new ArrayList<>(process.getActivities());
            for (Object object : activities) {
                Activity activity = (Activity) object;
                MIFlowConditionType flowCondition =
                        TaskObjectUtil.getMIFlowCondition(activity);
                Expression complexExitExpression =
                        ProcessScriptUtil.getMIComplexExitExpression(activity);
                if (flowCondition == null) {
                    continue;
                }
                if (MIFlowConditionType.COMPLEX_LITERAL != flowCondition) {
                    // raise a warning, script will be ignored as
                    // the flow condition is not complex
                    if (complexExitExpression != null) {
                        addIssue(COMPLEX_FLOW_CONDITION_SCRIPT_IGNORED,
                                activity);
                    }
                } else {
                    // raise a warning, as the flow condition is complex
                    // but no script defined
                    String expressionAsString = null;
                    if (complexExitExpression != null) {
                        expressionAsString =
                                ProcessScriptUtil
                                        .getExpressionAsString(complexExitExpression);
                    }
                    if (expressionAsString == null
                            || expressionAsString.length() < 1) {
                        addIssue(COMPLEX_FLOW_CONDITION_SCRIPT_REQUIRED,
                                activity);
                    }
                }

            }

        }
    }
}

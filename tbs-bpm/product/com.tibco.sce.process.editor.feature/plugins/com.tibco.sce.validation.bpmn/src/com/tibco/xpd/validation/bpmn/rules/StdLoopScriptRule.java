/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * <p>
 * <i>Created: 22 Apr 2008</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class StdLoopScriptRule extends FlowContainerValidationRule {

    private static final String STD_LOOP_EXPR_REQUIRED = "bpmn.StandardLoopExpressionRequired"; //$NON-NLS-1$

    @Override
    public void validate(FlowContainer container) {
        if (container instanceof Process) {
            Process process = (Process) container;
            EList activities = process.getActivities();
            if (activities == null) {
                return;
            }
            for (Object object : activities) {
                Activity activity = (Activity) object;
                LoopStandard standardLoop = TaskObjectUtil
                        .getStandardLoop(activity);
                if (standardLoop == null) {
                    continue;
                }
                Expression stdLoopExpression = ProcessScriptUtil
                        .getStdLoopExpression(activity);
                if (stdLoopExpression == null) {
                    addIssue(STD_LOOP_EXPR_REQUIRED, activity);
                }
            }
        }
    }

}

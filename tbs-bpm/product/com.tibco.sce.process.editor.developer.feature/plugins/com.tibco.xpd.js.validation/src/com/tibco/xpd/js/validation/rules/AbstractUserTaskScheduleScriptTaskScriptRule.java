/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.UserTaskScheduleScriptTaskScriptTool;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * User task schedule script validation rule.
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */

public abstract class AbstractUserTaskScheduleScriptTaskScriptRule extends
        AbstractExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {

        if (XpdExtensionPackage.eINSTANCE.getUserTaskScripts_ScheduleScript()
                .equals(expression.eContainmentFeature())) {

            Activity activity =
                    (Activity) Xpdl2ModelUtil.getAncestor(expression,
                            Activity.class);

            if (activity != null
                    && activity.getImplementation() instanceof Task) {

                Task task = (Task) activity.getImplementation();
                TaskUser userTask = task.getTaskUser();

                if (userTask != null) {

                    return getScope()
                            .getTool(UserTaskScheduleScriptTaskScriptTool.class,
                                    activity);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getExpressionHostForScriptTool(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected UniqueIdElement getExpressionHostForScriptTool(
            Expression expression) {

        return (Activity) Xpdl2ModelUtil
                .getAncestor(expression, Activity.class);

    }

    @Override
    protected String getScriptContext() {
        return ProcessScriptContextConstants.SCHEDULE_USER_TASK;
    }
}

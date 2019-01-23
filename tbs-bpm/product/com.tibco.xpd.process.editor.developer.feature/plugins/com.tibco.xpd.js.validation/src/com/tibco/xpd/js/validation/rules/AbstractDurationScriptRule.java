/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.DurationScriptsTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Duration script validation rule.
 * 
 * 
 * @author agondal
 * @since 25 Sep 2013
 */
public abstract class AbstractDurationScriptRule extends AbstractExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {

        if (expression.eContainer() instanceof DurationCalculation) {

            // if its defined on an activity
            Activity activity =
                    (Activity) Xpdl2ModelUtil.getAncestor(expression,
                            Activity.class);
            if (activity != null) {
                return getScope().getTool(DurationScriptsTool.class, activity);

            }

            // if its defined on a process
            Process process =
                    (Process) Xpdl2ModelUtil.getAncestor(expression,
                            Process.class);
            if (process != null) {
                return getScope().getTool(DurationScriptsTool.class, process);
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
        /*
         * Check if its defined on an activity or on a process and return
         * accordingly
         */
        Activity activity =
                (Activity) Xpdl2ModelUtil.getAncestor(expression,
                        Activity.class);
        if (activity != null) {
            return activity;
        }
        Process process =
                (Process) Xpdl2ModelUtil.getAncestor(expression, Process.class);
        if (process != null) {
            return process;
        }
        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessScriptContextConstants.DURATION_CALCULATION;
    }
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.DeadlineExpiredScriptTaskScriptTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Deadline expired script task validation rule
 * 
 * 
 * @author agondal
 * @since 18 Sep 2013
 */
public abstract class AbstractDeadlineExpiredScriptTaskScriptRule extends
        AbstractExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {
        /*
         * Find out if this is a task with completed script and if so create and
         * return tool
         */

        if (expression.eContainer() instanceof AuditEvent) {

            AuditEvent auditEvent = (AuditEvent) expression.eContainer();

            if (AuditEventType.DEADLINE_EXPIRED_LITERAL.getLiteral()
                    .equals(auditEvent.getType().getLiteral())) {

                Activity activity =
                        (Activity) Xpdl2ModelUtil.getAncestor(expression,
                                Activity.class);
                if (activity != null) {

                    return getScope()
                            .getTool(DeadlineExpiredScriptTaskScriptTool.class,
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
        return ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK;
    }
}

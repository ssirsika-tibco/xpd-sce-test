/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.AdhocTaskScriptTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract rule class for Adhoc Task Precondition Script.
 * 
 * @author kthombar
 * @since 15-Aug-2014
 */
public abstract class AbstractAdhocPreconditionScriptRule extends AbstractExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractTaskScriptRule#getScriptContext()
     * 
     * @return
     */
    @Override
    protected String getScriptContext() {
        return ProcessScriptContextConstants.ADHOC_PRECONDITION;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractTaskScriptRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {
        /*
         * If this is a Adhoc task precondition script then return appropriate
         * tool.
         */
        if (expression.eContainer() instanceof EnablementType) {

            Activity activity =
                    (Activity) Xpdl2ModelUtil.getAncestor(expression,
                            Activity.class);

            if (activity != null) {

                return getScope().getTool(AdhocTaskScriptTool.class, activity);
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
}

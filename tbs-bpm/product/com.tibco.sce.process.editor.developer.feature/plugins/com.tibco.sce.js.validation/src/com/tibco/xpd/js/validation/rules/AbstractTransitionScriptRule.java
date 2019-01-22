/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.TransitionScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Transition script validation rule.
 * 
 * 
 * @author agondal
 * @since 25 Sep 2013
 */
public abstract class AbstractTransitionScriptRule extends
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
         * If this is a conditional transition script then return appropriate
         * script tool.
         */
        if (expression.eContainer() instanceof Condition) {

            Transition transition =
                    (Transition) Xpdl2ModelUtil.getAncestor(expression,
                            Transition.class);

            if (transition != null) {

                return getScope().getTool(TransitionScriptTool.class,
                        transition);
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

        return (Transition) Xpdl2ModelUtil.getAncestor(expression,
                Transition.class);
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.SEQUENCE_FLOW;
    }
}

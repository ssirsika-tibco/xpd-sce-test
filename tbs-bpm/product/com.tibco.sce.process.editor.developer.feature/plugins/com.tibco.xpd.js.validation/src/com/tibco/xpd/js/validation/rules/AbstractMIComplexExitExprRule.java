/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.MIComplexExitExprTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Multi-instance loop with complex flow condition script validation rule.
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractMIComplexExitExprRule extends
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
         * If this is a multi-instance loop with complex flow script then return
         * appropriate tool
         */

        if (Xpdl2Package.eINSTANCE
                .getLoopMultiInstance_ComplexMIFlowCondition()
                .equals(expression.eContainmentFeature())) {

            Activity activity =
                    (Activity) Xpdl2ModelUtil.getAncestor(expression,
                            Activity.class);
            if (activity != null) {

                return getScope()
                        .getTool(MIComplexExitExprTool.class, activity);

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
        return ProcessJsConsts.MI_COMPLEX_EXIT_EXPR;
    }

}

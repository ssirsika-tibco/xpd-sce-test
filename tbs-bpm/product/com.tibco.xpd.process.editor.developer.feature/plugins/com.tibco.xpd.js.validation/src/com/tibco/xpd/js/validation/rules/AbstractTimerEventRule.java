package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.TimerEventScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Timer event script validation rule.
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractTimerEventRule extends AbstractExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {

        if (expression.eContainer() instanceof Deadline) {

            Activity activity =
                    (Activity) Xpdl2ModelUtil.getAncestor(expression,
                            Activity.class);
            if (activity != null) {

                return getScope().getTool(TimerEventScriptTool.class, activity);
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
        return ProcessJsConsts.TIMER_EVENT;
    }

}

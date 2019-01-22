/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.DecFlowUnmappedScriptInputTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Decision flow unmapped input script validation rule
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractDecFlowUnmappedScriptInputRule extends
        AbstractUnmappedScriptExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractUnmappedScriptExpressionRule#getScriptTool(com.tibco.xpd.xpdExtension.ScriptInformation)
     * 
     * @param scriptInformation
     * @return
     */
    @Override
    protected ScriptTool getScriptTool(ScriptInformation scriptInformation) {
        /*
         * Sid XPD-8009: to be on the safe side explicitly do not validate
         * process data mapper script mapping scripts.
         */
        if (!MappingRuleUtil.isProcessDataScriptMappingScript(scriptInformation)) {

            DirectionType scriptInfoDirection =
                    scriptInformation.getDirection();

            if (scriptInfoDirection == null
                    || !scriptInfoDirection.getLiteral()
                            .equals(DirectionType.OUT_LITERAL.getLiteral())) {

                Activity activity =
                        (Activity) Xpdl2ModelUtil
                                .getAncestor(scriptInformation, Activity.class);

                if (activity != null
                        && DecisionFlowUtil.isDecisionServiceTask(activity)) {

                    return getScope()
                            .getTool(DecFlowUnmappedScriptInputTool.class,
                                    scriptInformation);
                }
            }
        }

        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.DEC_FLOW_SERVICE_TASK;
    }
}

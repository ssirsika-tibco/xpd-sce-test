/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.SubProcessUnmappedScriptInputTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Sub-process unmapped input script validation rule
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractSubProcessUnmappedScriptInputRule extends
        AbstractUnmappedScriptExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractUnmappedScriptExpressionRule#getScriptTool(com.tibco.xpd.xpdExtension.ScriptInformation)
     * 
     * @param scriptInformation
     * @return
     */
    @Override
    protected ScriptTool getScriptTool(ScriptInformation scriptInformation) {
        if (scriptInformation != null
                && DirectionType.IN_LITERAL.equals(MappingRuleUtil
                        .getMappingDirection(scriptInformation))) {

            /*
             * Sid XPD-8009: to be on the safe side explicitly do not validate
             * process data mapper script mapping scripts.
             */
            if (!MappingRuleUtil
                    .isProcessDataScriptMappingScript(scriptInformation)) {

                Activity activity =
                        (Activity) Xpdl2ModelUtil
                                .getAncestor(scriptInformation, Activity.class);
                if (activity != null
                        && activity.getImplementation() instanceof SubFlow) {

                    return getScope()
                            .getTool(SubProcessUnmappedScriptInputTool.class,
                                    scriptInformation);
                }
            }
        }

        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.SUBPROCESS_TASK;
    }
}

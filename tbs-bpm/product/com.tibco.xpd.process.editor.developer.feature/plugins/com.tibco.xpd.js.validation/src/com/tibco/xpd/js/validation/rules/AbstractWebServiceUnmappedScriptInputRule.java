/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.WebServiceUnmappedJScriptInputTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Web service unmapped input script validation rule.
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractWebServiceUnmappedScriptInputRule extends
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
             * Sid XPD-8009: Sort out confusion in web service mapping scripts.
             * As far as I can see we have different implementations for all the
             * different activity types are all the same except the decision to
             * activitate and return a script tool or not (service, send,
             * receive, events etc that are all entirely)
             */
            if (!MappingRuleUtil
                    .isProcessDataScriptMappingScript(scriptInformation)
                    && MappingRuleUtil
                            .isWebServiceActivity((Activity) Xpdl2ModelUtil
                                    .getAncestor(scriptInformation,
                                            Activity.class))) {
                return getScope()
                        .getTool(WebServiceUnmappedJScriptInputTool.class,
                                scriptInformation);
            }
        }
        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.WEBSERVICE_TASK;
    }
}

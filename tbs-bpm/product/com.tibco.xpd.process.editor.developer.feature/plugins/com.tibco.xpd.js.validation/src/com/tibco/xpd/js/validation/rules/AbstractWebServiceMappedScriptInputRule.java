/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.WebServiceMappedJScriptInputTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Web service mapped input script validation rule.
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractWebServiceMappedScriptInputRule extends
        AbstractMappedScriptExpressionInputRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractMappedScriptExpressionOutputRule#getScriptTool(com.tibco.xpd.xpdl2.DataMapping)
     * 
     * @param dataMapping
     * @return
     */
    @Override
    protected ScriptTool getScriptTool(DataMapping dataMapping) {

        if (dataMapping != null) {
            /*
             * Sid XPD-8009: Sort out confusion in web service mapping scripts.
             * As far as I can see we have different implementations for all the
             * different activity types are all the same except the decision to
             * activitate and return a script tool or not (service, send,
             * receive, events etc that are all entirely)
             */
            if (!MappingRuleUtil.isProcessDataScriptMappingScript(dataMapping)
                    && MappingRuleUtil
                            .isWebServiceActivity((Activity) Xpdl2ModelUtil
                                    .getAncestor(dataMapping, Activity.class))) {
                return getScope()
                        .getTool(WebServiceMappedJScriptInputTool.class,
                                dataMapping);
            }
        }

        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.WEBSERVICE_TASK;
    }
}

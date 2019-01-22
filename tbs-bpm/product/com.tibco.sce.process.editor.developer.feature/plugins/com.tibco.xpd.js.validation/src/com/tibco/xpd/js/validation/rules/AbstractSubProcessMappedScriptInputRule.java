/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.SubProcessMappedScriptInputTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Sub-process mapped input script validation rule.
 * 
 * 
 * @author agondal
 * @since 19 Sep 2013
 */
public abstract class AbstractSubProcessMappedScriptInputRule extends
        AbstractMappedScriptExpressionInputRule {

    private static final String TYPE_CHECK = "js.cannotCheckTypes"; //$NON-NLS-1$

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
             * Sid XPD-8009: to be on the safe side explicitly do not validate
             * process data mapper script mapping scripts.
             */
            if (!MappingRuleUtil.isProcessDataScriptMappingScript(dataMapping)) {
                /*
                 * Sid XPD-7892: Not all data mappings under a activity apply to
                 * this specific data mapping scenario (i.e. process data mapper
                 * scripts). Need to be more fussy about when we activate.
                 */
                SubFlow subFlow =
                        (SubFlow) Xpdl2ModelUtil.getAncestor(dataMapping,
                                SubFlow.class);

                if (subFlow != null) {
                    /*
                     * Sid XPD-7914 - prevent decision service invoke and std
                     * sub-process rule executing in each other's scenarios.
                     */
                    Activity activity =
                            (Activity) Xpdl2ModelUtil.getAncestor(subFlow,
                                    Activity.class);

                    if (activity != null
                            && !DecisionFlowUtil
                                    .isDecisionServiceTask(activity)) {
                        return getScope()
                                .getTool(SubProcessMappedScriptInputTool.class,
                                        dataMapping);
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.SUBPROCESS_TASK;
    }

    /**
     * 
     * @return ID for the additional issue being raised
     */
    protected String getAdditionalIssueId() {
        return TYPE_CHECK;
    }
}

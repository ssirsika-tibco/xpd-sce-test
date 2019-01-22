/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.js.validation.tools.DecFlowMappedScriptInputTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Decision flow mapped input script validation rule
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractDecFlowMappedScriptInputRule extends
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
                            && DecisionFlowUtil.isDecisionServiceTask(activity)) {
                        return getScope()
                                .getTool(DecFlowMappedScriptInputTool.class,
                                        dataMapping);
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.DEC_FLOW_SERVICE_TASK;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#performAdditionalValidation(com.tibco.xpd.xpdl2.Expression,
     *      com.tibco.xpd.xpdl2.UniqueIdElement)
     * 
     * @param expression
     * @param expressionIssueHost
     */
    @Override
    protected void performAdditionalValidation(Expression expression,
            UniqueIdElement expressionIssueHost) {

        List<String> messages = new ArrayList<String>();
        String key = MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
        if (expression.eContainer() instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) expression.eContainer();
            addIssue(getAdditionalIssueId(),
                    expression,
                    messages,
                    Collections.singletonMap(key,
                            DataMappingUtil.getTarget(dataMapping)));
        }

    }

    protected String getAdditionalIssueId() {
        return TYPE_CHECK;
    }
}

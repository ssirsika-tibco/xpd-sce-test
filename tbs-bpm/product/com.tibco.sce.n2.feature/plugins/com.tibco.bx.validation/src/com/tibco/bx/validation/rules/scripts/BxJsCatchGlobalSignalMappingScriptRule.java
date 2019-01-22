/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.scripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.js.validation.rules.AbstractExpressionRule;
import com.tibco.xpd.js.validation.rules.MappingRuleUtil;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.CatchGlobalSignalMappingScriptTool;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Mapping Rule for catch global signals script, both for mapped and
 * un-mapped scripts.
 * 
 * @author kthombar
 * @since Feb 12, 2015
 */
public class BxJsCatchGlobalSignalMappingScriptRule extends
        AbstractExpressionRule {
    private static final String EMPTY_SCRIPT_DATA_NOT_SUPPORTED =
            "bx.emptyScriptNotSupported"; //$NON-NLS-1$

    private static final String ERROR_ID =
            "bx.error.catchGlobalSignalMapToSignalScript"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "bx.warning.catchGlobalSignalMapToSignalScript"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {
        if (expression.eContainer() instanceof ScriptInformation) {

            ScriptInformation scriptInformation =
                    (ScriptInformation) expression.eContainer();

            /*
             * Sid XPD-8009: to be on the safe side explicitly do not validate
             * process data mapper script mapping scripts.
             */
            if (!MappingRuleUtil
                    .isProcessDataScriptMappingScript(scriptInformation)) {

                /*
                 * Sid XPD-7914 only pick up mapping scripts we are specifically
                 * interested in (else we will validate process data mapper task
                 * scripts)
                 */
                if (Xpdl2ModelUtil.getAncestor(scriptInformation,
                        TriggerResultSignal.class) != null) {
                    Activity activity =
                            (Activity) Xpdl2ModelUtil.getAncestor(expression,
                                    Activity.class);

                    if (activity != null
                            && EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                    .equals(EventObjectUtil
                                            .getEventTriggerType(activity))
                            && GlobalSignalUtil.isGlobalSignalEvent(activity)) {

                        return getScope()
                                .getTool(CatchGlobalSignalMappingScriptTool.class,
                                        scriptInformation);
                    }
                }
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
        if (expression.eContainer() instanceof ScriptInformation) {
            return (ScriptInformation) expression.eContainer();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getErrorId()
     * 
     * @return
     */
    @Override
    protected String getErrorId() {

        return ERROR_ID;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getWarningId()
     * 
     * @return
     */
    @Override
    protected String getWarningId() {

        return WARNING_ID;
    }

    /**
     * @param scriptInformation
     * @return <code>true</code> if script is empty.
     */
    private boolean checkEmptyScript(ScriptInformation scriptInformation) {
        boolean isEmpty = true;

        Expression expression = scriptInformation.getExpression();
        if (expression != null) {
            String script = expression.getText();
            if (script != null) {
                isEmpty =
                        ScriptParserUtil.isEmptyScript(script,
                                getScriptContext(),
                                getScriptGrammar());
            }
        }

        if (isEmpty) {
            String issueId = getEmptyScriptIssueId();
            if (issueId != null) {

                if (scriptInformation.eContainer() instanceof DataMapping) {
                    Map<String, String> additionalInfoMap =
                            new HashMap<String, String>();

                    String target =
                            DataMappingUtil
                                    .getTarget((DataMapping) scriptInformation
                                            .eContainer());
                    if (target != null) {
                        additionalInfoMap
                                .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        target);
                    }

                    addIssue(issueId,
                            scriptInformation.eContainer(),
                            new ArrayList<String>(),
                            additionalInfoMap);

                } else {
                    addIssue(issueId, scriptInformation);
                }
            }
        }

        return isEmpty;
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
        if (expression.eContainer() instanceof ScriptInformation) {
            checkEmptyScript((ScriptInformation) expression.eContainer());
        }
    }

    protected String getEmptyScriptIssueId() {
        return EMPTY_SCRIPT_DATA_NOT_SUPPORTED;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getScriptContext()
     * 
     * @return
     */
    @Override
    protected String getScriptContext() {

        return ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
    }
}

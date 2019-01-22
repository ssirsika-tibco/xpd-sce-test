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
import com.tibco.xpd.n2.process.globalsignal.mapping.ThrowGlobalSignalMappingScriptTool;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Mapping Rule for throw global signals script, both for mapped and
 * un-mapped scripts.
 * 
 * @author kthombar
 * @since Feb 4, 2015
 */
public class BxJsThrowGlobalSignalMappingScriptRule extends
        AbstractExpressionRule {

    private static final String EMPTY_SCRIPT_DATA_NOT_SUPPORTED =
            "bx.emptyScriptNotSupported"; //$NON-NLS-1$

    private static final String ERROR_ID =
            "bx.error.throwGlobalSignalMapToSignalScript"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "bx.warning.throwGlobalSignalMapToSignalScript"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {
        /*
         * For un-mapped Script the econtainer is ScriptInformation and for
         * mapped script the eContainer is DataMapping
         */
        if (expression.eContainer() instanceof ScriptInformation
                || expression.eContainer() instanceof DataMapping) {
            /*
             * Sid XPD-8009: to be on the safe side explicitly do not validate
             * process data mapper script mapping scripts.
             */
            if (!MappingRuleUtil.isProcessDataScriptMappingScript(expression)) {

                /*
                 * Sid XPD-7914 only pick up mapping scripts we are specifically
                 * interested in (else we will validate process data mapper task
                 * scripts)
                 */
                if (Xpdl2ModelUtil.getAncestor(expression,
                        TriggerResultSignal.class) != null) {
                    Activity activity =
                            (Activity) Xpdl2ModelUtil.getAncestor(expression,
                                    Activity.class);

                    if (activity != null
                            && EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                                    .equals(EventObjectUtil
                                            .getEventTriggerType(activity))
                            && GlobalSignalUtil.isGlobalSignalEvent(activity)) {

                        return getScope()
                                .getTool(ThrowGlobalSignalMappingScriptTool.class,
                                        expression.eContainer());
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
        /*
         * For un-mapped Script the econtainer is ScriptInformation and for
         * mapped script the eContainer is DataMapping
         */
        if (expression.eContainer() instanceof ScriptInformation) {

            return (ScriptInformation) expression.eContainer();
        } else if (expression.eContainer() instanceof DataMapping) {

            DataMapping dataMapping =
                    (DataMapping) Xpdl2ModelUtil.getAncestor(expression,
                            DataMapping.class);

            Object scriptInfoObj =
                    Xpdl2ModelUtil.getOtherElement(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            /*
             * get the script.
             */
            if (scriptInfoObj instanceof ScriptInformation) {

                return (ScriptInformation) scriptInfoObj;
            }
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
     * Raises empty script mapping not allowed validation if the passed
     * expression has empty script.
     * 
     * @param expression
     * @param dataMapping
     */
    private void checkEmptyScript(Expression expression, DataMapping dataMapping) {
        boolean isEmpty = true;

        String script = expression.getText();
        if (script != null) {
            isEmpty =
                    ScriptParserUtil.isEmptyScript(script, getScriptGrammar());
        }

        if (isEmpty) {
            String issueId = getEmptyScriptIssueId();
            if (issueId != null) {

                Map<String, String> additionalInfoMap =
                        new HashMap<String, String>();

                String target = DataMappingUtil.getTarget(dataMapping);
                if (target != null) {
                    additionalInfoMap
                            .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    target);
                }

                addIssue(issueId,
                        dataMapping,
                        new ArrayList<String>(),
                        additionalInfoMap);

            }
        }
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

        /*
         * XPD-7916: For Throw global signals the expression is contained inside
         * Datamapping/Actual and not in the Script Information.
         */
        if (expression != null
                && expression.eContainer() instanceof DataMapping) {
            checkEmptyScript(expression, (DataMapping) expression.eContainer());
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

        return ProcessScriptContextConstants.GLOBAL_THROW_SIGNAL_EVENTMAPPING;
    }

}

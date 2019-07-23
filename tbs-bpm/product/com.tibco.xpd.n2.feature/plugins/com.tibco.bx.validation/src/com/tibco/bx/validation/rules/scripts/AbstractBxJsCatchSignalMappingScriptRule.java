/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.scripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.js.validation.rules.AbstractExpressionRule;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;

/**
 * Script Mapping Rule for catch signals script, both for mapped and un-mapped
 * scripts.
 * 
 * @author kthombar
 * @since Feb 12, 2015
 */
public abstract class AbstractBxJsCatchSignalMappingScriptRule extends
        AbstractExpressionRule {
    private static final String EMPTY_SCRIPT_DATA_NOT_SUPPORTED =
            "bx.emptyScriptNotSupported"; //$NON-NLS-1$

    private String ERROR_ID;

    private String WARNING_ID;

    /**
     * Script Mapping Rule for catch global signals script, both for mapped and
     * un-mapped scripts.
     * 
     * @param errorId
     * @param warningId
     */
    public AbstractBxJsCatchSignalMappingScriptRule(String errorId, String warningId) {
        this.ERROR_ID = errorId;
        this.WARNING_ID = warningId;
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
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.js.validation.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.js.validation.tools.CatchSignalMappingScriptTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Base script mapping rule for catch signal event scripts.
 * 
 * @author aallway
 * @since 9 May 2012
 */
public abstract class AbstractCatchSignalMappingScriptRule extends
        AbstractExpressionRule {

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
             * Sid XPD-7914 only pick up mapping scripts we are specifically
             * interested in (else we will validate process data mapper task
             * scripts)
             */
            /*
             * Nick XPD-7914 Changed condition to exclude Data Mapper scripts as
             * the initial fix was filtering out non-Data Mapper cases that
             * still required validation. It would probably be better to
             * explicitly include all scenarios to which this rule should apply
             * but will raise that as a separate enhancement JIRA so that this
             * blocker can be closed.
             */
            if (Xpdl2ModelUtil.getAncestor(scriptInformation,
                    ScriptDataMapper.class) == null) {

                Activity activity =
                        (Activity) Xpdl2ModelUtil.getAncestor(expression,
                                Activity.class);

                if (activity != null
                        && EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(activity))
                        && shouldValidateMappingsScripts(activity)) {

                    return getScope()
                            .getTool(CatchSignalMappingScriptTool.class,
                                    scriptInformation);
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
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getScriptContext()
     * 
     * @return
     */
    @Override
    protected String getScriptContext() {
        return ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;
    }

    /**
     * Gives the sub-class the opportunity to NOT validate script mappings for a
     * given catch signal event activity.
     * <p>
     * This can be useful (for instance) if there is some other problem that can
     * cause problems with all mapping scripts that should be sorted out prior
     * to validating the scripts.
     * 
     * @param catchSignalEvent
     * 
     * @return <code>true</code> if the given catch signal event's mapping
     *         scripts should be validated.
     */
    protected boolean shouldValidateMappingsScripts(Activity catchSignalEvent) {
        return true;
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

    /**
     * @return Empty script issue id to use or <code>null</code> if empty script
     *         permitted.
     */
    protected abstract String getEmptyScriptIssueId();

}

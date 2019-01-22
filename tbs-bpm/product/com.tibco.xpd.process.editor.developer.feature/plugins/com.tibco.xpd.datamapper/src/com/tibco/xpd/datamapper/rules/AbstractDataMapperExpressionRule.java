/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.rules;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.js.validation.rules.AbstractExpressionRule;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Base Javascript Expression validation rule for Data Mapper scripts.
 * 
 * @author nwilson
 * @since 28 Apr 2015
 */
public abstract class AbstractDataMapperExpressionRule extends
        AbstractExpressionRule {

    /**
     * Sid XPD-7598: Validation of empty scripts moved to
     * {@link DataMapperScriptUseRule}
     */

    /**
     * 
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(Expression expression) {
        ScriptTool tool = null;
        if (JsConsts.JAVASCRIPT_GRAMMAR.equals(expression.getScriptGrammar())) {
            EObject container = expression.eContainer();
            if (container instanceof ScriptInformation) {
                ScriptInformation si = (ScriptInformation) container;
                EObject sdmEO =
                        Xpdl2ModelUtil.getAncestor(si, ScriptDataMapper.class);
                if (sdmEO instanceof ScriptDataMapper) {
                    ScriptDataMapper sdm = (ScriptDataMapper) sdmEO;
                    tool = createScriptToolIfInterested(si, sdm);
                }
            }
        }
        return tool;
    }

    /**
     * Create the {@link ScriptTool} appropriate to thr given mapping scenario
     * of the sub-class.
     * 
     * @param si
     * @param sdm
     * 
     * @return The valdiation {@link ScriptTool}
     */
    protected abstract ScriptTool createScriptToolIfInterested(
            ScriptInformation si, ScriptDataMapper sdm);

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getExpressionHostForScriptTool(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected UniqueIdElement getExpressionHostForScriptTool(
            Expression expression) {
        ScriptInformation si = null;
        EObject container = expression.eContainer();
        if (container instanceof ScriptInformation) {
            si = (ScriptInformation) container;
        }
        return si;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getErrorId()
     * 
     * @return
     */
    @Override
    protected String getErrorId() {
        return "js.error.dataMapperScriptInformation"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getWarningId()
     * 
     * @return
     */
    @Override
    protected String getWarningId() {
        return "js.warning.dataMapperScriptInformation"; //$NON-NLS-1$
    }

}

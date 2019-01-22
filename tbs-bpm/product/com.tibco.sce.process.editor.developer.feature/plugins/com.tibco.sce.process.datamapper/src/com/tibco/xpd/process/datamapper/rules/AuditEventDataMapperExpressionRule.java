/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.rules;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.datamapper.rules.AbstractDataMapperExpressionRule;
import com.tibco.xpd.datamapper.rules.DataMapperScriptTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Expression;

/**
 * Javascript expression validation rule for Audit Event Process Data Mapper
 * scripts.
 * 
 * @author nwilson
 * @since 28 Apr 2015
 */
public class AuditEventDataMapperExpressionRule extends
        AbstractDataMapperExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getScriptContext()
     * 
     * @return
     */
    @Override
    protected String getScriptContext() {
        return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperExpressionRule#isRelevant(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param sdmExpression
     * @return
     */
    protected boolean isRelevant(Expression sdmExpression) {
        boolean relevant = false;
        EObject parent = sdmExpression.eContainer();
        if (parent instanceof AuditEvent) {
            relevant = true;
        }
        return relevant;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdExtension.ScriptDataMapper)
     * 
     * @param sdm
     * @return
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(ScriptInformation si,
            ScriptDataMapper sdm) {
        ScriptTool tool = null;
        EObject sdmContainer = sdm.eContainer();
        if (sdmContainer instanceof Expression) {
            Expression sdmExpression = (Expression) sdmContainer;
            if (ScriptGrammarFactory.DATAMAPPER.equals(sdmExpression
                    .getScriptGrammar())) {
                if (isRelevant(sdmExpression)) {
                    tool = new DataMapperScriptTool(si, getScriptContext());
                }
            }
        }
        return tool;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.process.js.parser.validator.wsdl.WsdlVarNameResolver;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;

/**
 * @author nwilson
 */
public class JavaScriptExpressionParser implements IExpressionParser {

    /** JavaScript grammar identifier. */
    public static final String JAVASCRIPT = "JavaScript"; //$NON-NLS-1$

    @Override
    public Collection<String> getParameterNames(String script,
            IValidationStrategy strategy, String scriptType)
            throws FieldParserException {
        IVarNameResolver varNameResolver = new WsdlVarNameResolver();
        SymbolTable symbolTable = new SymbolTable();
        // setting validation Strategy
        List<IValidationStrategy> strategyList =
                new ArrayList<IValidationStrategy>();
        if (strategy != null) {
            strategyList.add(strategy);
        }
        // setting destination
        List<String> destList = new ArrayList<String>();
        destList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();
        JScriptParser parser;
        try {
            parser = ScriptParserUtil.validateScript(script,
                    destList,
                    strategyList,
                    symbolTable,
                    varNameResolver,
                    validationErrorMap,
                    validationWarningMap,
                    scriptType);
            if (parser == null) {
                Set<Entry<String, List<ErrorMessage>>> errorSet =
                        validationErrorMap.entrySet();
                List<ErrorMessage> comprehensiveErrorList =
                        new ArrayList<ErrorMessage>();
                for (Entry<String, List<ErrorMessage>> entry : errorSet) {
                    String destName = entry.getKey();
                    if (destList.contains(destName)) {
                        comprehensiveErrorList.addAll(entry.getValue());
                    }
                }
                throw new FieldParserException(
                        Messages.JavaScriptExpressionParser_ScriptParserNotFound_message,
                        comprehensiveErrorList);
            }
        } catch (Exception e) {
            throw new FieldParserException(e.getMessage());
        }
        ISymbolTable parserSymbolTable = parser.getSymbolTable();
        // getting the list of process data/local vars in use
        List<String> inUseVarList = parserSymbolTable.getVariablesInUse();
        // System.out.println("The variables in use are "+inUseVarList);

        symbolTable.dispose();
        symbolTable = null;

        return inUseVarList;
    }

}

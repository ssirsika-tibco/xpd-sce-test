/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.script;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.process.xpath.parser.antlr.XPathParser;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;

/**
 * 
 * <p>
 * <i>Created: 05 March 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractMappingXPathScriptProcessFieldResolver extends
        AbstractMappingScriptProcessFieldResolver {

    @Override
    protected Command getSetTransitionScriptCommand(
            EditingDomain editingDomain, String string, Transition transition) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected String getTransitionScript(Transition transition) {
        return null;
    }

    @Override
    protected boolean isInterestingTransition(Transition transition) {
        return false;
    }

    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        return null;
    }

    @Override
    protected String getGrammarType() {
        return ProcessXPathConsts.XPATH_GRAMMAR;
    }

    @Override
    protected String getDefaultDestination() {
        return ProcessXPathConsts.XPATH_DESTINATION;
    }

    @Override
    protected String getTranslatedScript(Process process, String strScript,
            Map<String, String> nameMap, String scriptType) {

        String newScript =
                XPathScriptParserUtil.replaceDataRefByName(strScript, nameMap);

        if (newScript == null || newScript.equals(strScript)) {
            return null;
        }

        return newScript;
    }

    @Override
    protected List<String> getVariablesInUse(Process process, String strScript,
            Map<String, IScriptRelevantData> dataMap, String scriptType) {

        /*
         * SID SIA-106: In-lined content of getScriptParser() here so that we
         * can create and dispose symbol table at same level of code.
         */
        List<String> destinationList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategyList =
                getValidationStrategyList(process, scriptType);
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.setScriptRelevantDataTypeMap(dataMap);

        // Parsing the script and get the list of variable in use
        XPathParser parser =
                XPathScriptParserUtil.validateXPathScript(strScript,
                        destinationList,
                        symbolTable,
                        validationStrategyList,
                        validationErrorMap,
                        validationWarningMap,
                        null,
                        false,
                        null);

        List<String> variablesInUse = symbolTable.getVariablesInUse();

        symbolTable.dispose();

        return variablesInUse;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#ignoreScriptComposite()
     * 
     * @return
     */
    @Override
    protected boolean ignoreScriptComposite() {
        return false;
    }

}

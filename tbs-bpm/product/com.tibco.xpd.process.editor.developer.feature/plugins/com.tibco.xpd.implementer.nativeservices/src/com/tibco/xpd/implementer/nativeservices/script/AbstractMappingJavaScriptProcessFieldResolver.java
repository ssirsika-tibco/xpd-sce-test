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
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.ResetExpressionContentCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 05 March 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractMappingJavaScriptProcessFieldResolver
        extends AbstractMappingScriptProcessFieldResolver {

    @Override
    protected Command getSetScriptInformationScriptCommand(
            EditingDomain editingDomain, String strScript,
            ScriptInformation scriptInformation) {

        /*
         * Sid XPD-7078 - implement a more generic method of resetting script
         * content
         * 
         * In ScriptDataMapper the ScriptInformation element always contains the
         * expression with script.
         * 
         * Otherwise Standard usage is for mapped scripts ScriptInformation is
         * under a data mapping the expression is dataMapping/Actual otherwise
         * (unmapped scripts) the expression is under ScriptInformation
         */
        Expression scriptExpression = null;
        if (Xpdl2ModelUtil.getAncestor(scriptInformation,
                ScriptDataMapper.class) != null) {
            scriptExpression = scriptInformation.getExpression();

        } else if (scriptInformation.eContainer() instanceof DataMapping) {
            scriptExpression =
                    ((DataMapping) scriptInformation.eContainer()).getActual();

        } else {
            scriptExpression = scriptInformation.getExpression();
        }

        if (scriptExpression != null) {
            return new ResetExpressionContentCommand(
                    (TransactionalEditingDomain) editingDomain,
                    Messages.AbstractMappingJavaScriptProcessFieldResolver_RefactorMappingScript_menu,
                    scriptExpression, strScript);
        }

        return null;
    }

    @Override
    protected Command getSetTransitionScriptCommand(EditingDomain editingDomain,
            String string, Transition transition) {
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
        return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
    }

    @Override
    protected String getDefaultDestination() {
        return ProcessJsConsts.JSCRIPT_DESTINATION;
    }

    @Override
    protected String getTranslatedScript(Process process, String strScript,
            Map<String, String> nameMap, String scriptType) {

        String newScript =
                ScriptParserUtil.replaceDataRefByName(strScript, nameMap);

        if (newScript == null || newScript.equals(strScript)) {
            return null;
        }

        return newScript;
    }

    @Override
    protected List<String> getVariablesInUse(Process process, String strScript,
            Map<String, IScriptRelevantData> dataMap, String scriptType) {

        JScriptParser parser =
                getScriptParser(process, strScript, dataMap, scriptType);
        if (parser == null) {
            // could not parse the script
            return null;
        }
        ISymbolTable symbolTable = parser.getSymbolTable();
        List<String> variablesInUse =
                parser.getSymbolTable().getVariablesInUse();
        if (symbolTable instanceof SymbolTable) {
            ((SymbolTable) symbolTable).dispose();
        }
        symbolTable = null;
        parser = null;
        return variablesInUse;
    }

    private JScriptParser getScriptParser(Process process, String strScript,
            Map<String, IScriptRelevantData> dataMap, String scriptType) {
        List<String> destinationList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategyList =
                getValidationStrategyList(process, scriptType);
        IVarNameResolver varNameResolver = getVarNameResolver();
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();
        ISymbolTable symbolTable = new SymbolTable();
        symbolTable.setScriptRelevantDataTypeMap(dataMap);
        // Parsing the script and get the list of variable in use
        JScriptParser parser = ScriptParserUtil.validateScript(strScript,
                destinationList,
                validationStrategyList,
                symbolTable,
                varNameResolver,
                validationErrorMap,
                validationWarningMap,
                scriptType);
        return parser;
    }

    protected IVarNameResolver getVarNameResolver() {
        IVarNameResolver nameResolver = new DefaultVarNameResolver();
        return nameResolver;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#ignoreScriptComposite()
     * 
     * @return
     */
    @Override
    protected boolean ignoreScriptComposite() {
        return true;
    }

}

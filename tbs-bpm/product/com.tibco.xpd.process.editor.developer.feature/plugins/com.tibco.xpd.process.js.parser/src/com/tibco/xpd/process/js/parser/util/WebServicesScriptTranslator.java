package com.tibco.xpd.process.js.parser.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import antlr.RecognitionException;
import antlr.collections.AST;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.process.js.parser.antlr.JScriptTreeParser;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptEmitter;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdl2.Process;

public class WebServicesScriptTranslator {

    /**
     * Returms null if the script could not be parsed or no passed process
     * fields are being used by the script. If the script is using the passed
     * fields then it returns the script with the new field name.
     * 
     * @param process
     * @param strScript
     * @param nameMap
     * @param scriptType
     * @return
     */
    public static String getTranslatedScript(Process process, String strScript,
            Map<String, String> nameMap, String scriptType) {

        if (scriptType == null || strScript.trim().length() < 1) {
            scriptType = ProcessJsConsts.SCRIPT_TASK;
        }
        List<String> destinationList = getProcessDestinationList(process);
        HashMap<String, IScriptRelevantData> processData =
                ProcessUtil.getProcessData(process);
        Map<String, IScriptRelevantData> dataMap =
                getProcessDataMap(nameMap, processData);
        List<IValidationStrategy> validationStrategyList =
                getValidationStrategyList(process, scriptType);
        IVarNameResolver varNameResolver = new DefaultVarNameResolver();
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();

        SymbolTable symbolTable = new SymbolTable();
        symbolTable.setScriptRelevantDataTypeMap(dataMap);

        JScriptParser scriptParser =
                ScriptParserUtil.validateScript(strScript,
                        destinationList,
                        validationStrategyList,
                        symbolTable,
                        varNameResolver,
                        validationErrorMap,
                        validationWarningMap,
                        scriptType);

        /*
         * SID SIA-106: make sure we dispose symbol table in all circumstances.
         */
        String translatedScript = null;

        if (scriptParser != null) {
            /*
             * Check all referenced variables are available before allowing
             * translate (I think?)
             */
            List<String> variableInUse =
                    scriptParser.getSymbolTable().getVariablesInUse();

            boolean varInUse = isVariableInUse(variableInUse, nameMap);

            if (varInUse) {
                translatedScript = translateScript(scriptParser, nameMap);
                symbolTable.dispose();
            }
        }
        return translatedScript;
    }

    private static List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(
                        DestinationUtil
                                .getEnabledValidationDestinations(process));
        if (processDestList == null) {
            processDestList = new ArrayList<String>();
            processDestList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        }
        return processDestList;
    }

    private static List<IValidationStrategy> getValidationStrategyList(
            Process process, String scriptType) {
        List<String> processDestList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategy = Collections.EMPTY_LIST;
        try {
            validationStrategy =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getValidationStrategy(processDestList,
                                    scriptType,
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return validationStrategy;
    }

    /**
     * This method returns a map of data name and their data type. The
     * datafields which are there in the passed 'nameMap' are searched in the
     * 'processData' and their datatypes are recovered from the processData.
     * 
     * @param nameMap
     * @param processData
     * @return
     */
    private static Map<String, IScriptRelevantData> getProcessDataMap(
            Map<String, String> nameMap,
            HashMap<String, IScriptRelevantData> processData) {
        Map<String, IScriptRelevantData> toReturn =
                new HashMap<String, IScriptRelevantData>();
        Set<Entry<String, IScriptRelevantData>> processDataSet =
                processData.entrySet();
        for (Entry<String, IScriptRelevantData> eachEntry : processDataSet) {
            String dataName = eachEntry.getKey();
            boolean isPassed = nameMap.containsKey(dataName);
            if (isPassed) {
                toReturn.put(dataName, eachEntry.getValue());
            }
        }
        return toReturn;
    }

    /**
     * This method returns true if any of the variable in the passed map are
     * present in the passed 'variableInUse'. This means that the variable is
     * used in the script and we have to rename it in the script.
     * 
     * @param variableInUse
     * @param nameMap
     * @return
     */
    private static boolean isVariableInUse(List<String> variableInUse,
            Map<String, String> nameMap) {
        boolean toReturn = false;
        Set<String> nameSet = nameMap.keySet();
        for (String strVar : nameSet) {
            boolean varUse = variableInUse.contains(strVar);
            if (varUse) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    private static String translateScript(JScriptParser scriptParser,
            Map<String, String> nameMap) {
        JScriptTreeParser treeParser = new JScriptTreeParser();
        try {
            treeParser.compilationUnit(scriptParser.getAST());

            AST ast = treeParser.getAST();
            StringBuffer buffer = new StringBuffer();
            JScriptEmitter out = new JScriptEmitter(buffer);
            out.setOldNewProcessDataMap(nameMap);
            out.compilationUnit(ast);
            String translatedScript = buffer.toString();
            if (translatedScript != null) {
                translatedScript = translatedScript.trim();
            }
            return translatedScript;
        } catch (RecognitionException rex) {
        }
        return null;
    }

}

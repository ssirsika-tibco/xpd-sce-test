/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.js.parser.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.ISymbolTableExt;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author mtorres
 * 
 */
public class ScriptMappingUtil {

    /**
     * Parse the script and return the resolved return type
     * 
     * @param mapping
     * @return the return type of the script
     */
    @SuppressWarnings("restriction")
    public static ScriptInformationParsed parseScript(Object mapping,
            List<IValidationStrategy> validationStrategyList,
            List<String> processDestinationList,
            Map<String, IScriptRelevantData> scriptRelevantDataTypeMap,
            String scriptType) {
        if (mapping instanceof Mapping
                && ((Mapping) mapping).getMappingModel() instanceof DataMapping) {
            DataMapping dataMapping =
                    (DataMapping) ((Mapping) mapping).getMappingModel();
            String strScript =
                    ProcessScriptUtil.getDataMappingScript(dataMapping);

            Activity activity = Xpdl2ModelUtil.getParentActivity(dataMapping);
            ScriptInformation scriptInformation =
                    ProcessScriptUtil
                            .getScriptInformationFromDataMapping(dataMapping);
            if (strScript == null || strScript.trim().length() < 1) {
                return new ScriptInformationParsed(scriptInformation, null);
            }
            Process process = Xpdl2ModelUtil.getProcess(activity);
            if (activity != null && scriptInformation != null
                    && process != null) {
                SymbolTable symbolTable = new SymbolTable();
                symbolTable.setInput(scriptInformation);
                symbolTable
                        .setScriptRelevantDataTypeMap(scriptRelevantDataTypeMap);
                IVarNameResolver varNameResolver = new DefaultVarNameResolver();
                Map<String, List<ErrorMessage>> validationErrorMap =
                        new HashMap<String, List<ErrorMessage>>();
                Map<String, List<ErrorMessage>> validationWarningMap =
                        new HashMap<String, List<ErrorMessage>>();
                JScriptParser scriptParser =
                        ScriptParserUtil.validateScript(strScript,
                                processDestinationList,
                                validationStrategyList,
                                symbolTable,
                                varNameResolver,
                                validationErrorMap,
                                validationWarningMap,
                                scriptType);

                /*
                 * SID SIA-106: make sure we dispose symbol table on all control
                 * paths.
                 */
                ScriptInformationParsed scriptInformationParsed = null;

                if (scriptParser != null
                        && scriptParser.getSymbolTable() instanceof ISymbolTableExt) {
                    IScriptRelevantData returnType =
                            ((ISymbolTableExt) scriptParser.getSymbolTable())
                                    .getReturnType();

                    scriptInformationParsed =
                            new ScriptInformationParsed(scriptInformation,
                                    returnType);
                }

                symbolTable.dispose();

                return scriptInformationParsed;

            } else {
                return new ScriptInformationParsed(scriptInformation, null);
            }
        }
        return null;
    }

}

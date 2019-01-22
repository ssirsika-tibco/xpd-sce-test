/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.xpdl2.edit.util;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStreamException;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptLexer;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ModeType;

/**
 * @author mtorres
 * 
 */
public class XsltUtils {

    private static Map<String, Set<String>> activityActualParametersMap;

    private static Map<String, Set<String>> activityScriptsMap;

    private static Map<String, Set<String>> processDestinationsMap;

    private static Map<String, Set<ProcessData>> processDataMap;

    private static final SimpleDateFormat v2PrimaryDateFormatter =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); //$NON-NLS-1$

    private static final SimpleDateFormat v2SecondaryDateFormatter =
            new SimpleDateFormat("MM/dd/yy HH:mm"); //$NON-NLS-1$

    private static XsltUtils xsltUtils;

    private static String EXT_NS = XpdExtensionPackage.eNS_URI;

    /**
     * 
     */
    public XsltUtils() {
        xsltUtils = this;
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static XsltUtils getInstance() {
        if (xsltUtils == null) {
            xsltUtils = new XsltUtils();
        }
        return xsltUtils;
    }

    public static Node transformScriptVariables(String processId,
            String activityId) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();

            /**
             * XPD-6169 Sid - return from java:getSequenceFlowResultActivities()
             * above had to change to Document.documentElement() now we have
             * moved to Java 1.7. Otherwise you get exception: Run-time internal
             * error in 'Don't know how to convert node type 9 I think that the
             * GregorSamsa stuff in java1.7 no longer recognises Document as a
             * valid w3c dom node type
             * 
             * So need an extra root element to return instead of whole Doc
             * itself.
             */

            Element root = doc.createElement("root"); //$NON-NLS-1$

            doc.appendChild(root);

            Element assocs =
                    doc.createElementNS(EXT_NS, "xpdExt:AssociatedParameters"); //$NON-NLS-1$
            root.appendChild(assocs);

            Set<String> scriptSet = XsltUtils.getScripts(activityId);
            List<String> processDestinationList =
                    XsltUtils.getProcessDestinationList(processId);
            if (processDestinationList.isEmpty()) {
                processDestinationList.add("JavaScript");//$NON-NLS-1$
            }
            Map<String, IScriptRelevantData> scriptRelevantData =
                    XsltUtils.getScriptRelevantDataTypeMap(processId);
            for (String strScript : scriptSet) {
                JScriptParser parser =
                        XsltUtils.validateScript(strScript,
                                processDestinationList,
                                scriptRelevantData);
                List<String> variablesInUse = new ArrayList<String>();
                if (parser != null) {
                    ISymbolTable symbolTable = parser.getSymbolTable();
                    if (symbolTable != null) {
                        variablesInUse = symbolTable.getVariablesInUse();
                        if (variablesInUse != null) {
                            for (String variableInUse : variablesInUse) {
                                if (!isActualParameter(activityId,
                                        variableInUse)) {
                                    Element assoc =
                                            doc.createElementNS(EXT_NS,
                                                    "xpdExt:AssociatedParameter"); //$NON-NLS-1$
                                    assocs.appendChild(assoc);
                                    assoc.setAttribute("FormalParam", variableInUse); //$NON-NLS-1$
                                    assoc.setAttribute("Mode", getProcessDataMode(processId, variableInUse)); //$NON-NLS-1$
                                    assoc.setAttribute("Mandatory", "false"); //$NON-NLS-1$ //$NON-NLS-2$
                                }
                            }
                        } else {
                            variablesInUse = new ArrayList<String>();
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        /**
         * XPD-6169 Sid - return from java:getSequenceFlowResultActivities()
         * above had to change to Document.documentElement() now we have moved
         * to Java 1.7. Otherwise you get exception: Run-time internal error in
         * 'Don't know how to convert node type 9 I think that the GregorSamsa
         * stuff in java1.7 no longer recognises Document as a valid w3c dom
         * node type
         */
        if (doc != null) {
            return doc.getDocumentElement();
        }

        return null;
    }

    private static String getProcessDataMode(String processId, String variable) {
        String mode = ModeType.INOUT_LITERAL.getLiteral();
        Set<ProcessData> processDataList = getProcessDataMap().get(processId);
        if (processDataList != null) {
            for (ProcessData processData : processDataList) {
                if (processData.getName().equals(variable)) {
                    String dataMode = processData.getMode();
                    if (dataMode != null && !dataMode.equals("")) {//$NON-NLS-1$
                        mode = dataMode;
                        break;
                    }
                }
            }
        }
        return mode;
    }

    private static boolean isActualParameter(String activityId,
            String parameterName) {
        Set<String> parameters =
                getActivityActualParametersMap().get(activityId);
        if (parameters != null && parameters.contains(parameterName)) {
            return true;
        }
        return false;
    }

    private static Set<String> getScripts(String activityId) {
        Set<String> scriptSet =
                XsltUtils.getActivityScriptsMap().get(activityId);
        if (scriptSet == null) {
            scriptSet = new HashSet<String>();
        }
        return scriptSet;
    }

    public static JScriptParser validateScript(String strScript,
            List<String> processDestinationList,
            Map<String, IScriptRelevantData> scriptRelevantData) {
        if (strScript == null || strScript.trim().length() < 1) {
            return null;
        }
        ISymbolTable symbolTable = new SymbolTable();
        // TODO: Add the script relevant Data
        symbolTable.setScriptRelevantDataTypeMap(scriptRelevantData);
        IVarNameResolver varNameResolver = new DefaultVarNameResolver();
        JScriptParser parser = getScriptParser(strScript);
        if (parser == null) {
            return null;
        }
        parser.setValidationStrategyList(Collections.EMPTY_LIST);
        parser.setVarNameResolver(varNameResolver);
        parser.setSymbolTable(symbolTable);
        try {
            parser.compilationUnit();
        } catch (RecognitionException e) {
            return null;
        } catch (TokenStreamException e) {
            return null;
        }
        return parser;
    }

    public static JScriptParser validateScriptLHS(String strScript,
            List<String> processDestinationList) {
        if (strScript == null || strScript.trim().length() < 1) {
            return null;
        }
        ISymbolTable symbolTable = new SymbolTable();
        // TODO: Add the script relevant Data
        symbolTable
                .setScriptRelevantDataTypeMap(new HashMap<String, IScriptRelevantData>());
        IVarNameResolver varNameResolver = new DefaultVarNameResolver();
        JScriptParser parser = getScriptParser(strScript);
        if (parser == null) {
            return null;
        }
        parser.setValidationStrategyList(Collections.EMPTY_LIST);
        parser.setVarNameResolver(varNameResolver);
        parser.setSymbolTable(symbolTable);
        try {
            parser.compilationUnit();
        } catch (RecognitionException e) {
            return null;
        } catch (TokenStreamException e) {
            return null;
        }
        return parser;
    }

    private static JScriptParser getScriptParser(String strScript) {
        StringReader reader = new StringReader(strScript);
        JScriptLexer lexer = new JScriptLexer(reader);
        JScriptParser parser = new JScriptParser(lexer);
        Token token;
        try {
            token = parser.LT(1);
            // check there to handle case when there is only comments in the
            // script
            if (token != null && token.getText() != null) {
                return parser;
            }
        } catch (TokenStreamException e) {
            // there could be errors thrown which we should handle
        }
        return null;
    }

    public static void addProcessData(String processId, String dataName,
            String dataMode) {
        Map<String, Set<ProcessData>> processDataMap = getProcessDataMap();
        Set<ProcessData> dataSet = processDataMap.get(processId);
        if (dataSet == null) {
            dataSet = new HashSet<ProcessData>();
        }
        if (dataName != null) {
            ProcessData processData =
                    XsltUtils.getInstance().new ProcessData(dataName, dataMode);
            dataSet.add(processData);
        }
        processDataMap.put(processId, dataSet);
    }

    public static Map<String, Set<ProcessData>> getProcessDataMap() {
        if (processDataMap == null) {
            processDataMap = new HashMap<String, Set<ProcessData>>();
        }
        return processDataMap;
    }

    public static void setProcessDataMap(
            Map<String, Set<ProcessData>> processDataMap) {
        XsltUtils.processDataMap = processDataMap;
    }

    public static void addActualParameter(String activityId,
            String parameterName) {
        Map<String, Set<String>> activityActualParametersMap =
                getActivityActualParametersMap();
        Set<String> parameterSet = activityActualParametersMap.get(activityId);
        if (parameterSet == null) {
            parameterSet = new HashSet<String>();
        }
        if (parameterName != null) {
            parameterSet.add(parameterName);
        }
        activityActualParametersMap.put(activityId, parameterSet);
    }

    public static Map<String, Set<String>> getActivityActualParametersMap() {
        if (activityActualParametersMap == null) {
            activityActualParametersMap = new HashMap<String, Set<String>>();
        }
        return activityActualParametersMap;
    }

    public static void setActivityActualParametersMap(
            Map<String, Set<String>> activityActualParametersMap) {
        XsltUtils.activityActualParametersMap = activityActualParametersMap;
    }

    public static void addProcessDestination(String processId,
            String destinationName) {

        Map<String, Set<String>> processDestinationsMap =
                getProcessDestinationsMap();
        Set<String> destinationSet = processDestinationsMap.get(processId);
        if (destinationSet == null) {
            destinationSet = new HashSet<String>();
        }
        if (destinationName != null) {
            destinationSet.add(destinationName);
        }
        processDestinationsMap.put(processId, destinationSet);
    }

    public static Map<String, Set<String>> getProcessDestinationsMap() {
        if (processDestinationsMap == null) {
            processDestinationsMap = new HashMap<String, Set<String>>();
        }
        return processDestinationsMap;
    }

    public static void setProcessDestinationsMap(
            Map<String, Set<String>> processDestinationsMap) {
        XsltUtils.processDestinationsMap = processDestinationsMap;
    }

    public static void addScript(String activityId, String script) {
        Map<String, Set<String>> activityScriptsMap = getActivityScriptsMap();
        Set<String> scriptSet = activityScriptsMap.get(activityId);
        if (scriptSet == null) {
            scriptSet = new HashSet<String>();
        }
        if (script != null) {
            scriptSet.add(script);
        }
        activityScriptsMap.put(activityId, scriptSet);
    }

    public static Map<String, Set<String>> getActivityScriptsMap() {
        if (activityScriptsMap == null) {
            activityScriptsMap = new HashMap<String, Set<String>>();
        }
        return activityScriptsMap;
    }

    public static void setActivityScriptsMap(
            Map<String, Set<String>> activityScriptsMap) {
        XsltUtils.activityScriptsMap = activityScriptsMap;
    }

    public static void dispose() {
        XsltUtils.activityActualParametersMap = null;
        XsltUtils.activityScriptsMap = null;
        XsltUtils.processDestinationsMap = null;
        XsltUtils.processDataMap = null;
    }

    public static List<String> getProcessDestinationList(String processId) {
        Set<String> processDestinationSet =
                getProcessDestinationsMap().get(processId);
        if (processDestinationSet == null) {
            processDestinationSet = new HashSet<String>();
        }
        return new ArrayList<String>(processDestinationSet);
    }

    public static Map<String, IScriptRelevantData> getScriptRelevantDataTypeMap(
            String processId) {
        Map<String, IScriptRelevantData> scriptRelevantDataMap =
                new HashMap<String, IScriptRelevantData>();
        Set<ProcessData> processDataSet = getProcessDataMap().get(processId);
        if (processDataSet != null) {
            for (ProcessData processData : processDataSet) {
                IScriptRelevantData scriptRelevantData =
                        new DefaultScriptRelevantData(processData.getName(),
                                JsConsts.UNDEFINED_DATA_TYPE, false);
                scriptRelevantDataMap.put(processData.getName(),
                        scriptRelevantData);
            }
        }
        return scriptRelevantDataMap;
    }

    /**
     * Convert a v2 format date string into v3 date string format.
     * 
     * @param v2Date
     * @return
     */
    public static String v2DateTov3Date(String v2Date) {
        String returnDate = ""; //$NON-NLS-1$        

        // ALWAYS check if passed null (xslt doesn't distinguish between null
        // and "").
        if (v2Date != null) {
            v2Date = v2Date.trim();
            try {
                Date v2DateObj = v2PrimaryDateFormatter.parse(v2Date);
                returnDate = LocaleUtils.getISO8601Date(v2DateObj);
            } catch (ParseException e) {
                try {
                    Date v2DateObj = v2SecondaryDateFormatter.parse(v2Date);
                    returnDate = LocaleUtils.getISO8601Date(v2DateObj);
                } catch (ParseException e1) {
                    returnDate = v2Date;
                }
            }
        }

        return returnDate;
    }

    /**
     * Get the token name for the given label name.
     * 
     * @param labelName
     * @return
     */
    public static String labelNameToTokenName(String labelName) {
        if (labelName == null || labelName.length() == 0) {
            return "";//$NON-NLS-1$
        }

        return NameUtil.getInternalName(labelName, false);
    }

    public static String getDefaultGlobalDestination(String globalDestinationId) {
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        String name =
                preferences.getDefaultGlobalDestination(globalDestinationId);
        return name;
    }

    private class ProcessData {
        String name;

        String mode;

        /**
         * 
         */
        public ProcessData(String name, String mode) {
            this.name = name;
            this.mode = mode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }

    public static Element getXslNameSpaceElement(Element stylesheet) {
        stylesheet
                .setAttribute("xmlns:xsl", "http://www.w3.org/1999/XSL/Transform");//$NON-NLS-1$ //$NON-NLS-2$
        return stylesheet;
    }
}

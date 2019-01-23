/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.internal.validator.ValidationUtil;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * <p>
 * <i>Created: 27 Mar 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public abstract class ScriptTool implements IPreProcessor {

    protected Map<String, Map<String, List<ErrorMessage>>> errorHashMap =
            new HashMap<String, Map<String, List<ErrorMessage>>>();

    protected Map<String, Map<String, List<ErrorMessage>>> warningHashMap =
            new HashMap<String, Map<String, List<ErrorMessage>>>();

    protected UniqueIdElement eObject;

    protected boolean autoTerminateSingleLineStatement = true;

    // Cache for enumerations in XPDL Package Scope
    private PackageScopeEnumCache packageScopeEnumCache = null;

    public ScriptTool(UniqueIdElement eObject) {
        this.eObject = eObject;
    }

    /**
     * Sets the Package Scope enumerations Cache. This cache is used to handle
     * the ambiguity lead by the existence of multiple enumerations with same
     * name in the process package scope.The cache in Validation Scope is used
     * to initialise the tool before usage in the Rules see {@link
     * AbstractScriptRule.getScriptTool()}. This cache is then passed on from
     * the ScriptTool to the Data Providers, to handle the ambiguity of
     * Enumerations.The cache shared using validation scope is initialised ONLY
     * once for each Process Package and avoids recreation of Enumeration cache
     * for each process package by all Rules/Providers, thus improving
     * performance.
     * 
     * @param packageScopeEnumCache
     *            the packageScopeEnumCache to set
     */
    public void setPackageScopeEnumCache(
            PackageScopeEnumCache packageScopeEnumCache) {
        this.packageScopeEnumCache = packageScopeEnumCache;
    }

    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(
                        DestinationUtil
                                .getEnabledValidationDestinations(process));

        if (processDestList == null || processDestList.isEmpty()) {
            processDestList = new ArrayList<String>();
            processDestList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        }
        return processDestList;
    }

    protected List<IValidationStrategy> getValidationStrategyList(
            Process process) {
        List<String> processDestList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategy = Collections.EMPTY_LIST;
        try {
            validationStrategy =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getValidationStrategy(processDestList,
                                    getScriptType(),
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return validationStrategy;
    }

    /**
     * If the given script is single line and not terminated with a semi-colon,
     * then append a semi colon;
     * 
     * @param strScript
     * @return
     */
    public static String autoTerminateSingleLineStatement(String strScript) {
        if (!strScript.contains("\n")) { //$NON-NLS-1$
            String tmp = strScript.trim();

            if (!tmp.endsWith(";")) { //$NON-NLS-1$
                return strScript + ";"; //$NON-NLS-1$
            }
        }

        return strScript;
    }

    protected void validateScript() {
        String strScript = getScript();
        if (strScript == null || strScript.trim().length() < 1) {
            return;
        }

        if (isAutoTerminateSingleLineStatement()) {
            strScript = autoTerminateSingleLineStatement(strScript);
        }

        Process process = getProcess();
        List<IValidationStrategy> validationStrategyList =
                getValidationStrategyList(process);
        List<String> processDestinationList =
                getProcessDestinationList(process);
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.setInput(getEObject());
        symbolTable
                .setScriptRelevantDataTypeMap(getScriptRelevantDataTypeMap());
        IVarNameResolver varNameResolver = new DefaultVarNameResolver();
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();
        JScriptParser parser =
                ScriptParserUtil.validateScript(strScript,
                        processDestinationList,
                        validationStrategyList,
                        symbolTable,
                        varNameResolver,
                        validationErrorMap,
                        validationWarningMap,
                        getScriptType());

        errorHashMap.put(eObject.getId(), validationErrorMap);
        warningHashMap.put(eObject.getId(), validationWarningMap);

        validationStrategyList = null;
        symbolTable.dispose();
        symbolTable = null;
        parser = null;
    }

    protected abstract String getScript();

    protected abstract String getScriptType();

    protected abstract Process getProcess();

    public List<ErrorMessage> getErrorList(String eObjectId,
            Destination destination) {

        if (isValidationSupressed()) {
            return Collections.emptyList();
        }
        Map<String, List<ErrorMessage>> eObjectErrorMap =
                errorHashMap.get(eObjectId);
        if (eObjectErrorMap == null || eObjectErrorMap.isEmpty()) {
            validateScript();
            eObjectErrorMap = errorHashMap.get(eObjectId);
            if (eObjectErrorMap == null) {
                return null;
            }
        }
        return eObjectErrorMap.get(destination.getId());
    }

    public List<ErrorMessage> getWarningList(String eObjectId,
            Destination destination) {
        if (isValidationSupressed()) {
            return Collections.emptyList();
        }
        Map<String, List<ErrorMessage>> eObjectErrorMap =
                warningHashMap.get(eObjectId);
        if (eObjectErrorMap == null || eObjectErrorMap.isEmpty()) {
            validateScript();
            eObjectErrorMap = warningHashMap.get(eObjectId);
            if (eObjectErrorMap == null) {
                return null;
            }
        }
        return eObjectErrorMap.get(destination.getId());
    }

    protected abstract Activity getActivity();

    /**
     * This method provides the map of dynamic script relevant data to the
     * script for the validation. This method should NOT be overridden, the
     * extension point "scriptRelevantDataContribution" should be used instead.
     * 
     * @return the map containing the map of script relevant data.
     * 
     **/
    public Map<String, IScriptRelevantData> getScriptRelevantDataTypeMap() {
        Map<String, IScriptRelevantData> scriptRelevantDataTypeMap =
                new HashMap<String, IScriptRelevantData>();

        List<IScriptRelevantData> scriptRelevantDataList =
                new ArrayList<IScriptRelevantData>();
        try {
            // get data providers, to be able to set the enumerations Cache
            // resolveing relevant data.
            List<AbstractScriptRelevantDataProvider> dataProviders =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getScriptRelevantDataProviders(getProcessDestinationList(getProcess()),
                                    getScriptType(),
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    getEObject(),
                                    ProcessJsConsts.JSCRIPT_DESTINATION);

            /*
             * When getting script relevant data for validating we need to take
             * into account single vs multiple enums in scope of package with
             * same name, so need to set the ValidationScope tool that has cache
             * for package on each data provider before getting data.
             */

            for (AbstractScriptRelevantDataProvider dataProvider : dataProviders) {
                // Set Package Scope Enumerations Cache before fetching Data.
                // THIS SHOULD BE DONE BEFORE getting relevant DATA list
                dataProvider
                        .setCustomPropertyClass(PackageScopeEnumCache.class,
                                packageScopeEnumCache);
                scriptRelevantDataList.addAll(dataProvider
                        .getScriptRelevantDataList());
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        if (scriptRelevantDataList != null) {
            for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
                if (scriptRelevantData != null
                        && scriptRelevantData.getName() != null) {
                    scriptRelevantDataTypeMap.put(scriptRelevantData.getName(),
                            scriptRelevantData);
                }
            }
            scriptRelevantDataList = null;
        }
        return scriptRelevantDataTypeMap;
    }

    protected List<JsClassDefinitionReader> readContributedDefinitionReaders(
            List<String> processDestList) {
        List<JsClassDefinitionReader> jsClassProvider = Collections.EMPTY_LIST;
        try {
            jsClassProvider =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(processDestList,
                                    getScriptType(),
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return jsClassProvider;
    }

    public Map<String, Map<String, List<ErrorMessage>>> getErrorHashMap() {
        if (errorHashMap == null) {
            errorHashMap =
                    new HashMap<String, Map<String, List<ErrorMessage>>>();
        }
        return errorHashMap;
    }

    public Map<String, Map<String, List<ErrorMessage>>> getWarningHashMap() {
        if (warningHashMap == null) {
            warningHashMap =
                    new HashMap<String, Map<String, List<ErrorMessage>>>();
        }
        return warningHashMap;
    }

    public UniqueIdElement getEObject() {
        return eObject;
    }

    /**
     * @return the autoTerminateSingleLineStatement
     */
    public boolean isAutoTerminateSingleLineStatement() {
        return autoTerminateSingleLineStatement;
    }

    /**
     * @param autoTerminateSingleLineStatement
     *            the autoTerminateSingleLineStatement to set
     */
    public void setAutoTerminateSingleLineStatement(
            boolean autoTerminateSingleLineStatement) {
        this.autoTerminateSingleLineStatement =
                autoTerminateSingleLineStatement;
    }

    public boolean isValidationSupressed() {
        ExtendedAttributesContainer extendedAttributesContainer = null;
        if (eObject instanceof ExtendedAttributesContainer) {
            extendedAttributesContainer = (ExtendedAttributesContainer) eObject;
        } else {
            extendedAttributesContainer =
                    Xpdl2ModelUtil.getParentActivity(eObject);
        }
        if (extendedAttributesContainer != null) {
            EList<ExtendedAttribute> extendedAttributes =
                    extendedAttributesContainer.getExtendedAttributes();
            if (extendedAttributes != null && !extendedAttributes.isEmpty()) {
                for (ExtendedAttribute extendedAttribute : extendedAttributes) {
                    if (extendedAttribute != null
                            && extendedAttribute.getName() != null
                            && extendedAttribute
                                    .getName()
                                    .equals(ValidationUtil.SUPPRESS_SCRIPT_VALIDATION_KEY)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

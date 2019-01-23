/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.process.xpath.parser.validator.xpath.XPathSymbolTable;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.validator.ValidationUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.UniqueIdElement;

/**
 * 
 * 
 * <p>
 * <i>Created: 07 February 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class XPathScriptTool implements IPreProcessor {

    private Map<String, Map<String, List<ErrorMessage>>> errorHashMap =
            new HashMap<String, Map<String, List<ErrorMessage>>>();

    private Map<String, Map<String, List<ErrorMessage>>> warningHashMap =
            new HashMap<String, Map<String, List<ErrorMessage>>>();

    private UniqueIdElement eObject;

    public XPathScriptTool(UniqueIdElement eObject) {
        this.eObject = eObject;
    }

    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(DestinationUtil
                        .getEnabledValidationDestinations(process));

        if (processDestList == null || processDestList.isEmpty()) {
            processDestList = new ArrayList<String>();
        }
        processDestList.add(XPathScriptParserUtil.XPATH_DESTINATION);
        return processDestList;
    }

    protected List<IValidationStrategy> getValidationStrategyList(
            Process process) {
        List<IValidationStrategy> validationStrategyList =
                new ArrayList<IValidationStrategy>();
        if (process != null) {
            List<String> processDestList = getProcessDestinationList(process);
            if (processDestList != null) {
                List<IValidationStrategy> validationStrategy =
                        Collections.EMPTY_LIST;
                try {
                    validationStrategy =
                            ScriptGrammarContributionsUtil.INSTANCE
                                    .getValidationStrategy(processDestList,
                                            getScriptType(),
                                            ProcessXPathConsts.XPATH_GRAMMAR,
                                            ProcessXPathConsts.XPATH_DESTINATION);
                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
                if (validationStrategy != null) {
                    return validationStrategy;
                }
            }
        }
        return validationStrategyList;
    }

    protected void validateScript() {
        String strScript = getScript();
        if (strScript == null || strScript.trim().length() < 1) {
            return;
        }
        Process process = getProcess();
        List<IValidationStrategy> validationStrategyList =
                getValidationStrategyList(process);
        ISymbolTable symbolTable = new XPathSymbolTable();

        try {
            symbolTable
                    .setScriptRelevantDataTypeMap(getScriptRelevantDataTypeMap());
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        List<String> processDestinationList =
                getProcessDestinationList(process);
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();
        XPathScriptParserUtil.validateXPathScript(strScript,
                processDestinationList,
                symbolTable,
                validationStrategyList,
                validationErrorMap,
                validationWarningMap,
                getWsdlPart(),
                isWsdlSupported(),
                getMappingType());
        errorHashMap.put(eObject.getId(), validationErrorMap);
        warningHashMap.put(eObject.getId(), validationWarningMap);
    }

    protected abstract String getScript();

    protected abstract String getScriptType();

    protected abstract Process getProcess();

    /**
     * This method provides the map of dynamic script relevant data to the
     * script for the validation. This method should NOT be overridden, the
     * extension point "scriptRelevantDataContribution" should be used instead.
     * 
     * @return the map containing the map of script relevant data.
     * @throws CoreException
     * 
     **/
    public Map<String, IScriptRelevantData> getScriptRelevantDataTypeMap()
            throws CoreException {
        Map<String, IScriptRelevantData> scriptRelevantDataTypeMap =
                new HashMap<String, IScriptRelevantData>();
        List<IScriptRelevantData> scriptRelevantDataList =
                ScriptGrammarContributionsUtil.INSTANCE
                        .getScriptRelevantData(getProcessDestinationList(getProcess()),
                                getScriptType(),
                                XPathScriptParserUtil.XPATH_GRAMMAR,
                                getEObject(),
                                XPathScriptParserUtil.XPATH_DESTINATION);
        if (scriptRelevantDataList != null) {
            for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
                if (scriptRelevantData != null
                        && scriptRelevantData.getName() != null) {
                    scriptRelevantDataTypeMap.put(scriptRelevantData.getName(),
                            scriptRelevantData);
                }
            }
        }
        return scriptRelevantDataTypeMap;
    }

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

    abstract Part getWsdlPart();

    abstract IScriptRelevantData getMappingType();

    abstract boolean isWsdlSupported();
    
    public boolean isValidationSupressed() {
        if (getActivity() != null) {
            EList<ExtendedAttribute> extendedAttributes =
                    getActivity().getExtendedAttributes();
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

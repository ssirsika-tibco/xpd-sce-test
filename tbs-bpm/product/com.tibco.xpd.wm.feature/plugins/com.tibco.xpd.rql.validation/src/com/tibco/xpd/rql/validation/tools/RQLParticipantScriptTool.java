/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.validation.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCSymbolTable;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.rql.parser.validator.RQLSymbolTable;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.validator.ValidationUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author Miguel Torres
 * 
 */
public class RQLParticipantScriptTool implements IPreProcessor {

    private Map<String, Map<String, List<ErrorMessage>>> errorHashMap =
            new HashMap<String, Map<String, List<ErrorMessage>>>();

    private Map<String, Map<String, List<ErrorMessage>>> warningHashMap =
            new HashMap<String, Map<String, List<ErrorMessage>>>();

    private UniqueIdElement eObject;

    public RQLParticipantScriptTool(UniqueIdElement eObject) {
        this.eObject = eObject;
    }

    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(DestinationUtil
                        .getEnabledValidationDestinations(process));

        if (processDestList == null || processDestList.isEmpty()) {
            processDestList = new ArrayList<String>();
        }
        processDestList.add(RQLParserUtil.N2UT_DESTINATION);
        return processDestList;
    }

    protected List<String> getProjectDestinationList(IProject project) {
        ProjectConfig projectConfig = XpdResourcesPlugin.getDefault()
        .getProjectConfig(project);
        if (projectConfig != null) {
            ProjectDetails projectDetails = projectConfig.getProjectDetails();
            if(projectDetails != null){
                List<String> projectDestinations = new ArrayList<String>();
                EList<String> enabledGlobalDestinationIds = projectDetails.getEnabledGlobalDestinationIds();
                if(enabledGlobalDestinationIds != null && !enabledGlobalDestinationIds.isEmpty()){
                DestinationPreferences preferences =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();
                for (String globalDestinationId : enabledGlobalDestinationIds) {
                    for (String componentId : preferences
                            .getEnabledComponents(globalDestinationId)) {
                        projectDestinations.add(preferences
                                .getValidationDestinationId(globalDestinationId,
                                        componentId));
                    }
                }                
                }
                return projectDestinations;
            }
        }
        return Collections.emptyList();
    }
    

    protected List<IValidationStrategy> getValidationStrategyList(
            IProject project) {
        List<IValidationStrategy> validationStrategyList =
                new ArrayList<IValidationStrategy>();
        if (project != null) {
            List<String> projectDestList = getProjectDestinationList(project);
            if (projectDestList != null) {
                List<IValidationStrategy> validationStrategy =
                        Collections.EMPTY_LIST;
                try {
                    validationStrategy =
                            ScriptGrammarContributionsUtil.INSTANCE
                                    .getValidationStrategy(projectDestList,
                                            getScriptType(),
                                            RQLParserUtil.RQL_GRAMMAR,
                                            RQLParserUtil.N2UT_DESTINATION);
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
                                            RQLParserUtil.RQL_GRAMMAR,
                                            RQLParserUtil.N2UT_DESTINATION);
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
        List<IValidationStrategy> validationStrategyList = new ArrayList<IValidationStrategy>();
        IProject project = WorkingCopyUtil.getProjectFor(eObject);
        if(process != null){
           validationStrategyList = getValidationStrategyList(process);
        } else if(project != null){
            validationStrategyList = getValidationStrategyList(project);
        }
        IJCCSymbolTable symbolTable = new RQLSymbolTable();

        symbolTable
                .setScriptRelevantDataTypeMap(getScriptRelevantDataTypeMap());
        List<String> destinationList = new ArrayList<String>();
        if(process != null){
            destinationList = getProcessDestinationList(process);
        } else if(project != null){
            destinationList = getProjectDestinationList(project);
        }
                
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();
        RQLParserUtil.validateRQLScript(strScript,
                destinationList,
                symbolTable,
                validationStrategyList,
                validationErrorMap,
                validationWarningMap);
        errorHashMap.put(eObject.getId(), validationErrorMap);
        warningHashMap.put(eObject.getId(), validationWarningMap);
    }

    protected Process getProcess() {
        Process process = null;
        if (eObject != null) {
            process = Xpdl2ModelUtil.getProcess(eObject);
        }
        return process;
    }

    protected String getScript() {
        if (eObject instanceof Participant) {
            return ProcessScriptUtil
                    .getQueryParticipantScript((Participant) eObject);
        } else if (eObject instanceof DataField) {
            return ProcessScriptUtil
                    .getQueryParticipantScript((DataField) eObject);
        }
        return null;
    }

    protected String getScriptType() {
        return ProcessScriptContextConstants.QUERY_PARTICIPANT;
    }

    /**
     * This method provides the map of dynamic script relevant data to the
     * script for the validation. This method should NOT be overridden, the
     * extension point "scriptRelevantDataContribution" should be used instead.
     * 
     * @return the map containing the map of script relevant data.
     * 
     **/
    public Map<String, List<IScriptRelevantData>> getScriptRelevantDataTypeMap() {
        Map<String, List<IScriptRelevantData>> scriptRelevantDataTypeMap =
                new HashMap<String, List<IScriptRelevantData>>();

        List<IScriptRelevantData> scriptRelevantDataList =
                Collections.EMPTY_LIST;
        try {
            scriptRelevantDataList =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getScriptRelevantData(getProcessDestinationList(getProcess()),
                                    getScriptType(),
                                    RQLParserUtil.RQL_GRAMMAR,
                                    getEObject(),
                                    RQLParserUtil.N2UT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
            if (scriptRelevantData != null
                    && scriptRelevantData.getName() != null) {
                // get the existing IScriptRelevantData
                List<IScriptRelevantData> existingType =
                        scriptRelevantDataTypeMap.get(scriptRelevantData
                                .getName());
                if (existingType == null) {
                    existingType = new ArrayList<IScriptRelevantData>();
                }
                existingType.add(scriptRelevantData);
                scriptRelevantDataTypeMap.put(scriptRelevantData.getName(),
                        existingType);
            }
        }
        return scriptRelevantDataTypeMap;
    }

    public List getComplexScriptRelevantDataTypeList() {
        List comlexScriptRelevantDataList = Collections.EMPTY_LIST;
        try {
            comlexScriptRelevantDataList =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getComplexScriptRelevantData(getProcessDestinationList(getProcess()),
                                    getScriptType(),
                                    RQLParserUtil.RQL_GRAMMAR,
                                    getEObject(),
                                    RQLParserUtil.N2UT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return comlexScriptRelevantDataList;
    }

    public List<ErrorMessage> getErrorList(String eObjectId,
            Destination destination) {
        if(isValidationSupressed()){
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
        if(isValidationSupressed()){
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

    protected Activity getActivity() {
        Activity activity = null;
        if (eObject != null) {
            activity = Xpdl2ModelUtil.getParentActivity(eObject);
        }
        return activity;
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
    
    public boolean isValidationSupressed() {
        ExtendedAttributesContainer extendedAttributesContainer = null;
        if (eObject instanceof ExtendedAttributesContainer) {
            extendedAttributesContainer =
                    (ExtendedAttributesContainer) eObject;
        }
        if(extendedAttributesContainer != null){
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

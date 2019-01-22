/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.n2.decisions.internal.configuration.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Type;

import com.tibco.adec.dt.model.DecisionTaskData;
import com.tibco.adec.dt.model.DecisionTaskFactory;
import com.tibco.adec.dt.model.DocumentRoot;
import com.tibco.bx.xpdl2bpel.extensions.ExtensionVariable;
import com.tibco.bx.xpdl2bpel.extensions.IMappingDataModel;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.decisions.internal.configuration.mapping.DecisionServiceMappingDataModel;
import com.tibco.xpd.n2.decisions.internal.util.UMLTypeConverterFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.DecisionStandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * Decision Service Task Mapping Data provider
 * 
 * 
 * @author mtorres
 * 
 */
public class DecisionServiceConfigurationModelProvider {

    // The activity
    private Activity activity;

    // Prefix of the temp variable
    public static final String TEMP_VARIABLE_PREFIX = "OUTPARAM_";//$NON-NLS-1$

    // Cache of the service data mappings
    private static Map<MappingDirection, Object[]> serviceDataMappings = null;

    public DecisionServiceConfigurationModelProvider(Activity activity) {
        this.activity = activity;
        //Clear the cache
        serviceDataMappings = new HashMap<MappingDirection, Object[]>();
    }

    public List<IMappingDataModel> getInputToServiceMappingData() {
        return getServiceMappingData(MappingDirection.IN);
    }

    public List<IMappingDataModel> getOutputFromServiceMappingData() {
        return getServiceMappingData(MappingDirection.OUT);
    }

    /**
     * Returns the Mapping Data Models for a given mapping direction
     * 
     * @param mappingDirection
     * @return
     */
    private List<IMappingDataModel> getServiceMappingData(
            MappingDirection mappingDirection) {
        Object[] serviceDataMappings = getServiceDataMappings(mappingDirection);
        if (serviceDataMappings != null) {
            List<IMappingDataModel> serviceMappingData =
                    new ArrayList<IMappingDataModel>();
            for (Object object : serviceDataMappings) {
                if (object instanceof Mapping) {
                    serviceMappingData.add(new DecisionServiceMappingDataModel(
                            (Mapping) object, mappingDirection));
                }
            }
            return serviceMappingData;
        }
        return Collections.emptyList();
    }

    /**
     * Returns all the Data Mappings of the service for a given mapping
     * direction
     * 
     * @param mappingDirection
     * @return
     */
    private Object[] getServiceDataMappings(MappingDirection mappingDirection) {
        if (activity != null) {
            if (serviceDataMappings == null) {
                serviceDataMappings = new HashMap<MappingDirection, Object[]>();
            }
            Object[] cachedServiceDataMappings =
                    serviceDataMappings.get(mappingDirection);
            if (cachedServiceDataMappings != null) {
                return cachedServiceDataMappings;
            } else {
                SubFlow decFlow =
                        DecisionTaskObjectUtil
                                .getDecFlowExtendedElement(activity);
                if (decFlow != null) {
                    EList<DataMapping> dataMappings = decFlow.getDataMappings();
                    List<FormalParameter> decFlowFormalParams =
                            DecisionStandardMappingUtil
                                    .getDecFlowFormalParameters(activity, mappingDirection);
                    if (decFlowFormalParams != null) {
                        EObject decisionFlow =
                                DecisionTaskObjectUtil
                                        .getDecisionFlow(activity);
                        if (decisionFlow instanceof Process) {
                            Object[] newCacheServiceDataMappings =
                                    DecisionStandardMappingUtil
                                            .getProcessDataToOtherProcessDataMappings(decFlowFormalParams,
                                                    activity,
                                                    (Process) decisionFlow,
                                                    dataMappings,
                                                    mappingDirection);
                            serviceDataMappings.put(mappingDirection,
                                    newCacheServiceDataMappings);
                            return newCacheServiceDataMappings;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns an array of Extension Variables
     * 
     * @param mappingDirection
     * @return
     */
    public ExtensionVariable[] getExtensionVariables() {
        List<ExtensionVariable> allVariables = new ArrayList<ExtensionVariable>();
        List<String> variableNames = new ArrayList<String>();
        Object[] inputServiceDataMappings =
                getServiceDataMappings(MappingDirection.IN);
        if (inputServiceDataMappings != null) {
            List<ExtensionVariable> inputVariables =
                    new ArrayList<ExtensionVariable>();
            for (Object object : inputServiceDataMappings) {
                if (object instanceof Mapping) {
                    Mapping mapping = (Mapping) object;
                    Object target = mapping.getTarget();
                    if (target instanceof ConceptPath) {
                        ExtensionVariable extensionVariable =
                                getExtensionVariable(((ConceptPath) target)
                                        .getRoot());
                        if (extensionVariable != null
                                && !variableNames.contains(extensionVariable
                                        .getName())) {
                            inputVariables.add(extensionVariable);
                            variableNames.add(extensionVariable.getName());
                        }
                    }
                }
            }
            allVariables.addAll(inputVariables);
        }
        Object[] outputServiceDataMappings =
                getServiceDataMappings(MappingDirection.OUT);
        if (outputServiceDataMappings != null) {
            List<ExtensionVariable> outputVariables =
                    new ArrayList<ExtensionVariable>();
            for (Object object : outputServiceDataMappings) {
                if (object instanceof Mapping) {
                    Mapping mapping = (Mapping) object;
                    Object source = mapping.getSource();
                    if (source instanceof ConceptPath) {
                        ExtensionVariable extensionVariable =
                                getExtensionVariable(((ConceptPath) source)
                                        .getRoot());
                        if (extensionVariable != null
                                && !variableNames.contains(extensionVariable
                                        .getName())) {
                            outputVariables.add(extensionVariable);
                            variableNames.add(extensionVariable.getName());
                        }
                    }
                }
            }
            allVariables.addAll(outputVariables);
        }
        return (ExtensionVariable[]) allVariables
                .toArray(new ExtensionVariable[allVariables.size()]);
    }

    /**
     * Returns the extension variable
     * 
     * @param conceptPath
     * @return
     */
    private ExtensionVariable getExtensionVariable(ConceptPath conceptPath) {
        String name = TEMP_VARIABLE_PREFIX + conceptPath.getName();
        Type type =
                DecisionServiceConfigurationModelProvider
                        .getConceptPathType(conceptPath);
        boolean isArray = conceptPath.isArray();
        if (type != null) {
            return new ExtensionVariable(name, type, isArray);
        }
        return null;
    }

    /**
     * Returns the concept Path type in UML Type format
     * 
     * @param conceptPath
     * @return
     */
    public static Type getConceptPathType(ConceptPath conceptPath) {
        if (conceptPath != null) {
            Classifier type = conceptPath.getType();
            if (type != null) {
                return type;
            } else {
                Object item = conceptPath.getItem();
                if (item != null) {
                    Object processBasicType =
                            BasicTypeConverterFactory.INSTANCE
                                    .getBasicType(item);
                    if (processBasicType != null) {
                        return UMLTypeConverterFactory.INSTANCE
                                .getBasicType(processBasicType);
                    }
                }
            }
        }
        return null;
    }

    public DecisionTaskData convertToDecisionTaskDataModel() {
        if (activity != null) {
            EObject decisionFlow =
                    DecisionTaskObjectUtil.getDecisionFlow(activity);
            if (decisionFlow instanceof Process) {
                Process decFlow = (Process) decisionFlow;
                String decFlowId = decFlow.getId();
                String decFlowName = DecisionFlowUtil.getDecisionFlowAppName(decFlow);
                IFile file = WorkingCopyUtil.getFile(decFlow);
                if (file != null && file.getFullPath() != null) {
                    String decFlowPackageRef =
                            file.getFullPath().toPortableString();
                    DecisionTaskFactory modelFactory =
                            DecisionTaskFactory.eINSTANCE;
                    DocumentRoot docRoot = modelFactory.createDocumentRoot();
                    DecisionTaskData decisionTaskDataModel =
                            modelFactory.createDecisionTaskData();
                    docRoot.setDecisionTaskDataModel(decisionTaskDataModel);
                    decisionTaskDataModel.setDecFlowId(decFlowId);
                    decisionTaskDataModel.setDecFlowName(decFlowName);
                    decisionTaskDataModel
                            .setDecFlowPackageRef(decFlowPackageRef);
                    decisionTaskDataModel.getDecFlowInput()
                            .addAll(getInputMappings(modelFactory));
                    decisionTaskDataModel.getDecFlowOutput()
                            .addAll(getOutputMappings(modelFactory));
                    return decisionTaskDataModel;
                }
            }
        }
        return null;
    }

    /**
     * Returns the list of input mappings
     * 
     * @param modelFactory
     * @return
     */
    private List<com.tibco.adec.dt.model.Mapping> getInputMappings(
            DecisionTaskFactory modelFactory) {
        Object[] serviceDataMappings =
                getServiceDataMappings(MappingDirection.IN);
        if (serviceDataMappings != null && serviceDataMappings.length > 0) {
            List<com.tibco.adec.dt.model.Mapping> inputMappings =
                    new ArrayList<com.tibco.adec.dt.model.Mapping>();
            Set<ConceptPath> rootPaths = new HashSet<ConceptPath>();
            for (Object serviceDataMapping : serviceDataMappings) {
                if (serviceDataMapping instanceof Mapping) {
                    Mapping mapping = (Mapping) serviceDataMapping;
                    Object target = mapping.getTarget();
                    if (target instanceof ConceptPath) {
                        ConceptPath targetMapping = ((ConceptPath) target).getRoot();
                        if (targetMapping.getName() != null) {
                            rootPaths.add(targetMapping);
                        }
                    }
                }
            }
            for (ConceptPath rootPath : rootPaths) {
                String name = rootPath.getName();
                com.tibco.adec.dt.model.Mapping inputMapping =
                        modelFactory.createMapping();
                inputMapping.setField(TEMP_VARIABLE_PREFIX + name);
                inputMapping.setFormalParameter(name);
                inputMappings.add(inputMapping);
            }
            return inputMappings;
        }
        return Collections.emptyList();
    }

    /**
     * Returns the list of ouput mappings
     * 
     * @param modelFactory
     * @return
     */
    private List<com.tibco.adec.dt.model.Mapping> getOutputMappings(
            DecisionTaskFactory modelFactory) {
        Object[] serviceDataMappings =
                getServiceDataMappings(MappingDirection.OUT);
        if (serviceDataMappings != null && serviceDataMappings.length > 0) {
            List<com.tibco.adec.dt.model.Mapping> outputMappings =
                    new ArrayList<com.tibco.adec.dt.model.Mapping>();
            Set<ConceptPath> rootPaths = new HashSet<ConceptPath>();
            for (Object serviceDataMapping : serviceDataMappings) {
                if (serviceDataMapping instanceof Mapping) {
                    Mapping mapping = (Mapping) serviceDataMapping;
                    Object source = mapping.getSource();
                    if (source instanceof ConceptPath) {
                        ConceptPath sourceMapping =
                                ((ConceptPath) source).getRoot();
                        if (sourceMapping.getName() != null) {
                            rootPaths.add(sourceMapping);
                        }
                    }
                }
            }
            for (ConceptPath rootPath : rootPaths) {
                if (rootPath.getName() != null) {
                    String name = rootPath.getName();
                    com.tibco.adec.dt.model.Mapping outputMapping =
                            modelFactory.createMapping();
                    outputMapping.setField(TEMP_VARIABLE_PREFIX + name);
                    outputMapping.setFormalParameter(name);
                    outputMappings.add(outputMapping);
                }
            }
            return outputMappings;
        }
        return Collections.emptyList();
    }

}

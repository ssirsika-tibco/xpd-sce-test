/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.n2.decisions.internal.configuration.mapping;

import org.eclipse.uml2.uml.Type;

import com.tibco.bx.xpdl2bpel.extensions.IDataModel;
import com.tibco.bx.xpdl2bpel.extensions.IMappingDataModel;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.decisions.internal.configuration.builder.DecisionServiceConfigurationModelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.FormalParameter;

/**
 * Decision Service Mapping Data Model
 * 
 * 
 * @author mtorres
 * 
 */
public class DecisionServiceMappingDataModel implements IMappingDataModel {

    private Mapping mapping;

    private MappingDirection mappingDirection;

    public DecisionServiceMappingDataModel(Mapping mapping,
            MappingDirection mappingDirection) {
        this.mapping = mapping;
        this.mappingDirection = mappingDirection;
    }

    @Override
    public IDataModel getSourceDataModel() {
        if (mapping != null) {
            Object mappingModel = mapping.getMappingModel();
            if (mappingModel instanceof DataMapping) {
                DataMapping dataMapping = (DataMapping) mappingModel;
                String text = DataMappingUtil.getScript(dataMapping);
                String grammar = DataMappingUtil.getGrammar(dataMapping);
                Object source = mapping.getSource();
                if (source instanceof ConceptPath) {
                    ConceptPath cp = (ConceptPath) source;
                    if (text != null
                            && mappingDirection.equals(MappingDirection.OUT)) {
                        text =
                                DecisionServiceConfigurationModelProvider.TEMP_VARIABLE_PREFIX
                                        + text;
                    }
                    Type conceptPathType =
                            DecisionServiceConfigurationModelProvider
                                    .getConceptPathType(cp);
                    if (grammar != null && text != null
                            && conceptPathType != null) {
                        return new DecisionServiceDataModel(grammar, text,
                                conceptPathType, cp.isArray());
                    }
                } else if (source instanceof ScriptInformation) {
                    if (grammar != null && text != null) {
                        return new DecisionServiceDataModel(grammar, text,
                                null, false);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public IDataModel getTargetDataModel() {
        if (mapping != null) {
            Object mappingModel = mapping.getMappingModel();
            if (mappingModel instanceof DataMapping) {
                DataMapping dataMapping = (DataMapping) mappingModel;
                String text = DataMappingUtil.getTarget(dataMapping);
                String grammar = DataMappingUtil.getGrammar(dataMapping);
                Object target = mapping.getTarget();
                if (target instanceof ConceptPath) {
                    ConceptPath cp = (ConceptPath) target;
                    if(text != null && mappingDirection.equals(MappingDirection.IN)){
                        text = DecisionServiceConfigurationModelProvider.TEMP_VARIABLE_PREFIX + text;
                    }
                    Type conceptPathType =
                            DecisionServiceConfigurationModelProvider
                                    .getConceptPathType(cp);
                    if (grammar != null && text != null
                            && conceptPathType != null) {
                        return new DecisionServiceDataModel(grammar, text,
                                conceptPathType, cp.isArray());
                    }
                } else if (target instanceof ScriptInformation) {
                    if (grammar != null && text != null) {
                        return new DecisionServiceDataModel(grammar, text,
                                null, false);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean isOptional() {
        if (mapping != null) {
            Object element = null;
            if (mappingDirection.equals(MappingDirection.IN)) {
                element = mapping.getTarget();
            } else {
                element = mapping.getSource();
            }
            if (element instanceof ConceptPath) {
                ConceptPath cp = (ConceptPath) element;
                Object item = cp.getItem();
                if (item instanceof FormalParameter) {
                    return !((FormalParameter) item).isRequired();
                }
            }
        }
        return true;
    }

}

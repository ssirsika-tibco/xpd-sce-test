/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author rsomayaj
 * 
 */
public class ParamMappingContentProvider implements ITreeContentProvider {

    private ConceptContentProvider umlContentProvider;

    private final DirectionType directionType;

    /**
     * @param inLiteral
     */
    public ParamMappingContentProvider(DirectionType directionType) {
        this.directionType = directionType;
        MappingDirection mappingDirection = MappingDirection.IN;
        if (DirectionType.OUT_LITERAL == directionType) {
            mappingDirection = MappingDirection.OUT;
        }
        umlContentProvider = new ConceptContentProvider(mappingDirection);
    }

    @Override
    public Object[] getElements(Object inputElement) {
        List<Object> elements = new ArrayList<Object>();
        if (inputElement instanceof StartMethod) {
            addParameters(elements,
                    getStartMethodElements((StartMethod) inputElement));
        } else if (inputElement instanceof IntermediateMethod) {
            addParameters(elements,
                    getIntermediateMethodElements((IntermediateMethod) inputElement));
        } else if (inputElement instanceof Activity
                && ProcessInterfaceUtil
                        .isEventImplemented((Activity) inputElement)) {
            Activity activity = (Activity) inputElement;
            if (ProcessInterfaceUtil.getImplementedStartMethod(activity) != null) {
                // Activity act = inputElement;
                addParameters(elements,
                        getStartMethodElements(ProcessInterfaceUtil
                                .getImplementedStartMethod(activity)));
            } else if (ProcessInterfaceUtil
                    .getImplementedIntermediateMethod((Activity) inputElement) != null) {
                addParameters(elements,
                        getIntermediateMethodElements(ProcessInterfaceUtil
                                .getImplementedIntermediateMethod((Activity) inputElement)));

            }
            Process process = activity.getProcess();
            List<DataField> coRelationData = getCorrelationDataFields(process);
            if (coRelationData.size() != 0) {
                CorrelationDataFolder container =
                        new CorrelationDataFolder(process);
                elements.add(container);
            }
        }

        return elements.toArray();
    }

    /**
     * @param parameters
     * @param formalParameters
     */
    private void addParameters(List<Object> parameters, List<?> toAdd) {
        for (Object next : toAdd) {
            if (next instanceof ProcessRelevantData) {
                ProcessRelevantData data = (ProcessRelevantData) next;
                if (data instanceof FormalParameter
                        || (data instanceof DataField && !((DataField) data)
                                .isCorrelation())) {
                    Class clss = ConceptUtil.getConceptClass(data);
                    ConceptPath path = new ConceptPath(data, clss);
                    parameters.add(path);
                }
            }
        }
    }

    private List<ProcessRelevantData> getIntermediateMethodElements(
            IntermediateMethod intermediateMethod) {
        List<ProcessRelevantData> mappingParameters =
                new ArrayList<ProcessRelevantData>();

        if (!(intermediateMethod.getAssociatedParameters().isEmpty())) {
            List<AssociatedParameter> assocParams =
                    intermediateMethod.getAssociatedParameters();
            for (AssociatedParameter associatedParameter : assocParams) {
                if (ProcessInterfaceUtil
                        .getAssocParamModeType(associatedParameter) != ModeType.OUT_LITERAL) {
                    ProcessRelevantData prd =
                            ProcessInterfaceUtil
                                    .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                    if (prd instanceof FormalParameter) {
                        mappingParameters.add(prd);
                    }
                }
            }
            return mappingParameters;
        }
        if (intermediateMethod.eContainer() != null) {
            /*
             * Sid XPD-2087: Only return all data implicitly if implicit
             * association has not been disabled.
             */
            if (!ProcessInterfaceUtil
                    .isImplicitAssociationDisabled(intermediateMethod)) {
                ProcessInterface pi =
                        (ProcessInterface) intermediateMethod.eContainer();
                List<FormalParameter> params = pi.getFormalParameters();
                for (FormalParameter param : params) {
                    if (param.getMode() != ModeType.OUT_LITERAL) {
                        mappingParameters.add(param);
                    }
                }
            }
        }
        return mappingParameters;
    }

    private List<ProcessRelevantData> getStartMethodElements(
            StartMethod startMethod) {
        List<ProcessRelevantData> mappingParameters =
                new ArrayList<ProcessRelevantData>();

        if (DirectionType.IN_LITERAL == directionType) {
            if (!(startMethod.getAssociatedParameters().isEmpty())) {
                List<AssociatedParameter> assocParams =
                        startMethod.getAssociatedParameters();
                for (AssociatedParameter associatedParameter : assocParams) {
                    if (ProcessInterfaceUtil
                            .getAssocParamModeType(associatedParameter) != ModeType.IN_LITERAL) {
                        ProcessRelevantData processRelevantDataFromAssociatedParam =
                                ProcessInterfaceUtil
                                        .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                        if (processRelevantDataFromAssociatedParam instanceof FormalParameter) {
                            mappingParameters
                                    .add(processRelevantDataFromAssociatedParam);
                        }
                    }
                }
            } else if (startMethod.eContainer() != null) {
                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(startMethod)) {
                    ProcessInterface pi =
                            (ProcessInterface) startMethod.eContainer();
                    List<FormalParameter> params = pi.getFormalParameters();
                    for (FormalParameter param : params) {
                        if (param.getMode() != ModeType.IN_LITERAL) {
                            mappingParameters.add(param);
                        }
                    }
                }
            }
        } else if (DirectionType.OUT_LITERAL == directionType) {
            if (!(startMethod.getAssociatedParameters().isEmpty())) {
                List<AssociatedParameter> assocParams =
                        startMethod.getAssociatedParameters();
                for (AssociatedParameter associatedParameter : assocParams) {
                    if (ProcessInterfaceUtil
                            .getAssocParamModeType(associatedParameter) != ModeType.OUT_LITERAL) {
                        ProcessRelevantData processRelevantDataFromAssociatedParam =
                                ProcessInterfaceUtil
                                        .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                        if (processRelevantDataFromAssociatedParam instanceof FormalParameter) {
                            mappingParameters
                                    .add(processRelevantDataFromAssociatedParam);
                        }
                    }
                }
            } else if (startMethod.eContainer() != null) {
                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(startMethod)) {
                    ProcessInterface pi =
                            (ProcessInterface) startMethod.eContainer();
                    List<FormalParameter> params = pi.getFormalParameters();
                    for (FormalParameter param : params) {
                        if (param.getMode() != ModeType.OUT_LITERAL) {
                            mappingParameters.add(param);
                        }
                    }
                }
            }
        }

        return mappingParameters;
    }

    private List<DataField> getCorrelationDataFields(Process process) {
        List<DataField> fields = new ArrayList<DataField>();
        for (DataField field : process.getDataFields()) {
            if (field.isCorrelation()) {
                fields.add(field);
            }
        }
        return fields;
    }

    private boolean isCorrelationDataField(Object element) {
        boolean isCorrelation = false;
        if (element instanceof ConceptPath) {
            ConceptPath path = (ConceptPath) element;
            Object item = path.getItem();
            if (item instanceof DataField) {
                DataField field = (DataField) item;
                isCorrelation = field.isCorrelation();
            }
        }
        return isCorrelation;
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        Object[] items;
        if (parentElement instanceof CorrelationDataFolder) {
            CorrelationDataFolder folder =
                    (CorrelationDataFolder) parentElement;
            Process process = folder.getProcess();
            List<DataField> fields = getCorrelationDataFields(process);
            List<ConceptPath> children = new ArrayList<ConceptPath>();
            for (DataField next : fields) {
                if (next.isCorrelation()) {
                    Class clss = ConceptUtil.getConceptClass(next);
                    ConceptPath path = new ConceptPath(next, clss);
                    children.add(path);
                }
            }
            items = children.toArray();
        } else {
            items = umlContentProvider.getChildren(parentElement);
        }
        return items;
    }

    @Override
    public Object getParent(Object element) {
        Object parent = null;
        if (isCorrelationDataField(element)) {
            if (element instanceof ConceptPath) {
                ConceptPath path = (ConceptPath) element;
                Object item = path.getItem();
                if (item instanceof DataField) {
                    DataField field = (DataField) item;
                    Process process = Xpdl2ModelUtil.getProcess(field);
                    if (process != null) {
                        parent = new CorrelationDataFolder(process);
                    }
                }
            }
        }
        if (parent == null) {
            parent = umlContentProvider.getParent(element);
        }
        return parent;
    }

    @Override
    public boolean hasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof CorrelationDataFolder) {
            CorrelationDataFolder folder = (CorrelationDataFolder) element;
            Process process = folder.getProcess();
            hasChildren = getCorrelationDataFields(process).size() != 0;
        } else {
            hasChildren = umlContentProvider.hasChildren(element);
        }
        return hasChildren;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    }

}

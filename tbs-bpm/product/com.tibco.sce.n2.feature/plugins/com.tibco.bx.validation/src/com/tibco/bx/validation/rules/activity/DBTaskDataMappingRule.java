/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * if the data mapping parameter mode is IN then the field cannot reference a
 * complex type
 * 
 * 
 * @author bharge
 * @since 3.3 (9 Aug 2010)
 */
public class DBTaskDataMappingRule extends FlowContainerValidationRule {

    private static final String CANNOT_REF_COMPLEX_TYPE_ISSUE =
            "bx.mapToComplexTypeInvalid"; //$NON-NLS-1$

    private static final String COMPLEX_TYPE_MUST_BE_ARRAY =
            "bx.complexTypeMustBeArray"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();
                TaskService taskService = task.getTaskService();
                if (null != taskService) {
                    String implType =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (TaskImplementationTypeDefinitions.DATABASE_SERVICE.equalsIgnoreCase(implType)) {
                        List<DataMapping> dataMappings =
                                taskService.getMessageIn().getDataMappings();
                        for (DataMapping dataMapping : dataMappings) {
                            if (DirectionType.IN_LITERAL.equals(dataMapping
                                    .getDirection())) {
                                ProcessRelevantData processRelevantData =
                                        getProcRelevantData(dataMapping);
                                if (null != processRelevantData) {
                                    if (processRelevantData.getDataType() instanceof ExternalReference) {
                                        List<String> messages =
                                                new ArrayList<String>();
                                        messages.add(processRelevantData
                                                .getName());
                                        messages.add(activity.getName());
                                        addIssue(CANNOT_REF_COMPLEX_TYPE_ISSUE,
                                                activity,
                                                messages);
                                    }
                                }
                            }

                        }

                        /**
                         * for out or in_out types if the parameter is
                         * referencing a bom then it has to be an array (since
                         * the return type would be a result set it could have
                         * returned more than one row)
                         */
                        List<DataMapping> dataOutMappings =
                                taskService.getMessageOut().getDataMappings();
                        for (DataMapping dataMapping : dataOutMappings) {
                            ProcessRelevantData processRelevantData =
                                    getProcRelevantData(dataMapping);
                            if (null != processRelevantData) {
                                if (processRelevantData.getDataType() instanceof ExternalReference) {
                                    if (!processRelevantData.isIsArray()) {
                                        List<String> messages =
                                                new ArrayList<String>();
                                        messages.add(processRelevantData
                                                .getName());
                                        messages.add(activity.getName());
                                        addIssue(COMPLEX_TYPE_MUST_BE_ARRAY,
                                                activity,
                                                messages);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private ProcessRelevantData getProcRelevantData(DataMapping dataMapping) {
        ConceptPath conceptPath = getConceptPathForDataMapping(dataMapping);
        if (null != conceptPath) {
            Object item = conceptPath.getItem();
            if (item instanceof ProcessRelevantData) {
                ProcessRelevantData processRelevantData =
                        (ProcessRelevantData) item;
                return processRelevantData;
            }
        }
        return null;
    }

    private ConceptPath getConceptPathForDataMapping(DataMapping mapping) {
        ConceptPath conceptPath = null;
        if (mapping != null) {
            Activity act = Xpdl2ModelUtil.getParentActivity(mapping);
            if (act != null) {
                Expression actual = mapping.getActual();
                if (actual != null) {
                    String text = actual.getText();
                    if (text != null) {
                        conceptPath = ConceptUtil.resolveConceptPath(act, text);
                    }
                }
            }
        }
        return conceptPath;
    }
}

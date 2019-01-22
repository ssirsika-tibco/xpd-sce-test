/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ProcessInterfaceParameterInitialValueRule extends
        DataTypeInitialValueRule {

    protected String DUPLICATE_VALUE =
            "ipm.parameterInitialValueDuplicateValue"; //$NON-NLS-1$

    public ProcessInterfaceParameterInitialValueRule() {
        INVALID_VALUE = "bpmn.parameterInitialValueIncorrect"; //$NON-NLS-1$
        INVALID_LENGTH = "bpmn.parameterInitialValueInvalidLength"; //$NON-NLS-1$
        DATE_FORMAT = "bpmn.parameterInitialValueDateFormat"; //$NON-NLS-1$
    }

    /**
     * Gets the list of data from the parameter object so we can later perform
     * validation checks.
     * 
     * @param pd
     * @return
     */
    protected String[] initialValueSetup(ProcessRelevantData pd) {
        String[] initialValues = new String[] {};
        FormalParameter formalParameter = (FormalParameter) pd;
        if (formalParameter.getOtherElement(XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_InitialValues().getName()) != null) {
            List<String> tempList =
                    ((InitialValues) formalParameter
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues().getName()))
                            .getValue();
            initialValues = (String[]) tempList.toArray();
            // MR 37833
            if (initialValues.length > 0) {
                List<String> uiInitialValuesList = new ArrayList<String>();
                for (String modelInitialValue : initialValues) {
                    String uiInitialValue =
                            ProcessDataUtil.convertModelInitialValueToUIFormat(formalParameter,
                                    modelInitialValue);
                    
                    uiInitialValuesList.add(uiInitialValue);
                }
                return uiInitialValuesList.toArray(new String[] {});
            }
            // MR 37833
        }

        return initialValues;
    }

    @Override
    public void validate(Object o) {
        ProcessInterface processInterface = (ProcessInterface) o;

        List<ProcessRelevantData> formalParameters =
                new ArrayList<ProcessRelevantData>(processInterface
                        .getFormalParameters());
        parameterOnlyValidation(formalParameters);
        super.validateFieldsOrParams(formalParameters);
    }

    protected void parameterOnlyValidation(List<ProcessRelevantData> formalParameters) {
        for (ProcessRelevantData pd : formalParameters) {
            String[] initialValues = initialValueSetup(pd);
            // Check for duplicates in Initial Values
            if (hasDuplicates(initialValues)) {
                addIssue(DUPLICATE_VALUE, pd);
            }
        }
    }

    private boolean hasDuplicates(String[] initialValues) {

        boolean hasDuplicates = false;
        if (initialValues != null && initialValues.length > 1) {
            Set<String> initialValuesSet = new HashSet<String>();
            for (int i = 0; i < initialValues.length; i++) {
                String initialValue = initialValues[i];
                if (initialValue != null) {
                    initialValuesSet.add(initialValue);
                }
            }
            if (initialValues.length != initialValuesSet.size()) {
                hasDuplicates = true;
            }
        }
        return hasDuplicates;
    }

    public Class getTargetClass() {
        return ProcessInterface.class;
    }

}

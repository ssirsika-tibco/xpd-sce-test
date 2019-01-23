/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Validation to check values inserted for Initial Values on Data Fields.
 * 
 * @author glewis
 */
public class PackageDataFieldInitialValueRule extends DataTypeInitialValueRule {

    /**
     * Initialise message string for this rule
     */
    public PackageDataFieldInitialValueRule() {
        INVALID_VALUE = "bpmn.packageDataFieldInitialValueIncorrect_1"; //$NON-NLS-1$
        INVALID_LENGTH = "bpmn.packageDataFieldInitialValueInvalidLength"; //$NON-NLS-1$
        DATE_FORMAT = "bpmn.packageDataFieldInitialValueDateFormat"; //$NON-NLS-1$
        INVALID_NOT_ARRAY = "bpmn.packageDataFieldInitialValueInvalidNotArray"; //$NON-NLS-1$
    }

    /**
     * @return The class type on which this rule will operate.
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class getTargetClass() {
        return Package.class;
    }

    /**
     * Gets the list of data from the parameter object so we can later perform
     * validation checks.
     * 
     * @param pd
     * @return
     */
    @Override
    protected String[] initialValueSetup(ProcessRelevantData pd) {
        DataField dataField = (DataField) pd;
        boolean isArray = dataField.isIsArray();

        List<String> initialValues =
                ProcessDataUtil.getDataInitialValues(pd, false);

        if (!isArray && initialValues.size() > 1) {
            addIssue(INVALID_NOT_ARRAY, pd);
        }

        return initialValues.toArray(new String[] {});
    }

    @Override
    public void validate(Object o) {
        Package tempPackage = (Package) o;

        ArrayList<ProcessRelevantData> pkgFields =
                new ArrayList<ProcessRelevantData>(tempPackage.getDataFields());
        super.validateFieldsOrParams(pkgFields);

        for (Iterator iterator = tempPackage.getProcesses().iterator(); iterator
                .hasNext();) {
            Process process = (Process) iterator.next();

            ArrayList<ProcessRelevantData> processFields =
                    new ArrayList<ProcessRelevantData>(process.getDataFields());

            super.validateFieldsOrParams(processFields);
        }

    }
}

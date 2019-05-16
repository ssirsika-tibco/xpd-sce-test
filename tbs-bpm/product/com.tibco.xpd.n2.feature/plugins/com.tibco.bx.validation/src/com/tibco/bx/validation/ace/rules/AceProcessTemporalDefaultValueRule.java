/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * A rule that creates an issue if the temporal attributes of a given process
 * have a default value defined.
 *
 * @author pwatson
 * @since 16 May 2019
 */
public class AceProcessTemporalDefaultValueRule extends ProcessValidationRule {

    private static final String ISSUE_TEMPORAL_DEFAULT_VALUE =
            "ace.process.temporal.default.value"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     *
     * @param aProcess
     *            the process to be validated
     */
    @Override
    public void validate(Process aProcess) {
        // validate all process data fields
        List<ProcessRelevantData> processData =
                ProcessInterfaceUtil.getAllProcessRelevantData(aProcess);

        // validate each data field
        for (ProcessRelevantData data : processData) {
            // we're only interested in data fields not parameters
            if (data instanceof DataField) {
                validate((DataField) data);
            }
        }
    }

    /**
     * Validates the given DataField to see if it's a temporal field with an
     * initial value.
     * 
     * @param aDataField
     *            the field to be validated.
     */
    private void validate(DataField aDataField) {
        if ((isTemporalType(aDataField)) && (hasInitialValue(aDataField))) {
            addIssue(ISSUE_TEMPORAL_DEFAULT_VALUE,
                    aDataField,
                    Collections.singletonList(getDataTypeName(aDataField)));
        }
    }

    /**
     * Returns the name that identifies the type of data held by the given
     * DataType.
     * 
     * @param aDataField
     *            the data field whose type name is required.
     * @return the name of the field's data type.
     */
    private String getDataTypeName(DataField aDataField) {
        DataType dataType = aDataField.getDataType();
        if (!(dataType instanceof BasicType)) {
            return ""; //$NON-NLS-1$
        }

        BasicTypeType basicType = ((BasicType) dataType).getType();
        return basicType.getName();
    }

    /**
     * Tests if the given data field is one of date, time or date-time.
     * 
     * @param aDataField
     *            the data field to be tested.
     * @return <code>true</code> if the data field is a temporal one.
     */
    private boolean isTemporalType(DataField aDataField) {
        DataType dataType = aDataField.getDataType();
        if (!(dataType instanceof BasicType)) {
            return false;
        }

        BasicTypeType basicType = ((BasicType) dataType).getType();
        return (BasicTypeType.DATE_LITERAL.equals(basicType))
                || (BasicTypeType.DATETIME_LITERAL.equals(basicType))
                || (BasicTypeType.TIME_LITERAL.equals(basicType));
    }

    /**
     * Tests if the given data field has an initial value configured.
     * 
     * @param aDataField
     *            the data field to be tested.
     * @return <code>true</code> if the data field has an initial value.
     */
    private boolean hasInitialValue(DataField aDataField) {
        // retrieve the initial values from the "other elements"
        Object values = Xpdl2ModelUtil.getOtherElement(aDataField,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_InitialValues());

        // if none found
        if (!(values instanceof InitialValues)) {
            return false;
        }

        // the initial values holds a list of values
        EList<String> list = ((InitialValues) values).getValue();

        // are any list items present
        return (list != null) && (!list.isEmpty());
    }
}

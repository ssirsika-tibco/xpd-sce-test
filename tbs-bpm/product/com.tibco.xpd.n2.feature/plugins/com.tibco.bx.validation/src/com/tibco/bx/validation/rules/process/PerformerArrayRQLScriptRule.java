/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Collection;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to check if a Data field of Basic type PERFORMER having array type set
 * should not have Initial value defined as 'RQL' script.
 * 
 * @author kthombar
 * @since 10-Sep-2013
 */
public class PerformerArrayRQLScriptRule extends PackageValidationRule {

    /*
     * Initial value as RQL script cannot be used for Performer array.
     */
    private static String ISSUE_PERFORMER_WITH_ARRAY_AND_RQL_SCRIPT =
            "bx.performerWithArrayAndRqlScript"; //$NON-NLS-1$

    private static final String RQL_GRAMMAR = "RQL"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     */
    @Override
    public void validate(Package pckg) {

        Collection<ProcessRelevantData> allDataInPackage =
                ProcessInterfaceUtil.getAllDataInPackage(pckg);

        if (allDataInPackage != null) {
            for (ProcessRelevantData processRelevantData : allDataInPackage) {
                /**
                 * Checking only for data fields as Participant have 'allowed
                 * values' and not 'initial value'
                 */
                if (processRelevantData instanceof DataField) {
                    DataField dataField = (DataField) processRelevantData;
                    validateDataField(dataField);
                }
            }
        }
    }

    /**
     * Checks if the Data Field has basic type = PERFORMER and is a array type
     * and has Initial value set to RQL. If yes then raise error marker.
     * 
     * @param processData
     */
    private void validateDataField(DataField dataField) {

        BasicType basicType =
                BasicTypeConverterFactory.INSTANCE.getBasicType(dataField);

        if (basicType != null
                && BasicTypeType.PERFORMER_LITERAL.equals(basicType.getType())) {
            /*
             * If the datafield has array type set only then check if initial
             * value ="RQL"
             */
            if (dataField.isIsArray()) {
                Object participantQuery =
                        Xpdl2ModelUtil.getOtherElement(basicType,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery());
                if (participantQuery != null
                        && participantQuery instanceof Expression) {
                    Expression participantExpression =
                            (Expression) participantQuery;
                    if (participantExpression.getScriptGrammar()
                            .equals(RQL_GRAMMAR)) {
                        addIssue(ISSUE_PERFORMER_WITH_ARRAY_AND_RQL_SCRIPT,
                                dataField);
                    }
                }
            }
        }
    }
}

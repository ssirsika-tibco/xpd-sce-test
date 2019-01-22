/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * TypeDeclarationTypeRule - This rule validates that, if a Data Field, Formal
 * Parameter , CorrelationData or TypeDeclaration is of type TypeDeclaration,
 * then a Declared Type must be selected
 * 
 * 
 * @author bharge
 * @since 3.3 (2 Feb 2010)
 */
public class TypeDeclarationTypeRule extends PackageValidationRule {

    private static final String NO_DECLAREDTYPE_SELECTED_ISSUE =
            "bpmn.noTypeDeclarationSelected"; //$NON-NLS-1$

    private static final String NO_DECLAREDTYPE_FOR_FIELDS_SELECTED_ISSUE =
            "bpmn.noTypeDeclarationForProcessDataSelected"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com
     * .tibco.xpd.xpdl2.Package)
     */

    @Override
    public void validate(Package pckg) {
        if (null != pckg) {
            EList<TypeDeclaration> typeDeclarations =
                    pckg.getTypeDeclarations();
            for (TypeDeclaration typeDeclaration : typeDeclarations) {
                if (null != typeDeclaration.getDeclaredType()) {
                    if (typeDeclaration.getDeclaredType()
                            .getTypeDeclarationId().length() <= 0) {
                        addIssue(NO_DECLAREDTYPE_SELECTED_ISSUE,
                                typeDeclaration);
                    }
                }
            }
            EList<Process> processes = pckg.getProcesses();
            for (Process process : processes) {
                validateDataFields(process);
                validateFormalParameter(process);
            }

        }
    }

    /**
     * Validate the Data fields having the type as 'Type Declaration'
     * 
     * @param process
     */
    private void validateDataFields(Process process) {
        EList<DataField> dataFields = process.getDataFields();
        for (DataField datafield : dataFields) {
            validateTypeDeclarationReference(process, datafield);
        }
    }

    /**
     * Validate the Formal Parameters having the type as 'Type Declaration'
     * 
     * @param process
     */
    private void validateFormalParameter(Process process) {

        EList<FormalParameter> formalParameters = process.getFormalParameters();
        for (FormalParameter formalParameter : formalParameters) {
            validateTypeDeclarationReference(process, formalParameter);
        }
    }

    /**
     * Check if the data having type = 'Type Declaration' has a Declared type,
     * if not then raise an issue
     * 
     * @param process
     * @param data
     */
    private void validateTypeDeclarationReference(Process process,
            ProcessRelevantData data) {

        DataType dataType = data.getDataType();
        if (dataType != null
                && dataType instanceof DeclaredType
                && process
                        .getPackage()
                        .getTypeDeclaration(((DeclaredType) dataType).getTypeDeclarationId()) == null) {

            addIssue(NO_DECLAREDTYPE_FOR_FIELDS_SELECTED_ISSUE, data);

        }
    }
}

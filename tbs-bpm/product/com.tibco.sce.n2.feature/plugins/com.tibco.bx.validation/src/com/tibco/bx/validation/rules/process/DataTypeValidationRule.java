package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class DataTypeValidationRule extends ProcessValidationRule {

    private static final String ISSUE_FORMALPARAMDECLAREDTYPE_ID =
            "n2pe.processFormalParametersCannotBeDeclaredTypes"; //$NON-NLS-1$

    public static final String ISSUE_INVALID_DATA_TYPE = "n2pe.invalidDataType"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {
        List<FormalParameter> parameters =
                ProcessInterfaceUtil.getAllFormalParameters(process);
        if (parameters != null) {
            for (FormalParameter parameter : parameters) {
                validateDataType(process, parameter.getDataType());
            }
        }

        List<DataField> dataFields = process.getDataFields();
        if (dataFields != null) {
            for (DataField dataField : dataFields) {
                validateDataField(process, dataField);
            }
        }
    }

    private boolean validateDataType(Process process, DataType dataType) {
        if (dataType instanceof BasicType) {
            BasicTypeType basicTypeType = ((BasicType) dataType).getType();
            if (basicTypeType == BasicTypeType.REFERENCE_LITERAL) {
                addDataTypeError(basicTypeType.getName(), dataType);
                return false;
            }
        } else if (dataType instanceof DeclaredType) {
            DeclaredType dt = (DeclaredType) dataType;
            String typeId = dt.getTypeDeclarationId();
            com.tibco.xpd.xpdl2.Package pckg =
                    Xpdl2ModelUtil.getPackage(process);
            if (pckg == null || typeId == null) {
                addDataTypeError(dt.getName(), dataType);
                return false;
            }

            TypeDeclaration typeDecl = pckg.getTypeDeclaration(typeId);
            if (typeDecl == null) {
                // Kapil- This issue is raised by BPMN rule, so commenting it
                // out from here.
                // addDataTypeError(dt.getName(), dataType);
                return false;
            }

            boolean validateDataType = false;
            if (typeDecl.getBasicType() != null) {
                validateDataType =
                        validateDataType(process, typeDecl.getBasicType());

            } else if (typeDecl.getExternalReference() != null) {
                validateDataType =
                        validateDataType(process,
                                typeDecl.getExternalReference());
            }

            if (validateDataType == true
                    && dataType.eContainer() instanceof FormalParameter) {
                FormalParameter formalParameter =
                        (FormalParameter) dataType.eContainer();
                addIssue(ISSUE_FORMALPARAMDECLAREDTYPE_ID,
                        formalParameter,
                        Collections.singletonList(formalParameter.getName()));
                return false;
            }

            if (validateDataType) {
                return validateDataType;
            }

            return false;
        }
        // Kapil- Added a null check. Some extreme cases might have dataType =
        // null
        else if (!(dataType instanceof ExternalReference || dataType instanceof RecordType)
                && dataType != null) {
            String className = dataType.eClass().getInstanceClass().getName();
            addDataTypeError(className, dataType);
            return false;
        }

        return true;
    }

    private void validateDataField(Process process, DataField dataField) {
        DataType dataType = dataField.getDataType();
        if (!validateDataType(process, dataType)) {
            return;
        }

        // data type is OK; now validate the String data that has an initial
        // value
        // BasicTypeType basicTypeType = ((BasicType)dataType).getType();
        // if (basicTypeType == BasicTypeType.STRING_LITERAL) {
        // Expression initialValue = dataField.getInitialValue();
        // if (initialValue != null && initialValue.getText() != null) {
        // String text = initialValue.getText();
        // if (!text.startsWith("\"") || !text.endsWith("\"")) {
        // addDataFieldError(text, dataField);
        // }
        // }
        // }
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
    }

    private void addDataTypeError(String message, EObject obj) {
        List<String> messages = new ArrayList<String>();
        messages.add(message);
        this.addIssue(ISSUE_INVALID_DATA_TYPE, obj, messages);
    }

}
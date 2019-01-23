/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * BasicTypeLengthRule - validates the length (for integer, and decimal data
 * types) to be greater than zero
 * 
 * validates the scale (for decimal data types) to be non-negative number
 * validates if the scale (for decimal data types) is greater than the length
 * specified
 * 
 * @author bharge
 * @since 3.3 (15 Feb 2010)
 */
public class BasicTypeLengthRule extends PackageValidationRule {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com
     * .tibco.xpd.xpdl2.Package)
     */
    private static final String INVALID_LENGTH = "bpmn.lengthNotCorrect"; //$NON-NLS-1$

    private static final String INVALID_SCALE = "bpmn.scaleNotCorrect"; //$NON-NLS-1$

    @Override
    public void validate(Package pckg) {
        if (null != pckg) {
            List dataFields = pckg.getDataFields();
            validateFieldsOrParamsLength(dataFields);

            EList<TypeDeclaration> typeDeclarations =
                    pckg.getTypeDeclarations();
            validateTypeDeclLength(typeDeclarations);

            List<Process> processes = pckg.getProcesses();
            for (Process process : processes) {
                List fields = process.getDataFields();
                validateFieldsOrParamsLength(fields);

                List parameters = process.getFormalParameters();
                validateFieldsOrParamsLength(parameters);
            }

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pckg);
            if (null != processInterfaces) {
                List<ProcessInterface> processInterfaceList =
                        processInterfaces.getProcessInterface();
                for (ProcessInterface processInterface : processInterfaceList) {
                    List formalParameters =
                            processInterface.getFormalParameters();
                    validateFieldsOrParamsLength(formalParameters);
                }
            }
        }
    }

    /**
     * @param typeDeclarations
     */
    protected void validateTypeDeclLength(
            EList<TypeDeclaration> typeDeclarations) {
        for (TypeDeclaration typeDeclaration : typeDeclarations) {
            BasicType basicType = typeDeclaration.getBasicType();
            if (null != basicType) {
                validateLengthOrScale(typeDeclaration, basicType);
            }
        }
    }

    /**
     * @param dataFields
     */
    protected void validateFieldsOrParamsLength(
            List<ProcessRelevantData> prdList) {
        for (ProcessRelevantData prd : prdList) {
            DataType dataType = prd.getDataType();
            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;
                validateLengthOrScale(prd, basicType);
            }
        }
    }

    /**
     * @param namedElement
     * @param basicType
     */
    protected void validateLengthOrScale(NamedElement namedElement,
            BasicType basicType) {
        String lengthPattern = "[1-9]\\d*"; //$NON-NLS-1$
        String scalePattern = "[0-9]\\d*"; //$NON-NLS-1$
        if (basicType != null) {
            if (basicType.getType() != null
                    && basicType.getType()
                            .equals(BasicTypeType.INTEGER_LITERAL)) {
                Short value = 0;

                if (basicType.getPrecision() != null) {
                    value = basicType.getPrecision().getValue();
                }

                if (!Pattern.matches(lengthPattern, value.toString())) {
                    addIssue(INVALID_LENGTH, namedElement);
                }

            } else if (basicType.getType() != null
                    && basicType.getType().equals(BasicTypeType.FLOAT_LITERAL)) {
                Short precision = 0;

                if (basicType.getPrecision() != null) {
                    precision = basicType.getPrecision().getValue();
                }

                Short scale = 0;
                if (basicType.getScale() != null) {
                    scale = basicType.getScale().getValue();
                }
                if (basicType.getScale() == null) {
                    addIssue(INVALID_SCALE, namedElement);
                }
                if (!Pattern.matches(lengthPattern, precision.toString())) {
                    addIssue(INVALID_LENGTH, namedElement);
                }
                if (!Pattern.matches(scalePattern, scale.toString())) {
                    addIssue(INVALID_SCALE, namedElement);
                }
                if (scale >= precision) {
                    addIssue(INVALID_SCALE, namedElement);
                }
            }
        }
    }
}

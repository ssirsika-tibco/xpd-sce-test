/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;
import java.util.regex.Pattern;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.FormalParameter;
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

    private static final String ISSUE_NOSCALE_MUST_HAVE_NOLENGTH =
            "bpmn.noScaleMustHaveNoLength"; //$NON-NLS-1$

    private static final String LENGTH_PATTERN = "[1-9]\\d*"; //$NON-NLS-1$

    private static final String SCALE_PATTERN = "[0-9]\\d*"; //$NON-NLS-1$

    @Override
    public void validate(Package pckg) {
        if (null != pckg) {
            List<DataField> dataFields = pckg.getDataFields();
            validateFieldsOrParamsLength(dataFields);

            List<TypeDeclaration> typeDeclarations = pckg.getTypeDeclarations();
            validateTypeDeclLength(typeDeclarations);

            List<Process> processes = pckg.getProcesses();
            for (Process process : processes) {
                List<DataField> fields = process.getDataFields();
                validateFieldsOrParamsLength(fields);

                List<FormalParameter> parameters =
                        process.getFormalParameters();
                validateFieldsOrParamsLength(parameters);
            }

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pckg);
            if (null != processInterfaces) {
                List<ProcessInterface> processInterfaceList =
                        processInterfaces.getProcessInterface();
                for (ProcessInterface processInterface : processInterfaceList) {
                    List<FormalParameter> formalParameters =
                            processInterface.getFormalParameters();
                    validateFieldsOrParamsLength(formalParameters);
                }
            }
        }
    }

    /**
     * Validates the numeric type declaration in the given list.
     * 
     * @param typeDeclarations
     */
    protected void validateTypeDeclLength(
            List<TypeDeclaration> typeDeclarations) {
        for (TypeDeclaration typeDeclaration : typeDeclarations) {
            BasicType basicType = typeDeclaration.getBasicType();
            if (null != basicType) {
                validateLengthOrScale(typeDeclaration, basicType);
            }
        }
    }

    /**
     * Validates the numeric data fields in the given list.
     * 
     * @param dataFields
     */
    protected void validateFieldsOrParamsLength(
            List<? extends ProcessRelevantData> prdList) {
        for (ProcessRelevantData prd : prdList) {
            DataType dataType = prd.getDataType();
            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;
                validateLengthOrScale(prd, basicType);
            }
        }
    }

    /**
     * Validates the length and decimal places configured on the given numeric
     * attribute element.
     * 
     * @param namedElement
     *            the attribute to be validated.
     * @param basicType
     *            the data type of the attribute.
     */
    protected void validateLengthOrScale(NamedElement namedElement,
            BasicType basicType) {
        if (basicType == null) {
            return;
        }

        BasicTypeType type = basicType.getType();
        if (type == null) {
            return;
        }

        if (type.equals(BasicTypeType.INTEGER_LITERAL)) {
            Short value = 0;

            if (basicType.getPrecision() != null) {
                value = basicType.getPrecision().getValue();
            }

            if (!Pattern.matches(LENGTH_PATTERN, value.toString())) {
                addIssue(INVALID_LENGTH, namedElement);
            }
        }

        else if (type.equals(BasicTypeType.FLOAT_LITERAL)) {
            /*
             * Sid ACE-1355 Numbers are now allowed to have no decimal places
             * defined (this means 'floating point number' rather than fixed
             * point number.
             */
            // if (basicType.getScale() == null) {
            // addIssue(INVALID_SCALE, namedElement);
            // }

            /*
             * Sid ACE-1355 Length is allowed to be null if the scale is (as it
             * means it's a floating point number then there can't be specific
             * precision).
             */
            if (basicType.getScale() != null) {
                short precision = 0;
                if (basicType.getPrecision() != null) {
                    precision = basicType.getPrecision().getValue();
                }

                short scale = 0;
                if (basicType.getScale() != null) {
                    scale = basicType.getScale().getValue();
                }

                if (!Pattern.matches(LENGTH_PATTERN,
                        Short.toString(precision))) {
                    addIssue(INVALID_LENGTH, namedElement);
                }
                if (!Pattern.matches(SCALE_PATTERN, Short.toString(scale))) {
                    addIssue(INVALID_SCALE, namedElement);
                } else if (scale > precision) {
                    addIssue(INVALID_SCALE, namedElement);
                }
            } else {
                /*
                 * Sid ACE-1355 - If scale is not set then length must not be
                 * set.
                 */
                if (basicType.getPrecision() != null) {
                    addIssue(ISSUE_NOSCALE_MUST_HAVE_NOLENGTH, namedElement);
                }
            }
        }
    }
}

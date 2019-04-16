/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.bds.designtime.validator.rules;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.resources.ui.internal.calendar.BOMDateUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * BOM validation rule to validate a Enumeration for Decisions.
 * 
 * @author njpatel
 */
public class EnumerationValidationRule implements IValidationRule {

    private static final String ISSUE_NO_ENUMLIT_VALUE = "cds.noEnumLitValue"; //$NON-NLS-1$

    private static final String ISSUE_NO_LOWER_VALUE =
            "cds.noEnumLitLowerValue"; //$NON-NLS-1$

    private static final String ISSUE_NO_UPPER_VALUE =
            "cds.noEnumLitUpperValue"; //$NON-NLS-1$

    private static final String ISSUE_INVALID_VALUE = "cds.invalidEnumLitValue"; //$NON-NLS-1$

    private static final String ISSUE_INVALID_LOWER_VALUE =
            "cds.invalidEnumLitLowerValue"; //$NON-NLS-1$

    private static final String ISSUE_INVALID_UPPER_VALUE =
            "cds.invalidEnumLitUpperValue"; //$NON-NLS-1$

    private static final String ISSUE_TYPE_NOT_SUPPORTED =
            "cds.unsupportedEnumType"; //$NON-NLS-1$

    private static final String ISSUE_RANGE_UPPER_GREATER_THAN_LOWER =
            "cds.upperValueGreaterThanLowerInRange"; //$NON-NLS-1$

    private static final String ISSUE_ONLY_TWO_LITERALS_FOR_BOOLEAN =
            "cds.onlyTwoLiteralsAllowedInBooleanEnum"; //$NON-NLS-1$

    private static final String ISSUE_ONLY_ONE_TRUE_AND_FALSE =
            "cds.onlyOneTrueAndFalseLitAllowed"; //$NON-NLS-1$

    private static final String ISSUE_INVALID_STRING_LITERAL =
            "cds.invalidStringEnumLitValue"; //$NON-NLS-1$

    private static final String ISSUE_RANGE_NOT_SUPPORTED =
            "cds.rangeOnLiteralsNotSupported"; //$NON-NLS-1$

    private IValidationScope scope;

    /**
     * List of all supported Enumeration types
     */
    private static final String[] SUPPORTED_TYPES = new String[] {
            PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME };

    /**
     * Create an issue against the given EObject.
     * 
     * @param eo
     * @param location
     * @param issue_id
     * @param params
     *            optional params to set for the validation message
     */
    private void createIssue(IValidationScope scope, EObject eo,
            String location, String issue_id, String... params) {
        if (params.length > 0) {
            scope.createIssue(issue_id,
                    location,
                    getURIStr(eo),
                    Arrays.asList(params));
        } else {
            scope.createIssue(issue_id, location, getURIStr(eo));
        }
    }

    /**
     * Get the URI string to add to the issue.
     * 
     * @param eo
     * @return
     */
    private String getURIStr(EObject eo) {
        if (eo != null && eo.eResource() != null) {
            return eo.eResource().getURIFragment(eo);
        }
        return null;
    }

    /**
     * Check if the given value is valid for the type specified. Only checks if
     * type is Decimal or Integer.
     * 
     * @param type
     * @param value
     * @return
     */
    private boolean isValueValid(PrimitiveType type, String value) {

        try {
            if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                    .equals(type.getName())) {
                Double.parseDouble(value);
            } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME.equals(type
                    .getName())) {
                Integer.parseInt(value);
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    @Override
    public Class<?> getTargetClass() {
        return Enumeration.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        this.scope = scope;
        if (obj instanceof Enumeration) {
            validateEnumeration((Enumeration) obj);
        }
    }

    private boolean isBaseTypeSupported(PrimitiveType type) {
        if (type != null) {
            for (String supportedTypes : SUPPORTED_TYPES) {
                if (supportedTypes.equals(type.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Validate the given {@link Enumeration}.
     * 
     * @param obj
     */
    private void validateEnumeration(Enumeration enumeration) {

        /*
         * Check for enumeration supported types
         */
        PrimitiveType type = EnumLitValueUtil.getBaseType(enumeration);

        if (isBaseTypeSupported(type)) {

            if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME
                    .equals(type.getName())) {
                createEnumIssue(enumeration,
                        ISSUE_TYPE_NOT_SUPPORTED,
                        PrimitivesUtil.getDisplayLabel(type));
                /*
                 * Boolean type Enumeration can only have 2 enum literals
                 */
                validateBooleanEnumeration(enumeration);
            } else {
                /* Supported type so validate the literals */
                for (EnumerationLiteral literal : enumeration
                        .getOwnedLiterals()) {
                    validateLiteral(literal);
                }
            }
        } else {
            createEnumIssue(enumeration,
                    ISSUE_TYPE_NOT_SUPPORTED,
                    PrimitivesUtil.getDisplayLabel(type));

        }
    }

    /**
     * @param enumeration
     */
    private void validateBooleanEnumeration(Enumeration enumeration) {
        // TODO: DOES IT HAVE TO HAVE 2 LITERALS?
        if (enumeration.getOwnedLiterals().size() > 2) {
            createEnumIssue(enumeration, ISSUE_ONLY_TWO_LITERALS_FOR_BOOLEAN);
        } else {
            EnumerationLiteral trueLiteral = null;
            EnumerationLiteral falseLiteral = null;

            for (EnumerationLiteral literal : enumeration.getOwnedLiterals()) {
                DomainValue value = EnumLitValueUtil.getDomainValue(literal);
                if (value != null) {
                    if (Boolean.parseBoolean(value.getValue())) {
                        if (trueLiteral == null) {
                            trueLiteral = literal;
                        } else {
                            /* There is a true literal defined already */
                            createLiteralIssue(literal,
                                    ISSUE_ONLY_ONE_TRUE_AND_FALSE);
                            createLiteralIssue(trueLiteral,
                                    ISSUE_ONLY_ONE_TRUE_AND_FALSE);
                        }
                    } else {
                        if (falseLiteral == null) {
                            falseLiteral = literal;
                        } else {
                            /* There is a false literal defined already */
                            createLiteralIssue(literal,
                                    ISSUE_ONLY_ONE_TRUE_AND_FALSE);
                            createLiteralIssue(falseLiteral,
                                    ISSUE_ONLY_ONE_TRUE_AND_FALSE);
                        }
                    }
                }
            }
        }
    }

    /**
     * Validate the given {@link EnumerationLiteral}.
     * 
     * @param literal
     */
    private void validateLiteral(EnumerationLiteral literal) {
        DomainValue value = EnumLitValueUtil.getDomainValue(literal);

        if (value instanceof SingleValue) {
            validateSingleLiteral(literal, (SingleValue) value);
        } else if (value instanceof RangeValue) {
            /*
             * TODO: remove the issue and uncomment the validation when range
             * value is supported
             */
            // validateRangeLiteral(literal, (RangeValue) value);
            createLiteralIssue(literal, ISSUE_RANGE_NOT_SUPPORTED);
        }
    }

    /**
     * Validate the Single value of the literal.
     * 
     * @param literal
     * @param value
     */
    private void validateSingleLiteral(EnumerationLiteral literal,
            SingleValue value) {
        PrimitiveType baseType =
                EnumLitValueUtil.getBaseType(literal.getEnumeration());

        if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(baseType.getName())) {
            /*
             * For string type literals the name has to match the value
             * extension attribute
             */
            if (value.getValue() == null
                    || !value.getValue().equals(literal.getName())) {
                createLiteralIssue(literal, ISSUE_INVALID_STRING_LITERAL);
            }
        } else {
            if (value.getValue() == null || value.getValue().isEmpty()) {
                /* Value cannot be empty */
                createLiteralIssue(literal, ISSUE_NO_ENUMLIT_VALUE);
            } else {

                if (!isValueValid(baseType, value.getValue())) {
                    createLiteralIssue(literal,
                            ISSUE_INVALID_VALUE,
                            value.getValue());
                }
                /*
                 * XPD-2511: moved enum lit value specific validations to xsd
                 * scope - see {@EnumLitValueRule} in bom validator plugin
                 */
            }
        }
    }

    /**
     * @param lowerInclusive
     * @param upperInclusive
     * @param lowerLimit
     * @param upperLimit
     * @param enumLitValue
     */
    private boolean isValidDeciBothLowerAndUpperLimits(Object lowerInclusive,
            Object upperInclusive, BigDecimal lowerLimit,
            BigDecimal upperLimit, BigDecimal enumLitValue) {

        if (lowerLimit.compareTo(upperLimit) == 1) {
            return false;
        }
        /*
         * both lower limit inclusive and upper limit inclusive are true
         */
        if (lowerInclusive == Boolean.TRUE && upperInclusive == Boolean.TRUE) {
            if (enumLitValue.compareTo(lowerLimit) == -1
                    || enumLitValue.compareTo(upperLimit) == 1) {
                return false;
            }
            return true;
        }
        /* only lower limit inclusive is true */
        if (lowerInclusive == Boolean.TRUE) {
            if (enumLitValue.compareTo(lowerLimit) == -1
                    || (enumLitValue.compareTo(upperLimit) == 1 || enumLitValue
                            .compareTo(upperLimit) == 0)) {
                return false;
            }
            return true;
        }
        /* only upper limit inclusive is true */
        if (upperInclusive == Boolean.TRUE) {
            if (enumLitValue.compareTo(lowerLimit) == -1
                    || enumLitValue.compareTo(lowerLimit) == 0
                    || enumLitValue.compareTo(upperLimit) == 1) {
                return false;
            }
            return true;
        }
        /*
         * neither lower limit nor upper limit inclusive are set
         */
        if (enumLitValue.compareTo(lowerLimit) == -1
                || enumLitValue.compareTo(lowerLimit) == 0
                || enumLitValue.compareTo(upperLimit) == 1
                || enumLitValue.compareTo(upperLimit) == 0) {
            return false;
        }
        return true;
    }

    /**
     * Validate the Range of the literal.
     * 
     * @param literal
     * @param value
     */
    private void validateRangeLiteral(EnumerationLiteral literal,
            RangeValue value) {
        PrimitiveType baseType =
                EnumLitValueUtil.getBaseType(literal.getEnumeration());
        if (value.getLower() == null || value.getLower().isEmpty()) {
            // Lower value cannot be empty
            createLiteralIssue(literal, ISSUE_NO_LOWER_VALUE);
        } else {
            if (!isValueValid(baseType, value.getLower())) {
                createLiteralIssue(literal,
                        ISSUE_INVALID_LOWER_VALUE,
                        value.getLower());
            }
        }

        if (value.getUpper() == null || value.getUpper().isEmpty()) {
            // Upper value cannot be empty
            createLiteralIssue(literal, ISSUE_NO_UPPER_VALUE);
        } else {
            if (!isValueValid(baseType, value.getUpper())) {
                createLiteralIssue(literal,
                        ISSUE_INVALID_UPPER_VALUE,
                        value.getUpper());
            } else {
                /*
                 * Upper value should be greater than lower value
                 */
                if (value.getLower() != null && !value.getLower().isEmpty()) {
                    if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME
                            .equals(baseType.getName())) {
                        validateDateTimeRange(literal,
                                value.getLower(),
                                value.getUpper());
                    } else if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                            .equals(baseType.getName())) {
                        validateDecimalRange(literal,
                                value.getLower(),
                                value.getUpper());
                    } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                            .equals(baseType.getName())) {
                        validateIntegerRange(literal,
                                value.getLower(),
                                value.getUpper());
                    }
                }
            }
        }
    }

    /**
     * Validate the decimal range.
     * 
     * @param literal
     * @param lower
     * @param upper
     */
    private void validateDecimalRange(EnumerationLiteral literal, String lower,
            String upper) {
        Double lowerDbl = Double.parseDouble(lower);
        Double upperDbl = Double.parseDouble(upper);

        if (upperDbl >= lowerDbl) {
            createLiteralIssue(literal, ISSUE_RANGE_UPPER_GREATER_THAN_LOWER);
        }
    }

    /**
     * Validate the integer range.
     * 
     * @param literal
     * @param lower
     * @param upper
     */
    private void validateIntegerRange(EnumerationLiteral literal, String lower,
            String upper) {
        Integer lowerInt = Integer.parseInt(lower);
        Integer upperInt = Integer.parseInt(upper);

        if (upperInt >= lowerInt) {
            createLiteralIssue(literal, ISSUE_RANGE_UPPER_GREATER_THAN_LOWER);
        }

    }

    /**
     * @param literal
     * @param lower
     * @param upper
     */
    private void validateDateTimeRange(EnumerationLiteral literal,
            String lower, String upper) {

        XMLGregorianCalendar lowerDate = null;
        XMLGregorianCalendar upperDate = null;
        try {
            lowerDate = BOMDateUtil.parse(lower, DateType.DATETIME);
            upperDate = BOMDateUtil.parse(upper, DateType.DATETIME);

            if (!(upperDate.compare(lowerDate) == DatatypeConstants.GREATER)) {
                createLiteralIssue(literal,
                        ISSUE_RANGE_UPPER_GREATER_THAN_LOWER);
            }

        } catch (DatatypeConfigurationException e) {
            if (lowerDate == null) {
                createLiteralIssue(literal, ISSUE_INVALID_LOWER_VALUE, lower);
            } else {
                createLiteralIssue(literal, ISSUE_INVALID_UPPER_VALUE, upper);
            }
        }

    }

    /**
     * Create an issue against the given Enumeration.
     * 
     * @param enumeration
     * @param issue_id
     * @param params
     *            optional params to set for the validation message
     */
    private void createEnumIssue(Enumeration enumeration, String issue_id,
            String... params) {
        createIssue(scope,
                enumeration,
                BOMValidationUtil.getLocation(enumeration),
                issue_id,
                params);
    }

    /**
     * Create an issue against the given Enumeration Literal.
     * 
     * @param literal
     * @param issue_id
     * @param params
     *            optional params to set for the validation message
     */
    private void createLiteralIssue(EnumerationLiteral literal,
            String issue_id, String... params) {
        String location = String.format("%s.%s", //$NON-NLS-1$
                PrimitivesUtil.getDisplayLabel(literal.getEnumeration()),
                PrimitivesUtil.getDisplayLabel(literal));

        createIssue(scope, literal, location, issue_id, params);
    }

}

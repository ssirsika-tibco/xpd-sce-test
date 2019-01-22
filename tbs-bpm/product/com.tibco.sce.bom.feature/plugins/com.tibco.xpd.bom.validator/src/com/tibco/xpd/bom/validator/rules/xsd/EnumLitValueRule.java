/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.resources.ui.internal.calendar.BOMDateUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * 
 * 
 * @author bharge
 * @since 5 Sep 2012
 */
public class EnumLitValueRule extends AbstractXsdRule {

    private IValidationScope scope;

    /**
     * @see com.tibco.xpd.bom.validator.rules.xsd.AbstractXsdRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return Enumeration.class;
    }

    /**
     * @see com.tibco.xpd.bom.validator.rules.xsd.AbstractXsdRule#performXSDValidation(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void performXSDValidation(IValidationScope scope, Object o) {

        if (!enabledInCapabilities()) {
            return;
        }
        this.scope = scope;
        Enumeration enumeration = (Enumeration) o;
        for (EnumerationLiteral literal : enumeration.getOwnedLiterals()) {
            validateLiteral(literal);
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

        String uri =
                literal.getEnumeration().eResource().getURIFragment(literal);
        List<String> messages = new ArrayList<String>();
        messages.add(value.getValue());

        if (null != value.getValue() && !value.getValue().isEmpty()) {
            if (!isValueValid(baseType, value.getValue())) {
                scope.createIssue(XsdIssueIds.ISSUE_INVALID_VALUE,
                        BOMValidationUtil.getLocation(literal),
                        uri,
                        messages);
            } else if (!isValueInLowerAndUpperLimits(literal,
                    baseType,
                    value.getValue())) {
                scope.createIssue(XsdIssueIds.ISSUE_VALUE_NOT_IN_LOWER_AND_UPPER_LIMITS,
                        BOMValidationUtil.getLocation(literal),
                        uri,
                        messages);
            }
            validateValueAgainstLengthAndDecimalPlaces(literal,
                    baseType,
                    value.getValue());
        }
    }

    /**
     * @param literal
     * @param baseType
     * @param value
     */
    private void validateValueAgainstLengthAndDecimalPlaces(
            EnumerationLiteral literal, PrimitiveType baseType, String value) {

        Enumeration tempEnumeration = literal.getEnumeration();

        if (tempEnumeration.getGenerals().size() > 0) {

            Classifier classifier = tempEnumeration.getGenerals().get(0);
            if (classifier instanceof PrimitiveType) {

                PrimitiveType primitiveType = (PrimitiveType) classifier;
                if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME.equals(baseType
                        .getName())) {

                    Integer numLen = null;
                    Integer decPlaces = null;
                    int valueNumLength = 0;
                    int valueDecPlacesLength = 0;

                    /* retrieve number length */
                    Object objNumLen =
                            PrimitivesUtil
                                    .getFacetPropertyValue(primitiveType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);
                    if (objNumLen instanceof Integer) {
                        numLen = (Integer) objNumLen;
                    }
                    /* Retrieve decimal places */
                    Object objDecPlaces =
                            PrimitivesUtil
                                    .getFacetPropertyValue(primitiveType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);
                    if (objDecPlaces instanceof Integer) {
                        decPlaces = (Integer) objDecPlaces;
                    }
                    /* check value for number places */
                    if (null != numLen && numLen > 0 && value.length() > 0) {
                        if (value.contains(".")) { //$NON-NLS-1$
                            String valueNumStr =
                                    value.substring(0, value.indexOf(".")); //$NON-NLS-1$
                            valueNumLength = valueNumStr.length();
                            String valueDecStr =
                                    value.substring(value.indexOf(".") + 1); //$NON-NLS-1$
                            valueDecPlacesLength = valueDecStr.length();
                        }
                    }
                    if (valueNumLength > numLen
                            || valueDecPlacesLength > decPlaces) {
                        List<String> additionalMessages =
                                new ArrayList<String>();
                        additionalMessages.add(value);
                        String uri =
                                tempEnumeration.eResource()
                                        .getURIFragment(literal);
                        scope.createIssue(XsdIssueIds.INVALID_ENUMLIT_VALUE_EXCEEDS_LENGTH_OR_DECIMALPLACES,
                                BOMValidationUtil.getLocation(literal),
                                uri,
                                additionalMessages);
                    }
                    /* check value for decimal places */

                } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                        .equals(baseType.getName())) {

                    Object len =
                            PrimitivesUtil
                                    .getFacetPropertyValue(primitiveType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);
                    if (len instanceof Integer) {

                        if (value.length() > (Integer) len) {
                            List<String> additionalMessages =
                                    new ArrayList<String>();
                            additionalMessages.add(value);
                            String uri =
                                    tempEnumeration.eResource()
                                            .getURIFragment(literal);
                            scope.createIssue(XsdIssueIds.INVALID_ENUMLIT_VALUE_EXCEEDS_LENGTH_OR_DECIMALPLACES,
                                    BOMValidationUtil.getLocation(literal),
                                    uri,
                                    additionalMessages);
                        }
                    }
                }
            }
        }

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
        // Make sure there is actually a type set when we are called
        if (type == null) {
            return false;
        }

        try {
            if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                    .equals(type.getName())) {
                Double.parseDouble(value);
            } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME.equals(type
                    .getName())) {
                Integer.parseInt(value);
            } else {
                // Check for dates
                DateType dateType = getDateType(type);
                if (dateType != null) {
                    // Check if the date string is valid
                    try {
                        BOMDateUtil.parse(value, dateType);
                    } catch (DatatypeConfigurationException e) {
                        // Do nothing
                    } catch (IllegalArgumentException e) {
                        // Invalid date string
                        return false;
                    }
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Get the calendar date type based on the primitive type.
     * 
     * @param baseType
     * @return DateType, <code>null</code> if this base type is not a date
     *         variant.
     */
    private DateType getDateType(PrimitiveType baseType) {
        DateType type = null;

        if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME.equals(baseType
                .getName())) {
            type = DateType.DATETIME;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME.equals(baseType
                .getName())) {
            type = DateType.DATETIMETZ;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(baseType
                .getName())) {
            type = DateType.DATE;
        } else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(baseType
                .getName())) {
            type = DateType.TIME;
        }

        return type;
    }

    /**
     * @param literal
     * @param baseType
     * @param value
     * @return
     */
    private boolean isValueInLowerAndUpperLimits(EnumerationLiteral literal,
            PrimitiveType baseType, String value) {

        Enumeration tempEnumeration = literal.getEnumeration();

        if (tempEnumeration.getGenerals().size() > 0) {

            Classifier classifier = tempEnumeration.getGenerals().get(0);
            if (classifier instanceof PrimitiveType) {

                PrimitiveType primitiveType = (PrimitiveType) classifier;
                try {

                    if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                            .equals(baseType.getName())) {

                        return isDecimalInLowerAndUpperLimits(tempEnumeration,
                                value,
                                primitiveType);
                    } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                            .equals(baseType.getName())) {

                        return isIntegerInLowerAndUpperLimits(tempEnumeration,
                                value,
                                primitiveType);
                    }
                } catch (NumberFormatException e) {
                    return false;
                }

            }
        }

        return true;
    }

    /**
     * @param tempEnumeration
     * @param value
     * @param primitiveType
     */
    private boolean isDecimalInLowerAndUpperLimits(Enumeration tempEnumeration,
            String value, PrimitiveType primitiveType) {

        Object lower =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER);
        Object upper =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER);
        Object lowerInclusive =
                PrimitivesUtil
                        .getFacetPropertyValue(primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE);
        Object upperInclusive =
                PrimitivesUtil
                        .getFacetPropertyValue(primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE);
        BigDecimal lowerLimit = null;
        BigDecimal upperLimit = null;

        if (null != lower) {
            lowerLimit = new BigDecimal((String) lower);
        }
        if (null != upper) {
            upperLimit = new BigDecimal((String) upper);
        }
        BigDecimal enumLitValue = new BigDecimal(value);

        /* both lower limit and upper limit are specified */
        if (null != lowerLimit && null != upperLimit) {

            if (lowerLimit.compareTo(upperLimit) == 1) {

                List<String> additionalMessages = new ArrayList<String>();
                additionalMessages.add(lowerLimit.toString());
                additionalMessages.add(upperLimit.toString());
                String uri =
                        tempEnumeration.eResource()
                                .getURIFragment(tempEnumeration);
                scope.createIssue(XsdIssueIds.INVALID_ENUMLIT_LOWERLIMIT_GREATER_THAN_UPPERLIMIT,
                        BOMValidationUtil.getLocation(tempEnumeration),
                        uri,
                        additionalMessages);
            }

            return isValidDeciBothLowerAndUpperLimits(lowerInclusive,
                    upperInclusive,
                    lowerLimit,
                    upperLimit,
                    enumLitValue);
        }
        /* only lower limit specified */
        if (null != lowerLimit) {
            /* lower limit inclusive is true */
            if (lowerInclusive == Boolean.TRUE) {
                if (enumLitValue.compareTo(lowerLimit) == -1) {
                    return false;
                }
                return true;
            }
            /* lower limit inclusive is not set or is false */
            if (enumLitValue.compareTo(lowerLimit) == -1
                    || enumLitValue.compareTo(lowerLimit) == 0) {
                return false;
            }
        }

        /* only upper limit specified */
        if (null != upperLimit) {
            /* upper limit inclusive is true */
            if (upperInclusive == Boolean.TRUE) {
                if (enumLitValue.compareTo(upperLimit) == 1) {
                    return false;
                }
                return true;
            }
            /* upper limit inclusive is not set or is false */
            if (enumLitValue.compareTo(upperLimit) == 1
                    || enumLitValue.compareTo(upperLimit) == 0) {
                return false;
            }
        }
        return true;
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
     * @param tempEnumeration
     * @param value
     * @param primitiveType
     */
    private boolean isIntegerInLowerAndUpperLimits(Enumeration tempEnumeration,
            String value, PrimitiveType primitiveType) {

        Object lower =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER);
        Object upper =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);

        BigInteger lowerLimit = null;
        BigInteger upperLimit = null;

        if (null != lower) {
            lowerLimit = new BigInteger((String) lower);
        }
        if (null != upper) {
            upperLimit = new BigInteger((String) upper);
        }
        BigInteger enumLitValue = new BigInteger(value);
        /* both lower limit and upper limit are specified */
        if (null != upperLimit && null != lowerLimit) {

            if (lowerLimit.compareTo(upperLimit) == 1) {

                List<String> additionalMessages = new ArrayList<String>();
                additionalMessages.add(lowerLimit.toString());
                additionalMessages.add(upperLimit.toString());
                String uri =
                        tempEnumeration.eResource()
                                .getURIFragment(tempEnumeration);
                scope.createIssue(XsdIssueIds.INVALID_ENUMLIT_LOWERLIMIT_GREATER_THAN_UPPERLIMIT,
                        BOMValidationUtil.getLocation(tempEnumeration),
                        uri,
                        additionalMessages);
            }

            if (enumLitValue.compareTo(lowerLimit) == -1
                    || enumLitValue.compareTo(upperLimit) == 1) {
                return false;
            }
        }
        /* only upper limit specified */
        if (null != upperLimit) {
            if (enumLitValue.compareTo(upperLimit) == 1) {
                return false;
            }
        }
        /* only lower limit specified */
        if (null != lowerLimit) {
            if (enumLitValue.compareTo(lowerLimit) == -1) {
                return false;
            }
        }
        return true;
    }

}

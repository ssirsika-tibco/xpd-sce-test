/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bpm.da.dm.api.Constraint;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Transforms BOM model constraints to Case Data Model (CDM) constraints.
 *
 * @author jarciuch
 * @since 3 Apr 2019
 */
public class BomConstraintTransformer {

    /** Constant for String value of true used in constraint values. */
    private static final String TRUE_STRING = "true"; //$NON-NLS-1$

    /**
     * Boolean constant to label option to fallback to the base type for the
     * constraint value.
     */
    private static final boolean FALLBACK_TO_BASE_TYPE = true; // $NON-NLS-1$

    /**
     * Represents a constraint in CDM.
     */
    public static class NameValuePair {
        private final String name, value;

        public NameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Returns all applicable CDM constraints for the BOM property in the for of
     * {@link NameValuePair}.
     * 
     * @param bomProperty
     *            the context property.
     * @return all applicable CDM constraints for the BOM property.
     */
    public Collection<NameValuePair> getContraints(Property bomProperty) {
        Type bomType = bomProperty.getType();
        if (bomType instanceof PrimitiveType) {
            String baseType = PrimitivesUtil
                    .getBasePrimitiveType((PrimitiveType) bomType).getName();
            switch (baseType) {
            case PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME:
            case PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME:
                return getTextConstraints(bomProperty);
            case PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME:
                return getIntegerConstraints(bomProperty);
            case PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME:
                return getDecimalConstraints(bomProperty);
            }
            // All other primitive types have no constraints.
        }
        return Collections.emptyList();
    }

    /**
     * Returns a string representation of a default value for a BOM property or
     * 'null' if value is not set.
     * 
     * @param bomProperty
     *            the BOM property.
     * @return A string representation of a default value for a BOM property or
     *         null.
     */
    public String getDefaultValue(Property bomProperty) {
        Type bomType = bomProperty.getType();

        Function<String, String> getFacetValue = facet -> {
            Object defaultValue = PrimitivesUtil.getFacetPropertyValue(
                    (PrimitiveType) bomType,
                    facet,
                    bomProperty,
                    FALLBACK_TO_BASE_TYPE);
            if (defaultValue instanceof String) {
                String strVal = (String) defaultValue;
                // Empty string is treated as "not-set".
                return !strVal.isEmpty() ? strVal : null;
            }
            return defaultValue != null ? defaultValue.toString() : null;
        };

        if (bomType instanceof PrimitiveType) {
            String baseType = PrimitivesUtil
                    .getBasePrimitiveType((PrimitiveType) bomType).getName();
            switch (baseType) {
            case PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME:
            case PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME:
                return getFacetValue.apply(
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE);
            case PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME:
                return getFacetValue.apply(
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE);
            case PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME:
                return getFacetValue.apply(
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE);
            case PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME:
                return getFacetValue.apply(
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE);
            case PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME:
                // "YYYY-MM-DD" for example "2019-04-28"
                return getFacetValue.apply(
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_DEFAULT_VALUE);
            case PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME:
                // BOM time is: "hh:mm:ss" for example "16:13:00" -> CDM time is
                // "hh:mm" for example "16:13"
                return transformToCdmTime(getFacetValue.apply(
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TIME_DEFAULT_VALUE));
            case PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME:
                // for example: "2019-04-06T14:58:00.000+01:00"
                return getFacetValue.apply(
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DATE_TIME_TZ_DEFAULT_VALUE);
            case PrimitivesUtil.BOM_PRIMITIVE_URI_NAME:
                // for example: "http://google.com"
                return getFacetValue.apply(
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_URI_DEFAULT_VALUE);
            }
        }
        return null;
    }

    /**
     * Gets a collection enumeration literals if a property is of Enumeration
     * type.
     * 
     * @param bomAttribute
     *            the bom attribute.
     * @return a collection enumeration literals or an empty list.
     */
    public Collection<EnumerationLiteral> getAllowedValues(
            Property bomProperty) {
        Type bomType = bomProperty.getType();
        if (bomType instanceof Enumeration) {
            return ((Enumeration) bomType).getOwnedLiterals(); // $NON-NLS-1$
        }
        return Collections.emptyList();
    }

    /**
     * Transforms BOM time is: "hh:mm:ss" for example "16:13:00" to CDM time is
     * "hh:mm" for example "16:13"
     * 
     * @param bomTime
     *            the string containing bomTime in "hh:mm:ss" format or null.
     * @return CDM string representation of time value or null;
     */
    private String transformToCdmTime(String bomTime) {
        if (bomTime instanceof String) {
            int lastColon = bomTime.lastIndexOf(':');
            if (lastColon != -1) {
                return bomTime.substring(0, lastColon);
            }
            return bomTime;
        }
        return null;
    }

    /**
     * Returns list of CDM constraints for the property of a Text primitive
     * type.
     * 
     * @param bomProperty
     *            the BOM property of a Text type.
     * @return list of CDM constraints for the property of a Text primitive
     *         type.
     */
    private Collection<NameValuePair> getTextConstraints(Property bomProperty) {
        assert (bomProperty.getType() instanceof PrimitiveType);

        PrimitiveType bomPrimitiveType =
                ((PrimitiveType) bomProperty.getType());

        List<NameValuePair> constraints = new ArrayList<>();
        Object length = PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                bomProperty,
                FALLBACK_TO_BASE_TYPE);
        // -1 means unbounded (not-set in UI).
        if (length instanceof Integer && ((Integer) length).intValue() != -1) {
            constraints.add(new NameValuePair(Constraint.NAME_LENGTH,
                    length.toString()));
        }

        /*
         * ACE-738: The pattern support was dropped from V1. According to:
         * https://confluence.tibco.com/display/BPM/AMX-BPM%3A+Container+Edition
         * +FP+-+Supported+data+types
         * "Pattern (REGEX) support is a future feature for ACE"
         * 
         * This would currently cause failures in CDM validation
         * "Unknown constraint "pattern", therefore disabling for now until it
         * is supported.
         */
        // Object pattern =
        // PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
        // PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE,
        // bomProperty,
        // FALLBACK_TO_BASE_TYPE);
        // // Empty text is consider as not-set.
        // if (pattern instanceof String && !((String) pattern).isEmpty()) {
        // constraints
        // .add(new NameValuePair(NAME_PATTERN, pattern.toString()));
        // }
        return constraints;
    }

    /**
     * Returns list of CDM constraints for the property of an Integer primitive
     * type. Integer is treated as a "base:FixedPointNumber" with 0 decimal
     * places.
     * 
     * @param bomProperty
     *            the BOM property of an Integer type.
     * @return list of CDM constraints for the property of an Integer primitive
     *         type.
     */
    private Collection<NameValuePair> getIntegerConstraints(
            Property bomProperty) {
        assert (bomProperty.getType() instanceof PrimitiveType);
        PrimitiveType bomPrimitiveType =
                ((PrimitiveType) bomProperty.getType());
        List<NameValuePair> constraints = new ArrayList<>();

        Object length = PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                bomProperty,
                FALLBACK_TO_BASE_TYPE);
        if (length instanceof Integer) {
            constraints.add(new NameValuePair(Constraint.NAME_LENGTH,
                    length.toString()));
        }

        // If minValue or maxValue are null that means the value was not-set.
        // For the integer lower and upper values are always inclusive (if set).

        Object minValue = PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                bomProperty,
                FALLBACK_TO_BASE_TYPE);
        if (minValue instanceof String) {
            constraints
                    .add(new NameValuePair(Constraint.NAME_MIN_VALUE,
                            (String) minValue));
            constraints.add(
                    new NameValuePair(Constraint.NAME_MIN_VALUE_INCLUSIVE,
                            TRUE_STRING));
        }

        Object maxValue = PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                bomProperty,
                FALLBACK_TO_BASE_TYPE);
        if (maxValue instanceof String) {
            constraints
                    .add(new NameValuePair(Constraint.NAME_MAX_VALUE,
                            (String) maxValue));
            constraints.add(
                    new NameValuePair(Constraint.NAME_MAX_VALUE_INCLUSIVE,
                            TRUE_STRING));
        }

        constraints.add(new NameValuePair(Constraint.NAME_DECIMAL_PLACES, "0")); //$NON-NLS-1$
        return constraints;
    }

    /**
     * Returns list of CDM constraints for the property of a Decimal primitive
     * type.
     * 
     * @param bomProperty
     *            the BOM property of an Decimal type.
     * @return list of CDM constraints for the property of a Decimal primitive
     *         type.
     */
    private Collection<NameValuePair> getDecimalConstraints(
            Property bomProperty) {
        assert (bomProperty.getType() instanceof PrimitiveType);
        PrimitiveType bomPrimitiveType =
                ((PrimitiveType) bomProperty.getType());
        List<NameValuePair> constraints = new ArrayList<>();

        /*
         * Sid ACE-1079: Do not add length or decimal places for Floating Point
         * Decimals (length property at least defaults to 10 but CDM says you
         * should not have a length spec'd for Floating point).
         */

        if (BomTransformer.isFixedPointDecimal(bomProperty)) {

            Object length =
                    PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                            bomProperty,
                            FALLBACK_TO_BASE_TYPE);
            if (length instanceof Integer) {
                constraints
                        .add(new NameValuePair(Constraint.NAME_LENGTH,
                                length.toString()));
            }

            Object decimalPlaces =
                    PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                            bomProperty,
                            FALLBACK_TO_BASE_TYPE);
            if (decimalPlaces instanceof Integer) {
                constraints
                        .add(new NameValuePair(Constraint.NAME_DECIMAL_PLACES,
                        decimalPlaces.toString()));
            }
        }

        // If upper or lower are null that means the value was not-set.
        // For the Decimal lower or upper exclusive are only set if upper or
        // lower are also set.

        Object lower = PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                bomProperty,
                FALLBACK_TO_BASE_TYPE);
        if (lower instanceof String) {
            constraints.add(new NameValuePair(Constraint.NAME_MIN_VALUE,
                    (String) lower));
            Object lowerInclusive = PrimitivesUtil.getFacetPropertyValue(
                    bomPrimitiveType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE,
                    bomProperty,
                    FALLBACK_TO_BASE_TYPE);
            if (lowerInclusive instanceof Boolean) {
                constraints.add(
                        new NameValuePair(Constraint.NAME_MIN_VALUE_INCLUSIVE,
                        lowerInclusive.toString()));
            }
        }

        Object upper = PrimitivesUtil.getFacetPropertyValue(bomPrimitiveType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                bomProperty,
                FALLBACK_TO_BASE_TYPE);
        if (upper instanceof String) {
            constraints.add(new NameValuePair(Constraint.NAME_MAX_VALUE,
                    (String) upper));
            Object upperInclusive = PrimitivesUtil.getFacetPropertyValue(
                    bomPrimitiveType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE,
                    bomProperty,
                    FALLBACK_TO_BASE_TYPE);
            if (upperInclusive instanceof Boolean) {
                constraints.add(
                        new NameValuePair(Constraint.NAME_MAX_VALUE_INCLUSIVE,
                        upperInclusive.toString()));
            }
        }

        return constraints;
    }

}

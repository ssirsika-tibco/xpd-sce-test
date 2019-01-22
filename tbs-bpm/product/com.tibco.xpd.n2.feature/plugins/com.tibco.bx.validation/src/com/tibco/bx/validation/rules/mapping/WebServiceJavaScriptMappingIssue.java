/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

/**
 * Issues that may be found by {@link JSTypeComparisonHelper}.
 * 
 * @author rsomayaj
 */
public enum WebServiceJavaScriptMappingIssue {
    TYPES_DONT_MATCH("bx.typesDontMatch"), //$NON-NLS-1$

    TEXT_TO_URIANDID("bx.txtToUriAndId.warning"), //$NON-NLS-1$

    LENGTH_TRUNCATED("bx.lengthTruncation.warning"), //$NON-NLS-1$

    PRECISION_LOSS("bx.precisionLoss.warning"), //$NON-NLS-1$

    TEXT_NUMERIC("bx.textToNumeric.warning"), //$NON-NLS-1$

    DATETYPES_TO_TEXT("bx.dateTypesToText.warning"), //$NON-NLS-1$

    DECIMAL_TO_TEXT("bx.decimalToString.warning"), //$NON-NLS-1$

    DATETIMETZ_TO_DATETYPES("bx.dateTimeTZToDateTypes.warning"), //$NON-NLS-1$

    DATETYPES_TO_DATETIMETZ("bx.dateTypesToDateTimeTZ.warning"), //$NON-NLS-1$

    DTORDTTZ_TO_DATE("bx.dtOrdttzToDate.warning"), //$NON-NLS-1$

    NUMERIC_TO_BOOLEAN("bx.integerToBoolean.warning"), //$NON-NLS-1$

    INVALID_TYPE("bx.invalidMappingParamType"), //$NON-NLS-1$

    DECIMAL_TO_INTEGER("bx.decimalToInteger.warning"), //$NON-NLS-1$

    TEXT_BOOLEAN("bx.textBoolean.warning"), //$NON-NLS-1$

    TEXT_TO_ENUM("bx.textToEnum.warning"), //$NON-NLS-1$

    /*
     * SID XPD-914 - issues tagged as deprecated can be removed when deprecated
     * rule classes that reference thewm have been removed.
     */
    ATTRIBUTE_REQUIRES_MAPPING("deprecated.bx.requiredAttributeNotMapped"), //$NON-NLS-1$

    PARAMETER_REQUIRES_MAPPING("deprecated.bx.requiredParameterNotMapped"), //$NON-NLS-1$

    OBJECT_REQUIRES_MAPPING("deprecated.bx.requiredObjectNotMapped"), //$NON-NLS-1$

    ELEMENT_REQUIRES_MAPPING("deprecated.bx.elementRequiresMapping"), //$NON-NLS-1$

    ARRAY_ARRAY_CHILDREN_MAPPING("deprecated.bx.jsArrayChildrenMapping"), //$NON-NLS-1$

    PATTERN_DOESNT_MATCH("bx.patternDoesntMatch"), //$NON-NLS-1$

    SIMPLE_CONTENT_VALUE_NEEDS_MAPPING(
            "deprecated.bx.restrictiveValueElNotMapped"), //$NON-NLS-1$

    /**
     * This is an error level marker to say
     * "Concatenation mappings not supported"
     */
    CONCATENATION_NOT_SUPPORTED("deprecated.bx.concatenationNotSupported"), //$NON-NLS-1$

    /**
     * This is an error level marker to say "Cannot map element when whole
     * ancestor has already been mapped
     */
    CHILD_SHOULDNT_BE_MAPPED("deprecated.bx.childElementShouldntBeMapped"), //$NON-NLS-1$

    SUPER_TYPE_SUB_TYPE_MAPPING("bx.superTypeToSubType.warning"), //$NON-NLS-1$

    /** Mapping from 'xsd any' typed message part is not supported */
    XSD_ANY_MAPPING_FROM_INVALID("bx.mappingFromXsdAnyInvalid"), //$NON-NLS-1$

    /** Mapping to 'xsd any' typed message part is not supported */
    XSD_ANY_MAPPING_TO_INVALID("bx.mappingToXsdAnyInvalid"), //$NON-NLS-1$

    XSD_SEQUENCE_MULTIPLICITY_ORDER(
            "bx.mappingPropertyInXSDMultiSequence.warning"); //$NON-NLS-1$
    ;

    private final String warning;

    private WebServiceJavaScriptMappingIssue(String warning) {
        this.warning = warning;
    }

    public String getValue() {
        return warning;
    }
}

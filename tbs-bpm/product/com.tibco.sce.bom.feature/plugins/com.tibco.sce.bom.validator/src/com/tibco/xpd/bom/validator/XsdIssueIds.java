/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.validator;

/**
 * Interface to centralise Issue Ids
 * 
 * @author glewis
 * 
 */
public final class XsdIssueIds {
    public static final String DUPLICATE_ATTRIBUTE_IDS =
            "xsd.duplicate.attribute.ids.issue"; //$NON-NLS-1$

    public static final String INVALID_ENUMLIT_FOR_PATTERN =
            "xsd.invalid.enumlit.for.pattern.issue"; //$NON-NLS-1$

    public static final String INVALID_ENUMLIT_FOR_LENGTH =
            "xsd.invalid.enumlit.for.length.issue"; //$NON-NLS-1$

    public static final String CHILD_ENUMLITS_NOT_IN_PARENT =
            "xsd.not.contain.parent.enumlits.issue"; //$NON-NLS-1$

    public static final String CHILD_ENUMLIT_VALUE_DOES_NOT_MATCH_PARENT =
            "xsd.not.contain.parent.enumlits.value.issue"; //$NON-NLS-1$

    public static final String ISSUE_INVALID_VALUE = "xsd.invalidEnumLitValue"; //$NON-NLS-1$

    public static final String ISSUE_VALUE_NOT_IN_LOWER_AND_UPPER_LIMITS =
            "xsd.enumLitValueDoesNotFitInLowerAndUpperLimits"; //$NON-NLS-1$

    public static final String INVALID_ENUMLIT_VALUE_EXCEEDS_LENGTH_OR_DECIMALPLACES =
            "xsd.lowerOrUpperLimitExceedsLengthIssue"; //$NON-NLS-1$

    public static final String INVALID_ENUMLIT_LOWERLIMIT_GREATER_THAN_UPPERLIMIT =
            "xsd.lowerLimitGreaterThanUpperLimitIssue"; //$NON-NLS-1$

    public static final String GENERALISED_CLASS_CONTAINS_DUPLICATE_ATTRIBUTE =
            "xsd.generalised.class.contains.duplicate.attribute.issue"; //$NON-NLS-1$

    public static final String PRIMITIVETYPE_DEFAULT_VALUE_LOST =
            "xsd.primitivetype.default.lost.issue"; //$NON-NLS-1$

    public static final String PRIMITIVETYPE_OBJECT_TYPE =
            "xsd.primitivetype.objecttype.issue"; //$NON-NLS-1$

    public static final String CLASSIFIER_GENERALIZATION_OF_FINAL_TYPE =
            "xsd.classifier.generalizesfinaltype.issue"; //$NON-NLS-1$

    public static final String PROPERTY_DUPLICATE_OBJECT_TYPE =
            "xsd.property.duplicateobjecttype.issue"; //$NON-NLS-1$

    public static final String PROPERTY_NO_TYPE_SET =
            "xsd.property.notypeset.issue"; //$NON-NLS-1$

    public static final String EXTERNAL_REF_NOT_ALLOWED =
            "xsd.external.ref.not.allowed.issue"; //$NON-NLS-1$

    public static final String PRIMITIVE_TYPE_DEFAULT =
            "xsd.primitive.type.default.issue"; //$NON-NLS-1$

    public static final String OPERATION = "xsd.operation.issue"; //$NON-NLS-1$

    public static final String UNSUPPORTED_RELATIONSHIP =
            "xsd.unsupported.relationship.issue"; //$NON-NLS-1$

    public static final String INVALID_PACKAGE_NAME =
            "xsd.invalid.package.name.issue"; //$NON-NLS-1$

    public static final String ASSOCIATION_CLASS_UNSUPPORTED =
            "xsd.unsupported.association.class.issue"; //$NON-NLS-1$

    public static final String XSD_CYCLIC_DEPENDENCY =
            "xsd.xsdCyclicDependency.issue"; //$NON-NLS-1$

    public static final String PROPERTY_OBJECT_TYPE_NOT_LAST =
            "xsd.primitivetype.objectposition.issue"; //$NON-NLS-1$    

    public static final String PROPERTY_MULTIPLICITY_PRE_OBJECT_TYPE =
            "xsd.primitivetype.objectpreceedingminmax.issue"; //$NON-NLS-1$

    public static final String PROPERTY_MULTIPLICITY_OBJECT_TYPE_GENERALIZATION =
            "xsd.primitivetype.objectextendedclass.issue"; //$NON-NLS-1$

    public static final String PROPERTY_MULTIPLICITY_POST_OBJECT_TYPE =
            "xsd.primitivetype.objectfollowingminmax.issue"; //$NON-NLS-1$

    public static final String PACKAGE_DUPLICATE_NAME =
            "xsd.invalid.package.nameclash.issue"; //$NON-NLS-1$

    public static final String PRIMITIVETYPE_RESTRICTIONS =
            "xsd.primitivetyperestrictions.issue"; //$NON-NLS-1$

    public static final String PROPERTY_CONSTRAINTS_SUBCLASS_CONTAINS_TYPE_ANY =
            "xsd.propertyrestrictionsrule.generalfirstproperty.issue"; //$NON-NLS-1$

    public static final String CLASS_GENERALIZATION_OF_ANON_TYPE_TYPE =
            "xsd.class.generalizesanontype.issue"; //$NON-NLS-1$

}

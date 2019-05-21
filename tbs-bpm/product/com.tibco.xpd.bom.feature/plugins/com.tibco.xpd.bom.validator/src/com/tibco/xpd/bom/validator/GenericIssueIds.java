/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator;

/**
 * Interface to centralize Issue Ids
 * 
 * @author rsomayaj
 * 
 */
public final class GenericIssueIds {

    public static final String CLASSIFIER_DUPLICATENAME =
            "classifierDuplicateName.issue"; //$NON-NLS-1$

    public static final String ELEMENT_NO_NAME = "elementNoName.issue"; //$NON-NLS-1$

    public static final String INVALID_MULTIPLICITY =
            "invalidMultiplicity.issue"; //$NON-NLS-1$

    public static final String INVALID_MULTIPLICITY_ASSOCIATION =
            "invalidMultiplicityOnReleationship.issue"; //$NON-NLS-1$

    public static final String TYPE_SINGLE_INHERITANCE =
            "singleInheritance.issue"; //$NON-NLS-1$

    public static final String TYPE_INHERITANCE_LOOP = "inheritanceLoop.issue"; //$NON-NLS-1$

    public static final String PROPERTY_NAME_DUPLICATE =
            "propertyNameDuplicate.issue"; //$NON-NLS-1$

    public static final String PACKAGE_DUPLICATENAME =
            "packageDuplicateName.issue"; //$NON-NLS-1$

    public static final String BROKEN_REFERENCE = "brokenReference.issue"; //$NON-NLS-1$

    public static final String BROKEN_REFERENCE_PROFILE =
            "brokenProfileReference.issue"; //$NON-NLS-1$

    public static final String OPERATION_DUPLICATE_PARAMETERS =
            "operationDuplicateParameters.issue"; //$NON-NLS-1$

    public static final String ENUMLIT_NAME_DUPLICATE =
            "enumLitNameDuplicate.issue"; //$NON-NLS-1$

    public static final String CYCLIC_DEPENDENCY = "cyclicDependency.issue"; //$NON-NLS-1$

    public static final String MAXTEXTLENGTH_ZERO = "textLengthZero.issue"; //$NON-NLS-1$

    public static final String PRIMITIVE_TYPE_RESTRICTION_BROKEN =
            "primitiveTypeRestrictionMissmatch.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_TYPE_NOT_SET =
            "attributeTypeNotSet.issue"; //$NON-NLS-1$
}

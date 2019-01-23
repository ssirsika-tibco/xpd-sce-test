/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.internal;

/**
 * Keys used in additionalInfo map of markers. Referred to in certain
 * resolutions.
 * 
 * @author patkinso
 */
public interface IAdditionalInfoMarkerKeys {

    /**
     * References class stereotype that can be added to a class (class mutation)
     */
    public static final String ADDITIONAL_STEREOTYPE_KIND_NAME =
            "viable.mutation.stereotype.kind"; //$NON-NLS-1$

    /**
     * References a resource location in which the class of an extraneous part
     * of a (inter-BOM) relationship can be found
     */
    public static final String TARGETS_RESOURCE_URI_LOCATION =
            "bom.relationship.extraneous.resource.location"; //$NON-NLS-1$

    /**
     * References an element location in which the element is on the extraneous
     * side of a (inter-BOM) relationship
     */
    public static final String TARGETS_FRAGMENT_URI_LOCATION =
            "bom.relationship.extraneous.fragment.location"; //$NON-NLS-1$

    /**
     * References an element location in which the element is not on the
     * extraneous side of a (inter-BOM) relationship
     */
    public static final String SOURCE_FRAGMENT_URI_LOCATION =
            "bom.relationship.intrinsic.fragment.location"; //$NON-NLS-1$

    /**
     * References a relationship location
     */
    public static final String RELATIONSHIP_LOCATION =
            "bom.relationship.issue.cause"; //$NON-NLS-1$

    public static final String RELATIONSHIP_NAME = "bom.relationship.name"; //$NON-NLS-1$

}

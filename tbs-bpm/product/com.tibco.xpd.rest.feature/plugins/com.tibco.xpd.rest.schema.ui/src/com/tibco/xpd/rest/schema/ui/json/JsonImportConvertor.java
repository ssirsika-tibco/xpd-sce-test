/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.json;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Package;

/**
 * Converts schema import data to UML content. Implementations will be provided
 * to support import from different resource types.
 * 
 * @author nwilson
 * @since 13 Jan 2015
 */
public interface JsonImportConvertor {

    /**
     * Root class identifier.
     */
    public static final String ROOT = "root"; //$NON-NLS-1$

    /**
     * Converts the import data writing the results to the given resource set
     * and UML package.
     * 
     * @param rs
     *            The target EMF resource set.
     * @param root
     *            The target UML Package element.
     */
    void convert(ResourceSet rs, Package root);

}

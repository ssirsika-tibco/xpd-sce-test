/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;

/**
 * JSON Schema resource factory implementation. This overrides the default UML
 * resource factory implementation in order to turn on modification tracking
 * which is necessary for keeping the working copy dirty flag updated.
 * 
 * @author nwilson
 * @since 27 Jan 2015
 */
@SuppressWarnings("restriction")
public class JsonSchemaResourceFactoryImpl extends UMLResourceFactoryImpl {

    /**
     * @see org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl
     *      #createResourceGen(org.eclipse.emf.common.util.URI)
     * 
     * @param uri
     *            The resource URI.
     * @return The resource.
     */
    @Override
    public Resource createResourceGen(URI uri) {
        Resource resource = super.createResourceGen(uri);
        resource.setTrackingModification(true);
        return resource;
    }

    /**
     * @see org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl
     *      #createResource(org.eclipse.emf.common.util.URI)
     * 
     * @param uri
     *            The resource URI.
     * @return The resource.
     */
    @Override
    public Resource createResource(URI uri) {
        Resource resource = super.createResource(uri);
        resource.setTrackingModification(true);
        return resource;
    }

}

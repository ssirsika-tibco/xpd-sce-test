/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * EMF resource factory for ChannelTypes resource.
 * <p>
 * <i>Created: 11 Mar 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ChannelTypesResourceFactoryImpl extends ResourceFactoryImpl {

    /**
     * Creates an instance of the resource factory.
     */
    public ChannelTypesResourceFactoryImpl() {
        super();
    }

    /**
     * Creates an instance of the resource.
     */
    @Override
    public Resource createResource(URI uri) {
        Resource result = new ChannelTypesResourceImpl(uri);
        return result;
    }

}

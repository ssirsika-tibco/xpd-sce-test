/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;

import com.tibco.xpd.deploy.Server;

/**
 * Adapter factory for server IAdaptable objects which adapts to Server.
 * <p>
 * <i>Created: 28 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeploymentServersAdapterFactory implements IAdapterFactory {

    private static final Class<?>[] SUPPORTED_TYPES = new Class<?>[] { Server.class };

    @SuppressWarnings("unchecked")
    public Class[] getAdapterList() {
        return SUPPORTED_TYPES;
    }

    @SuppressWarnings("unchecked")
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (Server.class.equals(adapterType)) {
            return ((IAdaptable) adaptableObject).getAdapter(adapterType);
        }
        return null;
    }
}

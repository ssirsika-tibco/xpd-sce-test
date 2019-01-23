/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.ui.views.properties.IPropertySource;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.ui.DeployUIActivator;

/**
 * Adapts deployment model objects to IPropertySource classes to be able to
 * display properties on eclipse properties sheet.
 * <p>
 * <i>Created: 28 August 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployPropertySourceAdapterFactory implements IAdapterFactory {

    private static Class<?>[] SUPPORTED_TYPES = { IPropertySource.class };

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (!(adaptableObject instanceof EObject && ((EObject) adaptableObject)
                .eClass().getEPackage().equals(DeployPackage.eINSTANCE))) {
            return null;
        }
        if (IPropertySource.class.equals(adapterType)) {
            AdapterFactory adapterFactory = DeployUIActivator
                    .getServerManager().getAdapterFactory();
            AdapterFactoryContentProvider provider = new AdapterFactoryContentProvider(
                    adapterFactory);
            return provider.getPropertySource(adaptableObject);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    @SuppressWarnings("unchecked")
    public Class[] getAdapterList() {
        return SUPPORTED_TYPES;
    }

}

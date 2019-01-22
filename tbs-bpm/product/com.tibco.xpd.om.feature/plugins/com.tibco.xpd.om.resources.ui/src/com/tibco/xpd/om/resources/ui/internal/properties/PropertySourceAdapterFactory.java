/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.gmf.runtime.emf.ui.properties.descriptors.EMFCompositePropertySource;
import org.eclipse.ui.views.properties.IPropertySource;

import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Adapts EMF models' objects to IPropertySource classes to be able to display
 * their properties on the standard properties sheet.
 * <p>
 * <i>Created: 28 August 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class PropertySourceAdapterFactory implements IAdapterFactory {

    private static Class<?>[] SUPPORTED_TYPES = { IPropertySource.class };

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     * java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (!(adaptableObject instanceof EObject)) {
            return null;
        }
        if (IPropertySource.class.equals(adapterType)) {
            AdapterFactory adapterFactory =
                    XpdResourcesPlugin.getDefault().getAdapterFactory();
            IItemPropertySource itemPropertySource =
                    (IItemPropertySource) adapterFactory.adapt(adaptableObject,
                            IItemPropertySource.class);
            return new EMFCompositePropertySource(adaptableObject,
                    itemPropertySource,
                    Messages.PropertySourceAdapterFactory_PropertySheetTitle);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    @Override
    @SuppressWarnings("unchecked")
    public Class[] getAdapterList() {
        return SUPPORTED_TYPES;
    }

}

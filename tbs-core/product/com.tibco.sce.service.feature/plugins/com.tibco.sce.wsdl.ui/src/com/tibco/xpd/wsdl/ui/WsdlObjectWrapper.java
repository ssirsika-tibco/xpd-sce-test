/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertySource;

import com.tibco.xpd.wsdl.ui.properites.WsdlWrapperPropertySource;

/**
 * Base class for WSDL object wrappers.
 * <p>
 * <i>Created: 20 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public abstract class WsdlObjectWrapper implements IWsdlObjectWrapper {

    private final Object object;
    private final Object parent;
    private WsdlWrapperPropertySource propertySource;

    protected WsdlObjectWrapper(Object parent, Object object) {
        this.parent = parent;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public Object getParent() {
        return parent;
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.IWsdlObjectWrapper#getWsdlObjectLocalName()
     */
    abstract public String getWsdlObjectLocalName();

    /*
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(Class adapter) {
        if (adapter == IPropertySource.class) {
            return getPropertySource();
        }
        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    /**
     * Default implementation returns WsdlWrapperPropertySource which provides
     * "Name" property. If you don't want to provide properties for object then
     * return <code>null</code> in the overwritten method.
     * 
     * @return The property source object for wrapper or null if object should
     *         not have properties.
     */
    protected IPropertySource getPropertySource() {
        if (propertySource == null) {
            propertySource = new WsdlWrapperPropertySource(this);
        }
        return propertySource;
    }
}

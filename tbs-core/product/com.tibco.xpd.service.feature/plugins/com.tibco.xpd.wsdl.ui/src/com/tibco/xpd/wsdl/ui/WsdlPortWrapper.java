/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import javax.wsdl.Port;

import org.eclipse.ui.views.properties.IPropertySource;

import com.tibco.xpd.wsdl.ui.internal.Messages;
import com.tibco.xpd.wsdl.ui.properites.PortWrapperPropertySource;

/**
 * Wraps WSDL 'port' element.
 * 
 * @author nwilson
 */
public class WsdlPortWrapper extends WsdlObjectWrapper {

    /** The port. */
    private IPropertySource propertySource;

    /**
     * @param port
     *            The port.
     */
    public WsdlPortWrapper(Object parent, Port port) {
        super(parent, port);
    }

    /**
     * @return The port.
     */
    public Port getPort() {
        return (Port) getObject();
    }

    /*
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == Port.class) {
            return getObject();
        }
        return super.getAdapter(adapter);
    }

    /**
     * Synchronisation is not needed as it will always be called form UI thread.
     * 
     * @return the singleton instance of BindingOperationPropertySource;
     */
    @Override
    protected IPropertySource getPropertySource() {
        if (propertySource == null) {
            propertySource = new PortWrapperPropertySource(this);
        }
        return propertySource;
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.WsdlObjectWrapper#getWsdlObjectLocalName()
     */
    @Override
    public String getWsdlObjectLocalName() {
        return ((Port) getObject()).getName();
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.IWsdlObjectWrapper#getWsdlTypeName()
     */
    public String getWsdlTypeName() {
        return Messages.WsdlPortWrapper_WsdlType_label;
    }
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import javax.wsdl.Service;

import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Wraps WSDL 'service' element.
 * 
 * @author nwilson
 */
public class WsdlServiceWrapper extends WsdlObjectWrapper {

    /**
     * @param service
     *            The service.
     */
    public WsdlServiceWrapper(Object parent, Service service) {
        super(parent, service);
    }

    /**
     * @return The service.
     */
    public Service getService() {
        return (Service) getObject();
    }

    /*
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == Service.class) {
            return getObject();
        }
        return super.getAdapter(adapter);
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.WsdlObjectWrapper#getWsdlObjectLocalName()
     */
    @Override
    public String getWsdlObjectLocalName() {
        return ((Service) getObject()).getQName().getLocalPart();
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.IWsdlObjectWrapper#getWsdlTypeName()
     */
    public String getWsdlTypeName() {
        return Messages.WsdlServiceWrapper_WsdlType_label;
    }
}

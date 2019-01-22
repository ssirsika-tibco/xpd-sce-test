/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import javax.wsdl.PortType;

import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Wrapper class for WSDL PortType.
 * <p>
 * <i>Created: 9 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class WsdlPortTypeWrapper extends WsdlObjectWrapper {

    /**
     * The constructor.
     * 
     * @param portType
     *            The wrapped PortType object.
     */
    public WsdlPortTypeWrapper(Object parent, PortType portType) {
        super(parent, portType);
    }

    /**
     * @return The wrapped WSDL port type.
     */
    public PortType getPortType() {
        return (PortType) getObject();
    }

    /*
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == PortType.class) {
            return getObject();
        }
        return super.getAdapter(adapter);
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.WsdlObjectWrapper#getWsdlObjectLocalName()
     */
    @Override
    public String getWsdlObjectLocalName() {
        return ((PortType) getObject()).getQName().getLocalPart();
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.IWsdlObjectWrapper#getWsdlTypeName()
     */
    public String getWsdlTypeName() {
        return Messages.WsdlPortTypeWrapper_WsdlType_label;
    }
}

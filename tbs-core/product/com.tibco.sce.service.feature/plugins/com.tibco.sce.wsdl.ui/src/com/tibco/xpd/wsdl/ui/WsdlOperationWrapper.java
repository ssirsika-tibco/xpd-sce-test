/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import javax.wsdl.Operation;

import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * @author nwilson
 */
public class WsdlOperationWrapper extends WsdlObjectWrapper {

    /**
     * @param operation
     *            The operation.
     */
    public WsdlOperationWrapper(Object parent, Operation operation) {
        super(parent, operation);
    }

    /**
     * @return The operation.
     */
    public Operation getOperation() {
        return (Operation) getObject();
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.WsdlObjectWrapper#getAdapter(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == Operation.class) {
            return getObject();
        }
        return super.getAdapter(adapter);
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.WsdlObjectWrapper#getWsdlObjectLocalName()
     */
    @Override
    public String getWsdlObjectLocalName() {
        return ((Operation) getObject()).getName();
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.IWsdlObjectWrapper#getWsdlTypeName()
     */
    public String getWsdlTypeName() {
        return Messages.WsdlOperationWrapper_WsdlType_label;
    }
}

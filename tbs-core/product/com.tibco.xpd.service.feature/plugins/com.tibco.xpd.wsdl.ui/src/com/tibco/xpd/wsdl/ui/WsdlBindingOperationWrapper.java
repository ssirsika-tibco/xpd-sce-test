/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import javax.wsdl.BindingOperation;

import org.eclipse.ui.views.properties.IPropertySource;

import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Wrapper for BindingOperation object.
 * <p>
 * <i>Created: 10 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class WsdlBindingOperationWrapper extends WsdlObjectWrapper {

    /**
     * @param operation
     *            The operation.
     */
    public WsdlBindingOperationWrapper(Object parent,
            BindingOperation bindingOperation) {
        super(parent, bindingOperation);
    }

    /**
     * @return The operation binding.
     */
    public BindingOperation getBindingOperation() {
        return (BindingOperation) getObject();
    }

    /*
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == BindingOperation.class) {
            return getObject();
        } else if (adapter == IPropertySource.class) {
            return getPropertySource();
        }
        return super.getAdapter(adapter);
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.WsdlObjectWrapper#getWsdlObjectLocalName()
     */
    @Override
    public String getWsdlObjectLocalName() {
        return ((BindingOperation) getObject()).getName();
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.IWsdlObjectWrapper#getWsdlTypeName()
     */
    public String getWsdlTypeName() {
        return Messages.WsdlBindingOperationWrapper_WsdlType_label;
    }
}

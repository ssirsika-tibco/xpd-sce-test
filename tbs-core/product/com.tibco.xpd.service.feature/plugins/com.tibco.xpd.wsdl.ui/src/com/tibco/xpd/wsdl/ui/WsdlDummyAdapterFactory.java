/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.IItemLabelProvider;

/**
 * @author nwilson
 */
public class WsdlDummyAdapterFactory implements AdapterFactory {

    /**
     * @param object The object to adapt.
     * @param type The type to adapt to.
     * @return The adapted object.
     * @see org.eclipse.emf.common.notify.AdapterFactory#adapt(java.lang.Object,
     *      java.lang.Object)
     */
    public Object adapt(Object object, Object type) {
        Object result = null;
        if (type != null) {
            if (type.getClass().equals(IItemLabelProvider.class)) {
                result = new WsdlItemLabelProvider();
            }
        }
        return result;
    }

    /**
     * @param target The target object.
     * @param type The type.
     * @return The adapter.
     * @see org.eclipse.emf.common.notify.AdapterFactory#adapt(
     *      org.eclipse.emf.common.notify.Notifier, java.lang.Object)
     */
    public Adapter adapt(Notifier target, Object type) {
        return null;
    }

    /**
     * @param notifier The notifier object.
     * @see org.eclipse.emf.common.notify.AdapterFactory#adaptAllNew(
     *      org.eclipse.emf.common.notify.Notifier)
     */
    public void adaptAllNew(Notifier notifier) {
    }

    /**
     * @param target The target notifier.
     * @param type The type.
     * @return The Adapter.
     * @see org.eclipse.emf.common.notify.AdapterFactory#adaptNew(
     *      org.eclipse.emf.common.notify.Notifier, java.lang.Object)
     */
    public Adapter adaptNew(Notifier target, Object type) {
        return null;
    }

    /**
     * @param type The type to check.
     * @return true if this adapter factory handles the type.
     * @see org.eclipse.emf.common.notify.AdapterFactory#isFactoryForType(
     *      java.lang.Object)
     */
    public boolean isFactoryForType(Object type) {
        return type instanceof IItemLabelProvider;
    }

}

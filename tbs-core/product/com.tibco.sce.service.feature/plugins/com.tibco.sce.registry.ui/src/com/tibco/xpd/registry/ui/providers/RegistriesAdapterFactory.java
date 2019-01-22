/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.providers;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.Search;
import com.tibco.xpd.registry.ui.selector.RegistrySearch;

/**
 * 
 * <p>
 * <i>Created: 4 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RegistriesAdapterFactory implements IAdapterFactory {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adaptableObject instanceof Registry) {
            if (adapterType == IWorkbenchAdapter.class) {
                return new RegistryItemProvider();
            }
        }
        if (adaptableObject instanceof Search
                || adaptableObject instanceof RegistrySearch) {
            if (adapterType == IWorkbenchAdapter.class) {
                return new SearchItemProvider();
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    public Class[] getAdapterList() {
        return new Class[] { IWorkbenchAdapter.class };
    }

}

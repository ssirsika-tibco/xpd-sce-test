/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.adapters;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;

/**
 * Adapter factory to adapt an <code>AbstractAssetGroup</code> to an
 * <code>IResource</code>.
 * 
 * @author njpatel
 */
public class AssetGroupsAdapterFactory implements IAdapterFactory {

    public Object getAdapter(Object adaptableObject, Class adapterType) {

        if (adaptableObject instanceof AbstractAssetGroup) {

            if (adapterType == IResource.class) {
                return ((AbstractAssetGroup) adaptableObject)
                        .getAdapter(adapterType);
            }

        }

        return null;
    }

    public Class[] getAdapterList() {
        // TODO Auto-generated method stub
        return new Class[] { IResource.class };
    }

}

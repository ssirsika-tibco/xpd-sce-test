/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.core.runtime.CoreException;

/**
 * This provides access to other asset configurations during the creation of a
 * new project.
 * 
 * @author njpatel
 * 
 */
public interface IAssetConfigurationProvider {

    /**
     * Get the asset configuration object of the asset with the given id.
     * 
     * @param assetId
     *            id of asset
     * @return configuration object. This will be <code>null</code> if no
     *         configuration object is provided for the asset.
     * 
     * @throws CoreException
     */
    public Object getAssetConfiguration(String assetId) throws CoreException;
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.core.resources.IProject;

/**
 * Used by the projectAsset extension to provide the current version of the
 * asset dynamically (in place of the version attribute in the Asset extension).
 * 
 * @author njpatel
 * 
 * @since 3.5
 * 
 */
public interface IProjectAssetVersionProvider {

    /**
     * Get the current version of this asset type.
     * 
     * @param project
     *            get the asset version of this project
     * 
     * @return version.
     */
    int getVersion(IProject project);
}

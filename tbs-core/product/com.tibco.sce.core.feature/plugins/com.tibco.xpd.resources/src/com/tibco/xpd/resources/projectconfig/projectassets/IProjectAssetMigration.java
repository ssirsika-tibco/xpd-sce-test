/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;

/**
 * Interface to be implemented by the asset migration extension.
 * 
 * @author njpatel
 * 
 * @since 3.5
 */
public interface IProjectAssetMigration {

    /**
     * Migrate the asset of the given project to the current version.
     * 
     * @param project
     *            project to migrate
     * @param asset
     *            the asset being migrated
     * @param assetVersion
     *            the version of the asset in the project
     * @param currentVersion
     *            the current version of the asset
     * @return result of the migrate. If this returns an ERROR status then the
     *         migration processes will be halted.
     */
    IStatus migrate(IProject project, IProjectAsset asset, int assetVersion,
            int currentVersion);

}

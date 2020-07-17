/*
 * Copyright (c) TIBCO Software Inc 2004, 2020. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.custom.internal.projectasset;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetMigration;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;

/**
 * Asset migration for the BusinessData asset (which marks a project as being for case data etc).
 * 
 * Currently this does nothing, but the migration needs to be there in order for the asset version to be updated during
 * import.
 *
 * @author aallway
 * @since 17 Jul 2020
 */
public class BusinessDataAssetMigration implements IProjectAssetMigration, IProjectAssetVersionProvider {

    /**
     * 
     */
    public BusinessDataAssetMigration() {

    }

    /**
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetMigration#migrate(org.eclipse.core.resources.IProject, com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset, int, int)
     *
     * @param project
     * @param asset
     * @param assetVersion
     * @param currentVersion
     * @return
     */
    @Override
    public IStatus migrate(IProject project, IProjectAsset asset, int assetVersion, int currentVersion) {
        /*
         * Currently this does nothing, but the migration needs to be there in order for the asset version to be updated
         * during import.
         */
        return Status.OK_STATUS;
    }

    /**
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider#getVersion(org.eclipse.core.resources.IProject)
     *
     * @param project
     * @return
     */
    @Override
    public int getVersion(IProject project) {
        return Integer.parseInt(BOMResourcesPlugin.BOM_VERSION);
    }

}

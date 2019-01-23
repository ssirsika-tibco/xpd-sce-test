/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.contributors;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;
import com.tibco.xpd.resources.projectconfig.projectassets.util.AssetWorkingCopyMigration;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;

/**
 * Asset migration for the Business Process asset.
 * 
 * @author njpatel
 */
public class BusinessProcessesAssetMigration extends AssetWorkingCopyMigration
        implements IProjectAssetVersionProvider {

    public BusinessProcessesAssetMigration() {
        super(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                Xpdl2ResourcesConsts.XPDL_EXTENSION);
    }

    @Override
    public int getVersion(IProject project) {
        return Integer.parseInt(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
    }

}

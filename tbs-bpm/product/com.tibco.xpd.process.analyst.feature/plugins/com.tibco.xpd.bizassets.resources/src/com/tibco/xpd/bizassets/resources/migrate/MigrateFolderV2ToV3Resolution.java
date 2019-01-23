/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.migrate;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * @author nwilson
 */
public class MigrateFolderV2ToV3Resolution implements IResolution {

    /**
     * @param marker
     * @throws ResolutionException
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     */
    public void run(IMarker marker) throws ResolutionException {
        IResource resource = marker.getResource();
        if (resource instanceof IProject) {
            IProject project = (IProject) resource;
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            config.addAssetTask(BusinessAssetsConstants.BUSINESS_ASSETS_V3);
            IFolder folder =
                    project
                            .getFolder(BusinessAssetsConstants.BUSINESSASSETS_FOLDER);
            if (folder.exists()) {
                config.getSpecialFolders().addFolder(folder,
                        BusinessAssetsConstants.BIZ_ASSETS_SPECIAL_FOLDER_KIND);
            }
        }
    }

}

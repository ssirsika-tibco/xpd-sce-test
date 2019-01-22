/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.migrate;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.BasicEList;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * @author nwilson
 */
public class BusinessAssetsResourceValidatorV2ToV3 implements
        WorkspaceResourceValidator {

    /** V2 Business Assets folder. */
    private static final String FOLDER_V2 = "bizassets.migrateFolderV2ToV3"; //$NON-NLS-1$

    /**
     * @param project
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject project) {
    }

    /**
     * @param scope
     * @param resource
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     */
    public void validate(IValidationScope scope, IResource resource) {
        if (resource instanceof IProject) {
            IProject project = (IProject) resource;
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (!config
                    .hasAssetType(BusinessAssetsConstants.BUSINESS_ASSETS_V3)) {
                IFolder folder =
                        project
                                .getFolder(BusinessAssetsConstants.BUSINESSASSETS_FOLDER);
                if (folder.exists()) {
                    SpecialFolders specials = config.getSpecialFolders();
                    SpecialFolder special =
                            specials
                                    .getFolder(
                                            folder,
                                            BusinessAssetsConstants.BIZ_ASSETS_SPECIAL_FOLDER_KIND);
                    if (special == null) {
                        String location = resource.getFullPath().toString();
                        scope.createIssue(FOLDER_V2, location, location);
                    } else {
                        BasicEList<String> assets = new BasicEList<String>();
                        assets.add(BusinessAssetsConstants.BUSINESS_ASSETS_V3);
                        config.addAssetTypes(assets);
                    }
                }
            }
        }
    }

}

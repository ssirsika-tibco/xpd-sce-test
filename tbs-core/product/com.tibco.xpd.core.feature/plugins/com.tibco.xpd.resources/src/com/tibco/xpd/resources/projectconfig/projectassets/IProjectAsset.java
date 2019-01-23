/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Interface defining a Project Asset.
 * 
 * @author njpatel
 */
public interface IProjectAsset {

    /**
     * Get the ID of the asset
     * 
     * @return Id of the asset
     */
    public String getId();

    /**
     * Get the name of the asset
     * 
     * @return Name of the asset
     */
    public String getName();

    /**
     * Get the description of the asset. This may contain HTML tags.
     * 
     * @return Description of the asset. If no description is defined then
     *         <b>null</b> will be returned.
     */
    public String getDescription();

    /**
     * Get the assets that extend this asset.
     * 
     * @return List of extending asset types, empty list if none.
     */
    public IProjectAsset[] getExtendingAssets();

    /**
     * Get the the asset category this asset belongs to (categories in the new
     * project wizard).
     * 
     * @return asset category.
     */
    public IProjectAssetCategory getCategory();

    /**
     * Returns image descriptor for asset.
     * 
     * @return image descriptor for project asset or null if image descriptor
     *         was not assigned.
     */
    public ImageDescriptor getImageDescriptor();

    /**
     * Get the visible attribute of this asset type.
     * 
     * @return <b>true</b> if the asset type should be displayed in the new
     *         project wizard, <b>false</b> if the asset type should be hidden
     *         in the wizard. The asset type should still be configured even if
     *         this value is set to false.
     */
    public boolean isVisible();

    /**
     * Get the current version of this asset.
     * 
     * @param project
     *            get version of this project
     * @return current version
     * @since 3.5
     */
    public int getVersion(IProject project);

    /**
     * Get the showConfigPageWhenInvisible attribute of this asset type.
     * 
     * @return <b>true</b> if the asset configuration page for invisible asset
     *         should be displayed in the new project wizard , <b>false</b> if
     *         the asset configuration page for invisible asset should be hidden
     *         in the wizard. The asset type should still be configured even if
     *         this value is set to false.
     */
    public boolean isShowConfigPageWhenInvisible();
}

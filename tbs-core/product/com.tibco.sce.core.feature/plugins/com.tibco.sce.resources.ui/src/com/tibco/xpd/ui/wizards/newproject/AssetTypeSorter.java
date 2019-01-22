/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import java.util.List;

import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;

/**
 * Asset type sorter contributed with the new project creation wizard. This
 * allows the wizard contributor to control the order in which the assets'
 * contribution appear. Note that this does not affect the tree selection view
 * in the asset selection page of the wizard.
 * 
 * @author njpatel
 * @since 3.2
 */
public abstract class AssetTypeSorter {

    /**
     * Sort the order of the asset contributions.
     * <p>
     * NOTE: Keep in mind the asset dependencies when sorting the asset types.
     * </p>
     * 
     * @param assets
     *            selected asset types.
     * @return ordered list of assets or <code>null</code> if no change should
     *         be made
     */
    public abstract List<IProjectAsset> sortAssetTypes(
            final List<IProjectAsset> assets);

}

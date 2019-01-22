/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import java.util.Collection;

import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;

/**
 * Represents project assets model.
 * <p>
 * <i>Created: 13 Jun 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public interface IProjectAssetManager {

    /**
     * Returns all available project assets.
     * 
     * @return all available project assets
     */
    public ProjectAssetElement[] getAssets();

    /**
     * Returns all top level project asset categories. Top level category does
     * not have parent category.
     * 
     * @return all top level project asset categories. The list can not be
     *         modified.
     */
    public Collection<IProjectAssetCategory> getTopLevelCategories();

    /**
     * Get the project asset of the given <i>id</i>.
     * 
     * @param id
     * @return <code>{@link ProjectAssetElement}</code> with the given Id. If
     *         there is no asset type defined of the given <i>id</i> then
     *         <b>null</b> will be returned.
     */
    public ProjectAssetElement getAssetById(String id);

}
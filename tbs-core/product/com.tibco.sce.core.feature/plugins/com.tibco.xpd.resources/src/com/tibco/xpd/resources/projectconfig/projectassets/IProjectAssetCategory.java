/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Defines project asset category. It is purely UI related element for better
 * asset organization.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public interface IProjectAssetCategory {
    /**
     * Get the ID of the category
     * 
     * @return Id of the asset
     */
    public String getId();

    /**
     * Get the name of the category
     * 
     * @return Name of the category
     */
    public String getName();

    /**
     * Get the description of the category. This may contain HTML tags.
     * 
     * @return Description of the category. If no description is defined then
     *         <b>null</b> will be returned.
     */
    public String getDescription();

    /**
     * Returns parent category or null it the category is top level category.
     * 
     * @return parent category or null if top level category.
     */
    public IProjectAssetCategory getParentCategory();

    /**
     * Returns children categories and assets.
     * 
     * @return children categories and assets.
     */
    public Object[] getChildren();

    /**
     * Returns true if category or sub category contains visible asset(s).
     * 
     * @return true if category or sub category contains visible asset(s).
     */
    public boolean containsVisibleAssets();

    /**
     * Returns image descriptor for asset category.
     * 
     * @return image descriptor for project asset category or null if image
     *         descriptor was not assigned.
     */
    public ImageDescriptor getImageDesctiptor();
}

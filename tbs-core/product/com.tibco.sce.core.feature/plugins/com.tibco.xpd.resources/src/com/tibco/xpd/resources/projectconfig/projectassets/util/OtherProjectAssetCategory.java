/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory;

/**
 * 
 * <p>
 * <i>Created: 14 Jun 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class OtherProjectAssetCategory implements IProjectAssetCategory {

    private static final String OTHER_PROJECT_ASSET_CATEGORY_NAME = Messages.OtherProjectAssetCategory_otherCategory_label;

    private static final String OTHER_PROJECT_ASSET_CATEGORY_DESC = Messages.OtherProjectAssetCategory_otherCategory_message;

    private Object[] children = new Object[0];

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#getChildren()
     */
    public Object[] getChildren() {
        return children;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#getDescription()
     */
    public String getDescription() {
        return OTHER_PROJECT_ASSET_CATEGORY_DESC;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#getId()
     */
    public String getId() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#getName()
     */
    public String getName() {
        return OTHER_PROJECT_ASSET_CATEGORY_NAME;
    }

    /*
     * It's always top level category.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#getParentCategory()
     */
    public IProjectAssetCategory getParentCategory() {
        return null;
    }

    /**
     * @param children
     *            the children to set
     */
    void setChildren(Object[] children) {
        this.children = children;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#containsAssets()
     */
    public boolean containsVisibleAssets() {
        for (Object child : getChildren()) {
            if (child instanceof IProjectAsset) {
                if (((IProjectAsset) child).isVisible()) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#getImageDesctiptor()
     */
    public ImageDescriptor getImageDesctiptor() {
        return null;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory;

/**
 * Represents category taken from extension.
 * 
 * <p>
 * <i>Created: 12 Jun 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ProjectAssetCategoryElement implements IProjectAssetCategory {

    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

    private static final String DESCRIPTION_ELEMENT = "description"; //$NON-NLS-1$

    private static final String PARENT_CATEGORY_ATTRIBUTE = "parentCategory"; //$NON-NLS-1$

    private static final String ICON_ATTRIBUTE = "icon"; //$NON-NLS-1$

    private IConfigurationElement element;

    private String id;

    private String name;

    private String description;

    private ProjectAssetCategoryElement parentCategory;

    private Object[] children = new Object[0];

    private String parentCategoryId;

    private ImageDescriptor imageDescriptor;

    /**
     * The constructor.
     */
    public ProjectAssetCategoryElement(IConfigurationElement element) {
        this.element = element;
    }

    /**
     * Get the id set in the extension
     * 
     * @return ID of the extension
     */
    public String getId() {
        if (id == null) {
            id = getAttribute(ID_ATTRIBUTE);
        }
        return id;
    }

    /**
     * Get the name set in the extension
     * 
     * @return name of the extension
     */
    public String getName() {
        if (name == null) {
            name = getAttribute(NAME_ATTRIBUTE);
        }
        return name;
    }

    /**
     * Get the description of this extension.
     * 
     * @return Description of this extension. If one is not set then <b>null</b>
     *         will be returned.
     */
    public String getDescription() {
        if (element != null) {
            IConfigurationElement[] children = element
                    .getChildren(DESCRIPTION_ELEMENT);
            if (children != null && children.length > 0) {
                description = children[0].getValue();
            }
        }
        return description;
    }

    /**
     * Get the value of the given attribute.
     * 
     * @param attribute
     * @return value of the attribute if set, empty string otherwise.
     */
    private String getAttribute(String attribute) {
        return element != null ? element.getAttribute(attribute) : ""; //$NON-NLS-1$
    }

    /**
     * Assigns parent category to the category.
     * 
     * @param parentCategory
     *            the parent category.
     */
    void setParent(ProjectAssetCategoryElement parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * Assigns children to the category.
     * 
     * @param children
     *            the list of direct children (categories and assets).
     */
    void setChildren(Object[] children) {
        this.children = children;
    }

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
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#getParent()
     */
    public IProjectAssetCategory getParentCategory() {
        return parentCategory;
    }

    /**
     * Get the name set in the extension
     * 
     * @return name of the extension
     */
    String getParentCategoryId() {
        if (parentCategoryId == null) {
            parentCategoryId = getAttribute(PARENT_CATEGORY_ATTRIBUTE);
        }
        return parentCategoryId;
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
            } else if (child instanceof IProjectAssetCategory) {
                IProjectAssetCategory category = (IProjectAssetCategory) child;
                if (category.containsVisibleAssets()) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory#getImageDesctiptor()
     */
    public ImageDescriptor getImageDesctiptor() {
        if (imageDescriptor == null) {
            imageDescriptor = getImageDescriptor(element, ICON_ATTRIBUTE);
        }
        return imageDescriptor;
    }

    private static ImageDescriptor getImageDescriptor(
            IConfigurationElement configElement, String attrName) {
        String iconPath = configElement.getAttribute(attrName);
        // Get the icon descriptor
        if (iconPath != null) {
            return AbstractUIPlugin.imageDescriptorFromPlugin(configElement
                    .getNamespaceIdentifier(), iconPath);
        }
        return null;
    }
}

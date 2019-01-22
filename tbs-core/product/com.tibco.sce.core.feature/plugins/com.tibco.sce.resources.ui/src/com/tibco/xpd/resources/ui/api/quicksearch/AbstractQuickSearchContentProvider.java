/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch;

import java.util.Collection;

import org.eclipse.ui.IWorkbenchPartReference;

/**
 * AbstractQuickSearchContentProvider
 * <p>
 * Content provider for the Quick Search View toolbar.
 * 
 * <p>
 * Provides content and the ability to force a refresh of the current matching
 * items when the content provider detects that something may have changed that
 * affects the content list.
 * 
 * <p>
 * When your content provider is created you can listen to the object that
 * provides you content and in turn call fireNotifyChanged() when this happens.
 * <p>
 * This will force the searchable content to be re-requested by the toolbar
 * because it adds a listener to your content provider.
 * 
 * @author aallway
 * @since 3.1
 */
public abstract class AbstractQuickSearchContentProvider {

    protected IWorkbenchPartReference workbenchPartRef;

    /**
     * 
     */
    public AbstractQuickSearchContentProvider(IWorkbenchPartReference partRef) {
        workbenchPartRef = partRef;
    }

    /**
     * @return the workbenchPart
     */
    public IWorkbenchPartReference getWorkbenchPartRef() {
        return workbenchPartRef;
    }

    /**
     * Sub-class should remove any listeners it has with the workbench part.
     */
    public abstract void dispose();

    /**
     * Get a list of user selectable category id's, these are passed to
     * getElements(Collection categories) when the user wishes to search within
     * a given category.
     * 
     * @return hierarchical list of quick search categories
     */
    public abstract Collection<QuickSearchPopupCategory> getCategories();

    /**
     * Get searchable elements by category.
     * <p>
     * Note that as there can be multiple separate contributions to a single
     * view, you may be passed categories contributed elsewhere - you can use
     * the Id element to identify which are yours.
     * <p>
     * Note that although the category list is hierarchical, this method will be
     * passed only the lowest level (i.e. without children) categories. This
     * makes life simpler for the implementer of the content provider because
     * they can build a hierarchy as they require but only have to deal with the
     * categories that deal with real searchable elements.
     * <p>
     * You can utilise the isCategoryEnabled() method passing this list and one
     * of your category id's to check whether tthe category is enabled or not.
     * <p>
     * If no sub-categories are selected by the user then the getElements()
     * method is called instead.
     * 
     * @param categories
     * 
     * @return List of searchable elements fitting the categories that are
     *         enabled in the given list
     */
    public abstract Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories);

    /**
     * Get all searchable elements for given view.
     * 
     * @return List of all searchable elements.
     */
    public abstract Collection<?> getElements();

    /**
     * If list is empty then ALL categories are implicitly enabled.
     * <p>
     * If ANY categories are given in list then the category must be present and
     * explicitly set to enabled in order for it to be considered enabled.
     * 
     * @param categories
     * @param categoryId
     * @return Whether the given quick search category is set to enabled in the
     *         given category list.
     */
    protected boolean isCategoryEnabled(
            Collection<QuickSearchPopupCategory> categories, String categoryId) {
        boolean enabled = false;

        if (categories == null || categories.isEmpty()) {
            enabled = true;
        } else {
            for (QuickSearchPopupCategory cat : categories) {
                if (categoryId.equals(cat.getId())) {
                    enabled = true;
                    break;
                }
            }
        }
        return enabled;
    }

}

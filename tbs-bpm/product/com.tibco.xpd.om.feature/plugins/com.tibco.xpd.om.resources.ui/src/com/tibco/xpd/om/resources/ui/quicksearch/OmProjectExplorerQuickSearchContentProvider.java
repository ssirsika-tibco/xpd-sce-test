/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.internal.indexing.OMResourceIndexProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 * 
 * 
 * @author patkinso
 * @since 20 Jun 2012
 */
public class OmProjectExplorerQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    private static final String SUPER_CATEGORY_ID =
            "projectExplorer.om.elements.category"; //$NON-NLS-1$

    private List<IndexerItem> availableElements = new ArrayList<IndexerItem>();

    /**
     * @param partRef
     */
    public OmProjectExplorerQuickSearchContentProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);

        // Cache all the currently available elements now because (a) this
        // looks providers up from extension point and (b) we can get away
        // with it because the content cannot change without search popup
        // being disabled and therefore we will get thrown away when that
        // happens.
        addAllAvailableElements(OmProjectExplorerQuickSearchCategoryEnum
                .getTypeIds());

    }

    /**
     * @param itemType
     */
    private void addAllAvailableElements(String... criterionTypes) {

        for (String itemType : criterionTypes) {
            IndexerItem criteria =
                    new IndexerItemImpl(null, itemType, null, null);

            Collection<IndexerItem> result =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(OMResourceIndexProvider.INDEXER_ID, criteria);
            if (result != null && !result.isEmpty()) {
                availableElements.addAll(result);
            }
        }

    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#getCategories()
     * 
     * @return
     */
    @Override
    public Collection<QuickSearchPopupCategory> getCategories() {

        List<QuickSearchPopupCategory> ret =
                new ArrayList<QuickSearchPopupCategory>();

        QuickSearchPopupCategory superCategory =
                new QuickSearchPopupCategory(
                        SUPER_CATEGORY_ID,
                        Messages.OmProjectExplorerQuickSearchContentProvider_SuperCategoryFilter_menu);
        ret.add(superCategory);

        superCategory
                .addChild(new QuickSearchPopupCategory(
                        OmProjectExplorerQuickSearchCategoryEnum.OM_ORGANISATION
                                .getTypeId(),
                        Messages.OmProjectExplorerQuickSearchContentProvider_Organisation_menu));
        superCategory
                .addChild(new QuickSearchPopupCategory(
                        OmProjectExplorerQuickSearchCategoryEnum.OM_ORG_UNIT
                                .getTypeId(),
                        Messages.OmProjectExplorerQuickSearchContentProvider_Organisation_units_menu));
        superCategory
                .addChild(new QuickSearchPopupCategory(
                        OmProjectExplorerQuickSearchCategoryEnum.OM_POSITION
                                .getTypeId(),
                        Messages.OmProjectExplorerQuickSearchContentProvider_Positions_menu));
        superCategory
                .addChild(new QuickSearchPopupCategory(
                        OmProjectExplorerQuickSearchCategoryEnum.OM_GROUP
                                .getTypeId(),
                        Messages.OmProjectExplorerQuickSearchContentProvider_Groups_menu));

        return ret;
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#getElements(java.util.Collection)
     * 
     * @param categories
     * @return
     */
    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {

        for (OmProjectExplorerQuickSearchCategoryEnum cat : OmProjectExplorerQuickSearchCategoryEnum
                .values()) {
            cat.setEnabled(isCategoryEnabled(categories, cat.getTypeId()));
        }

        List<IndexerItem> retElements = new ArrayList<IndexerItem>();

        for (IndexerItem item : availableElements) {
            OmProjectExplorerQuickSearchCategoryEnum cat =
                    OmProjectExplorerQuickSearchCategoryEnum
                            .get(item.getType());
            if ((cat != null) && (cat.isEnabled())) {
                retElements.add(item);
            }
        }

        return retElements;
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#getElements()
     * 
     * @return
     */
    @Override
    public Collection<?> getElements() {
        return availableElements;
    }

}

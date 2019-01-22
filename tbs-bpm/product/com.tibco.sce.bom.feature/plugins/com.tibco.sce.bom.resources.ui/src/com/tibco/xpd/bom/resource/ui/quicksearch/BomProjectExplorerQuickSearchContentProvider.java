/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.bom.resource.ui.quicksearch;

import static com.tibco.xpd.bom.resources.utils.ResourceItemType.CLASS;
import static com.tibco.xpd.bom.resources.utils.ResourceItemType.ENUMERATION;
import static com.tibco.xpd.bom.resources.utils.ResourceItemType.PACKAGE;
import static com.tibco.xpd.bom.resources.utils.ResourceItemType.PRIMITIVE_TYPE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 * Content provider for BOM quick search content provider.
 * 
 * @author aallway
 * @since 3.2
 */
public class BomProjectExplorerQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    private static final String BOM_ELEMENTS_QS_CATEGORY_ID =
            "projectExplorer.bomelements.category"; //$NON-NLS-1$

    private static final String BOM_PACKAGES_QS_CATEGORY_ID =
            "projectExplorer.bom.packages.category"; //$NON-NLS-1$

    private static final String BOM_CLASSES_QS_CATEGORY_ID =
            "projectExplorer.bom.classes.category"; //$NON-NLS-1$

    private static final String BOM_PRIMATIVE_TYPES_QS_CATEGORY_ID =
            "projectExplorer.bom.primitivetypes.category"; //$NON-NLS-1$

    private static final String BOM_ENUMERATIONS_QS_CATEGORY_ID =
            "projectExplorer.bom.enumerations.category"; //$NON-NLS-1$

    private List<IndexerItem> availableElements = new ArrayList<IndexerItem>();

    /**
     * @param partRef
     */
    public BomProjectExplorerQuickSearchContentProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);

        // Cache all the currently available elements now because (a) this
        // looks providers up from extension point and (b) we can get away
        // with it because the content cannot change without search popup
        // being disabled and therefore we will get thrown away when that
        // happens.
        addAllAvailableElements(new ResourceItemType[] { PACKAGE, CLASS,
                PRIMITIVE_TYPE, ENUMERATION });

    }

    /**
     * Add item to the available search set
     */
    private void addAllAvailableElements(ResourceItemType... criterionTypes) {

        for (ResourceItemType itemType : criterionTypes) {
            IndexerItem criteria =
                    new IndexerItemImpl(null, itemType.name(), null, null);

            Collection<IndexerItem> result =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
            if (result != null && !result.isEmpty()) {
                availableElements.addAll(result);
            }
        }

    }

    @Override
    public void dispose() {
    }

    @Override
    public Collection<QuickSearchPopupCategory> getCategories() {
        List<QuickSearchPopupCategory> cats =
                new ArrayList<QuickSearchPopupCategory>();

        QuickSearchPopupCategory libElementsCat =
                new QuickSearchPopupCategory(
                        BOM_ELEMENTS_QS_CATEGORY_ID,
                        Messages.BomProjectExplorerQuickSearchContentProvider_SuperCategoryFilter_menu);
        cats.add(libElementsCat);

        libElementsCat.addChild(new QuickSearchPopupCategory(
                BOM_PACKAGES_QS_CATEGORY_ID,
                Messages.BomEditorQuickSearchContentProvider_packages_menu));
        libElementsCat.addChild(new QuickSearchPopupCategory(
                BOM_CLASSES_QS_CATEGORY_ID,
                Messages.BomEditorQuickSearchContentProvider_classes_menu));
        libElementsCat.addChild(new QuickSearchPopupCategory(
                BOM_PRIMATIVE_TYPES_QS_CATEGORY_ID,
                Messages.BomEditorQuickSearchContentProvider_primitives_menu));
        libElementsCat
                .addChild(new QuickSearchPopupCategory(
                        BOM_ENUMERATIONS_QS_CATEGORY_ID,
                        Messages.BomEditorQuickSearchContentProvider_enumerations_menu));

        return cats;
    }

    @Override
    public Collection<?> getElements() {
        return availableElements;
    }

    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {

        boolean wantPackages =
                isCategoryEnabled(categories, BOM_PACKAGES_QS_CATEGORY_ID);

        boolean wantClasses =
                isCategoryEnabled(categories, BOM_CLASSES_QS_CATEGORY_ID);

        boolean wantPrimitiveTypes =
                isCategoryEnabled(categories,
                        BOM_PRIMATIVE_TYPES_QS_CATEGORY_ID);

        boolean wantEnumerations =
                isCategoryEnabled(categories, BOM_ENUMERATIONS_QS_CATEGORY_ID);

        List<IndexerItem> retElements = new ArrayList<IndexerItem>();

        for (IndexerItem item : availableElements) {

            String type = item.getType();
            boolean addItem =
                    (wantPackages && ResourceItemType.PACKAGE.name()
                            .equals(type))
                            || (wantClasses && ResourceItemType.CLASS.name()
                                    .equals(type))
                            || (wantPrimitiveTypes && ResourceItemType.PRIMITIVE_TYPE
                                    .name().equals(type))
                            || (wantEnumerations && ResourceItemType.ENUMERATION
                                    .name().equals(type));

            if (addItem) {
                retElements.add(item);
            }
            ;
        }

        return retElements;
    }

}

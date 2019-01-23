/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.wm.tasklibrary.editor.quicksearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;

/**
 * Content provider for Project explorer quick search contribution for Task
 * Library stuff.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryProjectExplorerQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    private static final String TASKLIBRARY_ELEMENTS_QS_CATEGORY_ID =
            "projectExplorer.tasklibraryelements.category"; //$NON-NLS-1$

    private static final String TASKLIBRARIES_QS_CATEGORY_ID =
            "projectExplorer.tasklibraries.category"; //$NON-NLS-1$

    private static final String TASKLIBRARY_TASKS_QS_CATEGORY_ID =
            "projectExplorer.tasklibrary.tasks.category"; //$NON-NLS-1$

    private List<IndexerItem> availableElements;

    /**
     * @param partRef
     */
    public TaskLibraryProjectExplorerQuickSearchContentProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);

        // Cache all the currently available elements now because (a) this
        // looks providers up from extension point and (b) we can get away
        // with it because the content cannot change without search popup
        // being disabled and therefore we will get thrown away when that
        // happens.

        // long start = System.currentTimeMillis();

        //
        // Searchable content is task libraries and tasks.
        //
        availableElements = new ArrayList<IndexerItem>();

        IndexerItem criteria =
                new IndexerItemImpl(null,
                        Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEX_TYPE, null,
                        null);

        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEXER_ID,
                                criteria);
        if (result != null && !result.isEmpty()) {
            availableElements.addAll(result);
        }

        criteria =
                new IndexerItemImpl(null,
                        Xpdl2ResourcesPlugin.TASK_LIBRARY_TASK_INDEX_TYPE,
                        null, null);

        result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEXER_ID,
                                criteria);
        if (result != null && !result.isEmpty()) {
            availableElements.addAll(result);
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
                        TASKLIBRARY_ELEMENTS_QS_CATEGORY_ID,
                        Messages.TaskLibraryProjectExplorerQuickSearchContentProvider_TaskLibraryElementsQuickSearchCategory_label);
        cats.add(libElementsCat);

        libElementsCat
                .addChild(new QuickSearchPopupCategory(
                        TASKLIBRARIES_QS_CATEGORY_ID,
                        Messages.TaskLibraryProjectExplorerQuickSearchContentProvider_TaskLibrariesQuickSearchCategory_label));
        libElementsCat
                .addChild(new QuickSearchPopupCategory(
                        TASKLIBRARY_TASKS_QS_CATEGORY_ID,
                        Messages.TaskLibraryEditorQuickSearchContentProvider_TasksQuickSearchCategory_label));

        return cats;
    }

    @Override
    public Collection<?> getElements() {
        return availableElements;
    }

    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {

        boolean wantTaskLibraries =
                isCategoryEnabled(categories, TASKLIBRARIES_QS_CATEGORY_ID);
       
        boolean wantTasks =
                isCategoryEnabled(categories, TASKLIBRARY_TASKS_QS_CATEGORY_ID);

        List<IndexerItem> retElements = new ArrayList<IndexerItem>();

        for (IndexerItem item : availableElements) {
            String type = item.getType();

            if (wantTaskLibraries
                    && Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEX_TYPE
                            .equals(type)) {
                retElements.add(item);
            } else if (wantTasks
                    && Xpdl2ResourcesPlugin.TASK_LIBRARY_TASK_INDEX_TYPE
                            .equals(type)) {
                retElements.add(item);
            }
        }

        return retElements;
    }

}

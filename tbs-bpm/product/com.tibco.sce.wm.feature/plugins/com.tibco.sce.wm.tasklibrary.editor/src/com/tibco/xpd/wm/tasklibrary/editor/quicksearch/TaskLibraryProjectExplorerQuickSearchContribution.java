/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.quicksearch;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractProjectExplorerQuickSearchContribution;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * Project explorer quick search contribution for Task Library stuff.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryProjectExplorerQuickSearchContribution extends
        AbstractProjectExplorerQuickSearchContribution {

    /**
     * 
     */
    public TaskLibraryProjectExplorerQuickSearchContribution() {
        super();
    }

    @Override
    public AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef) {
        return new TaskLibraryProjectExplorerQuickSearchContentProvider(partRef);
    }

    @Override
    public AbstractQuickSearchLabelProvider createLabelProvider(
            IWorkbenchPartReference partRef) {
        return new TaskLibraryProjectExplorerQuickSearchLabelProvider(partRef);
    }

    @Override
    protected Object getProjectExplorerSelectionElement(
            Object searchableContentElement) {
        //
        // Our searchable content is an indexerItem referencing a project
        // explorer content item - so dereference it.
        if (searchableContentElement instanceof IndexerItem) {
            String struri = ((IndexerItem) searchableContentElement).getURI();
            URI uri = URI.createURI(struri);

            if (uri != null) {
                EObject eo = ProcessUIUtil.getEObjectFrom(uri);
                if (eo != null) {
                    return eo;
                }
            }
        }

        return searchableContentElement;
    }

}

/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.quicksearchcontribution;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractProjectExplorerQuickSearchContribution;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * ProjectExplorerQuickSearchContribution
 * 
 * @author aallway
 * @since 3.1
 */
public class ProjectExplorerQuickSearchContribution extends
        AbstractProjectExplorerQuickSearchContribution {

    @Override
    public AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef) {
        return new ProjectExplorerQuickSearchContentProvider(partRef);
    }

    @Override
    public AbstractQuickSearchLabelProvider createLabelProvider(
            IWorkbenchPartReference partRef) {
        return new ProjectExplorerQuickSearchLabelProvider(partRef);
    }

    @Override
    protected Object getProjectExplorerSelectionElement(
            Object searchableContentElement) {
        // Our searchable content items are acutal objects that are content for
        // the project explorer so no dereferencing required.
        return searchableContentElement;
    }

}

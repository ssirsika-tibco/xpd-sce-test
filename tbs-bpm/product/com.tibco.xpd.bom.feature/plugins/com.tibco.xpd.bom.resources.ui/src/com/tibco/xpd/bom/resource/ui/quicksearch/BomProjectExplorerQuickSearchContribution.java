/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.bom.resource.ui.quicksearch;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractProjectExplorerQuickSearchContribution;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * Project Explorer quick search cotnribution for BOM related items.
 * 
 * @author aallway
 * @since 3.2
 */
public class BomProjectExplorerQuickSearchContribution extends
        AbstractProjectExplorerQuickSearchContribution {

    @Override
    public AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef) {
        return new BomProjectExplorerQuickSearchContentProvider(partRef);
    }

    @Override
    public AbstractQuickSearchLabelProvider createLabelProvider(
            IWorkbenchPartReference partRef) {
        return new BomProjectExplorerQuickSearchLabelProvider(partRef);
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
                EObject eObject =
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet().getEObject(uri, true);
                if (eObject != null) {
                    return eObject;
                }
            }
        }

        return null;
    }

}

package com.tibco.xpd.om.resources.ui.quicksearch;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractProjectExplorerQuickSearchContribution;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

public class OmProjectExplorerQuickSearchContribution extends
        AbstractProjectExplorerQuickSearchContribution {

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractProjectExplorerQuickSearchContribution#getProjectExplorerSelectionElement(java.lang.Object)
     * 
     * @param searchableContentElement
     * @return
     */
    @Override
    protected Object getProjectExplorerSelectionElement(
            Object searchableContentElement) {

        //
        // Our searchable content is an indexerItem referencing a project
        // explorer content item - so dereference it.
        if (searchableContentElement instanceof IndexerItem) {
            String struri = ((IndexerItem) searchableContentElement).getURI();
            URI uri = URI.createURI(struri);
            EObject eObject =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet().getEObject(uri, true);
            if (eObject != null) {
                return eObject;
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution#createContentProvider(org.eclipse.ui.IWorkbenchPartReference)
     * 
     * @param partRef
     * @return
     */
    @Override
    public AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef) {
        return new OmProjectExplorerQuickSearchContentProvider(partRef);
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution#createLabelProvider(org.eclipse.ui.IWorkbenchPartReference)
     * 
     * @param partRef
     * @return
     */
    @Override
    public AbstractQuickSearchLabelProvider createLabelProvider(
            IWorkbenchPartReference partRef) {
        return new OmProjectExplorerQuickSearchLabelProvider(partRef);
    }

}

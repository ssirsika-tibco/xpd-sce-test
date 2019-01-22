/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.EditorPart;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.internal.indexing.OMResourceIndexProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 * 
 * 
 * @author patkinso
 * @since 25 Jun 2012
 */
public class OmSubEditorQuickSearchContentProvider extends
        AbstractOmEditorQuickSearchContentProvider {

    /**
     * @param partRef
     */
    public OmSubEditorQuickSearchContentProvider(IWorkbenchPartReference partRef) {

        super(partRef);

        EditorPart editorPart = (EditorPart) partRef.getPart(false);
        WorkingCopy wc = getWorkingCopy(editorPart);

        if ((wc != null) && (editorPart != null)) {
            Organization orgModel =
                    getOrgModelEObject(wc, editorPart.getPartName());
            if (orgModel != null) {
                searchAndStoreRelevantElements(orgModel);
            }
        }

        initSearchCategories();
    }

    /**
     * @param wc
     * @param partName
     * @return
     */
    private Organization getOrgModelEObject(WorkingCopy wc, String partName) {

        if ((wc != null) && (partName != null)) {
            IndexerService service =
                    XpdResourcesPlugin.getDefault().getIndexerService();
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.set(OMResourceIndexProvider.DISPLAY_NAME, partName);
            Collection<IndexerItem> items =
                    service.query("com.tibco.xpd.om.resources.indexing.omIndexer", criteria); //$NON-NLS-1$
            Iterator<IndexerItem> it = items.iterator();
            if (it.hasNext()) {
                String uri = it.next().getURI();
                if (uri != null) {
                   ResourceSet resourceSet = 
                           wc.getEditingDomain().getResourceSet();
                    EObject eo =
                           resourceSet.getEObject(URI.createURI(uri), true);
                    if ((eo != null) && (eo instanceof Organization)) {
                        return (Organization) eo;
                    }
                }
            }

        }

        return null;
    }

    /**
     * @see com.tibco.xpd.om.resources.ui.quicksearch.AbstractOmEditorQuickSearchContentProvider#initSearchCategories()
     * 
     */
    @Override
    protected void initSearchCategories() {

        searchCategories.add(new QuickSearchPopupCategory(
                OM_POSITIONS_QS_CATEGORY_ID,
                Messages.OmEditorQuickSearchContentProvider_positions_menu));

        searchCategories
                .add(new QuickSearchPopupCategory(
                        OM_ORGANISATION_QS_CATEGORY_ID,
                        Messages.OmEditorQuickSearchContentProvider_organisations_menu));
    }

    /**
     * @see com.tibco.xpd.om.resources.ui.quicksearch.AbstractOmEditorQuickSearchContentProvider#storeRelevantElements(org.eclipse.emf.ecore.EObject)
     * 
     * @param element
     */
    @Override
    protected void storeRelevantElements(EObject element) {

        if (element instanceof OrgUnit) {
            availableOrgUnits.add((OrgUnit) element);
        } else if (element instanceof Position) {
            availablePositions.add((Position) element);
        }
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

        List<Object> searchSet = new ArrayList<Object>();

        if (isCategoryEnabled(categories, OM_ORG_UNITS_QS_CATEGORY_ID)) {
            searchSet.addAll(availableOrgUnits);
        }
        if (isCategoryEnabled(categories, OM_POSITIONS_QS_CATEGORY_ID)) {
            searchSet.addAll(availablePositions);
        }

        return searchSet;
    }

    /**
     * @see com.tibco.xpd.om.resources.ui.quicksearch.AbstractOmEditorQuickSearchContentProvider#getWorkingCopy(org.eclipse.ui.IEditorInput)
     * 
     * @param input
     * @return
     */
    @Override
    protected WorkingCopy getWorkingCopy(EditorPart editor) {

        IEditorInput input = editor.getEditorInput();
        return (WorkingCopy) Platform.getAdapterManager().getAdapter(input,
                WorkingCopy.class);
    }

}

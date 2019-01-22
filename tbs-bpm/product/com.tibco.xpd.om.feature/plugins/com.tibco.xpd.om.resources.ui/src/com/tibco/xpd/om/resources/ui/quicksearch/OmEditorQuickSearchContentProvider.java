/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.team.internal.ui.history.FileRevisionEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 * OM Editor quick search popup content provider.
 * 
 * @author patkinso
 * @since 22 Jun 2012
 */
public class OmEditorQuickSearchContentProvider extends
        AbstractOmEditorQuickSearchContentProvider {

    /**
     * @param partRef
     */
    public OmEditorQuickSearchContentProvider(IWorkbenchPartReference partRef) {

        super(partRef);

        EditorPart editor = (EditorPart) partRef.getPart(false);
        WorkingCopy wc = getWorkingCopy(editor);

        if (wc != null) {
            EObject root = wc.getRootElement();
            searchAndStoreRelevantElements(root);
        }

        initSearchCategories();
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
        return searchCategories;
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
        if (isCategoryEnabled(categories, OM_ORGANISATION_QS_CATEGORY_ID)) {
            searchSet.addAll(availableOrganisations);
        }

        return searchSet;
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#getElements()
     * 
     * @return
     */
    @Override
    public Collection<?> getElements() {
        return getElements(Collections.<QuickSearchPopupCategory> emptyList());
    }

    /**
     * @see com.tibco.xpd.om.resources.ui.quicksearch.AbstractOmEditorQuickSearchContentProvider#initSearchCategories()
     * 
     */
    @Override
    protected void initSearchCategories() {

        searchCategories
                .add(new QuickSearchPopupCategory(
                        OM_ORG_UNITS_QS_CATEGORY_ID,
                        Messages.OmEditorQuickSearchContentProvider_organisationUnits_menu));

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
        } else if (element instanceof Organization) {
            availableOrganisations.add((Organization) element);
        }
    }

    /**
     * @see com.tibco.xpd.om.resources.ui.quicksearch.AbstractOmEditorQuickSearchContentProvider#getWorkingCopy(org.eclipse.ui.part.EditorPart)
     * 
     * @param input
     * @return
     */
    @Override
    protected WorkingCopy getWorkingCopy(EditorPart editor) {

        IEditorInput input = editor.getEditorInput();

        WorkingCopy wc = null;
        if (input instanceof FileEditorInput) {
            wc =
                    (WorkingCopy) Platform.getAdapterManager()
                            .getAdapter(input, WorkingCopy.class);
        } else if (input instanceof FileRevisionEditorInput) {
            wc = (WorkingCopy) editor.getAdapter(WorkingCopy.class);
        }

        return wc;
    }

}

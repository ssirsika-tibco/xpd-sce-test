/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.EditorPart;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 * Implementations currently exist for both the Primary OM editor and the Sub OM
 * editor
 * 
 * @author patkinso
 * @since 22 Jun 2012
 */
abstract class AbstractOmEditorQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    protected List<QuickSearchPopupCategory> searchCategories =
            new ArrayList<QuickSearchPopupCategory>();

    protected static final String OM_ORGANISATION_QS_CATEGORY_ID =
            "subeditor.om.organisation.category"; //$NON-NLS-1$

    protected static final String OM_ORG_UNITS_QS_CATEGORY_ID =
            "subeditor.om.orgunits.category"; //$NON-NLS-1$

    protected static final String OM_POSITIONS_QS_CATEGORY_ID =
            "subeditor.om.positions.category"; //$NON-NLS-1$

    protected List<Organization> availableOrganisations =
            new ArrayList<Organization>();

    protected List<OrgUnit> availableOrgUnits = new ArrayList<OrgUnit>();

    protected List<Position> availablePositions = new ArrayList<Position>();

    /**
     * @param partRef
     */
    public AbstractOmEditorQuickSearchContentProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);
    }

    /**
     * @param input
     * @return
     */
    protected abstract WorkingCopy getWorkingCopy(EditorPart editor);

    /**
     * Set up the search categories that should be made available
     */
    protected abstract void initSearchCategories();

    /**
     * @param root
     */
    protected void searchAndStoreRelevantElements(EObject parent) {
        for (EObject element : parent.eContents()) {
            storeRelevantElements(element);
            searchAndStoreRelevantElements(element);
        }
    }

    /**
     * Store element in one of the available... collections if element is of a
     * searchable type
     * 
     * @param element
     */
    protected abstract void storeRelevantElements(EObject element);

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
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#getElements()
     * 
     * @return
     */
    @Override
    public Collection<?> getElements() {
        return getElements(Collections.<QuickSearchPopupCategory> emptyList());
    }

}

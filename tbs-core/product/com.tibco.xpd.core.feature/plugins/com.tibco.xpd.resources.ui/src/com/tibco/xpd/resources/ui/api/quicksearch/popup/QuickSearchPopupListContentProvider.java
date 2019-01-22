/**
 * SearchPopupContentProvider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch.popup;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.resources.ui.internal.quickSearch.QuickSearchContributionHelper;
import com.tibco.xpd.resources.ui.internal.quickSearch.QuickSearchElementAndContributor;


class QuickSearchPopupListContentProvider implements ITreeContentProvider {

    public static final String ENTER_PATTERN_ELEMENT =
            "enter.quick.search.pattern"; //$NON-NLS-1$

    public static final String NO_MATCHING_ELEMENT = "no.matching.element"; //$NON-NLS-1$

    private QuickSearchContributionHelper searchHelper = null;

    public Object[] getChildren(Object parentElement) {
        return null;
    }

    public Object getParent(Object element) {
        return null;
    }

    public boolean hasChildren(Object element) {
        return false;
    }

    public Object[] getElements(Object inputElement) {
        Object[] elements = null;

        if (searchHelper != null) {
            //
            // As far as we're concerned, we just get the current list of
            // matrching contributed elements from the helper.
            List<QuickSearchElementAndContributor> matching =
                    searchHelper.getCurrentMatchingElements();

            if (matching == null || matching.isEmpty()) {
                // There are 2 potential reasons for having no matching
                // elements, either there is no search criteria OR there are no
                // matching items.
                String searchString = searchHelper.getSearchString();

                if (searchString == null || searchString.length() == 0) {
                    elements = new Object[] { ENTER_PATTERN_ELEMENT };
                } else {
                    elements = new Object[] { NO_MATCHING_ELEMENT };
                }

            } else {
                elements = matching.toArray();
            }
        }

        return elements;
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput instanceof QuickSearchContributionHelper) {
            searchHelper = (QuickSearchContributionHelper) newInput;
        } else {
            searchHelper = null;
        }
    }
    
    
}

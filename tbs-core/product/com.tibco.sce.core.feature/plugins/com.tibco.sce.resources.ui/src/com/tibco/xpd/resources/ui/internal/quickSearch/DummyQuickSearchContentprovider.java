/**
 * DummyQuickSearchContentprovider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.quickSearch;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

class DummyQuickSearchContentprovider extends
        AbstractQuickSearchContentProvider {

    /**
     * @param partRef
     */
    public DummyQuickSearchContentprovider(IWorkbenchPartReference partRef) {
        super(partRef);
    }

    @Override
    public void dispose() {
    }

    @Override
    public Collection<QuickSearchPopupCategory> getCategories() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<Object> getElements() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {
        return Collections.EMPTY_LIST;
    }

}
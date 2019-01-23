/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * 
 * 
 * @author patkinso
 * @since 25 Jun 2012
 */
public class OmEditorQuickSearchContribution extends
        AbstractOmEditorQuickSearchContribution {

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution#createContentProvider(org.eclipse.ui.IWorkbenchPartReference)
     * 
     * @param partRef
     * @return
     */
    @Override
    public AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef) {
        return new OmEditorQuickSearchContentProvider(partRef);
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
        return new OmEditorQuickSearchLabelProvider(partRef);
    }

}

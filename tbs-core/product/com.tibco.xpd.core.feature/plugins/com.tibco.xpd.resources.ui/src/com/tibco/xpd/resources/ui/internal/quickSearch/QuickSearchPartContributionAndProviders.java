/**
 * QuickSearchPartContributionAndProviders.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.quickSearch;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution;

/**
 * CurrentPartContributionAndProviders
 * <p>
 * Class representing a contribution and its content/label providers for the
 * current active workbench part.
 * <p>
 * This is always disposed when the current workbench part changes.
 */
class QuickSearchPartContributionAndProviders {
    private AbstractQuickSearchPopupContribution contribution;

    private AbstractQuickSearchContentProvider contentProvider;

    private AbstractQuickSearchLabelProvider labelProvider;

    /**
     * @param contribution
     */
    public QuickSearchPartContributionAndProviders(
            IWorkbenchPartReference partRef,
            AbstractQuickSearchPopupContribution contribution) {
        super();

        this.contribution = contribution;

        this.contentProvider = contribution.createContentProvider(partRef);
        if (this.contentProvider == null) {
            this.contentProvider = new DummyQuickSearchContentprovider(partRef);
        }

        this.labelProvider = contribution.createLabelProvider(partRef);
        if (this.labelProvider == null) {
            this.labelProvider = new AbstractQuickSearchLabelProvider(partRef) {
            };
        }

    }

    public void dispose() {
        contentProvider.dispose();
    }

    /**
     * @return the contentprovider
     */
    public AbstractQuickSearchContentProvider getContentProvider() {
        return contentProvider;
    }

    /**
     * @return the contribution
     */
    public AbstractQuickSearchPopupContribution getContribution() {
        return contribution;
    }

    /**
     * @return the labelProvider
     */
    public AbstractQuickSearchLabelProvider getLabelProvider() {
        return labelProvider;
    }

}
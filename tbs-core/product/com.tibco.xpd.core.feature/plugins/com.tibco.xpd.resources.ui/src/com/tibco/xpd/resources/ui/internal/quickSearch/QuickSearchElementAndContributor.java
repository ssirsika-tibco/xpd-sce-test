/**
 * QuickSearchElementAndContributor.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.quickSearch;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution;



/**
 * ElementAndContributor
 * <p>
 * Class representing an element that can be searched and it's contributor.
 * 
 */
public class QuickSearchElementAndContributor {
    private Object contributedElement;

    private QuickSearchPartContributionAndProviders contributorAndProviders;

    /**
     * @param element
     * @param contributorAndProviders
     */
    public QuickSearchElementAndContributor(Object contributedElement,
            QuickSearchPartContributionAndProviders contributorAndProviders) {
        super();
        this.contributedElement = contributedElement;
        this.contributorAndProviders = contributorAndProviders;

    }

    /**
     * @return the element
     */
    public Object getContributedElement() {
        return contributedElement;
    }

    /**
     * @return the contributor
     */
    public AbstractQuickSearchPopupContribution getContributor() {
        return contributorAndProviders.getContribution();
    }

    /**
     * @return the label
     */
    public String getLabel() {
        String label =
                contributorAndProviders.getLabelProvider()
                        .getText(contributedElement);

        return label == null ? "" : label; //$NON-NLS-1$
    }

    /**
     * @return the element type name for the quick search element
     */
    public String getElementTypeName() {
        String typeName =
                contributorAndProviders.getLabelProvider()
                        .getElementTypeName(contributedElement);

        return typeName == null ? "" : typeName; //$NON-NLS-1$
    }

    /**
     * @return the path to the quick search element
     */
    public String getElementPath() {
        String path =
                contributorAndProviders.getLabelProvider()
                        .getElementPath(contributedElement);

        return path == null ? "" : path; //$NON-NLS-1$
    }

    /**
     * @return the image for the quick search element
     */
    public Image getImage() {
        Image img =
                contributorAndProviders.getLabelProvider()
                        .getImage(contributedElement);

        return img;
    }

    /**
     * Objects are equal if the contributed searchable object AND it's
     * contributer are the same.
     */
    public boolean equals(Object obj) {
        if (obj instanceof QuickSearchElementAndContributor) {
            QuickSearchElementAndContributor element =
                    (QuickSearchElementAndContributor) obj;

            if (element.contributedElement == this.contributedElement
                    && element.contributorAndProviders.getContribution() == this.contributorAndProviders
                            .getContribution()) {
                return true;
            }
        }
        return false;
    }
}
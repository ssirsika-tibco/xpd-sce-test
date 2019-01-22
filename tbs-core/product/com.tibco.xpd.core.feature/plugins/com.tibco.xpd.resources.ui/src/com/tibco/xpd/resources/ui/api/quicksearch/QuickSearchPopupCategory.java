/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * QuickSearchPopupCategory
 * <p>
 * Class describing a searchable item type category contributed to the
 * code>com.tibco.xpd.resources.ui.quickSearchViewCntribition</code> extension
 * point.
 * <p>
 * This category can be collection of child categories.
 * 
 * @author aallway
 * @since 3.1
 */
public class QuickSearchPopupCategory {
    private String id;

    private String label;

    private List<QuickSearchPopupCategory> children =
            new ArrayList<QuickSearchPopupCategory>();

    /**
     * s
     * 
     * @param id
     * @param label
     */
    public QuickSearchPopupCategory(String id, String label) {
        super();
        this.id = id;
        this.label = label;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuickSearchPopupCategory) {
            return id.equals(((QuickSearchPopupCategory) obj).id);
        }
        return false;
    }

    /**
     * @return the child categories
     */
    public List<QuickSearchPopupCategory> getChildren() {
        if (!children.isEmpty()) {
            Collections.sort(children,
                    new Comparator<QuickSearchPopupCategory>() {
                        public int compare(QuickSearchPopupCategory o1,
                                QuickSearchPopupCategory o2) {
                            return o1.getLabel().compareTo(o2.getLabel());
                        }
                    });
        }
        return children;
    }

    /**
     * add a child category.
     * 
     * @param childCategory
     */
    public void addChild(QuickSearchPopupCategory childCategory) {
        children.add(childCategory);
    }

    public void removeChild(QuickSearchPopupCategory childCategory) {
        children.remove(childCategory);
    }

}

/**
 * QuickSearchCategoryAndCheckstate.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.quickSearch;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 * QuickSearchCategoryAndCheckstate
 * <p>
 * Class to wrap a contributed quick search category and it's check enabled
 * state.
 */
public class QuickSearchCategoryAndCheckstate {
    private QuickSearchPopupCategory category;

    private QuickSearchCategoryAndCheckstate parent;

    private boolean checked = false;

    private List<QuickSearchCategoryAndCheckstate> children;

    public QuickSearchCategoryAndCheckstate(QuickSearchPopupCategory category) {
        this(category, null);
    }

    private QuickSearchCategoryAndCheckstate(QuickSearchPopupCategory category,
            QuickSearchCategoryAndCheckstate parent) {
        this.category = category;
        this.parent = parent;

        children = new ArrayList<QuickSearchCategoryAndCheckstate>();

        List<QuickSearchPopupCategory> catChildren = category.getChildren();
        if (catChildren != null) {
            for (QuickSearchPopupCategory child : catChildren) {
                children.add(new QuickSearchCategoryAndCheckstate(child, this));
            }
        }
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked
     *            the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * @return the category
     */
    public QuickSearchPopupCategory getCategory() {
        return category;
    }

    /**
     * @return the children
     */
    public List<QuickSearchCategoryAndCheckstate> getChildren() {
        return children;
    }

    /**
     * @return the parent
     */
    public QuickSearchCategoryAndCheckstate getParent() {
        return parent;
    }

}
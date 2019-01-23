/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.List;

import com.tibco.xpd.xpdl2.Activity;

/**
 * Abstract tree item used in mapping content providers representing rest
 * service mapped elements.
 *
 * @author jarciuch
 * @since 25 Mar 2015
 */
public abstract class RestMapperTreeItem {

    /**
     * @return the parent of the element or 'null' if it's a root element.
     */
    public abstract RestMapperTreeItem getParent();

    /**
     * @return a list of children elements.
     */
    public abstract List<?> getChildren();

    /**
     * @return 'true' if element has children.
     */
    public abstract boolean hasChildren();

    /**
     * @return label used for this element.
     */
    public abstract String getText();

    /**
     * @return the context activity.
     */
    public abstract Activity getActivity();

}

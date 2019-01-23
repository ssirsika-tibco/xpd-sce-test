/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments;

import java.util.Collection;

/**
 * A fragment category type.
 * 
 * @see IFragment
 * 
 * @author njpatel
 * 
 */
public interface IFragmentCategory extends IFragmentElement,
        IContainedFragmentElement {

    /**
     * Get the children of this category.
     * 
     * @return collection of children, empty collection if category has no
     *         children.
     */
    Collection<IFragmentElement> getChildren();
}

/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments;

/**
 * A fragment element type.
 * 
 * @author njpatel
 * 
 */
public interface IFragmentElement {

    /**
     * Get fragment id.
     * 
     * @return id
     */
    String getId();

    /**
     * Get fragment name. This is the untranslated fragment name.
     * 
     * @see #getNameLabel()
     * 
     * @return name
     */
    String getName();

    /**
     * Get the localized fragment name. For user fragments/categories this will
     * return the same value as {@link #getName()}.
     * 
     * @return localized name
     */
    String getNameLabel();

    /**
     * Get fragment description. This is the untranslated description.
     * 
     * @see #getDescriptionLabel()
     * 
     * @return description, empty string if no description is available.
     */
    String getDescription();

    /**
     * Get the localized fragment description. For user fragments/categories
     * this will return the same value as {@link #getDescription()}.
     * 
     * @return localized description, empty string if not description is
     *         available.
     */
    String getDescriptionLabel();

    /**
     * Get the fragment key. This can be used by the fragment provider to set a
     * unique id to identify this fragment or category.
     * 
     * @return key, can be <code>null</code> if no key is set.
     */
    String getKey();

    /**
     * Check if this fragment element is a system fragment/category.
     * 
     * @return <code>true</code> if system fragment/category, <code>false</code>
     *         otherwise.
     */
    boolean isSystem();

    /**
     * Dispose off any resources.
     */
    void dispose();

}

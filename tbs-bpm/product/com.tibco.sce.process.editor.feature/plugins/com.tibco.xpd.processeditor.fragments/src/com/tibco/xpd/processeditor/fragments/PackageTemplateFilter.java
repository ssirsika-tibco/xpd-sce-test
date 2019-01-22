/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.fragments;

import org.eclipse.jface.viewers.IFilter;

/**
 * Filter to not show up fragments in the fragments view when the package editor
 * is opened up.
 * 
 * @author rsomayaj
 * @since 3.3 (9 Apr 2010)
 */
public class PackageTemplateFilter implements IFilter {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    public boolean select(Object toTest) {
        return false;
    }

}

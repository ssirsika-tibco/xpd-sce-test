/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataFields;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.xpdl2.DataField;

/**
 * @author NWilson
 * 
 */
public class DataFieldFilter extends ViewerFilter {

    private boolean isCorrelation;

    /**
     * @param isCorrelation
     *            true for Correlation Data, false for Data Fields.
     */
    public DataFieldFilter(boolean isCorrelation) {
        this.isCorrelation = isCorrelation;
    }

    /**
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean ok = true;
        if (element instanceof DataField) {
            boolean correlation = ((DataField) element).isCorrelation();
            ok = isCorrelation == correlation;
        }
        return ok;
    }

}

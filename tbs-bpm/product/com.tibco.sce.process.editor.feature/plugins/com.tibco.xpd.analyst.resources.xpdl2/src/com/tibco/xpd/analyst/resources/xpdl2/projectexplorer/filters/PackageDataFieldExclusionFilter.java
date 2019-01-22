/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.DataFieldGroup;
import com.tibco.xpd.xpdl2.Package;

/**
 * PackageDataFieldExclusionFilter
 * <p>
 * Exclude process package level DataFields group (unless it is not empty).
 * 
 * @author bharge
 * @since 3.3 (8 Oct 2009)
 */
public class PackageDataFieldExclusionFilter extends ViewerFilter {

    public PackageDataFieldExclusionFilter() {
        super();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean include = true;
        if (element instanceof DataFieldGroup) {
            DataFieldGroup dataFieldGroup = (DataFieldGroup) element;
            if (dataFieldGroup.getParent() instanceof Package) {
                if (!dataFieldGroup.hasChildren()) {
                    include = false;
                }
            }
        }
        return include;
    }
}

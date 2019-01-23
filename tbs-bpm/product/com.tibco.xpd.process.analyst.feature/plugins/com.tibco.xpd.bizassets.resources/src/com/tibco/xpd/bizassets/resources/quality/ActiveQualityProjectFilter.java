/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.quality;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.bizassets.resources.wizard.QualityArchiveUtil;

/**
 * @author nwilson
 */
public class ActiveQualityProjectFilter extends ViewerFilter {

    /**
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean ok = true;
        if (element instanceof IProject) {
            ok = false;
            IProject project = (IProject) element;
            IProject active = QualityArchiveUtil
                    .getActiveQualityArchiveProject();
            if (project.equals(active)) {
                ok = true;
            }
        }
        return ok;
    }

}

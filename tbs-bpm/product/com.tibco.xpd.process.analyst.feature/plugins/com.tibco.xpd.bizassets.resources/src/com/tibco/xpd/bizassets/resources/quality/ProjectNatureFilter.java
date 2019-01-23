/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.quality;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * @author nwilson
 */
public class ProjectNatureFilter extends ViewerFilter {

    private Set<String> natures;

    /**
     * @param types
     */
    public ProjectNatureFilter(Set<String> natures) {
        this.natures = natures;
    }

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
            if (project.isOpen()) {
                try {
                    for (String nature : natures) {
                        if (project.hasNature(nature)) {
                            ok = true;
                            break;
                        }
                    }
                } catch (CoreException e) {

                }
            }
        }
        return ok;
    }

}

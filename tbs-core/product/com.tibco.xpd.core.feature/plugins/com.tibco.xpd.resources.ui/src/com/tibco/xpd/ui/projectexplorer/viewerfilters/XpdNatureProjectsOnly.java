/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Only include projects with XPD nature.
 * 
 * @author njpatel
 */
public class XpdNatureProjectsOnly extends ViewerFilter {

    private final boolean showClosedProjects;

    /**
     * Filter to include XPD nature projects only. This is equivalent of calling
     * {@link #XpdNatureProjectsOnly(boolean) XpdNatureProjectsOnly(true)}.
     */
    public XpdNatureProjectsOnly() {
        this(true);
    }

    /**
     * Filter to include XPD nature projects only.
     * 
     * @param showClosedProjects
     *            <code>true</code> to show closed project and
     *            <code>false</code> to hide them.
     * 
     * @since 3.3
     */
    public XpdNatureProjectsOnly(boolean showClosedProjects) {
        this.showClosedProjects = showClosedProjects;
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean ret = true;

        if (parentElement instanceof IWorkspaceRoot) {
            IProject project = null;

            // Check if the element is a project
            if (element instanceof IProject) {
                project = (IProject) element;

            } else if (element instanceof IAdaptable) {
                project = (IProject) ((IAdaptable) element)
                        .getAdapter(IProject.class);
            }

            if (project != null) {
                if (!showClosedProjects && !project.isOpen()) {
                    ret = false;
                } else {
                    // Check the nature of the project
                    try {
                        ret = project.hasNature(XpdConsts.PROJECT_NATURE_ID);
                    } catch (CoreException e) {
                        // Do nothing
                    }
                }
            } else {
                // This is not a project
                ret = false;
            }
        }

        return ret;
    }

}

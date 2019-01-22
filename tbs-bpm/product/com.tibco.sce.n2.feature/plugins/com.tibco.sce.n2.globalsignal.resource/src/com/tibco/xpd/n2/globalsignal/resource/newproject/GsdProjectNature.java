/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.newproject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;

/**
 * Global Signal Definition project nature. Even though we do not need any
 * builders etc, we need this nature so as to specifically identify a Global
 * Signal Definition project (can't identify on the basis of GSD asset because
 * that gets added even if we add a GSD special folder to a non-GSD project).
 * 
 * @author sajain
 * @since Apr 24, 2015
 */
public class GsdProjectNature implements IProjectNature {

    /**
     * GSD project nature ID.
     */
    public static final String ID = GsdResourcePlugin.PLUGIN_ID
            + ".gsdProjectNature"; //$NON-NLS-1$

    private IProject project;

    @Override
    public void configure() throws CoreException {

        // Nothing to do here.
    }

    @Override
    public void deconfigure() throws CoreException {

        // Nothing to do here.
    }

    @Override
    public IProject getProject() {

        return project;
    }

    @Override
    public void setProject(IProject project) {

        this.project = project;
    }

}

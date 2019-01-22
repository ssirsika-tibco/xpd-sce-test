/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.core.test;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.precompile.AbstractPreCompileContributor;

/**
 * Test class to mock enable pre-compile contribution.
 * 
 * <p>
 * PreCompileDAATest requires to test DAA contents for pre-compiled projects.
 * For a pre-compiled project the artifacts required for daa packaging will be
 * routed to .precompiled folder. This will be possible only when extension
 * point is bound to the thrown extension. So created a mock extension for the
 * framework to load contribution so that a resource can be distinguished from a
 * pre-compiled resource to that of a non-pre-compiled, so the daa generation
 * and packaging goes fine.
 * </p>
 * 
 * @author bharge
 * @since 16 Jun 2015
 */
public class TestEnablePrecompileContributor extends
        AbstractPreCompileContributor {

    /**
     * @see com.tibco.xpd.resources.precompile.AbstractPreCompileContributor#getSourceResource(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    public IResource getSourceResource(IProject project) {

        /*
         * Just a mock class for reading a contribution. No need to specify any
         * source resource
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.precompile.AbstractPreCompileContributor#getPrecompileTarget(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    public IPath getPrecompileTarget(IProject project) {

        /*
         * Just a mock class for reading a contribution. No need to specify any
         * target path
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.precompile.AbstractPreCompileContributor#precompile(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.resources.IFolder,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param preCompileFolder
     * @param progressMonitor
     * @return
     * @throws CoreException
     */
    @Override
    public IStatus precompile(IProject project, IFolder preCompileFolder,
            IProgressMonitor progressMonitor) throws CoreException {

        return Status.OK_STATUS;
    }

}

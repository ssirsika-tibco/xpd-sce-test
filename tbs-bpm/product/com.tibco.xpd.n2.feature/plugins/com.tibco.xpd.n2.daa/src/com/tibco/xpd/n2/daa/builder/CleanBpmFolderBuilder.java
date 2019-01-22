/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.daa.builder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.bom.gen.internal.BOMGeneratorHelper;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.ProjectDAAGenerator;
import com.tibco.xpd.n2.daa.internal.packager.DAAExportUtils;

/**
 * Builder which cleans .bpm folder of the project
 * 
 * @author kupadhya
 * 
 */
public class CleanBpmFolderBuilder extends IncrementalProjectBuilder {

    public static final String BUILDER_ID = Activator.PLUGIN_ID
            + ".cleanBpmFolderBuilder"; //$NON-NLS-1$

    @Override
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {
        return null;
    }

    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        cleanBpmFolder(monitor);
        cleanBOMJarsCacheFolder();
    }

    /**
     * @param monitor
     * @throws CoreException
     */
    private void cleanBpmFolder(IProgressMonitor monitor) throws CoreException {
        ProjectDAAGenerator daaGenerator = ProjectDAAGenerator.getInstance();
        daaGenerator.clean(getProject(), monitor);
    }

    /**
     * Cleans BOM jars cache.
     * 
     * @throws CoreException
     */
    private void cleanBOMJarsCacheFolder() throws CoreException {
        IFolder bomJarsFolder =
                DAAExportUtils.getCreateBOMJarsCacheFolder(getProject(), false);
        if (bomJarsFolder.isAccessible()) {
            List<IFile> allBOMFilesForProject =
                    BOMGeneratorHelper.getAllBOMFilesForProject(getProject());
            final Collection<IFile> cachedJars =
                    DAAExportUtils.getBomCachedJars(allBOMFilesForProject);
            cachedJars.addAll(DAAExportUtils
                    .getJavaServiceCachedJars(getProject()));
            cachedJars.addAll(DAAExportUtils
                    .getCachedJarsWithoutSource(bomJarsFolder));
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    for (IFile jarFile : cachedJars) {
                        jarFile.delete(false, new NullProgressMonitor());
                    }
                }
            }, new NullProgressMonitor());
        }
    }

}

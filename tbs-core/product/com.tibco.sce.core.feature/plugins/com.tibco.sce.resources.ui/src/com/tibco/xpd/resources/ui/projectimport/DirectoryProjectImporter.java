/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.ui.projectimport;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;

import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * 
 * 
 * @author patkinso
 * @since 23 Apr 2013
 */
public class DirectoryProjectImporter extends ProjectImporter {

    private boolean copyProject = false;

    protected File dotProjectFile;

    /**
     * Create a record for a project based on the info in the file.
     * 
     * @param dotProjectFile
     * @throws CoreException
     */
    public DirectoryProjectImporter(File dotProjectFile) throws CoreException {
        this.dotProjectFile = dotProjectFile;
        setProperties(dotProjectFile);
    }

    /**
     * @param dotProjectFile
     * @param copyFiles
     * @throws CoreException
     */
    public DirectoryProjectImporter(File dotProjectFile, boolean copyFiles)
            throws CoreException {
        this(dotProjectFile);
        this.copyProject = copyFiles;
    }

    /**
     * @param file
     * @return
     * @throws CoreException
     */
    private void setProperties(File file) throws CoreException {

        IPath path = new Path(dotProjectFile.getPath());

        /*
         * if the file is in the default location, use the directory name as the
         * project name
         */
        IWorkspace ws = ResourcesPlugin.getWorkspace();
        if (isProjectFileInDefaultLocation(path)) {
            setDescription(ws.newProjectDescription(getName()));
            setName(path.segment(path.segmentCount() - 2));
        } else {
            try {
                setDescription(ws.loadProjectDescription(path));
                setName(description.getName());
            } catch (CoreException e) {
                String message = "Could not determine project name"; //$NON-NLS-1$
                IStatus result =
                        new Status(
                                e.getStatus().getSeverity(),
                                com.tibco.xpd.resources.XpdResourcesPlugin.ID_PLUGIN,
                                message, e);
                throw new CoreException(result);
            }
        }
    }

    /**
     * @see com.tibco.xpd.resources.ui.projectimport.ProjectImporter#importProject(com.tibco.xpd.resources.util.SubProgressMonitorEx)
     * 
     * @param monitor
     * @return
     */
    @Override
    public IProject importProject(SubProgressMonitorEx monitor) {

        monitor.beginTask("", 100); //$NON-NLS-1$

        final IProject project =
                ResourcesPlugin.getWorkspace().getRoot().getProject(getName());

        /* import from file system */
        File sourceDir = null;
        if (copyProject) {
            // import project from location copying files - use default
            // project location for this workspace

            URI locationURI = getDescription().getLocationURI();
            if (locationURI != null) {
                sourceDir = new File(locationURI);
                setDescription(generateNewDescription());
            } else {
                // log error...
                // if location is null, project already exists in this location
                // or some error condition occured.
            }
        }

        /* Create & open new project */
        try {
            project.create(getDescription(), SubProgressMonitorEx
                    .createSubTaskProgressMonitor(monitor, 20));
            project.open(IResource.BACKGROUND_REFRESH, SubProgressMonitorEx
                    .createSubTaskProgressMonitor(monitor, 40));
        } catch (CoreException e) {
            // throw new InvocationTargetException(e);
        } finally {
            monitor.done();
        }

        /* copy project files from source */
        if (copyProject && sourceDir != null) {
            List<?> filesToImport =
                    FileSystemStructureProvider.INSTANCE.getChildren(sourceDir);

            StudioImportOperation operation =
                    new StudioImportOperation(project.getFullPath(), sourceDir,
                            FileSystemStructureProvider.INSTANCE, filesToImport);
            operation.setOverwriteResources(true); // need to overwrite
            operation.setCreateContainerStructure(false);
            try {
                operation.run(SubProgressMonitorEx
                        .createSubTaskProgressMonitor(monitor, 40));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return project;
    }

    /**
     * @return
     */
    private IProjectDescription generateNewDescription() {

        IWorkspace ws = ResourcesPlugin.getWorkspace();
        IProjectDescription desc = ws.newProjectDescription(getName());
        desc.setBuildSpec(getDescription().getBuildSpec());
        desc.setComment(getDescription().getComment());
        desc.setDynamicReferences(getDescription().getDynamicReferences());
        desc.setNatureIds(getDescription().getNatureIds());
        desc.setReferencedProjects(getDescription().getReferencedProjects());
        return desc;
    }

    /**
     * @return the copyProjects
     */
    public boolean isCopyProjects() {
        return copyProject;
    }

    /**
     * @param copyProjects
     *            the copyProjects to set
     */
    public void setCopyProjects(boolean copyProjects) {
        this.copyProject = copyProjects;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Archive handler for an MAA file.
 * 
 * @author njpatel
 */
public class MaaArchiveHandler {

    private final File maaFile;

    private final IWorkspaceRoot root;

    /**
     * Keeps track of files archived on the last create/save execution.
     */
    private final List<IPath> archivedFiles;

    private final boolean includeSVNResources;

    /**
     * Create an MAA archive.
     * <p>
     * This is the equivalent of calling
     * {@link #MaaArchiveHandler(File, boolean) MaaArchiveHandler(maaFile,
     * false)}.
     * </p>
     * 
     * @param maaFile
     *            MAA file to create
     */
    public MaaArchiveHandler(File maaFile) {
        this(maaFile, false);
    }

    /**
     * Create an MAA archive.
     * 
     * @param maaFile
     *            MAA file to create.
     * @param includeSVNResources
     *            <code>true</code> if team resources should also be archived.
     */
    public MaaArchiveHandler(File maaFile, boolean includeSVNResources) {
        this.maaFile = maaFile;
        this.includeSVNResources = includeSVNResources;
        archivedFiles = new ArrayList<IPath>();

        root = ResourcesPlugin.getWorkspace().getRoot();
    }

    public Collection<ProjectResource> open(IProgressMonitor monitor)
            throws CoreException {
        Set<ProjectResource> projectResources =
                new LinkedHashSet<ProjectResource>();
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(maaFile);
        } catch (Exception e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            RCPActivator.PLUGIN_ID,
                            String.format(Messages.ArchiveHandler_problemOpeningFile_error_message,
                                    maaFile.getAbsolutePath()), e));
        }
        monitor.beginTask("", zipFile.size()); //$NON-NLS-1$
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        archivedFiles.clear();
        try {
            while (entries.hasMoreElements()) {
                IResource resource = null;
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    IPath path = new Path(entry.getName());
                    if (path.segmentCount() == 1) {
                        // Create project resource
                        ProjectResource prResource =
                                createProjectResource(entry.getName());
                        monitor.setTaskName(entry.getName());
                        projectResources.add(prResource);
                    } else {
                        resource = createFolder(entry, projectResources);
                    }
                } else {
                    resource = createFile(zipFile, entry, projectResources);
                    if (resource != null) {
                        archivedFiles.add(resource.getFullPath());
                    }
                }

                monitor.worked(1);
            }
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    RCPActivator.getDefault().getLogger()
                            .error(e, "Problem saving the archive."); //$NON-NLS-1$
                }
            }
            monitor.done();
        }
        return projectResources;
    }

    /**
     * @param zipFile
     * @param entry
     * @param projectResources
     * @return
     * @throws CoreException
     */
    private IResource createFile(ZipFile zipFile, ZipEntry entry,
            Set<ProjectResource> projectResources) throws CoreException {
        IFile file = root.getFile(new Path(entry.getName()));

        if (!file.getProject().isAccessible()) {
            ProjectResource prRes =
                    createProjectResource(file.getProject().getName());
            projectResources.add(prRes);
        }

        if (!file.getParent().exists()) {
            ProjectUtil.createFolder(file.getParent(),
                    false,
                    new NullProgressMonitor());
        }
        InputStream inputStream = null;
        try {
            inputStream = zipFile.getInputStream(entry);
            if (!file.exists()) {
                file.create(inputStream, false, new NullProgressMonitor());
            } else {
                file.setContents(inputStream,
                        IResource.FORCE,
                        new NullProgressMonitor());
            }
            return file;
        } catch (IOException e) {
            throw new CoreException(new Status(IStatus.ERROR,
                    RCPActivator.PLUGIN_ID,
                    Messages.ArchiveHandler_openProjectFailed_error_message, e));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
    }

    /**
     * @param entry
     * @param projectResources
     * @return
     * @throws CoreException
     */
    private IResource createFolder(ZipEntry entry,
            Set<ProjectResource> projectResources) throws CoreException {
        IFolder folder = root.getFolder(new Path(entry.getName()));

        if (!folder.getProject().isAccessible()) {
            ProjectResource prRes =
                    createProjectResource(folder.getProject().getName());
            projectResources.add(prRes);
        }

        if (!folder.exists()) {
            ProjectUtil.createFolder(folder, false, null);
        }
        return folder;
    }

    /**
     * Create and open the project resource for the given project name.
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    private ProjectResource createProjectResource(String projectName)
            throws CoreException {
        if (projectName != null) {
            ProjectResource prRes =
                    RCPResourceFactory.createProjectResource(projectName);
            prRes.open(new NullProgressMonitor());
            return prRes;
        }
        return null;
    }

    /**
     * Get the project names contained in this MAA File.
     * 
     * @return
     * @throws ZipException
     * @throws IOException
     */
    public List<String> getProjectNames() throws ZipException, IOException {
        List<String> names = new ArrayList<String>();

        ZipFile zip = new ZipFile(maaFile);
        try {
            Enumeration<? extends ZipEntry> entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();

                if (zipEntry.isDirectory()) {
                    IPath path = new Path(zipEntry.getName());
                    if (path.segmentCount() == 1) {
                        names.add(path.lastSegment());
                    }
                }
            }
        } finally {
            zip.close();
        }

        return names;
    }

    /**
     * @param projectResources
     */
    public void archive(Collection<ProjectResource> projectResources,
            IProgressMonitor monitor) throws CoreException {

        Assert.isNotNull(projectResources, "No project resources provided"); //$NON-NLS-1$

        monitor.beginTask(Messages.ArchiveHandler_savingMaa_progress_shortdesc,
                20);

        List<IProject> projects =
                new ArrayList<IProject>(projectResources.size());
        for (ProjectResource prRes : projectResources) {
            IProject project = prRes.getProject();
            if (project != null) {
                projects.add(project);
            }
        }

        if (!projects.isEmpty()) {

            try {
                createArchive(projects, maaFile, new SubProgressMonitorEx(
                        monitor, 20));
            } catch (InvocationTargetException e) {

                // Delete the created file.
                if (maaFile.exists()) {
                    maaFile.delete();
                }

                if (e.getCause() instanceof CoreException) {
                    throw (CoreException) e.getCause();
                } else {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    RCPActivator.PLUGIN_ID,
                                    String.format(Messages.MaaArchiveHandler_problemSaving_error_shortdesc,
                                            maaFile.getName()), e));
                }
            } catch (InterruptedException e) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                RCPActivator.PLUGIN_ID,
                                String.format(Messages.MaaArchiveHandler_problemSaving_error_shortdesc,
                                        maaFile.getName()), e));
            }
        }
    }

    @SuppressWarnings("restriction")
    private void createArchive(List<IProject> projects, File file,
            IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException {
        ArchiveExportOperation op =
                new ArchiveExportOperation(projects, file.getAbsolutePath(),
                        includeSVNResources);
        op.setCreateLeadupStructure(true);
        op.setUseCompression(false);
        op.setUseTarFormat(false);

        op.run(monitor);
    }
}

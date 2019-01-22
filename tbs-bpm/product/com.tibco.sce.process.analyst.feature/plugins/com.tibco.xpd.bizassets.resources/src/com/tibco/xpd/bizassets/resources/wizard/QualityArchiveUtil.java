/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bizassets.resources.BusinessassetsPlugin;
import com.tibco.xpd.bizassets.resources.internal.Messages;
import com.tibco.xpd.bizassets.resources.quality.QualityArchiveNature;

/**
 * @author nwilson
 */
public final class QualityArchiveUtil {

    /**
     * Private constructor.
     */
    private QualityArchiveUtil() {
    }

    /** Buffer size for file copying. */
    private static final int FILE_BUFFER_SIZE = 1024;

    /** Preference store id for active project. */
    private static final String ACTIVE_ARCHIVE_PROJECT = "activeProject"; //$NON-NLS-1$

    /**
     * @param folder
     *            The target folder.
     * @param filename
     *            The archive to extract.
     * @throws CoreException
     *             If there was a problem accessing an Eclipse resource.
     * @throws IOException
     *             If there was a problem extracting the files.
     * @throws FileNotFoundException
     *             If the target folder was invalid.
     */
    public static void extractArchive(final IContainer folder, String filename)
            throws CoreException {
        InputStream inputStream =
                QualityArchiveUtil.class.getResourceAsStream(filename);
        if (inputStream != null && folder != null) {
            try {
                ZipInputStream input = new ZipInputStream(inputStream);
                ZipEntry entry;
                while ((entry = input.getNextEntry()) != null) {
                    if (!entry.isDirectory()) {
                        File file =
                                new File(folder.getLocation().toFile(), entry
                                        .getName());
                        file.getParentFile().mkdirs();
                        FileOutputStream output = new FileOutputStream(file);
                        byte[] buffer = new byte[FILE_BUFFER_SIZE];
                        int bytesRead = 0;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                        output.close();
                    }
                }
                input.close();
                ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

                    public void run(IProgressMonitor monitor)
                            throws CoreException {
                        folder.refreshLocal(IResource.DEPTH_INFINITE, monitor);
                    }

                }, null);
            } catch (IOException e) {
                throw new CoreException(new Status(IStatus.ERROR,
                        BusinessassetsPlugin.ID, e.getMessage(), e));
            }
        }
    }

    /**
     * @return The active quality archive project.
     */
    public static IProject getActiveQualityArchiveProject() {
        IProject project = null;
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IPreferenceStore store =
                BusinessassetsPlugin.getDefault().getPreferenceStore();
        String projectName = store.getString(ACTIVE_ARCHIVE_PROJECT);
        if (projectName != null && projectName.length() != 0) {
            IProject p = root.getProject(projectName);
            if (p.exists() && p.isOpen()) {
                project = p;
            }
        }
        // If there is no active project set to the first found.
        if (project == null) {
            IProject[] projects = root.getProjects();
            for (IProject current : projects) {
                try {
                    if (current.isOpen()
                            && current
                                    .hasNature(QualityArchiveNature.NATURE_ID)) {
                        project = current;
                        setActiveQualityArchiveProject(project);
                        break;
                    }
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        return project;
    }

    /**
     * @param project
     *            The project to set as the active quality process archive.
     */
    public static void setActiveQualityArchiveProject(IProject project) {
        IPreferenceStore store =
                BusinessassetsPlugin.getDefault().getPreferenceStore();
        store.setValue(ACTIVE_ARCHIVE_PROJECT, project.getName());
    }

    /**
     * @param folder
     * @param project
     * @throws CoreException
     */
    public static void copyQualityProject(final IFolder folder,
            final IProject project) throws CoreException {
        final IPath destinationFolder = folder.getFullPath();
        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

            public void run(IProgressMonitor monitor) throws CoreException {
                Boolean overwrite = null;
                for (IResource child : project.members()) {
                    String childName = child.getName();
                    if (!".project".equals(childName)) { //$NON-NLS-1$
                        IPath destination = destinationFolder.append(childName);
                        IResource current = null;
                        if (child instanceof IFile) {
                            current = folder.getFile(childName);
                        } else if (child instanceof IFolder) {
                            current = folder.getFolder(childName);
                        }
                        if (current.exists()) {
                            if (overwrite == null) {
                                overwrite =
                                        MessageDialog
                                                .openQuestion(
                                                        null,
                                                        Messages.QualityArchiveUtil_ConfirmOverwriteTitle,
                                                        Messages.QualityArchiveUtil_ConfirmOverwriteMessage);
                            }
                            if (overwrite == true) {
                                current.delete(true, monitor);
                                child.copy(destination, true, monitor);
                            }
                        } else {
                            child.copy(destination, true, monitor);
                        }
                    }
                }
            }
        }, null);
    }

    /**
     * @return
     */
    public static List<IProject> getQualityArchiveProjects() {
        List<IProject> projects = new ArrayList<IProject>();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] allProjects = root.getProjects();
        for (IProject current : allProjects) {
            try {
                if (current.isOpen()
                        && current.hasNature(QualityArchiveNature.NATURE_ID)) {
                    projects.add(current);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return projects;
    }

}

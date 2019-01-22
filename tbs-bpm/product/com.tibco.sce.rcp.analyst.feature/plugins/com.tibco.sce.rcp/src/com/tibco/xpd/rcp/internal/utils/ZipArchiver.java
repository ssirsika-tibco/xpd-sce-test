/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;

/**
 * Create a zip archive containing all the projects given. This will include all
 * hidden, phantom and team related resources.
 * 
 * @author njpatel
 * 
 */
public class ZipArchiver {

    private final File archiveFile;

    /**
     * Keeps track of files archived on the last create/save execution.
     */
    private final List<String> archivedFiles;

    public ZipArchiver(File archiveFile) {
        this.archiveFile = archiveFile;
        archivedFiles = new ArrayList<String>();
    }

    /**
     * Archive the given projects into the archive file.
     * 
     * @param projects
     *            the projects to archive.
     * @throws CoreException
     * @throws FileNotFoundException
     */
    public void createArchive(IProject[] projects) throws CoreException,
            FileNotFoundException {
        Assert.isTrue(projects != null && projects.length > 0,
                "No projects provided"); //$NON-NLS-1$
        archivedFiles.clear();
        ZipOutputStream os = null;
        try {
            os =
                    new ZipOutputStream(new BufferedOutputStream(
                            new FileOutputStream(archiveFile)));

            final ZipOutputStream zos = os;

            for (IProject project : projects) {

                /*
                 * Make sure the project is in sync with the file system.
                 */
                if (!project.isSynchronized(IResource.DEPTH_INFINITE)) {
                    project.refreshLocal(IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());
                }

                final String archiveName = project.getName();
                project.accept(new IResourceVisitor() {

                    @Override
                    public boolean visit(IResource resource)
                            throws CoreException {
                        if (resource instanceof IFile) {
                            IFile file = (IFile) resource;

                            IPath projectRelativePath =
                                    file.getProjectRelativePath();
                            IPath path =
                                    new Path(archiveName)
                                            .append(projectRelativePath);
                            ZipEntry entry = new ZipEntry(path.toString());
                            try {
                                addToOutputStream(zos,
                                        file.getContents(),
                                        entry);
                                archivedFiles.add(projectRelativePath
                                        .toString());
                            } catch (IOException e) {
                                throw new CoreException(
                                        new Status(
                                                IStatus.ERROR,
                                                RCPActivator.PLUGIN_ID,
                                                Messages.ArchiveHandler_problemArchivingProject_error_message,
                                                e));
                            }
                            return false;
                        } else if (resource instanceof IFolder) {
                            /*
                             * If this folder contains no files then archive it
                             * as an empty folder
                             */
                            if (((IFolder) resource).members().length == 0) {
                                ZipEntry entry =
                                        new ZipEntry(
                                                new Path(archiveName)
                                                        .append(resource
                                                                .getProjectRelativePath())
                                                        .toString()
                                                        + IPath.SEPARATOR);
                                try {
                                    zos.putNextEntry(entry);
                                } catch (IOException e) {
                                    throw new CoreException(
                                            new Status(
                                                    IStatus.ERROR,
                                                    RCPActivator.PLUGIN_ID,
                                                    Messages.ArchiveHandler_problemArchivingProject_error_message,
                                                    e));
                                }
                            }
                        }
                        return true;
                    }

                },
                        IResource.DEPTH_INFINITE,
                        IContainer.INCLUDE_TEAM_PRIVATE_MEMBERS
                                | IContainer.INCLUDE_HIDDEN
                                | IContainer.INCLUDE_PHANTOMS);
            }
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                // DO nothing
            }
        }
    }

    /**
     * Add the input stream as a zip entry into the zip output stream.
     * 
     * @param output
     * @param input
     * @param ze
     * @throws IOException
     */
    private void addToOutputStream(ZipOutputStream output, InputStream input,
            ZipEntry ze) throws IOException {

        try {
            output.putNextEntry(ze);
        } catch (ZipException zipEx) {
            // This entry already exists. So, go with the first one.
            input.close();
            return;
        }

        copy(output, input);

        output.closeEntry();
        input.close();
    }

    /**
     * Copy from input stream into the output stream.
     * 
     * @param os
     * @param is
     * @throws IOException
     */
    private void copy(OutputStream os, InputStream is) throws IOException {
        byte[] buffer = new byte[8192];
        int numBytes = -1;

        while ((numBytes = is.read(buffer)) > 0) {
            os.write(buffer, 0, numBytes);
        }
    }
}

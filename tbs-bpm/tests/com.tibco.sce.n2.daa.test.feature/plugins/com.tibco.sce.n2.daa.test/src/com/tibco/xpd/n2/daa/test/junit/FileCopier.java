/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Utility class used to copy the IFile to the different locations. Currently
 * the locationTypes
 * 
 * @author Jan Arciuchiewicz
 */
public class FileCopier {

    private final IFile srcFile;

    private final DestinationLocationType type;

    private IFile outWorkspaceIFile;

    private File outSystemFile;
    
    private String suffix;

    
    /**
     * Creates a copier.
     * 
     * @param srcFile
     *            the source workspace file to be copied.
     * @param type
     *            type of the location {@link DestinationLocationType}
     * @param outContainerPath
     *            the path to the destination container. It should be project
     *            relative path (the source file's project will be used) or
     *            absolute fileSystem path.
     */
    public FileCopier(IFile srcFile, DestinationLocationType type,
            String outContainerPath, String suffix) {
        this.srcFile = srcFile;
        this.type = type;
        this.suffix = suffix;
        switch (type) {
        case PROJECT:
            IPath projectPath = srcFile.getProject().getFullPath();
            IPath workspacePath =
                    projectPath.append(outContainerPath).append(srcFile
                            .getName());
            outWorkspaceIFile =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getFile(workspacePath);
            break;
        case SYSTEM_FILE:
            IPath systemPath =
                    new Path(outContainerPath).append(srcFile.getName());
            if (suffix != null) {
                String fileExtension = systemPath.getFileExtension();
                String fileName = systemPath.lastSegment();
                fileName =
                        fileName.replace("." + fileExtension, suffix + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + fileExtension);
                systemPath = systemPath.removeLastSegments(1).append(fileName);
            }
            outSystemFile = new File(systemPath.toOSString());
            break;
        default:
            Assert.isLegal(false, "Unknown LocationType."); //$NON-NLS-1$
        }
    }

    /**
     * Checks if destination exists.
     * 
     * @return true if destination exists.
     */
    public boolean destinationExists() {
        switch (type) {
        case PROJECT:
            return outWorkspaceIFile.exists();
        case SYSTEM_FILE:
            return outSystemFile.exists();
        }
        throw new AssertionError("Unknown LocationType."); //$NON-NLS-1$
    }

    /**
     * Copies source file to the destination.
     * 
     * @throws CoreException
     *             if there were problems during copy operation.
     */
    public void copy() throws CoreException {
        if (srcFile.exists()) {
            if (type == DestinationLocationType.PROJECT) {
                copyToWorkspace();
            } else if (type == DestinationLocationType.SYSTEM_FILE) {
                copyToSystem();
            } else {
                throw new AssertionError("Unknown LocationType."); //$NON-NLS-1$
            }
        }
    }

    /**
     * Returns the String representation of destination typically in form of a
     * path. The output is dependent of location type.
     * 
     * @return the String representation of destination.
     */
    public String getOutputPath() {
        switch (type) {
        case PROJECT:
            return outWorkspaceIFile.getFullPath().toPortableString();
        case SYSTEM_FILE:
            IPath systemPath = new Path(outSystemFile.getAbsolutePath());
            if (suffix != null) {
                String fileExtension = systemPath.getFileExtension();
                String fileName = systemPath.lastSegment();
                fileName =
                        fileName.replace("." + fileExtension, suffix + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + fileExtension);
                systemPath = systemPath.removeLastSegments(1).append(fileName);
            }
            return systemPath.toOSString();
        }
        throw new AssertionError("Unknown LocationType."); //$NON-NLS-1$
    }

    public IPath getInputPath() {
        return srcFile.getFullPath();
    }

    private void copyToWorkspace() throws CoreException {
        IContainer parent = outWorkspaceIFile.getParent();
        if (parent.getProject().isAccessible()) {
            ProjectUtil.createFolder(parent,
            /* derived */false, new NullProgressMonitor());
            if (srcFile.exists()) {
                outWorkspaceIFile.delete(true, new NullProgressMonitor());
            }
            IPath fullPath = outWorkspaceIFile.getFullPath();
            if (suffix != null) {
                String fileExtension =
                        outWorkspaceIFile.getFullPath().getFileExtension();
                String fileName = fullPath.lastSegment();
                fileName = fileName.replace("." + fileExtension, suffix + "." //$NON-NLS-1$ //$NON-NLS-2$
                        + fileExtension);
                fullPath =
                        outWorkspaceIFile.getFullPath().removeLastSegments(1)
                                .append(fileName);
            }
            srcFile.copy(fullPath,
            /* force sync */true, new NullProgressMonitor());
        }
    }

    private void copyToSystem() throws CoreException {
        try {
            File parent = outSystemFile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (parent.exists()) {
                
                copyFile(srcFile.getLocation().toFile(), outSystemFile);
            }
        } catch (Exception e) {
            // Convert to CoreException
            String message =
                    String
                            .format("Exception was thrown while copy of: '%1$s' to: '%1$s'.", //$NON-NLS-1$
                                    srcFile.getFullPath().toPortableString(),
                                    outSystemFile.toString());
            throw new CoreException(new Status(IStatus.ERROR,
                    Activator.PLUGIN_ID, message, e));
        }

    }

    protected static void copyFile(File in, File out) throws IOException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            // magic number for Windows, 64Mb - 32Kb)
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = inChannel.size();
            long position = 0;
            while (position < size) {
                position +=
                        inChannel.transferTo(position, maxCount, outChannel);
            }
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

}
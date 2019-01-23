/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.rest.json.schema.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.ui.documentation.DocGenUtil;
import com.tibco.xpd.ui.importexport.exportwizard.ExportSubTask;

/**
 * Copy the resources, ie. stylesheet and images, for the exported JSON
 * documentation
 * 
 * 
 * @author kthombar (taken from Nilay's ResourceCopier class for coping
 *         necessary files during Process Doc export.)
 * @since June 04, 2015
 */
public class ResourceCopier implements ExportSubTask {

    public static final String IMAGES_FOLDER = "images"; //$NON-NLS-1$

    public static final String JSON_FILE_ICON = "JSONFileIcon.png"; //$NON-NLS-1$

    /**
     * Copies the specified resources from the source to the target folder.
     * 
     * 
     * @author kthombar (taken from Nilay's ResourceCopier class for coping
     *         necessary files during Process Doc export.)
     * @since TBS 4.0.0 (June 4, 2015)
     */
    private static class ResourceCopyFile {
        String sourcePath;

        String targetPath;

        boolean overwrite;

        /**
         * @param sourcePath
         * @param targetPath
         * @param overwrite
         */
        public ResourceCopyFile(String sourcePath, String targetPath,
                boolean overwrite) {
            super();
            this.sourcePath = sourcePath;
            this.targetPath = targetPath;
            this.overwrite = overwrite;
        }

        /**
         * Performs the copy of resources.
         * 
         * @param baseTargetFolder
         * @throws CoreException
         */
        public void copyResource(File baseTargetFolder) throws CoreException {
            if (!sourcePath.endsWith("/")) { //$NON-NLS-1$
                // Single file copy.

                String targetFolderName = null;
                String targetName = null;

                int idx = targetPath.lastIndexOf("/"); //$NON-NLS-1$
                if (idx >= 0) {
                    targetFolderName = targetPath.substring(0, idx);
                    targetName = targetPath.substring(idx + 1);
                } else {
                    targetName = targetPath;
                }

                File targetFolder;
                if (targetFolderName != null && targetFolderName.length() > 0) {
                    targetFolder =
                            createFolder(baseTargetFolder, targetFolderName);
                } else {
                    targetFolder = baseTargetFolder;
                }

                if (targetName != null && targetName.length() > 0) {
                    try {
                        copyFile(sourcePath, new File(targetFolder, targetName));
                    } catch (IOException e) {
                        generateCoreException(e);
                    }
                }
            } else {
                /*
                 * Folder copy.
                 */
                File targetFolder = createFolder(baseTargetFolder, targetPath);

                URL sourceURL =
                        getClass().getResource(sourcePath.substring(0,
                                sourcePath.length() - 1));

                try {
                    URL fileURL = FileLocator.toFileURL(sourceURL);

                    File sourceFolder = new File(fileURL.toURI());

                    if (sourceFolder.isDirectory()) {
                        copyFolder(sourceFolder, targetFolder);
                    }
                } catch (Exception e) {
                    generateCoreException(e);
                }
            }

            return;
        }

        /**
         * Performs the copy of folder
         * 
         * @param baseTargetFolder
         * @throws CoreException
         */
        private void copyFolder(File sourceFolder, File targetFolder)
                throws CoreException {

            for (File file : sourceFolder.listFiles()) {
                if (!file.isHidden()) {
                    if (file.isDirectory()) {
                        File targetSubFolder =
                                createFolder(targetFolder, file.getName());

                        copyFolder(file, targetSubFolder);

                    } else if (file.isFile()) {
                        File targetFile =
                                new File(targetFolder, file.getName());

                        if (overwrite || !targetFile.exists()) {
                            try {
                                FileOutputStream outStream = null;
                                InputStream inStream = null;
                                try {
                                    inStream = new FileInputStream(file);

                                    outStream =
                                            new FileOutputStream(targetFile);

                                    for (int i = inStream.read(); i >= 0; i =
                                            inStream.read()) {
                                        outStream.write(i);
                                    }

                                } catch (Exception e) {
                                    generateCoreException(e);
                                } finally {
                                    // Close streams
                                    try {
                                        if (inStream != null) {
                                            inStream.close();
                                        }

                                        if (outStream != null) {
                                            outStream.close();
                                        }
                                    } catch (Exception e) {
                                        generateCoreException(e);
                                    }
                                }

                            } catch (Exception e) {
                                generateCoreException(e);
                            }
                        }
                    }
                }
            }

            return;
        }

        /**
         * Copies the file with the given source file to the destination file
         * given.
         * 
         * @param szSourceFile
         *            Location of source file
         * @param destinationFile
         *            The destination file to create
         * @throws IOException
         *             If file not found, or exception while copying contents of
         *             file.
         */
        private void copyFile(String szSourceFile, File destinationFile)
                throws IOException {

            if (szSourceFile != null && destinationFile != null) {
                FileOutputStream outStream = null;
                InputStream inStream = null;
                try {
                    inStream = getClass().getResourceAsStream(szSourceFile);
                    outStream = new FileOutputStream(destinationFile);

                    for (int i = inStream.read(); i >= 0; i = inStream.read()) {
                        outStream.write(i);
                    }

                } finally {
                    // Close streams
                    try {
                        if (inStream != null) {
                            inStream.close();
                        }

                        if (outStream != null) {
                            outStream.close();
                        }
                    } catch (IOException e) {
                        ; // ignore
                    }
                }
            }
        }

        /**
         * Create a folder with the given name under the parent location.
         * 
         * @param parent
         *            Location to create the new folder
         * @param folderName
         *            The name of the folder to create
         * @return <code>File</code> if the folder was created successfully,
         *         <b>null</b> otherwise.
         * @throws CoreException
         *             will be thrown if the folder fails to create, or a file
         *             with the same name already exists at the given location.
         */
        private File createFolder(File parent, String folderName)
                throws CoreException {
            File folder = null;

            if (parent != null && folderName != null) {
                folder = new File(parent, folderName);

                // If folder doesn't exist then create it
                if (!folder.exists()) {
                    // If failed to create folder then raise exception
                    if (!folder.mkdirs()) {
                        throw generateCoreException(String
                                .format(Messages.ResourceCopier_FailedToCreateFolderError_msg,
                                        folder.toString()));
                    }
                } else if (!folder.isDirectory()) {
                    // A file with the same name already exists so raise
                    // exception
                    throw generateCoreException(String
                            .format(Messages.ResourceCopier_UnableToCreateFolderError_msg,
                                    folder.toString()));
                }
            }

            return folder;
        }

        /**
         * Wrap the given throwable object into a <code>CoreException</code>
         * 
         * @param throwable
         * @return
         */
        private CoreException generateCoreException(Throwable throwable) {
            return new CoreException(new Status(IStatus.ERROR,
                    RestSchemaUiPlugin.PLUGIN_ID, IStatus.OK,
                    throwable.getLocalizedMessage(), throwable));
        }

        /**
         * Wrap the given error message into a <code>CoreException</code>
         * 
         * @param errMsg
         * @return
         */
        private CoreException generateCoreException(String errMsg) {
            return new CoreException(new Status(IStatus.ERROR,
                    RestSchemaUiPlugin.PLUGIN_ID, IStatus.OK, errMsg, null));
        }

    }

    /**
     * static array of 'ResourceCopyFile' which contains the details of files to
     * be copied.
     */
    private static final ResourceCopyFile copyFiles[] =
            {
                    new ResourceCopyFile(
                            "/style/jsondoc.css", "style/jsondoc.css", false), //$NON-NLS-1$ //$NON-NLS-2$
                    new ResourceCopyFile(
                            "/style/printjsondoc.css", "style/printjsondoc.css", false), //$NON-NLS-1$ //$NON-NLS-2$
                    new ResourceCopyFile(
                            "/icons/tick.gif", IMAGES_FOLDER + "/tick.gif", true), //$NON-NLS-1$ //$NON-NLS-2$
                    new ResourceCopyFile(
                            "/icons/" + JSON_FILE_ICON, IMAGES_FOLDER + "/" + JSON_FILE_ICON, true), //$NON-NLS-1$ //$NON-NLS-2$                 
            };

    private Set<File> alreadyProcessedFolders = new HashSet<File>();

    public ResourceCopier() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.importexport.export.wizards.SubTask#perform(org.eclipse
     * .core.runtime.SubProgressMonitor, org.eclipse.core.resources.IFile,
     * java.io.File)
     */
    @Override
    public void perform(SubProgressMonitor monitor, IFile inputFile,
            File outputFile) throws CoreException {
        if (outputFile != null) {

            if (monitor != null) {
                monitor.beginTask("", 1); //$NON-NLS-1$
                monitor.subTask(inputFile.getName() + ": " //$NON-NLS-1$
                        + Messages.ResourceCopier_CreatingStylesheetProgressMonitor_msg);
            }

            try {

                // Check if the resources have already been copied to this
                // destination - if so then no point doing it again
                File targetFolder = outputFile.getParentFile();
                if (alreadyProcessedFolders.contains(targetFolder)) {
                    return;
                }
                doResourceCopy(targetFolder, copyFiles);

                // Create images folder
                File imagesFolder =
                        new File(outputFile.getParentFile(), IMAGES_FOLDER);

                /*
                 * Generate logo image. If the custom logo preference is set
                 * then use the custom logo file otherwise use the TIBCO logo
                 */
                DocGenUtil.createLogoFile(inputFile.getProject(), imagesFolder);

                // Update the cache
                alreadyProcessedFolders.add(targetFolder);
            } finally {
                if (monitor != null) {
                    monitor.done();
                }
            }
        }
    }

    /**
     * Does the copy of resources
     * 
     * @param baseTargetFolder
     * @param copyFiles2
     * @throws CoreException
     */
    private void doResourceCopy(File baseTargetFolder, ResourceCopyFile[] files)
            throws CoreException {

        for (ResourceCopyFile rcf : files) {
            rcf.copyResource(baseTargetFolder);
        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.export.wizards.SubTask#dispose()
     */
    @Override
    public void dispose() {
        alreadyProcessedFolders.clear();
    }

}

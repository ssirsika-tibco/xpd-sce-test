/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.bizassets.resources.internal.Messages;

/**
 * Utility class to extract the Prince2 jar file to the given location. Also
 * provides method to extract the stage file to the given location
 * 
 * @author njpatel
 * 
 */
public class Prince2TemplateUtility {

    private IProgressMonitor monitor = null;

    private static final String YES = Messages.Prince2TemplateUtility_yes_button;

    private static final String YESTOALL = Messages.Prince2TemplateUtility_yesToAll_button;

    private static final String NO = Messages.Prince2TemplateUtility_no_button;

    private static final String NOTOALL = Messages.Prince2TemplateUtility_noToAll_button;

    private static final String CANCEL = Messages.Prince2TemplateUtility_cancel_button;

    private static final String[] buttons = new String[] { YES, YESTOALL, NO,
            NOTOALL, CANCEL };

    private String overwrite = ""; //$NON-NLS-1$

    private Shell shell = null;

    private static final String ADDINGMSG = Messages.Prince2TemplateUtility_adding_message;

    private static final String OVERWRITEMSG = Messages.Prince2TemplateUtility_overwriting_message;

    /**
     * Utility to extract the Prince2 Jar.
     * 
     * @param monitor
     * @param shell
     */
    protected Prince2TemplateUtility(IProgressMonitor monitor, Shell shell) {
        this.monitor = monitor;
        this.shell = shell;
    }

    /**
     * Extract the given template JAR file to the location specified
     * 
     * @param templateLocation
     * @param targetFolder
     * @throws IOException
     * @throws CoreException
     * @throws InterruptedException
     */
    private void extractTemplateJar(String templateLocation,
            IFolder targetFolder) throws IOException, CoreException,
            InterruptedException {

        // Get the input stream of the template
        InputStream templateInputStream = getClass().getResourceAsStream(
                templateLocation);

        if (templateInputStream != null) {
            // Get the JAR input stream
            JarInputStream jarInputStream = new JarInputStream(
                    templateInputStream);

            if (jarInputStream != null) {
                JarEntry jarEntry;
                InputStream inStream;
                IFolder folder;
                IFile file;

                // Process JAR file
                while ((jarEntry = jarInputStream.getNextJarEntry()) != null
                        && canContinue()) {

                    // If this is a place-holder file then continue to next
                    // entry
                    if (jarEntry.getName().substring(
                            jarEntry.getName().lastIndexOf('/') + 1)
                            .equalsIgnoreCase(
                                    BusinessAssetsConstants.MARKER_FILE)) {
                        continue;
                    }

                    // If this is a directory then create directory if it
                    // doesn't exist
                    if (jarEntry.isDirectory()) {
                        // If the folder does not exist at the destination
                        // then create it
                        folder = targetFolder.getFolder(jarEntry.getName());

                        if (!folder.exists()) {
                            monitorSubTask(MessageFormat.format(ADDINGMSG,
                                    new Object[] { jarEntry.getName() }));
                            folder.create(true, true, null);
                        }

                    } else {
                        // This is a file so extract it
                        file = targetFolder.getFile(jarEntry.getName());

                        if (file != null) {

                            // If file exists and the user previously decided to
                            // carry on with same decision
                            // then don't show the dialog
                            if (file.exists() && overwrite != YESTOALL
                                    && overwrite != NOTOALL) {
                                final String fileName = file.getFullPath()
                                        .toString();

                                // File exists so ask user if they want to
                                // overwrite
                                PlatformUI.getWorkbench().getDisplay()
                                        .syncExec(new Runnable() {

                                            public void run() {

                                                String msg = MessageFormat
                                                        .format(
                                                                Messages.Prince2TemplateUtility_overwrite_message,
                                                                new Object[] { fileName });

                                                MessageDialog dialog = new MessageDialog(
                                                        shell, Messages.Prince2TemplateUtility_fileExists_message,
                                                        null, msg,
                                                        MessageDialog.QUESTION,
                                                        buttons, 0);

                                                int ret = dialog.open();
                                                overwrite = buttons[ret];
                                            }

                                        });

                                // If cancel was pressed in the dialog then stop
                                // progress
                                if (overwrite == CANCEL) {
                                    throw new InterruptedException();
                                }

                            }

                            // Read the template input into a byte array output
                            // stream. This will then be
                            // passed to file.create below. This intermediate
                            // stage is required because
                            // file.create will close the stream passed in!
                            if (!file.exists()) {
                                monitorSubTask(MessageFormat.format(ADDINGMSG,
                                        new Object[] { jarEntry.getName() }));
                                // Update the contents of the file from the
                                // template
                                // JAR
                                inStream = copyInputStream(jarInputStream);
                                file.create(inStream, true, null);
                            } else {
                                if (overwrite == YES || overwrite == YESTOALL) {
                                    monitorSubTask(MessageFormat
                                            .format(OVERWRITEMSG,
                                                    new Object[] { jarEntry
                                                            .getName() }));

                                    inStream = copyInputStream(jarInputStream);

                                    file
                                            .setContents(inStream, true, true,
                                                    null);
                                }
                            }
                        }
                    }

                    monitorWorked(1);
                }

                // Close the jar input stream
                jarInputStream.close();
            }

            // Close the template input stream
            templateInputStream.close();
        }
    }

    /**
     * Extract the Prince2 Stage File to the given location
     * 
     * @param templateLocation
     * @param StageFileFolder
     * @throws IOException
     * @throws CoreException
     */
    private void extractStageFile(String templateLocation,
            IFolder StageFileFolder) throws IOException, CoreException {

        // Load the template
        InputStream templateInputStream = getClass().getResourceAsStream(
                templateLocation);

        if (templateInputStream != null && StageFileFolder != null) {
            // Open a JAR input stream of the template
            JarInputStream jarInputStream = new JarInputStream(
                    templateInputStream);

            if (jarInputStream != null) {
                JarEntry jarEntry;
                InputStream inStream;
                IFolder folder;
                IFile file;
                String patternMatch = BusinessAssetsConstants.STAGE_FILE_REGEX
                        + "/.+"; //$NON-NLS-1$

                // Process JAR file
                while ((jarEntry = jarInputStream.getNextJarEntry()) != null
                        && canContinue()) {

                    String entryName = jarEntry.getName();

                    // If this is not the Stage File then ignore
                    if (entryName.matches(patternMatch)) {
                        // If this is a place-holder file then continue to next
                        // entry
                        if (!entryName
                                .substring(entryName.lastIndexOf('/') + 1)
                                .equalsIgnoreCase(
                                        BusinessAssetsConstants.MARKER_FILE)) {

                            String targetEntryName = entryName.replaceFirst(
                                    BusinessAssetsConstants.STAGE_FILE_REGEX,
                                    ""); //$NON-NLS-1$

                            monitorSubTask(MessageFormat.format(ADDINGMSG,
                                    new Object[] { targetEntryName }));

                            if (jarEntry.isDirectory()) {
                                folder = StageFileFolder
                                        .getFolder(targetEntryName);

                                if (!folder.exists()) {
                                    folder.create(true, true, null);
                                }
                            } else {
                                file = StageFileFolder.getFile(targetEntryName);

                                // Read the template input into a byte array
                                // output
                                // stream. This will then be
                                // passed to file.create below. This
                                // intermediate
                                // stage is required because
                                // file.create will close the stream passed in!
                                inStream = copyInputStream(jarInputStream);

                                // Update the contents of the file from the
                                // template
                                // JAR
                                file.create(inStream, true, null);
                            }

                            monitorWorked(1);
                        }
                    }
                }
                jarInputStream.close();
            }

            templateInputStream.close();
        }
    }

    /**
     * Extract the Prince2 template into the given target location
     * 
     * @param businessAssetsFolder
     * 
     * @throws IOException
     * @throws CoreException
     * @throws InterruptedException
     */
    public static void extractPrince2Template(IFolder businessAssetsFolder,
            Shell shell, IProgressMonitor monitor) throws IOException,
            CoreException, InterruptedException {

        Prince2TemplateUtility util = new Prince2TemplateUtility(monitor, shell);

        util.monitorBeginTask(Messages.Prince2TemplateUtility_creatingPrince2_message, 50);

        // Create Prince2 folder if it doesn't exist
        IFolder prince2Folder = businessAssetsFolder
                .getFolder(BusinessAssetsConstants.PRINCE2_FOLDER);

        if (!prince2Folder.exists()) {
            util.monitorSubTask(MessageFormat.format(ADDINGMSG,
                    new Object[] { prince2Folder.getName() }));

            prince2Folder.create(true, true, null);

            util.monitorWorked(1);
        }

        if (prince2Folder.isAccessible()) {
            util.extractTemplateJar(
                    BusinessAssetsConstants.PRINCE2_TEMPLATE_JAR_LOCATION,
                    prince2Folder);
        }

        // Refresh the workspace
        businessAssetsFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor);

        util.monitorDone();

    }

    /**
     * Extract the Stage File folder to the given path
     * 
     * @param stageFileFolder
     * @param monitor
     * @throws IOException
     * @throws CoreException
     */
    public static void extractStageFile(IFolder stageFileFolder, Shell shell,
            IProgressMonitor monitor) throws IOException, CoreException {
        Prince2TemplateUtility util = new Prince2TemplateUtility(monitor, shell);

        if (stageFileFolder != null && stageFileFolder.isAccessible()) {
            util.monitorBeginTask(Messages.Prince2TemplateUtility_creatingStageFile_message, 20);

            util.extractStageFile(
                    BusinessAssetsConstants.PRINCE2_TEMPLATE_JAR_LOCATION,
                    stageFileFolder);
        }

        // Refresh the Stage File folder
        stageFileFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor);

        util.monitorDone();
    }

    /**
     * Copy the give <code>InputStream</code> into another (<code>ByteArrayInputStream</code>)
     * input stream
     * 
     * @param inStream
     * @return <code>ByteArrayInputStream</code> copy of the give inStream. If
     *         the parameter input stream is null then <code>null</code> will
     *         be returned.
     * 
     * @throws IOException
     */
    private InputStream copyInputStream(InputStream inStream)
            throws IOException {

        if (inStream != null) {
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();

            // Copy the input stream into the byte array output stream
            while (inStream.available() > 0) {
                byteOutStream.write(inStream.read());
            }

            // Convert the byte array output stream to a byte array input stream
            // and
            // return this
            return new ByteArrayInputStream(byteOutStream.toByteArray());

        }

        return inStream;
    }

    /**
     * Check if progress has been cancelled by the user
     * 
     * @return
     */
    private boolean canContinue() {

        if (monitor != null) {
            return !monitor.isCanceled();
        }

        return true;
    }

    /**
     * Set the monitor <code>beginTask</code>
     * 
     * @param name
     * @param totalWork
     */
    private void monitorBeginTask(String name, int totalWork) {
        if (monitor != null) {
            monitor.beginTask(name, totalWork);
        }
    }

    /**
     * Set the monitor <code>subTask</code>
     * 
     * @param name
     */
    private void monitorSubTask(String name) {
        if (monitor != null) {
            monitor.subTask(name);
        }
    }

    /**
     * Set the monitor <code>Worked</code>
     * 
     * @param work
     */
    private void monitorWorked(int work) {
        if (monitor != null) {
            monitor.worked(work);
        }
    }

    /**
     * Set the monitor <code>Done</code>
     */
    private void monitorDone() {
        if (monitor != null) {
            monitor.done();
        }
    }

}

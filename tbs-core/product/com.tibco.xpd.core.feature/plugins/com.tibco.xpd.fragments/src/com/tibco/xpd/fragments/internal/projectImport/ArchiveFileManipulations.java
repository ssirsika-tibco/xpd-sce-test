/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *     Red Hat, Inc - Extracted methods from WizardArchiveFileResourceImportPage1
 *******************************************************************************/
package com.tibco.xpd.fragments.internal.projectImport;

import java.io.IOException;
import java.util.zip.ZipFile;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.fragments.internal.Messages;

/**
 * @since 3.1
 */
public class ArchiveFileManipulations {

    /**
     * Determine whether the file with the given filename is in .tar.gz or .tar
     * format.
     * 
     * @param fileName
     *            file to test
     * @return true if the file is in tar format
     */
    public static boolean isTarFile(String fileName) {
        if (fileName.length() == 0) {
            return false;
        }

        TarFile tarFile = null;
        try {
            tarFile = new TarFile(fileName);
        } catch (TarException tarException) {
            return false;
        } catch (IOException ioException) {
            return false;
        } finally {
            if (tarFile != null) {
                try {
                    tarFile.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return true;
    }

    /**
     * Determine whether the file with the given filename is in .zip or .jar
     * format.
     * 
     * @param fileName
     *            file to test
     * @return true if the file is in tar format
     */
    public static boolean isZipFile(String fileName) {
        if (fileName.length() == 0) {
            return false;
        }

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(fileName);
        } catch (IOException ioException) {
            return false;
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return true;
    }

    /**
     * Closes the given structure provider.
     * 
     * @param structureProvider
     *            The structure provider to be closed, can be <code>null</code>
     * @param shell
     *            The shell to display any possible Dialogs in
     */
    public static void closeStructureProvider(
            ILeveledImportStructureProvider structureProvider, Shell shell) {
        if (structureProvider instanceof ZipLeveledStructureProvider) {
            closeZipFile(((ZipLeveledStructureProvider) structureProvider)
                    .getZipFile(), shell);
        }
        if (structureProvider instanceof TarLeveledStructureProvider) {
            closeTarFile(((TarLeveledStructureProvider) structureProvider)
                    .getTarFile(), shell);
        }
    }

    /**
     * Attempts to close the passed zip file, and answers a boolean indicating
     * success.
     * 
     * @param file
     *            The zip file to attempt to close
     * @param shell
     *            The shell to display error dialogs in
     * @return Returns true if the operation was successful
     */
    public static boolean closeZipFile(ZipFile file, Shell shell) {
        try {
            file.close();
        } catch (IOException e) {
            displayErrorDialog(
                    String
                            .format(
                                    Messages.ArchiveFileManipulations_cannotCloseFile_error_message,
                                    file.getName()), shell);
            return false;
        }

        return true;
    }

    /**
     * Attempts to close the passed tar file, and answers a boolean indicating
     * success.
     * 
     * @param file
     *            The tar file to attempt to close
     * @param shell
     *            The shell to display error dialogs in
     * @return Returns true if the operation was successful
     * @since 3.4
     */
    public static boolean closeTarFile(TarFile file, Shell shell) {
        try {
            file.close();
        } catch (IOException e) {
            displayErrorDialog(
                    String
                            .format(
                                    Messages.ArchiveFileManipulations_cannotCloseFile_error_message,
                                    file.getName()), shell);
            return false;
        }

        return true;
    }

    /**
     * Display an error dialog with the specified message.
     * 
     * @param message
     *            the error message
     */
    protected static void displayErrorDialog(String message, Shell shell) {
        MessageDialog.openError(shell, getErrorDialogTitle(), message);
    }

    /**
     * Get the title for an error dialog. Subclasses should override.
     */
    protected static String getErrorDialogTitle() {
        return Messages.ArchiveFileManipulations_internalErr_dlg_title;
    }
}

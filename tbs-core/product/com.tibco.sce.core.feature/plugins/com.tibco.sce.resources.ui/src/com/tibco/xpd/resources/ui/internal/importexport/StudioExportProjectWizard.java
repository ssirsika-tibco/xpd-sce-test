/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.importexport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.importexport.ProjectExportPage.FileType;

/**
 * Studio custom export wizard. This will ensure the target archive does not
 * contain any derived resources (uses the Team exclusion preference).
 * 
 * @author njpatel
 * @since 3.5.2
 */
@SuppressWarnings("restriction")
public class StudioExportProjectWizard extends Wizard implements IExportWizard {

    private ProjectExportPage projectSelectionPage;

    private IStructuredSelection selection;

    public StudioExportProjectWizard() {
        setWindowTitle(Messages.StudioExportProjectWizard_title);

        IDialogSettings workbenchSettings =
                XpdResourcesUIActivator.getDefault().getDialogSettings();
        IDialogSettings section =
                workbenchSettings.getSection("StudioExportProjectWizard");//$NON-NLS-1$
        if (section == null) {
            section =
                    workbenchSettings
                            .addNewSection("StudioExportProjectWizard");//$NON-NLS-1$
        }
        setDialogSettings(section);
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;

    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        projectSelectionPage = new ProjectExportPage("projectSelection"); //$NON-NLS-1$
        projectSelectionPage.setSelection(selection);
        addPage(projectSelectionPage);
    }

    @Override
    public boolean performFinish() {
        Object[] result = projectSelectionPage.getSelection();
        String outputPath = projectSelectionPage.getOutputPath();
        FileType type = projectSelectionPage.getFileType();
        boolean isCompressed = projectSelectionPage.isCompressed();

        if (result.length > 0 && outputPath != null) {
            try {
                // Add appropriate file extension if missing
                IPath path = new Path(outputPath);

                if (path.getFileExtension() == null) {
                    if (type == FileType.ZIP) {
                        path = path.addFileExtension(ProjectExportPage.ZIP_EXT);
                    } else if (type == FileType.TAR) {
                        path =
                                path.addFileExtension(isCompressed ? ProjectExportPage.TAR_GZ_EXT
                                        : ProjectExportPage.TAR_EXT);
                    }
                }
                
                //XPD-5570: if only the file name or a relative path is provided by the user, then it should be considered relative of the user's home directory
                if(!path.isAbsolute()) {
                	Path newPath = new Path(System.getProperty("user.home")); //$NON-NLS-1$
                	path = newPath.append(path);
                }

                // Validate destination file
                if (!ensureTargetIsValid(path.toOSString())) {
                    return false;
                }

                // if (createDestinationFolders(outputFile)) {
                List<IResource> resourcesToExport = new ArrayList<IResource>();

                for (Object next : result) {
                    if (next instanceof IProject) {
                        resourcesToExport.add((IResource) next);
                    }
                }

                ArchiveExportOperation op =
                        new ArchiveExportOperation(resourcesToExport,
                                path.toString());
                op.setCreateLeadupStructure(true);
                op.setUseCompression(isCompressed);
                op.setUseTarFormat(type == FileType.TAR);

                getContainer().run(true, true, op);
            } catch (Exception e) {
                ErrorDialog
                        .openError(getShell(),
                                Messages.StudioExportProjectWizard_exportErrorDlg_title,
                                Messages.StudioExportProjectWizard_exportErrorDlg_shortdesc,
                                e instanceof CoreException ? ((CoreException) e)
                                        .getStatus() : new Status(
                                        IStatus.ERROR,
                                        XpdResourcesPlugin.ID_PLUGIN, e
                                                .getLocalizedMessage(), e
                                                .getCause()));
                return false;
            }
        }

        projectSelectionPage.persistValues();

        return true;
    }

    /**
     * Ensures that the target output file and its containing directory are both
     * valid and able to be used. Answer a boolean indicating validity.
     */
    private boolean ensureTargetIsValid(String targetPath) {

        if (!ensureTargetDirectoryIsValid(targetPath)) {
            return false;
        }

        if (!ensureTargetFileIsValid(new File(targetPath))) {
            return false;
        }

        return true;
    }

    /**
     * Returns a boolean indicating whether the directory portion of the passed
     * pathname is valid and available for use.
     */
    private boolean ensureTargetDirectoryIsValid(String fullPathname) {
        int separatorIndex = fullPathname.lastIndexOf(File.separator);

        if (separatorIndex == -1) {
            return true;
        }

        return ensureTargetIsValid(new File(fullPathname.substring(0,
                separatorIndex)));
    }

    /**
     * Returns a boolean indicating whether the passed File handle is is valid
     * and available for use.
     */
    private boolean ensureTargetFileIsValid(File targetFile) {
        if (targetFile.exists() && targetFile.isDirectory()) {
            displayErrorDialog(Messages.StudioExportProjectWizard_exportDestinationMustBeFile_errorDlg_message);
            return false;
        }

        if (targetFile.exists()) {
            if (targetFile.canWrite()) {
                if (!queryYesNoQuestion(Messages.StudioExportProjectWizard_fileExists_errDlg_title,
                        Messages.StudioExportProjectWizard_fileExists_errDlg_message)) {
                    return false;
                }
            } else {
                displayErrorDialog(Messages.StudioExportProjectWizard_exportDestinationCannotBeOverwritten_errorDlg_message);
                return false;
            }
        }

        return true;
    }

    /**
     * If the target for export does not exist then attempt to create it. Answer
     * a boolean indicating whether the target exists (ie.- if it either
     * pre-existed or this method was able to create it)
     * 
     * @return boolean
     */
    private boolean ensureTargetIsValid(File targetDirectory) {
        if (targetDirectory.exists() && !targetDirectory.isDirectory()) {
            displayErrorDialog(Messages.StudioExportProjectWizard_targetDirectoryExistsAsFile_errorDlg_message);
            return false;
        }

        return ensureDirectoryExists(targetDirectory);
    }

    /**
     * Attempts to ensure that the specified directory exists on the local file
     * system. Answers a boolean indicating success.
     * 
     * @return boolean
     * @param directory
     *            java.io.File
     */
    private boolean ensureDirectoryExists(File directory) {
        if (!directory.exists()) {
            if (!queryYesNoQuestion(Messages.StudioExportProjectWizard_createDirectory_dlg_title,
                    Messages.StudioExportProjectWizard_createDirectory_dlg_message)) {
                return false;
            }

            if (!directory.mkdirs()) {
                displayErrorDialog(Messages.StudioExportProjectWizard_cannotCreateTargetDirectory_errDlg_message);
                return false;
            }
        }

        return true;
    }

    /**
     * Display an error dialog with the specified message.
     * 
     * @param message
     *            the error message
     */
    private void displayErrorDialog(String message) {
        MessageDialog.openError(getShell(),
                Messages.StudioExportProjectWizard_errorDialog_title,
                message);
    }

    /**
     * Displays a Yes/No question to the user with the specified message and
     * returns the user's response.
     * 
     * @param message
     *            the question to ask
     * @return <code>true</code> for Yes, and <code>false</code> for No
     */
    private boolean queryYesNoQuestion(String title, String message) {
        MessageDialog dialog =
                new MessageDialog(getContainer().getShell(), title,
                        (Image) null, message, MessageDialog.NONE,
                        new String[] { IDialogConstants.YES_LABEL,
                                IDialogConstants.NO_LABEL }, 0);
        // ensure yes is the default
        return dialog.open() == 0;
    }

}

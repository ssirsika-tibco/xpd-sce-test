/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.openspacegwtgadget.integration.daa;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.daa.internal.util.FileCopier;
import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.importexport.exportwizard.pages.DestinationLocationType;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;

/**
 * Export wizard for Openspace Gadget Development projects.
 * <p>
 * This is used for the standard Eclipse Export Wizard contributions (i.e. via
 * main menu File->Export, and project/package explorer context menu.
 * 
 * 
 * @author aallway
 * @since 24 Jan 2013
 */
public class OpenspaceExportDAAWizard extends Wizard implements IExportWizard {

    private static final Logger LOG = OpenspaceGadgetPlugin.getDefault()
            .getLogger();

    private IStructuredSelection initialSelection = StructuredSelection.EMPTY;

    private OpenspaceExportDAAProjectSelectionPage projectSelectionPage;

    /**
     * The constructor.
     */
    public OpenspaceExportDAAWizard() {
        setWindowTitle(Messages.OpenspaceExportDAAWizard_ExportOpenspaceGadgetDAA_title);
        setHelpAvailable(false);
        setDefaultPageImageDescriptor(OpenspaceGadgetPlugin
                .getImageDescriptor(OpenspaceGadgetPlugin.IMG_EXPORT_OPENSPACE_DAA_WIZARD));
        setNeedsProgressMonitor(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        initialSelection = selection;
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public final void addPages() {
        projectSelectionPage =
                new OpenspaceExportDAAProjectSelectionPage(initialSelection);

        addPage(projectSelectionPage);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean performFinish() {
        final List<IProject> selectedProjects =
                projectSelectionPage.getSelectedProjects();

        final IStatus[] statusArray = new IStatus[1];
        statusArray[0] = Status.CANCEL_STATUS;

        OpenspaceExportDAAGenerationOperation daaGenOperation =
                new OpenspaceExportDAAGenerationOperation(selectedProjects,
                        projectSelectionPage.getLocationType(),
                        projectSelectionPage.getLocationPath());
        try {
            getContainer().run(true, true, daaGenOperation);

        } catch (InvocationTargetException e) {
            LOG.error(e);
        } catch (InterruptedException e) {
            if (daaGenOperation.getStatus().getSeverity() != IStatus.CANCEL) {
                LOG.error(e);
            }
        }

        IStatus opStatus = daaGenOperation.getStatus();
        if (opStatus == null) {
            return false;

        } else if (opStatus.getSeverity() > IStatus.OK) {
            ErrorDialog
                    .openError(getShell(),
                            getWindowTitle(),
                            Messages.OpenspaceExportDAAWizard_ErrorBuildingDAA_message,
                            opStatus,
                            IStatus.OK | IStatus.INFO | IStatus.WARNING
                                    | IStatus.ERROR);

            return false;
        }

        return true;
    }

    /**
     * DAA generation class specifically used from
     * 
     * 
     * @author aallway
     * @since 24 Jan 2013
     */
    private final class OpenspaceExportDAAGenerationOperation extends
            OpenspaceProjectDAAGenerationOperation {
        private boolean overwriteAll = false;

        /**
         * @param projects
         */
        private OpenspaceExportDAAGenerationOperation(List<IProject> projects,
                DestinationLocationType destLocationType,
                String destOutFolderPath) {
            /**
             * Sid: New preserveDAA facility is not required as we override the
             * postGenerationTask and copy DAA out before calling super's
             * cleanup routines.
             */
            super(projects, false);
            this.destLocationType = destLocationType;
            this.destOutFolderPath = destOutFolderPath;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("restriction")
        @Override
        protected IStatus postGenerationTask(IProject project,
                IProgressMonitor monitor) {

            try {
                monitor.beginTask(String
                        .format(Messages.OpenspaceExportDAAWizard_ExportingDAAStaus_message,
                                project.getName()),
                        2);

                IFile srcFile = getProjectDaaGenerator().getDAAFile(project);

                if (srcFile != null && srcFile.exists()) {
                    FileCopier copier =
                            new FileCopier(srcFile, destLocationType,
                                    destOutFolderPath);

                    OverwriteStatus overwriteDestFile = OverwriteStatus.FILE;

                    if (copier.destinationExists() && !overwriteAll) {
                        overwriteDestFile =
                                overwriteFileMessageDialog(new Path(
                                        copier.getOutputPath()));

                        if (overwriteDestFile == OverwriteStatus.CANCEL) {
                            return Status.CANCEL_STATUS;
                        } else if (overwriteDestFile == OverwriteStatus.ALL_FILES) {
                            overwriteAll = true;
                        }
                    }

                    if (overwriteDestFile != OverwriteStatus.NO) {
                        try {
                            copier.copy();
                        } catch (CoreException e) {
                            String message =
                                    String.format(Messages.OpenspaceExportDAAWizard_problemCopyingFile_message,
                                            copier.getInputPath()
                                                    .toPortableString(),
                                            copier.getOutputPath());

                            return new Status(IStatus.ERROR,
                                    OpenspaceGadgetPlugin.PLUGIN_ID, message, e);
                        }
                    }

                }
                monitor.worked(1);

                return super.postGenerationTask(project,
                        new SubProgressMonitor(monitor, 1));
            } finally {
                monitor.done();
            }
        }
    }

}

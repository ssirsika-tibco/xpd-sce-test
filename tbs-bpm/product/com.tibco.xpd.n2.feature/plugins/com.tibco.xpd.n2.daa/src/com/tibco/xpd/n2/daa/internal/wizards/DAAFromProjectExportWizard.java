/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.daa.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.DAAConstants;
import com.tibco.xpd.n2.daa.ProjectDAAGenerator;
import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.n2.daa.internal.packager.DAAExportUtils;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.importexport.exportwizard.pages.DestinationLocationType;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;

/**
 * Exports xpd project(s) to DAA.
 * 
 * @author Jan Arciuchiewicz
 */
public class DAAFromProjectExportWizard extends Wizard implements IExportWizard {

    /**
     * @author Jan Arciuchiewicz
     */
    private final class MultiProjectDAAGenerationWithProgressExport extends
            MultiProjectDAAGenerationWithProgress {
        private boolean overwriteAll = false;

        /**
         * @param projects
         */
        private MultiProjectDAAGenerationWithProgressExport(
                List<IProject> projects,
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
        @Override
        protected IStatus postGenerationTask(IProject project,
                IProgressMonitor monitor) {

            try {
                monitor.beginTask(String
                        .format(Messages.DAAFromProjectExportWizard_Exporting_message,
                                project.getName()),
                        2);

                IFile srcFile =
                        ProjectDAAGenerator.getInstance().getDAAFile(project);

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
                                    String.format(Messages.DAAFromProjectExportWizard_CopyProblem_message,
                                            copier.getInputPath()
                                                    .toPortableString(),
                                            copier.getOutputPath());
                            return new Status(IStatus.ERROR,
                                    Activator.PLUGIN_ID, message, e);
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

    /** WebDAV resources selection page. */
    private DAAInputOutputSelectionWizardPage projectSelectionPage;

    private static final Logger LOG = Activator.getDefault().getLogger();

    private IStructuredSelection initialSelection = StructuredSelection.EMPTY;

    /**
     * The constructor.
     */
    public DAAFromProjectExportWizard() {
        setWindowTitle(Messages.DAAFromProjectExportWizard_title);
        setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry()
                .getDescriptor(DAAConstants.IMG_DAA_EXPORT_WIZARD));
        setNeedsProgressMonitor(true);
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public final void addPages() {
        projectSelectionPage =
                new DAAInputOutputSelectionWizardPage(initialSelection);
        addPage(projectSelectionPage);
    }

    protected List<IProject> getSelectedProjects() {
        List<IProject> projects = new ArrayList<IProject>();
        for (Object o : projectSelectionPage.getSelectedObjects()) {
            if (o instanceof IProject) {
                projects.add((IProject) o);
            }
        }
        return Collections.unmodifiableList(projects);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean performFinish() {
        // check target platform, return if target platform is not valid

        /*
         * the default button is Finish for Daa Export wizard.
         */
        Button finishButton = getShell().getDefaultButton();

        try {

            /*
             * XPD-6998: Disable the finish button when user clicks finish so
             * that the UI is more responsive and the user does not repeatedly
             * click on Finish.
             */
            if (finishButton.isEnabled()) {
                finishButton.setEnabled(false);
            }

            if (!DAAExportUtils.isTargetPlatformDefaultORValid(getShell())) {
                return true;
            }
            //
            final List<IProject> selectedProjects = getSelectedProjects();

            final AtomicBoolean dirtyResourceSaveResult =
                    new AtomicBoolean(true);

            /*
             * XPD-6998: Run the save all dirty editors in container thread so
             * that we can have the monitor passed to the
             * 'saveAllDirtyResourcesInWS' which will make the UI more
             * responsive.
             */
            try {
                /*
                 * set Fork == false because we want the save to get executed
                 * before any of the code below is reached. (i.e. wait for build
                 * and DAA generate.). Also DeployUtil.saveAllDirtyResourcesInWS
                 * internally calls the
                 * SaveableHelper.waitForBackgroundSaveJobs(modelsToSave) which
                 * saves the editors accessing the UI thread, hence we would
                 * want container thread to wait until the same is done.
                 */
                getContainer().run(false, false, new IRunnableWithProgress() {

                    @Override
                    public void run(final IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {

                        if (!DeployUtil.saveAllDirtyResourcesInWS(monitor)) {

                            dirtyResourceSaveResult.set(false);
                        }
                    };
                });
            } catch (InvocationTargetException e1) {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e1,
                                Messages.DAAFromProjectExportWizard_ProblemsWhileSavingResourcesError_msg);
            } catch (InterruptedException e1) {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e1,
                                Messages.DAAFromProjectExportWizard_ProblemsWhileSavingResourcesError_msg);
            }

            if (!dirtyResourceSaveResult.get()) {
                return false;
            }

            /*
             * XPD-5117: Wait for any builds as the files may have just been
             * saved by the statement above. If this is not done then some
             * artifacts from the builds may not be ready before this DAA
             * generation continues.
             */
            try {
                getContainer().run(true, false, new IRunnableWithProgress() {

                    @Override
                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {
                        BuildSynchronizerUtil.waitForBuildsToFinish(monitor);
                    }
                });
            } catch (InvocationTargetException e1) {
                // Report the error and continue
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e1,
                                Messages.DAAFromProjectExportWizard_problemWaitingForBuildsToFinish_error_longdesc);
            } catch (InterruptedException e1) {
                // Report the error and continue
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e1,
                                Messages.DAAFromProjectExportWizard_problemWaitingForBuildsToFinish_error_longdesc);
            }

            final IStatus[] statusArray = new IStatus[1];
            statusArray[0] = Status.CANCEL_STATUS;
            MultiProjectDAAGenerationWithProgress daaGenOperation =
                    new MultiProjectDAAGenerationWithProgressExport(
                            selectedProjects,
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
                ErrorDialog.openError(getShell(),
                        Messages.DAAFromProjectExportWizard_Error_title,
                        Messages.DAAFromProjectExportWizard_Error_message,
                        opStatus,
                        IStatus.OK | IStatus.INFO | IStatus.WARNING
                                | IStatus.ERROR);
            }
            return true;
        } finally {
            if (!finishButton.isEnabled()) {
                /*
                 * Finally bring the Finish button to its original state.
                 */
                finishButton.setEnabled(true);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        initialSelection = selection;
    }

    /**
     * Show the overwrite file message dialog
     */
    protected OverwriteStatus overwriteFileMessageDialog(final IPath outputPath) {
        final OverwriteStatus[] result = new OverwriteStatus[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String msg =
                        String.format(Messages.DAAFromProjectExportWizard_DestinationExistsDialog_message,
                                outputPath.lastSegment(),
                                outputPath.removeLastSegments(1).toOSString());

                OverwriteFileMessageDialog dialog =
                        new OverwriteFileMessageDialog(
                                getShell(),
                                Messages.DAAFromProjectExportWizard_DestinationExistsDialog_title,
                                msg);
                OverwriteStatus status =
                        dialog.getOverwriteStatus(dialog.open());
                synchronized (this) {
                    result[0] = status;
                }
            }
        };
        Display.getDefault().syncExec(runnable);
        synchronized (runnable) {
            return result[0];
        }
    }
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Wizard page to run the RASC export operation and display the status of the
 * export.
 *
 * @author nwilson
 * @since 19 Mar 2019
 */
public class RascExportStatusPage extends AbstractXpdWizardPage
        implements ExportStatusListener {

    private HashMap<IProject, LabelPair> status;

    private ScrolledComposite sc;

    private Composite statusArea;

    private Label errorMessage;

    private ProgressBarMonitor monitor;

    private boolean hasErrors;

    private ExportCompleteListener listener;

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     *
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {
        status = new HashMap<>();
        hasErrors = false;

        Composite area = new Composite(parent, SWT.NONE);
        area.setLayout(new GridLayout(2, false));

        errorMessage = new Label(area, SWT.NONE);
        errorMessage.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        sc = new ScrolledComposite(area, SWT.V_SCROLL);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
        gd.minimumHeight = 200;
        sc.setLayoutData(gd);
        sc.setExpandHorizontal(true);

        statusArea = new Composite(sc, SWT.NONE);
        statusArea.setLayout(new GridLayout(2, false));
        Label name = new Label(statusArea, SWT.FILL);
        GridData nameGD = new GridData(SWT.FILL, SWT.FILL, false, false);
        nameGD.widthHint = 100;
        name.setLayoutData(nameGD);
        name.setText(Messages.RascExportStatusPage_ProjectColumn);
        Label value = new Label(statusArea, SWT.FILL);
        GridData valueGD = new GridData(SWT.FILL, SWT.FILL, true, false);
        value.setLayoutData(valueGD);
        value.setText(Messages.RascExportStatusPage_StatusColumn);

        Label separator = new Label(statusArea, SWT.HORIZONTAL | SWT.SEPARATOR);
        separator.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        sc.setContent(statusArea);

        Label progressText = new Label(area, SWT.NONE);
        progressText.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        ProgressBar progress = new ProgressBar(area, SWT.HORIZONTAL);
        progress.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        monitor = new ProgressBarMonitor(progress, progressText);

        setControl(area);
    }

    /**
     * Sets the status for the given project.
     * 
     * @param project
     *            The project to update the status for.
     * @param message
     *            The status message.
     */
    @Override
    public void setStatus(IProject project, ExportStatus exportStatus,
            String message) {
        getShell().getDisplay().asyncExec(() -> {
            if (exportStatus == ExportStatus.FAILED_EXPORT) {
                setErrorMessage(Messages.RascExportStatusPage_RascExportError);
                setPageComplete(false);
                hasErrors = true;
            }
            if (exportStatus == ExportStatus.FAILED_VALIDATION) {
                setErrorMessage(
                        Messages.RascExportStatusPage_RascValidationError);
                setPageComplete(false);
                hasErrors = true;
            }
            if (status.containsKey(project)) {
                LabelPair pair = status.get(project);
                pair.getLabel1()
                        .setImage(PlatformUI.getWorkbench().getSharedImages()
                                .getImage(exportStatus.getIconName()));
                pair.getLabel2().setText(message);
                pair.getLabel2().setToolTipText(message);
            } else {
                Label name = new Label(statusArea, SWT.FILL);
                GridData nameGD =
                        new GridData(SWT.FILL, SWT.FILL, false, false);
                nameGD.minimumHeight = 16;
                name.setLayoutData(nameGD);
                name.setText(project.getName());

                Composite labelComposite = new Composite(statusArea, SWT.NONE);
                GridData labelCompositeGD =
                        new GridData(SWT.FILL, SWT.FILL, true, false);
                labelComposite.setLayoutData(labelCompositeGD);
                GridLayout labelCompositeLayout = new GridLayout(2, false);
                labelCompositeLayout.marginHeight = 0;
                labelCompositeLayout.marginWidth = 0;
                labelComposite.setLayout(labelCompositeLayout);

                Label value1 = new Label(labelComposite, SWT.NONE);
                GridData value1GD =
                        new GridData(SWT.FILL, SWT.FILL, false, false);
                value1.setLayoutData(value1GD);
                value1.setImage(PlatformUI.getWorkbench().getSharedImages()
                        .getImage(exportStatus.getIconName()));

                Label value2 = new Label(labelComposite, SWT.FILL);
                GridData value2GD =
                        new GridData(SWT.FILL, SWT.FILL, true, false);
                value2.setLayoutData(value2GD);
                value2.setText(message);
                value2.setToolTipText(message);

                statusArea.setSize(statusArea
                        .computeSize(statusArea.getSize().x, SWT.DEFAULT));
                statusArea.layout();

                LabelPair pair = new LabelPair(value1, value2);

                status.put(project, pair);
            }
        });
    }

    /**
     * Runs the given Runnable and monitors it's progress.
     * 
     * @param runnable
     *            The job to run.
     */
    public void run(IRunnableWithProgress runnable) {
        SubMonitor subMonitor = SubMonitor.convert(monitor,
                Messages.RascExportStatusPage_ExportingArtifacts_status,
                10);
        subMonitor.subTask(
                Messages.RascExportStatusPage_ExportingArtifacts_status);

        getShell().getDisplay().syncExec(() -> {
            if (!DeployUtil.saveAllDirtyResourcesInWS(subMonitor.split(1))) {

            }
        });

        WorkspaceJob job = new WorkspaceJob(Messages.RascExportStatusPage_ExportingArtifacts_status) {

            @Override
            public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
                try {
                    runnable.run(subMonitor.split(9));
                } catch (InvocationTargetException e) {
                    RascUiActivator.getLogger().error(e);
                    setErrorMessage(Messages.RascExportStatusPage_ExportError);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishedRun();
                    subMonitor.subTask(""); //$NON-NLS-1$
                    subMonitor.done();
                }
                return Status.OK_STATUS;
            }
        };
        job.schedule();
    }

    /**
     * Override so we don't close until the user has acknowledged the result.
     */
    protected void finishedRun() {
        monitor.done();
        // Enable launch button
        if (!hasErrors) {
            monitor.setTaskName(Messages.ProgressBarMonitor_ExportComplete);
            Shell shell = getShell();
            if (shell != null && !shell.isDisposed()) {
                shell.getDisplay().asyncExec(() -> {
                    setPageComplete(true);
                });
            }
        } else {
            monitor.setTaskName(
                    Messages.RascExportStatusPage_ProblemsFinish_status);

            Shell shell = getShell();
            if (shell != null && !shell.isDisposed()) {
                shell.getDisplay().asyncExec(() -> {
                    monitor.setState(SWT.ERROR);
                });
            }
        }
        listener.exportComplete();
    }

    /**
     * @param rascExportWizard
     */
    public void setExportCompleteListener(ExportCompleteListener listener) {
        this.listener = listener;
    }

    /**
     * Pair of Labels for use as a hashmap value.
     *
     * @author nwilson
     * @since 13 Mar 2019
     */
    class LabelPair {
        private Label label1;

        private Label label2;

        /**
         * @param label1
         * @param label2
         */
        public LabelPair(Label label1, Label label2) {
            super();
            this.label1 = label1;
            this.label2 = label2;
        }

        /**
         * @return the label1
         */
        public Label getLabel1() {
            return label1;
        }

        /**
         * @return the label2
         */
        public Label getLabel2() {
            return label2;
        }

    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.rasc.ui.export;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

import com.tibco.xpd.sce.rasc.ui.Messages;
import com.tibco.xpd.sce.rasc.ui.RascUiActivator;

/**
 * Progress dialog to monitor RASC export project status.
 *
 * @author nwilson
 * @since 7 Mar 2019
 */
public class ExportProgressMonitorDialog extends IconAndMessageDialog {

    private HashMap<IProject, Label> status;

    private Composite statusArea;

    private Label errorMessage;

    private Button ok;

    private Button launch;

    private Button cancel;

    private IProgressMonitor monitor;

    /**
     * Constructor.
     * 
     * @param parent
     *            The parent shell.
     */
    public ExportProgressMonitorDialog(Shell parent) {
        super(parent);
        setBlockOnOpen(false);
        status = new HashMap<>();
    }

    /**
     * @see org.eclipse.jface.dialogs.IconAndMessageDialog#getImage()
     */
    @Override
    protected Image getImage() {
        return getInfoImage();
    }

    /**
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(Messages.ExportProgressMonitorDialog_Title);
    }

    /**
     * @see org.eclipse.jface.dialogs.ProgressMonitorDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea(Composite parent) {

        ProgressBar progress = new ProgressBar(parent, SWT.HORIZONTAL);
        progress.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

        monitor = new ProgressBarMonitor(progress);

        Composite area = (Composite) super.createDialogArea(parent);

        errorMessage = new Label(area, SWT.NONE);
        errorMessage.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        statusArea = new Composite(area, SWT.FILL);
        statusArea.setLayout(new GridLayout(2, false));
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
        gd.minimumHeight = 200;
        statusArea.setLayoutData(gd);

        Label name = new Label(statusArea, SWT.FILL);
        GridData nameGD = new GridData(SWT.FILL, SWT.FILL, false, false);
        nameGD.widthHint = 100;
        name.setLayoutData(nameGD);
        name.setText(Messages.ExportProgressMonitorDialog_ProjectColumn);
        Label value = new Label(statusArea, SWT.FILL);
        GridData valueGD = new GridData(SWT.FILL, SWT.FILL, true, false);
        value.setLayoutData(valueGD);
        value.setText(Messages.ExportProgressMonitorDialog_StatusColumn);

        Label separator = new Label(statusArea, SWT.HORIZONTAL | SWT.SEPARATOR);
        separator.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        return area;
    }

    /**
     * Sets the status for the given project.
     * 
     * @param project
     *            The project to update the status for.
     * @param message
     *            The status message.
     */
    public void setStatus(IProject project, String message) {
        getShell().getDisplay().asyncExec(() -> {
            if (status.containsKey(project)) {
                Label label = status.get(project);
                label.setText(message);
            } else {
                Label name = new Label(statusArea, SWT.FILL);
                GridData nameGD =
                        new GridData(SWT.FILL, SWT.FILL, false, false);
                name.setLayoutData(nameGD);
                name.setText(project.getName());
                Label value = new Label(statusArea, SWT.FILL);
                GridData valueGD =
                        new GridData(SWT.FILL, SWT.FILL, true, false);
                value.setLayoutData(valueGD);
                value.setText(message);
                statusArea.layout();
                status.put(project, value);
            }
        });
    }

    /**
     * @see org.eclipse.jface.dialogs.ProgressMonitorDialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        ok = createButton(parent,
                OK,
                Messages.ExportProgressMonitorDialog_OKButton,
                false);
        ok.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                close();
            }
        });
        ok.setEnabled(false);
        launch = createButton(parent,
                100,
                Messages.ExportProgressMonitorDialog_LaunchAdminButton,
                false);
        launch.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                try {
                    launch();
                    close();
                } catch (PartInitException | MalformedURLException e) {
                    setErrorMessage(
                            Messages.ExportProgressMonitorDialog_LaunchError);
                    launch.setEnabled(false);
                }
            }
        });
        launch.setEnabled(false);
        cancel = createButton(parent,
                IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL,
                true);
        cancel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                monitor.setCanceled(true);
            }
        });
    }

    /**
     * Sets an error message in the dialog.
     * 
     * @param message
     *            The message to display.
     */
    protected void setErrorMessage(String message) {
        errorMessage.setText(message);
    }

    /**
     * Runs the given Runnable and monitors it's progress.
     * 
     * @param runnable
     *            The job to run.
     */
    public void run(IRunnableWithProgress runnable) {
        Job job = Job.createSystem(jobMonitor -> {
            try {
                runnable.run(monitor);
            } catch (InvocationTargetException e) {
                RascUiActivator.getLogger().error(e);
                setErrorMessage(
                        Messages.ExportProgressMonitorDialog_ExportError);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                finishedRun();
            }
        });
        job.schedule();
    }

    /**
     * Override so we don't close until the user has acknowledged the result.
     */
    protected void finishedRun() {
        getShell().getDisplay().asyncExec(() -> {
            ok.setEnabled(true);
            launch.setEnabled(true);
            cancel.setEnabled(false);
        });
    }

    /**
     * Launch the admin UI in a new browser window.
     * 
     * @throws PartInitException
     *             If the browser could not be opened.
     * @throws MalformedURLException
     *             If the URL is invalid (should never happen)
     */
    private void launch() throws PartInitException, MalformedURLException {
        IWebBrowser browser = PlatformUI.getWorkbench().getBrowserSupport()
                .createBrowser("admin-ui"); //$NON-NLS-1$
        URL url = new URL("http://localhost"); //$NON-NLS-1$
        browser.openURL(url);
    }
}

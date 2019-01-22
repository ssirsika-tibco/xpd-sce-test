/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.projectImport;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.FragmentsManager.FragmentsInitializationListener;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Import fragments project wizard. This will import registered fragment
 * projects into the 3.1 fragments. See extension point
 * <code>com.tibco.xpd.fragments.fragmentsImport</code> for details on how to
 * register an import.
 * 
 * @author njpatel
 * 
 */
public class ImportFragmentsProjectWizard extends Wizard implements
        IImportWizard {

    WizardFragmentsProjectsImportPage importPage;

    private boolean fragmentsInitialized = false;

    /**
     * Fragments initializer listener.
     */
    private final FragmentsInitializationListener listener = new FragmentsInitializationListener() {

        public void initializationComplete() {
            fragmentsInitialized = true;
        }
    };

    /**
     * Import fragments project wizard.
     */
    public ImportFragmentsProjectWizard() {
        setNeedsProgressMonitor(true);
        FragmentsManager.getInstance().addInitializationListener(listener);

        // Set the dialog settings for this wizard
        IDialogSettings dialogSettings = FragmentsActivator.getDefault()
                .getDialogSettings();
        IDialogSettings section = dialogSettings
                .getSection("ImportFragmentProjectWizard"); //$NON-NLS-1$
        if (section == null) {
            section = dialogSettings
                    .addNewSection("ImportFragmentProjectWizard"); //$NON-NLS-1$
        }
        setDialogSettings(section);

    }

    @Override
    public void addPages() {
        importPage = new WizardFragmentsProjectsImportPage();
        addPage(importPage);
    }

    @Override
    public void dispose() {
        FragmentsManager.getInstance().removeInitializationListener(listener);
        super.dispose();
    }

    @Override
    public boolean performFinish() {
        boolean ret = true;

        try {
            FragmentsManager.getInstance().runRunnableContext(getContainer(),
                    true, true, new IRunnableWithProgress() {

                        public void run(IProgressMonitor monitor)
                                throws InvocationTargetException,
                                InterruptedException {
                            if (!fragmentsInitialized) {
                                // Fragments view not initialized so wait till
                                // initialized
                                monitor
                                        .beginTask(
                                                Messages.ImportFragmentsProjectWizard_WaitingForInitToComplete_monitor_message,
                                                IProgressMonitor.UNKNOWN);

                                while (!fragmentsInitialized) {
                                    Thread.sleep(500);
                                    if (monitor.isCanceled()) {
                                        throw new OperationCanceledException();
                                    }
                                }
                            }

                            try {
                                importPage.importProjects(monitor);
                            } finally {
                                // Clear undo-redo history
                                FragmentsUtil.flushUndoRedoHistory();

                                // Refresh the view
                                FragmentsManager.getInstance()
                                        .refreshFragmentsView(null);
                                monitor.done();
                            }
                        }

                    });
        } catch (InvocationTargetException e) {
            // One of the steps resulted in a core exception
            Throwable t = e.getTargetException();
            String message = Messages.ImportFragmentsProjectWizard_importProblem_errorDlg_message;
            IStatus status;
            if (t instanceof CoreException) {
                status = ((CoreException) t).getStatus();
            } else {
                status = new Status(IStatus.ERROR,
                        FragmentsActivator.PLUGIN_ID, 1, message, t);
            }
            ErrorDialog.openError(getShell(), message, null, status);
            ret = false;
        } catch (InterruptedException e) {
            ret = false;
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setWindowTitle(Messages.ImportFragmentsProjectWizard_title);
    }

}

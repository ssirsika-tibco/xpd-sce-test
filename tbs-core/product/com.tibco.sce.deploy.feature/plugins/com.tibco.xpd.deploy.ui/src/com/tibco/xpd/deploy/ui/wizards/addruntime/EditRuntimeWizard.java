/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addruntime;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Wizard for editing runtime.
 * 
 * @author Jan Arciuchiewicz
 */
public class EditRuntimeWizard extends Wizard implements INewWizard {

    private final ServerManager serverManager;

    private RuntimeParametersPage runtimeParametersPage;

    private Runtime editedRuntime;

    private final Runtime runtime;

    private static final Logger log = DeployUIActivator.getDefault()
            .getLogger();

    /**
     * Constructor.
     */
    public EditRuntimeWizard(Runtime runtime) {
        super();
        this.runtime = runtime;
        setWindowTitle(Messages.EditRuntimeWizard_EditRuntime_title);
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry().getDescriptor(
                        DeployUIConstants.IMG_SERVER_WIZARD));
        setNeedsProgressMonitor(true);
        serverManager = DeployUIActivator.getServerManager();
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        // do nothing
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public void addPages() {

        runtimeParametersPage = new RuntimeParametersPage();
        addPage(runtimeParametersPage);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {

        editedRuntime = runtimeParametersPage.getRuntime();
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException {
                try {
                    doFinish(monitor);
                } catch (CoreException e) {
                    throw new InvocationTargetException(e);
                } finally {
                    monitor.done();
                }
            }
        };
        try {
            getContainer().run(true, false, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            Throwable realException = e.getTargetException();
            log.error(e);
            StringBuilder sb = new StringBuilder(
                    Messages.EditRuntimeWizard_ExceptionThrown_message);
            if (realException.getLocalizedMessage() != null) {
                sb.append('\n').append(realException.getLocalizedMessage());
            }
            MessageDialog.openError(getShell(),
                    Messages.EditRuntimeWizard_ExceptionThrown_title, sb
                            .toString());
            return false;
        }
        return true;
    }

    /**
     * The worker method.
     * 
     * @param repoConfig
     * @param repoType
     */
    private void doFinish(IProgressMonitor monitor) throws CoreException {
        monitor.worked(1);
    }

    public Runtime getEditedRuntime() {
        return editedRuntime;
    }

    ServerManager getServerManager() {
        return serverManager;
    }

    RuntimeParametersPage getRuntimeParametersPage() {
        return runtimeParametersPage;
    }

    @Override
    public void createPageControls(Composite pageContainer) {
        super.createPageControls(pageContainer);
        runtimeParametersPage.setEditedRuntime(runtime);
    }
}
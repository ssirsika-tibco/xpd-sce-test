/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addruntime;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Wizard for adding new runtime to the model.
 * 
 * @author Jan Arciuchiewicz
 */
public class AddRuntimeWizard extends Wizard implements INewWizard {

    private final ServerManager serverManager;

    private RuntimeTypePage runtimeTypePage;

    private RuntimeParametersPage runtimeParametersPage;

    private Runtime createdRuntime;

    private static final Logger log = DeployUIActivator.getDefault()
            .getLogger();

    /**
     * Constructor.
     */
    public AddRuntimeWizard() {
        super();
        setWindowTitle(Messages.AddRuntimeWizard_NewRuntime_title);
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

        runtimeTypePage = new RuntimeTypePage();
        addPage(runtimeTypePage);

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
        final Runtime runtime = runtimeParametersPage.getRuntime();

        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException {
                try {
                    doFinish(runtime, monitor);
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
                    Messages.AddRuntimeWizard_ExceptionThrownMessage);
            if (realException.getLocalizedMessage() != null) {
                sb.append('\n').append(realException.getLocalizedMessage());
            }
            MessageDialog.openError(getShell(),
                    Messages.AddRuntimeWizard_ExceptionThrown_title, sb
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
    private void doFinish(Runtime runtime, IProgressMonitor monitor)
            throws CoreException {
        EditingDomain ed = serverManager.getEditingDomain();
        ServerContainer serverContainer = serverManager.getServerContainer();
        Command cmd = AddCommand.create(ed, serverContainer,
                DeployPackage.eINSTANCE.getServerContainer_Runtimes(), runtime);
        if (cmd.canExecute()) {
            ed.getCommandStack().execute(cmd);
        }
        serverManager.saveServerContainer();
        monitor.worked(1);
    }

    public Runtime getCreatedRuntime() {
        return createdRuntime;
    }

    ServerManager getServerManager() {
        return serverManager;
    }

    RuntimeParametersPage getRuntimeParametersPage() {
        return runtimeParametersPage;
    }

    public RuntimeTypePage getRuntimeTypePage() {
        return runtimeTypePage;
    }
}
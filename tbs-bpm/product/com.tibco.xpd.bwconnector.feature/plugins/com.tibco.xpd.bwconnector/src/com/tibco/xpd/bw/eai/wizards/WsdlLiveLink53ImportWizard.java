/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bw.eai.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.bw.eai.BWActivator;
import com.tibco.xpd.bw.eai.BWLink;
import com.tibco.xpd.bw.eai.BWMessages;
import com.tibco.xpd.bw.eai.pages.JMSServerSelectionPage;
import com.tibco.xpd.bw.eai.pages.WsdlSelectionPage;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.registry.ui.wizard.IImportWizardWithOperationPicker;
import com.tibco.xpd.registry.ui.wizard.OperationPickerPage;
import com.tibco.xpd.registry.ui.wizard.TargetSelectionPage;
import com.tibco.xpd.registry.ui.wizard.WsdlImportWizard.IImportProjectProvider;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.dialogs.TextDetailsDialog;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * Business Works 5.3 Live Link WSDL import wizard.
 * 
 * @author nwilson
 */
public class WsdlLiveLink53ImportWizard extends Wizard implements
        IImportWizardWithOperationPicker, IImportProjectProvider {

    private IProject project;

    /** The WSDL list selection page. */
    private final JMSServerSelectionPage jmsSelectionPage;

    /** The read only WSDL info page. */
    private final WsdlSelectionPage sourcePage;

    /** The target page. */
    private final TargetSelectionPage targetPage;

    private OperationPickerPage operationPickerPage = null;

    private boolean finished = false;

    public WsdlLiveLink53ImportWizard() {
        super();
        setWindowTitle(BWMessages.WsdlImport_Title);
        setNeedsProgressMonitor(true);
        jmsSelectionPage = new JMSServerSelectionPage();
        sourcePage = new WsdlSelectionPage();
        targetPage = new TargetSelectionPage();
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        if (selection != null && selection.size() == 1) {
            Object target = selection.getFirstElement();
            if (target instanceof IAdaptable) {
                IAdaptable adaptable = (IAdaptable) target;
                // MR 41969
                IProject project = null;
                SpecialFolder specialFolderOfKind = null;
                if (adaptable instanceof IProject) {
                    project = (IProject) adaptable;
                    specialFolderOfKind =
                        SpecialFolderUtil.getSpecialFolderOfKind(project,
                                WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND);
                }
                if (adaptable instanceof SpecialFolder) {
                    SpecialFolder specialFolder = (SpecialFolder) adaptable;
                    project = specialFolder.getProject();
                    specialFolderOfKind =
                        SpecialFolderUtil.getSpecialFolderOfKind(project,
                                WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND);
                }
                if (null != project && null != specialFolderOfKind) {
                    targetPage.setLocation(project.getName()
                        + TargetSelectionPage.pathSeparator
                        + specialFolderOfKind.getLocation());
                }
                // MR 41969
            }
        }
    }

    public IProject getProject() {
        return project;
    }

    public void setProject(IProject project) {
        this.project = project;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        addPage(jmsSelectionPage);
        addPage(sourcePage);
        addPage(targetPage);
        if (operationPickerPage != null) {
            addPage(operationPickerPage);
        }
    }

    /**
     * @param page
     *            The current page.
     * @return The next page.
     * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        if (page.equals(jmsSelectionPage)) {
            Server selectedServer = jmsSelectionPage.getSelectedJMSServer();
            sourcePage.refreshServerDetails(selectedServer);
            targetPage.resetMappings();
            targetPage.setFileName(sourcePage.guessFileName());
            targetPage.setPageComplete(sourcePage.isPageComplete());
        }
        if (page.equals(sourcePage)) {
            targetPage.resetMappings();
            targetPage.setFileName(sourcePage.guessFileName());
        }

        IWizardPage nextPage = super.getNextPage(page);
        // if operation picker page is not null then we know this page has been
        // called from an action and we
        // need to import and show the wsdl methods before user can finish.
        if (operationPickerPage != null && operationPickerPage.equals(nextPage)) {
            if (!targetPage.getDestinationLocationResource().getLocation()
                    .toOSString().equals(operationPickerPage
                            .getNavigateLocation())) {
                if (!performFinish()) {
                    nextPage = null;
                }
            }
            operationPickerPage.setNavigateLocation(targetPage
                    .getDestinationLocationResource().getLocation()
                    .toOSString());
        }
        return nextPage;
    }

    @Override
    public boolean canFinish() {
        if (jmsSelectionPage != null && jmsSelectionPage.isPageComplete()
                && null != targetPage && targetPage.isPageComplete()) {
            Server selectedServer = jmsSelectionPage.getSelectedJMSServer();
            if (selectedServer != null) {
                if (operationPickerPage == null) {
                    return true;
                }
            }
        }
        return super.canFinish();
    }

    @Override
    public boolean performFinish() {
        if (!finished) {
            final String host = sourcePage.getHost();
            final int port = sourcePage.getPort();
            final String target = sourcePage.getTarget();
            final String jndi = sourcePage.getJndiName();
            final String user = sourcePage.getUsername();
            final String password = sourcePage.getPassword();
            IRunnableWithProgress op = new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException {
                    try {
                        monitor
                                .beginTask(BWMessages.WsdlLiveLink53ImportWizard_ImportingInProgress_message,
                                        IProgressMonitor.UNKNOWN);
                        Thread.sleep(5000);
                        String wsdl =
                                BWLink.getWsdl(host,
                                        port,
                                        jndi,
                                        target,
                                        user,
                                        password);
                        if (wsdl == null) {
                            throw new RuntimeException(
                                    BWMessages.WsdlLiveLink53ImportWizard_GetWsdlError);
                        } else {
                            IFile file = targetPage.getResource();
                            InputStream source =
                                    new ByteArrayInputStream(wsdl.getBytes());
                            if (targetPage.isOverwriteExistingResources()
                                    && file.exists()) {
                                file.setContents(source, IResource.FORCE, null);
                            } else {
                                file.create(source, true, null);
                            }
                            if (operationPickerPage != null) {
                                operationPickerPage.setInput(file.getProject());
                            }
                        }
                    } catch (Exception e) {
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
                handleException(realException);
                return false;
            }
            finished = true;
        }
        return true;
    }

    private void handleException(Throwable e) {
        String message =
                BWMessages.WsdlLiveLink53ImportWizard_ProblemDuringImport_message;
        String title = BWMessages.WsdlLiveLink53ImportWizard_ImportError_title;
        String details =
                String
                        .format(BWMessages.WsdlLiveLink53ImportWizard_ExceptionDetails_longdesc,
                                e.getClass().toString());
        if (e.getLocalizedMessage() != null
                && e.getLocalizedMessage().trim().length() > 0) {
            details += '\n' + e.getLocalizedMessage();
        } else if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
            details += '\n' + e.getMessage();
        }
        TextDetailsDialog.openError(getShell(), title, message, details);
        logException(e);
    }

    private void logException(Throwable e) {
        try {
            IStatus status =
                    new Status(IStatus.ERROR, BWActivator.PLUGIN_ID, 0, e
                            .getMessage(), e);
            BWActivator.getDefault().getLog().log(status);
        } catch (Throwable t) {
            // Ignore
        }
    }

    /**
     * @see com.tibco.xpd.registry.ui.wizard.IImportWizardWithOperationPicker#setOperationPickerPage(com.tibco.xpd.registry.ui.wizard.OperationPickerPage)
     * 
     * @param operationPickerPage
     */
    public final void setOperationPickerPage(
            OperationPickerPage operationPickerPage) {
        this.operationPickerPage = operationPickerPage;
    }

    /**
     * @see com.tibco.xpd.registry.ui.wizard.IImportWizardWithOperationPicker#getOperationPickerPage()
     * 
     * @return
     */
    public final OperationPickerPage getOperationPickerPage() {
        return operationPickerPage;
    }
}

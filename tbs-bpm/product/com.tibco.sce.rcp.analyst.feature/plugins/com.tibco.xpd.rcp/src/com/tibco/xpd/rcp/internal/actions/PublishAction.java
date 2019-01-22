/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.help.CoreHelpActivator;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.ui.actions.ExportDocAction;

/**
 * Action to publish the documentation for the project.
 * 
 * @author njpatel
 * 
 */
public class PublishAction extends Action {

    private static final String DOCUMENTATION_INDEX = "index.html"; //$NON-NLS-1$

    private static final String SETTINGS_OPENDOC = "rcp.publish.openDoc"; //$NON-NLS-1$

    private static final String TITLE = Messages.PublishAction_title;

    private final IWorkbenchWindow window;

    public PublishAction(IWorkbenchWindow window) {
        super(TITLE);
        this.window = window;
        setId("publish"); //$NON-NLS-1$
        setToolTipText(Messages.PublishAction_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.PUBLISH
                .getPath()));
        setDisabledImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.PUBLISH.getDisabledPath()));
        setEnabled(false);
        RCPResourceManager.addOpenListener(new IOpenResourceListener() {

            @Override
            public void opened(IRCPContainer resource) {
                if (resource != null && resource.getProjectResources() != null
                        && !resource.getProjectResources().isEmpty()) {
                    setEnabled(true);

                    resource.addChangeListener(new RCPResourceChangeListener() {

                        @Override
                        public void resourceChanged(IRCPResource resource,
                                RCPResourceChangeEvent event) {
                            if (event.eventType == RCPResourceEventType.DISPOSED) {
                                setEnabled(false);
                            }
                        }
                    });
                } else {
                    setEnabled(false);
                }
            }

        });
    }

    @Override
    public void run() {
        PublishWizard wizard = new PublishWizard();
        WizardDialog dlg = new WizardDialog(window.getShell(), wizard);
        dlg.open();
    }

    /**
     * Get all the resources from the current project.
     * 
     * @return
     */
    private List<IResource> getAllResources() {
        List<IResource> resources = new ArrayList<IResource>();

        IRCPContainer resource = RCPResourceManager.getResource();
        if (resource != null) {
            Collection<ProjectResource> projectResources =
                    resource.getProjectResources();
            if (projectResources != null && !projectResources.isEmpty()) {
                for (ProjectResource prResource : projectResources) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(prResource.getProject());
                    if (config != null) {
                        EList<SpecialFolder> folders =
                                config.getSpecialFolders().getFolders();

                        if (folders != null) {
                            for (SpecialFolder folder : folders) {
                                List<IResource> resList =
                                        SpecialFolderUtil
                                                .getAllDeepResourcesInSpecialFolderOfKind(prResource
                                                        .getProject(),
                                                        folder.getKind(),
                                                        null,
                                                        false);
                                if (resList != null && !resList.isEmpty()) {
                                    resources.addAll(resList);
                                }
                            }
                        }
                    }
                }
            }
        }
        return resources;
    }

    /**
     * Get the export documentation action.
     * 
     * @return
     */
    private ExportDocAction getAction() {
        return new ExportDocAction();
    }

    /**
     * Publish wizard.
     */
    private class PublishWizard extends Wizard {

        private FolderSelectionPage selectionPage;

        public PublishWizard() {
            setWindowTitle(TITLE);
            setNeedsProgressMonitor(true);
            setDialogSettings(RCPActivator.getDefault().getDialogSettings());
        }

        @Override
        public void addPages() {
            // Get persisted value for whether the documentation should be
            // opened
            String value = getDialogSettings().get(SETTINGS_OPENDOC);
            selectionPage =
                    new FolderSelectionPage(
                            value != null ? Boolean.parseBoolean(value) : true);
            addPage(selectionPage);
        }

        @Override
        public boolean performFinish() {
            final IPath path = selectionPage.getFolderPath();

            // Persist the open documentation choice
            getDialogSettings().put(SETTINGS_OPENDOC,
                    selectionPage.doOpenDocumentation());

            if (path != null) {
                IRunnableWithProgress runnable = new IRunnableWithProgress() {

                    @Override
                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {
                        ExportDocAction action = getAction();
                        action.setOutputPath(path);
                        action.setSelectedResources(getAllResources());
                        action.run(monitor);
                    }

                };

                try {
                    getContainer().run(true, false, runnable);

                    if (selectionPage.doOpenDocumentation()) {
                        // Open the documentation
                        File file = path.append(DOCUMENTATION_INDEX).toFile();
                        if (file.exists()) {
                            	try {
                                    URL url = file.toURI().toURL();
                                    PlatformUI.getWorkbench().getBrowserSupport()
                                            .createBrowser(XpdConsts.XPD_SHARRED_BROWSER_ID)
                                            .openURL(url);
                                } catch (PartInitException | MalformedURLException e) {
                                	RCPActivator.getDefault().getLogger().error(e);
                                	ErrorDialog
                                        .openError(getShell(),
                                                TITLE,
                                                Messages.PublishAction_problemOpeningDocumentation_error_message,
                                                new Status(
                                                        IStatus.ERROR,
                                                        RCPActivator.PLUGIN_ID,
                                                        e.getLocalizedMessage(),
                                                        e));
                                }
                        }
                    }

                    return true;
                } catch (InvocationTargetException e) {
                    ErrorDialog
                            .openError(getShell(),
                                    TITLE,
                                    Messages.PublishAction_problemGeneratingDocumentation_error_message,
                                    new Status(IStatus.ERROR,
                                            RCPActivator.PLUGIN_ID, e
                                                    .getLocalizedMessage(), e));
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
            return false;
        }
    }

    /**
     * Wizard page to select the destination folder for the generated
     * documentation.
     */
    private class FolderSelectionPage extends WizardPage {

        private IPath folderPath;

        private Text folderTxt;

        private boolean doOpenDocumentation;

        /**
         * @param doOpenDocumentation
         *            default 'Open documentation on completion' checkbox
         *            selection
         */
        protected FolderSelectionPage(boolean doOpenDocumentation) {
            super("folderSelection"); //$NON-NLS-1$
            setTitle(TITLE);
            setDescription(Messages.PublishAction_selectTargetFolder_dialog_message);
            this.doOpenDocumentation = doOpenDocumentation;
        }

        public IPath getFolderPath() {
            return folderPath;
        }

        protected boolean doOpenDocumentation() {
            return doOpenDocumentation;
        }

        @Override
        public void createControl(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout(3, false));

            Label lbl = new Label(root, SWT.NONE);
            lbl.setText(Messages.PublishAction_destination_label);

            folderTxt = new Text(root, SWT.BORDER);
            folderTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                    false));
            folderTxt.addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    validatePage();
                }
            });

            Button browseBtn = new Button(root, SWT.NONE);
            browseBtn.setText("..."); //$NON-NLS-1$
            browseBtn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    DirectoryDialog dlg =
                            new DirectoryDialog(window.getShell());
                    dlg.setText(TITLE);
                    dlg.setMessage(Messages.PublishAction_selectTargetFolder_dialog_message);
                    String path = dlg.open();
                    if (path != null) {
                        folderTxt.setText(path);
                    }
                }
            });

            // Leave first column empty
            new Label(root, SWT.NONE);

            final Button openDocBtn = new Button(root, SWT.CHECK);
            openDocBtn.setSelection(doOpenDocumentation);
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
            data.horizontalSpan = 2;
            openDocBtn.setLayoutData(data);
            openDocBtn.setText(Messages.PublishAction_openDocumentation_button);
            openDocBtn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    doOpenDocumentation = openDocBtn.getSelection();
                }
            });

            setPageComplete(false);
            setControl(root);
        }

        /**
         * Validate the folder path.
         */
        private void validatePage() {
            String errMsg = null;

            String value = folderTxt.getText();
            if (value.length() > 0) {
                File file = new File(value);
                if (!file.exists() || !file.isDirectory()) {
                    errMsg =
                            Messages.PublishAction_noDestinationFolder_error_shortdesc;
                } else if (value.startsWith(".")) { //$NON-NLS-1$
                    errMsg =
                            Messages.PublishAction_destinationCannotStartWithDot_error_shortdesc;
                }
            } else {
                errMsg =
                        Messages.PublishAction_emptyDestination_error_shortdesc;
            }

            folderPath = errMsg == null ? new Path(value) : null;
            setErrorMessage(errMsg);
            setPageComplete(errMsg == null);
        }
    }
}

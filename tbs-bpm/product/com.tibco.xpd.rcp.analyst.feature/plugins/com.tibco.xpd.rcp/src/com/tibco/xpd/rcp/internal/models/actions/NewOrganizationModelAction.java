/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.UserInfoUtil;

/**
 * Action to create a new {@link OrgModel}.
 * 
 * @author njpatel
 * 
 */
public class NewOrganizationModelAction extends ModelAction {

    private SpecialFolder sFolder;

    private IProject project;

    public NewOrganizationModelAction(IWorkbenchWindow window, String label,
            String image, IProject project, SpecialFolder sFolder) {
        super(window, label, image);
        this.project = project;
        this.sFolder = sFolder;
    }

    @Override
    public void run() {
        WizardDialog dlg =
                new WizardDialog(getWorkbenchWindow().getShell(),
                        new NewOrganizationModelWizard(getLabel()
                                .replaceAll("\\.+$", ""), project, sFolder)); //$NON-NLS-1$ //$NON-NLS-2$
        dlg.open();
    }

    @Override
    public boolean isNewAction() {
        return true;
    }

    @Override
    public boolean isNewFileAction() {
        return true;
    }

    /**
     * Command to create a new Organization Model.
     */
    private static class NewOrganizationModelCommand extends AbstractCommand {

        private final String modelName;

        private final SpecialFolder sFolder;

        private IFile file;

        private final IProgressMonitor monitor;

        private final String orgName;

        private final IProject project;

        private Resource resource;

        private final Shell shell;

        public NewOrganizationModelCommand(Shell shell, IProject project,
                SpecialFolder sFolder, String modelName, String orgName,
                IProgressMonitor monitor) {
            this.shell = shell;
            this.project = project;
            this.sFolder = sFolder;
            this.modelName = modelName;
            this.orgName = orgName;
            this.monitor = monitor;
        }

        @Override
        protected boolean prepare() {
            if (sFolder.getFolder() == null) {
                // Create the underlying folder
                IFolder folder = project.getFolder(sFolder.getLocation());
                try {
                    ProjectUtil.createFolder(folder, false, null);
                } catch (CoreException e) {
                    RCPActivator.getDefault().getLogger().error(e);
                    return false;
                }
            }

            file = getModelFile(sFolder.getFolder(), modelName);

            return true;
        }

        @Override
        public void execute() {
            // Create the Organization Model
            try {
                resource =
                        OrganizationModelDiagramEditorUtil
                                .createDiagram(modelName,
                                        orgName,
                                        URI.createPlatformResourceURI(file
                                                .getFullPath().toString(), true),
                                        monitor,
                                        createIntialParamsMap());
            } catch (CoreException e) {
                ErrorDialog
                        .openError(shell,
                                Messages.NewOrganizationModelAction_errorCreatingOrgModel_errorDlg_title,
                                e.getLocalizedMessage(),
                                e.getStatus());
            }
        }

        @Override
        public Collection<?> getResult() {
            return resource != null ? Collections.singleton(resource) : null;
        }

        @Override
        public boolean canUndo() {
            return false;
        }

        @Override
        public void redo() {
            // Nothing to do
        }

        /**
         * Get default Organization Model creation options.
         * 
         * @return
         */
        private Map<String, Object> createIntialParamsMap() {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(OrganizationModelDiagramEditorUtil.CREATE_DEFAULT_METAMODEL_PARAM,
                    Boolean.TRUE);
            params.put(OrganizationModelDiagramEditorUtil.APPLY_STANDARD_TYPE_PARAM,
                    Boolean.TRUE);

            return params;
        }

        /**
         * Get the organization model file name.
         * 
         * @param folder
         * @param modelName
         * @return
         */
        private IFile getModelFile(IFolder folder, String modelName) {
            IFile file = null;
            int idx = 0;

            String fileName = project.getName();

            do {
                if (idx == 0) {
                    file = folder.getFile(fileName + ".om"); //$NON-NLS-1$
                } else {
                    file =
                            folder.getFile(String
                                    .format("%s%d.om", fileName, idx)); //$NON-NLS-1$
                }
                ++idx;
            } while (file.exists());
            return file;
        }
    }

    /**
     * New Organization Model wizard.
     */
    private class NewOrganizationModelWizard extends Wizard {

        OrgModelWizardPage page;

        private final SpecialFolder folder;

        private final IProject project;

        private final String title;

        public NewOrganizationModelWizard(String title, IProject project,
                SpecialFolder sFolder) {
            this.title = title;
            this.project = project;
            folder = sFolder;
            setWindowTitle(title);
            setNeedsProgressMonitor(true);
            setDefaultPageImageDescriptor(OrganizationModelDiagramEditorPlugin
                    .findImageDescriptor("icons/wizban/NewOMWizard.gif")); //$NON-NLS-1$
        }

        @Override
        public void addPages() {

            // Use the default orgModel label from the user profile
            page =
                    new OrgModelWizardPage(project != null ? UserInfoUtil
                            .getProjectPreferences(project)
                            .getOrganisationName() : null);
            page.setTitle(title);
            page.setMessage(Messages.NewOrganizationModelAction_NewOrgModelWizardPage_shortdesc);
            addPage(page);
        }

        @Override
        public boolean performFinish() {
            try {
                final Resource[] resource = new Resource[] { null };
                getContainer().run(true, false, new WorkspaceModifyOperation() {

                    @Override
                    protected void execute(IProgressMonitor monitor)
                            throws CoreException, InvocationTargetException,
                            InterruptedException {
                        NewOrganizationModelCommand cmd =
                                new NewOrganizationModelCommand(getShell(),
                                        project, folder, page.orgModelLabel,
                                        page.orgLabel, monitor);
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getCommandStack().execute(cmd);
                        Collection<?> result = cmd.getResult();
                        if (result != null && !result.isEmpty()) {
                            resource[0] = (Resource) result.iterator().next();
                        }
                    }
                });

                // Open the diagram
                if (resource[0] != null) {
                    try {
                        OrganizationModelDiagramEditorUtil
                                .openDiagram(resource[0]);
                    } catch (PartInitException e) {
                        ErrorDialog
                                .openError(getShell(),
                                        getLabel(),
                                        Messages.NewOrganizationModelAction_editorFailed_error_message,
                                        new Status(IStatus.ERROR,
                                                RCPActivator.PLUGIN_ID, e
                                                        .getLocalizedMessage(),
                                                e));
                    }
                }
                return true;
            } catch (InvocationTargetException e) {
                RCPActivator.getDefault().getLogger().error(e);
                IStatus status;
                if (e.getCause() instanceof CoreException) {
                    status = ((CoreException) e.getCause()).getStatus();
                } else {
                    status =
                            new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID,
                                    e.getLocalizedMessage(), e.getCause());
                }
                ErrorDialog
                        .openError(getShell(),
                                getLabel(),
                                Messages.NewOrganizationModelAction_errorCreatingOrgModel_error_message,
                                status);
            } catch (InterruptedException e) {
                RCPActivator.getDefault().getLogger().error(e);
            }
            return false;
        }
    }

    /**
     * Page of the New Organization Model wizard.
     */
    private class OrgModelWizardPage extends WizardPage {

        private Text orgModelText;

        private Text orgText;

        private String orgLabel;

        private String orgModelLabel;

        protected OrgModelWizardPage(String orgModelLabel) {
            super("orgModelDetails"); //$NON-NLS-1$
            this.orgModelLabel =
                    orgModelLabel != null ? orgModelLabel
                            : Messages.NewOrganizationModelAction_default_orgModel_label;
            orgLabel = Messages.NewOrganizationModelAction_default_org_label;
        }

        @Override
        public void createControl(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout(2, false));

            ModifyListener listener = new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    setPageComplete(validatePage());
                }
            };

            createLabel(root,
                    Messages.NewOrganizationModelAction_orgModel_label);
            orgModelText = new Text(root, SWT.BORDER);
            orgModelText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                    false));
            orgModelText.setText(orgModelLabel);
            orgModelText.addModifyListener(listener);

            createLabel(root,
                    Messages.NewOrganizationModelAction_organization_label);
            orgText = new Text(root, SWT.BORDER);
            orgText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
            orgText.setText(orgLabel);
            orgText.addModifyListener(listener);
            setControl(root);

            setPageComplete(validatePage());
        }

        /**
         * Validate the input in this page.
         * 
         * @return
         */
        private boolean validatePage() {
            String errMsg = null;
            if (orgModelText != null && !orgModelText.isDisposed()) {
                if (orgModelText.getText().length() > 0) {
                    orgModelLabel = orgModelText.getText();

                    if (orgText.getText().length() > 0) {
                        orgLabel = orgText.getText();
                    } else {
                        errMsg =
                                Messages.NewOrganizationModelAction_blankOrgLabel_error_shortdesc;
                    }
                } else {
                    errMsg =
                            Messages.NewOrganizationModelAction_blankOrgModelLabel_error_shortdesc;
                }
            }
            setErrorMessage(errMsg);
            return errMsg == null;
        }

        /**
         * Create a label with the given text.
         * 
         * @param parent
         * @param text
         * @return
         */
        private Label createLabel(Composite parent, String text) {
            Label lbl = new Label(parent, SWT.NONE);
            GridData data = new GridData();
            data.widthHint = 120;
            lbl.setLayoutData(data);
            lbl.setText(text);
            return lbl;
        }

    }

    /**
     * @return the project
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @param project
     *            the project to set
     */
    public void setProject(IProject project) {
        this.project = project;
    }

    /**
     * @return the sFolder
     */
    public SpecialFolder getsFolder() {
        return sFolder;
    }

    /**
     * @param sFolder
     *            the sFolder to set
     */
    public void setsFolder(SpecialFolder sFolder) {
        this.sFolder = sFolder;
    }

}

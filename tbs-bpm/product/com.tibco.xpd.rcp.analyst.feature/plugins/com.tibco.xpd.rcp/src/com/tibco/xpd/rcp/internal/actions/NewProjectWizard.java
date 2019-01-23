/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceFactory;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.ProjectStatus;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetManager;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.ui.wizards.newproject.AssetWizard;
import com.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider;
import com.tibco.xpd.ui.wizards.newproject.ProjectAssetWizardNode;

/**
 * New project wizard.
 * 
 * @author njpatel
 */
public class NewProjectWizard extends Wizard {

    private static final String XPDL_ASSET =
            "com.tibco.xpd.asset.businessProcess"; //$NON-NLS-1$

    private static final String BOM_ASSET = "com.tibco.xpd.asset.bom"; //$NON-NLS-1$

    private static final String OM_ASSET = "com.tibco.xpd.asset.om"; //$NON-NLS-1$

    private static final String WSDL_ASSET = "com.tibco.xpd.asset.wsdl"; //$NON-NLS-1$

    private static final String PROJECT_VERSION = "1.0.0.qualifier"; //$NON-NLS-1$

    private ProjectSelectionPage projectSelectionPage;

    private IRCPContainer currentResource;

    private final boolean startNewApplication;

    public NewProjectWizard(IRCPContainer currentResource,
            boolean startNewApplication) {
        super();
        this.currentResource = currentResource;
        this.startNewApplication = startNewApplication;
        setWindowTitle(Messages.NewProjectAction_wizard_title);
        setNeedsProgressMonitor(true);
    }

    @Override
    public boolean performFinish() {
        // Prepare page to configure the assets
        projectSelectionPage.performFinish();
        final IRCPContainer[] newResource = new IRCPContainer[] { null };

        WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {

                monitor.beginTask(Messages.NewProjectWizard_newProject_monitor_shortdesc,
                        3);

                /*
                 * If starting new application then clear the currently open
                 * resource, if any.
                 */
                if (startNewApplication) {
                    RCPResourceManager.clearCurrentResource();
                    currentResource = null;
                }

                monitor.worked(1);

                /*
                 * If no current resource is set then create new MAA resource
                 * here
                 */
                if (currentResource == null) {
                    currentResource = RCPResourceFactory.createNewMAA();
                    // Indicate that a new resource has been created so it can
                    // be registered as the new application.
                    newResource[0] = currentResource;
                }

                monitor.worked(1);

                CreateXpdProjectOperation op =
                        new CreateXpdProjectOperation(projectSelectionPage,
                                currentResource);

                op.run(new SubProgressMonitorEx(monitor, 1));

            }
        };

        try {

            /*
             * If starting a new application then get the user to save the
             * current resource before continuing
             */
            if (startNewApplication) {
                if (!RCPResourceManager.saveCurrentResource(getShell())) {
                    // User cancelled save
                    return false;
                }
            }

            // Setting fork to true will cause problems with the asset
            // configurators
            getContainer().run(false, false, op);

            if (newResource[0] != null) {
                /*
                 * New MAA was created here so set this as the currently edited
                 * resource
                 */
                RCPResourceManager.addNewApplication(newResource[0]);
            }

            return true;
        } catch (InvocationTargetException e1) {
            ErrorDialog
                    .openError(getShell(),
                            Messages.NewProjectAction_errorDialog_title,
                            Messages.NewProjectAction_errorCreatingProject_error_message,
                            new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID,
                                    e1.getLocalizedMessage(), e1.getCause()));
        } catch (InterruptedException e1) {
            // Do nothing
        } catch (CoreException e) {
            ErrorDialog
                    .openError(getShell(),
                            Messages.NewProjectAction_errorDialog_title,
                            Messages.NewProjectAction_errorCreatingProject_error_message,
                            e.getStatus());
        }
        return false;
    }

    @Override
    public void addPages() {
        projectSelectionPage = new ProjectSelectionPage(currentResource);
        if (startNewApplication) {
            /*
             * If we are creating a new MAA the description should state all the
             * existing open projects will be closed.
             */
            projectSelectionPage
                    .setDescription(Messages.NewProjectWizard_projetNameWizardPage_description);
        }
        addPage(projectSelectionPage);
    }

    /**
     * Project name and asset selection page of the New Project wizard.
     * 
     */
    private static class ProjectSelectionPage extends WizardSelectionPage
            implements IProjectDetailsProvider {

        private String projectName;

        private Button processBtn;

        private Button bomBtn;

        private Button omBtn;

        private Text projectTxt;

        private final List<String> selectedAssets;

        private final List<String> existingProjects;

        protected ProjectSelectionPage(IRCPContainer currentResource) {
            super("projectName"); //$NON-NLS-1$
            setTitle(Messages.NewProjectAction_wizard_title);
            setDescription(Messages.NewProjectAction_projetNameWizardPage_description);
            selectedAssets = new ArrayList<String>();

            /*
             * Collect names of existing projects - this is used in validation
             * to prevent user from entering a duplicate project name
             */
            existingProjects = new ArrayList<String>();
            if (currentResource != null) {
                for (ProjectResource prRes : currentResource
                        .getProjectResources()) {
                    existingProjects.add(prRes.getProject().getName());
                }
            }
        }

        /**
         * @see com.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider#getProjectDetails()
         * 
         * @return
         */
        @Override
        public ProjectDetails getProjectDetails() {
            ProjectDetails details =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            details.setId(ProjectUtil
                    .getDefaultProjectLifecycleId(getProjectName()));
            details.setStatus(ProjectStatus.UNDER_REVISION);
            details.setVersion(PROJECT_VERSION);
            return details;
        }

        /**
         * @see com.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider#getProjectName()
         * 
         * @return
         */
        @Override
        public String getProjectName() {
            return projectName;
        }

        @Override
        public void createControl(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout(2, false));

            Label lbl = new Label(root, SWT.NONE);
            lbl.setText(Messages.NewProjectAction_name_label);

            projectTxt = new Text(root, SWT.BORDER);
            projectTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                    false));
            projectTxt.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent e) {
                    projectName = projectTxt.getText().trim();
                    validatePage();
                }

            });

            Group assetSelectionGrp = new Group(root, SWT.NONE);
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
            data.horizontalSpan = 2;
            data.minimumHeight = convertHeightInCharsToPixels(5);
            data.verticalIndent = 10;
            assetSelectionGrp.setLayoutData(data);
            RowLayout layout = new RowLayout();
            layout.marginWidth = 5;
            layout.marginHeight = 5;
            layout.wrap = true;
            layout.spacing = 5;
            layout.fill = true;

            assetSelectionGrp.setLayout(layout);

            assetSelectionGrp
                    .setText(Messages.NewProjectWizard_selectAssetsToAddToProject_label);

            SelectionListener buttonListener = new SelectionAdapter() {
                /**
                 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
                 * 
                 * @param e
                 */
                @Override
                public void widgetSelected(SelectionEvent e) {
                    updateSelectedAssets();
                    validatePage();
                }
            };

            processBtn = new Button(assetSelectionGrp, SWT.CHECK);
            processBtn
                    .setText(Messages.NewProjectWizard_businessProcessAsset_btn);
            processBtn.setImage(RCPActivator.getDefault().getImageRegistry()
                    .get(RCPImages.BUSINESS_PROCESS.getPath()));
            processBtn.addSelectionListener(buttonListener);

            bomBtn = new Button(assetSelectionGrp, SWT.CHECK);
            bomBtn.setText(Messages.NewProjectWizard_bomAsset_btn);
            bomBtn.setImage(RCPActivator.getDefault().getImageRegistry()
                    .get(RCPImages.BOM_16.getPath()));
            bomBtn.addSelectionListener(buttonListener);

            omBtn = new Button(assetSelectionGrp, SWT.CHECK);
            omBtn.setText(Messages.NewProjectWizard_omAsset_btn);
            omBtn.setImage(RCPActivator.getDefault().getImageRegistry()
                    .get(RCPImages.OM_16.getPath()));
            omBtn.addSelectionListener(buttonListener);

            // Check all 3 by default
            processBtn.setSelection(true);
            bomBtn.setSelection(true);
            omBtn.setSelection(true);
            updateSelectedAssets();

            setPageComplete(false);
            setControl(root);

        }

        @Override
        public boolean isPageComplete() {
            boolean pageComplete = super.isPageComplete();

            IWizardNode wizardNode = getSelectedNode();
            /*
             * If a wizard node has been set then check if the wizard can
             * finish, otherwise page is complete
             */
            if (wizardNode != null && wizardNode.getWizard() != null) {
                pageComplete = wizardNode.getWizard().canFinish();
            }

            return pageComplete;
        }

        /**
         * Update the selected assets list based on the selected assets in the
         * UI.
         */
        private void updateSelectedAssets() {
            selectedAssets.clear();

            if (processBtn.getSelection()) {
                selectedAssets.add(XPDL_ASSET);
                selectedAssets.add(WSDL_ASSET);
            }

            if (bomBtn.getSelection()) {
                selectedAssets.add(BOM_ASSET);
            }

            if (omBtn.getSelection()) {
                selectedAssets.add(OM_ASSET);
            }
        }

        /**
         * Prepare the wizard node for this wizard selection page.
         */
        protected void performFinish() {

            List<ProjectAssetElement> assetsToExecute =
                    new ArrayList<ProjectAssetElement>();

            IProjectAssetManager assetManager =
                    ProjectAssetManager.getProjectAssetMenager();
            ProjectAssetElement[] assets = assetManager.getAssets();
            Map<String, ProjectAssetElement> assetMap =
                    new HashMap<String, ProjectAssetElement>(assets.length);
            for (ProjectAssetElement asset : assets) {
                assetMap.put(asset.getId(), asset);
            }

            for (String id : selectedAssets) {
                ProjectAssetElement asset = assetMap.get(id);
                if (asset != null) {
                    assetsToExecute.add(asset);
                    addDependencies(asset, assetsToExecute, assetMap);
                }
            }

            ProjectAssetWizardNode wizardNode = null;

            if (!assetsToExecute.isEmpty()) {
                wizardNode =
                        new ProjectAssetWizardNode(
                                getWizard().getWindowTitle(), getContainer(),
                                assetsToExecute, this);
            }

            setSelectedNode(wizardNode);
        }

        /**
         * Add the dependencies of the given asset to the assetToSelect list.
         * This is a recursive method and will also include all implicit
         * dependencies.
         * 
         * @param asset
         *            get dependencies of
         * @param assetsToSelect
         *            list of assets that will be selected
         * @param assetMap
         *            assets
         */
        private void addDependencies(ProjectAssetElement asset,
                List<ProjectAssetElement> assetsToSelect,
                Map<String, ProjectAssetElement> assetMap) {

            for (String assetId : asset.getDependencies()) {
                ProjectAssetElement depAsset = assetMap.get(assetId);

                if (depAsset != null && !assetsToSelect.contains(depAsset)) {
                    assetsToSelect.add(depAsset);
                    addDependencies(depAsset, assetsToSelect, assetMap);
                }
            }
        }

        /**
         * Validate the given project name.
         * 
         * @param projectName
         * @return <code>null</code> if the name is valid, otherwise an error
         *         message will be returned.
         */
        private boolean validatePage() {
            String errMsg = null;
            String projectName = projectTxt.getText().trim();
            IStatus status =
                    ResourcesPlugin.getWorkspace().validateName(projectName,
                            IResource.PROJECT);
            if (status.isOK()) {
                // Check if project name contains invalid characters
                Set<Character> invalidCharacters =
                        ProjectUtil
                                .getInvalidCharacters(ProjectUtil.URI_DISRUPTIVE_CHARACTERS_PATTERN,
                                        projectName);

                if (!invalidCharacters.isEmpty()) {
                    errMsg =
                            String.format(Messages.NewProjectAction_invalidCharacter_error_shortdesc,
                                    invalidCharacters.iterator().next(),
                                    projectName);
                } else if (projectName.startsWith(".")) { //$NON-NLS-1$
                    errMsg =
                            Messages.NewProjectWizard_projectNameCannotStartWithDot_error_shortdesc;
                } else if (!existingProjects.isEmpty()) {
                    // Check if a project with the same name already exists
                    if (existingProjects.contains(projectName)) {
                        errMsg =
                                Messages.NewProjectWizard_projectAlreadyExists_error_shortdesc;
                    }
                }
            } else {
                errMsg = status.getMessage();
            }

            // if Project name is fine then make sure atleast one asset is
            // selected
            if (errMsg == null) {
                if (!processBtn.getSelection() && !bomBtn.getSelection()
                        && !omBtn.getSelection()) {
                    errMsg =
                            Messages.NewProjectWizard_atleastOneAssetShouldBeSelected_error_shortdesc;
                }
            }

            setErrorMessage(errMsg);
            boolean isValid = errMsg == null;
            setPageComplete(isValid);
            return isValid;
        }
    }

    /**
     * Local copy of the
     * {@link com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard.CreateXpdProjectOperation}
     * as we need to run extra operations before creating the project, for
     * example informing all assets of the project name so the resources can be
     * created with names that relate to the project.
     * 
     */
    private static class CreateXpdProjectOperation extends
            WorkspaceModifyOperation {

        private final ProjectSelectionPage projectSelectionPage;

        private IRCPContainer currentResource;

        public CreateXpdProjectOperation(
                ProjectSelectionPage projectSelectionPage,
                IRCPContainer currentResource) {
            this.projectSelectionPage = projectSelectionPage;
            this.currentResource = currentResource;
        }

        /**
         * @see org.eclipse.ui.actions.WorkspaceModifyOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param monitor
         * @throws CoreException
         * @throws InvocationTargetException
         * @throws InterruptedException
         */
        @Override
        protected void execute(IProgressMonitor monitor) throws CoreException,
                InvocationTargetException, InterruptedException {
            try {
                monitor.beginTask(Messages.NewProjectAction_creatingProject_monitor_shortDesc,
                        11);

                /*
                 * Create the new project
                 */
                ProjectResource projectResource =
                        currentResource
                                .createProjectResource(projectSelectionPage
                                        .getProjectName());
                if (projectResource != null) {
                    projectResource.open(new SubProgressMonitorEx(monitor, 1));

                    if (projectResource.getProject() != null) {
                        IProject project = projectResource.getProject();

                        ProjectUtil.addNature(project,
                                XpdConsts.PROJECT_NATURE_ID);

                        XpdResourcesPlugin.getDefault()
                                .createDefaultProjectConfigFile(project,
                                        projectSelectionPage
                                                .getProjectDetails());

                        // If asset selected then perform asset operation
                        if (projectSelectionPage.getSelectedNode() != null) {
                            IWizardNode wizardNode =
                                    projectSelectionPage.getSelectedNode();

                            if (wizardNode.getWizard() instanceof AssetWizard) {
                                AssetWizard assetWizard =
                                        (AssetWizard) wizardNode.getWizard();
                                fireProjectNameChange(assetWizard);
                                assetWizard
                                        .performFinish(new SubProgressMonitorEx(
                                                monitor, 10),
                                                project);
                            }
                        }
                    }
                }
            } finally {
                monitor.done();
                // if (isNewApplication && project != null) {
                // Application.addNewApplication(currentResource);
                // }
            }

        }

        /**
         * Inform all assets of the project name so that they can update the
         * names of their corresponding resources.
         * 
         * @param assetWizard
         */
        private void fireProjectNameChange(AssetWizard assetWizard) {
            IWizardPage[] pages = assetWizard.getPages();

            if (pages != null) {
                for (IWizardPage page : pages) {
                    if (page instanceof IAssetProjectPropertyChangeListener) {
                        ((IAssetProjectPropertyChangeListener) page)
                                .projectPropertyChanged(new PropertyChangeEvent(
                                        this,
                                        IAssetProjectPropertyChangeListener.PROJECTNAME_PROPERTY,
                                        null, projectSelectionPage
                                                .getProjectName()));
                    }
                }
            }

        }
    }
}
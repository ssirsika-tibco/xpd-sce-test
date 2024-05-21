/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetBindingUtil;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetBindingUtil.IAssetBinding;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetManager;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * XPD Project wizard that includes selection of asset types. This class is
 * instantiated by <code>{@link XpdProjectWizardFactory}</code>.
 * <p>
 * Method <code>{@link #setAssetIdsToEnable(String[])}</code> (deprecated) can
 * be used to specify the default assets to enable for a wizard but extension
 * point <b>com.tibco.xpd.resources.projectAssetBinding</b> should be used
 * instead.
 * </p>
 * 
 * @see XpdProjectWizardFactory
 * 
 * @author njpatel
 */
public class XpdProjectWizard extends BasicNewXpdResourceWizard {

    /* public */static final String PROJECT_SELECTION_PAGE_ID =
            "ProjectCreation"; //$NON-NLS-1$

    /**
     * Operation that create a XPD project.
     * 
     * @author wzurek
     */
    public static final class CreateXpdProjectOperation extends
            WorkspaceModifyOperation {
        private final IProject project;

        private final IProjectDescription description;

        private final ProjectAssetSelectionPage assetSelectionPage;

        private final ProjectDetails details;

        /**
         * Create XPD project operation.
         * 
         * @param project
         *            newly created project handle, the operation will invoke
         *            project.handle().
         * @param description
         *            project description
         * @param assetSelectionPage
         *            wizard page where assets were selected, can be null
         */
        public CreateXpdProjectOperation(IProject project,
                IProjectDescription description,
                ProjectAssetSelectionPage assetSelectionPage) {
            this(project, description, assetSelectionPage, null);
        }

        /**
         * Create XPD project operation.
         * 
         * @param project
         *            newly created project handle, the operation will invoke
         *            project.handle().
         * @param description
         *            project description
         * @param assetSelectionPage
         *            wizard page where assets were selected, can be null
         * @param details
         *            project details to set in the config (can be
         *            <code>null</code>)
         * 
         * @since 3.2
         */
        public CreateXpdProjectOperation(IProject project,
                IProjectDescription description,
                ProjectAssetSelectionPage assetSelectionPage,
                ProjectDetails details) {
            super(ResourcesPlugin.getWorkspace().getRoot());
            this.project = project;
            this.description = description;
            this.assetSelectionPage = assetSelectionPage;
            this.details = details;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.ui.actions.WorkspaceModifyOperation#execute(org.eclipse
         * .core.runtime.IProgressMonitor)
         */
        @Override
        public void execute(IProgressMonitor monitor) throws CoreException {
            try {
                monitor.beginTask(Messages.NewXPDProject_4, 400);
                // Create project
                project.create(description,
                        new SubProgressMonitor(monitor, 100));

                if (monitor.isCanceled())
                    throw new OperationCanceledException();

                project.open(new SubProgressMonitor(monitor, 100));
                ProjectUtil.addNature(project, XpdConsts.PROJECT_NATURE_ID);

                XpdResourcesPlugin.getDefault()
                        .createDefaultProjectConfigFile(project, details);

                if (monitor.isCanceled())
                    throw new OperationCanceledException();

                // If asset selected then perform asset operation
                if (assetSelectionPage != null
                        && assetSelectionPage.getSelectedNode() != null) {
                    IWizardNode wizardNode =
                            assetSelectionPage.getSelectedNode();

                    if (wizardNode.getWizard() instanceof AssetWizard) {
                        ((AssetWizard) wizardNode.getWizard())
                                .performFinish(monitor, project);
                    }
                }
            } finally {
                monitor.done();
            }
        }
    }

    private ProjectSelectionPage newProjectCreationPage;

    private ProjectAssetSelectionPage assetSelectionPage;

    // List of asset ids to enable for this project wizard
    private final List<String> assetIdsToEnable = new ArrayList<String>();

    // List of mandatory asset ids
    private final List<String> mandatoryAssets = new ArrayList<String>();

    private final IConfigurationElement config;

    private AssetTypeSorter assetTypeSorter;

    private boolean hideAssetSelection = false;

    private boolean hideDestinationEnv = false;

    private boolean hideProjectLifecycle = false;

    private String defaultProjectVersion;

    private boolean hideProjectVersion;

    private String presetDestinationEnv;

	/* Sid ACE-7330: Allow consumer to optionally set the project selection page banner icon. */
	private ImageDescriptor				bannerIcon;

    /**
     * Constructor.
     * 
     * @param config
     *            <code>IConfigurationElement</code> of the new wizard
     *            extension.
     */
    public XpdProjectWizard(IConfigurationElement config) {
        super();
        this.config = config;
        setNeedsProgressMonitor(true);
        IProjectAssetManager assetManager =
                ProjectAssetManager.getProjectAssetMenager();

        // Add all asset bindings to the list
        List<IAssetBinding> bindings =
                ProjectAssetBindingUtil.getAssetBindings(getWizardId());

        for (IAssetBinding binding : bindings) {
            String assetId = binding.getAssetId();
            if (assetId != null) {
                ProjectAssetElement asset = assetManager.getAssetById(assetId);
                if (asset != null) {
                    // Collect all bound assets
                    if (!assetIdsToEnable.contains(assetId)) {
                        assetIdsToEnable.add(assetId);
                    }
                    // Collect all mandatory assets (that don't extend existing
                    // asset types)
                    if (binding.isMandatory()
                            && (asset.getExtends() == null || asset
                                    .getExtends().length() == 0)
                            && !mandatoryAssets.contains(binding.getAssetId())) {
                        mandatoryAssets.add(binding.getAssetId());
                        addDependentAssetsOfMandatoryAsset(asset, assetManager);
                    }
                } else {
                    throw new IllegalArgumentException(
                            String.format(Messages.XpdProjectWizard_asseetNotFound_error_message,
                                    assetId));
                }
            }
        }
    }

    /**
     * Get the id of this wizard contribution.
     * 
     * @return
     */
    private String getWizardId() {
        return config.getAttribute("id"); //$NON-NLS-1$
    }

    /**
     * If a mandatory asset type depends on other asset types then they should
     * be marked as mandatory too.
     * 
     * @param assetId
     */
    private void addDependentAssetsOfMandatoryAsset(ProjectAssetElement asset,
            IProjectAssetManager assetManager) {
        if (asset != null) {
            String[] dependencies = asset.getDependencies();

            if (dependencies != null) {
                for (String id : dependencies) {
                    if (!mandatoryAssets.contains(id)) {
                        mandatoryAssets.add(id);

                        // Add it's dependencies
                        asset = assetManager.getAssetById(id);
                        if (asset != null) {
                            addDependentAssetsOfMandatoryAsset(asset,
                                    assetManager);
                        } else {
                            throw new IllegalArgumentException(
                                    String.format(Messages.XpdProjectWizard_asseetNotFound_error_message,
                                            id));
                        }
                    }

                }
            }
        }
    }

    /**
     * Set the Asset types to enable by default for this project.
     * 
     * @param assetsToEnable
     *            <ul>
     *            <li><code>null</code> or empty array - select none of the
     *            asset types,</li>
     *            <li>array of ids - select the asset types of the given ids.</li>
     *            </ul>
     * 
     * @deprecated Use extension point
     *             <b>com.tibco.xpd.resources.projectAssetBinding</b> to bind an
     *             asset type to a new project wizard.
     */
    @Deprecated
    public void setAssetIdsToEnable(String[] assetsToEnable) {
        if (assetsToEnable != null) {
            assetIdsToEnable.addAll(Arrays.asList(assetsToEnable));
        }
    }

    /**
     * Set the sorter to sort the order in which the asset type contributions
     * appear in this wizard. Note, this does not affect the asset selection
     * page.
     * 
     * @param assetTypeSorter
     * @since 3.2
     */
    public void setSorter(AssetTypeSorter assetTypeSorter) {
        this.assetTypeSorter = assetTypeSorter;
    }

    /**
     * Hide the asset selection page.
     * 
     * @since 3.2
     */
    public void hideAssetSelectionPage() {
        hideAssetSelection = true;
        // SCF-275: Force Asset selection Page to complete, when hidden
        if (assetSelectionPage != null) {
            assetSelectionPage.setForcePageComplete();
        }
    }

    /**
     * Hide the destination environment selection displayed on the project
     * selection page.
     * 
     * @since 3.2
     */
    public void hideDestinationEnv() {
        hideDestinationEnv = true;
    }

    /**
     * Hide the project lifecycle section in the project selection page of this
     * wizard.
     * 
     * @since 3.2
     */
    public void hideProjectLifecycle() {
        hideProjectLifecycle = true;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        newProjectCreationPage =
                new ProjectSelectionPage(PROJECT_SELECTION_PAGE_ID);

        if (hideProjectLifecycle) {
            newProjectCreationPage.hideProjectLifecycle();
        } else {
            if (hideDestinationEnv) {
                newProjectCreationPage.hideDestinationEnvironment();
            }

            if (hideProjectVersion) {
                newProjectCreationPage.hideProjectVersion();
            }
        }

        /*
         * Sid ACE-2980 always hide project status for new project wizard (showing Draft/Locked for production in new
         * dialog doesn't make sense).
         */
        newProjectCreationPage.hideProjectStatus();

        if (presetDestinationEnv != null) {
            newProjectCreationPage.setPresetDestinationEnv(presetDestinationEnv);
        }

        newProjectCreationPage
                .setTitle(Messages.NewAnalysisProject_projectPage_title);
        newProjectCreationPage
                .setDescription(Messages.NewAnalysisProject_projectPage_shortdesc);

    	/* Sid ACE-7330: Allow consumer to optionally set the project selection page banner icon. */
		if (bannerIcon != null)
		{
			newProjectCreationPage.setImageDescriptor(bannerIcon);
		}

        /*
         * If a default project version is provided by the wizard contribution
         * then pass it to the project details provider
         */
        if (defaultProjectVersion != null) {
            ProjectDetails details = newProjectCreationPage.getProjectDetails();
            details.setVersion(defaultProjectVersion);
        }

        addPage(newProjectCreationPage);

        assetSelectionPage =
                new ProjectAssetSelectionPage(
                        assetIdsToEnable.toArray(new String[assetIdsToEnable
                                .size()]), mandatoryAssets);
        assetSelectionPage.setTitle(Messages.NewXPDProject_2);
        assetSelectionPage.setMessage(Messages.NewXPDProject_3);
        assetSelectionPage.setWizardId(getWizardId());
        if (assetTypeSorter != null) {
            assetSelectionPage.setAssetSorter(assetTypeSorter);
        }
        if (hideAssetSelection) {
            // SCF-275: Force Asset selection Page to complete, when hidden
            assetSelectionPage.setForcePageComplete();
        }

        addPage(assetSelectionPage);
    }

    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        IWizardPage nextPage = super.getNextPage(page);
        if (hideAssetSelection && nextPage instanceof ProjectAssetSelectionPage) {
            // Skip straight into the asset pages
            nextPage = ((ProjectAssetSelectionPage) nextPage).getNextPage();
        }
        return nextPage;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        final IProject project = newProjectCreationPage.getProjectHandle();

        IPath newPath = null;
        if (!newProjectCreationPage.useDefaults()) {
            newPath = newProjectCreationPage.getLocationPath();
        }

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProjectDescription description =
                workspace.newProjectDescription(project.getName());
        description.setLocation(newPath);

        // create the new project operation
        final CreateXpdProjectOperation op =
                new CreateXpdProjectOperation(project, description,
                        assetSelectionPage,
                        newProjectCreationPage.getProjectDetails());

        // run the new project creation operation
        try {
            getContainer().run(false, true, new IRunnableWithProgress() {
                /*
                 * JA: SCF-93: We need to avoid sending workspace updates.
                 */
                @Override
                public void run(final IProgressMonitor mainMonitor)
                        throws InvocationTargetException, InterruptedException {
                    try {
                        IWorkspaceRunnable wsRunnableOperation =
                                new IWorkspaceRunnable() {
                                    @Override
                                    public void run(IProgressMonitor monitor)
                                            throws CoreException {
                                        op.execute(mainMonitor);
                                    }
                                };
                        ISchedulingRule wsSchedulingRule =
                                ResourcesPlugin.getWorkspace().getRoot();

                        ResourcesPlugin.getWorkspace().run(wsRunnableOperation,
                                wsSchedulingRule,
                                IWorkspace.AVOID_UPDATE,
                                mainMonitor);
                    } catch (CoreException e) {
                        throw new InvocationTargetException(e);
                    }

                }
            });
            BasicNewProjectResourceWizard.updatePerspective(config);

        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            // ie.- one of the steps resulted in a core exception
            Throwable t = e.getTargetException();
            String errMsg;

            if (project != null) {
                errMsg =
                        String.format(Messages.NewXPDProject_ERROR,
                                project.getName());
            } else {
                errMsg = String.format(Messages.NewXPDProject_ERROR, ""); //$NON-NLS-1$
            }

            if (t instanceof CoreException) {
                if (((CoreException) t).getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS) {
                    MessageDialog.openError(getShell(),
                            getWindowTitle(),
                            errMsg);
                } else {
                    ErrorDialog.openError(getShell(), errMsg, null, // no
                            // special
                            // message
                            ((CoreException) t).getStatus());
                }
            } else {
                // CoreExceptions are handled above, but unexpected
                // exceptions may still occur.
                ErrorDialog.openError(getShell(),
                        getWindowTitle(),
                        errMsg,
                        new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                                IStatus.OK, t.getLocalizedMessage(), t));
            }
            return false;
        }

        if (project != null && project.isAccessible()) {

            /*
             * Schedule the job with delay to show the project. If the delay is
             * not used then the project does not get expanded every time.
             */
            new UIJob(Messages.NewXPDProject_8) {

                @Override
                public IStatus runInUIThread(IProgressMonitor monitor) {
                    selectAndReveal(project);

                    return Status.OK_STATUS;
                }

            }.schedule(500);
        }

        return true;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IWorkbench wizWorkbench, IStructuredSelection wizSelection) {
        super.init(wizWorkbench, wizSelection);
        setNeedsProgressMonitor(true);
        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(XpdResourcesUIActivator.getDefault()
                        .getImageRegistry()
                        .get(XpdResourcesUIConstants.PROJECT_WIZARD_PAGE)));
    }

    /**
     * Set the default project version (to replace the initial version set on
     * the project - which is "1.0.0.qualifier").
     * 
     * @param defaultProjectVersion
     */
    public void setDefaultProjectVersion(String defaultProjectVersion) {
        this.defaultProjectVersion = defaultProjectVersion;
    }

    /**
     * Sid ACE-441: new function to force setting of a specific destination env.
     * 
     * @param setDestinationEnv
     */
    public void setDestinationEnv(String setDestinationEnv) {
        this.presetDestinationEnv = setDestinationEnv;
    }

    /**
     * Sid ACE-441 - new function to hide project version
     * 
     */
    public void hideProjectVersion() {
        this.hideProjectVersion = true;
    }

	/**
	 * Sid ACE-7330: Allow consumer to optionally set the project selection page banner icon. 
	 * 
	 * @return the bannerIcon
	 */
	public final ImageDescriptor getBannerIcon()
	{
		return bannerIcon;
	}

	/**
	 * Sid ACE-7330: Allow consumer to optionally set the project selection page banner icon. 
	 * 
	 * @param bannerIcon
	 *            the bannerIcon to set
	 */
	public final void setBannerIcon(ImageDescriptor bannerIcon)
	{
		this.bannerIcon = bannerIcon;
	}

}

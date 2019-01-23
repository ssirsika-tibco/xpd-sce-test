/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.daa.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.wizards.deploy.ISimpleDeployWizard;

/**
 * Abstract build and deploy project DAA wizard.
 * <p>
 * Can be used for contributing to Deploy Server view's "Deploy Module..."
 * action AND drag-drop project deployment.
 * <p>
 * Sid: Abstracted from
 * com.tibco.xpd.n2.daa.internal.deploy.BPMProjectDeployWizard during v3.6.0
 * work).
 * 
 * @author aallway
 * @since 25 Jan 2013
 */
public abstract class AbstractProjectDeployWizard extends Wizard implements
        ISimpleDeployWizard {

    private static final String DIALOG_SETTING_KEY = "ProjectDeployWizard"; //$NON-NLS-1$

    private Server selectedServer;

    private final AbstractProjectDeploySelectionPage projectSelectionPage;

    /**
     * Constructor.
     */
    public AbstractProjectDeployWizard() {
        super();

        setWindowTitle(String.format("%1$s Project Deployment",
                getProjectTypeName()));

        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry()
                .getDescriptor(DeployUIConstants.IMG_DEPLOY_MODULE_WIZARD));

        setNeedsProgressMonitor(true);
        setForcePreviousAndNextButtons(true);

        projectSelectionPage = new ProjectDeploySelectionPage();

        // Get/set dialog setting - memento.
        IDialogSettings settings =
                DaaActivator.getDefault().getDialogSettings()
                        .getSection(DIALOG_SETTING_KEY);
        if (settings == null) {
            settings =
                    DaaActivator.getDefault().getDialogSettings()
                            .addNewSection(DIALOG_SETTING_KEY);
        }
        setDialogSettings(settings);
    }

    /**
     * @return A short project type name for insertion in dialog messages /
     *         titles etc.
     */
    protected abstract String getProjectTypeName();

    /**
     * @param project
     * 
     * @return <code>true</code> if project is applicable to DAA type.
     */
    protected abstract boolean isApplicableProject(IProject project);

    /**
     * @param project
     * 
     * @return The DAA generation operation for the given project.
     */
    protected abstract AbstractMultiProjectDAAGenerationWithProgress createDAAGenerationOperation(
            IProject project);

    /**
     * Called after successful generation of DAA, this method is called to get
     * the DAA file itself
     * 
     * @param project
     * @return The DAA file just generated.
     */
    protected abstract IFile getGeneratedDAAFromProject(IProject project);

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public void addPages() {
        addPage(projectSelectionPage);
    }

    /*
     * This performFinish method has no logic. The wizard purpose is to enable
     * user to choose one of the other, more specific wizards. This method
     * though is invoked just after more specific wizard performFinish method is
     * invoked, but the result is ignored.
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        return true;
    }

    @Override
    public Server getServer() {
        return selectedServer;
    }

    @Override
    public void setServer(Server server) {
        selectedServer = server;
    }

    /**
     * Pre-set the selected project (for use when performing drag-drop deploy).
     * 
     * @param project
     */
    public void setSelectedProject(IProject project) {
        projectSelectionPage.setSelectedProject(project);
    }

    /**
     * Project deploy, project selection page.
     * 
     * 
     * @author aallway
     * @since 25 Jan 2013
     */
    private class ProjectDeploySelectionPage extends
            AbstractProjectDeploySelectionPage {

        /**
         * @see com.tibco.xpd.daa.wizards.AbstractProjectDeploySelectionPage#getProjectTypeName()
         * 
         * @return
         */
        @Override
        protected String getProjectTypeName() {
            return AbstractProjectDeployWizard.this.getProjectTypeName();
        }

        /**
         * @see com.tibco.xpd.daa.wizards.AbstractProjectDeploySelectionPage#createViewerFilters()
         * 
         * @return
         */
        @Override
        protected ViewerFilter[] createViewerFilters() {
            ViewerFilter applicableProjectFilter = new ViewerFilter() {

                @Override
                public boolean select(Viewer viewer, Object parentElement,
                        Object element) {
                    if (element instanceof IWorkspaceRoot) {
                        return true;

                    } else if (element instanceof IProject
                            && isApplicableProject((IProject) element)) {
                        return true;
                    }
                    return false;
                }
            };

            return new ViewerFilter[] { applicableProjectFilter };
        }

        /**
         * @see com.tibco.xpd.daa.wizards.AbstractProjectDeploySelectionPage#createDAAGenerationOperation(org.eclipse.core.resources.IProject)
         * 
         * @param project
         * @return
         */
        @Override
        protected AbstractMultiProjectDAAGenerationWithProgress createDAAGenerationOperation(
                IProject project) {
            return AbstractProjectDeployWizard.this
                    .createDAAGenerationOperation(project);
        }

        /**
         * @see com.tibco.xpd.daa.wizards.AbstractProjectDeploySelectionPage#getGeneratedDAAFromProject(org.eclipse.core.resources.IProject)
         * 
         * @param project
         * @return
         */
        @Override
        protected IFile getGeneratedDAAFromProject(IProject project) {
            return AbstractProjectDeployWizard.this
                    .getGeneratedDAAFromProject(project);
        }

    }
}

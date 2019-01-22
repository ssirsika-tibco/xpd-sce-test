/*
 * Copyright (c) TIBCO Software Inc 2004-2013. All rights reserved.
 */
package com.tibco.xpd.daa.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.amf.tools.admincligen.internal.deploywizard.DaaDeployWizard;
import com.tibco.amf.tools.admincligen.internal.wizard.pages.ApplicationSourcePage;
import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.daa.internal.Messages;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.deploy.ui.wizards.deploy.ISimpleDeployWizard;
import com.tibco.xpd.deploy.ui.wizards.deploy.ModuleTypeSelectionPage;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.dialogs.AbstractXpdSelectionWizardPage;

/**
 * Project selection page for deploy project DAA wizard.
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
abstract class AbstractProjectDeploySelectionPage extends
        AbstractXpdSelectionWizardPage {

    /**
     * Project Explorer Viewer ID
     */
    private static final String PROJECT_EXPLORER_VIEWER_ID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /** Name of the page used to identify page within a wizard. */
    public static final String PAGE_NAME = "ProjectSelectionPage"; //$NON-NLS-1$

    protected static final Logger LOG = DaaActivator.getDefault().getLogger();

    private CommonViewer projectsViewer;

    private IProject selectedProject;

    private IProject preselectedProject;

    private DaaDeployWizard daaDeployWizard;

    private IProject lastlyBuiltProject;

    private INavigatorContentService service;

    public AbstractProjectDeploySelectionPage() {
        super(PAGE_NAME);
        setTitle(String
                .format(Messages.AbstractProjectDeploySelectionPage_SelectProject_label,
                        getProjectTypeName()));
        setDescription(String
                .format(Messages.AbstractProjectDeploySelectionPage_SelectProject_description,
                        getProjectTypeName()));

    }

    /**
     * @return A short project type name for insertion in dialog messages /
     *         titles etc.
     */
    protected abstract String getProjectTypeName();

    /**
     * @return The viewer filters to include your chosen project types only.
     */
    protected abstract ViewerFilter[] createViewerFilters();

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

    @Override
    @SuppressWarnings("restriction")
    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        Group modulesGroup = new Group(composite, SWT.NULL);
        modulesGroup
                .setText(String
                        .format(Messages.AbstractProjectDeploySelectionPage_Projects_label,
                                getProjectTypeName()));
        GridData gridData2 = new GridData(GridData.FILL_BOTH);
        modulesGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        modulesGroup.setLayout(paramGroupLayout);

        // Tree projectsTree =
        // new Tree(modulesGroup, SWT.SINGLE | SWT.BORDER
        // | SWT.FULL_SELECTION);
        GridData gridData3 = new GridData(GridData.FILL_BOTH);
        gridData3.horizontalSpan = 2;
        // projectsTree.setLayoutData(gridData3);

        projectsViewer =
                new CommonViewer(PROJECT_EXPLORER_VIEWER_ID, modulesGroup,
                        SWT.FULL_SELECTION | SWT.BORDER | SWT.SINGLE);
        projectsViewer.getControl().setLayoutData(gridData3);

        NavigatorContentServiceFactory fact =
                NavigatorContentServiceFactory.INSTANCE;
        service =
                fact.createContentService(PROJECT_EXPLORER_VIEWER_ID,
                        projectsViewer);

        if (service != null) {
            projectsViewer.setContentProvider(service
                    .createCommonContentProvider());
            projectsViewer
                    .setLabelProvider(service.createCommonLabelProvider());

            ViewerFilter[] viewerFilters = createViewerFilters();
            if (viewerFilters != null) {
                projectsViewer.setFilters(viewerFilters);
            }

            projectsViewer
                    .addSelectionChangedListener(new ISelectionChangedListener() {
                        @Override
                        public void selectionChanged(SelectionChangedEvent event) {
                            if (event.getSelection() instanceof IStructuredSelection) {
                                IStructuredSelection s =
                                        (IStructuredSelection) event
                                                .getSelection();
                                if (s.getFirstElement() instanceof IProject) {
                                    selectedProject =
                                            (IProject) s.getFirstElement();
                                }
                            }
                            setPageComplete(validatePage());
                        }
                    });

            projectsViewer.addDoubleClickListener(new IDoubleClickListener() {
                @Override
                public void doubleClick(DoubleClickEvent event) {
                    IStructuredSelection selection =
                            (IStructuredSelection) event.getSelection();
                    if (selection != null) {
                        projectsViewer.setSelection(selection);
                        if (canFlipToNextPage()) {
                            IWizardPage page = getNextPage();
                            if (page != null) {
                                getWizard().getContainer().showPage(page);
                            }
                        }
                    }
                }
            });
            // Sorts using text returned by getText() method from the
            // LabelProvider
            // using default collator.
            projectsViewer.setSorter(new ViewerSorter());
            projectsViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());

            projectsViewer.expandAll();
            // Preselect project if it was set before control creation.
            if (preselectedProject != null) {
                projectsViewer.setSelection(new StructuredSelection(
                        preselectedProject));
            }
        }

        // Sub-wizard.
        daaDeployWizard = new DaaDeployWizard();
        // Hide application source page and pass the context server.
        daaDeployWizard
                .setShouldHidePage(ApplicationSourcePage.PAGE_NAME, true);
        daaDeployWizard.setServer(((ISimpleDeployWizard) getWizard())
                .getServer());

        setSelectedNode(new IWizardNode() {
            @Override
            public Point getExtent() {
                return new Point(-1, -1);
            }

            @Override
            public IWizard getWizard() {
                return daaDeployWizard;
            }

            @Override
            public boolean isContentCreated() {
                return daaDeployWizard != null
                        && daaDeployWizard.getPageCount() > 0;
            }

            @Override
            public void dispose() {
                if (daaDeployWizard != null) {
                    daaDeployWizard.dispose();
                }
            }

        });

        setPageComplete(validatePage());
        setMessage(null);
        setErrorMessage(null);

        setControl(composite);
    }

    /**
     * @return Validate Check the validity of the page selections.
     */
    protected boolean validatePage() {
        setMessage(null);
        setErrorMessage(null);
        // errors
        if (((IStructuredSelection) projectsViewer.getSelection()).isEmpty()) {
            setErrorMessage(Messages.AbstractProjectDeploySelectionPage_SelectProjects2_description);
            return false;
        }
        if (selectedProject != null && hasErrorMarkers(selectedProject)) {
            setErrorMessage(Messages.AbstractProjectDeploySelectionPage_SelectedProjectHasproblems_message);
            return false;
        }

        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        return true;
    }

    /**
     * @param project
     * @return CHeck whether there are problem markers on project.
     */
    private boolean hasErrorMarkers(IProject project) {
        try {
            return CompositeUtil.hasErrorLevelProblemMarkers(project);
        } catch (CoreException e) {
            LOG.error(e);
            return false;
        }
    }

    /**
     * Get the selected project.
     * 
     * @return
     */
    public IProject getSelectedProject() {
        return selectedProject;
    }

    /**
     * Preset the slected project.
     * 
     * @param selectedProject
     *            the selectedProject to set
     */
    public void setSelectedProject(IProject project) {
        preselectedProject = project;
        if (projectsViewer != null) {
            projectsViewer.setSelection(new StructuredSelection(project));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canFlipToNextPage() {
        return super.isPageComplete() && super.canFlipToNextPage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
    public boolean isPageComplete() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(boolean visible) {
        IWizardPage currentPage = getWizard().getContainer().getCurrentPage();
        if (!visible
                && currentPage != this
                && !ModuleTypeSelectionPage.PAGE_NAME.equals(currentPage
                        .getName())) {
            IStatus status = buildDAA();
            if (!status.isOK()) {

                // In case of CANCEL status the there is no ErrorDialog.
                ErrorDialog
                        .openError(getShell(),
                                getWizard().getWindowTitle(),
                                "Problem occurred during preparation of selected project to deployment.", //$NON-NLS-1$
                                status,
                                IStatus.OK | IStatus.INFO | IStatus.WARNING
                                        | IStatus.ERROR);

                IWizardPage previous = getPreviousPage();
                projectsViewer.refresh();
                super.setVisible(visible);

                getWizard().getContainer().showPage(this);
                setPreviousPage(previous);

                return;
            }
        }
        if (visible && projectsViewer != null) {
            projectsViewer.expandAll();
        }
        super.setVisible(visible);
    }

    private IStatus buildDAA() {
        final IProject projectToBuildDAA = getSelectedProject();
        if (projectToBuildDAA != null
                && lastlyBuiltProject == projectToBuildDAA) {
            return Status.OK_STATUS;
        }
        daaDeployWizard.setDaaLocation(null);

        if (projectToBuildDAA != null) {
            // Generate DAA for new selected projects.

            AbstractMultiProjectDAAGenerationWithProgress daaGenOperation =
                    createDAAGenerationOperation(projectToBuildDAA);

            try {
                getContainer().run(true, true, daaGenOperation);
            } catch (InvocationTargetException e) {
                LOG.error(e);
            } catch (InterruptedException e) {
                if (daaGenOperation.getStatus() != null
                        && daaGenOperation.getStatus().getSeverity() != IStatus.CANCEL) {
                    LOG.error(e);
                }
            }
            IStatus daaGenFailedStatus =
                    new Status(
                            IStatus.ERROR,
                            DaaActivator.PLUGIN_ID,
                            "The process of preparing project DAA (Distributed Application Archive) failed. Please check error log for more information."); //$NON-NLS-1$

            IStatus opStatus = daaGenOperation.getStatus();
            if (opStatus == null) {
                return daaGenFailedStatus;
            } else if (opStatus.getSeverity() > IStatus.OK) {
                return opStatus;
            }

            IFile daaIFile = getGeneratedDAAFromProject(projectToBuildDAA);
            File daaFile =
                    daaIFile != null ? new File(daaIFile.getRawLocation()
                            .toPortableString()) : null;

            if (daaFile != null) {
                daaDeployWizard.setDaaLocation(daaFile);
                // daaDeployWizard.updatePagesForNewDaaLocation();
            } else {
                return daaGenFailedStatus;
            }

            // OK
            lastlyBuiltProject = projectToBuildDAA;
            return Status.OK_STATUS;
        }
        return Status.CANCEL_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (service != null) {
            service.dispose();
            service = null;
        }
        super.dispose();
    }

}

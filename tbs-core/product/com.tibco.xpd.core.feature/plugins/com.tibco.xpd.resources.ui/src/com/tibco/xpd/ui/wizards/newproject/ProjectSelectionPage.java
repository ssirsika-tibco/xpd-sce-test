/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.ProjectStatus;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.destination.ProjectDetailsSection;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.UserInfo;
import com.tibco.xpd.resources.util.UserInfoUtil;

/**
 * Project selection page to select the project and, optionally, the location.
 * This extends <code>WizardNewProjectCreationPage</code> to increase validation
 * of the project location entry.
 * 
 * @author njpatel
 * 
 */
public class ProjectSelectionPage extends WizardNewProjectCreationPage {

    // List of projects in the workspace - lazy loaded
    private IProject[] projects;

    private final ProjectDetailsSection detailsSection;

    private ProjectDetails projectDetails;

    private boolean userChangedId = false;

    /**
     * Set to <code>true</code> to show the project lifecycle section on this
     * page.
     */
    private boolean showLifecycle = true;

    /* SID XPD-2139 Allow Asset Pages To Listen to project name change */
    private String currentProjectName = ""; //$NON-NLS-1$

    /* SID Allow Asset Pages To Listen to project name change */
    private Collection<PropertyChangeListener> propertyChangeListeners =
            new HashSet<PropertyChangeListener>();

    /* Sid ACE-441 Allow forced set of a specific destination environment */
    private String presetDestinationEnv = null;


    /**
     * Constructor.
     * 
     * @param pageName
     */
    public ProjectSelectionPage(String pageName) {
        super(pageName);
        detailsSection = new ProjectDetailsSection();
        projectDetails = ProjectConfigFactory.eINSTANCE.createProjectDetails();
    }

    /**
     * Hide the destination env. selection from this selection page.
     * 
     * @since 3.2
     */
    public void hideDestinationEnvironment() {
        detailsSection.setShowDestinationEnvironment(false);
    }

    /**
     * Hide the project lifecycle entry controls from this selection page. The
     * project lifecycle section allows the setting of the project id, version,
     * status and destination environments.
     * 
     * @since 3.2
     */
    public void hideProjectLifecycle() {
        showLifecycle = false;
    }

    @Override
    public void dispose() {
        propertyChangeListeners.clear();

        detailsSection.dispose();
        super.dispose();
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        if (showLifecycle) {
            updateLabelWidth(parent);

            Label separator =
                    new Label((Composite) getControl(), SWT.SEPARATOR
                            | SWT.HORIZONTAL);
            separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));

            Composite detailsArea =
                    createProjectDetailsArea((Composite) getControl());
            if (detailsArea != null) {
                detailsArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                        true, true));
                Dialog.applyDialogFont(detailsArea);
            }
        }
    }

    /**
     * Update (retrospectively) the label width of the controls added by the
     * super class so that the project version section added here aligns with
     * the labels correctly.
     * 
     * @param parent
     */
    private void updateLabelWidth(Composite parent) {
        if (parent != null) {
            Control[] children = parent.getChildren();
            for (Control child : children) {
                if (child instanceof Label) {
                    Object data = child.getLayoutData();
                    if (parent.getLayout() instanceof GridLayout) {
                        GridData newData = null;
                        if (data instanceof GridData) {
                            newData = (GridData) data;
                        } else if (data == null) {
                            newData = new GridData();
                        }
                        if (newData != null) {
                            int minWidth =
                                    ProjectDetailsSection.STANDARD_LABEL_WIDTH;
                            boolean enforceMinWidth =
                                    (child.computeSize(SWT.DEFAULT, SWT.DEFAULT).x < minWidth);
                            if (enforceMinWidth) {
                                newData.widthHint = minWidth;
                            }
                            child.setLayoutData(newData);
                        }
                    }
                } else if (child instanceof Composite) {
                    updateLabelWidth((Composite) child);
                }
            }
        }
    }

    /**
     * Get the {@link ProjectDetails} set on this page.
     * 
     * @return <code>ProjectDetails</code>.
     * @since 3.2
     */
    public ProjectDetails getProjectDetails() {
        if (projectDetails != null && detailsSection != null) {
            // Update the details
            projectDetails.setId(detailsSection.getId());
            if (detailsSection.getVersion() != null) {
                projectDetails.setVersion(detailsSection.getVersion());
            }

            /*
             * Sid ACE-2980 We don't expose the user to project status anymore so just set it to arbitrary value for
             * now.
             */
            projectDetails.setStatus(ProjectStatus.UNDER_REVISION);

            List<String> destinationIds = detailsSection.getDestinationIds();
            Destinations globalDestinations =
                    projectDetails.getGlobalDestinations();
            if (globalDestinations == null) {
                globalDestinations =
                        ProjectConfigFactory.eINSTANCE.createDestinations();
                projectDetails.setGlobalDestinations(globalDestinations);
            }
            updateDestinations(globalDestinations.getDestination(),
                    destinationIds);
        }
        return projectDetails;
    }

    /**
     * Update the global destinations in the config.
     * 
     * @param currentList
     * @param destinationIds
     * @return
     */
    private EList<Destination> updateDestinations(
            EList<Destination> currentList, List<String> destinationIds) {
        /* Sid ACE-441 Allow forced set of a specific destination environment */
        if (presetDestinationEnv != null && !presetDestinationEnv.isEmpty()) {
            currentList.clear();

            Destination destination =
                    ProjectConfigFactory.eINSTANCE.createDestination();
            destination.setType(presetDestinationEnv);
            currentList.add(destination);

        } else if (currentList != null) {
            EList<Destination> currListCopy =
                    new BasicEList<Destination>(currentList);
            currentList.clear();
            if (destinationIds != null && !destinationIds.isEmpty()) {
                // If the current list already contains the destination then
                // keep it otherwise create new one
                boolean exists;
                for (String id : destinationIds) {
                    exists = false;
                    for (Destination dest : currListCopy) {
                        if (id.equals(dest.getType())) {
                            currentList.add(dest);
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        Destination destination =
                                ProjectConfigFactory.eINSTANCE
                                        .createDestination();
                        destination.setType(id);
                        currentList.add(destination);
                    }
                }
            } else {
                currentList.clear();
            }
        }

        return currentList;
    }

    /**
     * Create the project details area.
     * 
     * @param parent
     * @return project details section.
     */
    private Composite createProjectDetailsArea(Composite parent) {
        ScrolledComposite root = new ScrolledComposite(parent, SWT.V_SCROLL);
        FillLayout layout = new FillLayout();
        root.setLayout(layout);
        root.setExpandHorizontal(true);
        root.setExpandVertical(true);

        // Set the default destination env. if set in the preferences
        UserInfo info = UserInfoUtil.getWorkspacePreferences();
        String userPrefDest = info != null ? info.getDestination() : ""; //$NON-NLS-1$
        if (!userPrefDest.isEmpty()) {
            detailsSection.setDestinationIds(Collections
                    .singletonList(userPrefDest));
        }

        detailsSection.createControl(root);
        root.setContent(detailsSection.getControl());
        detailsSection.getControl().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, false));
        setDetails(projectDetails);
        detailsSection.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                /*
                 * Check if the user changed the id - if they did then we do not
                 * reset with default value and leave it upto the user to update
                 * it
                 */
                if (!userChangedId) {
                    String projectName = getProjectName();
                    String actualId = detailsSection.getId();
                    if (projectName.length() > 0) {
                        String defaultId =
                                ProjectDetailsSection.getDefaultId(projectName);

                        userChangedId = !actualId.equals(defaultId);
                    } else {
                        userChangedId = !actualId.equals(projectName);
                    }
                }
                setPageComplete(validatePage());
            }
        });
        root.setMinSize(detailsSection.getControl().computeSize(SWT.DEFAULT,
                SWT.DEFAULT));
        return root;
    }

    /**
     * Update the details section with the given project details.
     * 
     * @param details
     */
    private void setDetails(ProjectDetails details) {
        if (detailsSection != null && details != null) {
            detailsSection.setId(details.getId());
            detailsSection.setVersion(details.getVersion());

            /* Sid ACE-2980 Project status is no longer shown in new project wizard. */

            if (details.getGlobalDestinations() != null
                    && !details.getGlobalDestinations().getDestination()
                            .isEmpty()) {
                List<String> destinations = new ArrayList<String>();
                for (Destination destination : details.getGlobalDestinations()
                        .getDestination()) {
                    destinations.add(destination.getType());
                }
                detailsSection.setDestinationIds(destinations);
            }
        }
    }

	/**
	 * Sid Changed to internal validate path allows configuration of whether to include next page validity into account.
	 * 
	 * @return <code>true</code> if this page is valid (and optionally, whether subsequent asset config page it valid)
	 */
	private boolean validatePage(boolean checkAssetPageValid)
	{
		boolean valid = super.validatePage();

        /*
         * Update project id
         */
        updateId();

        /*
         * SID XPD-2139: Check if project name has changed and if so then notify
         * listeners.
         */
        String newName = getProjectName();
        if (!currentProjectName.equals(newName)) {
            String oldName = currentProjectName;
            currentProjectName = newName != null ? newName : ""; //$NON-NLS-1$

            notifyNameChange(oldName, newName);
        }
        /* SID Allow Asset Pages To Listen to project name change */

        /*
         * Sid XPD-2198. Should not disallow space as that is specific to
         * desitnaton environment.
         */

        /*
         * XPD-3991: added '%' to the characters deemed disruptive to formation
         * of resource URIs. Check needs to be done here as well as BPM
         * destination-specific ProjectNameValidator rule. Creation of project
         * leads to those resources created not being accessible - so need to be
         * pre-emptive.
         */
        String msgLst =
                ProjectUtil
                        .getDisplayableLstOfInvalidCharacters(ProjectUtil.URI_DISRUPTIVE_CHARACTERS_PATTERN,
                                getProjectName());
        if ((msgLst != null) && (!msgLst.isEmpty())) {
            setErrorMessage(Messages.ProjectSelectionPage_projectHasInvalidChar_message
                    + " " //$NON-NLS-1$
                    + msgLst);
            valid = false;
        }

        /*
         * XPD-3991: prevent project names with leading dots
         */
        if (getProjectName().startsWith(".")) { //$NON-NLS-1$
            setErrorMessage(Messages.ProjectSelectionPage_projectHasInvalidLeadingDotChar_message);
            valid = false;
        }

        if (valid) {
            /*
             * If running on Windows platform then need to make a
             * case-insensitive search for existing project
             */
            if (Platform.getOS().equals(Platform.OS_WIN32)) {
                valid = !projectExists(getProjectName());

                if (!valid) {
                    // Project exist
                    setErrorMessage(Messages.ProjectSelectionPage_projectAlreadyExists_message);
                }
            }

            /*
             * If the page is valid then check if the project location needs
             * further validation
             */
            if (valid && !useDefaults()) {
                IPath locationPath = getLocationPath();

                // Check if location path is empty
                if (locationPath.isEmpty()) {
                    setMessage(Messages.ProjectSelectionPage_enterLocation_shortdesc);
                    valid = false;

                } else if (!Path.EMPTY.isValidPath(locationPath.toString())) {
                    // Not a syntactically valid path
                    setErrorMessage(Messages.ProjectSelectionPage_invlalidProjectContentDir_shortdesc);
                    valid = false;

                } else if (Platform.getLocation().isPrefixOf(locationPath)) {
                    // Project cannot be contained in the workspace
                    setErrorMessage(Messages.ProjectSelectionPage_cannotCreateProject_shortdesc);
                    valid = false;
                } else {
                    // Validate the project location
                    IStatus status =
                            ResourcesPlugin
                                    .getWorkspace()
                                    .validateProjectLocation(getProjectHandle(),
                                            locationPath);

                    if (!(valid = status.isOK())) {
                        setErrorMessage(status.getMessage());
                    }
                }
            }

            if (valid) {
                // Validate the project details if displayed
                if (showLifecycle && detailsSection != null) {
                    IStatus status = detailsSection.validate();
                    if (status.getSeverity() == IStatus.WARNING) {
                    	setMessage(status.getMessage(), IStatus.WARNING);
                    } else if (!status.isOK()) {
                        setErrorMessage(status.getMessage());
                        valid = false;
                    }
                }
            }

			/*
			 * Sid ACE-8813 For most new wizards, allowing Finish from project name entry page is ok as there is always
			 * a default for the initial project asset creation. But sometimes further user input is required on asset
			 * page and hence we shouldn't allow wizard finish directly from the project name entry page.
			 * 
			 * This code leaves the choice down to the individual asset page implementation.
			 */
			if (checkAssetPageValid)
			{
				IWizardPage nextPage = getNextPage();

				if (nextPage instanceof AbstractSpecialFolderAssetWizardPage)
				{
					AbstractSpecialFolderAssetWizardPage assetPage = (AbstractSpecialFolderAssetWizardPage) nextPage;

					if (assetPage.validityRequiredForProjectCreate())
					{
						if (!assetPage.isPageComplete())
						{
							valid = false;
						}
					}
				}
			}
        }

        return valid;
    }

	/**
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#validatePage()
	 *
	 * @return
	 */
	@Override
	protected boolean validatePage()
	{
		/*
		 * This page is complete as far as the consumer is concerned if it AND any subsquent asset config page is valid.
		 */
		return validatePage(true);
	}

	/**
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 *
	 * @return
	 */
	@Override
	public boolean canFlipToNextPage()
	{
		/*
		 * Sid ACE-8813 We can flip to next page if this page is ok (regardless of whether next page is ready or not yet - we have to
		 * be able to flip to it to complete it)
		 */
		return validatePage(false);
	}

	/**
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 *
	 * @return
	 */
	@Override
	public boolean isPageComplete()
	{
		/*
		 * Sid ACE-8813 We need to recheck validity of page including subsequent asset page if necessary. This is in
		 * order to reset the Finish button if the user steps forward in wizard and then comes back to project selection
		 * page after invalidating the next page.
		 */
		return validatePage(true) && super.isPageComplete();
	}

    /**
     * Update the project id. This will react to the project name changes
     * (unless the user has changed the id manually).
     */
    private void updateId() {
        if (!userChangedId && detailsSection != null) {
            String idFromProjectName =
                    ProjectDetailsSection.getDefaultId(getProjectName());
            detailsSection.setId(idFromProjectName);
        }
    }

    /**
     * Check if the project with the given name exists. This will be
     * case-insensitive search and will only be called on the Window's platform.
     * 
     * @param projectName
     * @return <code>true</code> if the project exists with the same name,
     *         <code>false</code> otherwise.
     */
    private boolean projectExists(String projectName) {
        boolean exists = false;
        IProject[] existingProjects = getProjects();

        for (IProject project : existingProjects) {
            if (exists = project.getName().equalsIgnoreCase(projectName)) {
                break;
            }
        }

        return exists;
    }

    /**
     * Get all projects in the workspace.
     * 
     * @return array of all (open and closed) projects.
     */
    private IProject[] getProjects() {

        if (projects == null) {
            projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        }

        return projects;
    }

    /**
     * Sid XPD-2139: For provision of project name change notification.
     * 
     * @param propertyChangeListener
     */
    public void addPropertyChangeListener(
            PropertyChangeListener propertyChangeListener) {
        propertyChangeListeners.add(propertyChangeListener);
    }

    /**
     * Sid XPD-2139: For provision of project name change notification.
     * 
     * @param propertyChangeListener
     */
    public void removePropertyChangeListener(
            PropertyChangeListener propertyChangeListener) {
        propertyChangeListeners.remove(propertyChangeListener);
    }

    /**
     * Sid XPD-2139: For provision of project name change notification.
     * 
     * @param oldName
     * @param newName
     */
    private void notifyNameChange(String oldName, String newName) {
        for (PropertyChangeListener listener : propertyChangeListeners) {
            listener.propertyChange(new PropertyChangeEvent(this,
                    IAssetProjectPropertyChangeListener.PROJECTNAME_PROPERTY,
                    oldName, newName));
        }
    }

    /**
     * @param presetDestinationEnv
     *            the destination to preset (as opposed to allowing the user to
     *            choose)
     */
    public void setPresetDestinationEnv(String presetDestinationEnv) {
        this.presetDestinationEnv = presetDestinationEnv;
    }

    /**
     * Hide the project version controls from the wizard.
     */
    public void hideProjectVersion() {
        detailsSection.setShowProjectVersion(false);
    }

    /**
     * Sid ACE-2980 - Hide the project status controls from the wizard.
     */
    public void hideProjectStatus() {
        detailsSection.setShowProjectStatus(false);
    }

}

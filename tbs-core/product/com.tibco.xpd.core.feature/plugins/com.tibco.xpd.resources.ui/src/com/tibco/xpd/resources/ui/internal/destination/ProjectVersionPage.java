/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.destination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.osgi.framework.Version;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.ProjectStatus;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Project version properties page for the properties of an XPD project. This
 * will allow the user to select the id, version, status and global destinations
 * for a project.
 * 
 * @author njpatel
 * 
 */
public class ProjectVersionPage extends PropertyPage implements
        IWorkbenchPropertyPage {

    private PageBook book;

    private MigratePage migratePage;

    private DetailsPage detailsPage;

    private DetailsPage noDetailsPage;

    private Page activePage;

    private IProject project;

    private ProjectConfig config;

    private ProjectDetails details;

    private ProjectConfigWorkingCopy wc;

    // Keep track of the current version - used for validation
    private String currentVersion;

    /**
     * Project version properties page.
     */
    public ProjectVersionPage() {
    }

    @Override
    public void setElement(IAdaptable element) {
        super.setElement(element);
        if (element instanceof IProject) {
            project = (IProject) element;
            config = XpdResourcesPlugin.getDefault().getProjectConfig(project);
            Assert.isNotNull(config,
                    String.format(Messages.ProjectVersionPage_projectConfigNotAvailable_error_shortdesc,
                            project.getName()));
            details = config.getProjectDetails();

            wc =
                    (ProjectConfigWorkingCopy) WorkingCopyUtil
                            .getWorkingCopyFor(config);
            Assert.isNotNull(config,
                    String.format(Messages.ProjectVersionPage_NoWorkingCopyForConfiguration_error_shortdesc,
                            project.getName()));
        }
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        if (activePage == migratePage) {
            updateButtons(false);
        } else {
            /*
             * Sid SCF-464: Don't blindly force buttons to true on
             * createControl() - leave it up to the state left by the super
             * class (else if content is initially invalid then the apply button
             * will still be enabled.
             */
        }

        /* Sid ACE-3170: We NEVER want the user to set the project version backwards. */
        disableButtonsIfProjectLocked();
    }

    /**
     * Force the disablement of the Apply button if the project is locked.
     * 
     * Always disable and hide Restore Defaults as we don't want to revert version to 1.0.
     */
    public void disableButtonsIfProjectLocked() {
        Button btn = getDefaultsButton();
        if (btn != null && !btn.isDisposed()) {
            btn.setEnabled(false);
            btn.setVisible(false);
        }
        
        btn = getApplyButton();
        if (btn != null && !btn.isDisposed()) {
            boolean enabled;
            try {
                enabled = project != null && !(new GovernanceStateService().isLockedForProduction(project));
                btn.setEnabled(enabled);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean performOk() {
        if (activePage == detailsPage) {
            // Update the details if they have changed
            ProjectDetails details = detailsPage.getDetails();

            if (details != null && wc != null) {
                try {
                    wc.setDetails(details);
                    currentVersion = details.getVersion();
                } catch (IOException e) {
                    ErrorDialog
                            .openError(getShell(),
                                    Messages.ProjectVersionPage_errorDialog_title,
                                    Messages.ProjectVersionPage_failedToApplyVersion_dialog_message,
                                    new Status(
                                            IStatus.ERROR,
                                            XpdResourcesUIActivator.ID,
                                            Messages.ProjectVersionPage_exception_error_shortdesc,
                                            e));
                }
            }
        }
        return true;
    }

    @Override
    protected void performDefaults() {
        detailsPage.setDetails(createDetails());
    }

    @Override
    protected Control createContents(Composite parent) {
        book = new PageBook(parent, SWT.NONE);

        migratePage = new MigratePage();
        migratePage.createControl(book);
        detailsPage = new DetailsPage(true);
        detailsPage.createControl(book);
        noDetailsPage = new DetailsPage(false);
        noDetailsPage.createControl(book);

        initialize();

        return book;
    }

    /**
     * Initialize this preference page.
     */
    private void initialize() {
        activePage = noDetailsPage;

        if (details != null) {
            currentVersion = details.getVersion();
            detailsPage.setDetails(details);
            activePage = detailsPage;
            updateButtons(true);
        } else {
            activePage = migratePage;
            updateButtons(false);
        }

        if (activePage != null) {
            book.showPage(activePage.getControl());
            if (activePage == noDetailsPage) {
                noDefaultAndApplyButton();
            }
        }
    }

    /**
     * Update the enabled status of the buttons.
     * 
     * @param enable
     */
    private void updateButtons(boolean enable) {
        Button btn = getApplyButton();
        if (btn != null && !btn.isDisposed()) {
            btn.setEnabled(enable);
        }
        btn = getDefaultsButton();
        if (btn != null && !btn.isDisposed()) {
            btn.setEnabled(enable);
        }

        /* Sid ACE-3170: We NEVER want the user to set the project version backwards. */
        disableButtonsIfProjectLocked();

    }

    /**
     * Create the {@link ProjectDetails}.
     * 
     * @return
     */
    private ProjectDetails createDetails() {
        ProjectDetails details =
                ProjectConfigFactory.eINSTANCE.createProjectDetails();
        details.setId(ProjectDetailsSection.getDefaultId(project.getName()));

        return details;
    }

    /**
     * Migrate project page.
     * 
     * @author njpatel
     * 
     */
    private class MigratePage extends Page {
        private Composite root;

        private Button migrateBtn;

        @Override
        public void createControl(Composite parent) {
            root = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout();
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            root.setLayout(layout);

            Label lbl = new Label(root, SWT.WRAP);
            lbl.setText(Messages.ProjectVersionPage_migrateProject_longdesc);
            GridData data = new GridData();
            data.widthHint = 400;
            lbl.setLayoutData(data);

            migrateBtn = new Button(root, SWT.NONE);
            migrateBtn.setText(Messages.ProjectVersionPage_migrate_button);
            data = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
            data.widthHint = 100;
            data.verticalIndent = 10;
            migrateBtn.setLayoutData(data);
            migrateBtn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    details = createDetails();
                    initialize();
                }
            });
        }

        @Override
        public Control getControl() {
            return root;
        }

        @Override
        public void setFocus() {
            if (migrateBtn != null && !migrateBtn.isDisposed()) {
                migrateBtn.setFocus();
            }
        }
    }

    /**
     * The details page that will display the text controls to update the
     * details of the project. It will also display the noDetails page if the
     * config is not available for this project.
     * 
     * @author njpatel
     * 
     */
    private class DetailsPage extends Page {
        private Composite root;

        private final boolean canSetDetails;

        private final ProjectDetailsSection detailsSection;

        private ProjectDetails details;

        public DetailsPage(boolean canSetDetails) {
            this.canSetDetails = canSetDetails;
            detailsSection = new ProjectDetailsSection();

            /*
             * Sid ACE-445 - hide the destinations UI from the project lifecycle
             * properties page.
             */
            detailsSection.setShowDestinationEnvironment(false);

            // Create default details
            details = ProjectConfigFactory.eINSTANCE.createProjectDetails();
        }

        @Override
        public void dispose() {
            if (detailsSection != null) {
                detailsSection.dispose();
            }
            super.dispose();
        }

        /**
         * Get the details set on this page.
         * 
         * @return
         */
        public ProjectDetails getDetails() {
            // Update details from the details section
            details.setId(detailsSection.getId());
            details.setVersion(detailsSection.getVersion());

            /*
             * Sid ACE-2980 We don't expose the user to project status anymore so just set it to arbitrary value for
             * now.
             */
            details.setStatus(ProjectStatus.UNDER_REVISION);

            Destinations destinationsElem = details.getGlobalDestinations();
            if (destinationsElem == null) {
                destinationsElem =
                        ProjectConfigFactory.eINSTANCE.createDestinations();
                details.setGlobalDestinations(destinationsElem);
            }
            EList<Destination> destinations = destinationsElem.getDestination();
            destinations.clear();
            for (String id : detailsSection.getDestinationIds()) {
                Destination dest =
                        ProjectConfigFactory.eINSTANCE.createDestination();
                dest.setType(id);
                destinations.add(dest);
            }
            return details;
        }

        /**
         * Set the project details in the UI.
         * 
         * @param details
         */
        public void setDetails(ProjectDetails details) {
            /*
             * If id was already set then don't reset it
             */
            String id = this.details != null ? this.details.getId() : null;
            this.details = details;
            if (id != null && id.length() > 0) {
                this.details.setId(id);
            }
            if (details != null) {
                // Update the details section
                detailsSection.setId(details.getId());
                detailsSection.setVersion(details.getVersion());

                /* Sid ACE-2980 we now just update the status according to the current state of project. */
                detailsSection.updateStatus(project);

                Destinations destinationsElem = details.getGlobalDestinations();
                if (destinationsElem != null
                        && !destinationsElem.getDestination().isEmpty()) {
                    List<String> destIds = new ArrayList<String>();
                    for (Destination dest : destinationsElem.getDestination()) {
                        destIds.add(dest.getType());
                    }
                    detailsSection.setDestinationIds(destIds);
                }
                validatePage();
            }
        }

        @Override
        public void createControl(Composite parent) {
            root = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout();
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            root.setLayout(layout);

            if (canSetDetails) {
                detailsSection.createControl(root);
                detailsSection.getControl().setLayoutData(new GridData(
                        SWT.FILL, SWT.FILL, true, false));
                detailsSection.addModifyListener(new ModifyListener() {
                    @Override
                    public void modifyText(ModifyEvent e) {
                        validatePage();
                    }
                });
            } else {
                Label lbl = new Label(root, SWT.NONE);
                lbl.setText(Messages.ProjectVersionPage_noDetailsCanBeSet_shortdesc);
                GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
                data.horizontalSpan = 2;
                lbl.setLayoutData(data);
            }
        }

        @Override
        public Control getControl() {
            return root;
        }

        @Override
        public void setFocus() {
            if (root != null && !root.isDisposed()) {
                root.setFocus();
            }
        }

        /**
         * Validate this details page and set the validity of this properties
         * page accordingly.
         */
        private void validatePage() {
            if (detailsSection != null) {
                IStatus status = detailsSection.validate();
                if (status.getSeverity() == IStatus.WARNING) {
                	setMessage(status.getMessage(), IStatus.WARNING);
                } else if (!status.isOK()) {
                    setErrorMessage(status.getMessage());
                    setValid(false);
                } else {
                    // Check if the version number has been downgraded - if it
                    // has then add warning
                    if (isVersionDowngraded(currentVersion,
                            detailsSection.getVersion())) {
                        setMessage(String.format(Messages.ProjectVersionPage_versionNumberDowngraded_warning_shortdesc,
                                currentVersion),
                                WARNING);
                    } else {
                        setMessage(null);
                    }
                    setErrorMessage(null);
                    setValid(true);
                }
            }
        }

        /**
         * Check if the new version is lower than the current version.
         * 
         * @param currVersion
         * @param newVersion
         * @return <code>true</code> if new version is lower than current
         *         version, <code>false</code> otherwise.
         */
        private boolean isVersionDowngraded(String currVersion,
                String newVersion) {
            if (currVersion != null && newVersion != null) {
                Version currV = Version.parseVersion(currVersion);
                Version newV = Version.parseVersion(newVersion);

                if (currV != null && newV != null) {
                    return currV.compareTo(newV) > 0;
                }
            }
            return false;
        }
    }
}

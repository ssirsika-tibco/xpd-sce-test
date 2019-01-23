/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.ui.IConfigurationWizard;
import org.eclipse.ui.IWorkbench;
import org.tigris.subversion.subclipse.core.ISVNLocalFolder;
import org.tigris.subversion.subclipse.core.ISVNRemoteFolder;
import org.tigris.subversion.subclipse.core.ISVNRepositoryLocation;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.core.SVNProviderPlugin;
import org.tigris.subversion.subclipse.core.repo.SVNRepositoryLocation;
import org.tigris.subversion.subclipse.core.resources.LocalFolder;
import org.tigris.subversion.subclipse.core.resources.LocalResourceStatus;
import org.tigris.subversion.subclipse.core.resources.SVNWorkspaceRoot;
import org.tigris.subversion.subclipse.core.util.Util;
import org.tigris.subversion.subclipse.ui.SVNUIPlugin;
import org.tigris.subversion.subclipse.ui.WorkspacePathValidator;
import org.tigris.subversion.subclipse.ui.wizards.sharing.ConfigurationWizardAutoconnectPage;
import org.tigris.subversion.subclipse.ui.wizards.sharing.DirectorySelectionPage;
import org.tigris.subversion.subclipse.ui.wizards.sharing.ISVNRepositoryLocationProvider;
import org.tigris.subversion.subclipse.ui.wizards.sharing.RepositorySelectionPage;
import org.tigris.subversion.subclipse.ui.wizards.sharing.SvnFoldersExistWarningPage;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * copy of org.tigris.subversion.subclipse.ui.wizards.sharing.SharingWizard with
 * minor modifications
 * 
 * @author kupadhya
 * @since 16 Dec 2012
 */
public class SVNSharingWizard extends Wizard implements IConfigurationWizard {
    // The project to configure
    private IProject project;

    // The autoconnect page is used if .svn/ directories already exist.
    private ConfigurationWizardAutoconnectPage autoconnectPage;

    // Warning page if .svn/ directories do not exist in root, but exist in
    // subdirectories.
    private SvnFoldersExistWarningPage warningPage;

    // The import page is used if .svn/ directories do not exist.
    private RepositorySelectionPage locationPage;

    // The page that prompts the user for connection information.
    private SVNConfigurationWizardMainPage createLocationPage;

    // The page that prompts the user for module name.
    private DirectorySelectionPage directoryPage;

    // The page that tells the user what's going to happen.
    private SVNSharingWizardFinishPage finishPage;

    // The status of the project directory
    private LocalResourceStatus projectStatus;

    // The repository locations
    private ISVNRepositoryLocation[] locations;

    private boolean shareCanceled;

    public SVNSharingWizard() {
        IDialogSettings workbenchSettings =
                SVNUIPlugin.getPlugin().getDialogSettings();
        IDialogSettings section =
                workbenchSettings.getSection("NewLocationWizard");//$NON-NLS-1$
        if (section == null) {
            section = workbenchSettings.addNewSection("NewLocationWizard");//$NON-NLS-1$
        }
        setDialogSettings(section);
        setNeedsProgressMonitor(true);
        setWindowTitle(Messages.SVNSharingWizard_title);
    }

    /**
     * add pages
     */
    @Override
    public void addPages() {
        ImageDescriptor sharingImage = SVNUtils.getSVNWizardImageDescriptor();
        if (doesSVNDirectoryExist()) {
            // if .svn directory exists, we add the autoconnect page
            autoconnectPage =
                    new ConfigurationWizardAutoconnectPage("autoconnectPage", //$NON-NLS-1$
                            Messages.SVNSharingWizard_autoConnectTitle,
                            sharingImage, projectStatus);
            autoconnectPage.setProject(project);
            autoconnectPage.setDescription(
                    Messages.SVNSharingWizard_autoConnectTitleDescription);
            addPage(autoconnectPage);
        } else {
            try {
                ISVNLocalFolder localFolder =
                        SVNWorkspaceRoot.getSVNFolderFor(project);
                if (localFolder instanceof LocalFolder) {
                    IFolder[] svnFolders = ((LocalFolder) localFolder)
                            .getSVNFolders(null, false);
                    if (svnFolders.length > 0) {
                        warningPage =
                                new SvnFoldersExistWarningPage("warningPage", //$NON-NLS-1$
                                        Messages.SVNSharingWizard_importTitle,
                                        sharingImage, svnFolders);
                        warningPage.setDescription(
                                Messages.SVNSharingWizard_svnFolderExists);
                        addPage(warningPage);
                        // Remember to update getNextPage.
                    }
                }
            } catch (SVNException e) {
                SVNUIPlugin.openError(getShell(),
                        null,
                        null,
                        e,
                        SVNUIPlugin.PERFORM_SYNC_EXEC);
            }

            // otherwise we add :
            // - the repository selection page
            // - the create location page
            // - the module selection page
            // - the finish page

            /*
             * XPD-8420: Jan: Adding RepositorySelectionPage causes problems on
             * unix platforms (probably related to the fact that it internally
             * runs a background task with progress window parented to wizard's
             * shell before shell is fully ready) resulting with empty wizard
             * window. It all works fine on windows though. As
             * RepositorySelectionPage is an external code and the page is used
             * only for convenience we don't display it on non windows
             * platforms.
             * 
             * NOTE! win32 - means all windows platforms (both 32 and 64bit)
             */
            if ("win32".equals(Platform.getOS())) {//$NON-NLS-1$ )
                IRunnableWithProgress runnable = new IRunnableWithProgress() {
                    @Override
                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {
                        locations =
                                SVNUIPlugin.getPlugin().getRepositoryManager()
                                        .getKnownRepositoryLocations(monitor);
                    }
                };
                try {
                    new ProgressMonitorDialog(getShell())
                            .run(true, false, runnable);
                } catch (Exception e) {
                    SVNUIPlugin.openError(getShell(),
                            null,
                            null,
                            e,
                            SVNUIPlugin.LOG_TEAM_EXCEPTIONS);
                }
                if (locations != null && locations.length > 0) {
                    locationPage = new RepositorySelectionPage("importPage", //$NON-NLS-1$
                            Messages.SVNSharingWizard_importTitle,
                            sharingImage);
                    locationPage.setDescription(
                            Messages.SVNSharingWizard_importTitleDescription);
                    addPage(locationPage);
                }
            }
            createLocationPage = new SVNConfigurationWizardMainPage(
                    "createLocationPage", //$NON-NLS-1$
                    Messages.SVNSharingWizard_enterInformation, sharingImage);
            createLocationPage.setDescription(
                    Messages.SVNSharingWizard_enterInformationDescription);
            addPage(createLocationPage);
            createLocationPage.setDialogSettings(getDialogSettings());
            ISVNRepositoryLocationProvider repositoryLocationProvider =
                    new ISVNRepositoryLocationProvider() {
                        @Override
                        public ISVNRepositoryLocation getLocation()
                                throws TeamException {
                            return SVNSharingWizard.this.getLocation();
                        }

                        @Override
                        public IProject getProject() {
                            return SVNSharingWizard.this.getProject();
                        }
                    };
            directoryPage = new DirectorySelectionPage("modulePage", //$NON-NLS-1$
                    Messages.SVNSharingWizard_enterModuleName, sharingImage,
                    repositoryLocationProvider);
            directoryPage.setDescription(
                    Messages.SVNSharingWizard_enterModuleNameDescription);
            addPage(directoryPage);
            finishPage = new SVNSharingWizardFinishPage("finishPage", //$NON-NLS-1$
                    Messages.SVNSharingWizard_readyToFinish, sharingImage,
                    repositoryLocationProvider);
            finishPage.setDescription(
                    Messages.SVNSharingWizard_readyToFinishDescription);
            addPage(finishPage);
        }
    }

    /**
     * check if wizard can finish
     */
    @Override
    public boolean canFinish() {
        IWizardPage page = getContainer().getCurrentPage();
        if (page == directoryPage) {
            return directoryPage.useProjectName()
                    || directoryPage.getDirectoryName() != null;
        } else if (page == finishPage) {
            return true;
        }
        return super.canFinish();
    }

    /**
     * get the next page
     */
    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        if (page == warningPage) {
            if (locationPage == null)
                return createLocationPage;
            else
                return locationPage;
        }
        if (page == autoconnectPage)
            return null;
        if (page == locationPage) {
            if (locationPage.getLocation() == null) {
                return createLocationPage;
            } else {
                return directoryPage;
            }
        }
        if (page == createLocationPage) {
            return directoryPage;
        }
        if (page == directoryPage) {
            return finishPage;
        }
        return null;
    }

    /*
     * @see IWizard#performFinish
     */
    @Override
    public boolean performFinish() {
        shareCanceled = false;
        if (!WorkspacePathValidator.validateWorkspacePath())
            return true;
        final boolean[] result = new boolean[] { true };
        try {
            final boolean[] doSync = new boolean[] { false };
            // final boolean[] projectExists = new boolean[] { false };
            getContainer().run(true /* fork */,
                    true /* cancel */,
                    new IRunnableWithProgress() {
                        @Override
                        public void run(IProgressMonitor monitor)
                                throws InvocationTargetException {
                            try {
                                monitor.beginTask("", 100); //$NON-NLS-1$
                                if (autoconnectPage != null
                                        && (projectStatus != null)) {
                                    // Autoconnect to the repository using svn/
                                    // directories

                                    // Get the repository location (the get will
                                    // add the locatin to the provider)
                                    boolean isPreviouslyKnown =
                                            SVNProviderPlugin.getPlugin()
                                                    .getRepositories()
                                                    .isKnownRepository(
                                                            projectStatus
                                                                    .getUrlString(),
                                                            false);

                                    // Validate the connection if the user wants
                                    // to
                                    boolean validate =
                                            autoconnectPage.getValidate();

                                    if (validate && !isPreviouslyKnown) {
                                        ISVNRepositoryLocation location =
                                                SVNProviderPlugin.getPlugin()
                                                        .getRepository(
                                                                projectStatus
                                                                        .getUrlString());
                                        // Do the validation
                                        try {
                                            location.validateConnection(
                                                    new SubProgressMonitor(
                                                            monitor, 50));
                                        } catch (final TeamException e) {
                                            // Exception validating. We can
                                            // continue if the user wishes.
                                            final boolean[] keep =
                                                    new boolean[] { false };
                                            getShell().getDisplay()
                                                    .syncExec(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            keep[0] =
                                                                    MessageDialog
                                                                            .openQuestion(
                                                                                    getContainer()
                                                                                            .getShell(),
                                                                                    Messages.SVNSharingWizard_validationFailedTitle,
                                                                                    String.format(
                                                                                            Messages.SVNSharingWizard_validationFailedText,
                                                                                            new Object[] {
                                                                                                    e.getStatus()
                                                                                                            .getMessage() }));
                                                        }
                                                    });
                                            if (!keep[0]) {
                                                // Remove the root
                                                try {
                                                    if (!isPreviouslyKnown) {
                                                        SVNProviderPlugin
                                                                .getPlugin()
                                                                .getRepositories()
                                                                .disposeRepository(
                                                                        location);
                                                    }
                                                } catch (TeamException e1) {
                                                    SVNUIPlugin.openError(
                                                            getContainer()
                                                                    .getShell(),
                                                            Messages.exception,
                                                            null,
                                                            e1,
                                                            SVNUIPlugin.PERFORM_SYNC_EXEC);
                                                }
                                                result[0] = false;
                                                return;
                                            }
                                            // They want to keep the connection
                                            // anyway. Fall through.
                                        }
                                    }

                                    // Set the sharing
                                    SVNWorkspaceRoot.setSharing(project,
                                            new SubProgressMonitor(monitor,
                                                    50));
                                } else {
                                    // No svn directory : Share the project
                                    doSync[0] = true;
                                    // Check if the directory exists on the
                                    // server
                                    ISVNRepositoryLocation location = null;
                                    boolean isKnown = false;
                                    boolean createDirectory = true;
                                    try {
                                        location = getLocation();
                                        isKnown = SVNProviderPlugin.getPlugin()
                                                .getRepositories()
                                                .isKnownRepository(
                                                        location.getLocation(),
                                                        false);

                                        // Purge any svn folders that may exists
                                        // in subfolders
                                        SVNWorkspaceRoot
                                                .getSVNFolderFor(project)
                                                .unmanage(null);

                                        // check if the remote directory already
                                        // exist
                                        String remoteDirectoryName =
                                                getRemoteDirectoryName();
                                        ISVNRemoteFolder folder =
                                                location.getRemoteFolder(
                                                        remoteDirectoryName);
                                        if (folder.exists(
                                                new SubProgressMonitor(monitor,
                                                        50))) {
                                            // projectExists[0] = true;
                                            // final boolean[] sync = new
                                            // boolean[] {true};
                                            if (autoconnectPage == null) {
                                                getShell().getDisplay()
                                                        .syncExec(
                                                                new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        // sync[0]
                                                                        // =
                                                                        // false;
                                                                        if (!MessageDialog
                                                                                .openQuestion(
                                                                                        getShell(),
                                                                                        Messages.SVNSharingWizard_couldNotImport,
                                                                                        Messages.SVNSharingWizard_couldNotImportLong)) {
                                                                            shareCanceled =
                                                                                    true;
                                                                            return;
                                                                        }
                                                                    }
                                                                });
                                                if (shareCanceled)
                                                    return;
                                            }
                                            // result[0] = sync[0];
                                            // doSync[0] = sync[0];
                                            // return;
                                            createDirectory = false;
                                        }
                                    } catch (TeamException e) {
                                        SVNUIPlugin.openError(getShell(),
                                                null,
                                                null,
                                                e,
                                                SVNUIPlugin.PERFORM_SYNC_EXEC);
                                        // if (!isKnown && location != null)
                                        // location.flushUserInfo();
                                        result[0] = false;
                                        doSync[0] = false;
                                        return;
                                    }

                                    // Add the location to the provider if it is
                                    // new
                                    if (!isKnown) {
                                        SVNProviderPlugin.getPlugin()
                                                .getRepositories()
                                                .addOrUpdateRepository(
                                                        location);
                                    }

                                    // Create the remote module for the project
                                    SVNWorkspaceRoot.shareProject(location,
                                            project,
                                            getRemoteDirectoryName(),
                                            finishPage.getComment(),
                                            createDirectory,
                                            new SubProgressMonitor(monitor,
                                                    50));

                                    try {
                                        project.refreshLocal(
                                                IProject.DEPTH_INFINITE,
                                                new SubProgressMonitor(monitor,
                                                        50));
                                    } catch (CoreException ce) {
                                        throw new TeamException(ce.getStatus());
                                    }

                                }
                            } catch (TeamException e) {
                                throw new InvocationTargetException(e);
                            } finally {
                                monitor.done();
                            }
                        }
                    });

            if (shareCanceled) {
                return false;
            }

        } catch (InterruptedException e) {
            return true;
        } catch (InvocationTargetException e) {
            SVNUIPlugin.openError(getContainer().getShell(), null, null, e);
        }

        return result[0];
    }

    /**
     * Return an ISVNRepositoryLocation
     */
    protected ISVNRepositoryLocation getLocation() throws TeamException {
        // If there is an autoconnect page then it has the location
        if (autoconnectPage != null) {
            return autoconnectPage.getLocation();
        }

        // If the import page has a location, use it.
        if (locationPage != null) {
            ISVNRepositoryLocation location = locationPage.getLocation();
            if (location != null)
                return location;
        }

        // Otherwise, get the location from the create location page
        getShell().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                createLocationPage.finish(new NullProgressMonitor());
            }
        });
        Properties properties = createLocationPage.getProperties();
        ISVNRepositoryLocation location =
                SVNRepositoryLocation.fromProperties(properties);
        return location;
    }

    /**
     * Return the directory name in the remote repository where to put the
     * project
     */
    private String getRemoteDirectoryName() {
        // If there is an autoconnect page then it has the module name
        if (autoconnectPage != null) {
            // return autoconnectPage.getSharing().getRepository();
            return Util.getLastSegment(
                    autoconnectPage.getSharingStatus().getUrlString());
        }
        String moduleName = directoryPage.getDirectoryName();
        if (moduleName == null)
            moduleName = project.getName();
        return moduleName;
    }

    /*
     * @see IConfigurationWizard#init(IWorkbench, IProject)
     */
    @Override
    public void init(IWorkbench workbench, IProject project) {
        this.project = project;
    }

    /**
     * check if there is a valid svn directory
     */
    private boolean doesSVNDirectoryExist() {
        // Determine if there is an existing .svn/ directory from which
        // configuration
        // information can be retrieved.
        boolean isSVNFolder = false;
        try {
            projectStatus = SVNWorkspaceRoot.peekResourceStatusFor(project);
            ;
            isSVNFolder = (projectStatus != null) && projectStatus.hasRemote();

        } catch (final SVNException e) {
            Shell shell = null;
            // If this is called before the pages have been added, getContainer
            // will return null
            if (getContainer() != null) {
                shell = getContainer().getShell();
            }
            SVNUIPlugin.openError(shell, null, null, e);

        }
        return isSVNFolder;
    }

    public IProject getProject() {
        return project;
    }

}

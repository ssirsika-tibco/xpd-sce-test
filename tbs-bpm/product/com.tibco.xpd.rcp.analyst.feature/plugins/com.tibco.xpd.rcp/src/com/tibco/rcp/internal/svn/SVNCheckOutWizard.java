/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.team.core.TeamException;
import org.tigris.subversion.subclipse.core.ISVNRemoteFolder;
import org.tigris.subversion.subclipse.core.ISVNRepositoryLocation;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.core.SVNProviderPlugin;
import org.tigris.subversion.subclipse.core.client.OperationManager;
import org.tigris.subversion.subclipse.core.client.OperationProgressNotifyListener;
import org.tigris.subversion.subclipse.ui.SVNUIPlugin;
import org.tigris.subversion.svnclientadapter.ISVNClientAdapter;
import org.tigris.subversion.svnclientadapter.SVNClientException;
import org.tigris.subversion.svnclientadapter.SVNRevision;

import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;

/**
 * This class is a copy of
 * org.tigris.subversion.subclipse.ui.wizards.CheckoutWizard. To customise the
 * wizard for some pages this was least time consuming option.
 * 
 * @author kupadhya
 * @since 28 Nov 2012
 */
public class SVNCheckOutWizard extends Wizard {

    private SVNCheckoutWizardLocationPage locationPage;

    private SVNConfigurationWizardMainPage createLocationPage;

    private SVNCheckoutWizardSelectionPage selectionPage;

    private SVNCheckOutWizardChkOutLocPage projectPage;

    private static final SVNRevision HEAD_SVN_REVISION = SVNRevision.HEAD;

    /** See class DepthComboHelper, the values have changed in subclipse 1.8.x. */
    private static final int DEPTH_INFINITY = 5;

    private static final boolean IGNORE_EXTERNALS = true;

    private static final boolean FORCE = true;

    private String projectName;

    private ISVNRepositoryLocation repositoryLocation;

    private ISVNRemoteFolder[] remoteFolders;

    private String chkOutLocation;

    public SVNCheckOutWizard() {
        super();
        setWindowTitle(Messages.SVNCheckOutWizard_title);
        setNeedsProgressMonitor(true);
    }

    public SVNCheckOutWizard(ISVNRemoteFolder[] remoteFolders) {
        this();
        this.remoteFolders = remoteFolders;
    }

    @Override
    public void addPages() {
        setNeedsProgressMonitor(true);
        ImageDescriptor imageDescriptor =
                SVNUtils.getSVNWizardImageDescriptor();
        locationPage = new SVNCheckoutWizardLocationPage("locationPage", //$NON-NLS-1$ 
                Messages.CheckoutWizardLocationPage_heading, imageDescriptor);

        addPage(locationPage);
        createLocationPage =
                new SVNConfigurationWizardMainPage(
                        "createLocationPage", //$NON-NLS-1$ 
                        Messages.CheckoutWizardLocationPage_heading,
                        imageDescriptor);
        addPage(createLocationPage);
        selectionPage = new SVNCheckoutWizardSelectionPage("selectionPage", //$NON-NLS-1$ 
                Messages.CheckoutWizardLocationPage_heading, imageDescriptor);
        addPage(selectionPage);
        projectPage = new SVNCheckOutWizardChkOutLocPage("projectPage", //$NON-NLS-1$ 
                Messages.CheckoutWizardLocationPage_heading, imageDescriptor);
        addPage(projectPage);
    }

    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        return getNextPage(page, true);
    }

    public IWizardPage getNextPage(IWizardPage page, boolean aboutToShow) {
        if (page == locationPage) {
            if (locationPage.createNewLocation())
                return createLocationPage;
            else {
                if (aboutToShow)
                    selectionPage.setLocation(repositoryLocation);
                return selectionPage;
            }
        }
        if (page == createLocationPage) {
            if (aboutToShow) {
                ISVNRepositoryLocation newLocation = createLocation();
                if (newLocation != null) {
                    locationPage.refreshLocations();
                    selectionPage.setLocation(newLocation);
                }
            }
            return selectionPage;
        }
        if (page == selectionPage) {
            return projectPage;
        }
        return null;
    }

    private ISVNRepositoryLocation createLocation() {
        createLocationPage.finish(new NullProgressMonitor());
        Properties properties = createLocationPage.getProperties();
        final ISVNRepositoryLocation[] root = new ISVNRepositoryLocation[1];
        SVNProviderPlugin provider = SVNProviderPlugin.getPlugin();
        try {
            root[0] = provider.getRepositories().createRepository(properties);
            // Validate the connection info. This process also determines the
            // rootURL
            try {
                new ProgressMonitorDialog(getShell()).run(true,
                        true,
                        new IRunnableWithProgress() {
                            @Override
                            public void run(IProgressMonitor monitor)
                                    throws InvocationTargetException {
                                try {
                                    root[0].validateConnection(monitor);
                                } catch (TeamException e) {
                                    throw new InvocationTargetException(e);
                                }
                            }
                        });
            } catch (InterruptedException e) {
                return null;
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                if (t instanceof TeamException) {
                    throw (TeamException) t;
                }
            }
            provider.getRepositories().addOrUpdateRepository(root[0]);
        } catch (TeamException e) {
            if (root[0] == null) {
                // Exception creating the root, we cannot continue
                SVNUIPlugin.openError(getContainer().getShell(),
                        Messages.NewLocationWizard_exception,
                        null,
                        e);
                return null;
            } else {
                // Exception validating. We can continue if the user wishes.
                IStatus error = e.getStatus();
                if (error.isMultiStatus() && error.getChildren().length == 1) {
                    error = error.getChildren()[0];
                }

                boolean keep = false;
                if (error.isMultiStatus()) {
                    SVNUIPlugin.openError(getContainer().getShell(),
                            Messages.NewLocationWizard_validationFailedTitle,
                            null,
                            e);
                } else {
                    keep =
                            MessageDialog
                                    .openQuestion(getContainer().getShell(),
                                            Messages.NewLocationWizard_validationFailedTitle,
                                            String.format(Messages.NewLocationWizard_validationFailedText,
                                                    new Object[] { error
                                                            .getMessage() }));
                }
                try {
                    if (keep) {
                        provider.getRepositories()
                                .addOrUpdateRepository(root[0]);
                    } else {
                        provider.getRepositories().disposeRepository(root[0]);
                    }
                } catch (TeamException e1) {
                    SVNUIPlugin.openError(getContainer().getShell(),
                            Messages.exception,
                            null,
                            e1);
                    return null;
                }
                if (keep)
                    return root[0];
            }
        }
        return root[0];
    }

    @Override
    public boolean canFinish() {
        IWizardPage page = getContainer().getCurrentPage();
        if (page == projectPage) {
            return projectPage.isPageComplete();
        }
        return super.canFinish();
    }

    public void setLocation(ISVNRepositoryLocation repositoryLocation) {
        this.repositoryLocation = repositoryLocation;
    }

    @Override
    public boolean performFinish() {
        chkOutLocation = projectPage.getLocation();
        /*
         * Remove the current resource before proceeding with the check-out.
         */
        RCPResourceManager.clearCurrentResource();
        try {
            getContainer().run(true, false, new IRunnableWithProgress() {

                @Override
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException, InterruptedException {
                    // check out projects from SVN
                    // do not try to import projects into workspace
                    monitor.beginTask(Messages.SVNCheckOutWizard_PerformFinishProgressMonitor,
                            100);
                    checkOutProjects(remoteFolders, monitor, getContainer()
                            .getShell(), getCheckOutLocation());
                }
            });
        } catch (InvocationTargetException e) {
        } catch (InterruptedException e) {
        }
        return true;
    }

    public void setRemoteFolders(ISVNRemoteFolder[] remoteFolders) {
        this.remoteFolders = remoteFolders;
    }

    public ISVNRemoteFolder[] getRemoteFolders() {
        return remoteFolders;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getCheckOutLocation() {
        return chkOutLocation;
    }

    private void checkOutProjects(final ISVNRemoteFolder[] folders,
            final IProgressMonitor monitor, final Shell shell,
            final String chkoutLocation) {
        monitor.worked(10);
        int eachIterationWork = 60 / folders.length;
        try {
            for (int i = 0; i < folders.length; i++) {
                ISVNRemoteFolder isvnRemoteFolder = folders[i];
                ISVNClientAdapter svnClient;
                svnClient = isvnRemoteFolder.getRepository().getSVNClient();
                File checkOutLocation =
                        new File(chkoutLocation + File.separator
                                + isvnRemoteFolder.getName());
                checkoutProject(monitor,
                        isvnRemoteFolder,
                        svnClient,
                        checkOutLocation);
                monitor.worked(eachIterationWork);
            }
        } catch (SVNException e) {
        } finally {
            monitor.worked(30);
            monitor.done();
        }
    }

    /**
     * @param pm
     * @param resource
     * @param svnClient
     * @param destPath
     * @throws SVNException
     */
    private void checkoutProject(final IProgressMonitor pm,
            ISVNRemoteFolder resource, ISVNClientAdapter svnClient,
            File destPath) throws SVNException {
        final IProgressMonitor subPm =
                org.tigris.subversion.subclipse.core.Policy
                        .infiniteSubMonitorFor(pm, 800);
        try {
            subPm.beginTask("", //$NON-NLS-1$
                    org.tigris.subversion.subclipse.core.Policy.INFINITE_PM_GUESS_FOR_CHECKOUT);
            // subPm.setTaskName("");
            OperationManager.getInstance().beginOperation(svnClient,
                    new OperationProgressNotifyListener(subPm));
            svnClient.checkout(resource.getUrl(),
                    destPath,
                    SVNCheckOutWizard.HEAD_SVN_REVISION,
                    SVNCheckOutWizard.DEPTH_INFINITY,
                    SVNCheckOutWizard.IGNORE_EXTERNALS,
                    SVNCheckOutWizard.FORCE);
        } catch (SVNClientException e) {
            throw new SVNException(Messages.SVNCheckOutWizard_CannotCheckout);
        } finally {
            OperationManager.getInstance().endOperation();
            subPm.done();
        }
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.ui.precompile.resolutions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.core.RepositoryProvider;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.subscribers.Subscriber;
import org.tigris.subversion.subclipse.core.ISVNLocalResource;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.core.resources.SVNWorkspaceRoot;
import org.tigris.subversion.svnclientadapter.ISVNProperty;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.precompile.PreCompileContributorManager;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to set 'DeferFileDelete' SVN property on all the pre-compiled
 * projects in the workspace
 * 
 * PLEASE NOTE THAT the code in this class is copied from
 * SetSvnDeferFileDeleteResolution in N2 Feature (bx.validation plugin)
 * 
 * @author bharge
 * @since 1 May 2015
 */
public class SetSvnDeferFileDeleteOnAllPrecompileProjectsInWSResolution
        implements IConfigurableResolutionLabel, IResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {

        IProject[] projects = ProjectUtil.getAllStudioProjects();

        boolean needToShowDialog = false;

        for (IProject project : projects) {

            if (ProjectUtil.isPrecompiledProject(project)) {

                final IFolder precompileFolder =
                        project.getFolder(PreCompileContributorManager
                                .getInstance().PRECOMPILE_OUTPUTFOLDER_NAME);

                RepositoryProvider repositoryProvider =
                        RepositoryProvider.getProvider(project);
                if (precompileFolder.exists() && repositoryProvider != null) {

                    Subscriber repoSubscriber =
                            repositoryProvider.getSubscriber();
                    if (repoSubscriber != null) {

                        try {

                            if (precompileFolder != null
                                    && repoSubscriber
                                            .isSupervised(precompileFolder)) {

                                boolean doItOnSubfolders = true;
                                boolean changed =
                                        setSvnDeferFileDeleteProperty(precompileFolder,
                                                doItOnSubfolders);
                                if (changed) {

                                    needToShowDialog = changed;
                                }

                                ProgressMonitorDialog monitorDialog =
                                        new ProgressMonitorDialog(Display
                                                .getDefault().getActiveShell());
                                try {

                                    monitorDialog.run(/* fork */false,
                                            /* cancelable */true,
                                            new IRunnableWithProgress() {

                                                @Override
                                                public void run(
                                                        IProgressMonitor monitor)
                                                        throws InvocationTargetException,
                                                        InterruptedException {

                                                    try {
                                                        monitor.beginTask("", //$NON-NLS-1$
                                                                1);
                                                        monitor.setTaskName(Messages.SetSvnDeferFileDeleteResolution_SettinSvnProp_message);
                                                        if (!monitor
                                                                .isCanceled()) {

                                                            monitor.worked(1);
                                                            monitor.subTask(""); //$NON-NLS-1$

                                                        }
                                                    } finally {

                                                        monitor.done();
                                                    }

                                                }
                                            });

                                } catch (InterruptedException e) {

                                    ValidationActivator.getDefault()
                                            .getLogger().error(e);
                                } catch (InvocationTargetException e) {

                                    ValidationActivator.getDefault()
                                            .getLogger().error(e);
                                }

                                try {
                                    /*
                                     * Touch resources so they are re-built and
                                     * re-validated.
                                     */
                                    project.touch(new NullProgressMonitor());
                                } catch (CoreException e) {

                                    ValidationActivator.getDefault()
                                            .getLogger().error(e);
                                }

                            }
                        } catch (TeamException e) {

                            ValidationActivator.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }
            }
        }
        if (needToShowDialog) {

            MessageDialog
                    .openWarning(null,
                            Messages.SetSvnDeferFileDeleteResolution_SvnCommitWarning_title,
                            Messages.SetSvnDeferFileDeleteResolution_SvnCommit_message);
        }
    }

    /**
     * Sets "DeferFileDelete" SVN to 'true' on a provided folder.
     * 
     * @param folder
     *            folder to set property on.
     * @param doItOnSubFolders
     *            boolean flag to decide if this property is to be on sub
     *            folders
     * @return 'true' if the SVN property was set ('false' otherwise).
     * @throws CoreException
     */
    private boolean setSvnDeferFileDeleteProperty(IFolder folder,
            boolean doItOnSubFolders) {

        ISVNLocalResource svnFolder =
                SVNWorkspaceRoot.getSVNResourceFor(folder);
        if (svnFolder != null) {

            try {

                if (svnFolder.isManaged()) {

                    ISVNProperty deferFileDelete =
                            svnFolder.getSvnProperty("DeferFileDelete"); //$NON-NLS-1$
                    if (deferFileDelete == null
                            || !"true".equals(deferFileDelete.getValue())) { //$NON-NLS-1$

                        svnFolder.setSvnProperty("DeferFileDelete", "true", //$NON-NLS-1$ //$NON-NLS-2$
                                /* recurse */false);
                        /*
                         * Apply this property on all the sub folders under
                         * .precompiled folder
                         */
                        if (doItOnSubFolders) {

                            try {

                                IResource[] members = folder.members();
                                for (IResource res : members) {

                                    if (res instanceof IFolder) {

                                        IFolder subFolder = (IFolder) res;
                                        setSvnDeferFileDeleteProperty(subFolder,
                                                true);
                                    }
                                }
                            } catch (CoreException e) {

                                XpdResourcesPlugin.getDefault().getLogger()
                                        .error(e.getMessage());
                            }
                        }

                        return true;
                    }
                }
            } catch (SVNException e) {

                ValidationActivator.getDefault().getLogger().error(e);
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel#getConfigurableResolutionLabel(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesLabel
     * @param marker
     * @return
     */
    @Override
    public String getConfigurableResolutionLabel(String propertiesLabel,
            IMarker marker) {

        return propertiesLabel;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel#getConfigurableResolutionDescription(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesDescription
     * @param marker
     * @return
     */
    @Override
    public String getConfigurableResolutionDescription(
            String propertiesDescription, IMarker marker) {

        return propertiesDescription;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
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

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to set 'DeferFileDelete' SVN property on all the BOM generated
 * special folders in the workspace.
 * 
 * @author jarciuch
 * @since 22 Apr 2014
 */
public class SetSvnDeferFileDeleteOnAllGenBOMInWSResolution implements
        IResolution, IConfigurableResolutionLabel {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    public void run(IMarker marker) throws ResolutionException {

        boolean needsToShowDialog = false;
        IProject[] allStudioProjects = ProjectUtil.getAllStudioProjects();
        for (IProject project : allStudioProjects) {

            final IFolder bomGenSf =
                    WsdlToBomBuilder.getGeneratedBOMFolder(project, false);

            RepositoryProvider repositoryProvider =
                    RepositoryProvider.getProvider(project);
            if (repositoryProvider != null) {

                Subscriber repoSubscriber = repositoryProvider.getSubscriber();
                if (repoSubscriber != null) {
                    try {

                        if (bomGenSf != null
                                && repoSubscriber.isSupervised(bomGenSf)) {

                            boolean changed =
                                    setSvnDeferFileDeleteProperty(bomGenSf);
                            if (changed) {

                                needsToShowDialog = true;
                            }
                            ProgressMonitorDialog monitorDialog =
                                    new ProgressMonitorDialog(Display
                                            .getDefault().getActiveShell());

                            try {

                                monitorDialog.run(/* fork */false,
                                        /* cancelable */true,
                                        new IRunnableWithProgress() {

                                            public void run(
                                                    IProgressMonitor monitor)
                                                    throws InvocationTargetException,
                                                    InterruptedException {

                                                try {

                                                    monitor.beginTask("", //$NON-NLS-1$
                                                            1);
                                                    monitor.setTaskName(Messages.SetSvnDeferFileDeleteResolution_SettinSvnProp_message);
                                                    if (!monitor.isCanceled()) {

                                                        monitor.worked(1);
                                                        monitor.subTask(""); //$NON-NLS-1$

                                                    }
                                                } finally {
                                                    monitor.done();
                                                }

                                            }
                                        });

                            } catch (InterruptedException e) {
                                BxValidationPlugin.getDefault().getLogger()
                                        .error(e);
                            } catch (InvocationTargetException e) {
                                BxValidationPlugin.getDefault().getLogger()
                                        .error(e);
                            }

                            try {
                                /*
                                 * Touch resources so they are re-built and
                                 * re-validated.
                                 */
                                project.touch(new NullProgressMonitor());
                            } catch (CoreException e) {
                                BxValidationPlugin.getDefault().getLogger()
                                        .error(e);
                            }

                        }
                    } catch (TeamException e) {
                        BxValidationPlugin.getDefault().getLogger().error(e);
                    }
                }
            }
        }
        if (needsToShowDialog) {

            MessageDialog
                    .openWarning(null,
                            Messages.SetSvnDeferFileDeleteResolution_SvnCommitWarning_title,
                            String.format(Messages.SetSvnDeferFileDeleteResolution_SvnCommit_message,
                                    WsdlToBomBuilder.GENERATED_BOM_FOLDER));
        }
    }

    /**
     * Sets "DeferFileDelete" SVN to 'true' on a provided folder.
     * 
     * @param folder
     *            folder to set property on.
     * @return 'true' if the SVN property was set ('false' otherwise).
     */
    private boolean setSvnDeferFileDeleteProperty(IFolder folder) {
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
                        return true;
                    }
                }
            } catch (SVNException e) {
                BxValidationPlugin.getDefault().getLogger().error(e);
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
    public String getConfigurableResolutionDescription(
            String propertiesDescription, IMarker marker) {
        return propertiesDescription;
    }

}

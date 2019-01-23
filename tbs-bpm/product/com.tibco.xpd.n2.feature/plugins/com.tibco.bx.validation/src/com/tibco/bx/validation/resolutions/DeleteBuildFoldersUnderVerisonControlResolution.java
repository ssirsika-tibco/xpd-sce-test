/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.bx.validation.internal.Messages;
import com.tibco.bx.validation.rules.BuildFoldersInVersionControlRule;
import com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to delete the build folders that should not be under version
 * control (.bpm etc)
 * 
 * @author aallway
 * @since 21 Dec 2012
 */
public class DeleteBuildFoldersUnderVerisonControlResolution implements
        IResolution, IConfigurableResolutionLabel {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    public void run(IMarker marker) throws ResolutionException {

        IResource resource = marker.getResource();
        if (resource instanceof IProject) {
            IProject project = (IProject) resource;

            final List<IFolder> buildFolders =
                    BuildFoldersInVersionControlRule.getBuildFolders(project);

            if (buildFolders.size() > 0) {
                ProgressMonitorDialog monitorDialog =
                        new ProgressMonitorDialog(Display.getDefault()
                                .getActiveShell());
                try {
                    monitorDialog.run(false,
                            false,
                            new IRunnableWithProgress() {

                                public void run(IProgressMonitor monitor)
                                        throws InvocationTargetException,
                                        InterruptedException {

                                    try {
                                        try {
                                            monitor.beginTask("", //$NON-NLS-1$
                                                    buildFolders.size());
                                            monitor.setTaskName(Messages.AddReferredProjectDestinationsResolution_DeletingBuilderFolders_label);

                                            for (IFolder buildFolder : buildFolders) {

                                                if (monitor.isCanceled()) {

                                                    break;
                                                }

                                                if (buildFolder.exists()) {
                                                    monitor.subTask(buildFolder
                                                            .getFullPath()
                                                            .toString());

                                                    buildFolder
                                                            .delete(true,
                                                                    false,
                                                                    new NullProgressMonitor());
                                                }
                                                monitor.worked(1);

                                            }
                                            monitor.subTask(""); //$NON-NLS-1$

                                            if (!monitor.isCanceled()) {
                                                MessageDialog
                                                        .openWarning(null,
                                                                Messages.AddReferredProjectDestinationsResolution_PleaseCommitProject_title,
                                                                Messages.AddReferredProjectDestinationsResolution_PleaseCommitProject_mesage);
                                            }
                                        } finally {
                                            monitor.done();
                                        }

                                    } catch (CoreException e) {
                                        BxValidationPlugin.getDefault()
                                                .getLogger().error(e);
                                    }

                                }
                            });

                } catch (InterruptedException e) {

                } catch (InvocationTargetException e) {
                    BxValidationPlugin.getDefault().getLogger().error(e);
                }
            }
        }
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

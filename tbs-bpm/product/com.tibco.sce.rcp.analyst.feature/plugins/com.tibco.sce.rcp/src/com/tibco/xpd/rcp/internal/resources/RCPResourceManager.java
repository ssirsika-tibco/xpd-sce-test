/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.progress.UIJob;

import com.tibco.xpd.rcp.Application;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.OverviewEditorInput;
import com.tibco.xpd.rcp.internal.overview.OverviewEditor;
import com.tibco.xpd.rcp.internal.utils.SaveChangesDialog;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Manager class for the rcp resource being edited.
 * 
 * @author njpatel
 * @since 27 Nov 2012
 */
public final class RCPResourceManager {

    private static IRCPContainer resourceBeingEdited;

    private static List<IOpenResourceListener> listeners = Collections
            .synchronizedList(new ArrayList<IOpenResourceListener>());

    /**
     * Get the resource being edited.
     * 
     * @return resource that is open for edit in this instance,
     *         <code>null</code> if no resource is open.
     */
    public static IRCPContainer getResource() {
        return resourceBeingEdited;
    }

    /**
     * Called when a new application is created. This will ensure all listeners
     * are notified and the Overview page is opened for the application.
     * 
     * @param resource
     *            new application created
     * @throws CoreException
     */
    public static void addNewApplication(IRCPContainer resource)
            throws CoreException {
        Assert.isNotNull(resource, "Resource cannot be null"); //$NON-NLS-1$
        try {
            resourceBeingEdited = resource;

            updateListeners();
            updateWindowTitle(resourceBeingEdited);
            resourceBeingEdited
                    .addChangeListener(new RCPResourceChangeListener() {
                        @Override
                        public void resourceChanged(IRCPResource resource,
                                RCPResourceChangeEvent event) {
                            if (event.eventType == RCPResourceEventType.ARCHIVE_CREATED) {
                                // Update the title as the project name may
                                // have changed
                                updateWindowTitle(resourceBeingEdited);
                            } else if (event.eventType == RCPResourceEventType.DISPOSED) {
                                updateWindowTitle(null);
                            }
                        }
                    });
            openOverview(resourceBeingEdited);
        } catch (Exception e) {
            throw new CoreException(new Status(IStatus.ERROR,
                    RCPActivator.PLUGIN_ID,
                    Messages.Application_createFileFailed_error_message, e));
        }
    }

    /**
     * Update the title of the main window with the path/name of the given
     * resource.
     * 
     * @param resource
     */
    private static void updateWindowTitle(IRCPContainer resource) {
        String title = null;
        if (resource != null) {
            IPath path = resource.getPath();
            if (path != null) {
                title = path.toOSString();
            } else {
                title = resource.getName();
            }
        }
        Application.updateWindowTitle(title);
    }

    /**
     * Open the given file (this will create a project in the workspace).
     * 
     * @param workbench
     * @param fileToOpen
     *            file to open for edit
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    public static void open(IWorkbench workbench, File fileToOpen,
            IProgressMonitor monitor) throws CoreException {
        if (fileToOpen != null && fileToOpen.exists()) {
            IRCPContainer newResource = null;
            try {

                newResource = RCPResourceFactory.createResource(fileToOpen);

                if (newResource != null) {
                    /*
                     * If there is an application already open then dispose it
                     * (this will clear the workspace of the projects from this
                     * application).
                     */
                    clearCurrentResource();

                    if (newResource.open(monitor)) {
                        // updateComplexPicker(projects);
                        Collection<ProjectResource> projects =
                                newResource.getProjectResources();
                        if (projects != null && !projects.isEmpty()) {

                            resourceBeingEdited = newResource;

                            /*
                             * Don't open overview editor if editing a model
                             * resource.
                             */
                            if (!(newResource instanceof ModelResource)) {
                                openOverview(newResource);
                            }

                            updateListeners();
                            updateWindowTitle(resourceBeingEdited);
                        } else {
                            // No project was created so dispose the resource
                            if (newResource != null) {
                                newResource.dispose();
                                newResource = null;
                            }
                        }
                    } else {
                        /*
                         * User cancelled the open (possibly did not want to
                         * migrate projects).
                         */
                        newResource.dispose();
                        newResource = null;
                    }
                }
            } catch (CoreException e) {
                if (newResource != null) {
                    /*
                     * Delete and dispose the resource as there was an error
                     * during opening of this resource.
                     */
                    newResource.dispose();
                    newResource = null;
                }
                throw e;
            } finally {
                monitor.done();
            }
        }
    }

    /**
     * Ask user to save the current resource, if dirty. Note: This should be
     * called from the UI thread.
     * 
     * @param shell
     * @return
     */
    public static boolean saveCurrentResource(Shell shell) {
        /*
         * If the current resource, if any, is dirty then get user to save first
         * before continuing
         */
        final IRCPContainer resource = getResource();
        if (resource != null && resource.isDirty()) {
            SaveChangesDialog dlg = new SaveChangesDialog(shell, resource);
            switch (dlg.open()) {
            case SaveChangesDialog.YES_ID:
                try {
                    ResourcesPlugin.getWorkspace()
                            .run(new IWorkspaceRunnable() {
                                @Override
                                public void run(IProgressMonitor monitor)
                                        throws CoreException {
                                    if (!resource.save(monitor)) {
                                        // User cancelled save
                                        throw new CoreException(
                                                Status.CANCEL_STATUS);
                                    }
                                }
                            },
                                    null);
                } catch (CoreException e) {

                    if (e.getStatus().getSeverity() != IStatus.CANCEL) {
                        /*
                         * report error if this is not a user cancel
                         */
                        RCPActivator.getDefault().getLogger().error(e);
                        ErrorDialog
                                .openError(shell,
                                        Messages.OpenAction_saveFailed_errorDlg_title,
                                        Messages.OpenAction_saveFailed_errorDlg_message,
                                        e.getStatus());
                    }
                    return false;
                }
                break;

            case SaveChangesDialog.CANCEL_ID:
                // User cancelled the save so don't proceed
                return false;
            }
        }

        return true;
    }

    /**
     * Clear the resource being edited currently. This will dispose the resource
     * and close all editors.
     * 
     */
    public static void clearCurrentResource() {
        if (resourceBeingEdited != null) {
            final IWorkbench workbench = PlatformUI.getWorkbench();

            workbench.getDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    IWorkbenchWindow window =
                            workbench.getActiveWorkbenchWindow();
                    // Close all open editors
                    if (window != null) {
                        window.getActivePage().closeAllEditors(false);
                    }
                }
            });

            resourceBeingEdited.dispose();
            resourceBeingEdited = null;
            updateWindowTitle(null);
        }
    }

    /**
     * Open the overview editor for the given project resource.
     * 
     * @param resource
     */
    private static void openOverview(final IRCPContainer resource) {
        new UIJob(Messages.Application_openingOverview_job_label) {

            @Override
            public IStatus runInUIThread(IProgressMonitor monitor) {
                try {
                    IDE.openEditor(PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getActivePage(),
                            new OverviewEditorInput(resource),
                            OverviewEditor.ID);
                } catch (PartInitException e) {
                    RCPActivator.getDefault().getLogger()
                            .error(e, "Failed to open the Overview Editor"); //$NON-NLS-1$
                }
                return Status.OK_STATUS;
            }
        }.schedule();
    }

    /**
     * Update all open application listeners.
     */
    private static void updateListeners() {
        synchronized (listeners) {
            XpdResourcesPlugin.getStandardDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    for (IOpenResourceListener listener : listeners) {
                        listener.opened(resourceBeingEdited);
                    }
                }
            });
        }
    }

    /**
     * Add application open listener.
     * 
     * @param listener
     */
    public static void addOpenListener(IOpenResourceListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Remove project open listener.
     * 
     * @param listener
     */
    public static void removeOpenListener(IOpenResourceListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

}

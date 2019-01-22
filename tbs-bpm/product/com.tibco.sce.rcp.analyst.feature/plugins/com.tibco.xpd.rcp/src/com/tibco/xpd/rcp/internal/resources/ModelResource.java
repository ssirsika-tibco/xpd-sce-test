/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.RCPResourceFactory.ModelType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard.CreateXpdProjectOperation;

/**
 * This handles the opening of a model file (ie XPDL, BOM or OM).
 * 
 * @author njpatel
 */
public class ModelResource extends AbstractRCPContainer {

    private ProjectResource projectResource;

    private final File modelFile;

    private WorkingCopy wc;

    private final ModelType type;

    private final ProjectResourceListener projectListener;

    /**
     * 
     */
    public ModelResource(File modelFile, ModelType type) {
        this.modelFile = modelFile;
        this.type = type;
        this.projectListener = new ProjectResourceListener();
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPContainer#getProjectResources()
     * 
     * @return
     */
    @Override
    public Collection<ProjectResource> getProjectResources() {
        return projectResource != null ? Collections.singleton(projectResource)
                : new ArrayList<ProjectResource>(0);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPContainer#deleteProjectResource(com.tibco.xpd.rcp.internal.resources.ProjectResource)
     * 
     * @param projectResource
     * @throws CoreException
     */
    @Override
    public void deleteProjectResource(ProjectResource projectResource)
            throws CoreException {
        if (this.projectResource.equals(projectResource)) {
            projectResource.removeChangeListener(projectListener);
            projectResource.delete();
            projectResource.dispose();
            this.projectResource = null;
        } else {
            throw new IllegalArgumentException(
                    String.format("ModelResource expecting '%1$s' but was passed '%2$s' for deletion.", //$NON-NLS-1$
                            this.projectResource.getName(),
                            projectResource.getName()));
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPContainer#doCreateProjectResource(java.lang.String)
     * 
     * @param name
     * @return
     */
    @Override
    protected ProjectResource doCreateProjectResource(String name) {
        /*
         * Cannot create project resource when editing this kind of resource.
         */
        throw new UnsupportedOperationException(
                "ModelResource cannot create project resource"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPContainer#introspect()
     * 
     * @return
     * @throws CoreException
     */
    @Override
    public List<String> introspect(IProgressMonitor monitor)
            throws CoreException {
        return new ArrayList<String>(0);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return modelFile != null ? modelFile.getName() : null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#getPath()
     * 
     * @return
     */
    @Override
    public IPath getPath() {
        return modelFile != null ? new Path(modelFile.getAbsolutePath()) : null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#doOpen(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @throws CoreException
     */
    @Override
    public boolean doOpen(IProgressMonitor monitor) throws CoreException {
        monitor.beginTask(String
                .format(Messages.ModelResource_opening_monitor_shortdesc,
                        getName()), 5);
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(getProjectName());

        /*
         * Create the dummy XPD project to contain the model file being opened.
         */
        IProjectDescription description =
                ResourcesPlugin.getWorkspace()
                        .newProjectDescription(project.getName());
        try {
            new CreateXpdProjectOperation(project, description, null).run(null);

            projectResource =
                    RCPResourceFactory.createProjectResource(project.getName());
            projectResource.open(new SubProgressMonitorEx(monitor, 1));
            projectResource.addChangeListener(projectListener);

            if (project.isAccessible()) {
                IFolder folder = project.getFolder(type.getFolderName());
                folder.create(true, true, null);
                monitor.worked(1);

                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);
                if (config != null) {
                    config.getSpecialFolders().addFolder(folder,
                            type.getSpecialFolderKind());
                }
                monitor.worked(1);

                IPath filePath = new Path(this.modelFile.getAbsolutePath());
                IFile file = folder.getFile(this.modelFile.getName());
                file.createLink(filePath, IResource.NONE, null);

                wc = WorkingCopyUtil.getWorkingCopy(file);
                if (wc != null) {
                    /*
                     * Check if this working copy is compatible with this
                     * version
                     */
                    if (needsMigrating(wc)) {
                        final Boolean[] result = new Boolean[] { false };
                        final Display display =
                                XpdResourcesPlugin.getStandardDisplay();
                        display.syncExec(new Runnable() {

                            @Override
                            public void run() {
                                result[0] =
                                        MessageDialog.openQuestion(display
                                                .getActiveShell(),
                                                Messages.ModelResource_incompatibleFile_messageDlg_title,
                                                String.format(Messages.ModelResource_migrateToLatestVersion_messageDlg_longdesc,
                                                        getName()));
                            }
                        });

                        if (result[0]) {
                            // Migrate the file
                            migrate(wc);
                        } else {
                            // Cannot proceed as user does not want to migrate.
                            return false;
                        }
                    }

                } else {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    RCPActivator.PLUGIN_ID,
                                    String.format(Messages.ModelResource_failedToGetWorkingCopy_error_message,
                                            file.getName())));
                }
                monitor.worked(1);

                // Open the file for editing
                IDE.openEditor(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage(), file);

            }

        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof CoreException) {
                throw (CoreException) e.getTargetException();
            } else {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                RCPActivator.PLUGIN_ID,
                                String.format(Messages.ModelResource_problemOpeningFile_error_message,
                                        getName()), e));
            }
        } catch (InterruptedException e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            RCPActivator.PLUGIN_ID,
                            String.format(Messages.ModelResource_problemOpeningFile_error_message,
                                    getName()), e));
        }

        monitor.done();

        return true;
    }

    /**
     * Check if this working copy needs migrating.
     * 
     * @param wc
     * @return
     */
    private boolean needsMigrating(WorkingCopy wc) {
        if (wc instanceof AbstractWorkingCopy) {
            EObject root = wc.getRootElement();
            return (root == null && ((AbstractWorkingCopy) wc)
                    .isInvalidVersion());
        }
        return false;
    }

    /**
     * Migrate the given working copy to the latest version.
     * 
     * @param wc
     * @throws CoreException
     */
    private void migrate(WorkingCopy wc) throws CoreException {
        if (wc instanceof AbstractWorkingCopy) {
            ((AbstractWorkingCopy) wc).migrate();
        }
    }

    /**
     * Get a unique project name.
     * 
     * @return
     */
    private String getProjectName() {
        String projectName = null;
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        boolean exists = true;
        int idx = 1;
        while (exists) {
            projectName = String.format("project%d", idx++); //$NON-NLS-1$
            IProject project = root.getProject(projectName);
            exists = project.exists();
        }

        return projectName;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPResource#doSave(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    protected boolean doSave(IProgressMonitor monitor) throws CoreException {
        if (projectResource != null) {
            projectResource.save(monitor);
        }
        return true;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPContainer#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (projectResource != null) {
            projectResource.removeChangeListener(projectListener);
        }
        super.dispose();
    }

    /**
     * Project listener that will delegate change events to listeners of this
     * resource.
     * 
     */
    private class ProjectResourceListener implements RCPResourceChangeListener {

        /**
         * @see com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener#resourceChanged(com.tibco.xpd.rcp.internal.resources.IRCPResource,
         *      com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.ResourceChangeEvent)
         * 
         * @param resource
         * @param event
         */
        @Override
        public void resourceChanged(IRCPResource resource,
                RCPResourceChangeEvent event) {
            notifyResourceChange(event.eventType, event.eventObj, event.source);
        }

    }
}

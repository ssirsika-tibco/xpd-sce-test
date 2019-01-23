/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RCPConsts;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager.ProjectCompatibilityWithCode;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * This manages the opening of a MAA file.
 * 
 * @author njpatel
 */
public class MAAResource extends AbstractRCPContainer {

    private static final String FILE_PATH = "newProjectPath"; //$NON-NLS-1$

    private File maaFile;

    private MaaArchiveHandler archiveHandler;

    private boolean isDirty;

    public MAAResource() {
        this(null);
    }

    /**
     * 
     */
    public MAAResource(File maaFile) {
        this.maaFile = maaFile;
        if (maaFile != null) {
            archiveHandler = new MaaArchiveHandler(maaFile);
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return maaFile != null ? maaFile.getName() : null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#getPath()
     * 
     * @return
     */
    @Override
    public IPath getPath() {
        return maaFile != null ? new Path(maaFile.getAbsolutePath()) : null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPContainer#isDirty()
     * 
     * @return
     */
    @Override
    public boolean isDirty() {
        if (isDirty) {
            return isDirty;
        }
        // Check the projects
        return super.isDirty();
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPContainer#doCreateProjectResource(java.lang.String)
     * 
     * @param name
     * @return
     */
    @Override
    protected ProjectResource doCreateProjectResource(String name) {
        if (name != null) {
            return RCPResourceFactory.createProjectResource(name);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPContainer#removeProjectResource(com.tibco.xpd.rcp.internal.resources.ProjectResource,
     *      boolean)
     * 
     * @param projectResource
     * @param notify
     * @return
     */
    @Override
    protected boolean removeProjectResource(ProjectResource projectResource,
            boolean notify) {
        boolean ret = super.removeProjectResource(projectResource, notify);

        // If the project resource is removed then mark this resource as dirty
        if (ret) {
            isDirty = true;
            notifyDirtyChange(isDirty);
        }

        return ret;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPContainer#introspect()
     * 
     * @return
     */
    @Override
    public List<String> introspect(IProgressMonitor monitor)
            throws CoreException {
        if (archiveHandler != null) {
            try {
                return archiveHandler.getProjectNames();
            } catch (Exception e) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                RCPActivator.PLUGIN_ID,
                                String.format(Messages.MAAResource_problemIntrospectingFile_error_shortdesc,
                                        maaFile.getName()), e));
            }
        }
        return new ArrayList<String>(0);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#doOpen()
     * 
     * @throws CoreException
     */
    @Override
    public boolean doOpen(IProgressMonitor monitor) throws CoreException {

        if (archiveHandler == null) {
            // Nothing to open
            return false;
        }

        monitor.beginTask(String
                .format(Messages.MAAResource_opening_monitor_shortdesc,
                        getName()), 20);
        try {
            Collection<ProjectResource> projectResources =
                    archiveHandler.open(new SubProgressMonitorEx(monitor, 10));

            if (projectResources != null && !projectResources.isEmpty()) {
                List<IProject> projects = new ArrayList<IProject>();
                for (ProjectResource res : projectResources) {
                    projects.add(res.getProject());
                    addProjectResource(res);
                }

                /*
                 * Make sure these projects are not from a later version of
                 * Studio. If any are then throw exception
                 */
                for (IProject project : projects) {
                    ProjectCompatibilityWithCode code =
                            ProjectAssetMigrationManager.getInstance()
                                    .getProjectCompatibilityWithCode(project);

                    if (code == ProjectCompatibilityWithCode.NEWER) {
                        throw new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        RCPActivator.PLUGIN_ID,
                                        String.format(Messages.MAAResource_incompatibleVersion_error_longdesc,
                                                getName())));
                    }
                }

                runPostImportTasks(projects, new SubProgressMonitorEx(monitor,
                        10));
            }
        } finally {
            monitor.done();
        }

        return true;
    }

    /**
     * Run the post-import tasks on the given projects. This will fix typical
     * issues after import (e.g. missing special folders, builder/nature issues)
     * and also migrate the project if required.
     * 
     * @param projects
     *            projects being opened (imported)
     * @param monitor
     * @return
     */
    private IStatus runPostImportTasks(List<IProject> projects,
            IProgressMonitor monitor) {
        try {
            return PostImportUtil.getInstance()
                    .performPostImportTasks(projects, monitor);
        } finally {
            monitor.done();
        }
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

        boolean isNewArchive = false;

        if (archiveHandler == null) {
            File maaFile = getArchivePathFromUser();

            if (maaFile != null) {
                this.maaFile = maaFile;
                archiveHandler = new MaaArchiveHandler(maaFile);
                isNewArchive = true;
            } else {
                return false;
            }
        }

        monitor.beginTask(String
                .format(Messages.MAAResource_saving_monitor_shortdesc,
                        maaFile.getName()), getProjectResources().size() + 10);

        if (maaFile != null) {
            boolean projectSaved = false;
            for (ProjectResource project : getProjectResources()) {
                projectSaved |=
                        project.save(new SubProgressMonitorEx(monitor, 1));
            }

            if (!projectSaved && !isDirty) {
                // No projects have been saved as none was dirty
                return false;
            }

            /*
             * Re-archive the MAA
             */
            try {
                archiveHandler.archive(getProjectResources(),
                        new SubProgressMonitorEx(monitor, 10));
                isDirty = false;

                if (isNewArchive) {
                    // Notify that a new archive was created
                    notifyResourceChange(RCPResourceEventType.ARCHIVE_CREATED,
                            null,
                            this);
                }
            } catch (CoreException e) {
                // Archive failed so set dirty flag
                isDirty = true;
                throw e;
            }
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    private File getArchivePathFromUser() {
        final String maaPath[] = new String[] { null };
        final IDialogSettings settings =
                RCPActivator.getDefault().getDialogSettings();

        final Display display = PlatformUI.getWorkbench().getDisplay();
        display.syncExec(new Runnable() {
            @Override
            public void run() {
                FileDialog dlg =
                        new FileDialog(display.getActiveShell(), SWT.SAVE);
                dlg.setText(Messages.MAAResource_saveProject_fileSelectionDialog_title);
                String path = settings.get(FILE_PATH);
                if (path != null) {
                    dlg.setFilterPath(path);
                }
                dlg.setFilterExtensions(new String[] { "*." //$NON-NLS-1$
                        + RCPConsts.FILE_EXT });
                String value = dlg.open();
                if (value != null) {
                    /*
                     * Kapil : XPD-4493:Check if the Save-As Dialog returns the
                     * filename.EXTENSION(.maa) in the value , If not then
                     * simply add the extension to the value.(Supports Linux
                     * machines)
                     */
                    IPath filePath = new Path(value);

                    if (!RCPConsts.FILE_EXT.equals(filePath.getFileExtension())) {
                        filePath =
                                filePath.removeFileExtension()
                                        .addFileExtension(RCPConsts.FILE_EXT);
                        value = filePath.toOSString();
                    }

                    /*
                     * If a file already exists then ask user whether they want
                     * to replace it
                     */
                    File file = new File(value);
                    if (!file.exists()
                            || (file.exists() && MessageDialog.openQuestion(display
                                    .getActiveShell(),
                                    Messages.MAAResource_saveProject_messageDlg_title,
                                    String.format(Messages.MAAResource_saveProject_messageDlg_message,
                                            file.getName())))) {

                        maaPath[0] = value;
                        settings.put(FILE_PATH, value);
                    }
                }
            }
        });

        return maaPath[0] != null ? new File(maaPath[0]) : null;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return maaFile.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof MAAResource && maaFile != null) {
            return maaFile.equals(((MAAResource) obj).maaFile);
        }
        return super.equals(obj);
    }
}

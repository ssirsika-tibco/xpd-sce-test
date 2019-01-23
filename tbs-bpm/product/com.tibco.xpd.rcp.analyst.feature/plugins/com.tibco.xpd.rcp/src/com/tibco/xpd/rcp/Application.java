/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.Bundle;

import com.tibco.rcp.internal.svn.SVNUtils;
import com.tibco.xpd.rcp.internal.Messages;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

    private static final String LOCK_FILE = ".lock"; //$NON-NLS-1$

    private static final String VERSION_FILE = ".version"; //$NON-NLS-1$

    private static final String TITLE = Messages.Application_title;

    private File workspaceLockFile;

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
     * IApplicationContext)
     */
    @Override
    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();

        initializeWorkspace();
        SVNUtils.forceSVNPreferenceStoreSettings();
        try {
            int returnCode = PlatformUI.createAndRunWorkbench(display,
                    new ApplicationWorkbenchAdvisor());
            if (returnCode == PlatformUI.RETURN_RESTART)
                return IApplication.EXIT_RESTART;
            else
                return IApplication.EXIT_OK;
        } finally {
            display.dispose();

            /*
             * If a workspace was created for this instance (and lock file
             * created) then delete the lock file.
             */
            if (workspaceLockFile != null && workspaceLockFile.exists()) {
                workspaceLockFile.delete();
            }
        }
    }

    /**
     * If the '-data @noDefault' option has been set then use a different
     * workspace for each instance of this RCP application.
     * 
     * @throws IOException
     * @throws MalformedURLException
     * @throws IllegalStateException
     */
    private void initializeWorkspace()
            throws IllegalStateException, MalformedURLException, IOException {
        Location location = Platform.getInstanceLocation();

        if (location != null && !location.isSet()) {

            String user_home = System.getProperty("user.home"); //$NON-NLS-1$

            if (user_home != null) {
                File userHomeFile = new File(user_home);

                if (userHomeFile.exists()) {
                    File workspaces = new File(userHomeFile.getAbsolutePath()
                            + File.separator + "rcp-workspaces"); //$NON-NLS-1$
                    workspaces.mkdir();

                    File workspace = getWorkspaceToUse(workspaces);

                    if (workspace != null) {
                        if (location != null) {
                            location.set(new URL("file", null, workspace //$NON-NLS-1$
                                    .getAbsolutePath()), false);

                            workspaceLockFile = addLockFile(workspace);
                        }
                    }
                }
            } else {
                throw new NullPointerException("User home setting is null."); //$NON-NLS-1$
            }
        }
    }

    /**
     * Get the next available workspace to use. If a previously created
     * workspace is found without the lock file then it will be returned,
     * otherwise a new workspace will be returned (folder already created).
     * <p>
     * If this workspace was created by a previous version of the RCP
     * application then the workspace will be deleted and re-created.
     * </p>
     * 
     * @param workspaces
     * @return
     * @throws IOException
     */
    private File getWorkspaceToUse(File workspaces) throws IOException {
        File workspace = null;

        int idx = 1;
        int workspaceCreationAttempt = 0;
        boolean isNewWorkspace = false;

        while (workspace == null) {
            String workspaceName = String.format("workspace%d", idx++); //$NON-NLS-1$
            IPath path = new Path(workspaces.getAbsolutePath())
                    .append(workspaceName);

            File workspaceFolder = path.toFile();

            if (workspaceFolder.exists()) {
                /*
                 * If this does not have a lock file then re-user this workspace
                 */
                File lockFile = path.append(LOCK_FILE).toFile();

                if (!lockFile.exists()) {

                    /*
                     * Check if this workspace was created by the same version
                     * RCP - if not then delete the workspace and re-create it.
                     */
                    File versionFile = path.append(VERSION_FILE).toFile();
                    if (!versionFile.exists()
                            || !isVersionCurrent(versionFile)) {
                        // Delete contents of the workspace to re-create it
                        if (deleteContent(workspaceFolder)) {
                            isNewWorkspace = true;
                            workspace = workspaceFolder;
                        }
                    } else {
                        workspace = workspaceFolder;
                    }
                }

            } else {
                /*
                 * Workspace does not exist so use this TODO: WHAT ABOUT
                 * PREFERENCES? DO WE NEED TO COPY PREFERENCES TO OTHER
                 * INSTANCES?
                 */
                workspace = workspaceFolder;
            }

            if (workspace != null) {
                if (!workspace.exists()) {
                    /*
                     * Try to create the workspace. If this fails then try
                     * another workspace location
                     */
                    isNewWorkspace = workspace.mkdir();
                    if (!isNewWorkspace) {
                        // Keep searching for free workspace
                        workspace = null;
                    }

                    if (++workspaceCreationAttempt > 10) {
                        /*
                         * Too many tries in creating workspace so throw
                         * exception otherwise will get into infinite loop
                         */
                        throw new IllegalArgumentException(String.format(
                                Messages.Application_unableToCreateWorkspace_error_longdesc,
                                workspaces.getAbsolutePath()));
                    }
                }
            }

        }

        if (isNewWorkspace) {
            /*
             * Add version file
             */
            File versionFile = new Path(workspace.getAbsolutePath())
                    .append(VERSION_FILE).toFile();
            versionFile.createNewFile();

            BufferedOutputStream os =
                    new BufferedOutputStream(new FileOutputStream(versionFile));
            os.write(getBundleVersion().getBytes());
            os.close();

        }
        return workspace;
    }

    /**
     * Delete the contents of the given folder.
     * 
     * @param folder
     * @return
     */
    private boolean deleteContent(File folder) {
        if (folder.isDirectory()) {
            for (File child : folder.listFiles()) {
                if (!delete(child)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Delete the given file/folder. (This method is called recursively to
     * delete all contents under the given folder.)
     * 
     * @param file
     * @return
     */
    private boolean delete(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!delete(child)) {
                    return false;
                }
            }
        }

        return file.delete();
    }

    /**
     * Get the bundle version of this plug-in.
     * 
     * @return
     */
    private String getBundleVersion() {
        Bundle bundle = RCPActivator.getDefault().getBundle();
        if (bundle != null) {
            Dictionary<?, ?> dict = bundle.getHeaders();
            if (dict != null) {
                Object bundleVersion = dict.get("Bundle-version"); //$NON-NLS-1$
                if (bundleVersion instanceof String) {
                    return (String) bundleVersion;
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Check if the version number in the version file matches the bundle
     * version.
     * 
     * @param versionFile
     * @return <code>true</code> if versions match.
     * @throws IOException
     */
    private boolean isVersionCurrent(File versionFile) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(versionFile)));

        try {
            String version = reader.readLine();
            String currentVersion = getBundleVersion();

            return currentVersion != null && currentVersion.equals(version);
        } finally {
            reader.close();
        }
    }

    /**
     * Add a lock file to the workspace - to indicate that the workspace is in
     * use.
     * 
     * @param workspace
     * @return the lock file created.
     * @throws IOException
     */
    private File addLockFile(File workspace) throws IOException {
        File lockFile = new Path(workspace.getAbsolutePath()).append(LOCK_FILE)
                .toFile();
        lockFile.createNewFile();

        return lockFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    @Override
    public void stop() {

        final IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench == null)
            return;
        final Display display = workbench.getDisplay();
        display.syncExec(new Runnable() {
            @Override
            public void run() {
                if (!display.isDisposed())
                    workbench.close();
            }
        });

    }

    /**
     * Add the path of the file being edited to the window title.
     * 
     * @param zipFile
     */
    public static void updateWindowTitle(final String title) {
        new UIJob(Messages.Application_updateTitle_job_label) {

            @Override
            public IStatus runInUIThread(IProgressMonitor monitor) {
                /*
                 * SID XPD-8302 - we're getting called after workbench disposed,
                 * so make a quick check whether it's there or not
                 */
                if (PlatformUI.getWorkbench() != null
                        && PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow() != null
                        && PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell() != null) {
                    Shell shell = PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getShell();
                    if (title != null) {
                        shell.setText(String.format("%s - %s", TITLE, title)); //$NON-NLS-1$
                    } else {
                        shell.setText(TITLE);
                    }
                }
                return Status.OK_STATUS;
            }
        }.schedule();

    }

}

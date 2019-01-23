/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.tutorials;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.osgi.framework.Bundle;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.internal.Messages;

/**
 * 
 * 
 * @author rgreen
 * 
 */
public class TutorialPackageInstallOperation implements IRunnableWithProgress {

    private final IOverwriteQuery query;
    private final String target;
    private final TutorialPackage tutorialPackage;

    public TutorialPackageInstallOperation(TutorialPackage tutorialPackage,
            String target) {
        this.tutorialPackage = tutorialPackage;
        this.target = target;
        this.query = new ImportOverwriteQuery();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core
     * .runtime.IProgressMonitor)
     */
    public void run(IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException {
        
        IWorkspaceRunnable op = new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                try {
                    monitor
                            .beginTask(
                                    Messages.TutorialPackageInstallOperation_CreatingProjects,
                                    4);
                    importPackage(tutorialPackage, target, monitor);
                } catch (InterruptedException e) {
                    throw new OperationCanceledException();
                } catch (InvocationTargetException e) {
                    throw getCoreException(e.getCause());
                } catch (Throwable e) {
                    throw getCoreException(e);
                } finally {
                    monitor.done();
                }
            }
        };

        try {
            ResourcesPlugin.getWorkspace().run(op, monitor);
        } catch (CoreException e) {
            throw new InvocationTargetException(e);
        } catch (OperationCanceledException e) {
            throw e;
        } finally {
            monitor.done();
        }

    }

    /**
     * 
     * Perform the import to the workspace of the TutorialPackages resources.
     * The target specifies whether the resources are the basic set to initiate
     * a tutorial, or the full solution's resources.
     * 
     * @param TutorialPackage
     *            tutorialPackage
     * @param String
     *            target
     * @param IProgressMonitor
     *            monitor
     * @throws CoreException
     * @throws InvocationTargetException
     * @throws InterruptedException
     * @throws IOException
     */
    private void importPackage(TutorialPackage tutorialPackage, String target,
            IProgressMonitor monitor) throws CoreException,
            InvocationTargetException, InterruptedException, IOException {

        // Get the path we are copying the resources from (either
        // basic resources or the complete solution
        List<String> sourcePathList = null;
        if (ITutorialConstants.InstallTarget.RESOURCES.getValue()
                .equals(target)) {
            sourcePathList = tutorialPackage.getResourcesFolders();
        } else {
            sourcePathList = tutorialPackage.getSolutionFolders();
        }

        String importStatus = null;

        for (String sourcePath : sourcePathList) {
            if (sourcePath != null) {
                if (IOverwriteQuery.ALL.equals(importStatus)) {
                    importStatus = importTutorialResources(sourcePath, monitor,
                            true);
                } else {
                    importStatus = importTutorialResources(sourcePath, monitor,
                            false);
                }

                if (IOverwriteQuery.CANCEL.equals(importStatus)) {
                    break;
                }
            }
        }

    }

    /**
     * 
     * Imports all the resources inside the folder at the location represented
     * by sourcePath. For example, this could be a number of Business Studio
     * projects.
     * 
     * A silent import can be specified by setting the boolean value
     * silentImport to true. In this case, an overwrite dialog will not display
     * and any existing resources will be deleted and replaced.
     * 
     * Returns a string (IOverWriteQuery) representing the choice selected from
     * the Overwrite dialog. If the dialog has not been called then this string
     * will be null;
     * 
     * 
     * @param String
     *            sourcePath
     * @param IProgressMonitor
     *            monitor
     * @param boolean silentImport
     * @return String overWriteStatus
     * @throws CoreException
     * @throws InvocationTargetException
     * @throws InterruptedException
     * @throws IOException
     */
    private String importTutorialResources(String sourcePath,
            IProgressMonitor monitor, boolean silentImport)
            throws CoreException, InvocationTargetException,
            InterruptedException, IOException {

        String overWriteQueryStatus = null;

        // Try and resolve the plugin ID
        
        // Strip off any leading '/'
        if (sourcePath.charAt(0) == '/') {
            sourcePath = sourcePath.substring(1);
        }

        int indexOf = sourcePath.indexOf('/');
        Bundle bundle = null;
        String pluginID = null;
        
        String path=null;

        if (indexOf >= 0) {
            // derive the the pluginID
            pluginID = sourcePath.substring(0, indexOf);
            path = sourcePath.substring(indexOf + 1);
            bundle = Platform.getBundle(pluginID);
        }

        if (bundle == null) {
            path = sourcePath;
            // Assume that this is a relative path to the
            // contributing plugin
            String contributor = tutorialPackage.getContributor();

            if (contributor != null) {
                bundle = Platform.getBundle(contributor);
            }
        }

        // Resolve the path to the source resources
        URL url = FileLocator.find(bundle, new Path(path), null);

        if (url != null) {
            url = FileLocator.toFileURL(url);
            String sourceFolder = url.getPath();

            // Get the contents of the source folder
            // For now we assume that we are only copying
            // Studio project folders
            File folder = new File(sourceFolder);
            File[] listFiles = folder.listFiles();

            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IWorkspaceRoot root = workspace.getRoot();
            boolean overWriteAll = false;

            for (File resource : listFiles) {
                if (resource.isDirectory()) {
                    // Check that this folder is an Eclipse
                    // project file by looking for a .project file.
                    // If it is then we will:
                    // 1. Create a new project in the workspace with
                    // the same name as source project
                    // 2. Copy the contents of the source project
                    // into the newly created project.
                    if (isProject(resource)) {

                        IProject workspaceProject = root.getProject(resource
                                .getName());

                        boolean skip = false;

                        if (!silentImport && workspaceProject.exists()
                                && overWriteAll == false) {
                            overWriteQueryStatus = query
                                    .queryOverwrite(workspaceProject
                                            .getFullPath().toString());

                            if (overWriteQueryStatus
                                    .equals(IOverwriteQuery.YES)) {
                                skip = false;
                            } else if (overWriteQueryStatus
                                    .equals(IOverwriteQuery.NO)) {
                                skip = true;
                            } else if (overWriteQueryStatus
                                    .equals(IOverwriteQuery.CANCEL)) {
                                skip = true;
                                return IOverwriteQuery.CANCEL;
                            } else if (overWriteQueryStatus
                                    .equals(IOverwriteQuery.ALL)) {
                                skip = false;
                                overWriteAll = true;
                            }

                        }

                        if (skip == false) {
                            if (workspaceProject.exists()) {
                                try {
                                    workspaceProject.delete(true, true,
                                            new SubProgressMonitor(monitor, 1));
                                } catch (CoreException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                workspaceProject = root.getProject(resource
                                        .getName());

                            }
                            importProject(resource, workspaceProject, monitor);
                        } else {
                            monitor.worked(1);
                        }

                    }

                }
            }
        } else {
            throw new IllegalArgumentException(String.format(
                    "Location '%s' cannt be resolved", sourcePath));
        }

        if (silentImport == true) {
            overWriteQueryStatus = IOverwriteQuery.ALL;
        }
        return overWriteQueryStatus;
    }

    /**
     * 
     * Checks whether the File is an Eclipse project i.e. folder contains a
     * .project file.
     * 
     * @param File
     *            file
     * @return boolean
     */
    private boolean isProject(File file) {
        File[] files = file.listFiles(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                // TODO Auto-generated method stub
                if (".project".equals(name)) { //$NON-NLS-1$
                    return true;
                } else {
                    return false;
                }
            }

        });
        return files != null && files.length > 0;
    }

    /**
     * @param InvocationTargetException
     *            e
     * @throws CoreException
     */
    private CoreException getCoreException(Throwable e) {
        IStatus status = new Status(IStatus.ERROR, BrandingPlugin.PLUGIN_ID, e
                .getLocalizedMessage(), e);
        return new CoreException(status);
    }

    /**
     * 
     * Performs the actual file copying.
     * 
     * @param File
     *            srcProject
     * @param IProject
     *            destination
     * @param IProgressMonitor
     *            monitor
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    private void importProject(File srcProject, IProject destination,
            IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException {
        FileSystemStructureProvider provider = FileSystemStructureProvider.INSTANCE;

        ImportOperation op = new ImportOperation(destination.getFullPath(),
                srcProject, provider, query);
        op.setCreateContainerStructure(false);

        op.run(monitor);
    }

    /**
     * @author rgreen
     * 
     */
    private class ImportOverwriteQuery implements IOverwriteQuery {

        public String queryOverwrite(String file) {
            String[] returnCodes = { YES, NO, ALL, CANCEL };
            int returnVal = openDialog(file);
            return returnVal < 0 ? CANCEL : returnCodes[returnVal];
        }

        private int openDialog(final String file) {
            final int[] result = { IDialogConstants.CANCEL_ID };

            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    String title = Messages.TutorialPackageInstallOperation_SampleWizard;
                    String msg = String
                            .format(
                                    Messages.TutorialPackageInstallOperation_ProjectAlreadyExists,
                                    file);

                    String[] options = { IDialogConstants.YES_LABEL,
                            IDialogConstants.NO_LABEL,
                            IDialogConstants.YES_TO_ALL_LABEL,
                            IDialogConstants.CANCEL_LABEL };

                    Display current = Display.getDefault();
                    Shell currentShell = current.getActiveShell();
                    MessageDialog dialog = new MessageDialog(currentShell,
                            title, null, msg, MessageDialog.QUESTION, options,
                            0);
                    result[0] = dialog.open();
                }
            });
            return result[0];
        }
    }

}

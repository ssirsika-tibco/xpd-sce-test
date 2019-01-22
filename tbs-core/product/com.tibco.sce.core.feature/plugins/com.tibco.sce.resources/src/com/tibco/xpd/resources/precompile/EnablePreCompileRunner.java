/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.resources.precompile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Class that gets all the contributions for enable pre-compile extension point
 * and runs the enable pre-compile life cycle methods for the given project and
 * its referenced projects
 * 
 * @author bharge
 * @since 13 Nov 2014
 */
public class EnablePreCompileRunner extends WorkspaceModifyOperation {

    Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    private List<IProject> projectsToEnablePrecompile;

    private IFolder preCompileFolder;

    /**
     * Constructor to create the instance of <code>EnablePreCompileRunner</code>
     * class with the list of projects to be enabled for pre compile
     * 
     * @param sortedProjects
     */
    public EnablePreCompileRunner(List<IProject> sortedProjects) {

        this.projectsToEnablePrecompile = sortedProjects;
    }

    /**
     * @see org.eclipse.ui.actions.WorkspaceModifyOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @throws CoreException
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    @Override
    protected void execute(IProgressMonitor monitor) throws CoreException,
            InvocationTargetException, InterruptedException {

        try {

            monitor.beginTask("", projectsToEnablePrecompile.size()); //$NON-NLS-1$

            for (IProject eachProject : projectsToEnablePrecompile) {

                if (monitor.isCanceled()) {

                    return;
                }
                monitor.setTaskName(String
                        .format(Messages.EnablePreCompileRunner_PrecompilingProject_monitor_message,
                                eachProject.getName()));
                preCompileProject(eachProject, new SubProgressMonitor(monitor,
                        1));
            }

        } finally {

            monitor.done();
        }
    }

    /**
     * @param project
     * @param monitor
     * @throws CoreException
     */
    private void preCompileProject(IProject project, IProgressMonitor monitor)
            throws CoreException {

        try {

            /* Create .precompiled folder */
            preCompileFolder =
                    ProjectUtil
                            .createFolder(project,
                                    PreCompileContributorManager.getInstance().PRECOMPILE_OUTPUTFOLDER_NAME,
                                    null);
            if (null != preCompileFolder) {

                try {

                    /*
                     * Ensure the precompile folder is clean before calling the
                     * contributors (delete the contents under sub folders under
                     * .precompile folder even before invoking any
                     * contributions)
                     */
                    try {

                        PreCompileUtil.deleteFolderMembers(preCompileFolder);
                    } catch (CoreException e) {

                        logger.error(e,
                                Messages.EnablePreCompileRunner_deleting_artifacts_message);
                        throw new CoreException(
                                new Status(
                                        Status.ERROR,
                                        XpdResourcesPlugin.ID_PLUGIN,
                                        Messages.EnablePreCompileRunner_deleting_artifacts_message,
                                        e));
                    }

                    /* Get all the contributions */
                    List<AbstractPreCompileContributor> preCompileContributors =
                            PreCompileContributorManager.getInstance()
                                    .getPreCompileContributors();

                    IStatus postPrecompileStatus = Status.OK_STATUS;

                    monitor.beginTask("", preCompileContributors.size() * 3); //$NON-NLS-1$

                    /*
                     * Call prepare for pre compile - first of the life cycle
                     * methods on all the contributors
                     */
                    for (AbstractPreCompileContributor preCompileContributor : preCompileContributors) {

                        IStatus prepareForPrecompileStatus =
                                preCompileContributor.precompile(project,
                                        preCompileFolder,
                                        new SubProgressMonitor(monitor, 1));

                        if (Status.OK != prepareForPrecompileStatus
                                .getSeverity()) {

                            if (monitor.isCanceled()) {

                                /*
                                 * If the progress monitor is cancelled, handle
                                 * the cancel operation
                                 */
                                logger.info(String
                                        .format(Messages.EnablePreCompileRunner_user_cancelled_info_message,
                                                project.getName()));
                                handleCancelOperation(project);
                                return;
                            }

                            logger.error(prepareForPrecompileStatus
                                    .getMessage());
                            handlePrecompilationFailed(prepareForPrecompileStatus
                                    .getMessage());
                            return;
                        }
                    }
                    /*
                     * Call preservePrecompiledArtifacts on all the contributors
                     */
                    for (AbstractPreCompileContributor preCompileContributor : preCompileContributors) {

                        /* Call pre compile */
                        IStatus precompileStatus =
                                preCompileContributor
                                        .preservePrecompiledArtifacts(project,
                                                preCompileFolder,
                                                new SubProgressMonitor(monitor,
                                                        1));

                        if (Status.OK != precompileStatus.getSeverity()) {

                            if (monitor.isCanceled()) {
                                /*
                                 * If the progress monitor is cancelled, handle
                                 * the cancel operation
                                 */
                                logger.info(String
                                        .format(Messages.EnablePreCompileRunner_user_cancelled_info_message,
                                                project.getName()));
                                handleCancelOperation(project);
                                return;
                            }
                            logger.error(precompileStatus.getMessage());
                            handlePrecompilationFailed(precompileStatus
                                    .getMessage());
                            return;
                        }
                    }
                    /*
                     * Run post pre-compile on all the contributors after
                     * prepareForPrecompile and preservePrecompiledArtifacts.
                     */
                    for (AbstractPreCompileContributor preCompileContributor : preCompileContributors) {

                        /*
                         * Call post pre compile to do any clean up task
                         */
                        postPrecompileStatus =
                                preCompileContributor.postPrecompile(project,
                                        preCompileFolder,
                                        new SubProgressMonitor(monitor, 1));

                        monitor.worked(1);
                        if (Status.OK != postPrecompileStatus.getSeverity()) {

                            if (monitor.isCanceled()) {
                                /*
                                 * If the progress monitor is cancelled, handle
                                 * the cancel operation
                                 */
                                logger.info(String
                                        .format(Messages.EnablePreCompileRunner_user_cancelled_info_message,
                                                project.getName()));
                                handleCancelOperation(project);
                                return;
                            }
                            logger.error(postPrecompileStatus.getMessage());
                            handlePrecompilationFailed(postPrecompileStatus
                                    .getMessage());
                            return;

                        }
                        /*
                         * Disable modifications on the pre-compiled project
                         * 
                         * PLEASE NOTE THAT this will be done in the
                         * AbstractWorkingCopy isReadOnly() method. If Project
                         * is in pre-compilation mode, it checks whether the
                         * file that backs that working copy will be marked as
                         * read only
                         */
                    }
                    monitor.worked(1);
                    if (monitor.isCanceled()) {

                        logger.info(String
                                .format(Messages.EnablePreCompileRunner_user_cancelled_info_message,
                                        project.getName()));
                        handleCancelOperation(project);
                        return;
                    }
                    /*
                     * Save the source resource (contributing to pre-compile)
                     * workspace relative path and its check sum in a properties
                     * file under .precompiled folder
                     */
                    if (Status.OK == postPrecompileStatus.getSeverity()) {

                        saveSourceResChecksumToProperties(project);
                    }
                    project.refreshLocal(IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());

                } catch (CoreException e) {

                    logger.error(e.getMessage());
                    throw new CoreException(new Status(Status.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN, e.getMessage(), e));
                }
                /*
                 * Add pre compile project nature at the end so if the user
                 * cancels or if anything goes wrong, user doesn't have to
                 * disable pre-compile before enabling it again
                 */
                ProjectUtil.addNature(project,
                        ProjectUtil.PRE_COMPILED_PROJECT_NATURE_ID);
            }
        } finally {

            monitor.done();
        }

    }

    /**
     * When progress monitor on the pre-compile wizard is cancelled, handle the
     * cancel operation by informing the user of the consequences/remedial
     * actions
     * 
     * @param project
     */
    private void handleCancelOperation(IProject project) {

        if (preCompileFolder.isAccessible()) {

            if (!XpdResourcesPlugin.isInHeadlessMode()) {

                Display.getDefault().asyncExec(new Runnable() {

                    @Override
                    public void run() {

                        Shell shell =
                                PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow().getShell();
                        String msg =
                                Messages.EnablePreCompileRunner_UserCancelled_warning_message;
                        MessageDialog
                                .openWarning(shell,
                                        Messages.EnablePreCompileRunner_UserCancelled_warning_message_title,
                                        msg);
                    }
                });
            }
        }
    }

    /**
     * If something happens during pre-compilation and the pre-compilation
     * operation fails, then pop-up the error dialog to the user with the error
     * message encountered
     * 
     * @param errMsg
     */
    private void handlePrecompilationFailed(final String errMsg) {

        if (!XpdResourcesPlugin.isInHeadlessMode()) {

            Display.getDefault().asyncExec(new Runnable() {

                @Override
                public void run() {

                    Shell shell =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getShell();
                    MessageDialog.openError(shell,
                            Messages.EnablePreCompileRunner_PreCompilationFailed_error_message_title,
                            errMsg);
                }
            });
        }
    }

    /**
     * Creates the properties file under .precompiled folder to hold source
     * resource workspace relative path and its its check sum
     * 
     * @param eachProject
     * @throws CoreException
     */
    private void saveSourceResChecksumToProperties(IProject eachProject)
            throws CoreException {

        PrecompileResourceVisitor precompileResVisitor =
                new PrecompileResourceVisitor();

        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {

            /*
             * Call visitor on project to build the hash map of source resource
             * path and check sum key value pairs.
             */
            eachProject.accept(precompileResVisitor,
                    IResource.DEPTH_INFINITE,
                    false);
            /*
             * After calling the visitor, get the source resource check sum hash
             * map
             */
            Map<String, String> sourceResChecksumMap =
                    precompileResVisitor.getSourceResChecksumMap();

            /*
             * Iterate thru the hash map and create the properties file with key
             * value pairs from hash map
             */
            String propertiesFileName =
                    PreCompileContributorManager.getInstance().SOURCE_PRECOMPILE_RESOURCES_PROPERTIES;

            outputStream = new ByteArrayOutputStream();
            Properties properties = new Properties();
            Set<String> keySet = sourceResChecksumMap.keySet();

            for (String key : keySet) {

                String value = sourceResChecksumMap.get(key);
                properties.setProperty(key, value);
            }

            /*
             * Put the check sum value calculated on project id, version and
             * destination(s) in the check sum properties file. This will be
             * later used in validation to check if the any of the id, version
             * or destination(s) of the pre-compiled project changes!
             */
            String projectDetailsChecksumValue =
                    PreCompileUtil.getProjectDetailsChecksumValue(eachProject);

            properties
                    .setProperty(PreCompileUtil.PROJECT_DETAILS_CHECK_SUM_KEY,
                            projectDetailsChecksumValue);

            properties.store(outputStream,
                    "Source Files and their check sum values at pre-compile"); //$NON-NLS-1$
            /* Create the properties file under .precompiled folder */
            IFile propertiesFile = preCompileFolder.getFile(propertiesFileName);

            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            propertiesFile.create(inputStream,
                    IResource.FORCE,
                    new NullProgressMonitor());

        } catch (FileNotFoundException e) {

            logger.error(e.getMessage());
        } catch (IOException e) {

            logger.error(e.getMessage());
        } finally {

            try {
                if (null != inputStream) {

                    inputStream.close();
                }
                if (null != outputStream) {

                    outputStream.close();
                }
            } catch (IOException e) {

                logger.error(e);
            }
        }
    }
}

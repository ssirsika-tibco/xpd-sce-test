/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.resources.precompile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
 * Class that gets all the contributions for disable pre-compile extension point
 * and runs the disable pre-compile life cycle methods for the given project and
 * its referencing projects
 * 
 * @author bharge
 * @since 13 Nov 2014
 */
public class DisablePreCompileRunner extends WorkspaceModifyOperation {

    Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    private List<IProject> projectsToDisablePrecompile;

    /**
     * Constructor to create the instance of
     * <code>DisablePreCompileRunner</code> class with the list of projects to
     * be disabled for pre compile
     * 
     * @param projectsToDisablePrecompile
     */
    public DisablePreCompileRunner(List<IProject> projectsToDisablePrecompile) {

        this.projectsToDisablePrecompile = projectsToDisablePrecompile;
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

        /*
         * Go thru all the projects and disable them for pre compile by calling
         * all the disable pre compile contributors on each project
         */
        for (IProject eachProject : projectsToDisablePrecompile) {

            try {

                /* Remove pre compile project nature */
                ProjectUtil.removeNature(eachProject,
                        ProjectUtil.PRE_COMPILED_PROJECT_NATURE_ID);
                /* Get the .precompiled folder */
                IFolder preCompileFolder =
                        eachProject.getFolder(PreCompileContributorManager
                                .getInstance().PRECOMPILE_OUTPUTFOLDER_NAME);
                if (preCompileFolder.isAccessible()) {

                    /* Get all the contributions */
                    List<AbstractPreCompileContributor> contributors =
                            PreCompileContributorManager.getInstance()
                                    .getPreCompileContributors();
                    /*
                     * Call about to disable pre compile - first of the life
                     * cycle methods on all the contributors
                     */
                    monitor.beginTask("", contributors.size() * 2); //$NON-NLS-1$
                    for (AbstractPreCompileContributor preCompileContributor : contributors) {

                        IStatus status =
                                preCompileContributor
                                        .aboutToDisablePrecompile(eachProject,
                                                preCompileFolder,
                                                monitor);
                        monitor.worked(1);
                        if (status.getSeverity() != Status.OK) {

                            if (monitor.isCanceled()) {

                                logger.info(String
                                        .format(Messages.DisablePreCompileRunner_user_cancelled_info_message,
                                                eachProject.getName()));
                                handleCancelOperation(eachProject);
                                return;
                            }
                            logger.error(Messages.DisablePreCompileProjectAction_error_message);
                            logger.log(status);
                        }
                    }

                    /*
                     * Call post precompile disabled on all the contributors
                     */
                    for (AbstractPreCompileContributor preCompileContributor : contributors) {

                        /*
                         * Call post pre compile to do any clean up task
                         */
                        IStatus postPrecompileDisabledStatus =
                                preCompileContributor
                                        .postPrecompileDisabled(eachProject,
                                                preCompileFolder,
                                                monitor);
                        monitor.worked(1);
                        if (postPrecompileDisabledStatus.getSeverity() != Status.OK) {

                            if (monitor.isCanceled()) {

                                logger.info(String
                                        .format(Messages.DisablePreCompileRunner_user_cancelled_info_message,
                                                eachProject.getName()));
                                handleCancelOperation(eachProject);
                                return;
                            }
                            logger.error(Messages.DisablePreCompileProjectAction_error_message);
                            logger.log(postPrecompileDisabledStatus);
                        }
                    }

                    /*
                     * Don't want to delete the .precompile folder or any of its
                     * sub folder to avoid getting svn defer file delete
                     * validation when a folder is deleted and re-added again
                     */
                    // preCompileFolder.delete(false, monitor);
                }

            } catch (CoreException e) {

            }

        } /* for each project */

        /*
         * clean build all the projects after the stuffs from disable pre
         * compile contributors is done
         */
        List<IBuildConfiguration> configArr =
                new ArrayList<IBuildConfiguration>();
        for (IProject eachProject : projectsToDisablePrecompile) {

            if (eachProject.isAccessible()) {

                try {

                    configArr.addAll(Arrays.asList(eachProject
                            .getBuildConfigs()));
                } catch (CoreException e) {

                    logger.error(e,
                            "Error in getting build configs on projects being disabled for pre-compile"); //$NON-NLS-1$
                }
            }
        }
        IBuildConfiguration[] configs =
                configArr.toArray(new IBuildConfiguration[configArr.size()]);
        try {

            ResourcesPlugin.getWorkspace().build(configs,
                    IncrementalProjectBuilder.CLEAN_BUILD,
                    true,
                    monitor);
            ResourcesPlugin.getWorkspace().build(configs,
                    IncrementalProjectBuilder.FULL_BUILD,
                    true,
                    monitor);
        } catch (CoreException e) {

            logger.error(e.getMessage());
        }
    }

    /**
     * @param eachProject
     */
    private void handleCancelOperation(IProject eachProject) {

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

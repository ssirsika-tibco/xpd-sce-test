/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.imports.IPostImportProjectTask;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Utility to perform post import tasks contributed by
 * 'com.tibco.xpd.resources.ui.postImportProjectTask' extensions.
 * 
 * @author jarciuch
 * @since 3.5.3
 */
public class PostImportUtil {

    /**
     * Post-import task extension local id.
     */
    private static final String POST_IMPORT_PROJECT_TASK_EXT_ID =
            "postImportProjectTask"; //$NON-NLS-1$

    private static final PostImportUtil INSTANCE = new PostImportUtil();

    /**
     * Prevents direct instance creation. Please use {@link #getInstance()}
     * instead.
     */
    private PostImportUtil() {
    }

    /**
     * Returns an instance of this utility.
     */
    public static PostImportUtil getInstance() {
        return INSTANCE;
    }

    /**
     * Runs post-import tasks (contributed by
     * 'com.tibco.xpd.resources.ui.postImportProjectTask') on a provided
     * collection of projects.
     * 
     * @param projects
     *            the collection of project to run the post-inport tasks on.
     * @param monitor
     *            the progress monitor.
     * @return IStatus indicating the result of the operation.
     */
    public IStatus performPostImportTasks(Collection<IProject> projects,
            IProgressMonitor monitor) {
        List<PostImportTask> postImportTasks = getPostImportTasks();

        MultiStatus postImportErrorStatus =
                new MultiStatus(XpdResourcesUIActivator.ID, 0,
                        "There was a problem running post-import tasks.", null); //$NON-NLS-1$

        monitor.beginTask(String
                .format(Messages.PostImportUtil_PerformingPostImportTask_message),
                postImportTasks.size());
        try {
            List<IProject> sortedProjects =
                    ProjectUtil.getDependencySortedProjects(projects);
            for (IProject project : sortedProjects) {
                /* If user has cancelled then stop */
                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }

                IStatus status =
                        performPostImportTasks(project,
                                postImportTasks,
                                SubProgressMonitorEx
                                        .createMainProgressMonitor(monitor, 1));
                if (status.getSeverity() > IStatus.WARNING) {
                    postImportErrorStatus.add(status);
                }

            }
            if (postImportErrorStatus.getSeverity() > IStatus.WARNING) {
                return postImportErrorStatus;
            }
            return Status.OK_STATUS;

        } catch (CoreException e) {
            IStatus status = e.getStatus();
            status = (status != null) ? status : postImportErrorStatus;
            XpdResourcesUIActivator.getDefault().getLog().log(status);
            return status;
        } finally {
            monitor.done();
        }
    }

    /**
     * Perform all of the contributed post-import tasks on the given project.
     * 
     * @param project
     * @param postImportTasks
     * @param monitor
     */
    private static IStatus performPostImportTasks(IProject project,
            List<PostImportTask> postImportTasks, IProgressMonitor monitor) {
        try {
            if (project != null) {
                MultiStatus postImportStatus =
                        new MultiStatus(XpdResourcesUIActivator.ID, 0, "", null); //$NON-NLS-1$
                monitor.beginTask(String
                        .format(Messages.ProjectsImportPage_PerformingPostImportTasks_message,
                                project.getName()),
                        postImportTasks.size());

                /*
                 * Sid ACE-4365 Refresh project completely before performing post import tasks to make sure that when we
                 * do import from folder 'DON'T copy into workspace', then the workspace project is properly synched
                 * with the file-system
                 */
                try {
                    project.refreshLocal(IResource.DEPTH_INFINITE, monitor);

                } catch (CoreException e2) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e2,
                            String.format("There is a problem refreshing project '%s' prior to post-import tasks.", //$NON-NLS-1$
                                    project.getName()));
                }

                for (PostImportTask task : postImportTasks) {

                    /*
                     * Check if this task can process the project type
                     */
                    try {
                        if (!task.appliesTo(project)) {
                            monitor.worked(1);
                            continue;
                        }
                    } catch (CoreException e1) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e1,
                                        String.format(
                                                "There is a problem checking the 'appliesTo' on post-import task '%s'.", //$NON-NLS-1$
                                                task.getId()));
                    }

                    /* If user has cancelled then stop */
                    if (monitor.isCanceled()) {
                        throw new OperationCanceledException();
                    }

                    try {
                        task.getTask()
                                .runPostImportTask(project,
                                        SubProgressMonitorEx
                                                .createNestedSubProgressMonitor(monitor,
                                                        1));

                    } catch (CoreException e) {
                        // Log and add to multi-status and continue with next
                        // task
                        String message =
                                String.format("There was a problem running the post-import task '%1$s' on project '%2$s'.", //$NON-NLS-1$
                                        task.getId(),
                                        project.getName());
                        IStatus status = e.getStatus();
                        if (status != null
                                && status.getSeverity() > IStatus.WARNING) {
                            postImportStatus.add(status);
                        } else {
                            postImportStatus.add(new Status(IStatus.ERROR,
                                    XpdResourcesUIActivator.ID, message, e));
                        }
                        XpdResourcesUIActivator.getDefault().getLogger()
                                .error(e, message);
                    }
                }
                if (postImportStatus.getSeverity() > IStatus.WARNING) {
                    if (postImportStatus.getChildren().length == 1) {
                        return postImportStatus.getChildren()[0];
                    } else {
                        String message =
                                String.format("There was a problem running post-import tasks on project '%1$s'.", //$NON-NLS-1$
                                        project.getName());
                        return new MultiStatus(XpdResourcesUIActivator.ID, 0,
                                postImportStatus.getChildren(), message, null);
                    }
                }
            }
            return Status.OK_STATUS;
        } finally {
            monitor.done();
        }
    }

    /**
     * Get the registered post-import tasks.
     * 
     * @return
     */
    private static List<PostImportTask> getPostImportTasks() {
        List<PostImportTask> tasks = new ArrayList<PostImportTask>();

        IConfigurationElement[] elements =
                Platform.getExtensionRegistry()
                        .getConfigurationElementsFor(XpdResourcesUIActivator.ID,
                                POST_IMPORT_PROJECT_TASK_EXT_ID);

        for (IConfigurationElement element : elements) {
            try {
                tasks.add(new PostImportTask(element));

            } catch (CoreException e) {
                // Log and continue
                XpdResourcesUIActivator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("There is a problem loading post-import project task: %s", //$NON-NLS-1$
                                        element.getAttribute("id"))); //$NON-NLS-1$
            }
        }

        if (!tasks.isEmpty()) {
            // Sort the task in priority order
            Collections.sort(tasks);
        }

        return tasks;
    }

    /**
     * Wrapper around the post project import task extension.
     * 
     */
    private static class PostImportTask implements Comparable<PostImportTask> {
        private int priority = 50;

        private String id;

        private IPostImportProjectTask task;

        private final Set<String> natureIds;

        public PostImportTask(IConfigurationElement element)
                throws CoreException {

            id = element.getAttribute("id"); //$NON-NLS-1$
            String priorityStr = element.getAttribute("priority"); //$NON-NLS-1$

            if (priorityStr != null && !priorityStr.isEmpty()) {
                priority = Integer.parseInt(priorityStr);

                if (priority < 1) {
                    priority = 1;
                } else if (priority > 100) {
                    priority = 100;
                }
            }

            task =
                    (IPostImportProjectTask) element
                            .createExecutableExtension("class"); //$NON-NLS-1$

            natureIds = getNatureIds(element);
        }

        /**
         * @return the priority
         */
        public int getPriority() {
            return priority;
        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @return the task
         */
        public IPostImportProjectTask getTask() {
            return task;
        }

        /**
         * Check if this import task applies to the given project.
         * 
         * @param project
         * @return <code>true</code> if this post import extension has no nature
         *         ids specified, or explicitly specifies the nature of this
         *         project.
         * @throws CoreException
         * @since 3.6.1
         */
        public boolean appliesTo(IProject project) throws CoreException {
            if (!natureIds.isEmpty()) {
                for (String natureId : natureIds) {
                    if (project.hasNature(natureId)) {
                        return true;
                    }
                }
            } else {
                // No nature ids specified so applies to all project types
                return true;
            }
            return false;
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return String.format("%s (priority: %d)", getId(), getPriority()); //$NON-NLS-1$
        }

        /**
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         * 
         * @param o
         * @return
         */
        @Override
        public int compareTo(PostImportTask o) {
            if (this.priority < o.getPriority()) {
                return -1;
            } else if (this.priority > o.getPriority()) {
                return 1;
            }
            return 0;
        }

        /**
         * Get the specified natureIds.
         * 
         * @param element
         * 
         * @return Set of nature ids if specified, empty set otherwise (applies
         *         to all project natures).
         */
        private Set<String> getNatureIds(IConfigurationElement element) {
            Set<String> natureIds = new HashSet<String>();

            String values = element.getAttribute("appliesToNatureIds"); //$NON-NLS-1$

            if (values != null && !values.isEmpty()) {
                if (values.equals("*")) { //$NON-NLS-1$
                    // Applies to all project natures so return empty set.
                    return natureIds;
                }

                String[] ids = values.split(","); //$NON-NLS-1$

                for (String id : ids) {
                    id = id.trim();
                    if (!id.isEmpty()) {
                        natureIds.add(id);
                    }
                }
            } else {
                // Default value
                natureIds.add(XpdConsts.PROJECT_NATURE_ID);
            }

            return natureIds;
        }
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.resources.migrateproject.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.migrateproject.MigrateProject;
import com.tibco.xpd.resources.migrateproject.util.MigrateProjectElem;
import com.tibco.xpd.resources.migrateproject.util.MigrateProjectManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Migrate Project action. This action is added to the popup menu when a Studio
 * 1.1 project is selected. This action will migrate the project to the current
 * version by updating it's natures. Also, migration will be delegated to any
 * extensions of the migrate project extension point.
 * 
 * @author njpatel
 */
public class MigrateProjectAction implements IObjectActionDelegate {

    /** Minimum amount of time for progress diaglog to be visible (ms).* */
    private static final int PROGRESS_MINIMUM_VISIBLE = 2000;

    private static final String PROJECT_1_1_NATURE =
            "com.tibco.xpd.resources.bpmNature"; //$NON-NLS-1$

    private IStructuredSelection selection;

    private List<MigrateProject> extensionExecs =
            new ArrayList<MigrateProject>();

    private Shell shell;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
     * action.IAction, org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // Get all extension of the migrate project extension point
        MigrateProjectElem[] extensions =
                MigrateProjectManager.createInstance().getExtensions();

        // Get the shell
        if (targetPart != null) {
            this.shell = targetPart.getSite().getShell();
        }

        // Sort the extensions in accordance with the dependencies
        if (extensions.length > 0) {
            Arrays.sort(extensions, new MigrationExtComparator());
        }

        extensionExecs.clear();

        if (extensions != null) {
            try {
                for (MigrateProjectElem ext : extensions) {
                    MigrateProject exec = ext.getMigrateProject();

                    if (exec != null) {
                        exec.setAction(action);
                        exec.setTargetPart(targetPart);
                        extensionExecs.add(exec);
                    }
                }
            } catch (CoreException e) {
                showError(e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        if (selection != null) {
            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                Object next = iter.next();

                final IProject project = (IProject) next;
                // This is done only to force config to be created before we
                // start migration steps
                // Otherwise you may get resource locked problems.
                XpdResourcesPlugin.getDefault()
                        .createDefaultProjectConfigFile(project);

                IRunnableWithProgress runnable = new IRunnableWithProgress() {
                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {
                        /*
                         * Update the nature - remove the Studio 1.1 nature and
                         * add the new xpd nature
                         */
                        try {
                            IProjectDescription description =
                                    project.getDescription();

                            // Update the nature
                            updateNature(description);

                            // Apply description back to project
                            project.setDescription(description, null);

                            // Run all extensions
                            monitor
                                    .beginTask(Messages.MigrateProjectAction_migrationTask_label,
                                            extensionExecs.size() + 1);
                            for (MigrateProject ext : extensionExecs) {
                                ext.migrate(project, monitor);
                            }
                            monitor.worked(1);

                            /*
                             * Migrate all assets to the latest version
                             */
                            ProjectAssetMigrationManager.getInstance()
                                    .migrate(project,
                                            new SubProgressMonitor(monitor, 1));

                        } catch (Exception ex) {
                            throw new InvocationTargetException(ex);
                        } finally {
                            monitor.done();
                        }
                    }
                };

                ProgressMonitorDialog d;
                d =
                        new ProgressMonitorDialog(Display.getCurrent()
                                .getActiveShell());
                d.setOpenOnRun(true);
                boolean fork = true;
                boolean cancelable = false;
                long startTime = System.currentTimeMillis();
                boolean migrationFailed = false;
                try {
                    d.run(fork, cancelable, runnable);
                } catch (InvocationTargetException ex) {
                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(ex.getCause(),
                                    Messages.MigrateProjectAction_migrationException_shortDesc);
                    migrationFailed = true;
                } catch (InterruptedException ex) {
                    XpdResourcesPlugin.getDefault().getLogger().error(ex);
                    migrationFailed = true;
                }

                if (migrationFailed) {
                    String title =
                            Messages.MigrateProjectAction_errorDialog_title;
                    String message =
                            Messages.MigrateProjectAction_errorDialog_message;
                    MessageDialog.openError(Display.getCurrent()
                            .getActiveShell(), title, message);
                } else {
                    // If dialog was shown for shorter time than we expected,
                    // then wait. We don't want progress dialog to blink on
                    // short jobs.
                    long timeDelta = System.currentTimeMillis() - startTime;
                    if (timeDelta < PROGRESS_MINIMUM_VISIBLE) {
                        try {
                            Thread.sleep(PROGRESS_MINIMUM_VISIBLE - timeDelta);
                        } catch (InterruptedException ex) {
                            XpdResourcesPlugin.getDefault().getLogger()
                                    .error(ex);
                        }
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
     * .IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            this.selection = (IStructuredSelection) selection;
        } else {
            this.selection = null;
        }
    }

    /**
     * Update the nature of this project - remove the Studio 1.1 project nature
     * and add the latest nature.
     * 
     * @param description
     *            Project description.
     */
    private void updateNature(IProjectDescription description) {
        if (description != null) {
            String[] natureIds = description.getNatureIds();

            if (natureIds != null) {
                List<String> natureList =
                        new ArrayList<String>(Arrays.asList(natureIds));

                // Remove the 1.1 project nature
                natureList.remove(PROJECT_1_1_NATURE);
                // Add current nature
                if (!natureList.contains(XpdConsts.PROJECT_NATURE_ID)) {
                    natureList.add(XpdConsts.PROJECT_NATURE_ID);
                }

                // Update description
                description.setNatureIds(natureList
                        .toArray(new String[natureList.size()]));
            }
        }
    }

    /**
     * Show an error dialog with the given core exception.
     * 
     * @param e
     */
    private void showError(CoreException e) {
        if (e != null) {
            ErrorDialog.openError(shell,
                    Messages.MigrateProjectAction_errorDialog_title,
                    Messages.MigrateProjectAction_errorDialog_message,
                    e.getStatus());
        }
    }

    /**
     * Comparator of migrate project extensions. This will sort in accordance
     * with the dependencies.
     * 
     * @author njpatel
     * 
     */
    private class MigrationExtComparator implements
            Comparator<MigrateProjectElem> {

        public int compare(MigrateProjectElem o1, MigrateProjectElem o2) {

            if (!o1.equals(o2)) {
                /*
                 * Check the dependencies
                 */
                List<String> ids = o1.getDependsOnIds();

                // If o1 depends on o2 then o2 should be executed first
                if (ids.contains(o2.getId())) {
                    return 1;
                }

                // If o2 depends on o1 then o1 should be executed first
                ids = o2.getDependsOnIds();

                if (ids.contains(o1.getId())) {
                    return -1;
                }
            } else {
                return 0;
            }

            return 1;
        }

    }

}

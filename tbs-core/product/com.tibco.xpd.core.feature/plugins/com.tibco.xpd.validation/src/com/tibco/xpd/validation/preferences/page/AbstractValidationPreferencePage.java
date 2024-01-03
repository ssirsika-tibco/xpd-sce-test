/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.preferences.page;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.preferences.control.PreferenceGroupsControl;

/**
 * Abstract preference page for validation issues. This will provide a framework
 * for Error/Warning style preference page that will allow the setting of the
 * severity level for validation issues. The severity values will be Error,
 * Warning, Info and Ignore.
 * <p>
 * This class uses the <b>com.tibco.xpd.validation.provider</b> extension point
 * to determine which preference groups / issues to include in this page. This
 * will be determined by the <i>preferenceId</i> provided. All preferenceGroups
 * with this <i>preferenceId</i> will be included in this page. The
 * preferenceGroups will have their assigned issues displayed in a
 * description-combo pairing.
 * </p>
 * <p>
 * To persist the (expand) state of the preference groups within the page use
 * <code>{@link #setDialogSettings(IDialogSettings, String)}</code> to set the
 * plugin's dialog settings object.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class AbstractValidationPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private final String preferenceId;

    private final PreferenceGroupsControl prefGroup;

    /**
     * Default constructor.
     * 
     * @param preferenceId
     *            preference id. This page will contain all preference groups
     *            assigned this preference id.
     */
    public AbstractValidationPreferencePage(String preferenceId) {
        this.preferenceId = preferenceId;

        if (preferenceId == null) {
            throw new NullPointerException("Preference id is null."); //$NON-NLS-1$
        }

        prefGroup = new PreferenceGroupsControl(this.preferenceId);
    }

    /**
     * Set the IDialogSettings to persist the state of the groups.
     * 
     * @param dialogSettings
     *            <code>IDialogSettings</code> of the plugin
     * @param sectionId
     *            Section to add to the dialog settings to persist this data.
     */
    public void setDialogSettings(IDialogSettings dialogSettings,
            String sectionId) {
        prefGroup.setDialogSetting(dialogSettings, sectionId);
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite groups = prefGroup.createControl(parent,
                convertWidthInCharsToPixels(10));

        return groups;
    }

    @Override
    public void dispose() {
        prefGroup.dispose();
        super.dispose();
    }

    @Override
    protected void performDefaults() {
        prefGroup.restoreDefaults();
        super.performDefaults();
    }

    @Override
    protected void performApply() {
        doUpdate(null, null);
    }

    @Override
    public boolean performOk() {
        return doUpdate((IWorkbenchPreferenceContainer) getContainer(), null);
    }

    /**
     * Update the preference store - the user will be asked if they wish to
     * rebuild.
     * 
     * @param container
     *            <code>IWorkbenchPreferenceContainer</code>.
     * @param project
     *            <code>IProject</code> to build, <code>null</code> if full
     *            workspace build is required.
     * @return <code>false</code> if the user decided to cancel the update,
     *         <code>true</code> otherwise.
     */
    protected boolean doUpdate(IWorkbenchPreferenceContainer container,
            IProject project) {
        boolean updated = true;

        if (prefGroup.hasValueChanged()) {
            /*
             * Ask user if they wish to build, if the user decides to cancel at
             * this stage then the preference store should not be updated.
             */
            MessageDialog dialog = new MessageDialog(
                    getShell(),
                    Messages.AbstractValidationPreferencePage_errorWarningUpdateDialog_title,
                    null,
                    Messages.AbstractValidationPreferencePage_errorWarningUpdateDialog_message,
                    MessageDialog.QUESTION, new String[] {
                            IDialogConstants.YES_LABEL,
                            IDialogConstants.NO_LABEL,
                            IDialogConstants.CANCEL_LABEL }, 3);

            int dlgRet = dialog.open();

            if (dlgRet != 2 /* cancel */) {
                // Update the preferences
                prefGroup.saveChangedPreferences();

                // Do build if requested
                if (dlgRet == 0 /* yes */) {
                    rebuild(container, project);
                }
            } else {
                // Update cancelled
                updated = false;
            }
        }

        return updated;
    }

    /**
     * Rebuild the project / workspace. This will carry out a FULL build.
     * 
     * @param container
     *            workbench preference container, can be <code>null</code>.
     * @param project
     *            {@link IProject} of project to rebuild, if this is
     *            <code>null</code> then the whole workspace will be built.
     */
    protected void rebuild(IWorkbenchPreferenceContainer container,
            IProject project) {
        Job job = getBuildJob(project);

        if (container != null) {
            container.registerUpdateJob(job);
        } else {
            job.schedule();
        }
    }

    /**
     * Get a full build job.
     * 
     * @param project
     *            project to build. If project is <code>null</code> then the
     *            whole workspace will be rebuilt.
     * @return build <code>Job</code>
     */
    private Job getBuildJob(final IProject project) {
        return new BuildJob(
                Messages.AbstractValidationPreferencePage_buildJob_label,
                project);
    }

    /**
     * Build job to revalidate the validation rules.
     * 
     * @author njpatel
     * 
     */
    private class BuildJob extends Job {
        private final IProject project;

        /**
         * Constructor
         * 
         * @param name
         *            name of the job
         * @param project
         *            project to build, if this is <code>null</code> then the
         *            entire workspace will be built.
         */
        private BuildJob(String name, IProject project) {
            super(name);
            this.project = project;
        }

        /**
         * Check if this build is covered by another job.
         * 
         * @param other
         * @return <code>true</code> if this build is covered by the <i>other</i>
         *         build job.
         */
        public boolean isCoveredBy(BuildJob other) {
            if (other.project == null) {
                return true;
            }
            return project != null && project.equals(other.project);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
         */
        @Override
        protected IStatus run(IProgressMonitor monitor) {
            synchronized (getClass()) {
                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                // Check if a build job is already running
                Job[] buildJobs = Job.getJobManager().find(
                        ResourcesPlugin.FAMILY_MANUAL_BUILD);
                for (int i = 0; i < buildJobs.length; i++) {
                    Job curr = buildJobs[i];
                    if (curr != this && curr instanceof BuildJob) {
                        BuildJob job = (BuildJob) curr;
                        if (job.isCoveredBy(this)) {
                            // cancel all other build jobs of our kind
                            curr.cancel();
                        }
                    }
                }
            }
            try {
                if (project != null) {
                    monitor
                            .beginTask(
                                    String
                                            .format(
                                                    Messages.AbstractValidationPreferencePage_buildProject_label,
                                                    project.getName()), 2);
                    project.build(IncrementalProjectBuilder.FULL_BUILD,
                            new SubProgressMonitor(monitor, 1));
                    ResourcesPlugin.getWorkspace().build(
                            IncrementalProjectBuilder.INCREMENTAL_BUILD,
                            new SubProgressMonitor(monitor, 1));
                } else {
                    monitor
                            .beginTask(
                                    Messages.AbstractValidationPreferencePage_buildAll_label,
                                    2);
                    ResourcesPlugin.getWorkspace().build(
                            IncrementalProjectBuilder.FULL_BUILD,
                            new SubProgressMonitor(monitor, 2));
                }
            } catch (CoreException e) {
                return e.getStatus();
            } catch (OperationCanceledException e) {
                return Status.CANCEL_STATUS;
            } finally {
                monitor.done();
            }
            return Status.OK_STATUS;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.jobs.Job#belongsTo(java.lang.Object)
         */
        @Override
        public boolean belongsTo(Object family) {
            return ResourcesPlugin.FAMILY_MANUAL_BUILD == family;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // Do nothing
    }

}
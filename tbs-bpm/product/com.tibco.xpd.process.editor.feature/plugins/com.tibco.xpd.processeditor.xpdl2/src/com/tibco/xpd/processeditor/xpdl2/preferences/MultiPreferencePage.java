package com.tibco.xpd.processeditor.xpdl2.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.ProcessDestinationEnvironment;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.preferences.control.PreferenceGroupsControl;

public class MultiPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private final Collection<ProcessDestinationEnvironment> pde;

    private Categories categories;

    public MultiPreferencePage() {
        pde = DestinationUtil.getAvailableProcessDestinationEnvironments();
        categories = new Categories();
    }

    @Override
    protected Composite createContents(Composite parent) {
        Composite result = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, true);
        result.setLayout(layout);

        CCombo combo = new CCombo(result, SWT.BORDER);
        PageBook catComposite = new PageBook(result, SWT.NONE);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        catComposite.setLayoutData(gd);
        catComposite.setBackground(PlatformUI.getWorkbench().getDisplay()
                .getSystemColor(SWT.COLOR_RED));

        addCategories(catComposite, categories);

        /* Sort alphabetically for consistent appearance. */
        categories.sortByName();

        for (Category cat : categories) {
            combo.add(cat.getName());
        }
        if (categories.size() > 0) {
            combo.select(0);
            Category category = categories.get(0);
            Composite composite = category.getComposite();
            catComposite.showPage(composite);
        }
        SelectionListener listener =
                new CategoryListener(catComposite, categories);
        combo.addSelectionListener(listener);

        return result;
    }

    private void addCategories(PageBook catComposite, Categories categories) {
        IDialogSettings dialogSettings =
                XpdResourcesPlugin.getDefault().getDialogSettings();
        int minWidth = convertWidthInCharsToPixels(10);
        for (ProcessDestinationEnvironment env : pde) {
            Category category =
                    new Category(env, catComposite, minWidth, dialogSettings);
            if (!category.isEmpty()) {
                category.getComposite();
                categories.add(category);
            }
        }

    }

    class CategoryListener implements SelectionListener {

        private final PageBook root;

        private final Categories categories;

        public CategoryListener(PageBook composite, Categories categories) {
            this.root = composite;
            this.categories = categories;
        }

        @Override
		public void widgetDefaultSelected(SelectionEvent e) {
            performAction(e);
        }

        @Override
		public void widgetSelected(SelectionEvent e) {
            performAction(e);
        }

        private void performAction(SelectionEvent e) {
            CCombo combo = (CCombo) e.getSource();
            int selectionIndex = combo.getSelectionIndex();
            Category category = categories.get(selectionIndex);
            Composite composite = category.getComposite();
            root.showPage(composite);
        }

    }

    @Override
	public void init(IWorkbench workbench) {
    }

    @Override
    public void dispose() {
        categories.dispose();
        super.dispose();
    }

    @Override
    protected void performDefaults() {
        categories.restoreDefaults();
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

        if (categories.hasValueChanged()) {
            /*
             * Ask user if they wish to build, if the user decides to cancel at
             * this stage then the preference store should not be updated.
             */
            MessageDialog dialog =
                    new MessageDialog(
                            getShell(),
                            Messages.PreferencePage_errorWarningUpdateDialog_title,
                            null,
                            Messages.PreferencePage_errorWarningUpdateDialog_message,
                            MessageDialog.QUESTION, new String[] {
                                    IDialogConstants.YES_LABEL,
                                    IDialogConstants.NO_LABEL,
                                    IDialogConstants.CANCEL_LABEL }, 3);

            int dlgRet = dialog.open();

            if (dlgRet != 2 /* cancel */) {
                // Update the preferences
                categories.saveChangedPreferences();

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
        return new BuildJob(Messages.PreferencePage_buildJob_label, project);
    }

    /**
     * Build job to revalidate the validation rules.
     * 
     * @author njpatel
     * 
     */
    static class BuildJob extends Job {
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
        BuildJob(String name, IProject project) {
            super(name);
            this.project = project;
        }

        /**
         * Check if this build is covered by another job.
         * 
         * @param other
         * @return <code>true</code> if this build is covered by the
         *         <i>other</i> build job.
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
         * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
         * IProgressMonitor)
         */
        @Override
        protected IStatus run(IProgressMonitor monitor) {
            synchronized (getClass()) {
                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                // Check if a build job is already running
                Job[] buildJobs =
						Job.getJobManager()
                                .find(ResourcesPlugin.FAMILY_MANUAL_BUILD);
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
                    monitor.beginTask(String
                            .format(Messages.PreferencePage_buildProject_label,
                                    project.getName()), 2);
                    project.build(IncrementalProjectBuilder.FULL_BUILD,
                            new SubProgressMonitor(monitor, 1));
                    ResourcesPlugin.getWorkspace()
                            .build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                                    new SubProgressMonitor(monitor, 1));
                } else {
                    monitor
                            .beginTask(Messages.PreferencePage_buildAll_label,
                                    2);
                    ResourcesPlugin.getWorkspace()
                            .build(IncrementalProjectBuilder.FULL_BUILD,
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

    static class Categories implements Iterable<Category> {
        private List<Category> categories;

        Categories() {
            categories = new ArrayList<Category>();
        }

        public int size() {
            return categories.size();
        }

        public boolean hasValueChanged() {
            boolean result = false;
            for (Category cat : categories) {
                if (cat.hasValueChanged()) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        public void saveChangedPreferences() {
            for (Category cat : categories) {
                cat.saveChangedPreferences();
            }
        }

        public void restoreDefaults() {
            for (Category cat : categories) {
                cat.restoreDefaults();
            }
        }

        public void dispose() {
            for (Category cat : categories) {
                cat.dispose();
            }
        }

        public Category get(int index) {
            return categories.get(index);
        }

        void add(Category cat) {
            categories.add(cat);
        }

        @Override
		public Iterator<Category> iterator() {
            return categories.iterator();
        }

        public void sortByName() {
            Collections.sort(categories, new Comparator<Category>() {
                @Override
				public int compare(Category arg0, Category arg1) {
                    return arg0.getName().compareToIgnoreCase(arg1.getName());
                }
            });
        }
    }

    /**
     * @author mmaciuki
     * 
     */
    static class Category {

        private final Composite parent;

        private Composite control;

        private PreferenceGroupsControl p;

        private final int minWidth;

        private final ProcessDestinationEnvironment pde;

        /**
         * 
         * @param pde
         * @param parent
         * @param minWidth
         * @param dialogSettings
         * @throws NullPointerException
         *             If any of the arguments supplied are null.
         */
        public Category(ProcessDestinationEnvironment pde, Composite parent,
                int minWidth, IDialogSettings dialogSettings) {
            // TODO TS 3 Aug 07 (code review) How may these arguments be null?
            // TODO Should log if it happens telling the caller how to avoid it.
            if (pde == null) {
                throw new NullPointerException(
                        Messages.MultiPreferencePage_ProcessDestinationEnvironmentIsNull_error_message);
            }
            if (parent == null) {
                throw new NullPointerException(
                        Messages.MultiPreferencePage_ParentCompositeIsNull_error_message);
            }
            if (dialogSettings == null) {
                throw new NullPointerException(
                        Messages.MultiPreferencePage_DialogSettingsIsNull_error_message);
            }
            this.pde = pde;
            this.parent = parent;
            this.minWidth = minWidth;
            p = new PreferenceGroupsControl(pde.getId());
            p.setDialogSetting(dialogSettings, "processValidationPreference"); //$NON-NLS-1$
        }

        public String getName() {
            return pde.getName();
        }

        public boolean hasValueChanged() {
            return p.hasValueChanged();
        }

        public void saveChangedPreferences() {
            p.saveChangedPreferences();
        }

        public void restoreDefaults() {
            p.restoreDefaults();
        }

        public void dispose() {
            p.dispose();
        }

        synchronized Composite getComposite() {
            if (control == null) {
                control = new Composite(parent, SWT.NONE);
                GridLayout layout = new GridLayout(1, true);
                control.setLayout(layout);
                Label l = new Label(control, SWT.NONE);
                l.setText(Messages.PreferencePage_msg);
                GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
                Composite configControl = p.createControl(control, minWidth);
                configControl.setLayoutData(gd);
            }
            return control;
        }

        boolean isEmpty() {
            return p.isEmpty();
        }
    }
}

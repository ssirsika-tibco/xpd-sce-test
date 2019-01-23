package com.tibco.xpd.bom.resources.ui.internal.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.GlobalBuildAction;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Page for setting BPM debugging preferences.
 * 
 * @author jarciuch
 * @since 3.5.3
 */
public class BpmDebugPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private Button enableDebugButton;

    private IWorkbenchWindow activeWorkbenchWindow;

    /**
     * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
     */
    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return Activator.getDefault().getPreferenceStore();
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
        activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().applyTo(root);

        Group debugModeGroup = new Group(root, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(debugModeGroup);
        GridLayoutFactory.swtDefaults().applyTo(debugModeGroup);
        debugModeGroup.setText(Messages.BpmDebugPreferencePage_TibcoBpmDebugMode_label);
        enableDebugButton = new Button(debugModeGroup, SWT.CHECK);
        enableDebugButton.setText(Messages.BpmDebugPreferencePage_EnableBpmDebug_button);

        Label debugNoteLabel = new Label(debugModeGroup, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, false).indent(20, 0)
                .applyTo(debugNoteLabel);
        debugNoteLabel
                .setText(Messages.BpmDebugPreferencePage_NoteAboutDebugging_shortdesc);

        Label pageNoteLabel = new Label(root, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(pageNoteLabel);
        pageNoteLabel
                .setText(Messages.BpmDebugPreferencePage_NoteAboutBuild_shortdesc);
        loadValues();
        return root;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {
        enableDebugButton.setSelection(getPreferenceStore()
                .getDefaultBoolean(BpmPreferenceUtil.IS_IN_BPM_DEBUG_MODE));
        super.performDefaults();
    }

    /**
     * Loads values into controls from preference store.
     */
    private void loadValues() {
        enableDebugButton.setSelection(getPreferenceStore()
                .getBoolean(BpmPreferenceUtil.IS_IN_BPM_DEBUG_MODE));
    }

    /**
     * Saves values back to preference store from controls.
     */
    private boolean saveValues() {
        boolean changed = false;
        boolean currentDebugMode = enableDebugButton.getSelection();
        if (currentDebugMode != getPreferenceStore()
                .getBoolean(BpmPreferenceUtil.IS_IN_BPM_DEBUG_MODE)) {
            getPreferenceStore()
                    .setValue(BpmPreferenceUtil.IS_IN_BPM_DEBUG_MODE,
                            currentDebugMode);
            changed = true;
        }
        return changed;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    @Override
    public boolean performOk() {
        boolean isBuildNeeded = saveValues();
        if (isBuildNeeded) {
            scheduleCleanWorkspaceBuild();
        }
        return true;
    }

    /**
     * Schedules a clean workspace build.
     */
    private void scheduleCleanWorkspaceBuild() {
        WorkspaceJob cleanJob = new WorkspaceJob(Messages.BpmDebugPreferencePage_CleaningProjects_shortdesc) {
            @Override
            public boolean belongsTo(Object family) {
                return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
            }

            @Override
            public IStatus runInWorkspace(IProgressMonitor monitor)
                    throws CoreException {
                List<IProject> studioProjects = new ArrayList<IProject>();
                for (IResource r : ResourcesPlugin.getWorkspace().getRoot()
                        .members()) {
                    if (r instanceof IProject
                            && ProjectUtil.isStudioProject((IProject) r)) {
                        studioProjects.add((IProject) r);
                    }
                }
                monitor.beginTask(Messages.BpmDebugPreferencePage_CleaningProjects_shortdesc, studioProjects.size());
                for (IProject project : studioProjects) {
                    project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                            new SubProgressMonitor(monitor, 1));
                }
                // Starts an immediate workspace build.
                GlobalBuildAction build =
                        new GlobalBuildAction(activeWorkbenchWindow,
                                IncrementalProjectBuilder.INCREMENTAL_BUILD);
                build.doBuild();
                return Status.OK_STATUS;
            }
        };
        cleanJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory()
                .buildRule());
        cleanJob.setUser(true);
        cleanJob.schedule();
    }
}
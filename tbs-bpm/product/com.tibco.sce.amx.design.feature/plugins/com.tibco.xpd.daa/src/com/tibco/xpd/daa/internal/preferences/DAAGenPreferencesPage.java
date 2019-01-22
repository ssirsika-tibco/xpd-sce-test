package com.tibco.xpd.daa.internal.preferences;

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
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.GlobalBuildAction;

import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.daa.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

public class DAAGenPreferencesPage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private Button cleanDAAStageButton;

    private Button cacheBomJarsButton;

    private Button confirmTargetPlatformUse;

    private IWorkbenchWindow activeWorkbenchWindow;

    /**
     * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
     */
    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return DaaActivator.getDefault().getPreferenceStore();
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

        Group daaGenGroup = new Group(root, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(daaGenGroup);
        GridLayoutFactory.swtDefaults().applyTo(daaGenGroup);
        daaGenGroup.setText(Messages.DAAGenPreferencesPage_DaaGenOptions_label);
        cleanDAAStageButton = new Button(daaGenGroup, SWT.CHECK);
        cleanDAAStageButton
                .setText(Messages.DAAGenPreferencesPage_CleanDaaStagingFolder_label);
        cacheBomJarsButton = new Button(daaGenGroup, SWT.CHECK);
        cacheBomJarsButton
                .setText(Messages.DAAGenPreferencesPage_cacheBomJars_label);

        Group targetPlatform = new Group(root, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(targetPlatform);
        GridLayoutFactory.swtDefaults().applyTo(targetPlatform);
        targetPlatform
                .setText(Messages.DAAGenPreferencesPage_CONFIRM_TARGET_PLATFORM);

        confirmTargetPlatformUse = new Button(targetPlatform, SWT.CHECK);
        confirmTargetPlatformUse
                .setText(Messages.DAAGenPreferencesPage_DO_NOT_SHOW_CCONFIRM_DIALOG);
        loadValues();
        return root;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {
        cleanDAAStageButton.setSelection(DAAGenPreferences
                .cleanDAAStageFolder());
        cacheBomJarsButton.setSelection(DAAGenPreferences.cacheBomJars());
        super.performDefaults();
    }

    /**
     * Loads values into controls from preference store.
     */
    private void loadValues() {
        cleanDAAStageButton.setSelection(DAAGenPreferences
                .cleanDAAStageFolder());
        cacheBomJarsButton.setSelection(DAAGenPreferences.cacheBomJars());
        confirmTargetPlatformUse.setSelection(DAAGenPreferences
                .confirmTargetPlatform());
    }

    /**
     * Saves values back to preference store from controls.
     */
    private void saveValues() {
        boolean currentClenDAAStageValue = cleanDAAStageButton.getSelection();
        if (currentClenDAAStageValue != DAAGenPreferences.cleanDAAStageFolder()) {
            DAAGenPreferences.setCleanDAAStageFolder(currentClenDAAStageValue);
        }
        boolean currentCacheBomJarsValue = cacheBomJarsButton.getSelection();
        if (currentCacheBomJarsValue != DAAGenPreferences.cacheBomJars()) {
            DAAGenPreferences.setCacheBomJars(currentCacheBomJarsValue);
        }
        boolean currentConfirmTPValue = confirmTargetPlatformUse.getSelection();
        if (currentConfirmTPValue != DAAGenPreferences.confirmTargetPlatform()) {
            DAAGenPreferences.setConfirmTargetPlatform(currentConfirmTPValue);
        }
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    @Override
    public boolean performOk() {
        if (hasDaaPreferenceSettingChanged()) {
            if (shouldWorkspaceRebuild()) {
                /*
                 * if the preferences have changed and user clicks on Apply/Ok
                 * and says yes for re-build workspace then save the changes and
                 * clean workspace.
                 */
                saveValues();
                scheduleCleanWorkspaceBuild();
                return true;
            } else {
                return false;
            }
        }
        return super.performOk();
    }

    /**
     * 
     * @return <code>true</code> if the DAA Preference Settings have changed
     *         else <code>false</code>
     */
    private boolean hasDaaPreferenceSettingChanged() {
        if (cleanDAAStageButton.getSelection() != DAAGenPreferences
                .cleanDAAStageFolder()
                || cacheBomJarsButton.getSelection() != DAAGenPreferences
                        .cacheBomJars()
                || confirmTargetPlatformUse.getSelection() != DAAGenPreferences
                        .confirmTargetPlatform()) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @return <code>true</code> if the user chooses to re-build workspace else
     *         <code>false</code>
     */
    private boolean shouldWorkspaceRebuild() {

        MessageDialog dialog =
                new MessageDialog(
                        getShell(),
                        Messages.DAAGenPreferencesPage_DaaExportSettingsChangedDialog_title,
                        null,
                        Messages.DAAGenPreferencesPage_DaaExportSettingsChangedDialog_desc,
                        MessageDialog.QUESTION, new String[] {
                                IDialogConstants.YES_LABEL,
                                IDialogConstants.NO_LABEL }, 2);

        int dlgRet = dialog.open();
        if (dlgRet == /* yes */0) {
            return true;
        }
        return false;
    }

    /**
     * Performs clean workspace build
     */
    private void scheduleCleanWorkspaceBuild() {
        WorkspaceJob cleanJob =
                new WorkspaceJob(
                        Messages.DAAGenPreferencesPage_CleanProjectsJob_label) {

                    @Override
                    public boolean belongsTo(Object family) {
                        return ResourcesPlugin.FAMILY_MANUAL_BUILD
                                .equals(family);
                    }

                    @Override
                    public IStatus runInWorkspace(IProgressMonitor monitor)
                            throws CoreException {
                        List<IProject> studioProjects =
                                new ArrayList<IProject>();
                        for (IResource r : ResourcesPlugin.getWorkspace()
                                .getRoot().members()) {
                            if (r instanceof IProject
                                    && ProjectUtil
                                            .isStudioProject((IProject) r)) {
                                studioProjects.add((IProject) r);
                            }
                        }
                        monitor.beginTask(Messages.DAAGenPreferencesPage_CleanProjectsJob_label,
                                studioProjects.size());
                        for (IProject project : studioProjects) {
                            project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                                    new SubProgressMonitor(monitor, 1));
                        }
                        // Starts an immediate workspace build.
                        GlobalBuildAction build =
                                new GlobalBuildAction(
                                        activeWorkbenchWindow,
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

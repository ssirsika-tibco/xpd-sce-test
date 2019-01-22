/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.internal.utils.MonitorDialog;
import com.tibco.xpd.rcp.internal.utils.SaveChangesDialog;

/**
 * Workbench window configuration.
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("restriction")
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    /**
     * Forms preview port preference id.
     */
    private static final String PREVIEW_PORT_PREF_ID = "previewPort"; //$NON-NLS-1$

    /**
     * Plug-in id of the Forms Designer Editor plug-in.
     */
    private static final String FORMS_DESIGNER_EDITOR_PLUGIN_ID =
            "com.tibco.xpd.forms.designer.editor"; //$NON-NLS-1$

    /**
     * Default height of the RCP application window
     */
    private static final int DEFAULT_HEIGHT = 768;

    /**
     * Default width of the RCP application window
     */
    private static final int DEFAULT_WIDTH = 1146;

    private static final String RCP_WIDTH = "rcp-width"; //$NON-NLS-1$

    private static final String RCP_HEIGHT = "rcp-height"; //$NON-NLS-1$

    /**
     * Sid XPD-8302 Ribbon removed ribbon control added (the control from the
     * {@link RibbonViewPart} that is now contributed as a ViewPart)
     */
    private Control ribbonControl;

    public ApplicationWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor
     * (org.eclipse.ui.application.IActionBarConfigurer)
     */
    @Override
    public ActionBarAdvisor createActionBarAdvisor(
            IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#preWindowOpen()
     */
    @Override
    public void preWindowOpen() {

        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(getpersistedWindowSize());
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(true);
        configurer.setShowMenuBar(false);
        configurer.setShowProgressIndicator(true);
        configurer.getWorkbenchConfigurer().setSaveAndRestore(false);

        /*
         * Disable the dialog that confirms with user whether package should be
         * saved when saving a process.
         */
        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        if (prefStore != null) {
            if (!prefStore.getBoolean(
                    ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE)) {
                prefStore.setValue(
                        ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE,
                        true);
            }
        }

        /*
         * XPD-4078: Set preview port to 0 to avoid the jetty server startup
         * problem when multiple instance of this RCP are run.
         */
        IPreferenceStore designerPreferenceStore = new ScopedPreferenceStore(
                new InstanceScope(), FORMS_DESIGNER_EDITOR_PLUGIN_ID);
        if (designerPreferenceStore != null) {
            if (designerPreferenceStore.getInt(PREVIEW_PORT_PREF_ID) != 0) {
                designerPreferenceStore.setDefault(PREVIEW_PORT_PREF_ID, 0);
                designerPreferenceStore.setValue(PREVIEW_PORT_PREF_ID, 0);
            }
        }

    }

    /**
     * 
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#createWindowContents(org.eclipse.swt.widgets.Shell)
     *
     * @param shell
     * 
     * @deprecated the underlying super class / framework never calls this
     *             method any more.
     */
    @Deprecated
    @Override
    public void createWindowContents(final Shell shell) {
        /*
         * SID XPD-8302 - this function is no longer called by Eclipse 4.x. That
         * means we have to take a different approach to setting up the static
         * ribbon control (by contributing it as a ViewPart) as we don't get
         * direct control over the top level controls in the workbench anymore.
         */
        return;
    }

    @Override
    public boolean preWindowShellClose() {
        final IWorkbenchWindow window = getWindowConfigurer().getWindow();
        final Shell shell = window.getShell();
        final IRCPResource resource = RCPResourceManager.getResource();
        final Boolean doSave[] = new Boolean[] { false };

        if (resource != null) {
            if (resource.isDirty()) {
                // Ask user if they wish to save changes
                SaveChangesDialog dlg = new SaveChangesDialog(shell, resource);
                switch (dlg.open()) {
                case SaveChangesDialog.YES_ID:
                    // Save the changes
                    doSave[0] = true;
                    break;
                case SaveChangesDialog.CANCEL_ID:
                    // User cancelled the save so abort close
                    return false;
                }
            }

            WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

                @Override
                protected void execute(IProgressMonitor monitor)
                        throws CoreException, InvocationTargetException,
                        InterruptedException {
                    // Setting title on beginTask doesn't work
                    monitor.beginTask("", 3); //$NON-NLS-1$
                    monitor.setTaskName(
                            Messages.ApplicationWorkbenchWindowAdvisor_closing_monitor_shortdesc);
                    monitor.worked(1);

                    if (doSave[0]) {
                        boolean ret = resource
                                .save(new SubProgressMonitor(monitor, 1));
                        if (!ret) {
                            // User cancelled save
                            throw new OperationCanceledException();
                        }
                    }
                    monitor.worked(1);
                    // Dispose off the resource -this will clear the workspace
                    // of the project
                    resource.dispose();
                    monitor.done();
                }
            };
            try {
                new MonitorDialog(shell).run(op);

                /*
                 * Sid XPD-8302 - moved closeAllEditors to a finally clause to
                 * make sure it gets done. Startup doesn't do well if editors
                 * are left open because startup deletes the projects left
                 * behind by the previous session.
                 */
                // if (window.getActivePage() != null) {
                // window.getActivePage().closeAllEditors(false);
                // }

            } catch (InvocationTargetException e) {
                IStatus status;
                if (e.getTargetException() instanceof CoreException) {
                    status = ((CoreException) e.getTargetException())
                            .getStatus();
                } else {
                    status = new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID,
                            e.getLocalizedMessage(), e);
                }
                ErrorDialog dlg = new ErrorDialog(shell,
                        Messages.ApplicationWorkbenchWindowAdvisor_errorDialog_title,
                        String.format(
                                Messages.ApplicationWorkbenchWindowAdvisor_continueExit_message,
                                e.getLocalizedMessage()),
                        status, 0) {
                    @Override
                    protected void createButtonsForButtonBar(Composite parent) {
                        createButton(parent,
                                IDialogConstants.YES_ID,
                                IDialogConstants.YES_LABEL,
                                false);
                        createButton(parent,
                                IDialogConstants.NO_ID,
                                IDialogConstants.NO_LABEL,
                                true);
                        createDetailsButton(parent);
                    }
                };

                if (dlg.open() == 1) {
                    return false;
                }
            } catch (InterruptedException e) {
                return false;

            } finally {
                /*
                 * Sid XPD-8302 - moved closeAllEditors to a finally clause to
                 * make sure it gets done. Startup doesn't do well if editors
                 * are left open because startup deletes the projects left
                 * behind by the previous session.
                 */
                if (window.getActivePage() != null) {
                    window.getActivePage().closeAllEditors(false);
                }
            }
        }

        persistWindowSize(shell.getSize());

        return super.preWindowShellClose();
    }

    /**
     * Persist the size of the RCP application window in the preference store.
     * 
     * @param size
     */
    private void persistWindowSize(Point size) {
        IPreferenceStore store = RCPActivator.getDefault().getPreferenceStore();
        if (store != null && size != null) {
            store.setValue(RCP_WIDTH, size.x);
            store.setValue(RCP_HEIGHT, size.y);
        }
    }

    /**
     * Get the last persisted size of the RCP application window.
     * 
     * @return
     */
    private Point getpersistedWindowSize() {
        int x = 0;
        int y = 0;

        IPreferenceStore store = RCPActivator.getDefault().getPreferenceStore();
        if (store != null) {
            x = store.getInt(RCP_WIDTH);
            y = store.getInt(RCP_HEIGHT);
        }

        return new Point(x > 0 ? x : DEFAULT_WIDTH, y > 0 ? y : DEFAULT_HEIGHT);
    }

    /**
     * SID XPD-8302 - Force hide of the standard menu bar (had started showing
     * Search and another option in menu bar in Eclipse 4.7)
     */
    public void initialiseWindowConfiguration() {
        getWindowConfigurer().setShowMenuBar(false);
        getWindowConfigurer().setShowCoolBar(false);

    }

}

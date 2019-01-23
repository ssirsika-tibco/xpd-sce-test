/* 
 ** 
 **  MODULE:             $RCSfile: PrepareSimulationUIStatusHandler.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-02-09 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.ui.runner;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.simulation.ui.PreferenceStoreKeys;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;
import com.tibco.xpd.xpdl2.Process;

/**
 * Handles all UI preparation tasks needed before starting simulation like
 * channgins perspective, showing views, and opening process editor.
 * 
 * 
 * @author jarciuch
 */
public class PrepareSimulationUIStatusHandler extends
        AbstractUISimulationStatusHandler {

    /** Simulation control view id */
    private static final String SIMULATION_CONTROL_VIEW_ID =
            "com.tibco.xpd.simulation.ui.views.SimulationControlView"; //$NON-NLS-1$

    /** simulation perspective id */
    private static final String SIMULATION_PERSPECTIVE_ID =
            "com.tibco.xpd.simulation.perspective"; //$NON-NLS-1$

    /**
     * @param source
     *            Map with params
     * @see com.tibco.xpd.simulation.ui.runner.AbstractUISimulationStatusHandler#handleUIStatus(org.eclipse.core.runtime.IStatus,
     *      java.lang.Object, org.eclipse.swt.widgets.Shell)
     */
    @Override
    public Object handleUIStatus(IStatus status, Object source, Shell shell) {

        Process process = null;
        ILaunchConfiguration launchConfig = null;
        // Read arguments from object
        if (source instanceof Map) {
            Map params = (Map) source;
            process = (Process) params.get(LaunchPlugin.WORKFLOW_PROCESS_KEY);
            launchConfig =
                    (ILaunchConfiguration) params
                            .get(LaunchPlugin.LAUNCH_CONFIGURATION_KEY);
        }

        if (process == null) {
            return Boolean.TRUE;
        }

        // switch perspective
        boolean runSimulation = switchToSimulationPerspective();
        if (!runSimulation) {
            return Boolean.FALSE;
        }

        // open simulation control view
        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        try {
            openSimulationControlView(page,
                    SIMULATION_CONTROL_VIEW_ID,
                    launchConfig);
        } catch (PartInitException e) {
            e.printStackTrace();
        }

        // open diagram with process
        openProcessEditor(page, process);

        return Boolean.TRUE;
    }

    /**
     * Open diagram editor for selected process.
     * 
     * @param page
     * @param process
     */
    private void openProcessEditor(IWorkbenchPage page, Process process) {
        IConfigurationElement facConfig =
                XpdResourcesUIActivator.getEditorFactoryConfigFor(process);
        if (facConfig != null) {
            String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            try {
                EditorInputFactory f =
                        (EditorInputFactory) facConfig
                                .createExecutableExtension("factory"); //$NON-NLS-1$
                IEditorInput input = f.getEditorInputFor(process);
                page.openEditor(input, editorID);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Open simulation control view and pass selection.
     * 
     * @param page
     * @param viewId
     * @throws PartInitException
     */
    private void openSimulationControlView(IWorkbenchPage page, String viewId,
            ILaunchConfiguration config) throws PartInitException {
        IViewPart part = page.showView(viewId);
        IViewSite viewSite = part.getViewSite();
        ISelectionProvider selectionProvider = viewSite.getSelectionProvider();
        IStructuredSelection structuredSelection =
                new StructuredSelection(new Object[] { config });
        selectionProvider.setSelection(structuredSelection);
    }

    /**
     * Switch to simulation perspective, Ask if switch to simulation
     * perspective.
     * 
     * @return true if
     */
    private boolean switchToSimulationPerspective() {
        boolean runSimulation = true;
        if (XpdResourcesPlugin.isRCP()) {
            return runSimulation;
        }
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchPage activePage =
                workbench.getActiveWorkbenchWindow().getActivePage();
        IPerspectiveDescriptor perspective = activePage.getPerspective();
        String currentPerspectiveId = perspective.getId();
        if (!currentPerspectiveId.equalsIgnoreCase(SIMULATION_PERSPECTIVE_ID)) {

            SimulationUIPlugin plugin = SimulationUIPlugin.getDefault();
            IPreferenceStore preferenceStore = plugin.getPreferenceStore();

            boolean switchPerspective =
                    preferenceStore
                            .getBoolean(PreferenceStoreKeys.SWITCH_PERSPECTIVE);
            // boolean
            // hasPreference=preferenceStore.contains(PreferenceStoreKeys.DONT_ASK_TO_SWITCH_PERSPECTIVE);
            boolean dontAskToSwitchPerspective =
                    preferenceStore
                            .getBoolean(PreferenceStoreKeys.DONT_ASK_TO_SWITCH_PERSPECTIVE);
            // askToSwitchPerspective=true; // this stay here long until there
            // is no way
            // to change this in preferences

            Shell activeShell = Display.getCurrent().getActiveShell();
            if (!dontAskToSwitchPerspective) {
                String title = Messages.PrepareSimulationUIStatusHandler_Title;
                String message =
                        Messages.PrepareSimulationUIStatusHandler_Message;
                String toggleMessage =
                        Messages.PrepareSimulationUIStatusHandler_Toggle;
                boolean toggleState = dontAskToSwitchPerspective;
                MessageDialogWithToggle dialog =
                        new MessageDialogWithToggle(activeShell, title, null, // accept
                                                                              // the
                                                                              // default
                                                                              // window
                                // icon
                                message, MessageDialog.QUESTION, new String[] {
                                        IDialogConstants.YES_LABEL,
                                        IDialogConstants.NO_LABEL,
                                        IDialogConstants.CANCEL_LABEL }, 0, // YES
                                                                            // is
                                // the
                                // default
                                toggleMessage, toggleState);

                dialog.open();

                dontAskToSwitchPerspective = dialog.getToggleState();
                preferenceStore
                        .setValue(PreferenceStoreKeys.DONT_ASK_TO_SWITCH_PERSPECTIVE,
                                dontAskToSwitchPerspective);
                plugin.savePluginPreferences();

                switch (dialog.getReturnCode()) {
                case IDialogConstants.CANCEL_ID:
                    // cancel running simulation
                    switchPerspective = false;
                    runSimulation = false;
                    break;
                case IDialogConstants.YES_ID:
                    switchPerspective = true;
                    runSimulation = true;
                    break;
                case IDialogConstants.NO_ID:
                    switchPerspective = false;
                    runSimulation = true;
                    break;
                }
            }
            if (runSimulation) {
                preferenceStore
                        .setValue(PreferenceStoreKeys.SWITCH_PERSPECTIVE,
                                switchPerspective);
                plugin.savePluginPreferences();

                if (switchPerspective) {
                    IWorkbenchWindow workbenchWindow =
                            workbench.getActiveWorkbenchWindow();
                    try {
                        workbench.showPerspective(SIMULATION_PERSPECTIVE_ID,
                                workbenchWindow);
                    } catch (WorkbenchException e) {
                        // oh well - nothing special happend
                        // there is no reason to do anything with missing
                        // simulation perspective
                    }
                }
            }
        }

        return runSimulation;
    }

}

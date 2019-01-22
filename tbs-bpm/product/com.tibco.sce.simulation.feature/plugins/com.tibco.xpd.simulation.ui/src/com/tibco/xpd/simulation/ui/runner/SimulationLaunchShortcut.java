/* 
 ** 
 **  MODULE:             $RCSfile: SimulationLaunchShortcut.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-02-08 $ 
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.launch.SimulationLaunchConfigurationConstants;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;
import com.tibco.xpd.xpdl2.Process;

/**
 * Simulation launch shortcut implementation class.
 * 
 * @author jarciuch
 */
public class SimulationLaunchShortcut implements ILaunchShortcut {

    /**
     * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.jface.viewers.ISelection,
     *      java.lang.String)
     */
    public void launch(ISelection selection, String mode) {
        if (selection instanceof IStructuredSelection) {
            Process launchProcess = null;
            IFile launchPackageFile = null;
            IProject launchProject = null;
            IStructuredSelection ss = (IStructuredSelection) selection;
            if (!ss.isEmpty()) {
                Object obj = ss.getFirstElement();
                if (obj instanceof Process) {
                    launchProcess = (Process) obj;

                    if (launchProcess != null) {
                        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(launchProcess);
                        launchPackageFile = (IFile) wc.getEclipseResources().get(0);
                        if (launchPackageFile != null) {
                            launchProject = launchPackageFile.getProject();

                            // delegate to launch
                            launch(launchProject, launchPackageFile,
                                    launchProcess, mode);
                        }
                    }

                } else if (obj instanceof IAdaptable) {
                    launchProcess = (Process) ((IAdaptable) obj)
                            .getAdapter(Process.class);

                    if (launchProcess != null) {
                        WorkingCopy wc = WorkingCopyUtil
                                .getWorkingCopyFor(launchProcess);

                        launchPackageFile = (IFile) wc.getEclipseResources().get(0);
                        if (launchPackageFile != null) {
                            launchProject = launchPackageFile.getProject();

                            // delegate to launch
                            launch(launchProject, launchPackageFile,
                                    launchProcess, mode);
                        }
                    }

                }
            }
        } else {
            showInncorectSelectionLaunchMessage();
        }
    }

    /**
     * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.ui.IEditorPart,
     *      java.lang.String)
     */
    public void launch(IEditorPart editor, String mode) {
        // TODO implement launching form shortcut for editor part.
        String title = Messages.SimulationLaunchShortcut_Title;
        String message = Messages.SimulationLaunchShortcut_Message;
        MessageDialog.openInformation(getShell(), title, message);
    }

    /**
     * Launches a configuration for the given type
     */
    protected void launch(IProject project, IFile packageFile,
            Process process, String mode) {
        ILaunchConfiguration config = findLaunchConfiguration(project,
                packageFile, process, mode);
        if (config != null) {
            DebugUITools.launch(config, mode);
        }
    }

    /**
     * Locate a configuration to relaunch for the given type. If one cannot be
     * found, create one.
     * 
     * @return a re-useable config or <code>null</code> if none
     */
    protected ILaunchConfiguration findLaunchConfiguration(IProject project,
            IFile packageFile, Process process, String mode) {
        ILaunchConfigurationType configType = getSimulationConfigType();
        List candidateConfigs = Collections.EMPTY_LIST;
        try {
            ILaunchConfiguration[] configs = DebugPlugin.getDefault()
                    .getLaunchManager().getLaunchConfigurations(configType);
            candidateConfigs = new ArrayList(configs.length);
            for (int i = 0; i < configs.length; i++) {
                ILaunchConfiguration config = configs[i];
                if (config
                        .getAttribute(
                                SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                                "").trim().equals(project.getName())) { //$NON-NLS-1$
                    if (config
                            .getAttribute(
                                    SimulationLaunchConfigurationConstants.ATTR_PACKAGE,
                                    "").trim().equals(packageFile.getProjectRelativePath().toString())) { //$NON-NLS-1$
                        if (config
                                .getAttribute(
                                        SimulationLaunchConfigurationConstants.ATTR_PROCESS_ID,
                                        "").trim().equals(process.getId())) { //$NON-NLS-1$
                            candidateConfigs.add(config);
                        }
                    }
                }
            }
        } catch (CoreException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Core exception while launch config!", e); //$NON-NLS-1$
        }

        int candidateCount = candidateConfigs.size();
        if (candidateCount < 1) {
            return createLaunchConfiguration(project, packageFile, process);
        } else if (candidateCount == 1) {
            return (ILaunchConfiguration) candidateConfigs.get(0);
        } else {
            // Prompt the user to choose a config. A null result means the user
            // cancelled the dialog, in which case this method returns null,
            // since cancelling the dialog should also cancel launching
            // anything.
            ILaunchConfiguration config = null;
            config = chooseConfiguration(candidateConfigs, mode);
            if (config != null) {
                return config;
            }
        }

        return null;
    }

    protected ILaunchManager getLaunchManager() {
        return DebugPlugin.getDefault().getLaunchManager();
    }

    /**
     * Returns the simulation launch config type
     */
    protected ILaunchConfigurationType getSimulationConfigType() {
        return getLaunchManager().getLaunchConfigurationType(
                SimulationLaunchConfigurationConstants.ID_SIMULATION);
    }

    /**
     * Show a selection dialog that allows the user to choose one of the
     * specified launch configurations. Return the chosen config, or
     * <code>null</code> if the user cancelled the dialog.
     */
    private ILaunchConfiguration chooseConfiguration(List configList,
            String mode) {
        IDebugModelPresentation labelProvider = DebugUITools
                .newDebugModelPresentation();
        ElementListSelectionDialog dialog = new ElementListSelectionDialog(
                getShell(), labelProvider);
        dialog.setElements(configList.toArray());
        dialog.setTitle(Messages.SimulationLaunchShortcut_DialogTitle);
        if (mode.equals(ILaunchManager.DEBUG_MODE)) {
            dialog.setMessage(Messages.SimulationLaunchShortcut_Debug);
        } else {
            dialog.setMessage(Messages.SimulationLaunchShortcut_Run);
        }
        dialog.setMultipleSelection(false);
        int result = dialog.open();
        labelProvider.dispose();
        if (result == Window.OK) {
            return (ILaunchConfiguration) dialog.getFirstResult();
        }
        return null;
    }

    /**
     * Create & return a new configuration based on the specified process.
     */
    protected ILaunchConfiguration createLaunchConfiguration(IProject project,
            IFile packageFile, Process process) {
        ILaunchConfiguration config = null;
        ILaunchConfigurationWorkingCopy wc = null;
        try {
            ILaunchConfigurationType configType = getSimulationConfigType();
            wc = configType.newInstance(null, getLaunchManager()
                    .generateUniqueLaunchConfigurationNameFrom(
                            packageFile.getName() + " - " + process.getName())); //$NON-NLS-1$
        } catch (CoreException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Core exception while config create!", e); //$NON-NLS-1$
        }
        wc.setAttribute(
                SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                project.getName());
        wc.setAttribute(SimulationLaunchConfigurationConstants.ATTR_PACKAGE,
                packageFile.getProjectRelativePath().toString());
        wc.setAttribute(SimulationLaunchConfigurationConstants.ATTR_PROCESS_ID,
                process.getId());
        wc.setAttribute(
                SimulationLaunchConfigurationConstants.ATTR_PROCESS_NAME,
                process.getName());
        try {
            config = wc.doSave();
        } catch (CoreException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Core exception while config save!", e); //$NON-NLS-1$
        }
        return config;
    }

    protected Shell getShell() {
        return SimulationUIPlugin.getShell();
    }

    private void showInncorectSelectionLaunchMessage() {
        MessageDialog.openError(getShell(), Messages.SimulationLaunchShortcut_ErrorTitle,
                Messages.SimulationLaunchShortcut_ErrorMessage);
    }
}

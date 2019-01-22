/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.utils;

import java.util.Set;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.xpdl2.Process;

/**
 * Utility class that provides simulation information.
 * 
 * @author mtorres
 * 
 */
public final class RCPSimulationUtils {

    public static String SIMULATION_DESTINATION_ID = "Simulation"; //$NON-NLS-1$

    /**
     * Returns if the simulation destination is enabled
     * 
     * @return
     */
    public static boolean isSimulationDestinationEnabled() {
        return RCPSimulationUtils
                .isSimulationDestinationEnabled(getContextProcess());
    }

    /**
     * Returns if the simulation destination is enabled for the current context
     * process
     * 
     * @param contextProcess
     * @return
     */
    public static boolean isSimulationDestinationEnabled(Process contextProcess) {
        if (contextProcess != null) {
            Set<String> enabledValidationDestinations =
                    DestinationUtil
                            .getEnabledGlobalDestinations(contextProcess);
            if (enabledValidationDestinations != null
                    && !enabledValidationDestinations.isEmpty()) {
                for (String validationDestination : enabledValidationDestinations) {
                    if (validationDestination != null
                            && validationDestination
                                    .equals(SIMULATION_DESTINATION_ID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the current Process element context from which to initialize
     * default settings, or <code>null</code> if none.
     * 
     * @return Process element context.
     */
    public static Process getContextProcess() {
        IWorkbenchPage page = null;
        IWorkbenchWindow window =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        return RCPSimulationUtils.getContextProcess(window);
    }

    /**
     * Returns the current Process element context from which to initialize
     * default settings, or <code>null</code> if none.
     * 
     * @return Process element context.
     */
    public static Process getContextProcess(IWorkbenchWindow window) {
        IWorkbenchPage page = null;
        if (window != null) {
            page = window.getActivePage();
        }
        if (page != null) {
            // get process from active editor
            IEditorPart part = page.getActiveEditor();
            if (part != null) {
                IEditorInput input = part.getEditorInput();
                if (input instanceof ProcessEditorInput) {
                    return ((ProcessEditorInput) input).getProcess();
                }
            }
        }
        return null;
    }

}

/* 
 ** 
 **  MODULE:             $RCSfile: SimulationProcessErrorStatusHandler.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-01-20 $ 
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsMessages;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;

public class SimulationValidationProcessErrorStatusHandler implements IStatusHandler {

    public Object handleStatus(IStatus status, Object source)
            throws CoreException {
        Integer errors = null;
        if (source instanceof Map) {
            errors = (Integer) ((Map) source)
                    .get(LaunchPlugin.NUMBER_OF_ERRORS_KEY);
        }
        final Integer errorNumber = errors;
        Display display = SimulationUIPlugin.getStandardDisplay();
        display.syncExec(new Runnable() {
            public void run() {
                Shell shell = SimulationUIPlugin.getShell();
                if (shell != null) {
                    String title = Messages.SimulationValidationProcessErrorStatusHandler_Title;
                    String message = MessageFormat
                            .format(
                                    Messages.SimulationValidationProcessErrorStatusHandler_Message,
                                    new String[] { errorNumber != null ? errorNumber
                                            .toString()
                                            : "" }); //$NON-NLS-1$
                    MessageDialog.openError(shell, title, message);
                }
            }
        });
        return new Boolean(false);
    }
}

/* 
** 
**  MODULE:             $RCSfile: LaunchConfigProblemStatusHandler.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2006-03-06 $ 
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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.ui.Messages;

public class LaunchConfigProblemStatusHandler extends
        AbstractUISimulationStatusHandler implements IStatusHandler {

    public Object handleUIStatus(final IStatus status, final Object source,
            final Shell shell) {  
        String message = null;
        ILaunchConfiguration launchConfig = null;
        // Read arguments from object
        if (source instanceof Map) {
            Map params = (Map) source;
            message = (String) params
                    .get(LaunchPlugin.PROBLEM_MESSAGE_KEY);
            launchConfig = (ILaunchConfiguration) params
                    .get(LaunchPlugin.LAUNCH_CONFIGURATION_KEY);
        }
        String title = Messages.TITLE_SIMULATION;
        MessageDialog.openWarning(shell, title, message);
        String groupId = "org.eclipse.debug.ui.launchGroup.run"; //$NON-NLS-1$
        DebugUITools.openLaunchConfigurationDialogOnGroup(shell, new StructuredSelection( new ILaunchConfiguration[] {launchConfig}), groupId);
        return Boolean.FALSE;
    }
}

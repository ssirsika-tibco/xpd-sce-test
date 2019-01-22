/* 
** 
**  MODULE:             $RCSfile: NoSimulationDestinationStatusHandler.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2006-02-01 $ 
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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.simulation.ui.Messages;

public class NoSimulationDestinationStatusHandler extends
        AbstractUISimulationStatusHandler {

    public Object handleUIStatus(final IStatus status, final Object source,
            final Shell shell) {  
        displayHanlerMessage(shell);
        return null;
    }
    public static void displayHanlerMessage(Shell shell){
        String title = Messages.TITLE_SIMULATION;
        String message =Messages.MSG_SIMULATION_NOT_DESTINATION_ENVIRONMENT;  
        MessageDialog.openWarning(shell, title, message);
    }

}

/* 
 ** 
 **  MODULE:             $RCSfile: AbstractUISimulationStatusHandler.java $ 
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.simulation.ui.SimulationUIPlugin;

/**
 * Abstract class for handling handlers which requires UI thread.
 *
 * @author jarciuch
 */
public abstract class AbstractUISimulationStatusHandler implements
        IStatusHandler {

    /**
     * @see org.eclipse.debug.core.IStatusHandler#handleStatus(org.eclipse.core.runtime.IStatus, java.lang.Object)
     */
    public Object handleStatus(final IStatus status, final Object source)
            throws CoreException {
        final Object[] retObject = new Object[1];
        Display display = SimulationUIPlugin.getStandardDisplay();
        display.syncExec(new Runnable() {
            public void run() {
                Shell shell = SimulationUIPlugin.getShell();
                if (shell != null) {
                    retObject[0] = handleUIStatus(status, source, shell);
                }
            }

        });
        return retObject[0];
    }

    /**
     * Handles status in UI thread.
     * 
     * @param status
     *            status to be handled
     * @param source source object from handler
     * @param shell shell which is used to display messages or dialogs.
     * @return Object carring return data specyfic to handler. 
     * 
     * @see IStatusHandler#handleStatus(org.eclipse.core.runtime.IStatus,
     *      java.lang.Object)
     */
    public abstract Object handleUIStatus(final IStatus status,
            final Object source, final Shell shell);

}

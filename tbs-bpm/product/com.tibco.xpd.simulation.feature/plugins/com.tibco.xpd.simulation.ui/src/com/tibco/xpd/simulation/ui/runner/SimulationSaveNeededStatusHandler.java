/* 
 ** 
 **  MODULE:             $RCSfile: SimulationSaveNeededStatusHandler.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-01-23 $ 
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;

public class SimulationSaveNeededStatusHandler implements IStatusHandler {

    @Override
    public Object handleStatus(IStatus status, Object source)
            throws CoreException {
        WorkingCopy paramWc = null;
        if (source instanceof Map) {
            paramWc =
                    (WorkingCopy) ((Map) source)
                            .get(LaunchPlugin.PACKAGE_WORKING_COPY_KEY);
        }
        final Object lock = this;
        final WorkingCopy wc = paramWc;
        final Boolean[] result = new Boolean[] { Boolean.FALSE };
        final Exception[] exception = new Exception[1];
        Display display = SimulationUIPlugin.getStandardDisplay();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    if (XpdResourcesPlugin.isRCP()) {
                        if (wc.isWorkingCopyDirty()) {
                            try {
                                wc.save();
                                result[0] = Boolean.TRUE;
                            } catch (IOException e) {
                                exception[0] = e;
                            }
                        }
                    } else {
                        Map dirtyResource = new HashMap();
                        dirtyResource.put(wc.getEclipseResources().get(0), wc);

                        Shell shell = SimulationUIPlugin.getShell();
                        if (shell != null) {
                            ListSelectionDialog dlg =
                                    new ListSelectionDialog(shell,
                                            dirtyResource,
                                            new SaveDialogContentProvider(),
                                            // new BpmContentLabelProvider(),
                                            new WorkbenchLabelProvider(),
                                            Messages.SimulationSaveNeededStatusHandler_Message);

                            dlg.setInitialSelections(dirtyResource.keySet()
                                    .toArray());
                            dlg.setTitle(Messages.SimulationSaveNeededStatusHandler_Title);
                            if (dlg.open() == ListSelectionDialog.OK) {
                                // If there is a selection in the dialog then
                                // save
                                // this
                                // process as it will be the only one in the
                                // selection
                                if (dlg.getResult().length > 0) {
                                    try {
                                        wc.save();
                                        result[0] = Boolean.TRUE;
                                    } catch (IOException e) {
                                        exception[0] = e;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    exception[0] = e;
                } finally {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }

            }
        };
        synchronized (lock) {
            display.asyncExec(r);
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (exception[0] != null) {
            throw new RuntimeException(exception[0]);
        }

        return result[0];
    }

}

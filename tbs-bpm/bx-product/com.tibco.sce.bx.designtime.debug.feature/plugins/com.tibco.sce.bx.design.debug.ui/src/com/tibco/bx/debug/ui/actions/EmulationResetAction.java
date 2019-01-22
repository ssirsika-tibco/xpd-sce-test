package com.tibco.bx.debug.ui.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.progress.UIJob;

import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.views.EmulationLauncherView;
import com.tibco.bx.debug.ui.views.internal.EmulationPane;
import com.tibco.bx.debug.ui.views.internal.EmulationRunResultInfoPane;

public class EmulationResetAction extends Action{

    private EmulationRunResultInfoPane resultInfoPane;
    
    private EmulationPane pane;
    
    EmulationLauncherView emulationLauncherView;
    
    public EmulationResetAction(EmulationRunResultInfoPane resultInfoPane,
            EmulationPane pane, EmulationLauncherView emulationLauncherView) {
        super();
        this.resultInfoPane = resultInfoPane;
        this.pane = pane;
        this.emulationLauncherView = emulationLauncherView;
        setText(Messages.getString("EmulationResetAction_Reset")); //$NON-NLS-1$
        setImageDescriptor(DebugUIActivator.getRegisteredImageDescriptor(DebugUIActivator.IMG_REFRESH));
    }

	@Override
    public void run() {
        UIJob uiJob = new UIJob("") { //$NON-NLS-1$
            @Override
            public IStatus runInUIThread(IProgressMonitor monitor) {
            	if(!pane.isDisposed() && !resultInfoPane.isDisposed()){
	                resultInfoPane.reset();
	                pane.reset();
	                emulationLauncherView.updateRunningStatus(""); //$NON-NLS-1$
	                emulationLauncherView.closeAllTabPanes();
	                emulationLauncherView.updateViewPartToolBar();
            	}
                return Status.OK_STATUS;
            }
        };
        uiJob.schedule();
    }

}

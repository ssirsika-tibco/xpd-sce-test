/**
 * 
 */
package com.tibco.bx.debug.bpm.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.bpm.ui.Messages;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;

public class ToggleBreakpointAction implements IActionDelegate {

    private BaseFlowNodeEditPart nodeEditPart;
    
    protected IBreakpoint breakpoint;
    /**
     * 
     */
    public ToggleBreakpointAction() {
        super();
    }

    public void run(IAction action) {
		
		try {
			if(breakpoint==null)
				return;
			if (breakpoint.isEnabled()) {
				breakpoint.setEnabled(false);
			}else{
				breakpoint.setEnabled(true);
			}
		} catch (CoreException e) {
			DebugBPMUIActivator.log(e);
		}
    }

    
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {
                Object obj = sel.getFirstElement();
                if (obj instanceof BaseFlowNodeEditPart) {
                	nodeEditPart=(BaseFlowNodeEditPart) obj;
                	NamedElement task = (NamedElement)nodeEditPart.getModel();
                	Process process = (com.tibco.xpd.xpdl2.Process)nodeEditPart.getParentProcess().getModel();

                	Resource resource = task.eResource();
                	String taskLocation = resource.getURIFragment(task);
            		String processId = process.getId();
            		breakpoint=getBreakpoint(processId,taskLocation);
            		if(breakpoint!=null){
            			action.setEnabled(true);
            			try {
							if (breakpoint.isEnabled()) {
								action.setText(Messages.getString("ToggleBreakpointAction.disableLabel")); //$NON-NLS-1$
								action.setImageDescriptor(getdisabledImageDescription());
							}else{
								action.setText(Messages.getString("ToggleBreakpointAction.enableLabel")); //$NON-NLS-1$
								action.setImageDescriptor(getEnabledImageDescription());
							}
						} catch (CoreException e) {
							DebugBPMUIActivator.log(e);
						}
            			return;
            		}
                }
            }
        }
        action.setText(Messages.getString("ToggleBreakpointAction.DisableLabel")); //$NON-NLS-1$
        action.setEnabled(false);
    }
    
    protected ImageDescriptor getEnabledImageDescription() {
		return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(DebugBPMUIActivator.IMG_BREAKPOINT_ACTION_ENABLED);
	}

	protected ImageDescriptor getdisabledImageDescription() {
		return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(DebugBPMUIActivator.IMG_BREAKPOINT_ACTION_DISABLED);
	}
    
	protected IBreakpoint getBreakpoint(String processId,String location) {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
		for(IBreakpoint bkpt : breakpoints){
			if (bkpt instanceof BxBreakpoint
					&& ((BxBreakpoint) bkpt).getProcessId().equals(processId)
					&& ((BxBreakpoint) bkpt).getLocation().equals(location)) {
				return bkpt;
			}
		}
		return null;
	}
}

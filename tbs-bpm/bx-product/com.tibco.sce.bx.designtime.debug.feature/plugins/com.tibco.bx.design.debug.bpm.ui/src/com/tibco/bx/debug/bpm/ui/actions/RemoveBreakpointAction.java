/**
 * 
 */
package com.tibco.bx.debug.bpm.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.bpm.ui.Messages;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;

public class RemoveBreakpointAction implements IActionDelegate {

    BxBreakpoint breakpoint;
    /**
     * 
     */
    public RemoveBreakpointAction() {
    }

    public void run(IAction action) {
		try {
			if (breakpoint!=null) {
				DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(breakpoint, true);
			}
		} catch (CoreException e) {
			DebugBPMUIActivator.log(e);
		}
    }
    
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            Object obj = sel.getFirstElement();
            if (obj instanceof BaseFlowNodeEditPart) {
            	BaseFlowNodeEditPart nodeEditPart=(BaseFlowNodeEditPart) obj;
                NamedElement task = (NamedElement)((BaseFlowNodeEditPart) obj).getModel();
                Process process = (com.tibco.xpd.xpdl2.Process)nodeEditPart.getParentProcess().getModel();
            	Resource resource = task.eResource();
            	String taskLocation = resource.getURIFragment(task);
            	breakpoint=getBreakpoint(process.getId(),taskLocation);
    			
    			action.setText(getActionText()); //$NON-NLS-1$
    			action.setImageDescriptor(
    					DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(DebugBPMUIActivator.IMG_BREAKPOINT_ACTION_REMOVE));
    		}
         }
    }

	private BxBreakpoint getBreakpoint(String processId,String location) {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
		for(IBreakpoint bkpt : breakpoints){
			if (bkpt instanceof BxBreakpoint
					&& ((BxBreakpoint) bkpt).getProcessId().equals(processId)
					&& ((BxBreakpoint) bkpt).getLocation().equals(location)) {
				return (BxBreakpoint)bkpt;
			}
		}
		return null;
	}
	
	private  String getActionText(){
		if(breakpoint!=null && breakpoint.getBreakWhen() == BreakWhen.BOTH){
					return Messages.getString("RemoveBreakpointAction.removeBothLabel"); //$NON-NLS-1$
				}else{
					return Messages.getString("RemoveBreakpointAction.removeLabel"); //$NON-NLS-1$
				}
			}
}

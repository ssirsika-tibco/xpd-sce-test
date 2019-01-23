package com.tibco.bx.debug.bpm.ui.actions;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyDialogAction;

import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;

public class BxBreakPointPropertiesAction implements IActionDelegate {
	protected BxBreakpoint fBreakpoint;

	public void run(IAction action) {
		if(fBreakpoint!=null){
			run();
		}
	}

	/**
	 * @see Action#run()
	 */
	private void run() {
		PropertyDialogAction action= 
			new PropertyDialogAction(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), new ISelectionProvider() {
				public void addSelectionChangedListener(ISelectionChangedListener listener) {
				}
				public ISelection getSelection() {
					return new StructuredSelection(fBreakpoint);
				}
				public void removeSelectionChangedListener(ISelectionChangedListener listener) {
				}
				public void setSelection(ISelection selection) {
				}
			});
		action.run();	
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			if (obj instanceof BaseFlowNodeEditPart) {
				BaseFlowNodeEditPart nodeEditPart = (BaseFlowNodeEditPart) obj;
				NamedElement task = (NamedElement) nodeEditPart.getModel();
				Process process = (com.tibco.xpd.xpdl2.Process) nodeEditPart
						.getParentProcess().getModel();

				Resource resource = task.eResource();
				String taskLocation = resource.getURIFragment(task);
				String processId = process.getId();
				fBreakpoint = getBreakpoint(processId, taskLocation);
				action.setImageDescriptor(getPropertyImageDescriptor());
			}
		}
	}

	protected ImageDescriptor getPropertyImageDescriptor() {
		return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(DebugBPMUIActivator.IMG_BREAKPOINT_ACTION_PROP);
	}
	
	private BxBreakpoint getBreakpoint(String processId, String location) {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault()
				.getBreakpointManager().getBreakpoints();
		for (IBreakpoint bkpt : breakpoints) {
			if (bkpt instanceof BxBreakpoint
					&& ((BxBreakpoint) bkpt).getProcessId().equals(processId)
					&& ((BxBreakpoint) bkpt).getLocation().equals(location)) {
				return (BxBreakpoint) bkpt;
			}
		}
		return null;
	}
}

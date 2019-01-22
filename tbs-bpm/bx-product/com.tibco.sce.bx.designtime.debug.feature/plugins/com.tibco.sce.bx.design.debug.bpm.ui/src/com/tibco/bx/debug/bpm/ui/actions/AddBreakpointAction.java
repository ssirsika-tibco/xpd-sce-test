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
import com.tibco.bx.debug.bpm.core.DebugBPMActivator;
import com.tibco.bx.debug.bpm.core.util.BPMProcessUtil;
import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.bpm.ui.Messages;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;

public abstract class AddBreakpointAction implements IActionDelegate {

	private BaseFlowNodeEditPart nodeEditPart;

	protected BreakWhen breakWhen;

	BxBreakpoint breakpoint;
	boolean isEnabled;
	IFile file;
	Process process;
	String taskLocation;
	String name;
	String modelType;

	/**
     * 
     */
	public AddBreakpointAction() {
	}

	public void run(IAction action) {

		String taskName = ((name == null || name.trim().equals("")) ? getLabelByType() : name); //$NON-NLS-1$
		try {
			if (breakpoint != null) {
				DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(breakpoint, true);
			}
			BxBreakpoint bxBreakpoint = new BxBreakpoint(file, process.getId(), taskLocation, modelType, breakWhen,
					isEnabled, process.getName() + " [" + taskName + "] - " + getBreakWhenLabel(breakWhen)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bxBreakpoint);
		} catch (CoreException e) {
			DebugBPMUIActivator.log(e);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			Object obj = sel.getFirstElement();
			if (obj instanceof BaseFlowNodeEditPart) {
				nodeEditPart = (BaseFlowNodeEditPart) obj;
				NamedElement task = (NamedElement) ((BaseFlowNodeEditPart) obj).getModel();
				process = (com.tibco.xpd.xpdl2.Process) nodeEditPart.getParentProcess().getModel();
				modelType = BPMProcessUtil.isPageflowProcess(process) ? DebugBPMActivator.PAGEFLOW_MODEL_TYPE
						: DebugBPMActivator.BPM_MODEL_TYPE;
				Resource resource = task.eResource();
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(
						new Path(resource.getURI().toPlatformString(true)));
				taskLocation = resource.getURIFragment(task);
				name = task.getName();
				breakpoint = getBreakpoint(process.getId(), taskLocation);
				try {
					isEnabled = breakpoint == null ? true : breakpoint.isEnabled();
				} catch (CoreException e) {
					DebugBPMUIActivator.log(e);
				}
				action.setText(getActionText());
				action.setImageDescriptor(getActionImageDescriptor());
				action.setEnabled(true);
				return;
			}
		}
		action.setEnabled(false);
	}

	private ImageDescriptor getActionImageDescriptor() {
		if (modelType.equals(DebugBPMActivator.BPM_MODEL_TYPE)) {
			switch (breakWhen) {
			case ENTRY:
				return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(
						isEnabled ? DebugBPMUIActivator.IMG_BREAKPOINT_BEFORE
								: DebugBPMUIActivator.IMG_BREAKPOINT_BEFORE_DISABLED);
			case RETURN:
				return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(
						isEnabled ? DebugBPMUIActivator.IMG_BREAKPOINT_AFTER
								: DebugBPMUIActivator.IMG_BREAKPOINT_AFTER_DISABLED);
			default:
				return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(
						isEnabled ? DebugBPMUIActivator.IMG_BREAKPOINT_ALL
								: DebugBPMUIActivator.IMG_BREAKPOINT_ALL_DISABLED);
			}
		} else if (modelType.equals(DebugBPMActivator.PAGEFLOW_MODEL_TYPE)) {
			switch (breakWhen) {
			case ENTRY:
				return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(
						isEnabled ? DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_BEFORE
								: DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_BEFORE_DISABLED);
			case RETURN:
				return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(
						isEnabled ? DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_AFTER
								: DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_AFTER_DISABLED);
			default:
				return DebugBPMUIActivator.getDefault().getImageRegistry().getDescriptor(
						isEnabled ? DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_ALL
								: DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_ALL_DISABLED);
			}
		}
		return null;

	}

	private String getActionText() {
		switch (breakWhen) {
			case ENTRY:
				return breakpoint == null ? Messages.getString("AddBreakpointAction.addBeforeLabel") : Messages.getString("AddBreakpointAction.removeAfterLabel"); //$NON-NLS-1$ //$NON-NLS-2$
			case RETURN:
				return breakpoint == null ? Messages.getString("AddBreakpointAction.addAfterLabel") : Messages.getString("AddBreakpointAction.removeBeforeLabel"); //$NON-NLS-1$ //$NON-NLS-2$
			default:// both
			{
				if (breakpoint == null) {
					return Messages.getString("AddBreakpointAction.addBothLabel"); //$NON-NLS-1$
				} else if (breakpoint.getBreakWhen() == BreakWhen.ENTRY) {
					return Messages.getString("AddBreakpointAction.addAfterLabel"); //$NON-NLS-1$
				} else {
					return Messages.getString("AddBreakpointAction.addBeforeLabel"); //$NON-NLS-1$
				}
			}
		}
	}

	private BxBreakpoint getBreakpoint(String processId, String location) {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
		for (IBreakpoint bkpt : breakpoints) {
			if (bkpt instanceof BxBreakpoint && ((BxBreakpoint) bkpt).getProcessId().equals(processId)
					&& ((BxBreakpoint) bkpt).getLocation().equals(location)) {
				return (BxBreakpoint) bkpt;
			}
		}
		return null;
	}

	private static String getBreakWhenLabel(BreakWhen breakWhen) {
		switch (breakWhen) {
		case ENTRY:
			return Messages.getString("AddBreakpointAction.beforeLabel"); //$NON-NLS-1$
		case RETURN:
			return Messages.getString("AddBreakpointAction.afterLabel"); //$NON-NLS-1$
		default:
			return Messages.getString("AddBreakpointAction.bothLabel"); //$NON-NLS-1$
		}
	}

	private String getLabelByType() {
		if (nodeEditPart instanceof TaskEditPart) {
			return Messages.getString("AddBreakpointAction.taskLabel"); //$NON-NLS-1$
		} else if (nodeEditPart instanceof EventEditPart) {
			return Messages.getString("AddBreakpointAction.eventLabel"); //$NON-NLS-1$
		} else if (nodeEditPart instanceof GatewayEditPart) {
			return Messages.getString("AddBreakpointAction.gatewayLabel"); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}
}

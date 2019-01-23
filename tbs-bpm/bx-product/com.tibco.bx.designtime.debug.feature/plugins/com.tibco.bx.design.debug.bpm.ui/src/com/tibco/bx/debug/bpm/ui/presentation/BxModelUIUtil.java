package com.tibco.bx.debug.bpm.ui.presentation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.bpm.core.DebugBPMActivator;
import com.tibco.bx.debug.bpm.core.models.IBxNodeType;
import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.util.DecoratorUtil;

public class BxModelUIUtil {

	public static Image getImageForStackframe(IBxStackFrame stackFrame) {
		Image image = null;
		String type = stackFrame.getElementType();
		if (type == null) {
			return null;
		} else if (type.equals(IBxNodeType.EVENT)) {
			image = getEventImageForStackframe(stackFrame);
		} else if (type.equals(IBxNodeType.TASK)) {
			image = getTaskImageForStackframe(stackFrame);
		} else if (type.equals(IBxNodeType.GATEWAY)) {
			image = getGatewayImageForStackFrame(stackFrame);
		} else if (type.equals(IBxNodeType.EMBEDDED_SUBPROCESS)) {
			image = getSubProcessImageForStackframe(stackFrame);
		} else if (type.equals(IBxNodeType.INDENPENDENT_SUBPROCESS)) {
			image = getIndependentSubImageForStackFrame(stackFrame);
		} else if (type.equals(IBxNodeType.TRACK)) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_TRACK);
		}
		return image;
	}
	
	private static Image getGatewayImageForStackFrame(IBxStackFrame stackFrame){
		Image image = null;
		String modelType = ((IBxDebugTarget) stackFrame.getDebugTarget()).getModelType();
		if(modelType.equals(DebugBPMActivator.BPM_MODEL_TYPE)){
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_GATEWAY_BPM);
		}else if(modelType.equals(DebugBPMActivator.PAGEFLOW_MODEL_TYPE)){
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_GATEWAY_PAGEFLOW);
		}
		return image;
	}

	private static Image getIndependentSubImageForStackFrame(IBxStackFrame stackFrame) {
		Image image = null;
		String type = ((ProcessVisibleNode) stackFrame.getProcessElement()).getState();
		String modelType = ((IBxDebugTarget) stackFrame.getDebugTarget()).getModelType();
		if (type.equals(ProcessVisibleNode.State.NOT_STARTED) || type.equals(ProcessVisibleNode.State.SKIPPED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_INDEPENDENT_GRAY);
		} else if (type.equals(ProcessVisibleNode.State.ACTIVE) || type.equals(ProcessVisibleNode.State.STARTING)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_INDEPENDENT_GREEN);
		} else if (type.equals(ProcessVisibleNode.State.WAITING) || type.equals(ProcessVisibleNode.State.ARMED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_INDEPENDENT_GREEN);
		} else if (type.equals(ProcessVisibleNode.State.INSTALLED) || type.contains("ing")) { //$NON-NLS-1$
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_INDEPENDENT_BLUE);
		} else if (type.equals(ProcessVisibleNode.State.FAULTED) || type.equals(ProcessVisibleNode.State.CANCELLED)
				|| type.equals(ProcessVisibleNode.State.COMPENSATED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_INDEPENDENT_RED);
		}
		if(modelType.equals(DebugBPMActivator.BPM_MODEL_TYPE)){
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_INDEPENDENT_BPM);
		}else if(modelType.equals(DebugBPMActivator.PAGEFLOW_MODEL_TYPE)){
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_INDEPENDENT_PAGEFLOW);
		}
		return image;
	}

	public static Image getImageForThread(IBxThread bxThread) {
		String modelType = ((IBxDebugTarget) bxThread.getDebugTarget()).getModelType();
		return DebugUIActivator.getRegisteredImage(modelType);
	}

	private static Image getEventImageForStackframe(IBxStackFrame stackFrame) {
		Image image = null;
		String type = ((ProcessVisibleNode) stackFrame.getProcessElement()).getState();
		String modelType = ((IBxDebugTarget) stackFrame.getDebugTarget()).getModelType();
		if (type.equals(ProcessVisibleNode.State.NOT_STARTED) || type.equals(ProcessVisibleNode.State.SKIPPED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_EVENT_GRAY);
		} else if (type.equals(ProcessVisibleNode.State.ACTIVE) || type.equals(ProcessVisibleNode.State.STARTING)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_EVENT_GREEN);
		} else if (type.equals(ProcessVisibleNode.State.WAITING) || type.equals(ProcessVisibleNode.State.ARMED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_EVENT_GREEN);
		} else if (type.equals(ProcessVisibleNode.State.INSTALLED) || type.contains("ing")) { //$NON-NLS-1$ 
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_EVENT_BLUE);
		} else if (type.equals(ProcessVisibleNode.State.FAULTED) || type.equals(ProcessVisibleNode.State.CANCELLED)
				|| type.equals(ProcessVisibleNode.State.COMPENSATED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_EVENT_RED);
		}
		if (modelType.equals(DebugBPMActivator.BPM_MODEL_TYPE)) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_EVENT_BPM);
		} else if (modelType.equals(DebugBPMActivator.PAGEFLOW_MODEL_TYPE)) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_EVENT_PAGEFLOW);
		}
		return image;
	}

	private static Image getSubProcessImageForStackframe(IBxStackFrame stackFrame) {
		Image image = null;
		String type = ((ProcessVisibleNode) stackFrame.getProcessElement()).getState();
		String modelType = ((IBxDebugTarget) stackFrame.getDebugTarget()).getModelType();
		if (type.equals(ProcessVisibleNode.State.NOT_STARTED) || type.equals(ProcessVisibleNode.State.SKIPPED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_SUBPROCESS_GRAY);
		} else if (type.equals(ProcessVisibleNode.State.ACTIVE) || type.equals(ProcessVisibleNode.State.STARTING)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_SUBPROCESS_GREEN);
		} else if (type.equals(ProcessVisibleNode.State.WAITING) || type.equals(ProcessVisibleNode.State.ARMED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_SUBPROCESS_GREEN);
		} else if (type.equals(ProcessVisibleNode.State.INSTALLED) || type.contains("ing")) { //$NON-NLS-1$
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_SUBPROCESS_BLUE);
		} else if (type.equals(ProcessVisibleNode.State.FAULTED) || type.equals(ProcessVisibleNode.State.CANCELLED)
				|| type.equals(ProcessVisibleNode.State.COMPENSATED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_SUBPROCESS_RED);
		}
		if (modelType.equals(DebugBPMActivator.BPM_MODEL_TYPE)) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_SUBPROCESS_BPM);
		} else if (modelType.equals(DebugBPMActivator.PAGEFLOW_MODEL_TYPE)) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_SUBPROCESS_PAGEFLOW);
		}
		return image;
	}

	private static Image getTaskImageForStackframe(IBxStackFrame stackFrame) {
		Image image = null;
		String type = ((ProcessVisibleNode) stackFrame.getProcessElement()).getState();
		String modelType = ((IBxDebugTarget) stackFrame.getDebugTarget()).getModelType();
		if (type.equals(ProcessVisibleNode.State.NOT_STARTED) || type.equals(ProcessVisibleNode.State.SKIPPED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_TASK_GRAY);
		} else if (type.equals(ProcessVisibleNode.State.ACTIVE) || type.equals(ProcessVisibleNode.State.STARTING)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_TASK_GREEN);
		} else if (type.equals(ProcessVisibleNode.State.WAITING) || type.equals(ProcessVisibleNode.State.ARMED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_TASK_GREEN);
		} else if (type.equals(ProcessVisibleNode.State.INSTALLED) || type.contains("ing")) { //$NON-NLS-1$
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_TASK_BLUE);
		} else if (type.equals(ProcessVisibleNode.State.FAULTED) || type.equals(ProcessVisibleNode.State.CANCELLED)
				|| type.equals(ProcessVisibleNode.State.COMPENSATED)) {
			return DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_TASK_RED);
		}
		if (modelType.equals(DebugBPMActivator.BPM_MODEL_TYPE)) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_TASK_BPM);
		} else if (modelType.equals(DebugBPMActivator.PAGEFLOW_MODEL_TYPE)) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_TASK_PAGEFLOW);
		}
		return image;
	}

	public static Image getImage(Object element) {
		Image image = null;
		if (element instanceof IBxDebugTarget) {
			image = DebugUIActivator.getRegisteredImage(((IBxDebugTarget) element).getModelType());
		} else if (element instanceof IBxThread) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_BPM_PROCESS);
		} else if (element instanceof IBxStackFrame) {
			image = BxModelUIUtil.getImageForStackframe((IBxStackFrame) element);
			image = DecoratorUtil.addCurrentDecorator((IBxStackFrame) element, image);
		} else if (element instanceof BxBreakpoint) {
			image = BxModelUIUtil.getImageForBreakpoint((BxBreakpoint) element);
		}
		return image;
	}

	public static Image getImageForBreakpoint(BxBreakpoint breakpoint) {
		Image image = null;
		boolean isEnabled = false;
		BreakWhen breakWhen = null;
		String modelType = null;
		try {
			isEnabled = breakpoint.isEnabled();
			breakWhen = breakpoint.getBreakWhen();
			modelType = breakpoint.getModelType();
			if (modelType.equals(DebugBPMActivator.BPM_MODEL_TYPE)) {
				switch (breakWhen) {
				case ENTRY:
					image = DebugBPMUIActivator.getDefault().getImageRegistry().get(
							isEnabled ? DebugBPMUIActivator.IMG_BREAKPOINT_BEFORE
									: DebugBPMUIActivator.IMG_BREAKPOINT_BEFORE_DISABLED);
					break;
				case RETURN:
					image = DebugBPMUIActivator.getDefault().getImageRegistry().get(
							isEnabled ? DebugBPMUIActivator.IMG_BREAKPOINT_AFTER
									: DebugBPMUIActivator.IMG_BREAKPOINT_AFTER_DISABLED);
					break;
				case BOTH:
					image = DebugBPMUIActivator.getRegisteredImage(isEnabled ? DebugBPMUIActivator.IMG_BREAKPOINT_ALL
							: DebugBPMUIActivator.IMG_BREAKPOINT_ALL_DISABLED);
					break;
				}
			} else if (modelType.equals(DebugBPMActivator.PAGEFLOW_MODEL_TYPE)) {
				switch (breakWhen) {
				case ENTRY:
					image = DebugBPMUIActivator.getDefault().getImageRegistry().get(
							isEnabled ? DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_BEFORE
									: DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_BEFORE_DISABLED);
					break;
				case RETURN:
					image = DebugBPMUIActivator.getDefault().getImageRegistry().get(
							isEnabled ? DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_AFTER
									: DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_AFTER_DISABLED);
					break;
				case BOTH:
					image = DebugBPMUIActivator
							.getRegisteredImage(isEnabled ? DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_ALL
									: DebugBPMUIActivator.IMG_PAGEFLOW_BREAKPOINT_ALL_DISABLED);
					break;
				}
			}

		} catch (CoreException e) {
			DebugBPMUIActivator.log(e);
		}
		return image;
	}

}

package com.tibco.bx.debug.bpm.ui.presentation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.util.DecoratorUtil;

public class BxModelLabelProvider implements ILabelProvider {

	public BxModelLabelProvider() {
	}

	@Override
	public Image getImage(Object element) {
		Image image = null;
		if (element instanceof IBxThread) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_BPM_PROCESS);
		} else if (element instanceof IBxStackFrame) {
			image = BxModelUIUtil.getImageForStackframe((IBxStackFrame) element);
			image = DecoratorUtil.addCurrentDecorator((IBxStackFrame)element, image);
		}
		return image;
	}

	@Override
	public String getText(Object element) {
		String text = ""; //$NON-NLS-1$
		try {
			if (element instanceof IBxThread) {
				text = getThreadText((IBxThread) element);
			} else if (element instanceof IBxStackFrame) {
				text = getStackFrameText((IBxStackFrame) element);
			}
		} catch (CoreException e) {
			DebugUIActivator.log(e);
		}
		return text;
	}

	private String getStackFrameText(IBxStackFrame frame) throws CoreException {
		String frameName;
		ProcessVisibleNode node = (ProcessVisibleNode) frame.getProcessElement();
		String state = node.getState();
		state = state.substring(0, 1).toUpperCase() + state.substring(1, state.length());
		if (node.getState().equals(ProcessVisibleNode.State.NULL)) {
			frameName = frame.getName();
		} else {
			frameName = frame.getName() + " [" + node.getInstanceId() //$NON-NLS-1$
					+ "] - (" //$NON-NLS-1$
					+ state + ")"; //$NON-NLS-1$
		}
		return frameName;
	}

	private String getThreadText(IBxThread thread) throws CoreException {
		ProcessInstance instance = (ProcessInstance) thread.getProcessElement();
		String state = instance.getState();
		state = state.substring(0, 1).toUpperCase() + state.substring(1, state.length());
		return thread.getName() + " [" //$NON-NLS-1$
				+ instance.getInstanceId() + "] - (" //$NON-NLS-1$
				+ state + ")"; //$NON-NLS-1$
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
	}

}

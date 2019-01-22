package com.tibco.bx.debug.ui.presentation;

import java.beans.PropertyChangeListener;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.core.models.variable.BxComplexValue;
import com.tibco.bx.debug.core.models.variable.BxFieldVariable;
import com.tibco.bx.debug.core.models.variable.BxLocalVariable;
import com.tibco.bx.debug.core.models.variable.BxPrimitiveValue;
import com.tibco.bx.debug.core.models.variable.BxSubVariable;
import com.tibco.bx.debug.core.models.variable.BxVariable;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;

public abstract class AbstractModelPresentation  extends LabelProvider implements IDebugModelPresentation, PropertyChangeListener{

	private static Color redColor;
	private static Color greenColor;
	private static Color grayColor;
	private static Color blueColor;

	public AbstractModelPresentation() {
		redColor = getColor(255, 0, 0);
		greenColor = getColor(0, 255, 0);
		grayColor = getColor(192, 192, 192);
		blueColor = getColor(0, 0, 255);
		DebugCoreActivator.addCurrentStackFrameChangeListener(this);
	}

	protected Color getRedColor() {
		if(redColor.isDisposed()){
			redColor = getColor(255, 0, 0);
		}
		return redColor;
	}

	protected Color getGreenColor() {
		if(greenColor.isDisposed()){
			greenColor = getColor(0, 255, 0);
		}
		return greenColor;
	}

	protected Color getGrayColor() {
		if(grayColor.isDisposed()){
			grayColor = getColor(192, 192, 192);
		}
		return grayColor;
	}

	protected Color getBlueColor() {
		if(blueColor.isDisposed()){
			blueColor = getColor(0,0, 255);
		}
		return blueColor;
	}

	public Color getColor(int red, int green, int blue) {
		RGB rgb = new RGB(red, green, blue);
		Color result = null;
		if (PlatformUI.isWorkbenchRunning()) {
			result = new Color(PlatformUI.getWorkbench().getDisplay(), rgb);
		} else {
			result = new Color(Display.getDefault(), rgb);
		}

		return result;
	}

	@Override
	public void dispose() {
		redColor.dispose();
		greenColor.dispose();
		grayColor.dispose();
		blueColor.dispose();
		DebugCoreActivator.removeCurrentStackFrameChangeListener(this);
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.IDebugModelPresentation#setAttribute(java.lang.String
	 * , java.lang.Object)
	 */
	public void setAttribute(String attribute, Object value) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		try {
			if (element instanceof BxBreakpoint) {
				return getBreakpointText((BxBreakpoint) element);
			} else if (element instanceof IBxStackFrame) {
				return getStackFrameText((IBxStackFrame) element);
			} else if (element instanceof IBxThread) {
				return getThreadText((IBxThread) element);
			} else if (element instanceof IBxDebugTarget) {
				return getTargetText((IBxDebugTarget) element);
			} 
		} catch (CoreException e) {
			DebugUIActivator.log(e);
		}
		return "";//$NON-NLS-1$
	}

	/**
	 * Returns a label for the given bxBreakpoint.
	 * 
	 * @param bxBreakpoint
	 * @return a label for the given bxBreakpoint
	 */
	private String getBreakpointText(BxBreakpoint bxBreakpoint)
			throws CoreException {
		return (String) bxBreakpoint.getMarker().getAttribute(IMarker.MESSAGE);
	}

	/**
	 * Returns a label for the given stack frame
	 * 
	 * @param frame
	 *            a stack frame
	 * @return a label for the given stack frame
	 */
	private String getStackFrameText(IBxStackFrame frame)
			throws CoreException {
		String frameName;
		ProcessVisibleNode node = (ProcessVisibleNode)frame.getProcessElement();
		String state = node.getState();
		state = state.substring(0, 1).toUpperCase() + state.substring(1, state.length());
		if(node.getState().equals(ProcessVisibleNode.State.NULL)){
			frameName = frame.getName();
		}else{
			frameName = frame.getName() + " [" + node.getInstanceId() //$NON-NLS-1$
					+ "] - (" //$NON-NLS-1$
					+ state
					+ ")"; //$NON-NLS-1$
		}
		return frameName;
	}

	/**
	 * Returns a label for the given debug target
	 * 
	 * @param target
	 *            debug target
	 * @return a label for the given debug target
	 */
	private String getTargetText(IBxDebugTarget target) throws CoreException {
		if (target.isDisconnected() || target.isTerminated()) {
			return Messages.getString("AbstractModelPresentation.terminatedLabel") + target.getName(); //$NON-NLS-1$
		} else {
			return Messages.getString("AbstractModelPresentation.launchedState") + target.getName(); //$NON-NLS-1$
		}
	}

	@Override
	public Image getImage(Object element) {
		Image image = null;
		try {
			if (element instanceof BxVariable) {
				if (element instanceof BxLocalVariable) {
					image = DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_VARIABLE_LOCAL);
				} else if (element instanceof BxSubVariable) {
					BxSubVariable var = (BxSubVariable) element;
					if (var.getValue() instanceof BxPrimitiveValue) {
						image = DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_VARIABLE_SUB);
					} else if (var.getValue() instanceof BxComplexValue) {
						image = DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_VARIABLE_SUB_COMPLEX);
					}
				} else if (element instanceof BxFieldVariable) {
					image = DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_VARIABLE_GLOBAL);
				}
			}
		} catch (DebugException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * Returns a label for the given thread
	 * 
	 * @param thread
	 *            a thread
	 * @return a label for the given thread
	 */
	private String getThreadText(IBxThread thread) throws CoreException {
		ProcessInstance instance = (ProcessInstance)thread.getProcessElement();
		String state = instance.getState();
		state = state.substring(0, 1).toUpperCase() + state.substring(1, state.length());
		return thread.getName() + " [" //$NON-NLS-1$
				+ instance.getInstanceId() + "] - (" //$NON-NLS-1$
				+ state + ")"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse
	 * .debug.core.model.IValue, org.eclipse.debug.ui.IValueDetailListener)
	 */
	public void computeDetail(IValue value, IValueDetailListener listener) {
		String detail = ""; //$NON-NLS-1$
		try {
			detail = value.getValueString();
		} catch (DebugException e) {
		}
		listener.detailComputed(value, detail);
	}

	/**
	 * Returns the standard display to be used. The method first checks, if the
	 * thread calling this method has an associated display. If so, this display
	 * is returned. Otherwise the method returns the default display.
	 * 
	 */
	public static Display getStandardDisplay() {
		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}
		return display;
	}

}

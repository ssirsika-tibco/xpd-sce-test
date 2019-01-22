package com.tibco.bx.debug.ui.util;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.bx.debug.core.models.BxDebugTarget;

public class DebugContextUtil {

	public static BxDebugTarget getTargetFromDebugContextEvent(DebugContextEvent event) {
		BxDebugTarget target = null;
		ISelection selection = event.getContext();
		if (selection instanceof IStructuredSelection) {
			Object source = ((IStructuredSelection) selection).getFirstElement();
			if (source != null) {
				if (source instanceof ILaunch) {
					target = getFirstTibcoProcessDebugTarget((ILaunch) source);
				} else if (source instanceof IDebugElement) {
					IDebugTarget debugTarget = ((IDebugElement) source).getDebugTarget();
					if (debugTarget instanceof BxDebugTarget) {
						target = (BxDebugTarget) debugTarget;
					} else {
						target = getFirstTibcoProcessDebugTarget(debugTarget.getLaunch());
					}
				}
			}
		}
		return target;
	}

	public static BxDebugTarget getFirstTibcoProcessDebugTarget(ILaunch launch) {
		IDebugTarget[] targets = launch.getDebugTargets();
		for (IDebugTarget debugTarget : targets) {
			if (debugTarget instanceof BxDebugTarget && !debugTarget.isDisconnected() && !debugTarget.isTerminated()) {
				return (BxDebugTarget) debugTarget;
			}
		}
		return null;
	}

	public static boolean hasTarget(DebugContextEvent event) {
		boolean hasTarget = true;
		if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0) {
			ISelection selection = event.getContext();
			if (selection.isEmpty()) {
				hasTarget = false;
			}
		} else {
			hasTarget = false;
		}
		return hasTarget;
	}

}

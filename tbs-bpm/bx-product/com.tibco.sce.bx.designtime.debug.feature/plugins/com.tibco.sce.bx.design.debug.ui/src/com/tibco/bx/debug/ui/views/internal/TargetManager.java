package com.tibco.bx.debug.ui.views.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;

import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.ui.util.DebugContextUtil;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;

public class TargetManager implements IDebugContextListener {

	private transient ListenerList listenerList = new ListenerList();

	public static TargetManager instance = new TargetManager();

	private BxDebugTarget currentTarget;

	public static TargetManager getDefault() {
		return instance;
	}

	@Override
	public void debugContextChanged(DebugContextEvent event) {
		boolean isChanged = false;
		if (DebugContextUtil.hasTarget(event)) {
			BxDebugTarget debugTarget = DebugContextUtil.getTargetFromDebugContextEvent(event);
			EmulationData emulationData = EmulationUIUtil.getEmulationData(debugTarget);
			EmulationCacheManager.getDefault().setCurrentEmulationData(emulationData);
			if (debugTarget != null && (!debugTarget.isDisconnected() || !debugTarget.isTerminated())) {
				if (debugTarget != currentTarget) {
					currentTarget = debugTarget;
					isChanged = true;
				}
			} else {
				currentTarget = null;
				isChanged = true;
			}
			if (isChanged) {
				fireDebugTargetChange(currentTarget);
			}
		}
	}

	private void fireDebugTargetChange(BxDebugTarget event) {
		for (int i = 0; i < listenerList.getListeners().length; i++) {
			IDebugTargetChangedListener listener = (IDebugTargetChangedListener) listenerList.getListeners()[i];
			listener.selectionChanged(event);
		}
	}

	public BxDebugTarget getCurrentTarget() {
		return currentTarget;
	}

	public IBxProcessListing getProcessListing() {
		if (currentTarget != null) {
			return (IBxProcessListing) currentTarget.getAdapter(IBxProcessListing.class);
		}
		return null;
	}

	public void addDebugTargetChangedListener(IDebugTargetChangedListener listener) {
		listenerList.add(listener);
	}

	public void removeDebugTargetChangedListener(IDebugTargetChangedListener listener) {
		listenerList.remove(listener);
	}

	public void setCurrentTarget(BxDebugTarget target) {
		if (currentTarget != target) {
			currentTarget = target;
			fireDebugTargetChange(currentTarget);
		}
	}

	public List<IBxDebugTarget> getDebugTargets() {
		List<IBxDebugTarget> bxDebugTargets = new ArrayList<IBxDebugTarget>();
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
		for (ILaunch launch : launches) {
			IDebugTarget[] targets = launch.getDebugTargets();
			for (IDebugTarget target : targets) {
				if (target instanceof IBxDebugTarget) {
					bxDebugTargets.add((IBxDebugTarget) target);
				}
			}
		}
		return bxDebugTargets;
	}

}

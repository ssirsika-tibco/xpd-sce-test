package com.tibco.bx.emulation.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.bx.emulation.ui.views.IEmulationView;

public class SelectAllTestpointsAction extends AbstractHandleTestpointsAction {
	public void run(IAction action) {
		IEmulationView view = getEmulationView();
		if(view == null) return;
		Viewer viewer = view.getViewer();
		((TreeViewer)viewer).getTree().selectAll();
		viewer.setSelection(viewer.getSelection());
	}

	protected boolean isEnabled() {
		IEmulationView view = getEmulationView();
		if(view != null)
			return view.hasElements();
		return false;
	}

}

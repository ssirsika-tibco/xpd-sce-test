package com.tibco.bx.emulation.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.IEmulationUIConstants;
import com.tibco.bx.emulation.ui.views.IEmulationView;
import com.tibco.bx.emulation.ui.views.TestpointsContentProvider;

public class ToggleProcessNodesAction implements IViewActionDelegate, IActionDelegate2{

	
	private IViewPart viewPart;
	boolean showNode;
	
	public void init(IViewPart viewPart) {
		this.viewPart = viewPart;
		refreshViewPart();
	}

	public void run(IAction action) {
		showNode = !showNode;
		IPreferenceStore store = EmulationUIActivator.getDefault().getPreferenceStore();
		store.setDefault(IEmulationUIConstants.SHOW_PROCESS_NODES , showNode);
		action.setChecked(showNode);
		refreshViewPart();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IAction action) {
		IPreferenceStore store = EmulationUIActivator.getDefault().getPreferenceStore();
		showNode = store.getDefaultBoolean(IEmulationUIConstants.SHOW_PROCESS_NODES);
        action.setChecked(showNode);
	}

	public void runWithEvent(IAction action, Event event) {
		run(action);
		
	}

	private void refreshViewPart(){
		TreeViewer treeViewer = (TreeViewer)((IEmulationView)viewPart).getViewer();
		IContentProvider provider = treeViewer.getContentProvider();
		((TestpointsContentProvider)provider).setShowNode(showNode);
		treeViewer.refresh();
		treeViewer.expandAll();
	}
}

package com.tibco.bx.emulation.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.bx.emulation.core.EmulationAdapter;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.ui.views.IEmulationView;

public abstract class AbstractHandleTestpointsAction extends EmulationAdapter implements IViewActionDelegate, IActionDelegate2{

	private IAction action;
	private Shell shell;
	private IEmulationView emulationView;
	
	public void init(IViewPart viewPart) {
		if(viewPart instanceof IEmulationView){
			emulationView = (IEmulationView)viewPart;
		}
		shell = viewPart.getSite().getShell();
		update();
	}
	
	public void dispose() {
		EmulationCacheManager.getDefault().removeEmulationListener(this);
	}

	public void init(IAction action) {
		this.action = action;
		EmulationCacheManager.getDefault().addEmulationListener(this);
		update();
	}

	public void runWithEvent(IAction action, Event arg1) {
		run(action);
		
	}

	public void selectionChanged(IAction arg0, ISelection arg1) {
		update();		
	}

	protected abstract boolean isEnabled();
	
	protected Shell getShell(){
		return shell;
	}
	
	protected IEmulationView getEmulationView(){
		return emulationView;
	}
	
	public void emulationDataChanged(EmulationData emulationData, int status) {
		update();
	}

	public void currentEmulationDataChanged(EmulationData newData, EmulationData oldData){
		update();
	}
	
	public void update(){
		if (action != null) {
			action.setEnabled(isEnabled());
		}
	}
}

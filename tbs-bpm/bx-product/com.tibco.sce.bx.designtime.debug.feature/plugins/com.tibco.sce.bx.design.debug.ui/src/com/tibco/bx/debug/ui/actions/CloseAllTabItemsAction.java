package com.tibco.bx.debug.ui.actions;

import org.eclipse.jface.action.IAction;

import com.tibco.bx.debug.ui.views.internal.IProcessTabPaneCreator;
import com.tibco.bx.emulation.ui.actions.AbstractHandleTestpointsAction;

public class CloseAllTabItemsAction extends AbstractHandleTestpointsAction{

	@Override
	public void run(IAction action) {
		if(getEmulationView() instanceof IProcessTabPaneCreator){
			IProcessTabPaneCreator creator = (IProcessTabPaneCreator)getEmulationView();
			creator.closeAllTabPanes();
		}
	}

	@Override
	protected boolean isEnabled() {
		if(getEmulationView() != null){
			return getEmulationView().hasElements();
		}
		return false;
	}

	
}

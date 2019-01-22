package com.tibco.bx.debug.ui.views.internal;

import com.tibco.bx.debug.core.runtime.IProcessInstanceController;


public interface IProcessTabPane{
	IProcessInstanceController getController();
	void setController(IProcessInstanceController controller);
	void refresh();
	public boolean isDisposed () ;
}

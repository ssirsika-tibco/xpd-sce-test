package com.tibco.bx.debug.ui.views;

import org.eclipse.core.runtime.IStatus;

import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPaneCreator;

public interface IBatchRunListener {
	
	void beforeRunNode(IProcessInstanceController processController, IProcessTabPaneCreator create);
	void postRunNode();
	void handleError(IStatus error);
}

package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.emf.ecore.EObject;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.ui.wizards.IResultCandidateCreater;

public interface IProcessTabPaneCreator extends IResultCandidateCreater{

	IProcessTabPane createIProcessTabPane();
	IProcessTabPane getProcessInstanceController(String activeId,EObject activity);
	BxDebugTarget getTarget();
	void closeAllTabPanes();
}

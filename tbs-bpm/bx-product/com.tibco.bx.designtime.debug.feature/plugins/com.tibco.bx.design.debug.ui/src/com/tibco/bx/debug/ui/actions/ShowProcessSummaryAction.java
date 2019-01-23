package com.tibco.bx.debug.ui.actions;

import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.views.internal.ShowProcessSummaryDialog;

public class ShowProcessSummaryAction {

	private IProcessInstanceController controller;

	public ShowProcessSummaryAction(IProcessInstanceController controller) {
		super();
		this.controller = controller;
	}

	public void execute() {
		String message = null;
		if (controller.getErrorMessage() != null) {
			String subMessage = controller.getErrorMessage().substring(controller.getErrorMessage().indexOf('@') + 1);
			String linkId = subMessage.substring(subMessage.indexOf('[') + 1, subMessage.length() - 1);
			String linkName = ProcessUtil.getDisplayName(ProcessUtil.getLink(controller.getProcess(), linkId));
			if(linkName == null || "".equals(linkName)) {
				linkName = linkId;
			}
			String errorMesssage = String.format(Messages.getString("ShowProcessSummaryAction.AssertionError"), new Object[] { linkName }); //$NON-NLS-1$
			message = errorMesssage;
		} else {
			message = null;
		}

		ShowProcessSummaryDialog showProcess = new ShowProcessSummaryDialog(null, controller.getThread(), message);
		showProcess.open();
	}

}

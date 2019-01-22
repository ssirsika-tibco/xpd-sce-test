package com.tibco.bx.debug.bpm.ui.actions;

import com.tibco.bx.debug.api.BreakWhen;

public class AddBeforeBreakpointAction extends AddBreakpointAction {

	public AddBeforeBreakpointAction() {
		super();
		this.breakWhen=BreakWhen.ENTRY;
	}

}

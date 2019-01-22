package com.tibco.bx.debug.bpm.ui.actions;

import com.tibco.bx.debug.api.BreakWhen;

public class AddAfterBreakpointAction extends AddBreakpointAction {

	public AddAfterBreakpointAction() {
		super();
		this.breakWhen=BreakWhen.RETURN;
	}

}

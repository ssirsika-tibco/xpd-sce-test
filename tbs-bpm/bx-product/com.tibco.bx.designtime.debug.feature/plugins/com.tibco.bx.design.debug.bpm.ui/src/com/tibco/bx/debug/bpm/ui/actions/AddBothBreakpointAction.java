package com.tibco.bx.debug.bpm.ui.actions;

import com.tibco.bx.debug.api.BreakWhen;

public class AddBothBreakpointAction extends AddBreakpointAction {

	public AddBothBreakpointAction() {
		super();
		this.breakWhen=BreakWhen.BOTH;
	}

}

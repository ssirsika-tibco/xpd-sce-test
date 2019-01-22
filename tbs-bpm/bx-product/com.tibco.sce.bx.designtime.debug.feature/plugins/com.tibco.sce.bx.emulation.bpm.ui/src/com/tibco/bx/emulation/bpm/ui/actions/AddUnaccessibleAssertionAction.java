package com.tibco.bx.emulation.bpm.ui.actions;

public class AddUnaccessibleAssertionAction extends AddAssertionAction {

	@Override
	boolean getAccessable() {
		return false;
	}

}
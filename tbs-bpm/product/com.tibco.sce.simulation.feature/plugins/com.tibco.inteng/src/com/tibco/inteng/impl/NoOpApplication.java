package com.tibco.inteng.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tibco.inteng.Invocable;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;

public class NoOpApplication implements Invocable {

	public List getFormalParameters() {
		return Collections.EMPTY_LIST;
	}

	public List getPopulatedFormalParameters(ProcessThread thread) {
		return Collections.EMPTY_LIST;
	}

	public boolean validate(ProcessState state, List args) {
		return true;
	}

	public String getId() {
		return "NoOpImplementation";
	}

	public void submit(ProcessState state, List args) {
		; // do nothing
	}

	public Map getExtendedAttributes() {
		// At this time there are no extended attributes for 'No' implementation
		return Collections.EMPTY_MAP;
	}

}

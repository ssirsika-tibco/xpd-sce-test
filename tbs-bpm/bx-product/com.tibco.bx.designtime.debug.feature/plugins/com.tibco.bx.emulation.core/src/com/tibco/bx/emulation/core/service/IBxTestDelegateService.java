package com.tibco.bx.emulation.core.service;

import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.emulation.model.Testpoint;

public interface IBxTestDelegateService {

	public void addTestPoint(BxDebugTarget debugTarget,Testpoint tp);
	public void removeTestPoint(BxDebugTarget debugTarget,Testpoint tp);
}

package com.tibco.bx.debug.bpm.core.serviceImpl;

import com.tibco.bx.debug.bpm.core.BxTestDelegate;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.emulation.core.service.IBxTestDelegateService;
import com.tibco.bx.emulation.model.Testpoint;

public class BxTestDelegateServiceImpl implements IBxTestDelegateService {

	@Override
	public void addTestPoint(BxDebugTarget debugTarget,Testpoint tp) {
		((BxTestDelegate) debugTarget.getTestDelegate()).addTestpoint(tp);
	}

	@Override
	public void removeTestPoint(BxDebugTarget debugTarget,Testpoint tp) {
		((BxTestDelegate) debugTarget.getTestDelegate()).removeTestpoint(tp);
	}

}

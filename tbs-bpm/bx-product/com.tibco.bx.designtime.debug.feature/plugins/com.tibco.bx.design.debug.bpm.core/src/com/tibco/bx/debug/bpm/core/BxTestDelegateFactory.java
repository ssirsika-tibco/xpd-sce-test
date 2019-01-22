package com.tibco.bx.debug.bpm.core;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.core.runtime.IBxTester;
import com.tibco.bx.debug.core.runtime.ITestDelegate;
import com.tibco.bx.debug.core.runtime.ITestDelegateFactory;

public class BxTestDelegateFactory implements ITestDelegateFactory{

	@Override
	public ITestDelegate createTestDelegate(IBxTester bxTester)
			throws CoreException {
		ITestDelegate testDelegate = new BxTestDelegate(bxTester);
		return testDelegate;
	}

	@Override
	public String getModelType() {
		return DebugBPMActivator.BPM_MODEL_TYPE;
	}

}

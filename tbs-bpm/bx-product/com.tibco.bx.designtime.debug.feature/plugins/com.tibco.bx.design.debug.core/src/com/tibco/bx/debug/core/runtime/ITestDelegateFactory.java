package com.tibco.bx.debug.core.runtime;

import org.eclipse.core.runtime.CoreException;

public interface ITestDelegateFactory {
	
	String getModelType();
	
	ITestDelegate createTestDelegate(IBxTester bxTester) throws CoreException;

}

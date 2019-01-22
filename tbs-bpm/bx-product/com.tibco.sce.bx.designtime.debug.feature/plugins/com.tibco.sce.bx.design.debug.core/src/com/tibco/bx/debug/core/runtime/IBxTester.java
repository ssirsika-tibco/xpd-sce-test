package com.tibco.bx.debug.core.runtime;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.tester.Assertion;
import com.tibco.bx.tester.Testpoint;


public interface IBxTester {

	void addTestpoint(Testpoint testpoint) throws CoreException;

	void addAssertion(Assertion assertion) throws CoreException;

	void removeAllTestpoints() throws CoreException;

	void removeAllAssertions() throws CoreException;

	void removeTestpoint(Testpoint testpoint) throws CoreException;

	void removeAssertion(Assertion assertion) throws CoreException;

	boolean isTesterValid();
}

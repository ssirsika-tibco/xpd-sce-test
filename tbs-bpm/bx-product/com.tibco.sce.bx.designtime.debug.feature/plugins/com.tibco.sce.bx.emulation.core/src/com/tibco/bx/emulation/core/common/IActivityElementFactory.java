package com.tibco.bx.emulation.core.common;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.Testpoint;

public interface IActivityElementFactory {

	IInOutElement createInputElement(Input input, EObject process, String modelType);
	IInOutElement createOutputElement(Output output, EObject process, String modelType);
	ITestpointElement createTestpointElement(Testpoint testpoint, EObject process, String modelType);
}

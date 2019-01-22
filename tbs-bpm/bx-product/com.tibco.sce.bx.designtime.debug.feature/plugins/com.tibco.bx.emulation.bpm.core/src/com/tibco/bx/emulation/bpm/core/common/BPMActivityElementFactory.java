package com.tibco.bx.emulation.bpm.core.common;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.emulation.core.common.IActivityElementFactory;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.common.ITestpointElement;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.Testpoint;

public class BPMActivityElementFactory implements IActivityElementFactory {

	@Override
	public IInOutElement createInputElement(Input input, EObject process, String modelType) {
		return new InOutElement(input, ((com.tibco.xpd.xpdl2.Process) process).getId(), modelType);// TODO
	}

	@Override
	public IInOutElement createOutputElement(Output output, EObject process, String modelType) {
		return new InOutElement(output, ((com.tibco.xpd.xpdl2.Process) process).getId(), modelType);// TODO
	}

	@Override
	public ITestpointElement createTestpointElement(Testpoint testpoint, EObject process, String modelType) {
		return new TestpointElement(testpoint, ((com.tibco.xpd.xpdl2.Process) process).getId(), modelType);// TODO
	}

}

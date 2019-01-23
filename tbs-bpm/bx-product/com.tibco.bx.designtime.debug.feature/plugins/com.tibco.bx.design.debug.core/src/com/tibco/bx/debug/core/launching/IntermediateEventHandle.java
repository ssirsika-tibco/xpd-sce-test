package com.tibco.bx.debug.core.launching;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.operation.ILauncherEventHandler;

public class IntermediateEventHandle implements ILauncherEventHandler {

	private String requestMsg;
	private String responseMsg;
	private EObject activity;
	
	
	
	public IntermediateEventHandle(EObject activity) {
		this.activity = activity;
	}

	public EObject getActivity() {
		return activity;
	}

	@Override
	public void afterLauncher(String responseSoap) {
		responseMsg = responseSoap;
	}

	@Override
	public void beforeLauncher(String requestSoap) {
		requestMsg = requestSoap;
	}

	@Override
	public void setInvokeType(boolean isSoapType) {

	}

	public String getRequestMsg() {
		return requestMsg;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}

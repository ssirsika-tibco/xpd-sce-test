package com.tibco.bx.debug.core.launching;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.invoke.launcher.EmulationService;
import com.tibco.bx.debug.core.invoke.launcher.ResponseBackEvent;
import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public class MultiInputEventHandle implements ILauncherEventHandler {

	private String requestMsg;
	private String responseMsg;
	private EObject startActivity;
	private EObject process; 
	private Object processNode;
	public MultiInputEventHandle(EObject activity,Object processNode) {
		this.startActivity = activity;
		process = ProcessUtil.getProcess(this.startActivity);
		this.processNode = processNode;
	}
	public MultiInputEventHandle(EObject activity){
		this.startActivity = activity;
		process = ProcessUtil.getProcess(this.startActivity);
	}

	public EObject getStartActivity() {
		return startActivity;
	}


	@Override
	public void afterLauncher(String responseSoap) {
		if(StringUtils.isNotBlank(responseSoap.trim())){
			EmulationService service = DebugCoreActivator.createEmulationService();
			if(service !=null){
				service.afterMultiLauncher(responseSoap,startActivity,processNode);
			}
			
		}
	}

	@Override
	public void beforeLauncher(String requestSoap) {
		EmulationService service = DebugCoreActivator.createEmulationService();
		if(service !=null){
			processNode = service.beforeLauncher(startActivity, requestSoap, processNode, process);
		}
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

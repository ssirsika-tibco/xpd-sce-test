package com.tibco.bx.emulation.core.invoke;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.invoke.launcher.EmulationService;
import com.tibco.bx.debug.core.invoke.launcher.ResponseBackEvent;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.common.IActivityElement;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.common.IProcessVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.ProcessNode;

public class EmulationServiceImpl implements EmulationService{

	private void getParameters(IActivityElement inputParameter,Map<String, String> parametersMap) {
		if (inputParameter == null) {
			return;
		}
		IVariableElement[] elements = inputParameter.getVariableElements();

		for (int i = 0; i < elements.length; i++) {
			String parameterValue = ((IProcessVariableElement) elements[i]).getVariableValueString();
			parametersMap.put(ProcessUtil.getVariableName(elements[i].getEMFCharacter()), parameterValue);
		}
	}

	@Override
	public void setInput(Object input,EObject startActivity, Map<String, String> parametersMap) {
		// TODO Auto-generated method stub
		if(input instanceof Input){
			// only for bpm
			IInOutElement inputElement = EmulationUtil.createInputElement((Input)input, startActivity, "bpm"); //$NON-NLS-1$
			getParameters(inputElement,parametersMap);
		}else if(input instanceof IActivityElement){
			getParameters((IActivityElement)input,parametersMap);
		}
	}

	@Override
	public void afterMultiLauncher(String responseSoap,EObject startActivity, Object object) {
		// TODO Auto-generated method stub
		final ResponseBackEvent event = new ResponseBackEvent(responseSoap, null);
		ProcessNode processNode = (ProcessNode)object;
		for(MultiInput input:processNode.getMultiInputNodes()){
			if(ProcessUtil.getElementId(startActivity).equals(input.getId())){
				input.setResponseMessage(event.toXml());
			}
		}
		processNode.getOutput().setSoapMessage(event.toXml());
	}


	public ProcessNode getProcessNode(EObject process,Object processNode) {
		 if(processNode == null && process != null){
	        	if(processNode == null){
	        		return EmulationUtil.createProcessNodeWithOutput(process);
	        	}
		 }
	    return (ProcessNode) processNode;
	}

	@Override
	public Object beforeLauncher(EObject startActivity,String requestSoap, Object processNode,EObject process) {
		MultiInput input = EmulationFactory.eINSTANCE.createMultiInput();
		input.setId(ProcessUtil.getElementId(startActivity));
		input.setName(ProcessUtil.getDisplayName(startActivity));
		input.setSoapMessage(requestSoap);
		ProcessNode processNode2 = getProcessNode(process,processNode);
		processNode2.getMultiInputNodes().add(input);
		return processNode2;
		
	}
	
}

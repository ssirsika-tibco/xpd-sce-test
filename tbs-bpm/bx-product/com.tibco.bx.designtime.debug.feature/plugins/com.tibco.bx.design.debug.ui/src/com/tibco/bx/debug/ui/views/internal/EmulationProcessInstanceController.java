package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.bpm.core.models.IBxNodeType;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.launching.IntermediateLauncherManager;
import com.tibco.bx.debug.core.launching.MultiNodesLauncherManager;
import com.tibco.bx.debug.core.models.BxStackFrame;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.actions.InvokeAction;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Transition;

public class EmulationProcessInstanceController extends
		ProcessInstanceController {

	public EmulationProcessInstanceController(ProcessNode processNode) {
		super(processNode);
	}
	
	@Override
	protected void handleActivityStart(BxStackFrame source) {
		String activityId = source.getProcessElement().getIndex();
		 IntermediateInput input = getIntermediateInputById(activityId);
		 EObject intermediateActivity = ProcessUtil.getActivity(((BxThread)source.getThread()).getProcessElement().getIndex(),
				 activityId, ((IBxDebugTarget)source.getDebugTarget()).getModelType());
		 if(input ==null && source.getProcessElement() instanceof ProcessVisibleNode){
			 ProcessVisibleNode processVisibleNode = (ProcessVisibleNode)source.getProcessElement();
			 if(processVisibleNode.getType().equals(IBxNodeType.GATEWAY)){
				 EList<Transition> outgoingTransitions = ((Activity)intermediateActivity).getOutgoingTransitions();
				for(Transition trastion:outgoingTransitions){
					input = getIntermediateInputById(trastion.getTo());
					if(input !=null){
						intermediateActivity = ProcessUtil.getActivity(((BxThread)source.getThread()).getProcessElement().getIndex(),
								input.getId(), ((IBxDebugTarget)source.getDebugTarget()).getModelType());
						break;
					}
				}
			 }
		 }
		MultiInput multiInput = getMultiInputById(activityId);
		if(input != null && intermediateActivity != null){
			InvokeAction action = new InvokeAction(intermediateActivity);
			try {
				action.init(input.getRequestMessage(), null);
				action.run(IntermediateLauncherManager.
	        			  instance.createLauncherEventHandle(intermediateActivity));
			} catch (CoreException e) {
				DebugUIActivator.log(e);
			}
		}else if(multiInput != null && intermediateActivity !=null){
			InvokeAction action = new InvokeAction(intermediateActivity);
			try {
				action.init(multiInput.getSoapMessage(), null);
				action.run(MultiNodesLauncherManager.
	        			  instance.createLauncherEventHandle(intermediateActivity));
			} catch (CoreException e) {
				DebugUIActivator.log(e);
			}
		}
		
	}
	
	private IntermediateInput getIntermediateInputById(String activityId){
		EList<IntermediateInput> intermediateInputs = getProcessNode().getIntermediateInputs();
		for(IntermediateInput input : intermediateInputs){
			if(StringUtils.equal(activityId, input.getId())){
				return input;
			}
		}
		return null;
	}
	
	private MultiInput getMultiInputById(String activityId){
		EList<MultiInput> multiInputs = getProcessNode().getMultiInputNodes();
		for(MultiInput input:multiInputs){
			if(StringUtils.equal(activityId, input.getId())){
				return input;
			}
		}
		return null;
	}
}

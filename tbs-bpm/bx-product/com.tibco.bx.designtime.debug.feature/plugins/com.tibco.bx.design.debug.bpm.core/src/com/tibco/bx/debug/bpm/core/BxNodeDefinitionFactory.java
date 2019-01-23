package com.tibco.bx.debug.bpm.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.bx.debug.bpm.core.models.BxNodeDefinition;
import com.tibco.bx.debug.bpm.core.models.IBxNodeType;
import com.tibco.bx.debug.common.model.IBxNodeDefinition;
import com.tibco.bx.debug.core.runtime.INodeDefinitionFactory;
import com.tibco.bx.emulation.bpm.core.util.EmulationBPMUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

public class BxNodeDefinitionFactory implements INodeDefinitionFactory {
	
	public BxNodeDefinitionFactory() {
	}

	@Override
	public IBxNodeDefinition[] createNodeDefinitions(String processId)
			throws CoreException {
		com.tibco.xpd.xpdl2.Process process = Xpdl2WorkingCopyImpl.locateProcess(processId);
		List<IBxNodeDefinition> definitions = new ArrayList<IBxNodeDefinition>();
		if (process != null) {
			EList<Activity> activities = process.getActivities();
			initActivities(activities, definitions, null);
		}
		return definitions.toArray(new IBxNodeDefinition[0]);
	}

	private void initActivities(EList<Activity> activities, List<IBxNodeDefinition> list, IBxNodeDefinition parentNode){
		for(Activity activity:activities){
			BxNodeDefinition nodeDefinition = new BxNodeDefinition();
			nodeDefinition.setActivityId(activity.getId());
			nodeDefinition.setParentId(parentNode == null ? null : parentNode.getActivityId());
			nodeDefinition.setActivityName(EmulationBPMUtil.getDisplayName(activity));
			nodeDefinition.setCanStepOut(parentNode == null ? false : true);
			if(activity.getEvent() !=null){
				nodeDefinition.setType(IBxNodeType.EVENT);
				nodeDefinition.setCanStepInto(false);
			}else if(activity.getRoute() !=null){
				nodeDefinition.setType(IBxNodeType.GATEWAY);
				nodeDefinition.setCanStepInto(false);
			}else if(activity.getImplementation() !=null){
				Implementation implementation = activity.getImplementation();
				if(implementation instanceof SubFlow){
					nodeDefinition.setType(IBxNodeType.INDENPENDENT_SUBPROCESS);
					nodeDefinition.setCanStepInto(true);
				}else{
					nodeDefinition.setType(IBxNodeType.TASK);
					nodeDefinition.setCanStepInto(false);
				}	
			}else if(activity.getBlockActivity() !=null){//sub Process
				nodeDefinition.setType(IBxNodeType.EMBEDDED_SUBPROCESS);
				nodeDefinition.setCanStepInto(true);
				ActivitySet activitySet =activity.getProcess().getActivitySet(activity.getBlockActivity().getActivitySetId());
				initActivities(activitySet.getActivities(), list, nodeDefinition);
			}else{
				continue;
			}
			for(Transition transition : activity.getProcess().getTransitions()){
				if(transition.getFrom().endsWith(activity.getId())){
					nodeDefinition.addTargetId(transition.getTo());
				}else if(transition.getTo().endsWith(activity.getId())){
					nodeDefinition.addSourceId(transition.getFrom());
				}
			}
			list.add(nodeDefinition);
		}
	}
	
	
	
	@Override
	public String getModelType() {
		return DebugBPMActivator.BPM_MODEL_TYPE;
	}
}

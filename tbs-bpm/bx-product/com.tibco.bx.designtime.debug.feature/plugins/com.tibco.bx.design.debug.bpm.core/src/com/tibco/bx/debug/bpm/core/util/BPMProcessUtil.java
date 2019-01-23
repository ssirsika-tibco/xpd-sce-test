package com.tibco.bx.debug.bpm.core.util;

import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class BPMProcessUtil {
	
	public static boolean isXpdlFile(IResource file) {
		String ext = file.getFileExtension();
    	return file.getType() == IResource.FILE && ext != null && "xpdl".equals(ext.toLowerCase()); //$NON-NLS-1$
    }
	
	public static TriggerResultMessage getResultMessage(Activity activity) {
	    TriggerResultMessage triggerResultMessage = null;
	    com.tibco.xpd.xpdl2.Event event = activity.getEvent();
	    if (event instanceof StartEvent)
	        triggerResultMessage = ((StartEvent) event)
	                .getTriggerResultMessage();
	    else if (event instanceof IntermediateEvent)
	        triggerResultMessage = ((IntermediateEvent) event)
	                .getTriggerResultMessage();
	    else if (event instanceof EndEvent)
	        triggerResultMessage = ((EndEvent) event)
	                .getTriggerResultMessage();
	    return triggerResultMessage;
	}

	public static Activity getRequestActivity(Activity activity){
		TriggerResultMessage resultMessage = getResultMessage(activity);
		if(resultMessage != null){
			String activityId = (String)resultMessage.getOtherAttributes().get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ReplyToActivityId(), true);
			if(activityId != null){
				return Xpdl2ModelUtil.getActivityById(activity.getProcess(), activityId);
			}
		}
		return null;
	}

	public static Activity getResponseActivity(Activity activity){
		Collection<Activity> list =  Xpdl2ModelUtil.getAllActivitiesInProc(activity.getProcess());
		for (Activity a : list) {
			if(a != activity){
				Event event = a.getEvent();
				Implementation implementation = a.getImplementation();
				if(event != null){
					TriggerResultMessage resultMessage = getResultMessage(a);
					if(resultMessage != null){
						String activityId = (String)resultMessage.getOtherAttributes().get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ReplyToActivityId(), true);
						if(activity.getId().equals(activityId)){
							return a;
						}
					}
					
				}else if(implementation != null){
					if( implementation instanceof Task && ((Task)implementation).getTaskSend() != null){
						TaskSend taskSend = ((Task)implementation).getTaskSend();
						String activityId = (String)taskSend.getOtherAttributes().get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ReplyToActivityId(), true);
						if(activity.getId().equals(activityId)){
							return a;
						}
					}
					
				}
			}
		}
		return null;
	}

	public static WebServiceOperation getWebServiceOperationInXpdl(Activity activity){
		
		WebServiceOperation wso = null;
		if (activity != null)
			if (activity.getImplementation() instanceof Task) {
				Task task = (Task) activity.getImplementation();
				if (task.getTaskService() != null) {
					TaskService taskService = task.getTaskService();
					wso = taskService.getWebServiceOperation();
				} else if (task.getTaskSend() != null) {
					TaskSend taskSend = task.getTaskSend();
					wso = taskSend.getWebServiceOperation();
				} else if (task.getTaskReceive() != null) {
					TaskReceive taskRecieve = task.getTaskReceive();
					wso = taskRecieve.getWebServiceOperation();
				}
			} else {
				TriggerResultMessage triggerResultMessage = getResultMessage(activity);
				if (triggerResultMessage != null)
					wso = triggerResultMessage.getWebServiceOperation();
			}
		return wso;
	}

	public static boolean isPageflowProcess(Process process){
		FeatureMap map = process.getOtherAttributes();
		int size = map.size();
		for(int i =0; i< size; i++){
			EStructuralFeature feature = map.get(i).getEStructuralFeature();
			if(feature.getName().equals("xpdModelType")){//$NON-NLS-1$
				return "PageFlow".equals(((XpdModelType)map.getValue(i)).getLiteral());//$NON-NLS-1$
			}
		} 
		return false;
	}
}

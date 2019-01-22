package com.tibco.bx.debug.core.launching;

import java.util.Stack;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public enum MultiNodesLauncherManager {
	
	instance;
	

	private Stack<MultiInputEventHandle> cacheHandle;

	
	private MultiNodesLauncherManager() {
		cacheHandle = new Stack<MultiInputEventHandle>();
	}

	public ILauncherEventHandler createLauncherEventHandle(EObject activity,IProcessInstanceController lunchHandler) {
		MultiInputEventHandle newHandle = new MultiInputEventHandle(activity,lunchHandler.getProcessNode());
		cacheHandle.push(newHandle);
		return newHandle;
	}

	public ILauncherEventHandler createLauncherEventHandle(EObject activity){
		MultiInputEventHandle newHandle = new MultiInputEventHandle(activity);
		cacheHandle.push(newHandle);
		return newHandle;
	}

    public MultiInputEventHandle popMultiNodesEventHandle(){
    	return cacheHandle.pop();
    }
	public boolean isCurrentActivity(String activityId) {
		if (cacheHandle.isEmpty()) {
			return false;
		}
		MultiInputEventHandle handle = cacheHandle.get(cacheHandle.size() - 1);
		return StringUtils.equal(
				ProcessUtil.getElementId(handle.getStartActivity()), activityId);
	}
	
	
}

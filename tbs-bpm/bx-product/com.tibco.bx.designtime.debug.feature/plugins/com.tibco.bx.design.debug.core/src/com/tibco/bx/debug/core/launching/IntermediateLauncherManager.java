package com.tibco.bx.debug.core.launching;

import java.util.Stack;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.launching.IntermediateEventHandle;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public enum IntermediateLauncherManager{ 

	instance;
	
	private Stack<IntermediateEventHandle> cacheHandle;
	
	
    private IntermediateLauncherManager() {
    	cacheHandle = new Stack<IntermediateEventHandle>();
	}

	public ILauncherEventHandler createLauncherEventHandle(EObject activity){
    	IntermediateEventHandle newHandle = new IntermediateEventHandle(activity);
    	cacheHandle.push(newHandle);
        return newHandle;
    }
    
    public IntermediateEventHandle popIntermediateEventHandle(){
    	return cacheHandle.pop();
    }
    
    public boolean isCurrentActivity(String activityId){
    	if(cacheHandle.isEmpty()){
    		return false;
    	}
    	IntermediateEventHandle handle = cacheHandle.get(cacheHandle.size() -1);
    	return StringUtils.equal(ProcessUtil.getElementId(handle.getActivity()), activityId);
    }
    
}

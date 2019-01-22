package com.tibco.bx.debug.bpm.ui.actions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;

public class VisiblePropertyTester extends PropertyTester {

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		BreakWhen breakWhen = null;
		if (receiver instanceof BaseFlowNodeEditPart) {
			BaseFlowNodeEditPart nodeEditPart=(BaseFlowNodeEditPart) receiver;
        	NamedElement task = (NamedElement)nodeEditPart.getModel();
        	Process process = (com.tibco.xpd.xpdl2.Process)nodeEditPart.getParentProcess().getModel();
        	Resource resource = task.eResource();
        	if(resource == null){
        		return false;//TODO
        	}
        	String taskLocation = resource.getURIFragment(task);
    		String processId = process.getId();
    		BxBreakpoint breakpoint=getBreakpoint(processId,taskLocation);
    		String value = null;
    		if(breakpoint!=null){
    			breakWhen= breakpoint.getBreakWhen();
    			if(breakWhen == BreakWhen.ENTRY){
    				value = "before";//$NON-NLS-1$
    			}else if(breakWhen == BreakWhen.RETURN){
    				value = "after";//$NON-NLS-1$
    			}else if(breakWhen == BreakWhen.BOTH){
    				value = "both";//$NON-NLS-1$
    			}
    		}else{
    			value = "none";//$NON-NLS-1$
    		}
    		for (int i = 0; i < args.length; i++) {
    			if(args[i].equals(value)){
    				return true;
    			}
    		}
		}
		return false;
	}

	private BxBreakpoint getBreakpoint(String processId,String location) {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
		for(IBreakpoint bkpt : breakpoints){
			if (bkpt instanceof BxBreakpoint
					&& ((BxBreakpoint) bkpt).getProcessId().equals(processId)
					&& ((BxBreakpoint) bkpt).getLocation().equals(location)) {
				return (BxBreakpoint)bkpt;
			}
		}
		return null;
	}
	
}
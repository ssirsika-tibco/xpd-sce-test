package com.tibco.bx.debug.core.models;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.DebugElement;

import com.tibco.bx.debug.common.model.ProcessElement;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.runtime.IBxDebugger;

public abstract class BxDebugElement extends DebugElement implements IBxDebugElement{

	private ProcessElement element;
	public BxDebugElement(ProcessElement element, IBxDebugTarget target) {
		super(target);
		this.element = element;
	}
	
   public final ProcessElement getProcessElement() {
		return element;
	}
   
   public final String getInstanceId(){
	   return getProcessElement().getInstanceId();
   }
	
	public String getName()throws DebugException{
		return element.getName();
	}
	
	public String getModelIdentifier() {
		return DebugCoreActivator.ID_BX_DEBUG_MODEL + 
			"." + ((IBxDebugTarget)getDebugTarget()).getModelType(); //$NON-NLS-1$
	}
	
	
	/**
	 * Returns the breakpoint manager
	 * 
     * @return the breakpoint manager
     */
    protected IBreakpointManager getBreakpointManager() {
        return DebugPlugin.getDefault().getBreakpointManager();
    }	
    
    public IBxDebugger getDebugger() {
		return ((IBxDebugTarget)getDebugTarget()).getDebugger();
	}

}

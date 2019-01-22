package com.tibco.bx.debug.core.models;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

import com.tibco.bx.debug.api.BreakWhen;

public interface IBxStackFrame extends IBxDebugElement, IThread, IStackFrame {
	public IThread getParent();

	public BreakWhen getBreakWhen();

	public void setBreakWhen(BreakWhen breakWhen);
	
	public boolean isCurrent() ;

	public void setCurrent(boolean isCurrent);
}

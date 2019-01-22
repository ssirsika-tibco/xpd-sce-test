package com.tibco.bx.debug.core.models.variable;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;

import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
/**
 * Common functionality for process variables that support value modification
 */
public abstract class BxModificationVariable extends BxVariable {

	
	public BxModificationVariable(ProcessVariable processVariable,IBxDebugTarget debugTarget) {
		super(processVariable, debugTarget);
		
		
	}
	
	protected abstract void initValue();
	
	/**
	 * @see org.eclipse.debug.core.model.IValueModification#supportsValueModification()
	 */
	public boolean supportsValueModification() {
		return fValue.canModify();
	}
	
	
	/**
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(java.lang.String)
	 */
	public void setValue(String expression) throws DebugException {
		IBxValue value= generateValue(expression);
		updateValue(value);
	}
	
	protected abstract IBxValue generateValue(String expression) throws DebugException;
	
	protected abstract void updateValue(IValue value) throws DebugException;
}
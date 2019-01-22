package com.tibco.bx.debug.core.models.variable;

import java.util.Collections;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.tibco.bx.debug.common.model.ProcessElement;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.models.BxDebugElement;
import com.tibco.bx.debug.core.models.IBxDebugTarget;

public abstract class BxValue extends BxDebugElement implements IBxValue {

	protected Object value; 
	protected VariableType type;
	public BxValue(ProcessVariable processVariable, IBxDebugTarget debugTarget) {
		super((ProcessElement)processVariable, debugTarget);
		this.type = processVariable.getType();
		this.value = processVariable.getValue();
	}

	/**
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	public boolean isAllocated() throws DebugException {
		return true;
	}

	/**
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException {
		List list = getVariablesList();
		return (IVariable[])list.toArray(new IVariable[list.size()]);
	}

	protected List getVariablesList() throws DebugException{
		return Collections.EMPTY_LIST;
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IValue#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		return false;
	}
	
	@Override
	public VariableType getType() {
		return type;
	}
	
	public String getElementType() {
		return VALUE;
	}
	
	@Override
	public boolean canModify() {
		return false;
	}
}

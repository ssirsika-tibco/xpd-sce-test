package com.tibco.bx.debug.core.models.variable;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;

import com.tibco.bx.debug.common.model.VariableType;
/**
 * A primitive data type, complex type or array.
 */
public interface IBxValue extends IValue{

	public boolean verifyValue(String expression)throws DebugException;
	public VariableType getType();
	public Object getValue(String expression) throws DebugException;
	public boolean canModify();
}

package com.tibco.bx.debug.core.models.variable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;

import com.tibco.bx.debug.common.model.ProcessContainer;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.util.DateUtil;

/**
 * a process instance variable.
 */
public class BxFieldVariable extends BxModificationVariable {

	public BxFieldVariable(IBxDebugTarget debugTarget, ProcessVariable processVariable) {
		super(processVariable, debugTarget);
		initValue();
	}

	protected void initValue() {
		ProcessVariable processVariable = (ProcessVariable) getProcessElement();
		VariableType type = processVariable.getType();
		if (type instanceof BxPrimitiveType) {
			fValue = new BxPrimitiveValue(processVariable, (IBxDebugTarget) getDebugTarget());
		} else if (type instanceof BxComplexType) {
			fValue = new BxComplexValue(processVariable, (IBxDebugTarget) getDebugTarget());
		} else if (type instanceof BxListType) {
			fValue = new BxListValue(processVariable, (IBxDebugTarget) getDebugTarget());
		} else {
			fValue = ((IBxDebugTarget) getDebugTarget()).getVariableHandler().getExtDebugValue(processVariable, (IBxDebugTarget) getDebugTarget());
		}
	}

	protected IBxValue generateValue(String expression) throws DebugException {
		ProcessVariable processVariable = (ProcessVariable) getProcessElement();
		Object object = fValue.getValue(expression);
		processVariable.setValue(object);
		VariableType type = fValue.getType();
		if (type instanceof BxPrimitiveType) {
			return new BxPrimitiveValue(processVariable, (IBxDebugTarget) getDebugTarget());
		} else if (type instanceof BxComplexType) {
			return new BxComplexValue(processVariable, (IBxDebugTarget) getDebugTarget());
		} else {
			return ((IBxDebugTarget) getDebugTarget()).getVariableHandler().getExtDebugValue(processVariable, (IBxDebugTarget) getDebugTarget());
		}
	}

	public IValue getValue() throws DebugException {
		return fValue;
	}

	@Override
	public void setValue(IValue value) throws DebugException {
		if (verifyValue(value)) {
			updateValue(value);
		}
	}

	protected void updateValue(IValue value) throws DebugException {
		try {
			String valueString = value.getValueString();
			if (BxPrimitiveType.DATETIME.equals(((BxValue) value).getType())) {
				valueString = DateUtil.formatGregorianCalendarDateTime(valueString);
			} else if (BxPrimitiveType.DATE.equals(((BxValue) value).getType())) {
				valueString = DateUtil.formatGregorianCalendarDate(valueString);
			} else if (BxPrimitiveType.TIME.equals(((BxValue) value).getType())) {
				valueString = DateUtil.formatGregorianCalendarTime(valueString);
			}
			ProcessVariable processVariable = (ProcessVariable) getProcessElement();
			ProcessContainer container = processVariable.getParent();
			getDebugger().setVariable(container.getInstanceId(), getName(), valueString);
		} catch (CoreException e) {
			throw new DebugException(e.getStatus());
		}
		fireChangeEvent(DebugEvent.CONTENT);// it will call
											// BxStackFrame.getVariables();
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		return fValue.verifyValue(expression);
	}

	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		return value != null && value.getClass().equals(fValue.getClass()) && value.getReferenceTypeName().equals(fValue.getReferenceTypeName());
	}

}

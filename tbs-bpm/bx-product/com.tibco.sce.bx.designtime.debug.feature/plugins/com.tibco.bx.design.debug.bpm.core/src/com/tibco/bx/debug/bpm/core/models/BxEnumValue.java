package com.tibco.bx.debug.bpm.core.models;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;

import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.variable.BxValue;

public class BxEnumValue extends BxValue{

	public BxEnumValue(ProcessVariable processVariable,IBxDebugTarget debugTarget) {
		super(processVariable, debugTarget);
	}

	@Override
	public Object getValue(String expression) throws DebugException {
		try {
			return ((IBxDebugTarget)getDebugTarget()).getVariableHandler().getValue(type, expression);
		} catch (CoreException e) {
			throw new DebugException(new Status(IStatus.ERROR,
					DebugCoreActivator.PLUGIN_ID, IStatus.OK, 
					String.format(Messages.getString("BxEnumValue.invalidExpression"),//$NON-NLS-1$
							new Object[] { expression }), e));
		}
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		Object object = getValue(expression);
		return object!=null;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return type.getTypeString();
	}

	@Override
	public String getValueString() throws DebugException {
		if(value == null){
			return "null"; //$NON-NLS-1$
		} else {
			return value.toString();
		}
	}

	@Override
	public boolean canModify() {
		return true;
	}
}

package com.tibco.bx.debug.core.models.variable;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;

import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.IBxDebugTarget;

public class BxComplexValue extends BxValue {
	
	/*
	 * the value field is a CDS Object
	 */
	public BxComplexValue(ProcessVariable processVariable, IBxDebugTarget debugTarget) {
		super(processVariable, debugTarget);
	}
	
	public boolean verifyValue(String expression) throws DebugException {
		Object object = getValue(expression);
		return object!=null;
	}

	public String getReferenceTypeName() throws DebugException {
		return type.getTypeString();
	}

	public String getValueString() throws DebugException {
		if(value == null){
			return "null"; //$NON-NLS-1$
		} else {
			try {
				return ((IBxDebugTarget)getDebugTarget()).getVariableHandler().getSerializer()
						.serialize(value);
			} catch (Exception e) {
				throw new DebugException(new Status(IStatus.ERROR,
						DebugCoreActivator.PLUGIN_ID, IStatus.OK, Messages
								.getString("BxComplexValue.invalidObject"),e));//$NON-NLS-1$
			}
		}
	}

	public Object getValue(String expression) {
		// TODO the value object is an EObject, just set the field, never set new object to it. 
		return null;
	}

	public boolean hasVariables() throws DebugException {
		return getVariablesList().size()>0;
	}
	
	protected List<BxVariable> getVariablesList() throws DebugException{
		try{
			return ((IBxDebugTarget)getDebugTarget()).getVariableHandler().generateSubVariables(this, value);
		}catch(CoreException e){
			throw new DebugException(new Status(IStatus.ERROR,
					DebugCoreActivator.PLUGIN_ID, IStatus.OK, Messages
							.getString("BxComplexValue.invalidObject"),e));//$NON-NLS-1$
		}
		
	}
	
	
}

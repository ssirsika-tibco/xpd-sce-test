package com.tibco.bx.debug.core.models.variable;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import com.tibco.bx.debug.common.model.ProcessElement;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.BxDebugElement;
import com.tibco.bx.debug.core.models.IBxDebugTarget;

public abstract class BxVariable extends BxDebugElement implements IVariable {

	protected IBxValue fValue;
	public BxVariable(ProcessVariable processVariable, IBxDebugTarget debugTarget) {
		super((ProcessElement)processVariable, debugTarget);
	}

	/**
	 * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return fValue.getReferenceTypeName();
	}

	/**
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	public boolean hasValueChanged() throws DebugException {
		return false;
	}

	/**
	 * @see org.eclipse.debug.core.model.IDebugElement#getModelIdentifier()
	 */
	public String getModelIdentifier() {
		return getDebugTarget().getModelIdentifier();
	}

	/**
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(java.lang.String)
	 */
	public void setValue(String expression) throws DebugException {
		notSupported(Messages.getString("BxVariable.noModification"), null); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(org.eclipse.debug.core.model.IValue)
	 */
	public void setValue(IValue value) throws DebugException {
		notSupported(Messages.getString("BxVariable.noModification"), null); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.debug.core.model.IValueModification#supportsValueModification()
	 */
	public boolean supportsValueModification() {
		return false;
	}

	/**
	 * @see org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang.String)
	 */
	public boolean verifyValue(String expression) throws DebugException {
		return false;
	}

	/**
	 * @see org.eclipse.debug.core.model.IValueModification#verifyValue(org.eclipse.debug.core.model.IValue)
	 */
	public boolean verifyValue(IValue value) throws DebugException {
		return false;
	}
	
	public String getElementType() {
		return VARIABLE;
	}
}

package com.tibco.bx.debug.core.models.variable;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.api.IDebugConstants;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;

public class BxComplexType extends VariableType{

    private final static String NULL_ATTR = IDebugConstants.NULL_ATTR;     

	private ICDSDeserializer deserializer;
	public BxComplexType(ICDSDeserializer deserializer, String typeString){
		super(typeString);
		this.deserializer = deserializer;
	}
	
	@Override
	public Object getValue(String valueString) throws CoreException {
		if (NULL_ATTR.equals(valueString)) {
			return null;
		}
		try {
			if ((getTypeString() != null || !"".equals(getTypeString())) && getTypeString().lastIndexOf(".") != -1) { //$NON-NLS-1$ //$NON-NLS-2$
				String pName = getTypeString().substring(0, getTypeString().lastIndexOf(".")); //$NON-NLS-1$
				return deserializer.deserialize(valueString, pName);
			} else {
				return deserializer.deserialize(valueString);
			}
		} catch (Exception e) {
			DebugCoreActivator.log(e);
			throw new CoreException(DebugCoreActivator.newStatus(e, Messages.getString("BxComplexType.deserializeError"))); //$NON-NLS-1$
		}
	}
	
}

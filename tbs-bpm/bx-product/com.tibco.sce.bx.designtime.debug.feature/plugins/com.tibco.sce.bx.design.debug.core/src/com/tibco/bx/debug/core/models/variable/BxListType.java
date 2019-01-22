package com.tibco.bx.debug.core.models.variable;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;

import com.tibco.bx.debug.api.IDebugConstants;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.util.NetUtil;

public class BxListType extends VariableType{

    private final static String NULL_ATTR = IDebugConstants.NULL_ATTR;     

	private VariableType basicType;
	public BxListType(VariableType basicType){
		super("List"); //$NON-NLS-1$
		this.basicType = basicType;

	}
	
	@Override
	public String getTypeString() {
		return super.getTypeString() + "<" + basicType.getTypeString() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public VariableType getBasicType(){
		return basicType;
	}

	@Override
	public Object getValue(String valueString) throws DebugException {
		if (NULL_ATTR.equals(valueString)) {
			return null;
		}
		ByteArrayInputStream bis;
		try {
			List<String> list = NetUtil.deserializeList(valueString);
			List result = new ArrayList();
			for (String str: list) {
				result.add(basicType.getValue(str));
			}
			return result;
		} catch (Exception e) {
			throw new DebugException(DebugCoreActivator.newStatus(e, Messages.getString("BxListType.deserizalizError"))); //$NON-NLS-1$
		} 
	}
	
}

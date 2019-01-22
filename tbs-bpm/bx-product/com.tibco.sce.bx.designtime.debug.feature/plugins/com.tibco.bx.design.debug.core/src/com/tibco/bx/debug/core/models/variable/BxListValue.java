package com.tibco.bx.debug.core.models.variable;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;

import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
/*
 * the value is a List, which contains BxPrimitiveValue or BxComplexValue 
 */
public class BxListValue extends BxValue{

	List<BxVariable> varList = new ArrayList<BxVariable>();
	String topVarName;
	public BxListValue(ProcessVariable processVariable, IBxDebugTarget debugTarget) {
		super(processVariable, debugTarget);
	}

	
	public Object getValue(String expression) {
		return null;
	}

	
	public boolean verifyValue(String expression) throws DebugException {
		return false;
	}

	
	public String getReferenceTypeName() throws DebugException {
		return type.getTypeString();
	}

	
	public String getValueString() throws DebugException {
		if(value == null){
			return "null"; //$NON-NLS-1$
		}
		return getReferenceTypeName()+"["+ ((List)value).size() +"]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean hasVariables() throws DebugException {
		return value==null?false:true;
	}
	
	protected List getVariablesList(){
		varList.clear();
		((ProcessVariable)getProcessElement()).getElements().clear();
		if(value != null){
			List list = (List)value;
			BxVariable variable = null;
			int i=0;
			for(Object item : list){
				String name = "[" + i++ + "]"; //$NON-NLS-1$ //$NON-NLS-2$
				ProcessVariable cVariable = new ProcessVariable(name, name, (ProcessVariable)getProcessElement());
				
				if(type instanceof BxListType){
					cVariable.setType(((BxListType)type).getBasicType());
				}else{
					cVariable.setType(type);
				}
				cVariable.setValue(item);
				BxVariable dVariable = new BxSubVariable(cVariable, (IBxDebugTarget)this.getDebugTarget());
				varList.add(dVariable);
			}
		}
		return varList;
	}
	
}

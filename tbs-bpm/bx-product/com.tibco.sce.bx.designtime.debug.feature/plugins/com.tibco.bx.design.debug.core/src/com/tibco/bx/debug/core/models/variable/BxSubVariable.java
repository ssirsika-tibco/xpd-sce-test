package com.tibco.bx.debug.core.models.variable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;

import com.tibco.bx.debug.common.model.ProcessContainer;
import com.tibco.bx.debug.common.model.ProcessElement;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.util.NetUtil;
/**
 * an field of an complex data variable or item of a List.
 */
public class BxSubVariable extends BxModificationVariable {

	public BxSubVariable(ProcessVariable processVariable, IBxDebugTarget debugTarget) {
		super(processVariable, debugTarget);
		initValue();
	}

	protected void initValue(){
		ProcessVariable processVariable = (ProcessVariable)getProcessElement();
		VariableType type = processVariable.getType();
		Object value = processVariable.getValue();
		if(value instanceof List){
			fValue = new BxListValue(processVariable, (IBxDebugTarget)getDebugTarget());
		}else{
			if(type instanceof BxPrimitiveType){
				fValue = new BxPrimitiveValue(processVariable, (IBxDebugTarget)getDebugTarget());
			}else if(type instanceof BxComplexType){
				fValue = new BxComplexValue(processVariable, (IBxDebugTarget)getDebugTarget());
			}else{
				fValue = ((IBxDebugTarget)getDebugTarget()).getVariableHandler().
					getExtDebugValue(processVariable, (IBxDebugTarget)getDebugTarget());
			}
		}
	}
	
	protected IBxValue generateValue(String expression) throws DebugException {
		Object object = fValue.getValue(expression);
		VariableType type = fValue.getType();
		
		if(!(type instanceof BxComplexType)){
			//set the corresponding field
			String varName = ((ProcessVariable)getProcessElement()).getName();
			ProcessVariable parentVariable = (ProcessVariable)((ProcessVariable)getProcessElement()).getParent();
			Object parentType = parentVariable.getType();
			Object parentValue = parentVariable.getValue();
			if(parentType instanceof BxComplexType){
				((IBxDebugTarget)getDebugTarget()).getVariableHandler().setChildValue(parentValue, varName, object);
			}else if(parentType instanceof BxListType){
				int index = parentVariable.getElements().indexOf(getProcessElement());
				((List)parentValue).set(index, object);
			}
		}
		return null;
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
	
	protected void updateValue(IValue value) throws DebugException{
		//update the top EObject variable
		BxThread thread =(BxThread)((IBxDebugTarget)getDebugTarget()).getCurrentThread();
		try {
			ProcessVariable parent = getTopProcessVariable(getProcessElement());
			String valueString = null;
			if(parent.getType() instanceof BxComplexType){
				valueString = ((IBxDebugTarget)getDebugTarget()).getVariableHandler().
					getSerializer().serialize(parent.getValue());
			}else{// BxListType
				List list  = (List)parent.getValue();
				VariableType basicType = ((BxListType)parent.getType()).getBasicType();
				List<String> strList = new ArrayList<String>();
				for(Object object : list){
					if(basicType instanceof BxComplexType){
						strList.add(((IBxDebugTarget)getDebugTarget()).getVariableHandler().
								getSerializer().serialize(object));
					}else{
						strList.add(object.toString());
					}
				}
				valueString = NetUtil.serializeList(strList);
			}
			getDebugger().setVariable(
					parent.getParent().getInstanceId(), 
					parent.getName(), 
					valueString);
		} catch (IOException e) {
			throw new DebugException(DebugCoreActivator.newStatus(e, Messages.getString("BxSubVariable.serializeError"))); //$NON-NLS-1$
		} catch (CoreException e) {
			throw new DebugException(e.getStatus());
		}
		fireChangeEvent(DebugEvent.CONTENT);// it will call BxStackFrame.getVariables();
	}
	
	@Override
	public boolean verifyValue(String expression) throws DebugException {
		return fValue.verifyValue(expression);
	}

	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		return value !=null 
		&& value.getClass().equals(fValue.getClass()) 
		&& value.getReferenceTypeName().equals(fValue.getReferenceTypeName());
	}
	
	private ProcessVariable getTopProcessVariable(ProcessElement processElement){
		ProcessContainer parent = processElement.getParent();
		ProcessContainer grandParent = parent.getParent();
		if(!(grandParent instanceof ProcessVariable) && parent instanceof ProcessVariable && (
				((ProcessVariable)parent).getType() instanceof BxComplexType
				|| ((ProcessVariable)parent).getType() instanceof BxListType)){
			return (ProcessVariable)parent;
		}else {
			return getTopProcessVariable((ProcessElement)parent);
		}
		
	}
}

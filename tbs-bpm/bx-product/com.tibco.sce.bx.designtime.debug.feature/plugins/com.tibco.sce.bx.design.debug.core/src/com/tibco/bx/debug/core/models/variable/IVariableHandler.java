package com.tibco.bx.debug.core.models.variable;

import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.models.IBxDebugTarget;

public interface IVariableHandler {

	public VariableType getGlobalVariableType(String processId, String variableName, String nameSpace, String typeName) throws CoreException;
	public VariableType getLocalVariableType(String processId, String activityId, String variableName, String nameSpace, String typeName) throws CoreException;
	public Object getValue(VariableType variableType, String valueString) throws CoreException;
	
	public ICDSSerializer getSerializer();
	public ICDSDeserializer getDeserializer();
	public void setChildValue(Object ParentValue, String varibaleName,Object value);
	public List<BxVariable> generateSubVariables(BxComplexValue parent, Object parentValue) throws CoreException;
	
	public BxValue getExtDebugValue(ProcessVariable commonVariable, IBxDebugTarget debugTarget);
}

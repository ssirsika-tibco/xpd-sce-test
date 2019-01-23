package com.tibco.bx.emulation.bpm.core.common;

import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.emulation.core.common.IActivityElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;


public abstract class ActivityElement implements IActivityElement{

	protected List<ProcessVariableElement> list;
	private String processId;
	private String processModelType;
	/*
	 * processId and modelType can not be null
	 */
	public ActivityElement(String processId, String processModelType) {
		this.processId = processId;
		this.processModelType = processModelType;
	}
	
	public String getProcessId() {
		return processId;
	}

	public String getProcessModelType() {
		return processModelType;
	}
	
	public ProcessVariableElement[] getVariableElements() {
		return list.toArray(new ProcessVariableElement[list.size()]);
	}
	
	protected abstract void init(String id);
	
	public void removeVariableElement(IVariableElement variableElement){
		list.remove(variableElement);
	}
	
	public void clearVariableElements() {
	    list.clear();
	}
	
	public ProcessVariableElement createVariableElement(EObject processVariable, String valueString){
		ProcessVariableElement element = null;
		ProcessRelevantData relevantData = (ProcessRelevantData)processVariable;
		if(relevantData.isIsArray()){
			element = new ProcessVariableElementList((ProcessRelevantData)relevantData, valueString);
			list.add(element);
		}else{
			DataType dataType = relevantData.getDataType();
			if(dataType instanceof DeclaredType){
				TypeDeclaration declaration = Xpdl2ModelUtil.getPackage(dataType).getTypeDeclaration(((DeclaredType)dataType).getTypeDeclarationId());
				ExternalReference reference = declaration.getExternalReference();
				if(reference != null)
					dataType = reference;
			}
			if(dataType instanceof ExternalReference){
				if(valueString == null || valueString.trim().equals("")){//$NON-NLS-1$
					element = new ComplexVariableElement(relevantData, new HashMap<String, String>());
				}else{
					element = new ComplexVariableElement(relevantData, valueString);
				}
				list.add(element);
			}else{//(dataType is BasicType or Declared BasicType
				element = new SimpleVariableElement(relevantData, valueString);
				list.add(element);
			}
		}
		return element;
	}
	
	public abstract String getVariableValueString(String valiableName);
	
	@Override
	public boolean isValid(){
		boolean isValid = true;
		for(IVariableElement element : list){
			isValid = isValid&& element.isValid();
		}
		return isValid;
	}
}

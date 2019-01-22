package com.tibco.bx.emulation.bpm.core.common;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.bx.emulation.core.common.IEnumerationVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElement;

public class EnumerationVariableElement extends BomVariableElement implements IEnumerationVariableElement{
	
	public EnumerationVariableElement(BomVariableElement parent, String name, Enumeration type, String valueString) {
		super(parent, name, type);
		initValue(valueString);
	}

	protected void initValue(String valueString){
		if(valueString == null || valueString.trim().length() == 0){
			setValueString(valueString);
		}else{
			setValueString(valueString.substring(valueString.lastIndexOf(".")+1, valueString.length()));//$NON-NLS-1$
		}
	}

	public String getScriptString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getAbsolateName());
		buffer.append("="); //$NON-NLS-1$
		buffer.append(((Enumeration)getType()).getName());
		buffer.append("."); //$NON-NLS-1$
		buffer.append(getValueString());
		buffer.append(";"); //$NON-NLS-1$
		return buffer.toString();
	}

	public String getValueString() {
		return value == null ? "" : ((EnumerationLiteral)value).getName();//$NON-NLS-1$
	}

	public Object getValue() {
		return value;
	}
	
	public IVariableElement[] getVariableElements() {
		return null;
	}

	public boolean hasVariables() {
		return false;
	}

	public void setValueString(String value) {
		EnumerationLiteral literal = ((Enumeration)getType()).getOwnedLiteral(value);
		this.value = literal;
	}
	
	public String[] getEnumNames(){
		EList<EnumerationLiteral> list = ((Enumeration)getType()).getOwnedLiterals();

		String[] Names = new String[list.size()];
		for(int i =0; i< Names.length; i++){
			Names[i] = list.get(i).getName();
		}
		return Names;
	}
}

package com.tibco.bx.emulation.core.common;


public interface IVariableElementList extends IVariableElement{

	public IVariableElement createVariableElement();
	
	public boolean removeVariableElement(IVariableElement variableElement);
	
	public int getIndex(IVariableElement variableElement);
	
	public int getLower();
	
	public int getUpper();
}

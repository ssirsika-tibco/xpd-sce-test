package com.tibco.bx.emulation.bpm.core.common;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.bx.emulation.core.common.VariableElement;


public abstract class BomVariableElement extends VariableElement{

	
	EObject type;// Class or DataType
	String name;
	public BomVariableElement(IVariableElement parent, String name, EObject type) {
		this.parent = parent;
		this.name = name;
		this.type = type;
	}
	
	public EObject getEMFCharacter() {
		return type;
	}

	public Object getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	protected String getInitAbsolateName(){
		if(parent == null || !(parent instanceof BomVariableElement)){
			return getName();
		}else{
			String parentInitAbsolateName = ((BomVariableElement)getParent()).getInitAbsolateName().replace('.', '_');
			if(parent instanceof IVariableElementList){
				int index = ((IVariableElementList)parent).getIndex(this);
				if(index == -1){
					index = ((IVariableElementList)parent).getVariableElements().length;
				}
				return parentInitAbsolateName +"_" + index +"_"; //$NON-NLS-1$ //$NON-NLS-2$
			}else{
				return parentInitAbsolateName + "." + getName(); //$NON-NLS-1$
			}
		}
	}
	
	protected String getAbsolateName(){
		if(parent == null|| !(parent instanceof BomVariableElement)){
			return getName();
		}else if(parent instanceof IVariableElementList){
			return ((BomVariableElement)getParent()).getAbsolateName()+"_" + ((IVariableElementList)parent).getIndex(this) +"_"; //$NON-NLS-1$ //$NON-NLS-2$
		}else{
			return ((BomVariableElement)getParent()).getAbsolateName() + "." + getName(); //$NON-NLS-1$
		}
	}
	
	@Override
	public boolean isValid(){
		return value != null;
	}
}

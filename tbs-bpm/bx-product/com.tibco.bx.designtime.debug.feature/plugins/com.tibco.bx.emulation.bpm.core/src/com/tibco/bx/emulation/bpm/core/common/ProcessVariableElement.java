package com.tibco.bx.emulation.bpm.core.common;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.emulation.core.common.IProcessVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.bx.emulation.core.common.VariableElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;


public abstract class ProcessVariableElement extends VariableElement implements IProcessVariableElement{

	private EObject processVariable;
	
	public ProcessVariableElement(IVariableElementList parent, EObject processVariable, String valueString) {
		this.parent = parent;
		this.processVariable = processVariable;
	}
	
	public EObject getEMFCharacter() {
		return processVariable;
	}
	
	public String getName() {
		return ((ProcessRelevantData)getEMFCharacter()).getName();
	}
	
	protected String getInitAbsolateName(){
		if(parent != null){//IVariableElementList
			String parentName = ((IVariableElementList)getParent()).getName();
			int index = ((IVariableElementList)parent).getIndex(this);
			return parentName +"_" + index +"_"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return getName();
	}
}

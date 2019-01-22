package com.tibco.bx.emulation.ui.properties;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.bx.emulation.core.common.IActivityElement;
import com.tibco.bx.emulation.core.common.IVariableElement;

public class VariablesContentProvider implements ITreeContentProvider {

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof IActivityElement) {
			return ((IActivityElement) inputElement).getVariableElements();
		}else if(inputElement instanceof IVariableElement){
			return ((IVariableElement) inputElement).getVariableElements();
		}
		return new Object[0];
	}

	public void dispose() {
		
	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		
	}

	public Object[] getChildren(Object parentElement) {
		return getElements(parentElement);
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if(element instanceof IVariableElement){
			return ((IVariableElement) element).hasVariables();
		}else{
			return false;
		}
	}

}

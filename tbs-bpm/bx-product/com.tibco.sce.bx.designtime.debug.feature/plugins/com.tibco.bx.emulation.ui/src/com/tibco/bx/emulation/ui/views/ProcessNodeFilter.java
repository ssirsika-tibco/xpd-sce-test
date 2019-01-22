package com.tibco.bx.emulation.ui.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.bx.emulation.model.ProcessNode;

public class ProcessNodeFilter extends ViewerFilter{

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof ProcessNode && 
				((ProcessNode)element).getTestpoints().size() == 0 &&
				((ProcessNode)element).getAssertions().size() == 0 &&
				((ProcessNode)element).getInput() == null &&
				((ProcessNode)element).getOutput() == null &&
				((ProcessNode)element).getIntermediateInputs() == null){
			return false;
		}
		return true;
	}
}


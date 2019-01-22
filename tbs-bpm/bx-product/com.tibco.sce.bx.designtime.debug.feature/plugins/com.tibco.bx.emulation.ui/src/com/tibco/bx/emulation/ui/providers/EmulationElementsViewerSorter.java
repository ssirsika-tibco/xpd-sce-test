package com.tibco.bx.emulation.ui.providers;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;

public class EmulationElementsViewerSorter extends ViewerSorter {

	public EmulationElementsViewerSorter() {
		
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if(e1 instanceof Input){
			return -1;
		}else if(e1 instanceof Output){
			return 1;
		}else if(e1 instanceof IntermediateInput && (e2 instanceof Testpoint
				|| e2 instanceof Assertion)){
			return -1;
		}else if(e1 instanceof Testpoint && e2 instanceof Assertion){
			return -1;
		}else if(e1 instanceof IntermediateInput && e2 instanceof IntermediateInput){
			EList<IntermediateInput> list = ((ProcessNode)((IntermediateInput)e1).eContainer()).getIntermediateInputs();
			return list.indexOf((IntermediateInput)e1) < list.indexOf((IntermediateInput)e2) ? -1 :1;
			
		}
		return super.compare(viewer, e1, e2);
	}

	
}

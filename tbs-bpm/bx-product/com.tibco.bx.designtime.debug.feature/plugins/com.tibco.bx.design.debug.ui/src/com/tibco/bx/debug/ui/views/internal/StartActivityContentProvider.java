package com.tibco.bx.debug.ui.views.internal;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.bx.debug.core.util.ProcessUtil;

public class StartActivityContentProvider implements IStructuredContentProvider {
    
    //private Process oldValue;

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
        if(inputElement instanceof EObject && ProcessUtil.isProcess((EObject)inputElement)){
            // get start event
            return ProcessUtil.getCanStartActivities((EObject)inputElement);
        }else if(inputElement instanceof List){
        	return ((List)inputElement).toArray();
        }
		return null;
	}

}

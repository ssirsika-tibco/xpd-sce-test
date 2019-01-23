package com.tibco.bx.emulation.ui.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;

public abstract class AbstractEmulationPropertySection extends AbstractTransactionalSection implements IFilter{
	
	protected final EClass eClass;
	
	public AbstractEmulationPropertySection(EClass eClass){
		this.eClass = eClass;
	}
	
	@Override
	protected Command doGetCommand(Object arg0) {
		return null;
	}
	
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		Object object = ((IStructuredSelection)selection).getFirstElement();
		EmulationElement element = null;
		if(object instanceof EmulationElement){
			element = (EmulationElement)object;
		}else if (object instanceof IAdaptable) {
	        EObject processModelObject = (EObject)((IAdaptable)object).getAdapter(EObject.class);
	        element = EmulationUIUtil.getCurrentEmulationElement(processModelObject, eClass);
	    }
		super.setInput(part, new StructuredSelection(element));
	}
	
	@Override
	public boolean select(Object toTest) {
		EmulationElement element = null;
	    if (toTest instanceof EmulationElement)
	    	element = (EmulationElement)toTest;
	    else if (toTest instanceof IAdaptable) {
	        EObject processModelObject = (EObject)((IAdaptable)toTest).getAdapter(EObject.class);
	        if(processModelObject != null)
	        	element = EmulationUIUtil.getCurrentEmulationElement(processModelObject, eClass);
	    }
	    if ((element != null) && (this.eClass != null) && (this.eClass.isSuperTypeOf(element.eClass()))) {
	        return true;
	    }
	    return false;
	}
	
}

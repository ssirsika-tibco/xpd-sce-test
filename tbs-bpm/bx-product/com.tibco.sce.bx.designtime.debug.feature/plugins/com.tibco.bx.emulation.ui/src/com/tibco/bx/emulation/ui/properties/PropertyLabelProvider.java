package com.tibco.bx.emulation.ui.properties;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;

public class PropertyLabelProvider extends LabelProvider{

	public Image getImage(Object object) {
		NamedElement element = getNamedElement(object);
		if (element instanceof ProcessNode) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_PROCESSNODE);
        }else if (element instanceof Input) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
        }else if (element instanceof Output) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_OUTPUT);
        }else if (element instanceof Testpoint) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_TESTPOINT);
        }else if (element instanceof IntermediateInput) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INTERMEDIATEINPUT);
        }else if (element instanceof Assertion) {
        	if(((Assertion)element).isAccessible()){
        		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_ASSERTION_EN);
        	}else{
        		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_ASSERTION_DIS);
        	}
        }
		return null;
	}
	

	 public String getText(Object object) {
		NamedElement element = getNamedElement(object);
		if(element != null){
			return element.getName();
		}
		return super.getText(object);
	}

	private NamedElement getNamedElement(Object object){
		Object element = object;
	    if(element instanceof IStructuredSelection)
	    	element = ((IStructuredSelection)object).getFirstElement();
	    if(element instanceof NamedElement){
	    	return (NamedElement)element;
	    }else if (element instanceof AbstractGraphicalEditPart) {
	    	EObject model = (EObject)((AbstractGraphicalEditPart)element).getModel();
        	return EmulationUIUtil.getCurrentEmulationElement(model);
        }
	    return null;
	}
	
}

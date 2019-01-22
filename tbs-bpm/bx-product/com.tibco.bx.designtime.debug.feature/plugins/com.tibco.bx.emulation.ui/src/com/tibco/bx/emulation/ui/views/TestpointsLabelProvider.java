package com.tibco.bx.emulation.ui.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;

public class TestpointsLabelProvider extends LabelProvider {

	public Image getImage(Object element) {
        if (element instanceof ProcessNode) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_PROCESSNODE);
        }else if (element instanceof Testpoint) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_TESTPOINT);
        }else if (element instanceof Assertion) {
        	if(((Assertion)element).isAccessible()){
        		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_ASSERTION_EN);
        	}else{
        		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_ASSERTION_DIS);
        	} 
        }else if (element instanceof Input) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
        }else if (element instanceof Output) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_OUTPUT);
        }else if (element instanceof IntermediateInput) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INTERMEDIATEINPUT);
        }
        return null;
	}
	
	 public String getText(Object element) {
		if (element instanceof NamedElement) {
			return ((NamedElement) element).getName();
		}
		return ""; //$NON-NLS-1$
	}
}

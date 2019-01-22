package com.tibco.bx.emulation.bpm.ui.properties;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.emulation.bpm.core.util.EmulationBPMUtil;
import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.xpdl2.Transition;

public class PropertyLabelProvider extends LabelProvider{

	public Image getImage(Object element) {
		Object object = element;
	    if(object instanceof IStructuredSelection)
	    	object = ((IStructuredSelection)object).getFirstElement();
	    if (object instanceof BaseGraphicalEditPart) {
        	object = getNamedElement((BaseGraphicalEditPart)object);
        }else if (object instanceof SequenceFlowEditPart) {
        	object = getAssertion((SequenceFlowEditPart)object);
        }
	    if(object instanceof NamedElement){
	    	return getImage4Element((NamedElement)object);
	    }
        return null;
	}
	
	private Image getImage4Element(EmulationElement element){
		if (element instanceof ProcessNode) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_PROCESSNODE);
        }else if (element instanceof Input) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
        }else if (element instanceof Output) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_OUTPUT);
        }else if (element instanceof Testpoint) {
            return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_TESTPOINT);
        }else if(element instanceof MultiInput){
        	 return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
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
	
	 public String getText(Object element) {
		 Object object = element;
		 if(object instanceof IStructuredSelection)
		    object = ((IStructuredSelection)object).getFirstElement();
		 if(object instanceof NamedElement){//ProcessNode or Testpoint or Assertion
			return ((NamedElement)object).getName();
		 }else if (object instanceof BaseGraphicalEditPart) {
			return EmulationBPMUtil.getDisplayName(((com.tibco.xpd.xpdl2.NamedElement)((BaseGraphicalEditPart)object).getModel()));
		}else if (object instanceof SequenceFlowEditPart) {
			EditPart from = ((SequenceFlowEditPart)object).getSource();
			EditPart to = ((SequenceFlowEditPart)object).getTarget();
			return EmulationBPMUtil.getDisplayName(((com.tibco.xpd.xpdl2.NamedElement)((BaseGraphicalEditPart)from).getModel()))
			+ " - " //$NON-NLS-1$
			+ EmulationBPMUtil.getDisplayName(((com.tibco.xpd.xpdl2.NamedElement)((BaseGraphicalEditPart)to).getModel()));
        }
		return super.getText(element);
	}

	private Assertion getAssertion(SequenceFlowEditPart editPart){
		Transition transition = (Transition)((SequenceFlowEditPart)editPart).getModel();
		ProcessNode processNode = EmulationBPMUIUtil.getProcessNode();
		if(processNode != null){
			return (Assertion)EmulationUtil.getAssertionById(processNode, transition.getId());
		}else{
			// get Assertion from cache
			return (Assertion)EmulationUtil.getAssertionFromCache(transition.getId());
		}
	}
	
	private NamedElement getNamedElement(BaseGraphicalEditPart editPart){
		String id = ((com.tibco.xpd.xpdl2.NamedElement)editPart.getModel()).getId();
		ProcessNode processNode = EmulationBPMUIUtil.getProcessNode();
		if(processNode != null){
			if(editPart instanceof ProcessEditPart){
				return processNode;
			}
			Input input = processNode.getInput();
			if(input != null && input.getId().equals(id)){
				return input;
			}
			Output output = processNode.getOutput();
			if(output != null && output.getId().equals(id)){
				return output;
			}
			Testpoint testpoint = EmulationUtil.getTestpointById(processNode, id);
			if(testpoint != null && testpoint.getId().equals(id)){
				return testpoint;
			}
			List<IntermediateInput> list = EmulationUtil.getIntermediateInputs(processNode, id);
			if(list.size()>0){
				return list.get(0);
			}
			
			List<MultiInput> multiList = EmulationUtil.getMultiInputs(processNode, id);
			if(multiList.size()>0){
				return multiList.get(0);
			}
		}
		return null;
	}
}

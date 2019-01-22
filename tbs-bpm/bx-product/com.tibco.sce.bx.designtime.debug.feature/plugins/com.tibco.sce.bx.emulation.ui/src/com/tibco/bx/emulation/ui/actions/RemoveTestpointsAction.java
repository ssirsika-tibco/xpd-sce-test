package com.tibco.bx.emulation.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IViewPart;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class RemoveTestpointsAction extends AbstractHandleTestpointsAction implements ISelectionChangedListener{

	EmulationElement[] elements = new EmulationElement[0];
	@Override
	public void init(IViewPart viewPart) {
		super.init(viewPart);
		getEmulationView().getViewSite().getSelectionProvider().addSelectionChangedListener(this);
	}

	@Override
	public void dispose() {
		getEmulationView().getViewSite().getSelectionProvider().removeSelectionChangedListener(this);
		super.dispose();
	}

	public void run(IAction action) {
		if(elements.length == 0)
			return;
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(elements[0]);
		
    	CompoundCommand compoundCommand = new CompoundCommand();
    	EStructuralFeature eStructuralFeature = null;
		for (int i = 0; i < elements.length; i++) {
			if(elements[i] instanceof Testpoint){
				eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Testpoints();
				compoundCommand.append(RemoveCommand.create(editingDomain, elements[i].eContainer(), eStructuralFeature, elements[i]));
			}else if(elements[i] instanceof Assertion){
				eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Assertions();
				compoundCommand.append(RemoveCommand.create(editingDomain, elements[i].eContainer(), eStructuralFeature, elements[i]));
			}else if(elements[i] instanceof IntermediateInput){
				eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_IntermediateInputs();
				compoundCommand.append(RemoveCommand.create(editingDomain, elements[i].eContainer(), eStructuralFeature, elements[i]));
			}else if(elements[i] instanceof Input){
				eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Input();
				compoundCommand.append(SetCommand.create(editingDomain, elements[i].eContainer(), eStructuralFeature, null));
			}else if(elements[i] instanceof Output){
				eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Output();
				compoundCommand.append(SetCommand.create(editingDomain, elements[i].eContainer(), eStructuralFeature, null));
			}else if (elements[i] instanceof MultiInput){
				eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_MultiInputNodes();
				compoundCommand.append(RemoveCommand.create(editingDomain, elements[i].eContainer(), eStructuralFeature, elements[i]));
			}
			
		}
		editingDomain.getCommandStack().execute(compoundCommand);
	}

	protected boolean isEnabled() {
		return elements.length > 0;
	}

	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (((IStructuredSelection)selection).size() >= 1) {
			 Iterator iterator = ((IStructuredSelection)selection).iterator();
				List<EmulationElement> tpList = new ArrayList<EmulationElement>();
				while(iterator.hasNext()){
					Object object = iterator.next();
					if (object instanceof Testpoint) {
						tpList.add((Testpoint)object);
					}else if(object instanceof Assertion) {
						tpList.add((Assertion)object);
					}else if(object instanceof Input){
						tpList.add((Input)object);
					}else if(object instanceof Output){
						tpList.add((Output)object);
					}else if(object instanceof IntermediateInput){
						tpList.add((IntermediateInput)object);
					}
				}
				elements = tpList.toArray(new EmulationElement[tpList.size()]);
		} else {
			elements = new EmulationElement[0];
		}
		update();
	}
	
}

package com.tibco.bx.emulation.bpm.ui.actions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

public class ToggleAssertionAction implements IActionDelegate{
	ProcessNode processNode;
	Assertion assertion;
	public void run(IAction action) {
		if(assertion == null) return;
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode);
        EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getAssertion_Accessible();
        Command command = SetCommand.create(editingDomain, assertion, eStructuralFeature, assertion.isAccessible()? false:true);
        editingDomain.getCommandStack().execute(command);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (((IStructuredSelection)selection).size() >= 1) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if(element instanceof SequenceFlowEditPart){
				processNode = EmulationBPMUIUtil.getProcessNode();
				Object model = ((SequenceFlowEditPart)element).getModel();
				if(processNode == null){
					Process process = ((Transition)model).getProcess();
					processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
				}
				assertion = null;
				if(model instanceof Transition){
					assertion = EmulationUtil.getAssertionById(processNode, ((Transition)model).getId());
					if (assertion != null && assertion.isAccessible()) {
						action.setText( Messages.getString("DisableAssertionAction_LABEL")); //$NON-NLS-1$
						action.setImageDescriptor(getdisabledImageDescription());
					}else{
						action.setText( Messages.getString("EnableAssertionAction_LABEL")); //$NON-NLS-1$
						action.setImageDescriptor(getEnabledImageDescription());
					}
				}else{
					return;
				}
			}  	
		}
		
	}

	protected ImageDescriptor getEnabledImageDescription() {
		return EmulationUIActivator.getDefault().getImageRegistry().getDescriptor(EmulationUIActivator.IMG_ASSERTION_EN);
	}

	protected ImageDescriptor getdisabledImageDescription() {
		return EmulationUIActivator.getDefault().getImageRegistry().getDescriptor(EmulationUIActivator.IMG_ASSERTION_DIS);
	}
}

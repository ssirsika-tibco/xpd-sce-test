package com.tibco.bx.emulation.bpm.ui.actions;

import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

public class DeleteAssertionAction implements IActionDelegate{

	private AbstractGraphicalEditPart editPart;
	private ProcessNode processNode;
	public void run(IAction action) {
		Object model = editPart.getModel();
		Assertion assertion = null;
		if(!(model instanceof Transition))
			return;
		processNode = EmulationBPMUIUtil.getProcessNode();
		
		if(processNode == null){
			Process process = ((Transition)model).getProcess();
			processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
		}
		
		assertion = EmulationUtil.getAssertionById(processNode, ((Transition)model).getId());
		
		if(processNode != null){
			EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode);
				editingDomain.getCommandStack().execute(
						DeleteCommand.create(editingDomain, assertion));
			// to call ((BpmnScrollingGraphicalViewer)editPart.getViewer()).fireSelectionChanged();
	        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart.getParent());
	        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (((IStructuredSelection)selection).size() >= 1) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if(element instanceof SequenceFlowEditPart){
				editPart =(SequenceFlowEditPart) element;
			}  	
		}
	}

}

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
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

public class DeleteTestpointAction implements IActionDelegate{

	private AbstractGraphicalEditPart editPart;
	private ProcessNode processNode;
	public void run(IAction action) {
		Object model = editPart.getModel();
		Testpoint testpoint = null;
		if(!(model instanceof Activity))
			return;
		processNode = EmulationBPMUIUtil.getProcessNode();
		if(processNode == null){
			Process process = ((Activity)model).getProcess();
			processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
		}

		testpoint = EmulationUtil.getTestpointById(processNode, ((Activity)model).getId());
		
		
		if(processNode != null){
			EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode);
				editingDomain.getCommandStack().execute(
						DeleteCommand.create(editingDomain, testpoint));
			// to call ((BpmnScrollingGraphicalViewer)editPart.getViewer()).fireSelectionChanged();
	        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart.getParent());
	        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (((IStructuredSelection)selection).size() >= 1) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if(element instanceof BaseFlowNodeEditPart){
				editPart=(BaseFlowNodeEditPart) element;
			} 	
		}
	}

}

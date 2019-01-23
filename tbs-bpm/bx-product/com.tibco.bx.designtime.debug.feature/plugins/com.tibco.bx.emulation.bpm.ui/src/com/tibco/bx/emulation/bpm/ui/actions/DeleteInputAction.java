package com.tibco.bx.emulation.bpm.ui.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

public class DeleteInputAction implements IActionDelegate{

	private BaseFlowNodeEditPart editPart;
	private ProcessNode processNode;
	public void run(IAction action) {
		Object model = editPart.getModel();
		Input input = null;
		EList<MultiInput> list = null;
		if(!(model instanceof Activity))
			return;
		processNode = EmulationBPMUIUtil.getProcessNode();
		
		if(processNode == null){
			Process process = ((Activity)model).getProcess();
			processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
		}
		
		input = processNode.getInput();
		String activityId = ((Activity)model).getId();
		if(input == null ||!(input.getId().equals(activityId))){
			list = processNode.getMultiInputNodes();
			for(MultiInput multiInput:list){
				if(activityId.equals(multiInput.getId())){
					input = multiInput;
					break;
				}
			}
		}
		if(processNode != null){
			EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode);
			IUndoableOperation operation = EmulationCommandHelper.getDeleteOperation(editingDomain, input);
	        try {
				OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
			} catch (ExecutionException e) {
				EmulationUIActivator.log(e);
			}
			// to call ((BpmnScrollingGraphicalViewer)editPart.getViewer()).fireSelectionChanged();
	        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart.getParent());
	        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (((IStructuredSelection)selection).size() >= 1) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if(element instanceof BaseFlowNodeEditPart){
				editPart =(BaseFlowNodeEditPart) element;
			}  	
		}
	}

}

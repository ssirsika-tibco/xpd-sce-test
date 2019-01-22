package com.tibco.bx.emulation.bpm.ui.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

public class AddIntermediateInputAction implements IActionDelegate{

	private BaseFlowNodeEditPart editPart;
	private EmulationData emulationData;
	private ProcessNode processNode;
	private Process process;
	public void run(IAction action) {
		Object model = editPart.getModel();
		if(model instanceof Activity){
			process = ((Activity)model).getProcess();
		}else{
			return;
		}
		processNode = EmulationBPMUIUtil.getProcessNode();
		if(processNode == null){
			emulationData = EmulationCacheManager.getDefault().getCurrentEmulationData();
			processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
		}else{
			emulationData = (EmulationData)processNode.eContainer();
		}
		//TODO in debug status, if the processNode is not existed, create processNode and testpoint in one command
		ProcessNode processNode = getProcessNode(process);
		createIntermediateInput(processNode, model);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (((IStructuredSelection)selection).size() >= 1) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if(element instanceof BaseFlowNodeEditPart){
				editPart=(BaseFlowNodeEditPart) element;
			}  	
		}
	}

	/*
	 * create a Testpoint
	 */
	private void createIntermediateInput(ProcessNode processNode, Object model){
		IntermediateInput intermediateInput = null;
		if(model instanceof Activity){
			intermediateInput = EmulationFactory.eINSTANCE.createIntermediateInput();
			intermediateInput.setName(((Activity)model).getName());
			intermediateInput.setId(((Activity)model).getId());
		}
		
		
		IUndoableOperation operation = EmulationCommandHelper.getIntermediateInputAddOperation(processNode, intermediateInput);
        try {
			OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
		} catch (ExecutionException e) {
			EmulationUIActivator.log(e);
		}
        
        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart.getParent());
        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart);
	}
	
	private ProcessNode getProcessNode(Process process){
		if(processNode == null){
			processNode = EmulationFactory.eINSTANCE.createProcessNode();
			processNode.setId(ProcessUtil.getElementId(process));
			processNode.setName(ProcessUtil.getDisplayName(process));
			processNode.setModelType(ProcessUtil.getModelType(process));
			IUndoableOperation operation = EmulationCommandHelper.getProcessNodeAddOperation(emulationData, processNode);
	        try {
				OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
			} catch (ExecutionException e) {
				EmulationUIActivator.log(e);
			}
		}
		return processNode;
	}
	
	
}

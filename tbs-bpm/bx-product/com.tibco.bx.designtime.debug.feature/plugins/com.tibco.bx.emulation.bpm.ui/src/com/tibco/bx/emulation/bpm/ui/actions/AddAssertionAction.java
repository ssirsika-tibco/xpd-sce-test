package com.tibco.bx.emulation.bpm.ui.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

public abstract class AddAssertionAction implements IActionDelegate{

	private AbstractGraphicalEditPart editPart;
	private EmulationData emulationData;
	private ProcessNode processNode;
	private Process process;
	public void run(IAction action) {
		Object model = editPart.getModel();
		if(model instanceof Transition){
			process = ((Transition)model).getProcess();
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
		//TODO if the processNode is not existed, create processNode and assertion in one command
		ProcessNode processNode = getProcessNode(process);
		createAssertion(processNode, model);
	}

	abstract boolean getAccessable();
	
	public void selectionChanged(IAction action, ISelection selection) {
		if (((IStructuredSelection)selection).size() >= 1) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if(element instanceof SequenceFlowEditPart){
				editPart =(SequenceFlowEditPart) element;
			}  	
		}
	}

	/*
	 * create an Assertion
	 */
	private void createAssertion(ProcessNode processNode, Object model){
		Assertion assertion = null;
		if(model instanceof Transition){
			assertion = EmulationFactory.eINSTANCE.createAssertion();
			String fromId = ((Transition)model).getFrom();
			String toId = ((Transition)model).getTo();
			WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopyFor(((Transition)model).getProcess());
			String fromName = ((Activity)workingCopy.resolveEObject(fromId)).getName();
			String toName = ((Activity)workingCopy.resolveEObject(toId)).getName();
			assertion.setName(fromName + " - " + toName); //$NON-NLS-1$
			assertion.setId(((Transition)model).getId());
			assertion.setAccessible(getAccessable());
		}
		IUndoableOperation operation = EmulationCommandHelper.getAssertionAddOperation(processNode, assertion);
        try {
			OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
		} catch (ExecutionException e) {
			EmulationUIActivator.log(e);
		}
		
        // to call ((BpmnScrollingGraphicalViewer)editPart.getViewer()).fireSelectionChanged();
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
